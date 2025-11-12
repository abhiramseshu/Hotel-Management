import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
    private int loyaltyPoints;
    private List<Reservation> bookingHistory;

    public Customer(String name, String email, String phoneNumber) {
        this.customerId = "CUST" + System.currentTimeMillis();
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.loyaltyPoints = 0;
        this.bookingHistory = new ArrayList<>();
    }

    public void addPoints(int points) { this.loyaltyPoints += points; }
    public void addBooking(Reservation res) { bookingHistory.add(res); }
    
    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public int getLoyaltyPoints() { return loyaltyPoints; }
    public List<Reservation> getBookingHistory() { return bookingHistory; }
    
    public void setAddress(String address) { this.address = address; }
    public String getAddress() { return address; }
    
    public double getDiscountRate() {
        if (loyaltyPoints >= 5000) return 0.15;
        if (loyaltyPoints >= 2000) return 0.10;
        if (loyaltyPoints >= 500) return 0.05;
        return 0.0;
    }
    
    public String getMembershipTier() {
        if (loyaltyPoints >= 5000) return "Platinum";
        if (loyaltyPoints >= 2000) return "Gold";
        if (loyaltyPoints >= 500) return "Silver";
        return "Bronze";
    }
}

abstract class Room {
    protected String roomNumber;
    protected String type;
    protected double pricePerNight;
    protected int maxGuests;
    protected boolean available;
    protected List<String> amenities;

    public Room(String roomNumber, String type, double price, int maxGuests) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.pricePerNight = price;
        this.maxGuests = maxGuests;
        this.available = true;
        this.amenities = new ArrayList<>();
    }

    public abstract double calculatePrice(int nights);
    
    public String getRoomNumber() { return roomNumber; }
    public String getType() { return type; }
    public double getPricePerNight() { return pricePerNight; }
    public int getMaxGuests() { return maxGuests; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean status) { this.available = status; }
    public List<String> getAmenities() { return amenities; }
    
    public void showDetails() {
        System.out.println("\nRoom " + roomNumber + " - " + type);
        System.out.println("Price: $" + pricePerNight + "/night");
        System.out.println("Max Guests: " + maxGuests);
        System.out.println("Amenities: " + String.join(", ", amenities));
        System.out.println("Status: " + (available ? "Available" : "Booked"));
    }
}

class StandardRoom extends Room {
    public StandardRoom(String number) {
        super(number, "Standard", 100.0, 2);
        amenities.add("WiFi");
        amenities.add("TV");
        amenities.add("Mini Fridge");
    }

    public double calculatePrice(int nights) {
        return pricePerNight * nights;
    }
}

class DeluxeRoom extends Room {
    public DeluxeRoom(String number) {
        super(number, "Deluxe", 180.0, 3);
        amenities.add("WiFi");
        amenities.add("Smart TV");
        amenities.add("Mini Bar");
        amenities.add("Balcony");
    }

    public double calculatePrice(int nights) {
        return pricePerNight * nights;
    }
}

class SuiteRoom extends Room {
    public SuiteRoom(String number) {
        super(number, "Suite", 300.0, 4);
        amenities.add("WiFi");
        amenities.add("Smart TV");
        amenities.add("Kitchen");
        amenities.add("Living Room");
        amenities.add("Jacuzzi");
    }

    public double calculatePrice(int nights) {
        return pricePerNight * nights;
    }
}

class Hotel {
    private String hotelId;
    private String name;
    private String location;
    private String address;
    private String phone;
    private List<Room> rooms;
    private List<String> facilities;

    public Hotel(String name, String location) {
        this.hotelId = "HTL" + System.currentTimeMillis();
        this.name = name;
        this.location = location;
        this.rooms = new ArrayList<>();
        this.facilities = new ArrayList<>();
        initializeRooms();
        initializeFacilities();
    }

    private void initializeRooms() {
        for (int i = 1; i <= 15; i++) {
            rooms.add(new StandardRoom("S" + String.format("%03d", i)));
        }
        for (int i = 1; i <= 10; i++) {
            rooms.add(new DeluxeRoom("D" + String.format("%03d", i)));
        }
        for (int i = 1; i <= 5; i++) {
            rooms.add(new SuiteRoom("ST" + String.format("%03d", i)));
        }
    }

    private void initializeFacilities() {
        facilities.add("24/7 Reception");
        facilities.add("Swimming Pool");
        facilities.add("Gym");
        facilities.add("Restaurant");
        facilities.add("Bar");
        facilities.add("Spa");
        facilities.add("Free Parking");
        facilities.add("Room Service");
    }

