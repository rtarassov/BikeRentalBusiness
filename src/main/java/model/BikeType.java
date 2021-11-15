package model;

public enum BikeType {
    DIRT_BIKE("Dirt Bike"),
    KIDS_BIKE("Kids Bike"),
    ROAD_BIKE("Road Bike");


    private String value;

    public String getValue() {
        return this.value;
    }

    BikeType(String value) {
        this.value = value;
    }

    public static BikeType fromString(String value) {
        for (BikeType type : BikeType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return null;
    }
}
