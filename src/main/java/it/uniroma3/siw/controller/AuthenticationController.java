package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.CredentialsValidator;
import it.uniroma3.siw.controller.validator.UserValidator;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.GroupService;
import it.uniroma3.siw.service.UserService;
import it.uniroma3.siw.service.WeekService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class AuthenticationController {
    @Autowired
    private CredentialsService credentialsService;

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private CredentialsValidator credentialsValidator;

    @Autowired
    private WeekService weekService;

    @GetMapping(value = "/register")
    public String showRegisterForm (Model model, @RequestParam(value = "imageFile", required = false) MultipartFile imageFile) {
        model.addAttribute("user", new User());
        model.addAttribute("credentials", new Credentials());
        return "formRegisterUser";
    }

    @GetMapping("/login")
    public String showLoginForm () {
        return "formLogin";
    }

    @GetMapping(value = "/")
    public String index(Model model) {
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return "index";
        }
        else {
            UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Credentials credentials = credentialsService.findByUsername(userDetails.getUsername());
            if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                return "administration";
            }
            if (credentials.getRole().equals(Credentials.LEADER_ROLE)) {
                Long user_id= credentials.getUser().getId();
                model.addAttribute("user_id", user_id);
                return "leader_administration";
            }
            if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
                Long user_id= credentials.getUser().getId();
                model.addAttribute("user_id", user_id);
                return "default_administration";
            }

        }
        return "index";
    }

    @PostMapping(value = { "/register" })
    public String registerUser(@Valid @ModelAttribute("user") User user,
                               BindingResult userBindingResult, @Valid
                               @ModelAttribute("credentials") Credentials credentials,
                               BindingResult credentialsBindingResult, Model model) {

        this.userValidator.validate(user,userBindingResult);
        this.credentialsValidator.validate(credentials,credentialsBindingResult);
        if (!userBindingResult.hasErrors() && !credentialsBindingResult.hasErrors()) {
            this.userService.save(user); // Save user first to get the ID
            credentials.setUser(user);
            credentialsService.save(credentials);
            model.addAttribute("user", user);
            String groupName = user.getGroupName();
            Group userGroup = groupService.getGroupByName(groupName);
            if (userGroup != null) {
                // If the group exists, add the user to the group if not already present
                if (!userGroup.getUsers().contains(user)) {
                    userGroup.getUsers().add(user);
                    groupService.save(userGroup);
                }
            } else {
                // If the group does not exist, create a new group and add the user to it
                userGroup = new Group();
                Set<User> users = new HashSet<>();
                users.add(user);
                userGroup.setName(groupName);
                userGroup.setUsers(users);
                groupService.save(userGroup);
            }

            // Add the group to the user's groups
            user.setGroup(userGroup);
            userService.save(user);

            return "registrationSuccessful";
        }
        return "formRegisterUser";
    }



    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }


    @GetMapping("/success")
    public String defaultAfterLogin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                Credentials credentials = credentialsService.findByUsername(userDetails.getUsername());
                if (credentials != null) {
                    if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                        return "administration";
                    } else if (credentials.getRole().equals(Credentials.LEADER_ROLE)) {
                        model.addAttribute("user_id", credentials.getUser().getId());
                        return "leader_administration";
                    } else if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
                        model.addAttribute("user_id", credentials.getUser().getId());
                        return "default_administration";
                    }
                }
            }
            else{
                OAuth2User userDetails = (OAuth2User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                System.out.println(userDetails.getAttributes().toString());
                String email = userDetails.getAttribute("email");
                User user = userService.findByEmail(email);
                if(user == null) {
                    user = new User();
                    user.setName((String)userDetails.getAttribute("given_name"));
                    user.setSurname((String)userDetails.getAttribute("family_name"));
                    user.setEmail(email);
                    userService.save(user);

                    Credentials  credentials = new Credentials();
                    credentials.setUsername(email);
                    credentials.setUser(user);
                    credentials.setRole(Credentials.DEFAULT_ROLE);
                    credentialsService.save(credentials);
                }
                Credentials credentials = credentialsService.findById(user.getId());
                Long userId = credentials.getUser().getId();
                model.addAttribute("user_id", userId);

                // Gestisci il reindirizzamento basato sul ruolo
                if (credentials.getRole().equals(Credentials.ADMIN_ROLE)) {
                    return "administration";
                } else if (credentials.getRole().equals(Credentials.LEADER_ROLE)) {
                    return "leader_administration";
                } else if (credentials.getRole().equals(Credentials.DEFAULT_ROLE)) {
                    return "default_administration";
                }
                return "formLogin";
            }
        }
        return "formLogin";
    }



}