    public void setAddress(String address) { this.address = address; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getName() { return name; }
    public String getLocation() { return location; }
    public List<Room> getRooms() { return rooms; }

    public void displayInfo() {
        System.out.println("\n========================================");
        System.out.println("  " + name.toUpperCase());
        System.out.println("========================================");
        System.out.println("Location: " + location);
        System.out.println("Address: " + address);
        System.out.println("Phone: " + phone);
        System.out.println("\nFacilities:");
        for (String facility : facilities) {
            System.out.println("  - " + facility);
        }
        System.out.println("========================================\n");
    }

    public List<Room> getAvailableRooms(String type) {
        List<Room> available = new ArrayList<>();
        for (Room room : rooms) {
            if (room.isAvailable() && room.getType().equals(type)) {
                available.add(room);
            }
        }
        return available;
    }
    
    public int countAvailableRooms(String type) {
        return getAvailableRooms(type).size();
    }
}

class Reservation {
    private String reservationId;
    private Customer customer;
    private Hotel hotel;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int guests;
    private String status;
    private double totalAmount;
    private String paymentMethod;
    private LocalDateTime bookingTime;

    public Reservation(Customer customer, Hotel hotel, Room room, 
                      LocalDate checkIn, LocalDate checkOut, int guests) {
        this.reservationId = "RES" + System.currentTimeMillis();
        this.customer = customer;
        this.hotel = hotel;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.guests = guests;
        this.status = "Pending";
        this.bookingTime = LocalDateTime.now();
        calculateTotal();
    }

    private void calculateTotal() {
        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double basePrice = room.calculatePrice(nights);
        double discount = basePrice * customer.getDiscountRate();
        this.totalAmount = basePrice - discount;
    }

    public void setPaymentMethod(String method) { this.paymentMethod = method; }
    public void setStatus(String status) { this.status = status; }
    
    public String getReservationId() { return reservationId; }
    public String getStatus() { return status; }
    public double getTotalAmount() { return totalAmount; }
    public Room getRoom() { return room; }
    public LocalDate getCheckIn() { return checkIn; }
    public LocalDate getCheckOut() { return checkOut; }

    public void displayConfirmation() {
        System.out.println("\n========================================");
        System.out.println("     RESERVATION CONFIRMATION");
        System.out.println("========================================");
        System.out.println("Reservation ID: " + reservationId);
        System.out.println("Status: " + status);
        System.out.println("Booked on: " + bookingTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        
        System.out.println("\nGuest Details:");
        System.out.println("Name: " + customer.getName());
        System.out.println("Email: " + customer.getEmail());
        System.out.println("Phone: " + customer.getPhoneNumber());
        System.out.println("Membership: " + customer.getMembershipTier());
        
        System.out.println("\nHotel Details:");
        System.out.println("Hotel: " + hotel.getName());
        System.out.println("Location: " + hotel.getLocation());
        
        System.out.println("\nRoom Details:");
        System.out.println("Room: " + room.getRoomNumber() + " (" + room.getType() + ")");
        System.out.println("Check-in: " + checkIn);
        System.out.println("Check-out: " + checkOut);
        System.out.println("Nights: " + ChronoUnit.DAYS.between(checkIn, checkOut));
        System.out.println("Guests: " + guests);
        
        System.out.println("\nPayment Details:");
        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double basePrice = room.calculatePrice(nights);
        System.out.println("Room Cost: $" + String.format("%.2f", basePrice));
        
        double discount = customer.getDiscountRate();
        if (discount > 0) {
            System.out.println("Discount (" + (int)(discount * 100) + "%): -$" + 
                             String.format("%.2f", basePrice * discount));
        }
        
        System.out.println("Total: $" + String.format("%.2f", totalAmount));
        System.out.println("Payment: " + paymentMethod);
        System.out.println("========================================\n");
    }

    public boolean canCancel() {
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), checkIn);
        return daysUntil >= 2 && !status.equals("Cancelled");
    }

    public double getCancellationFee() {
        long daysUntil = ChronoUnit.DAYS.between(LocalDate.now(), checkIn);
        if (daysUntil >= 7) return 0;
        if (daysUntil >= 2) return totalAmount * 0.25;
        return totalAmount * 0.50;
    }
}

class ReservationSystem {
    private List<Hotel> hotels;
    private List<Customer> customers;
    private List<Reservation> reservations;
    private Scanner scanner;

    public ReservationSystem() {
        this.hotels = new ArrayList<>();
        this.customers = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.scanner = new Scanner(System.in);
        setupHotels();
    }

