import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 11: Concurrent Booking Simulation
 *
 * @version 11.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Concurrent Booking Simulation\n");

        // Shared inventory
        RoomInventory inventory = new RoomInventory();

        // Shared allocation service
        RoomAllocationService service = new RoomAllocationService(inventory);

        // Simulate multiple users (threads)
        Thread t1 = new Thread(() -> service.bookRoom("Abhi", "Single"));
        Thread t2 = new Thread(() -> service.bookRoom("Vanmathi", "Double"));
        Thread t3 = new Thread(() -> service.bookRoom("Kural", "Suite"));
        Thread t4 = new Thread(() -> service.bookRoom("Subha", "Single"));

        // Start threads (concurrent execution)
        t1.start();
        t2.start();
        t3.start();
        t4.start();

        // Wait for all threads to finish
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Display remaining inventory
        System.out.println("\nRemaining Inventory:");
        inventory.displayInventory();
    }
}

/**
 * =========================================================
 * CLASS - RoomInventory
 * =========================================================
 */
class RoomInventory {

    private Map<String, Integer> availability = new HashMap<>();

    public RoomInventory() {
        availability.put("Single", 5);
        availability.put("Double", 3);
        availability.put("Suite", 2);
    }

    public int getAvailable(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduce(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void displayInventory() {
        for (String key : availability.keySet()) {
            System.out.println(key + ": " + availability.get(key));
        }
    }
}

/**
 * =========================================================
 * CLASS - RoomAllocationService (Thread Safe)
 * =========================================================
 */
class RoomAllocationService {

    private RoomInventory inventory;

    // Track room IDs
    private Map<String, Integer> counters = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    // Critical section (synchronized)
    public synchronized void bookRoom(String guest, String type) {

        // Check availability
        if (inventory.getAvailable(type) <= 0) {
            System.out.println("No rooms available for " + guest);
            return;
        }

        // Generate room ID
        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);

        String roomId = type + "-" + count;

        // Reduce inventory
        inventory.reduce(type);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + guest + ", Room ID: " + roomId);
    }
}