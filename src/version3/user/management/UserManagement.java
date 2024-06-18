package version3.user.management;

import version3.user.User;
import version3.utils.data.save.UsersSave;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

public class UserManagement {

    private ArrayList<User> users;
    private UsersSave usersSave;

    public UserManagement() {
        users = new ArrayList<>();
        usersSave = new UsersSave();
        initializeUsersDat();
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addUser(final User user) {
        users.add(user);
        try {
            usersSave.saveUserToFile(user);
        } catch (IOException e) {
            System.err.println("Erreur lors de la sauvegarde de l'utilisateur : " + e.getMessage());
        }
    }

    public void removeUser(final User user) {
        User.getIdTrash().add(user.getId());
        users.remove(user);
        usersSave.deleteUserFile(user);
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User getUserByNomPrenom(String nom, String prenom) {
        for (User user : users) {
            if (user.getNom().equalsIgnoreCase(nom) && user.getPrenom().equalsIgnoreCase(prenom)) {
                return user;
            }
        }
        return null;
    }

    private void initializeUsersDat() {
    File userDirectory = new File(UsersSave.getUserDirectory());
    File[] userFiles = userDirectory.listFiles();

    if (userFiles != null) {
        for (File file : userFiles) {
            if (file.isFile() && file.getName().endsWith(".dat")) {
                try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                    User user = (User) ois.readObject();
                    users.add(user);
                } catch (IOException | ClassNotFoundException e) {
                    System.err.println("Erreur lors de la lecture du fichier utilisateur " + file.getName() + ": " + e.getMessage());
                }
            }
        }
    }
}

}
