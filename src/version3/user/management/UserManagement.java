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

    // private void initializeUsersCSV() {
    //     for (final String data : UserDataExtractor.users) {
    //         final String[] split = data.split(";");

    //         if (split.length >= 3 && split.length <= 5) {
    //             final String prenom = split[0];
    //             final String nom = split[1];
    //             final String ville = split[2];
    //             final String critere = (split.length >= 4) ? split[3] : null;

    //             if (prenom != null && nom != null && ville != null && (critere == null || Verifications.estCritereValide(critere))) {
    //                 final TypeCout critUser = (critere != null) ? TypeCout.valueOf(critere.toUpperCase()) : User.getCritereDefaut();

    //                 if (split.length == 4) {
    //                     users.add(new User(prenom, nom, ville, critUser));
    //                 } else if (split.length == 3) {
    //                     users.add(new User(prenom, nom, ville));
    //                 } else if (split.length == 2) {
    //                     users.add(new User(prenom, nom));
    //                 } else if (split.length == 5) {
    //                     final TypeCout crit = TypeCout.valueOf(split[4].toUpperCase());
    //                     users.add(new User(prenom, nom, ville, crit));
    //                 }
    //             }
    //         }
    //     }
    // }

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
