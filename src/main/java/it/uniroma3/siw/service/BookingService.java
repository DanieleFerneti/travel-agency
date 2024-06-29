package it.uniroma3.siw.service;

import it.uniroma3.siw.model.Booking;
import it.uniroma3.siw.model.Group;
import it.uniroma3.siw.model.User;
import it.uniroma3.siw.model.Week;
import it.uniroma3.siw.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<Booking>();

        for(Booking b : this.bookingRepository.findAll()) {
            bookings.add(b);
        }
        return bookings;
    }

    @Transactional
    public void save(Booking booking) {
        this.bookingRepository.save(booking);
    }

    @Transactional
    public Booking getBooking(Long id) {
        return this.bookingRepository.findById(id).get();
    }

    @Transactional
    public void deleteById(Long id) {
        this.bookingRepository.deleteById(id);
    }

    @Transactional
    public List<Booking> getBookingsByGroup(Group group) {
        return bookingRepository.findByGroup(group);
    }

    public boolean alreadyExists(Booking booking) {
        return bookingRepository.existsByGroupAndWeek(booking.getGroup(), booking.getWeek());
    }
}
