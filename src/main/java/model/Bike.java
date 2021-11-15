package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bike {
    private BikeType bikeType;
    private String manufacturer;
    private String model;
    private String color;
    private int id;

    @Override
    public String toString() {
        // List of Bikes
        //return String.format("%d) %s %s (%s) - %s", id, manufacturer, model, color, bikeType.getValue());
        return id + ") " + manufacturer + " " + model + " (" + color + ")" + " - " + bikeType.getValue();
    }
}
