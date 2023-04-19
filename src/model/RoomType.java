package model;

public enum RoomType {
    SINGLE("single"),
    DOUBLE("double");

    private final String identifier;

    RoomType(String identifier) {
        this.identifier = identifier;
    }

    public static RoomType getEnumeratedValueByIdentifier(String identifier) {
        for (RoomType roomType : values()) {
            if (roomType.identifier.equals(identifier)) {
                return roomType;
            }
        }
        throw new IllegalArgumentException("No matching room type found for identifier \"" + identifier + "\"");
    }
}
