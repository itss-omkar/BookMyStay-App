import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * @version 10.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Booking Cancellation\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Simulate allocated booking
        String reservationId = "Single-1";
        String roomType = "Single";

        // Reduce inventory as if booked earlier
        inventory.reduceAvailability(roomType);

        // Initialize cancellation service
        CancellationService cancelService = new CancellationService(inventory);

        // Perform cancellation
        cancelService.cancelBooking(reservationId, roomType);
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

    public void reduceAvailability(String type) {
        availability.put(type, availability.get(type) - 1);
    }

    public void increaseAvailability(String type) {
        availability.put(type, availability.get(type) + 1);
    }

    public int getAvailability(String type) {
        return availability.get(type);
    }
}

/**
 * =========================================================
 * CLASS - CancellationService
 * =========================================================
 */
class CancellationService {

    private RoomInventory inventory;

    // Stack for rollback (LIFO)
    private Stack<String> rollbackStack = new Stack<>();

    // Track active reservations
    private Set<String> activeReservations = new HashSet<>();

    public CancellationService(RoomInventory inventory) {
        this.inventory = inventory;

        // Simulating existing reservation
        activeReservations.add("Single-1");
    }

    public void cancelBooking(String reservationId, String roomType) {

        // Validate reservation
        if (!activeReservations.contains(reservationId)) {
            System.out.println("Cancellation failed: Reservation not found.");
            return;
        }

        // Push to rollback stack
        rollbackStack.push(reservationId);

        // Remove from active reservations
        activeReservations.remove(reservationId);

        // Restore inventory
        inventory.increaseAvailability(roomType);

        // Output
        System.out.println("Booking cancelled successfully. Inventory restored for room type: " + roomType);

        // Show rollback history
        System.out.println("\nRollback History (Most Recent First):");
        for (int i = rollbackStack.size() - 1; i >= 0; i--) {
            System.out.println("Released Reservation ID: " + rollbackStack.get(i));
        }

        // Show updated inventory
        System.out.println("\nUpdated " + roomType + " Room Availability: " + inventory.getAvailability(roomType));
    }
}