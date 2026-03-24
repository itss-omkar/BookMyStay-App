import java.util.*;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * @version 6.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        System.out.println("Room Allocation Processing\n");

        // Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        bookingQueue.addRequest(new Reservation("Abhi", "Single"));
        bookingQueue.addRequest(new Reservation("Subha", "Double"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite"));

        // Initialize allocation service
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        // Process bookings
        while (bookingQueue.hasPendingRequests()) {
            Reservation request = bookingQueue.getNextRequest();
            allocationService.processReservation(request);
        }
    }
}

/**
 * =========================================================
 * CLASS - Reservation
 * =========================================================
 */
class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }
}

/**
 * =========================================================
 * CLASS - BookingRequestQueue
 * =========================================================
 */
class BookingRequestQueue {

    private Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasPendingRequests() {
        return !queue.isEmpty();
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

    public int getAvailableRooms(String type) {
        return availability.getOrDefault(type, 0);
    }

    public void reduceAvailability(String type) {
        availability.put(type, availability.get(type) - 1);
    }
}

/**
 * =========================================================
 * CLASS - RoomAllocationService
 * =========================================================
 */
class RoomAllocationService {

    private RoomInventory inventory;

    // Track allocated room IDs (prevents duplicates)
    private Set<String> allocatedRoomIds = new HashSet<>();

    // Map room type → allocated room IDs
    private Map<String, Set<String>> roomAllocations = new HashMap<>();

    // Counters for unique ID generation
    private Map<String, Integer> counters = new HashMap<>();

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void processReservation(Reservation r) {

        String type = r.getRoomType();

        // Check availability
        if (inventory.getAvailableRooms(type) <= 0) {
            System.out.println("No rooms available for " + type);
            return;
        }

        // Generate unique room ID
        int count = counters.getOrDefault(type, 0) + 1;
        counters.put(type, count);

        String roomId = type + "-" + count;

        // Ensure uniqueness using Set
        if (allocatedRoomIds.contains(roomId)) {
            System.out.println("Duplicate room detected! Skipping...");
            return;
        }

        // Store allocation
        allocatedRoomIds.add(roomId);

        roomAllocations
                .computeIfAbsent(type, k -> new HashSet<>())
                .add(roomId);

        // Reduce inventory
        inventory.reduceAvailability(type);

        // Confirm booking
        System.out.println("Booking confirmed for Guest: "
                + r.getGuestName()
                + ", Room ID: "
                + roomId);
    }
}