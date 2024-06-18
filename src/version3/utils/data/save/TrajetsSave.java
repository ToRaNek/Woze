package version3.utils.data.save;

import version3.graphe.Trajet;

import java.io.IOException;

public class TrajetsSave extends ObjectSave<Trajet> {

    private static final String TRAJET_DIRECTORY = "res/version3/users/history/";

    private final int userId;
    private int currentTrajetId;

    public TrajetsSave(int userId) {
        super(getUserTrajetDirectory(userId));
        this.userId = userId;
        this.currentTrajetId = 1;
    }

    /**
     * Sauvegarde un trajet dans un fichier.
     *
     * @param trajet Le trajet à sauvegarder.
     * @throws IOException En cas d'erreur lors de l'écriture dans le fichier.
     */
    public void saveTrajetToFile(Trajet trajet) throws IOException {
        String fileName = currentTrajetId + ".dat";
        saveObjectToFile(trajet, fileName);
        currentTrajetId++; // Incrémenter l'ID pour le prochain trajet
    }

    /**
     * Charge un trajet à partir d'un fichier en utilisant son ID.
     *
     * @param trajetId L'ID du trajet à charger.
     * @return Le trajet chargé depuis le fichier.
     * @throws IOException            En cas d'erreur lors de la lecture du fichier.
     * @throws ClassNotFoundException Si la classe du trajet chargé n'est pas trouvée.
     */
    public Trajet loadTrajetFromFile(int trajetId) throws IOException, ClassNotFoundException {
        String fileName = trajetId + ".dat";
        return loadObjectFromFile(fileName);
    }

    /**
     * Supprime le fichier correspondant à un trajet en utilisant son ID.
     *
     * @param trajetId L'ID du trajet à supprimer.
     */
    public void deleteTrajetFile(int trajetId) {
        String fileName = trajetId + ".dat";
        deleteObjectFile(fileName);
    }

    /**
     * Obtient le répertoire spécifique des trajets pour cet utilisateur.
     *
     * @param userId L'ID de l'utilisateur.
     * @return Le chemin vers le répertoire des trajets pour cet utilisateur.
     */
    private static String getUserTrajetDirectory(int userId) {
        return TRAJET_DIRECTORY + userId + "/";
    }

    /**
     * Obtient le répertoire des trajets pour tous les utilisateurs.
     *
     * @return Le répertoire des trajets pour tous les utilisateurs.
     */
    public static String getTrajetDirectory() {
        return TRAJET_DIRECTORY;
    }
}
