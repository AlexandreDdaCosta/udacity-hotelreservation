package model;

import java.util.Objects;

public class Room implements IRoom {

    private final String roomNumber;
    private final Double roomPrice;
    private final RoomType roomType;
    private final String roomTypeIdentifier;

    public Room(String roomNumber, Double roomPrice, String roomTypeIdentifier) {
        super();
        roomNumber = roomNumber.toLowerCase();
        roomNumber = roomNumber.trim();
        Validator validator = new Validator();
        validator.validateRoomDetails(roomNumber, roomPrice);
        this.roomType = RoomType.getEnumeratedValueByIdentifier(roomTypeIdentifier);
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomTypeIdentifier = roomTypeIdentifier;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() { return roomPrice; }

    @Override
    public RoomType getRoomType() {
        return roomType;
    }

    @Override
    public boolean isFree() {
        return this.roomPrice != null && this.roomPrice.equals(0.00);
    }

    @Override
    public String toString() {
        return "Room Number: " + roomNumber + "\n"
                + "Room Price: $" + String.format("%.2f", roomPrice) + "\n"
                + "Room Type: " + roomTypeIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }
}
