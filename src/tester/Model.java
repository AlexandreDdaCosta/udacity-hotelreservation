package tester;

import model.*;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*
Test coverage for data models
 */
public class Model {

    public static void main(String[] args) throws Exception {

        /*
        model.Customer
        */
        Customer errorCustomer = null;
        try {
            errorCustomer = new Customer("  Fred  ", "  Flintstone  ",
                    "Fred Flintstone @hannabarbera.com  ");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid email address");
        }
        Customer customer = new Customer("  Fred  ", "  Flintstone  ",
                                   " FredFlintstone@hannabarbera.com  ");
        System.out.println("\"Customer\" declaration successful with customer data as follows:");
        System.out.println(customer + "\n");
        try {
            errorCustomer = new Customer("$%^&", "Bar", "foobar@foobar.com");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid first name");
        }
        try {
            errorCustomer = new Customer("    ", "Bar", "foobar@foobar.com");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid first name");
        }
        try {
            errorCustomer = new Customer("Foo", "*$#@", "foobar@foobar.com");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid last name");
        }
        try {
            errorCustomer = new Customer("Foo", "  -  ", "foobar@foobar.com");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid last name");
        }
        try {
            errorCustomer = new Customer("Foo foo", "Bar-bar", "foobar");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Customer\" declaration successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (errorCustomer != null) {
            throw new Exception("Failed to catch bad \"Customer\" instantiation with invalid email address");
        }
        System.out.println();

        /*
        model.RoomType
        */
        String[] roomTypeIdentifiers = {"single", "double"};
        for (String identifier: roomTypeIdentifiers) {
            String roomType = String.valueOf(RoomType.getEnumeratedValueByIdentifier(identifier));
            System.out.println("Enumerated room type \"" + roomType
                               + "\" successfully retrieved for identifier \"" + identifier + "\"");
        }
        System.out.println();
        String[] badRoomTypeIdentifiers = {"Triple", "SINGLE", "doubl"};
        String badRoomType = null;
        for (String identifier: badRoomTypeIdentifiers) {
            try {
                badRoomType = String.valueOf(RoomType.getEnumeratedValueByIdentifier(identifier));
            } catch(IllegalArgumentException e) {
                System.out.println("Incorrect \"RoomType\" identifier successfully caught with the following error:");
                System.out.println(e.getMessage());
            }
            if (badRoomType != null) {
                throw new Exception("Failed to catch bad \"RoomType\" instantiation with invalid identifier");
            }
        }
        System.out.println();

        /*
        model.Room
        */
        Room room = new Room("101A", 100.00, "single");
        System.out.println("\"Room\" object correctly created, as follows:");
        System.out.println(room);
        room = new Room("150", 200.00, "double");
        System.out.println("\"Room\" object correctly created, as follows:");
        System.out.println(room + "\n");
        Room badRoom = null;
        try {
            badRoom = new Room(" 1 0 1", 100.00, "single");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Room\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badRoom != null) {
            throw new Exception("Failed to catch bad \"Room\" instantiation with invalid room number");
        }
        try {
            badRoom = new Room("&^*", 100.00, "single");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Room\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badRoom != null) {
            throw new Exception("Failed to catch bad \"Room\" instantiation with invalid room number");
        }
        try {
            badRoom = new Room("110", 300.00, "double");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Room\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badRoom != null) {
            throw new Exception("Failed to catch bad \"Room\" instantiation with invalid room price");
        }
        try {
            badRoom = new Room("140", 150.00, "triple");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"Room\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badRoom != null) {
            throw new Exception("Failed to catch bad \"Room\" instantiation with invalid room type identifier");
        }
        System.out.println();

        /*
        model.FreeRoom
        */
        FreeRoom freeRoom = new FreeRoom("B102", "single");
        System.out.println("\"FreeRoom\" object correctly created, as follows:");
        System.out.println(freeRoom + "\n");
        FreeRoom badFreeRoom = null;
        try {
            badFreeRoom = new FreeRoom("&*()", "double");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"FreeRoom\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badFreeRoom != null) {
            throw new Exception("Failed to catch bad \"FreeRoom\" instantiation with invalid room number");
        }
        try {
            badFreeRoom = new FreeRoom("111", "Quadruple");
        } catch(IllegalArgumentException e) {
            System.out.println("Incorrect \"FreeRoom\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badFreeRoom != null) {
            throw new Exception("Failed to catch bad \"FreeRoom\" instantiation with invalid room type identifier");
        }
        System.out.println();

        /*
        model.Reservation
        */
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String checkInDateString = LocalDate.now().plusDays(10).toString();
        String checkOutDateString = LocalDate.now().plusDays(17).toString();
        LocalDate checkInDate = LocalDate.parse(checkInDateString, formatter);
        LocalDate checkOutDate = LocalDate.parse(checkOutDateString, formatter);
        Reservation reservation = new Reservation(customer, room, checkInDate, checkOutDate);
        System.out.println("\"Reservation\" object successfully created, as follows:");
        System.out.println(reservation + "\n");
        Reservation badReservation = null;
        try {
            LocalDate yesterday = LocalDate.now().minusDays(1);
            badReservation = new Reservation(customer, room, yesterday, checkOutDate);
        } catch (DateTimeException e) {
            System.out.println("Incorrect \"Reservation\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badReservation != null) {
            throw new Exception("Failed to catch bad \"Reservation\" instantiation with invalid check in date");
        }
        try {
            LocalDate today = LocalDate.now();
            badReservation = new Reservation(customer, room, checkInDate, today);
        } catch (DateTimeException e) {
            System.out.println("Incorrect \"Reservation\" object creation successfully caught with the following error:");
            System.out.println(e.getMessage());
        }
        if (badReservation != null) {
            throw new Exception("Failed to catch bad \"Reservation\" instantiation with invalid check out date");
        }
    }
}
