import com.bofa.entities.Clothes;
import com.bofa.entities.ClothesType;
import com.bofa.entities.Role;
import com.bofa.entities.User;
import com.bofa.exceptions.ClothesNotFoundException;
import com.bofa.services.ClotheService;
import com.bofa.services.UserService;
import com.bofa.utils.MockDB;

import java.util.List;
import java.util.Scanner;

public class SeasonsApp {
    // Initialize variables and services
    public static Scanner scanner = new Scanner(System.in);
    public static UserService userService = new UserService();
    public static ClotheService clotheService = new ClotheService();
    public static User loggedinUser = null;

    public static void main(String[] args) throws ClothesNotFoundException {
        int userChoice = -1;

        while (userChoice != 0) {
            // Check if a user is logged in and display appropriate menu
            if (loggedinUser != null) {
                displayUserMenu(loggedinUser);
            } else {
                welcomeMenu(); // Display the main menu

                userChoice = collectUserInput(); // Collect and parse user input

                parseWelcomeMenuInput(userChoice); // Process the main menu choice
            }
        }
    }


    // Collect user input and convert it to an integer
    private static int collectUserInput() {

        int choice = -1;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
        return choice;
    }

    // Display the welcome menu options
    private static void welcomeMenu() {

        System.out.println("Welcome to SeasonsApp!");
        System.out.println("1. Create an Account");
        System.out.println("2. Log In");
        System.out.println("0. Quit");
    }

    // Process the main menu choice
    private static void parseWelcomeMenuInput(int userChoice) {
        switch (userChoice) {
            case 1:
                createNewAccount();
                break;
            case 2:
                loginMenu();
                break;
            case 0:
                goodbye();
                break;
            default:
                invalidInput();
        }
    }

    // Create a new user account and add it to the registeredUser list
    private static void createNewAccount() {

        System.out.println("Enter a username:");
        String username = scanner.nextLine();

        System.out.println("Enter a password:");
        String password = scanner.nextLine();

        User newUser = new User(username, password, Role.CUSTOMER);

        MockDB.registeredUser.add(newUser);

        System.out.println("Account created successfully! Welcome " + username  + "!");
    }

    //Handle Login
    private static void loginMenu() {
        System.out.println("Enter your username:");
        String username = scanner.nextLine();
        System.out.println("Enter your password:");
        String password = scanner.nextLine();

        // Authenticate against the registeredUser list
        User authenticatedUser = null;
        for (User user : MockDB.registeredUser) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                authenticatedUser = user;
                break;
            }
        }

        if (authenticatedUser != null) {
            loggedinUser = authenticatedUser;
            System.out.println("Login successful!");
        } else {
            System.out.println("Login failed. Invalid username or password.");
        }
    }
    //This directs you to the needed menu depending on if you're a customer or owner
    private static void displayUserMenu(User user) throws ClothesNotFoundException {
        if (user.getRole() == Role.OWNER) {
            displayOwnerMenu(user);
        } else {
            displayCustomerMenu(user);
        }
    }

    // Owner Menu
    private static void displayOwnerMenu(User owner) throws ClothesNotFoundException {
        while (loggedinUser != null) {
            System.out.println("Owner Menu:");
            System.out.println("1. Browse Clothing");
            System.out.println("2. Add Clothing to Inventory");
            System.out.println("3. Log Out");

            int userChoice = collectUserInput();

            switch (userChoice) {
                case 1:
                    browseClothing();
                    break;
                case 2:
                    addClothingToInventory(owner);
                    break;
                case 3:
                    loggedinUser = null;
                    System.out.println("Logged out successfully.");
                    return; // Exit the user menu loop
                default:
                    invalidInput();
            }
        }
    }

    //Customer Menu
    private static void displayCustomerMenu(User customer) throws ClothesNotFoundException {
        while (loggedinUser != null) {
            System.out.println("User Menu:");
            System.out.println("1. Browse Clothing");
            System.out.println("2. Place an Order");
            System.out.println("3. Log Out");

            int userChoice = collectUserInput();

            switch (userChoice) {
                case 1:
                    browseClothing();
                    break;
                case 2:
                    placeOrder();
                    break;
                case 3:
                    loggedinUser = null;
                    System.out.println("Logged out successfully.");
                    return; // Exit the user menu loop
                default:
                    invalidInput();
            }
        }
    }

    //Placing order
    private static void placeOrder() throws ClothesNotFoundException {
        // Allow a user to place an order for clothing items
        List<Clothes> availableClothes = clotheService.getAllClothes();

        if (availableClothes.isEmpty()) {
            System.out.println("No clothing items available to place an order.");
            return;
        }

        System.out.println("Available Clothing Items:");
        for (Clothes clothing : availableClothes) {
            System.out.println(clothing.toString());
        }

        System.out.println("Enter the ID of the clothing item you want to order:");
        int selectedClothingId = collectUserInput();

        Clothes selectedClothing = clotheService.getClothesById(selectedClothingId);
        if (selectedClothing == null) {
            System.out.println("Clothing item not found.");
            return;
        }

        System.out.println("Enter the quantity you want to order:");
        int orderQuantity = collectUserInput();

        if (orderQuantity <= 0) {
            System.out.println("Invalid quantity. Please enter a positive number.");
            return;
        }

        double totalOrderPrice = selectedClothing.getPrice() * orderQuantity;
        System.out.println("Order Receipt:");
        System.out.println("Item: " + selectedClothing.getTitle());
        System.out.println("Price per Item: $" + selectedClothing.getPrice());
        System.out.println("Quantity: " + orderQuantity);
        System.out.println("Total Price: $" + totalOrderPrice);
        System.out.println(" ");
    }

    //Browse Clothing
    private static void browseClothing() {
        // Display a list of available clothing items
        List<Clothes> availableClothes = clotheService.getAllClothes();

        if (availableClothes.isEmpty()) {
            System.out.println("No clothing items available.");
        } else {
            System.out.println("Available Clothing Items:");
            for (Clothes clothing : availableClothes) {
                System.out.println(clothing.toString());
            }
        }
    }

    //Add clothing to array
    private static void addClothingToInventory(User owner) {
        if (owner.getRole() == Role.OWNER) {
            // Allow an owner to add new clothing items to the inventory
            System.out.println("Enter clothing details:");
            System.out.println("Title: ");
            String title = scanner.nextLine();
            System.out.println("Color: ");
            String color = scanner.nextLine();
            System.out.println("Type (SHIRTS, PANTS, SHOES, HAT, etc.): ");
            String typeInput = scanner.nextLine();

            try {
                ClothesType type = ClothesType.valueOf(typeInput);
                System.out.println("Whats the price for the item?");
                double price = Double.parseDouble(scanner.nextLine());

                Clothes newClothes = new Clothes(title, color, type, price);


                // Add the new item to the inventory
                MockDB.clothesList.add(newClothes);

                System.out.println("Clothing added to the inventory.");

                displayInventory();
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid clothing type. Clothing not added to the inventory.");
            }
        } else {
            System.out.println("Only owners can add clothing to the inventory.");
        }
    }

    //View inventory
    private static void displayInventory() {
        // Display the current clothing inventory
        System.out.println("Current Clothing Inventory:");
        for (Clothes clothing : MockDB.clothesList) {
            System.out.println(clothing.toString());
        }
    }

    private static void goodbye() {
        // Log out and say goodbye
        loggedinUser = null;
        System.out.println("Thanks for coming.");
    }

    private static void invalidInput() {
        // Handle invalid user input
        System.out.println("That wasn't an option");
    }
}
