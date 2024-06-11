package version3.user.management;

import version3.graphe.TypeCout;
import version3.user.Voyageur;
import version3.utils.verifications.Verifications;
import version3.utils.data.extract.UserDataExtractor;
import java.util.ArrayList;

public class UserManagement {
    private ArrayList<Voyageur> users;

    public UserManagement() {
        users = new ArrayList<>();
        initializeUsers();
    }

    private void initializeUsers() {
        for (final String data : UserDataExtractor.users) {
            final String[] split = data.split(";");

            if (split.length >= 3 && split.length <= 5) {
                final String prenom = split[0];
                final String nom = split[1];
                final String ville = split[2];
                final String critere = (split.length >= 4) ? split[3] : null;

                if (prenom != null && nom != null && ville != null && (critere == null || Verifications.estCritereValide(critere))) {
                    final TypeCout critUser = (critere != null) ? TypeCout.valueOf(critere.toUpperCase()) : Voyageur.getCritereDefaut();

                    if (split.length == 4) {
                        users.add(new Voyageur(prenom, nom, ville, critUser));
                    } else if (split.length == 3) {
                        users.add(new Voyageur(prenom, nom, ville));
                    } else if (split.length == 2) {
                        users.add(new Voyageur(prenom, nom));
                    } else if (split.length == 5) {
                        final TypeCout crit = TypeCout.valueOf(split[4].toUpperCase());
                        users.add(new Voyageur(prenom, nom, ville, crit));
                    }
                }
            }
        }
    }

    public ArrayList<Voyageur> getUsers() {
        return users;
    }

    public void addUser(final Voyageur user) {
        users.add(user);
        // TODO Save
    }

    public void removeUser(final Voyageur user) {
        Voyageur.getIdTrash().add(user.getId());
        // TODO save
        users.remove(user);
    }

    public Voyageur getUserById(int userId) {
        for (Voyageur user : users) {
            if (user.getId() == userId) {
                return user;
            }
        }
        return null; 
    }

    public Voyageur getUserByNomPrenom(String nom, String prenom) {
        for (Voyageur user : users) {
            if (user.getNom().equalsIgnoreCase(nom) && user.getPrenom().equalsIgnoreCase(prenom)) {
                return user;
            }
        }
        return null; 
    }
    
}
