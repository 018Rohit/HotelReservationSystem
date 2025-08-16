package hotel.service;

import hotel.model.Room;
import hotel.util.FileStorage;
import java.util.ArrayList;

public class RoomService {
    private final String ROOM_FILE = "data/rooms.dat";
    private ArrayList<Room> rooms;

    public RoomService() {
        rooms = FileStorage.loadData(ROOM_FILE);
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        rooms.add(room);
        FileStorage.saveData(ROOM_FILE, rooms);
    }

    public void updateRooms() {
        FileStorage.saveData(ROOM_FILE, rooms);
    }
}
