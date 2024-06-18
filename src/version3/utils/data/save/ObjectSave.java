package version3.utils.data.save;

import java.io.*;

public class ObjectSave<T> {

    private final String directory;

    public ObjectSave(String directory) {
        this.directory = directory;
    }

    public void saveObjectToFile(T object, String fileName) throws IOException {
        String filePath = directory + fileName;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(object);
        }
    }

    @SuppressWarnings("unchecked") // eleve les soulignements
    public T loadObjectFromFile(String fileName) throws IOException, ClassNotFoundException {
        String filePath = directory + fileName;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (T) ois.readObject();
        }
    }

    public void deleteObjectFile(String fileName) {
        String filePath = directory + fileName;
        File fileToDelete = new File(filePath);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public String getDirectory() {
        return directory;
    }
}
