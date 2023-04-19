package tester;

import model.*;
import services.CustomerService;
import services.ReservationService;

import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

/*
Test coverage for services layer
 */
public class Services {

    public static void main(String[] args) throws Exception {

        /*
        services.CustomerService
        */
        CustomerService customerService = CustomerService.getInstance();
        String firstEmail = "barneyrubble@hannabarbera.com";
        customerService.addCustomer(firstEmail, "Barney", "Rubble");
        String secondEmail = "fredflintstone@hannabarbera.com";
        String firstName = "Freddy";
        String lastName = "Flintrock";
        customerService.addCustomer(secondEmail, firstName, lastName);
        System.out.println("Retrieved result of successful customer addition:");
        Customer customer = customerService.getCustomer(secondEmail);
        System.out.println(customer + "\n");
        customer = customerService.getCustomer("Barney Rubble@hannabarbera.com");
        System.out.println("Retrieved result of successful customer addition:");
        System.out.println(customer + "\n");
        firstName = "Fred";
        lastName = "Flintstone";
        customerService.addCustomer(secondEmail, firstName, lastName);
        customer = customerService.getCustomer(secondEmail);
        if (customer.firstName().equals(firstName) && customer.lastName().equals(lastName)) {
            System.out.println("Successfully re-added customer with updated name, as follows:");
            System.out.println(customer + "\n");
        } else {
            throw new Exception("Failed to update customer name on re-addition: " + customer);
        }
        Collection<Customer> customers = customerService.getAllCustomers();
        for (Customer listedCustomer : customers) {
            if (listedCustomer.email().equals(firstEmail)) {
                System.out.println("Full listing retrieved " + firstEmail);
            } else if (listedCustomer.email().equals(secondEmail)) {
                System.out.println("Full listing retrieved " + secondEmail);
            } else {
                throw new Exception("Full listing retrieved unknown customer, as follows:\n" + customer);
            }
        }
        System.out.println();
        String unknownCustomerEmail = "bettyrubble@hannabarbera.com";
        Customer unknownCustomer = null;
        try {
            unknownCustomer = customerService.getCustomer(unknownCustomerEmail);
        } catch (InvalidParameterException e) {
            System.out.println("Correctly failed to retrieve non-existent customer " + unknownCustomerEmail);
        }
        if (unknownCustomer != null) {
            throw new Exception("Retrieved unknown customer, as follows:\n" + customer);
        }
        System.out.println();

        /*
        services.ReservationService
        */
        ReservationService reservationService = ReservationService.getInstance();
        int totalRoomsAdded = 0;
        Room firstRoom = new Room("101a", 100.00, "single");
        ReservationService.addRoom(firstRoom);
        totalRoomsAdded++;
        Room secondRoom = new Room("102B", 100.00, "single");
        ReservationService.addRoom(secondRoom);
        totalRoomsAdded++;
        Room thirdRoom = new Room("201", 200.00, "double");
        ReservationService.addRoom(thirdRoom);
        totalRoomsAdded++;
        Room fourthRoom = new Room("202", 200.00, "double");
        ReservationService.addRoom(fourthRoom);
        totalRoomsAdded++;
        Collection<IRoom> rooms = reservationService.findAllRooms();
        int totalRoomsCounted = 0;
        for (IRoom checkRoom : rooms) {
            if (checkRoom.getRoomNumber().equals(firstRoom.getRoomNumber())) {
                System.out.println("First room retrieved, as follows:\n" + checkRoom);
                totalRoomsCounted++;
            } else if (checkRoom.getRoomNumber().equals(secondRoom.getRoomNumber())) {
                System.out.println("Second room retrieved, as follows:\n" + checkRoom);
                totalRoomsCounted++;
            } else if (checkRoom.getRoomNumber().equals(thirdRoom.getRoomNumber())) {
                System.out.println("Third room retrieved, as follows:\n" + checkRoom);
                totalRoomsCounted++;
            } else if (checkRoom.getRoomNumber().equals(fourthRoom.getRoomNumber())) {
                System.out.println("Fourth room retrieved, as follows:\n" + checkRoom);
                totalRoomsCounted++;
            } else {
                throw new Exception("Full listing retrieved unknown room, as follows:\n"
                                     + checkRoom);
            }
        }
        if (totalRoomsAdded == totalRoomsCounted) {
            System.out.println("Total rooms added matches total rooms counted: "
                    + totalRoomsAdded + " == " + totalRoomsCounted);
        } else {
            throw new Exception("Total rooms added does not match total rooms counted: "
                    + totalRoomsAdded + " == " + totalRoomsCounted);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkInDateString = LocalDate.now().plusDays(5).toString();
        String checkOutDateString = LocalDate.now().plusDays(8).toString();
        LocalDate checkInDate = LocalDate.parse(checkInDateString, formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateString, formatter);
        Collection<IRoom> availableRooms = reservationService.findRooms(checkInDate, checkOutDate);
        customer = customerService.getCustomer("fredflintstone@hannabarbera.com");
        IRoom selectedRoom = (IRoom) availableRooms.toArray()[2];
        Reservation firstReservation = reservationService.reserveARoom(customer, selectedRoom,
                checkInDate, checkOutDate);
        System.out.println("\nFirst reservation was as follows:");
        System.out.println(firstReservation);
        selectedRoom = (IRoom) availableRooms.toArray()[0];
        Reservation secondReservation = reservationService.reserveARoom(customer, selectedRoom,
                checkInDate, checkOutDate);
        System.out.println("\nSecond reservation was as follows:");
        System.out.println(secondReservation);
        int countOfReservations = reservationService.getAllReservations().size();
        if (!(countOfReservations == 2)) {
            throw new Exception("Mismatch between count of reservations and actual reservations submitted:" +
                    countOfReservations + " != 2");
        } else {
            System.out.println("\nCount of reservations matches number of reservations submitted: 2\n");
        }
        int countOfAvailableRooms = printAvailableRooms(reservationService, checkInDate, checkOutDate);
        if (!(countOfAvailableRooms == 2)) {
            throw new Exception("Mismatch between count of available rooms and actual rooms available "
                    + "on selected dates:" + countOfAvailableRooms + " != 2");
        } else {
            System.out.println("\nCount of available rooms on selected dates is correct: 0");
        }
        customer = customerService.getCustomer("barneyrubble@hannabarbera.com");
        selectedRoom = (IRoom) availableRooms.toArray()[3];
        Reservation thirdReservation = reservationService.reserveARoom(customer, selectedRoom,
                checkInDate, checkOutDate);
        System.out.println("\nThird reservation was as follows:");
        System.out.println(thirdReservation);
        selectedRoom = (IRoom) availableRooms.toArray()[1];
        Reservation fourthReservation = reservationService.reserveARoom(customer, selectedRoom,
                checkInDate, checkOutDate);
        System.out.println("\nFourth reservation was as follows:");
        System.out.println(fourthReservation);
        System.out.println("\nPrint out of all current reservations:");
        reservationService.printAllReservations();
        countOfReservations = reservationService.getAllReservations().size();
        if (!(countOfReservations == 4)) {
            throw new Exception("Mismatch between count of reservations and actual reservations submitted:" +
                    countOfReservations + " != 4");
        } else {
            System.out.println("Count of reservations matches number of reservations submitted: 4\n");
        }
        countOfAvailableRooms = printAvailableRooms(reservationService, checkInDate, checkOutDate);
        if (!(countOfAvailableRooms == 0)) {
            throw new Exception("Mismatch between count of available rooms and actual rooms available "
                                 + "on selected dates:" + countOfAvailableRooms + " != 0");
        } else {
            System.out.println("\nCount of available rooms on selected dates is correct: 0");
        }
        Reservation fifthReservation = null;
        String thirdEmail = "georgejetson@warnerbrothers.com";
        String thirdFirstName = "George";
        String thirdLastName = "Jetson";
        customer = new Customer(thirdFirstName, thirdLastName, thirdEmail);
        try {
            fifthReservation = reservationService.reserveARoom(customer, selectedRoom,
                    checkInDate, checkOutDate);
        } catch (InvalidParameterException e) {
            System.out.println("Correctly caught bad reservation: customer " + customer.email() + " not found");
        }
        if (fifthReservation != null) {
            throw new Exception("Failed to catch bad reservation: customer " + customer.email()
                                 + "not in customer records");
        }
        customerService.addCustomer(thirdEmail, thirdFirstName, thirdLastName);
        customer = customerService.getCustomer("georgejetson@warnerbrothers.com");
        selectedRoom = new Room("203", 200.00, "double");
        try {
            fifthReservation = reservationService.reserveARoom(customer, selectedRoom,
                    checkInDate, checkOutDate);
        } catch (InvalidParameterException e) {
            System.out.println("Correctly caught bad reservation: unknown room "
                                + selectedRoom.getRoomNumber());
        }
        if (fifthReservation != null) {
            throw new Exception("Failed to catch bad reservation: unknown room " + selectedRoom.getRoomNumber());
        }
        selectedRoom = (IRoom) availableRooms.toArray()[3];
        try {
            fifthReservation = reservationService.reserveARoom(customer, selectedRoom,
                    checkInDate, checkOutDate);
        } catch (DateTimeException e) {
            System.out.println("Correctly caught conflicting reservation: "
                               + "room already reserved for all or part of date range");
        }
        if (fifthReservation != null) {
            throw new Exception("Failed to catch bad reservation: room already reserved for all or part of date range");
        }
        AlternateRooms alternateRooms = reservationService.findAlternateRooms(checkInDate, checkOutDate);
        System.out.println("\nPrint out of available alternate rooms:");
        System.out.println(alternateRooms);
        selectedRoom = (IRoom) alternateRooms.rooms().toArray()[0];
        fifthReservation = reservationService.reserveARoom(customer, selectedRoom,
                alternateRooms.checkInDate(), alternateRooms.checkOutDate());
        System.out.println("\nFifth reservation was as follows:");
        System.out.println(fifthReservation);
        selectedRoom = (IRoom) alternateRooms.rooms().toArray()[2];
        Reservation sixthReservation = reservationService.reserveARoom(customer, selectedRoom,
                alternateRooms.checkInDate(), alternateRooms.checkOutDate());
        System.out.println("\nSixth reservation was as follows:");
        System.out.println(sixthReservation);
        System.out.println("\nPrint out of all current reservations:");
        reservationService.printAllReservations();
        countOfReservations = reservationService.getAllReservations().size();
        if (!(countOfReservations == 6)) {
            throw new Exception("Mismatch between count of reservations and actual reservations submitted:" +
                    countOfReservations + " != 6");
        } else {
            System.out.println("Count of reservations matches number of reservations submitted: 6");
        }
        Collection<Reservation> reservations = reservationService.getCustomerReservations(customer);
        if (reservations.size() != 2) {
            throw new Exception("Expected two reservations for customer " + thirdEmail
                                 + "; " + reservations.size() + " retrieved.");
        } else {
            System.out.println("Successfully retrieved two reservations for customer " + thirdEmail);
        }
        for (Reservation reservation : reservations) {
            if (reservation.equals(fifthReservation)) {
                System.out.println("\nFirst reservation found in customer reservation list:\n"
                                    + reservation + "\n");
            } else if (reservation.equals(sixthReservation)) {
                System.out.println("\nSecond reservation found in customer reservation list:\n"
                        + reservation + "\n");
            } else {
                throw new Exception("Unknown reservation found in customer reservation list:\n"
                        + reservation);
            }
        }
    }

    private static int printAvailableRooms(ReservationService reservationService,
                                                         LocalDate checkInDate, LocalDate checkOutDate) {
        Collection<IRoom> availableRooms = reservationService.findRooms(checkInDate, checkOutDate);
        System.out.println("Current list of available rooms for "
                + "check in date " + checkInDate
                + ", check out date " + checkInDate + ":");
        int countOfAvailableRooms = 0;
        for(IRoom availableRoom : availableRooms) {
            countOfAvailableRooms++;
            System.out.println(availableRoom.toString());
        }
        if (countOfAvailableRooms == 0) {
            System.out.println("No rooms available for selected dates");
        }
        return countOfAvailableRooms;
    }
}
