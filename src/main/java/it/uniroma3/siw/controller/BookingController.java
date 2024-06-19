package it.uniroma3.siw.controller;

import it.uniroma3.siw.controller.validator.BookingValidator;
import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.Week;
import it.uniroma3.siw.service.BookingService;
import it.uniroma3.siw.service.WeekService;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingValidator bookingValidator;

    @Autowired
    private WeekService weekService;

    @Autowired
    private UserService userService;

    @GetMapping("/leader/user/{user_id}/booking_management")
    public String getAllBookingsLeader(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.getUser(user_id);
        model.addAttribute("user", user);
        model.addAttribute("bookings", user.getGroup().getBookings());
        return "leader/booking/booking_management";
    }

    @GetMapping("/user/{user_id}/booking_management")
    public String getAllBookingsDefault(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.getUser(user_id);
        model.addAttribute("user", user);
        model.addAttribute("bookings", user.getGroup().getBookings());
        return "default/booking/booking_management";
    }
    
    

    @GetMapping("/leader/user/{user_id}/booking_management/add_booking")
    public String showAddBookingFormLeader(@PathVariable("user_id") Long user_id, Model model) {
        User user = userService.getUser(user_id);
        model.addAttribute("user", user);
        model.addAttribute("booking", new Booking());
        model.addAttribute("weeks", this.weekService.getAllWeeks());
        return "leader/booking/create_booking";
    }

    @PostMapping("/leader/user/{user_id}/booking_management/add_booking")
    public String addBookingLeader(@PathVariable("user_id") Long user_id, @Valid @ModelAttribute("booking") Booking booking,
                                  BindingResult bindingResult, Model model) {

        User user = userService.getUser(user_id);
        Group groupOfUser = user.getGroup();
        booking.setGroup(groupOfUser);

        this.bookingValidator.validate(booking, bindingResult);

        if (!bindingResult.hasErrors()) {
            this.bookingService.save(booking);
            Week week = booking.getWeek();
            Set<Booking> bookings = week.getBookings();
            bookings.add(booking);
            week.setBookings(bookings);
            this.weekService.save(week);
            return "redirect:/leader/user/{user_id}/booking_management";
        } else {
            model.addAttribute("user", user);
            model.addAttribute("weeks", this.weekService.getAllWeeks());
            return "leader/booking/create_booking";
        }
    }


    @GetMapping("/leader/user/{user_id}/booking_management/delete_booking/{id}")
    public String deleteBookingLeader(@PathVariable("user_id") Long user_id, @PathVariable("id") Long id) {
        this.bookingService.deleteById(id);
        return "redirect:/leader/user/{user_id}/booking_management";
    }

    @GetMapping("/leader/user/{user_id}/booking_management/booking_details/{id}")
    public String showBookingDetailsLeader(@PathVariable("user_id") Long user_id, @PathVariable("id") Long id, Model model) {
        User user = userService.getUser(user_id);
        Booking booking = this.bookingService.getBooking(id);
        model.addAttribute("user", user);
        model.addAttribute("booking", booking);
        model.addAttribute("week", booking.getWeek());
        return "leader/booking/booking_details";
    }
}
