package model;

public class FreeRoom extends Room {

    public FreeRoom(String roomNumber, String roomTypeIdentifier) {
        super(roomNumber, 0.00, roomTypeIdentifier);
    }

    @Override
    public String toString() {
        return "Free room!\n" + super.toString();
    }
}
