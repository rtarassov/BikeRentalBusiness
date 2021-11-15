package repositories;

import model.Bike;
import model.BikeType;
import util.CustomException;
import util.ExceptionMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class BikeRepository {
    List<Bike> bikes = new ArrayList<>();

    public void addBike(Bike bike) throws CustomException {
            if (bikes.stream().anyMatch(bikeId -> bikeId.getId() == bike.getId())) {
                throw new CustomException(ExceptionMessage.BIKE_ALREADY_EXISTS);
            }
        bikes.add(bike);
    }

    public void removeBike(int id) throws CustomException {
        if (bikes.stream().anyMatch(bikeId -> bikeId.getId() == id)) {
            throw new CustomException(ExceptionMessage.NO_SUCH_BIKE_WITH_ID);
        }
        bikes = bikes.stream()
                .filter(bike -> bike.getId() != id)
                .collect(Collectors.toList());
    }

    public void updateBike(int id, Bike bike) throws CustomException {
        removeBike(id);
        addBike(bike);
    }

    public Bike findBikeById(int id) throws CustomException {
        return bikes
                .stream()
                .filter(bike -> bike.getId() == id)
                .findFirst()
                .orElseThrow(() -> new CustomException(ExceptionMessage.NO_SUCH_BIKE_WITH_ID));
    }

    public void listBikes() {
        for (Bike bike : bikes) {
            System.out.println(bike);
        }
    }

}
