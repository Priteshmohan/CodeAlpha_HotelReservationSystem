import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class HotelReservationSystem {
    
    private static ArrayList<Room> rooms = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("--- Hotel Reservation System ---");

        while (true) {
            System.out.println("\n1. Search for Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. Cancel a Reservation");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            
            if (scanner.hasNextInt()) {
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1: displayAvailableRooms(); break;
                    case 2: makeReservation(scanner); break;
                    case 3: cancelReservation(scanner); break;
                    case 4: 
                        System.out.println("Thank you!"); 
                        return;
                    default: System.out.println("Invalid option.");
                }
            } else {
                scanner.next(); // clear invalid input
                System.out.println("Please enter a number.");
            }
        }
    }

    private static void initializeRooms() {
        rooms.add(new Room(101, "Standard", 100.00));
        rooms.add(new Room(102, "Standard", 100.00));
        rooms.add(new Room(201, "Deluxe",   200.00));
        rooms.add(new Room(202, "Deluxe",   200.00));
        rooms.add(new Room(301, "Suite",    350.00));
    }

    private static void displayAvailableRooms() {
        System.out.println("\n--- Available Rooms ---");
        for (Room room : rooms) {
            if (room.isAvailable) {
                System.out.println("Room " + room.roomNumber + " (" + room.category + ") - $" + room.price);
            }
        }
    }

    private static void makeReservation(Scanner scanner) {
        System.out.print("\nEnter Room Number to book: ");
        int roomNum = scanner.nextInt();
        Room selectedRoom = findRoom(roomNum);

        if (selectedRoom == null) {
            System.out.println("Room not found!");
        } else if (!selectedRoom.isAvailable) {
            System.out.println("Sorry, Room " + roomNum + " is already booked.");
        } else {
            System.out.println("Price: $" + selectedRoom.price);
            System.out.println("Payment Successful!");
            selectedRoom.isAvailable = false;
            System.out.println("Booking Confirmed for Room " + roomNum);
            saveToFile("Booked Room " + roomNum);
        }
    }

    private static void cancelReservation(Scanner scanner) {
        System.out.print("\nEnter Room Number to cancel: ");
        int roomNum = scanner.nextInt();
        Room selectedRoom = findRoom(roomNum);

        if (selectedRoom != null && !selectedRoom.isAvailable) {
            selectedRoom.isAvailable = true;
            System.out.println("Cancelled booking for Room " + roomNum);
            saveToFile("Cancelled Room " + roomNum);
        } else {
            System.out.println("That room is not currently booked.");
        }
    }

    private static Room findRoom(int roomNum) {
        for (Room room : rooms) {
            if (room.roomNumber == roomNum) return room;
        }
        return null;
    }

    private static void saveToFile(String message) {
        try {
            FileWriter writer = new FileWriter("hotel_records.txt", true);
            writer.write(message + "\n");
            writer.close();
            System.out.println("(Receipt saved to file)");
        } catch (IOException e) {
            System.out.println("Could not save file.");
        }
    }
}
class Room {
    int roomNumber;
    String category;
    double price;
    boolean isAvailable;

    public Room(int roomNumber, String category, double price) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.isAvailable = true;
    }
}