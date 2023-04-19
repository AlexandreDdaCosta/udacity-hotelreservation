package model;

import java.time.LocalDate;

public class Reservation {

    private final Customer customer;
    private final IRoom room;
    private final LocalDate checkInDate;
    private final LocalDate checkOutDate;

    public Reservation(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) {
        Validator validator = new Validator();
        validator.validateReservationDates(checkInDate, checkOutDate);
        this.customer = customer;
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public IRoom getRoom() {
        return room;
    }

    @Override
    public String toString() {
        return "Customer:\n" + customer.toString() + "\n"
                + "Room:\n" + room.toString() + "\n"
                + "Check In Date: " + checkInDate + "\n"
                + "Check Out Date: " + checkOutDate;
    }
}

