package it.uniroma3.siw.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Entity
public class Week {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    @NotNull
    private LocalDate dateFrom;


    @NotNull
    private LocalDate dateTo;

    @NotBlank
    @NotNull
    private String transport;


    @NotBlank
    @NotNull
    private String plan;

    @NotBlank
    @NotNull
    private String location;

    @NotBlank
    @NotNull
    private String hotel;


    @NotNull
    private Integer price;

    @OneToMany(cascade = CascadeType.ALL)
    private Set<Booking> bookings;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Set<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(Set<Booking> bookings) {
        this.bookings = bookings;
    }

    public LocalDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(LocalDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public LocalDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(LocalDate dateTo) {
        this.dateTo = dateTo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Week week)) return false;
        return Objects.equals(id, week.id) && Objects.equals(dateFrom, week.dateFrom) && Objects.equals(dateTo, week.dateTo) && Objects.equals(transport, week.transport) && Objects.equals(plan, week.plan) && Objects.equals(location, week.location) && Objects.equals(hotel, week.hotel) && Objects.equals(price, week.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateFrom, dateTo, transport, plan, location, hotel, price);
    }
}
