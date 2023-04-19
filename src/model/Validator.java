package model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Pattern;

public class Validator {

    public Validator() {}

    public void validateCustomerDetails(String firstName, String lastName, String email) {
        String nameRegex = "^[A-Za-z0-9\\- ]+$";
        String alphanumericNameRegex = "^.*[A-Za-z0-9]+.*$";
        String emailRegex = "^(.+)@(.+).com$";
        String whiteSpaceRegex = ".*\\s.*";
        Pattern namePattern = Pattern.compile(nameRegex);
        Pattern alphanumericNamePattern = Pattern.compile(alphanumericNameRegex);
        Pattern emailPattern = Pattern.compile(emailRegex);
        Pattern whiteSpacePattern = Pattern.compile(whiteSpaceRegex);
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("First name required");
        }
        if (! namePattern.matcher(firstName).matches()) {
            throw new IllegalArgumentException("Invalid characters in first name: " + firstName);
        }
        if (! alphanumericNamePattern.matcher(firstName).matches()) {
            throw new IllegalArgumentException("First name has no alphanumeric characters: "
                    + "\"" + firstName + "\"");
        }
        if (lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException("Last name required");
        }
        if (! namePattern.matcher(lastName).matches()) {
            throw new IllegalArgumentException("Invalid characters in last name: " + lastName);
        }
        if (! alphanumericNamePattern.matcher(lastName).matches()) {
            throw new IllegalArgumentException("Last name has no alphanumeric characters: "
                    + "\"" + lastName + "\"");
        }
        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("Email address required");
        }
        if (! emailPattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }
        System.out.println(email);
        if (whiteSpacePattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email address, spacing not allowed: " + email);
        }
    }

    public void validateReservationDates(LocalDate checkInDate, LocalDate checkOutDate) {
        LocalDate today = LocalDate.now();
        if (checkInDate == null) {
            throw new IllegalArgumentException("Check-in date required");
        }
        if (checkInDate.isBefore(today)) {
            throw new DateTimeException("Check-in date cannot be earlier than today: "
                    + checkInDate + " < " + today);
        }
        if (checkOutDate == null) {
            throw new IllegalArgumentException("Check-out date required");
        }
        if (!checkOutDate.isAfter(checkInDate)) {
            throw new DateTimeException("Check-out date must be later than check-in date: "
                    + checkOutDate + " < " + checkInDate);
        }
    }

    public void validateRoomDetails(String roomNumber, Double roomPrice) {
        String roomNumberRegex = "^[A-Za-z0-9]+$";
        Pattern pattern = Pattern.compile(roomNumberRegex);
        if (roomNumber == null || roomNumber.equals("")) {
            throw new IllegalArgumentException("Room number required.");
        }
        if (! pattern.matcher(roomNumber).matches()) {
            throw new IllegalArgumentException("Invalid room number: " + roomNumber);
        }
        if (roomPrice == null) {
            throw new IllegalArgumentException("Room price required.");
        }
        Double minimumRoomPrice = 0.00;
        Double maximumRoomPrice = 200.00;
        if (roomPrice < minimumRoomPrice || roomPrice > maximumRoomPrice) {
            throw new IllegalArgumentException("Invalid room price "
                    + "\"$" + String.format("%.2f", roomPrice) + "\""
                    + "; prices range from $"
                    + String.format("%.2f", minimumRoomPrice) + " to $"
                    + String.format("%.2f", maximumRoomPrice));
        }
    }
}
