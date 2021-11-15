package services;

import model.Bike;
import model.Client;
import repositories.BikeRepository;

import java.time.LocalDateTime;

public class BikeRentalService {

    private BikeRepository repository = new BikeRepository();

    public BikeRentalService() {
    }

    public void rentBike(Bike bike, LocalDateTime returnDate, Client client) {

    }

    public Double getRentalPrice(Bike bike, LocalDateTime returnDate, Client client) {
        return 0.0;
    }

}
