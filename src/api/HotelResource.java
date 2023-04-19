package api;

import model.AlternateRooms;
import model.Customer;
import model.IRoom;
import model.Reservation;
import services.CustomerService;
import services.ReservationService;

import java.time.LocalDate;
import java.util.Collection;

public class HotelResource {

    private static HotelResource INSTANCE;

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private HotelResource() {
    }

    public static HotelResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HotelResource();
        }
        return INSTANCE;
    }

    public Reservation bookARoom(String customerEmail, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) throws Exception {
        return reservationService.reserveARoom(getCustomer(customerEmail), room, checkInDate, checkOutDate);
    }

    public void createACustomer(String email, String firstName, String lastName) {
        customerService.addCustomer(email, firstName, lastName);
    }

    public Collection<IRoom> findARoom(LocalDate checkIn, LocalDate checkOut) {
        return reservationService.findRooms(checkIn, checkOut);
    }

    public AlternateRooms findAlternateRooms(LocalDate checkIn, LocalDate checkOut) {
        return reservationService.findAlternateRooms(checkIn, checkOut);
    }

    public Customer getCustomer(String email) {
        return customerService.getCustomer(email);
    }

    public Collection<Reservation> getCustomerReservations(String customerEmail) {
        return reservationService.getCustomerReservations(getCustomer(customerEmail));
    }

    public IRoom getRoom(String roomNumber) {
        return reservationService.getRoom(roomNumber);
    }
}