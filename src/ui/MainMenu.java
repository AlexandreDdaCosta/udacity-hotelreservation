package ui;

import java.security.InvalidParameterException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Scanner;

import api.HotelResource;
import model.AlternateRooms;
import model.Customer;
import model.IRoom;
import model.Reservation;

class MainMenu {

    private static final HotelResource hotelResource = HotelResource.getInstance();

    public MainMenu() {
        Scanner reader = new Scanner(System.in);
        String selection = "";
        while (! selection.equals("5")) {
            displayMenu();
            selection = reader.nextLine();
            switch (selection) {
                case "1":
                    System.out.println();
                    bookARoom();
                    break;
                case "2":
                    System.out.println();
                    seeAllReservations();
                    break;
                case "3":
                    System.out.println();
                    createAccount();
                    break;
                case "4":
                    new AdminMenu();
                    break;
                case "5":
                    break;
                default:
                    System.out.println("Bad entry.");
                    break;
            }
        }
    }

    public static void displayMenu() {
        System.out.print("""

                Main Menu
                --------------------------------------
                1. Find and reserve a room
                2. See my reservations
                3. Create an account
                4. Administrative menu
                5. Exit
                --------------------------------------
                Please select a menu option by number:
                """);
    }

    private static void bookARoom() {
        System.out.println("Find and reserve a room.");
        System.out.println("NOTE: You will need an account with us to book.");
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter a date range you are interested in.");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("Enter a check-in date in format \"yyyy-MM-dd\":");
        String checkInDateString = reader.nextLine();
        LocalDate checkInDate = null;
        try {
            checkInDate = LocalDate.parse(checkInDateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error in format of check-in date; should be \"yyyy-MM-dd\".");
            enterContinue();
            return;
        }
        System.out.println("Enter a check-out date in format \"yyyy-MM-dd\":");
        String checkOutDateString = reader.nextLine();
        LocalDate checkOutDate = null;
        try {
            checkOutDate = LocalDate.parse(checkOutDateString, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Error in format of check-out date; should be \"yyyy-MM-dd\".");
            enterContinue();
            return;
        }
        Collection<IRoom> availableRooms = null;
        AlternateRooms alternateRooms = null;
        try {
            availableRooms = hotelResource.findARoom(checkInDate, checkOutDate);
        } catch (DateTimeException e) {
            System.out.println("Failure!\nYour submission generated the following error:");
            System.out.println(e.getMessage());
            enterContinue();
            return;
        }
        if (availableRooms.isEmpty()) {
            alternateRooms = hotelResource.findAlternateRooms(checkInDate, checkOutDate);
        }
        if (availableRooms.isEmpty()) {
            System.out.println("\nNo rooms available for your selected dates.");
            if (alternateRooms.rooms().isEmpty()) {
                System.out.println("Additionally, we have no nearby alternative dates available.");
            } else {
                System.out.println("However, we have availability on alternate dates.\n"
                        + "Check-in date: " + alternateRooms.checkInDate() + "\n"
                        + "Check-out date: " + alternateRooms.checkOutDate());
                makeReservation(alternateRooms.rooms(), alternateRooms.checkInDate(), alternateRooms.checkOutDate());
            }
        } else {
            System.out.println("\nThe following rooms are available.");
            makeReservation(availableRooms, checkInDate, checkOutDate);
        }
    }

    private static void createAccount() {
        System.out.println("Add a customer account.");
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter your first name:");
        String firstName = reader.nextLine();
        System.out.println("Enter your last name:");
        String lastName = reader.nextLine();
        System.out.println("Enter your email address:");
        String email = reader.nextLine().toLowerCase();
        Customer existingCustomer = null;
        try {
            existingCustomer = hotelResource.getCustomer(email);
        } catch (InvalidParameterException e) {
            // Do nothing; customer existence check.
        }
        if (existingCustomer != null) {
            String updateCustomer = "";
            while (! updateCustomer.equals("y")) {
                System.out.println("Customer already exists; update? (y/n)");
                updateCustomer = reader.nextLine().toLowerCase();
                switch (updateCustomer) {
                    case "y":
                        break;
                    case "n":
                        return;
                    default:
                        System.out.println("Bad entry.");
                        break;
                }
            }
        }
        try {
            hotelResource.createACustomer(email, firstName, lastName);
            if (existingCustomer != null) {
                System.out.println("\nCustomer " + email + " updated in system.");
            } else {
                System.out.println("\nCustomer " + email + " added to system.");
            }
        } catch (Exception e) {
            System.out.println("Failure!\nYour submission generated the following error:");
            System.out.println(e.getMessage());
        }
        enterContinue();
    }
    private static void enterContinue() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Press \"Enter\" key to continue ...");
        reader.nextLine();
    }

    private static void makeReservation(Collection<IRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {
        Scanner reader = new Scanner(System.in);
        int whichRoom = 1;
        for (IRoom room : rooms) {
            System.out.println(whichRoom + ". " + room.toString());
            whichRoom++;
        }
        String selection = "";
        int selectedRoom = 0;
        while (true) {
            System.out.println("Select room by number, or enter \"q\" to return to main menu.");
            selection = reader.nextLine().toLowerCase();
            if (selection.equals("q")) {
                break;
            }
            try {
                selectedRoom = Integer.parseInt(selection);
                selectedRoom--;
            } catch (NumberFormatException e) {
                System.out.println("Bad input. Please re-try.");
                continue;
            }
            if (selectedRoom < 0 || selectedRoom > (rooms.size() - 1)) {
                System.out.println("Bad input. Please re-try.");
                continue;
            }
            break;
        }
        if (selection.equals("q")) {
            return;
        }
        System.out.println("Enter your customer email address to continue with booking:");
        String email = reader.nextLine();
        String proceedWithReservation = "";
        while (true) {
            System.out.println("Proceed with reservation? (y/n)");
            proceedWithReservation = reader.nextLine().toLowerCase();
            switch (proceedWithReservation) {
                case "y" -> {
                    System.out.println();
                    IRoom roomToBook = (IRoom) rooms.toArray()[selectedRoom];
                    Reservation reservation = null;
                    try {
                        reservation = hotelResource.bookARoom(email, roomToBook, checkInDate, checkOutDate);
                    } catch (Exception e) {
                        System.out.println("Failure!\nYour submission generated the following error:");
                        System.out.println(e.getMessage());
                        enterContinue();
                        return;
                    }
                    System.out.println("You have successfully reserved the following room:\n"
                            + reservation.toString() + "\n");
                    enterContinue();
                    return;
                }
                case "n" -> {
                    System.out.println("Reservation not booked.");
                    enterContinue();
                    return;
                }
                default -> System.out.println("Bad entry.");
            }
        }
    }

    private static void seeAllReservations() {
        System.out.println("View all reservations for a given account.");
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the email address associated with the account:");
        String email = reader.nextLine();
        Collection<Reservation> reservations = null;
        try {
            reservations = hotelResource.getCustomerReservations(email);
        } catch (Exception e) {
            System.out.println("Failure!\nYour submission generated the following error:");
            System.out.println(e.getMessage());
            enterContinue();
            return;
        }
        if (reservations == null) {
            System.out.println("\nNo reservations found.");
        } else {
            System.out.println("\nThe following reservations were located:\n");
            for (Reservation reservation : reservations) {
                System.out.println(reservation.toString() + "\n");
            }
        }
        enterContinue();
    }

    public static void main (String[] args) {
        new MainMenu();
    }
}
