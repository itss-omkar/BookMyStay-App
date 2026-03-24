import java.util.LinkedList;
import java.util.Queue;

/**
 * =========================================================
 * MAIN CLASS - BookMyStayApp
 * =========================================================
 *
 * Use Case 5: Booking Request Queue (FIFO)
 *
 * @version 5.1
 */
public class BookMyStayApp {

    public static void main(String[] args) {

        // Display header
        System.out.println("Booking Request Queue\n");

        // Initialize queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Create booking requests
        Reservation r1 = new Reservation("Abhi", "Single");
        Reservation r2 = new Reservation("Subha", "Double");
        Reservation r3 = new Reservation("Vanmathi", "Suite");

        // Add requests
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Process requests (FIFO)
        while (bookingQueue.hasPendingRequests()) {
            Reservation r = bookingQueue.getNextRequest();
            System.out.println("Processing booking for Guest: "
                    + r.getGuestName()
                    + ", Room Type: "
                    + r.getRoomType());
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

    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    // Add request to queue
    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    // Get next request (FIFO)
    public Reservation getNextRequest() {
        return queue.poll();
    }

    // Check if queue has pending requests
    public boolean hasPendingRequests() {
        return !queue.isEmpty();
    }
}