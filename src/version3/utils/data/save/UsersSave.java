package version3.utils.data.save;

import version3.user.User;

import java.io.IOException;

public class UsersSave extends ObjectSave<User> {

    private static final String USER_DIRECTORY = "res/version3/users/";

    public UsersSave() {
        super(USER_DIRECTORY);
    }

    public void saveUserToFile(User user) throws IOException {
        String fileName = user.getId() + ".dat";
        saveObjectToFile(user, fileName);
    }

    public User loadUserFromFile(int userId) throws IOException, ClassNotFoundException {
        String fileName = userId + ".dat";
        return loadObjectFromFile(fileName);
    }

    public void deleteUserFile(User user) {
        String fileName = user.getId() + ".dat";
        deleteObjectFile(fileName);
    }

    public static String getUserDirectory() {
        return USER_DIRECTORY;
    }

    
}
