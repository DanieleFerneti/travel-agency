package it.uniroma3.siw.controller;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Credentials;
import it.uniroma3.siw.service.BookingService;
import it.uniroma3.siw.service.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.GroupService;
import it.uniroma3.siw.service.UserService;
import java.util.List;
import java.util.Set;


@Controller
public class GroupController {

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private CredentialsService credentialService;

    @GetMapping("/leader/user/{user_id}/group_management")
    public String getUserGroups(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.getUser(user_id);
        Group group = user.getGroup();

        if (group == null) {
            model.addAttribute("user_id", user_id);
            return "leader_administration";
        }
        model.addAttribute("user", user);
        model.addAttribute("group", group);
        return "leader/group/group_management";
    }

    @GetMapping("/leader/user/{user_id}/group_details")
    public String getGroupDetails(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.getUser(user_id);
        Group group = user.getGroup();

        if (group == null) {
            model.addAttribute("user_id", user_id);
            return "leader_administration";
        }

        model.addAttribute("user", user);
        model.addAttribute("group", group);
        model.addAttribute("groupUsers", group.getUsers());
        model.addAttribute("groupBookings", group.getBookings());

        return "leader/group/group_details";
    }



}




