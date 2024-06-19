package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.WeekValidator;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Week;
import it.uniroma3.siw.service.WeekService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class WeekController {

    @Autowired
    private WeekService weekService;


    @Autowired
    private WeekValidator weekValidator;



    @GetMapping("/admin/week_management")
    public String getAllWeeksAdmin(Model model) {
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        return "admin/week/week_management";
    }


    @GetMapping("/admin/week_management/add_week")
    public String showAddWeekFormAdmin(Model model) {
        model.addAttribute("week", new Week());
        return "admin/week/create_week";
    }


    @PostMapping("/admin/week_management/add_week")
    public String addWeekAdmin(@Valid @ModelAttribute("week") Week week,
                               BindingResult bindingResult,
                               Model model,
                               Errors errors) {

        this.weekValidator.validate(week, bindingResult);

        if (!bindingResult.hasErrors()) {
            this.weekService.save(week);
            return "redirect:/admin/week_management";
        } else {
            return "admin/week/create_week";
        }
    }

    @GetMapping("/admin/week_management/edit_week/{id}")
    public String showEditWeekFormAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("week", this.weekService.getWeek(id));
        return "admin/week/edit_week";
    }


    @PostMapping("/admin/week_management/{id}")
    public String editWeek(@PathVariable Long id, @Valid @ModelAttribute("week") Week week, BindingResult bindingResult,Model model) {

        this.weekValidator.validate(week,bindingResult);
        if (!bindingResult.hasErrors()){
            this.weekService.save(week);
            return "redirect:/admin/week_management";
        } else {
            model.addAttribute("week", week);
            return "admin/week/edit_week";
        }
    }

    @GetMapping("/admin/week_management/delete_week/{id}")
    public String deleteWeekAdmin(@PathVariable Long id, Model model) {
        Week week = this.weekService.getWeek(id);
        boolean presenteInBooking = checkIfWeekPresentInAnyBooking(week);

        model.addAttribute("w", week);
        if (!presenteInBooking) {
                    this.weekService.deleteById(id);
                return "redirect:/admin/week_management";
        } else {
            model.addAttribute("weeks", this.weekService.getAllWeeks());
            return "admin/week/week_management";
        }
    }

    @GetMapping("/admin/week_management/week_details/{id}")
    public String showWeekDetailsAdmin(@PathVariable Long id, Model model) {
        model.addAttribute("week", this.weekService.getWeek(id));
        return "admin/week/week_details";
    }


    @GetMapping("/week/{id}")
    public String getWeeks(@PathVariable("id") Long id, Model model) {
        Week week = this.weekService.getWeek(id);
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        model.addAttribute("week", week);

        // Verifica della location della week
        if (week.getLocation().equalsIgnoreCase("barcellona")) {
            return "barcellona";
        } else if (week.getLocation().equalsIgnoreCase("mykonos")) {
            return "mykonos";
        } else {
            return "zante";
        }
    }


    private boolean checkIfWeekPresentInAnyBooking(Week week) {
            Set<Booking> booking = week.getBookings();
            if(booking.isEmpty())
                return false;
            return true;
    }



}
