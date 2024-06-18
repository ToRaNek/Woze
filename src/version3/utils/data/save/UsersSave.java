package version3.utils.data.save;

import version3.user.User;

import java.io.*;

public class UsersSave {

    private static final String USER_DIRECTORY = "res/version3/users/";

    public void saveUserToFile(User user) throws IOException {
        String fileName = USER_DIRECTORY + user.getId() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(user);
        }
    }

    public void deleteUserFile(User user) {
        String fileName = USER_DIRECTORY + user.getId() + ".dat";
        File fileToDelete = new File(fileName);
        if (fileToDelete.exists()) {
            fileToDelete.delete();
        }
    }

    public User loadUserFromFile(int userId) throws IOException, ClassNotFoundException {
        String fileName = USER_DIRECTORY + userId + ".dat";
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            return (User) ois.readObject();
        }
    }

    public static String getUserDirectory() {
        return USER_DIRECTORY;
    }
}
