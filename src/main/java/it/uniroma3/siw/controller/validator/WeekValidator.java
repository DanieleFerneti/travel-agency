package it.uniroma3.siw.controller.validator;

import it.uniroma3.siw.model.Week;
import it.uniroma3.siw.service.WeekService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.Month;

@Component
public class WeekValidator implements Validator {

    private static final int MAX_STRING_LENGTH = 255;
    private static final int MIN_STRING_LENGTH = 2;
    private static final long MIN_PRICE = 0;

    @Autowired
    private WeekService weekService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Week.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Week week = (Week) target;
        String transport = week.getTransport().trim();
        String plan = week.getPlan().trim();
        String location = week.getLocation().trim();
        String hotel = week.getHotel().trim();
        Integer price = week.getPrice();
        LocalDate dateFrom = week.getDateFrom();
        LocalDate dateTo = week.getDateTo();

        if (transport.length() < MIN_STRING_LENGTH || transport.length() > MAX_STRING_LENGTH) {
            errors.rejectValue("transport", "transport.size");
        }

        if (plan.length() < MIN_STRING_LENGTH || plan.length() > MAX_STRING_LENGTH) {
            errors.rejectValue("plan", "plan.size");
        }

        if (location.length() < MIN_STRING_LENGTH || location.length() > MAX_STRING_LENGTH) {
            errors.rejectValue("location", "location.size");
        }

        if (hotel.length() < MIN_STRING_LENGTH || hotel.length() > MAX_STRING_LENGTH) {
            errors.rejectValue("hotel", "hotel.size");
        }

        if (price == null || price <= MIN_PRICE) {
            errors.rejectValue("price", "price.invalid");
        }
        // Validate dateFrom and dateTo
        if (dateFrom == null || dateTo == null) {
            errors.rejectValue("dateFrom", "date.null");
            errors.rejectValue("dateTo", "date.null");
        } else {
            if (dateFrom.getYear() != 2024 || dateTo.getYear() != 2024) {
                errors.rejectValue("dateFrom", "date.year");
                errors.rejectValue("dateTo", "date.year");
            }

            if ((dateFrom.getMonth() != Month.JULY && dateFrom.getMonth() != Month.AUGUST) ||
                    (dateTo.getMonth() != Month.JULY && dateTo.getMonth() != Month.AUGUST)) {
                errors.rejectValue("dateFrom", "date.month");
                errors.rejectValue("dateTo", "date.month");
            }

            if (!dateFrom.isBefore(dateTo)) {
                errors.rejectValue("dateFrom", "date.order");
            }

            if (!dateFrom.plusDays(7).equals(dateTo)) {
                errors.rejectValue("dateTo", "date.duration");
            }
        }


        // Validate if chef already exists
        if (this.weekService.alreadyExists(week)) {
            errors.reject("week.duplicato");
        }
    }
}