    private void setupHotels() {
        Hotel taj = new Hotel("The Taj Palace", "Mumbai");
        taj.setAddress("Apollo Bunder, Mumbai 400001");
        taj.setPhone("+91-22-6665-3366");
        hotels.add(taj);

        Hotel oberoi = new Hotel("The Oberoi", "New Delhi");
        oberoi.setAddress("Connaught Place, New Delhi 110001");
        oberoi.setPhone("+91-11-2389-0606");
        hotels.add(oberoi);

        Hotel leela = new Hotel("The Leela", "Bangalore");
        leela.setAddress("HAL Airport Road, Bangalore 560008");
        leela.setPhone("+91-80-2521-1234");
        hotels.add(leela);

        Hotel itc = new Hotel("ITC Grand", "Chennai");
        itc.setAddress("Mount Road, Chennai 600032");
        itc.setPhone("+91-44-2220-0000");
        hotels.add(itc);

        Hotel hyatt = new Hotel("Grand Hyatt", "Goa");
        hyatt.setAddress("Bambolim Beach, Goa 403206");
        hyatt.setPhone("+91-832-2721-234");
        hotels.add(hyatt);
    }

    public void start() {
        System.out.println("\n╔════════════════════════════════════════╗");
        System.out.println("║   HOTEL RESERVATION SYSTEM             ║");
        System.out.println("╚════════════════════════════════════════╝\n");

        while (true) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Book a Room");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Check Availability");
            System.out.println("5. View Hotels");
            System.out.println("6. Loyalty Program");
            System.out.println("7. Exit");
            System.out.print("\nChoose option: ");

            int choice = getInt();
            
