package services;

import model.*;

import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.*;

public class ReservationService {
    private static ReservationService INSTANCE;

    private final int maximumCheckInDateDifference = 7;
    private static final Map<String, IRoom> rooms = new HashMap<>();
    private final Map<String, Collection<Reservation>> reservations = new HashMap<>();

    private ReservationService() {}

    public static ReservationService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ReservationService();
        }
        return INSTANCE;
    }

    public static void addRoom(IRoom room) {
        rooms.put(room.getRoomNumber(), room);
    }

    public Collection<IRoom> findAllRooms() {
        return rooms.values();
    }

    public AlternateRooms findAlternateRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Validator validator = new Validator();
        validator.validateReservationDates(checkInDate, checkOutDate);
        int i = 1;
        LocalDate newCheckInDate;
        LocalDate newCheckOutDate;
        Collection<IRoom> possibleAlternateRooms = new ArrayList<IRoom>();
        AlternateRooms alternateRooms = new AlternateRooms(possibleAlternateRooms, checkInDate, checkOutDate);
        while (i <= maximumCheckInDateDifference) {
            newCheckInDate = checkInDate.plusDays(i);
            newCheckOutDate = checkOutDate.plusDays(i);
            possibleAlternateRooms = findRooms(newCheckInDate, newCheckOutDate);
            if (possibleAlternateRooms.size() > 0) {
                alternateRooms = new AlternateRooms(possibleAlternateRooms, newCheckInDate, newCheckOutDate);
                return alternateRooms;
            }
            i++;
        }
        return alternateRooms;
    }

    public Collection<IRoom> findRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        Validator validator = new Validator();
        validator.validateReservationDates(checkInDate, checkOutDate);
        HashSet<IRoom> unavailableRooms = new HashSet<IRoom>();
        for (Reservation customerReservation : getAllReservations()) {
            if (customerReservation.getCheckInDate().isBefore(checkOutDate)
                    && customerReservation.getCheckOutDate().isAfter(checkInDate)) {
                    unavailableRooms.add(customerReservation.getRoom());
            }
        }
        Collection<IRoom> availableRooms = new ArrayList<IRoom>();
        for(IRoom possibleRoom : findAllRooms()) {
            if (! unavailableRooms.contains(possibleRoom)) {
                availableRooms.add(possibleRoom);
            }
        }
        return availableRooms;
    }

    public Collection<Reservation> getAllReservations() {
        Collection<Reservation> allReservations = new ArrayList<Reservation>();
        for(Collection<Reservation> reservations : reservations.values()) {
            allReservations.addAll(reservations);
        }
        return allReservations;
    }

    public Collection<Reservation> getCustomerReservations(Customer customer) {
        return reservations.get(customer.email());
    }

    public IRoom getRoom(String roomNumber) {
        roomNumber = roomNumber.toLowerCase();
        return rooms.get(roomNumber);
    }

    public void printAllReservations() {
        Collection<Reservation> allReservations = getAllReservations();
        if (allReservations.isEmpty()) {
            printEmpty();
        } else {
            for(Reservation customerReservation : allReservations) {
                System.out.println(customerReservation.toString() + "\n");
            }
        }
    }

    void printEmpty() {
        System.out.println("There are no existing reservations.");
    }

    public Reservation reserveARoom(Customer customer, IRoom room, LocalDate checkInDate, LocalDate checkOutDate) throws Exception {
        CustomerService customerService = CustomerService.getInstance();
        Customer checkCustomer = customerService.getCustomer(customer.email());
        if (checkCustomer == null) {
            throw new InvalidParameterException("Customer " + customer.email() + " is not in customer records");
        }
        IRoom checkRoom = getRoom(room.getRoomNumber());
        if (checkRoom == null) {
            throw new InvalidParameterException("Room " + room.getRoomNumber() + " is not in room records");
        }
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        for (Reservation customerReservation : getAllReservations()) {
            if (customerReservation.getRoom().equals(room)
                    && customerReservation.getCheckInDate().isBefore(checkOutDate)
                    && customerReservation.getCheckOutDate().isAfter(checkInDate)) {
                throw new DateTimeException("Requested room is already reserved for given dates.");
            }
        }
        Collection<Reservation> customerReservations = getCustomerReservations(customer);
        if (customerReservations == null) {
            customerReservations = new ArrayList<>();
        }
        customerReservations.add(reservation);
        reservations.put(customer.email(), customerReservations);
        return reservation;
    }
}
