package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.CredentialsService;
import it.uniroma3.siw.service.GroupService;
import it.uniroma3.siw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private CredentialsService credentialService;

    @GetMapping("/admin/user_management")
    public String getAllUsersAdmin(Model model) {
        Set<User> users = this.userService.getAllUsers().stream()
                .filter(user -> !user.getGroupName().equals("ROMA3PARTY"))
                .collect(Collectors.toSet());
        model.addAttribute("users", users);
        return "admin/user/user_management";
    }

    @GetMapping("/admin/user_management/delete_user/{id}")
    public String deleteUserAdmin(@PathVariable Long id, Model model) {
        boolean presenteInBooking = false;
        User user = this.userService.getUser(id);


        model.addAttribute("usr",user);

        if (!user.getGroupName().equals("ROMA3PARTY")) {
            if(!user.getGroup().getBookings().isEmpty()) {
                presenteInBooking = true;
            }

            if(!presenteInBooking) {
                Credentials credentials = credentialService.findById(id);
                this.credentialService.deleteById(credentials.getId());
                this.userService.deleteById(id);
                return "redirect:/admin/user_management";
            }
            else{
                Set<User> users = this.userService.getAllUsers().stream()
                        .filter(u -> !u.getGroupName().equals("ROMA3PARTY"))
                        .collect(Collectors.toSet());
                model.addAttribute("users", users);
                return "admin/user/user_management";
            }
        } else {
            Set<User> users = this.userService.getAllUsers().stream()
                    .filter(u -> !u.getGroupName().equals("ROMA3PARTY"))
                    .collect(Collectors.toSet());
            model.addAttribute("users", users);
            return "admin/user/user_management";
        }
    }

    @GetMapping("/admin/user_management/details_user/{id}")
    public String getUserDetailsAdmin(@PathVariable Long id, Model model) {
        User user = this.userService.getUser(id);
        if (!user.getGroupName().equals("ROMA3PARTY")) {
            model.addAttribute("user", user);
            model.addAttribute("group", user.getGroup());
            model.addAttribute("bookings", user.getGroup().getBookings());
            return "admin/user/user_details";
        } else {
            return "redirect:/admin/user_management";
        }
    }
}
