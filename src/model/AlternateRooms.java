package model;

import java.time.LocalDate;
import java.util.Collection;

/*
Used to return a set of available rooms based on a flexible date search.
*/

public record AlternateRooms(Collection<IRoom> rooms, LocalDate checkInDate, LocalDate checkOutDate) {

    @Override
    public String toString() {
        StringBuilder alternateRooms = new StringBuilder();
        if (rooms.size() == 0) {
            alternateRooms = new StringBuilder("No alternate rooms available.");
        } else {
            for (IRoom alternateRoom : rooms) {
                alternateRooms.append("\n").append(alternateRoom.toString());
            }
        }
        return "Check In Date: " + checkInDate + "\n"
                + "Check Out Date: " + checkOutDate + "\n"
                + "Alternate Rooms: " + alternateRooms;
    }
}
