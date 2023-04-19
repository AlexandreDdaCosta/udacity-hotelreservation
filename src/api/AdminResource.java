package api;

import model.Customer;
import model.IRoom;
import services.CustomerService;
import services.ReservationService;

import java.util.Collection;
import java.util.List;

public class AdminResource {

    private static AdminResource INSTANCE;

    private final CustomerService customerService = CustomerService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();

    private AdminResource() {}

    public static AdminResource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AdminResource();
        }
        return INSTANCE;
    }

    public void addARoom(IRoom room) {
        reservationService.addRoom(room);
    }

    public void addRoom(List<IRoom> rooms) {
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }

    public void displayAllReservations() {
        reservationService.printAllReservations();
    }

    public Collection<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    public Collection<IRoom> getAllRooms() {
        return reservationService.findAllRooms();
    }
}