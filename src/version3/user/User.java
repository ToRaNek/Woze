package version3.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.layout.HBox;
import version3.graphe.TypeCout;
import version3.utils.data.save.UsersSave;

/**
 * La classe User représente un utilisateur du système.
 */
public class User implements Serializable {

    /** La liste des ID d'utilisateurs supprimés */
    private final static List<Integer> IdTrash = new ArrayList<>();

    /** Variables statiques pour gérer les ID */
    private static int prochaineID = 1;

    /** Le critère de l'utilisateur par défaut */
    private static final TypeCout critere_defaut = TypeCout.TEMPS;

    /** La ville de référence de l'utilisateur par défaut */
    private static final String ville_defaut = "Aucune";

    /** La sauvegarde des utilisateurs */
    private static final UsersSave usersSave = new UsersSave();

    /**
     * Obtient le critère par défaut.
     * @return Le critère par défaut.
     */
    public static TypeCout getCritereDefaut() {
        return critere_defaut;
    }

    /**
     * Obtient la liste des ID des utilisateurs supprimés.
     * @return La liste des ID des utilisateurs supprimés.
     */
    public static List<Integer> getIdTrash() {
        return IdTrash;
    }

    /**
     * Obtient le prochain ID disponible pour un nouvel utilisateur.
     * @return Le prochain ID disponible.
     */
    public static int getProchaineID() {
        return prochaineID;
    }

    /**
     * Définit le prochain ID disponible pour un nouvel utilisateur.
     * @param prochaineID Le prochain ID disponible.
     */
    public static void setProchaineID(final int prochaineID) {
        User.prochaineID = prochaineID;
    }

    /**
     * Génère un ID unique pour le User.
     * @return Un ID unique.
     */
    private static int generateId() {
        if (!IdTrash.isEmpty()) {
            return IdTrash.remove(0);
        } else {
            return prochaineID++;
        }
    }

    /** La liste des trajets cliqués */
    private List<HBox> historique;

    /** Le critère de l'utilisateur */
    private TypeCout critere;

    /** L'ID de l'utilisateur */
    private final int id;

    /** Le nom de l'utilisateur */
    private String nom;

    /** Le prénom de l'utilisateur */
    private String prenom;

    /** La ville de référence de l'utilisateur */
    private String ville;

    /**
     * Constructeur de la classe User.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param ville La ville de référence de l'utilisateur.
     * @param critere Le critère de l'utilisateur.
     */
    public User(final String prenom, final String nom, final String ville, final TypeCout critere) throws Exception  {
        this.id = generateId();
        this.prenom = prenom;
        this.nom = nom;
        this.ville = ville;
        this.critere = critere;
        this.historique = new ArrayList<>();
        saveUserToFile(); // Sauvegarde initiale à la création de l'utilisateur
    }

    /**
     * Constructeur de la classe User avec critère par défaut.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param ville La ville de référence de l'utilisateur.
     */
    public User(final String prenom, final String nom, final String ville)throws Exception  {
        this(prenom, nom, ville, critere_defaut);
    }

    /**
     * Constructeur de la classe User avec ville par défaut et critère spécifié.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param critere Le critère de l'utilisateur.
     */
    public User(final String prenom, final String nom, final TypeCout critere) throws Exception {
        this(prenom, nom, ville_defaut, critere);
    }

    /**
     * Constructeur de la classe User avec ville et critère par défaut.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     */
    public User(final String prenom, final String nom) throws Exception {
        this(prenom, nom, ville_defaut, critere_defaut);
    }

    /**
     * Obtient le critère de l'utilisateur.
     * @return Le critère de l'utilisateur.
     */
    public TypeCout getCritere() {
        return critere;
    }

    /**
     * Obtient l'ID de l'utilisateur.
     * @return L'ID de l'utilisateur.
     */
    public int getId() {
        return id;
    }

    /**
     * Obtient le nom de l'utilisateur.
     * @return Le nom de l'utilisateur.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Obtient le prénom de l'utilisateur.
     * @return Le prénom de l'utilisateur.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Obtient la ville de référence de l'utilisateur.
     * @return La ville de référence de l'utilisateur.
     */
    public String getVille() {
        return ville;
    }

    /**
     * Définit le critère de l'utilisateur.
     * @param critere Le nouveau critère de l'utilisateur.
     */
    public void setCritere(final TypeCout critere) throws Exception {
        this.critere = critere;
        updateUserInFile();
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param nom Le nouveau nom de l'utilisateur.
     */
    public void setNom(final String nom) throws Exception {
        this.nom = nom;
        updateUserInFile();
    }

    /**
     * Définit le prénom de l'utilisateur.
     * @param prenom Le nouveau prénom de l'utilisateur.
     */
    public void setPrenom(final String prenom)throws Exception  {
        this.prenom = prenom;
        updateUserInFile();
    }

    /**
     * Définit la ville de référence de l'utilisateur.
     * @param ville La nouvelle ville de référence de l'utilisateur.
     */
    public void setVille(final String ville) throws Exception {
        this.ville = ville;
        updateUserInFile();
    }

    /**
     * Obtient la liste des trajets de l'historique de l'utilisateur.
     * @return La liste des trajets de l'historique.
     */
    public List<HBox> getHistorique() {
        return historique;
    }

    /**
     * Définit la liste des trajets de l'historique de l'utilisateur.
     * @param historique La nouvelle liste des trajets de l'historique.
     */
    public void setHistorique(List<HBox> historique)throws Exception  {
        this.historique = historique;
        updateUserInFile();
    }

    /**
     * Ajoute un trajet à l'historique de l'utilisateur.
     * @param trajet Le trajet à ajouter.
     */
    public void addHistorique(HBox trajet) throws Exception {
        this.historique.add(trajet);
        updateUserInFile();
    }

    /**
     * Supprime un trajet à l'historique de l'utilisateur.
     * @param trajet Le trajet à supprimer.
     */
    public void removeHistorique(HBox trajet) throws Exception {
        this.historique.remove(trajet);
        updateUserInFile();
    }

    /**
     * Supprime un trajet à l'historique de l'utilisateur par index.
     * @param index L'index du trajet à supprimer.
     */
    public void removeHistorique(int index) throws Exception {
        this.historique.remove(index);
        updateUserInFile();
    }

    /**
     * Ajoute l'ID de l'utilisateur à la liste des ID supprimés.
     */
    public void addIdToIdTrash() throws Exception  {
        IdTrash.add(this.id);
        updateUserInFile();
    }

    /**
     * Met à jour l'utilisateur dans le fichier correspondant.
     */
    private void updateUserInFile() throws Exception {
        usersSave.saveUserToFile(this);
    }

    /**
     * Supprime l'historique des trajets de l'utilisateur.
     */
    public void supprimerHistorique() throws Exception  {
        historique.clear();
        updateUserInFile();
    }

    /**
     * Renvoie une représentation textuelle de l'utilisateur sous forme de chaîne de caractères.
     * @return Une représentation textuelle de l'utilisateur.
     */
    @Override
    public String toString() {
        return "User [critere=" + critere + ", id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", ville="
                + ville + "]";
    }

    /**
     * Sauvegarde l'utilisateur dans un fichier correspondant à son ID.
     */
    public void saveUserToFile() throws Exception {
        usersSave.saveUserToFile(this);
    }
}
