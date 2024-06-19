package it.uniroma3.siw.controller.validator;


import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class BookingValidator implements Validator {

    @Autowired
    private BookingService bookingService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Booking.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Booking booking = (Booking) target;

        // Validate group
        if (booking.getGroup() == null) {
            errors.rejectValue("group", "required");
        }

        // Validate week
        if (booking.getWeek() == null) {
            errors.rejectValue("week", "required");
        }

        // Validate if booking already exists
        if (this.bookingService.alreadyExists(booking)) {
            errors.reject("booking.duplicato");
        }
    }
}