            switch (choice) {
                case 1: bookRoom(); break;
                case 2: viewBookings(); break;
                case 3: cancelBooking(); break;
                case 4: checkAvailability(); break;
                case 5: viewHotels(); break;
                case 6: viewLoyalty(); break;
                case 7:
                    System.out.println("\nThank you for using our system!");
                    return;
                default:
                    System.out.println("\nInvalid option.");
            }
        }
    }

    private void bookRoom() {
        System.out.println("\n=== NEW BOOKING ===\n");

        Hotel hotel = selectHotel();
        if (hotel == null) return;

        hotel.displayInfo();

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();

        Customer customer = findCustomer(email);
        if (customer == null) {
            customer = new Customer(name, email, phone);
            customers.add(customer);
            System.out.println("\nWelcome! You've been enrolled in our loyalty program.");
        } else {
            System.out.println("\nWelcome back, " + customer.getName() + "!");
            System.out.println("Membership: " + customer.getMembershipTier() + 
                             " (" + customer.getLoyaltyPoints() + " points)");
        }

        System.out.print("\nEnter address: ");
        customer.setAddress(scanner.nextLine());

        LocalDate checkIn = getDate("Check-in date (YYYY-MM-DD): ");
        LocalDate checkOut = getDate("Check-out date (YYYY-MM-DD): ");

        if (checkOut.isBefore(checkIn) || checkOut.isEqual(checkIn)) {
            System.out.println("\nInvalid dates.");
            return;
        }

        System.out.print("Number of guests: ");
        int guests = getInt();

        System.out.println("\n--- Select Room Type ---");
        System.out.println("1. Standard ($100/night)");
        System.out.println("2. Deluxe ($180/night)");
        System.out.println("3. Suite ($300/night)");
        System.out.print("\nChoice: ");
        
        int roomChoice = getInt();
        String roomType = "";
        
        switch (roomChoice) {
            case 1: roomType = "Standard"; break;
            case 2: roomType = "Deluxe"; break;
            case 3: roomType = "Suite"; break;
            default:
                System.out.println("\nInvalid choice.");
                return;
        }

        List<Room> available = hotel.getAvailableRooms(roomType);
        
        if (available.isEmpty()) {
            System.out.println("\nNo " + roomType + " rooms available.");
            return;
        }

        Room room = available.get(0);
        room.showDetails();

        if (guests > room.getMaxGuests()) {
            System.out.println("\nWarning: Guests exceed room capacity.");
            System.out.print("Continue? (yes/no): ");
            if (!scanner.nextLine().equalsIgnoreCase("yes")) {
                return;
            }
        }

        Reservation reservation = new Reservation(customer, hotel, room, checkIn, checkOut, guests);

        System.out.println("\n--- Payment Method ---");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. UPI");
        System.out.println("4. Net Banking");
        System.out.println("5. Cash at Hotel");
        System.out.print("\nSelect: ");
        
        int payChoice = getInt();
        String payment = "";
        
        switch (payChoice) {
            case 1: payment = "Credit Card"; break;
            case 2: payment = "Debit Card"; break;
            case 3: payment = "UPI"; break;
            case 4: payment = "Net Banking"; break;
            case 5: payment = "Cash"; break;
            default: payment = "Cash"; break;
        }

        reservation.setPaymentMethod(payment);

        if (payChoice != 5) {
            System.out.println("\nProcessing payment...");
            try { Thread.sleep(1500); } catch (InterruptedException e) {}
            System.out.println("Payment successful!");
        }

        reservation.setStatus("Confirmed");
        room.setAvailable(false);
        reservations.add(reservation);
        customer.addBooking(reservation);
        
        int points = (int)(reservation.getTotalAmount() / 10);
        customer.addPoints(points);

        reservation.displayConfirmation();
        System.out.println("✓ Booking confirmed! You earned " + points + " loyalty points.");
    }

    private Hotel selectHotel() {
        System.out.println("\n--- Available Hotels ---");
        for (int i = 0; i < hotels.size(); i++) {
            System.out.println((i + 1) + ". " + hotels.get(i).getName() + 
                             " - " + hotels.get(i).getLocation());
        }
        System.out.print("\nSelect hotel: ");
        
        int choice = getInt();
        if (choice < 1 || choice > hotels.size()) {
            System.out.println("\nInvalid selection.");
            return null;
        }
        
        return hotels.get(choice - 1);
    }

    private Customer findCustomer(String email) {
        for (Customer c : customers) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        return null;
    }

    private void viewBookings() {
        System.out.println("\n=== MY BOOKINGS ===\n");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Customer customer = findCustomer(email);
        if (customer == null) {
            System.out.println("\nNo customer found.");
            return;
        }

        List<Reservation> bookings = customer.getBookingHistory();
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
            return;
        }

        for (Reservation res : bookings) {
            res.displayConfirmation();
        }
    }

    private void cancelBooking() {
        System.out.println("\n=== CANCEL BOOKING ===\n");
        System.out.print("Enter reservation ID: ");
        String resId = scanner.nextLine();

        Reservation reservation = null;
        for (Reservation res : reservations) {
            if (res.getReservationId().equals(resId)) {
                reservation = res;
                break;
            }
        }

        if (reservation == null) {
            System.out.println("\nReservation not found.");
            return;
        }

        if (!reservation.canCancel()) {
            System.out.println("\nCannot cancel. Must cancel at least 2 days before check-in.");
            return;
        }

        double fee = reservation.getCancellationFee();
        double refund = reservation.getTotalAmount() - fee;

        System.out.println("\nOriginal Amount: $" + String.format("%.2f", reservation.getTotalAmount()));
        System.out.println("Cancellation Fee: $" + String.format("%.2f", fee));
        System.out.println("Refund: $" + String.format("%.2f", refund));
        System.out.print("\nConfirm cancellation? (yes/no): ");

        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            reservation.setStatus("Cancelled");
            reservation.getRoom().setAvailable(true);
            System.out.println("\n✓ Booking cancelled. Refund will be processed in 5-7 days.");
        } else {
            System.out.println("\nCancellation aborted.");
        }
    }

    private void checkAvailability() {
        System.out.println("\n=== CHECK AVAILABILITY ===\n");
        Hotel hotel = selectHotel();
        if (hotel == null) return;

        System.out.println("\n--- Room Availability at " + hotel.getName() + " ---");
        System.out.println("Standard Rooms: " + hotel.countAvailableRooms("Standard"));
        System.out.println("Deluxe Rooms: " + hotel.countAvailableRooms("Deluxe"));
        System.out.println("Suites: " + hotel.countAvailableRooms("Suite"));
    }

    private void viewHotels() {
        System.out.println("\n=== OUR HOTELS ===\n");
        for (Hotel hotel : hotels) {
            hotel.displayInfo();
        }
    }

    private void viewLoyalty() {
        System.out.println("\n=== LOYALTY PROGRAM ===\n");
        System.out.print("Enter email: ");
        String email = scanner.nextLine();

        Customer customer = findCustomer(email);
        if (customer == null) {
            System.out.println("\nNo customer found.");
            return;
        }

        System.out.println("\nName: " + customer.getName());
        System.out.println("Tier: " + customer.getMembershipTier());
        System.out.println("Points: " + customer.getLoyaltyPoints());
        System.out.println("Discount: " + (int)(customer.getDiscountRate() * 100) + "%");
        
        System.out.println("\n--- Tier Benefits ---");
        System.out.println("Bronze: 0% (0-499 points)");
        System.out.println("Silver: 5% (500-1999 points)");
        System.out.println("Gold: 10% (2000-4999 points)");
        System.out.println("Platinum: 15% (5000+ points)");
    }

    private LocalDate getDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Invalid format. Use YYYY-MM-DD");
            }
        }
    }

    private int getInt() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}

public class HotelReservationSystem {
    public static void main(String[] args) {
        ReservationSystem system = new ReservationSystem();
        system.start();
    }
}
