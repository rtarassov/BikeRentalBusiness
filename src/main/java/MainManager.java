
import menu.Menu;
import menu.MenuItem;
import menu.MenuManager;
import menu_category.BikeMenu;
import model.Bike;
import model.BikeType;
import model.Client;
import repositories.BikeRepository;
import repositories.ClientRepository;
import util.CustomException;
import util.ExceptionMessage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainManager {
    static Scanner scanner = new Scanner(System.in);
    static BikeRepository bikeRepository = new BikeRepository();
    static ClientRepository clientRepository = new ClientRepository();
    static MenuManager menuManager = new MenuManager();
    static Properties properties = new Properties();



    public static void main(String[] args) {
        runMain();
    }

    public static void loadProperties(Properties properties) throws CustomException {
        try {
            properties.load(new FileInputStream(new File(".").getCanonicalFile() + "/application.properties"));
        } catch (IOException e) {
            throw new CustomException(ExceptionMessage.ERROR_LOADING_PROPERTIES);
        }
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
        while (true) {
            menuManager.getCurrentMenu().printMenu();
            input = scanner.nextLine();
            System.out.println();
            Menu nextMenu = null;
            boolean choseOption = false;

            for (MenuItem item : menuManager.getCurrentMenu().getMenuItems()) {
                if (item.getOption().equalsIgnoreCase(input)) {
                    choseOption = true;
                    nextMenu = menuManager.getMenuByHeading(item.getCodeToRun().runCode());
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
            if (!choseOption) {
                continue;
            }
            if (nextMenu == null) {
                break;
            }
        }
    }

    private static void createMenus(MenuManager manager) {
        Menu mainMenu = new Menu(properties.getProperty("mainMenu"));
        manager.addItemToMenu(mainMenu, new MenuItem("x", "Exit program", () -> null));
        manager.addItemToMenu(mainMenu, new MenuItem("1", "Manage bikes", () -> properties.getProperty("bikesMenu")));
        manager.addItemToMenu(mainMenu, new MenuItem("2", "Manage clients", () -> properties.getProperty("clientsMenu")));
        manager.addItemToMenu(mainMenu, new MenuItem("3", "Funds", () -> properties.getProperty("fundsMenu")));
        manager.addItemToMenu(mainMenu, new MenuItem("4", "Discounts", () -> properties.getProperty("discountsMenu")));
        manager.addMenu(mainMenu, null);

        Menu bikesMenu = new Menu(properties.getProperty("bikesMenu"));
        manager.addItemToMenu(bikesMenu, new MenuItem("1", "Add bikes", () -> BikeMenu.addBike(properties, bikeRepository)));
        manager.addItemToMenu(bikesMenu, new MenuItem("2", "Remove bikes", MainManager::removeBike));
        manager.addItemToMenu(bikesMenu, new MenuItem("3", "List all bikes", MainManager::listBikes));
        manager.addItemToMenu(bikesMenu, new MenuItem("4", "Update bike info", MainManager::updateBike));
        manager.addMenu(bikesMenu, mainMenu);

        Menu clientsMenu = new Menu(properties.getProperty("clientsMenu"));
        manager.addItemToMenu(clientsMenu, new MenuItem("1", "Add clients", MainManager::addClient));
        manager.addItemToMenu(clientsMenu, new MenuItem("2", "Remove clients", MainManager::removeClient));
        manager.addItemToMenu(clientsMenu, new MenuItem("3", "List all clients", MainManager::listClients));
        manager.addMenu(clientsMenu, mainMenu);

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

    public static String addClient() {

        System.out.print("Whats the client name?: ");
        String name = scanner.nextLine();
        System.out.println("Date of birth format: [ DD-MM-YYYY ]");
        System.out.print("Whats the client date of birth?: ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = scanner.nextLine();
        Date dateOfBirth = null;
        try {
            dateOfBirth = simpleDateFormat.parse(date);
        } catch (ParseException e) {
            // TODO: Better error for client
            e.printStackTrace();
        }
        System.out.print("Whats the client email?: ");
        String email = scanner.nextLine();
        System.out.print("Whats the client ID?: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Client client;
        do {

            try {
                clientRepository.addClient(new Client(name, dateOfBirth, email, id));
                client = null;
            } catch (CustomException e) {
                System.out.println(e.getMessage());
                System.out.print("Whats the client ID?: ");
                id = scanner.nextInt();
                scanner.nextLine();
                client = new Client(name, dateOfBirth, email, id);
            }

        } while (client != null);

        System.out.println("Successfully added");
        System.out.println();

        return properties.getProperty("clientsMenu");
    }

    public static String removeBike() {
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

    public static String removeClient() {
        System.out.println("Whats the client ID you want to remove?: ");
        int id = scanner.nextInt();

        try {
            clientRepository.removeClient(id);
        } catch (CustomException e) {
            e.printStackTrace();
            return properties.getProperty("clientsMenu");
        }
        System.out.println("Successfully removed");
        System.out.println();
        return properties.getProperty("clientsMenu");
    }

    public static String updateBike() {
        System.out.println("Whats the bike ID you want to update?: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        Bike bikeToUpdate;
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
            case "TYPE": {
                System.out.println("Enter new bike type");
                System.out.println("/ DIRT BIKE / KIDS BIKE / ROAD BIKE /");
                String newType = scanner.nextLine();
                bikeToUpdate.setBikeType(BikeType.fromString(newType));
            }
            case "MANUFACTURER": {
                System.out.print("Enter new manufacturer: ");
                String newManufacturer = scanner.nextLine();
                bikeToUpdate.setManufacturer(newManufacturer);
            }
            case "MODEL": {
                System.out.print("Enter new model: ");
                String newModel = scanner.nextLine();
                bikeToUpdate.setModel(newModel);
            }
            case "COLOR": {
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

    public static String listBikes() {
        bikeRepository.listBikes();
        return properties.getProperty("bikesMenu");
    }

    public static String listClients() {
        clientRepository.listClients();
        return properties.getProperty("clientsMenu");
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
