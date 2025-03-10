import java.io.*;  // For file handling
import java.util.*; // For ArrayList and Scanner

// Product class representing items for sale
class Product {
    int id;
    String name;
    double price;

    // Constructor to initialize product details
    public Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    // Overriding toString method to display product details
    public String toString() {
        return id + ". " + name + " - $" + price;
    }
}

// User class to store user details (username & password)
class User {
    String username;
    String password;

    // Constructor to initialize user details
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

// Main class for the eCommerce application
public class EcommerceApp {
    static List<Product> products = new ArrayList<>();  // List of available products
    static List<User> users = new ArrayList<>();        // List of registered users
    static Scanner scanner = new Scanner(System.in);    // Scanner for user input
    static User currentUser = null;                     // Tracks logged-in user

    public static void main(String[] args) {
        loadUsers();   // Load registered users from file
        loadProducts(); // Initialize product list
        showMainMenu(); // Display main menu
    }

    // Displays the main menu (Register, Login, Exit)
    static void showMainMenu() {
        while (true) {
            System.out.println("\n===== E-Commerce System =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> registerUser(); // Call register function
                case 2 -> loginUser(); // Call login function
                case 3 -> exitApp(); // Exit application
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Handles user registration
    static void registerUser() {
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Create a new user and add to the list
        users.add(new User(username, password));
        saveUsers(); // Save users to file
        System.out.println("Registration successful! Please login.");
    }

    // Handles user login
    static void loginUser() {
        System.out.print("\nEnter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Check if user exists
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                currentUser = user;
                System.out.println("Login successful! Welcome, " + username + ".");
                showUserMenu(); // Show user menu after successful login
                return;
            }
        }
        System.out.println("Invalid credentials! Try again.");
    }

    // Displays user menu after login
    static void showUserMenu() {
        while (true) {
            System.out.println("\n===== User Menu =====");
            System.out.println("1. View Products");
            System.out.println("2. Buy Product");
            System.out.println("3. Logout");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> viewProducts(); // Show product list
                case 2 -> buyProduct(); // Handle product purchase
                case 3 -> {
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // Displays the list of available products
    static void viewProducts() {
        System.out.println("\n===== Available Products =====");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Handles buying a product
    static void buyProduct() {
        System.out.print("\nEnter product ID to buy: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        // Check if the entered product ID exists
        for (Product product : products) {
            if (product.id == id) {
                System.out.println("You bought: " + product.name + " for $" + product.price);
                return;
            }
        }
        System.out.println("Invalid product ID! Try again.");
    }

    // Loads users from file
    static void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("users.dat"))) {
            Object obj = ois.readObject();
            if (obj instanceof List<?>) {
                users = (List<User>) obj;
            }
        } catch (Exception e) {
            users = new ArrayList<>(); // If file not found, start fresh
        }
    }

    // Saves users to file
    static void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("users.dat"))) {
            oos.writeObject(users);
        } catch (IOException e) {
            System.out.println("Error saving users.");
        }
    }

    // Initializes the list of products (Hardcoded)
    static void loadProducts() {
        products.add(new Product(1, "Laptop", 800.00));
        products.add(new Product(2, "Smartphone", 500.00));
        products.add(new Product(3, "Headphones", 50.00));
    }

    // Exits the application
    static void exitApp() {
        System.out.println("Thank you for using our eCommerce system. Goodbye!");
        System.exit(0);
    }
}
