import java.util.Map;
import java.util.HashMap;

public class BookMyStayApp {
    public static void main(String[] args) {

        System.out.println("Centralized Room Inventory\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Display initial inventory
        displayInventory(inventory);

        // Update example
        System.out.println("\nUpdating SingleRoom availability...\n");
        inventory.updateAvailability("SingleRoom", 4);

        // Display updated inventory
        displayInventory(inventory);
    }

    // Helper method to display inventory
    public static void displayInventory(RoomInventory inventory) {
        Map<String, Integer> availability = inventory.getRoomAvailability();

        for (String roomType : availability.keySet()) {
            System.out.println(roomType + " Available: " + availability.get(roomType));
        }
    }
}

/**
 * =========================================================
 * CLASS - RoomInventory
 * =========================================================
 */
class RoomInventory {

    private Map<String, Integer> roomAvailability;

    public RoomInventory() {
        roomAvailability = new HashMap<>();
        initializeInventory();
    }

    private void initializeInventory() {
        roomAvailability.put("SingleRoom", 5);
        roomAvailability.put("DoubleRoom", 3);
        roomAvailability.put("SuiteRoom", 2);
    }

    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}