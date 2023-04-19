package ui;

import java.util.Collection;
import java.util.Scanner;

import api.AdminResource;
import model.Customer;
import model.IRoom;
import model.Room;

class AdminMenu {

    private static final AdminResource adminResource = AdminResource.getInstance();

    public AdminMenu() {
        Scanner reader = new Scanner(System.in);
        String selection = "";
        while (! selection.equals("5")) {
            displayMenu();
            selection = reader.nextLine();
            switch (selection) {
                case "1" -> {
                    System.out.println();
                    seeAllCustomers();
                }
                case "2" -> {
                    System.out.println();
                    seeAllRooms();
                }
                case "3" -> {
                    System.out.println();
                    seeAllReservations();
                }
                case "4" -> {
                    System.out.println();
                    addARoom();
                }
                case "5" -> MainMenu.displayMenu();
                default -> System.out.println("Bad entry.");
            }
        }
    }

    public static void displayMenu() {
        System.out.println("""

                Administrative Menu
                --------------------------------------
                1. See all customers
                2. See all rooms
                3. See all reservations
                4. Add a room
                5. Back to main menu
                --------------------------------------

                Please choose a menu option by number:""");
    }

    private static void addARoom() {
        System.out.println("Adding a room.");
        Scanner reader = new Scanner(System.in);
        System.out.println("Enter the room number (numbers and letters):");
        String roomNumber = reader.nextLine();
        System.out.println("Add the price of the room (a plain or decimal number):");
        String roomPriceString = reader.nextLine();
        System.out.println("What is the type if the room? (\"single\" or \"double\"):");
        String roomType = reader.nextLine().toLowerCase();
        try {
            Double roomPrice = Double.parseDouble(roomPriceString);
            Room room = new Room(roomNumber, roomPrice, roomType);
            adminResource.addARoom(room);
            System.out.println("Room " + roomNumber + " successfully added.");
        } catch (NumberFormatException e) {
            System.out.println("Failure!\nRoom price must be a plain or decimal number: "
                                + "\"" + roomPriceString + "\"");
            enterContinue();
        } catch (Exception e) {
            System.out.println("Failure!\nYour submission generated the following error:");
            System.out.println(e.getMessage());
            enterContinue();
        }
        String addAnotherRoom = "";
        while (true) {
            System.out.println("Would you like to add another room? (y/n)");
            addAnotherRoom = reader.nextLine().toLowerCase();
            switch (addAnotherRoom) {
                case "y" -> {
                    System.out.println();
                    addARoom();
                    return;
                }
                case "n" -> {
                    return;
                }
                default -> System.out.println("Bad entry.");
            }
        }
    }

    private static void enterContinue() {
        Scanner reader = new Scanner(System.in);
        System.out.println("Press \"Enter\" key to continue ...");
        reader.nextLine();
    }

    private static void seeAllCustomers() {
        Collection<Customer> allCustomers = adminResource.getAllCustomers();
        if (allCustomers.isEmpty()) {
            System.out.println("No customers found.");
        } else {
            System.out.println("Customer listing:");
            for (Customer customer : allCustomers) {
                System.out.println("\n" + customer.toString());
            }
            System.out.println();
        }
        enterContinue();
    }

    private static void seeAllReservations() {
        System.out.println("Existing reservations:\n");
        adminResource.displayAllReservations();
        enterContinue();
    }

    private static void seeAllRooms() {
        Collection<IRoom> allRooms = adminResource.getAllRooms();
        if (allRooms.isEmpty()) {
            System.out.println("No rooms found.");
        } else {
            System.out.println("The following rooms were located:\n");
            for (IRoom room : allRooms) {
                System.out.println(room.toString() + "\n");
            }
        }
        enterContinue();
    }

    public static void main (String[] args) {
        new AdminMenu();
    }
}

