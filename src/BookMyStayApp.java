import java.io.*;
import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * @version 12.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("System Recovery\n");

        PersistenceService persistence = new PersistenceService();

        // Try to load inventory
        Map<String, Integer> inventory = persistence.loadInventory();

        if (inventory == null) {
            System.out.println("No valid inventory data found. Starting fresh.\n");

            // Default inventory
            inventory = new HashMap<>();
            inventory.put("Single", 5);
            inventory.put("Double", 3);
            inventory.put("Suite", 2);
        }

        // Display inventory
        System.out.println("Current Inventory:");
        for (String key : inventory.keySet()) {
            System.out.println(key + ": " + inventory.get(key));
        }

        // Save inventory
        persistence.saveInventory(inventory);

        System.out.println("Inventory saved successfully.");
    }
}

/**
 * =========================================================
 * CLASS - PersistenceService
 * =========================================================
 */
class PersistenceService {

    private static final String FILE_NAME = "inventory.dat";

    // Save inventory to file
    public void saveInventory(Map<String, Integer> inventory) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(inventory);

        } catch (IOException e) {
            System.out.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from file
    public Map<String, Integer> loadInventory() {

        File file = new File(FILE_NAME);

        // If file doesn't exist
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            return (Map<String, Integer>) ois.readObject();

        } catch (Exception e) {
            System.out.println("Error loading inventory. Starting fresh.");
            return null;
        }
    }
}