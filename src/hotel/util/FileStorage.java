package hotel.util;

import java.io.*;
import java.util.ArrayList;

public class FileStorage {
    public static <T> void saveData(String filename, ArrayList<T> list) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> ArrayList<T> loadData(String filename) {
        File file = new File(filename);
        if (!file.exists()) return new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (ArrayList<T>) ois.readObject();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }
}
