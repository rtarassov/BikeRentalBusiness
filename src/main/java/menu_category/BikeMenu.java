package menu_category;

import model.Bike;
import model.BikeType;
import repositories.BikeRepository;

import java.util.Properties;
import java.util.Scanner;

public class BikeMenu {

    static Scanner scanner = new Scanner(System.in);

    public static String addBike(Properties properties, BikeRepository bikeRepository) {
        // add new bike
        System.out.println(properties.getProperty("bikesMenu"));
        for (var bikeType : BikeType.values()) {
            System.out.print(bikeType.getValue() + " / ");
        }
        System.out.println();
        String bikeType;
        do {
            System.out.print("What type of bike are you adding?: ");
            bikeType = scanner.nextLine();

        } while (BikeType.fromString(bikeType) == null);

        System.out.print("Who is the manufacturer?: ");
        String manufacturer = scanner.nextLine();
        System.out.print("Whats the bike model?: ");
        String model = scanner.nextLine();
        System.out.print("Whats the color?: ");
        String color = scanner.nextLine();
        System.out.print("Whats the bike ID?: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Bike bike = new Bike(BikeType.fromString(bikeType), manufacturer, model, color, id);
        try {
            bikeRepository.addBike(bike);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return properties.getProperty("bikesMenu");
        }

        System.out.println("Successfully added");
        System.out.println();
        // Go to main menu
        // transactionRepo.createTransaction(myRegister, -500, "New Bike",;
        // transactionRepo.createTransaction(myRegister, 20, "Dirt Bike Rental - (Customer ID: 5, bike ID: 24)");
        return properties.getProperty("bikesMenu");
    }


}
