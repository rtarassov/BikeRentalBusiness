import menu.Menu;
import menu.MenuItem;
import menu.MenuManager;
import model.Bike;
import model.BikeType;
import repositories.BikeRepository;
import util.CustomException;
import util.ExceptionMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class MainManager {
    static Scanner scanner = new Scanner(System.in);
    static BikeRepository bikeRepository = new BikeRepository();
    static MenuManager menuManager = new MenuManager();
    static Properties properties = new Properties();



    public static void main(String[] args) {
        runMain();
    }

    public static Properties loadProperties(Properties properties) throws CustomException {
        try {
            properties.load(new FileInputStream(new File(".").getCanonicalFile() + "/application.properties"));
        } catch (IOException e) {
            throw new CustomException(ExceptionMessage.ERROR_LOADING_PROPERTIES);
        }

        return properties;
    }

    public static void runMain() {
        try {
            loadProperties(properties);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return;
        }
        String input;
        createMenus(menuManager);
        menuManager.setCurrentMenu(menuManager.getMenuByHeading(properties.getProperty("mainMenu")));
        do {
            menuManager.getCurrentMenu().printMenu();
            input = scanner.nextLine();
            System.out.println();

            for (MenuItem item : menuManager.getCurrentMenu().getMenuItems()) {
                if (item.getOption().equalsIgnoreCase(input)) {
                    Menu nextMenu = menuManager.getMenuByHeading(item.getCodeToRun().runCode());
                    if (nextMenu == null) {
                        break;
                    }
                    if (nextMenu.equals(menuManager.getPreviousMenu())) {
                        menuManager.goToPreviousMenu();
                    } else {
                        menuManager.setCurrentMenu(nextMenu);
                    }
                }
            }

        } while (!input.equalsIgnoreCase("X"));
    }

    private static void createMenus(MenuManager manager) {
        Menu mainMenu = new Menu(properties.getProperty("mainMenu"));
        manager.addItemToMenu(mainMenu, new MenuItem("x", "Exit program", () -> null));
        manager.addItemToMenu(mainMenu, new MenuItem("1", "Manage items", () -> "### Bikes ###"));
        manager.addItemToMenu(mainMenu, new MenuItem("2", "Funds", () -> " ### Funds ###"));
        manager.addItemToMenu(mainMenu, new MenuItem("3", "Discounts", () -> "### Discounts ###"));
        manager.addMenu(mainMenu, null);

        Menu bikesMenu = new Menu(properties.getProperty("bikesMenu"));
        manager.addItemToMenu(bikesMenu, new MenuItem("1", "Add bikes", MainManager::addBike));
        manager.addItemToMenu(bikesMenu, new MenuItem("2", "Remove bikes", MainManager::removeBike));
        manager.addItemToMenu(bikesMenu, new MenuItem("3", "List all bikes", MainManager::listBikes));
        manager.addItemToMenu(bikesMenu, new MenuItem("4", "Update bike info", MainManager::updateBike));
        manager.addMenu(bikesMenu, mainMenu);

        Menu fundsMenu = new Menu(properties.getProperty("fundsMenu"));
        manager.addItemToMenu(fundsMenu, new MenuItem("1", "Display cash in register", MainManager::cashInRegister));
        manager.addItemToMenu(fundsMenu, new MenuItem("2", "Display free cash", MainManager::cash));
        manager.addItemToMenu(fundsMenu, new MenuItem("3", "Display guarantee cash", MainManager::guaranteeCash));
        manager.addMenu(fundsMenu, mainMenu);

        Menu discounts = new Menu(properties.getProperty("discountsMenu"));
        manager.addItemToMenu(discounts, new MenuItem("1", "Display active discount", MainManager::currentDiscount));
        manager.addItemToMenu(discounts, new MenuItem("2", "Add a new discount", MainManager::addDiscount));
        manager.addItemToMenu(discounts, new MenuItem("3", "Remove discount", MainManager::removeDiscount));
        manager.addMenu(discounts, mainMenu);

    }

    private static String addBike() {
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
        return properties.getProperty("bikesMenu");
    }

    private static String removeBike() {
        System.out.println("Whats the bike ID you want to remove?: ");
        int id = scanner.nextInt();

        try {
            bikeRepository.removeBike(id);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return properties.getProperty("bikesMenu");
        }
        System.out.println("Successfully removed");
        System.out.println();
        return properties.getProperty("bikesMenu");
    }

    private static String updateBike() {
        System.out.println("Whats the bike ID you want to update?: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Bike bikeToUpdate = null;
        try {
            bikeToUpdate = bikeRepository.findBikeById(id);
        } catch (CustomException e) {
            System.out.println(e.getMessage());
            return properties.getProperty("bikesMenu");
        }

        System.out.println("What you want to update?: ");
        System.out.println("/ TYPE / MANUFACTURER / MODEL / COLOR /");
        String updateOption = scanner.nextLine();

        switch (updateOption.toUpperCase()) {
            case "TYPE" -> {
                System.out.println("Enter new bike type");
                System.out.println("/ DIRT BIKE / KIDS BIKE / ROAD BIKE /");
                String newType = scanner.nextLine();
                bikeToUpdate.setBikeType(BikeType.fromString(newType));
            }
            case "MANUFACTURER" -> {
                System.out.print("Enter new manufacturer: ");
                String newManufacturer = scanner.nextLine();
                bikeToUpdate.setManufacturer(newManufacturer);
            }
            case "MODEL" -> {
                System.out.print("Enter new model: ");
                String newModel = scanner.nextLine();
                bikeToUpdate.setModel(newModel);
            }
            case "COLOR" -> {
                System.out.print("Enter new color: ");
                String newColor = scanner.nextLine();
                bikeToUpdate.setColor(newColor);
            }
        }
        try {
            bikeRepository.updateBike(id, bikeToUpdate);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return properties.getProperty("bikesMenu");
        }
        System.out.println("Bike updated successfully");
        return properties.getProperty("bikesMenu");
    }

    private static String listBikes() {
        bikeRepository.listBikes();
        return properties.getProperty("bikesMenu");
    }

    private static String cashInRegister() {
        return null;

    }

    private static String cash() {
        return null;

    }

    private static String guaranteeCash() {
        return null;

    }

    private static String currentDiscount() {
        return null;

    }

    private static String addDiscount() {
        return null;

    }

    private static String removeDiscount() {
        return null;

    }
}
