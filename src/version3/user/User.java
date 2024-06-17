package version3.user;

import java.util.ArrayList;
import java.util.List;

import version3.graphe.Trajet;
import version3.graphe.TypeCout;

/**
 * La classe User représente un utilisateur du système.
 */
public class User {

    /** La liste des ID d'utilisateurs supprimés */
    private final static List<Integer> IdTrash = new ArrayList<>();

    /** Variables statiques pour gérer les ID */
    private static int prochaineID = 1;

    /** Le critère de l'utilisateur par défaut */
    private static final TypeCout critere_defaut = TypeCout.TEMPS;

    /** La ville de référence de l'utilisateur par défaut */
    private static final String ville_defaut = "Aucune";

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
    private List<Trajet> historique;

    /** La liste des trajets favoris de l'utilisateur */
    private List<Trajet> favoris;

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
    public User(final String prenom, final String nom, final String ville, final TypeCout critere) {
        this.id = generateId();
        this.prenom = prenom;
        this.nom = nom;
        this.ville = ville;
        this.critere = critere;
        this.historique = new ArrayList<>();
        this.favoris = new ArrayList<>();
    }

    /**
     * Constructeur de la classe User.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param ville La ville de référence de l'utilisateur.
     */
    public User(final String prenom, final String nom, final String ville) {
        this(prenom, nom, ville, critere_defaut);
    }

    /**
     * Constructeur de la classe User.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     */
    public User(final String prenom, final String nom) {
        this(prenom, nom, ville_defaut, critere_defaut);
    }

    /**
     * Constructeur de la classe User.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param critere Le critère de l'utilisateur.
     */
    public User(final String prenom, final String nom, final TypeCout critere) {
        this(prenom, nom, ville_defaut, critere);
    }

    /**
     * Ajoute l'ID de l'utilisateur à la liste des ID supprimés.
     */
    public void addIdToIdTrash() {
        IdTrash.add(this.id);
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
    public void setCritere(final TypeCout critere) {
        this.critere = critere;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param nom Le nouveau nom de l'utilisateur.
     */
    public void setNom(final String nom) {
        this.nom = nom;
    }

    /**
     * Définit le prénom de l'utilisateur.
     * @param prenom Le nouveau prénom de l'utilisateur.
     */
    public void setPrenom(final String prenom) {
        this.prenom = prenom;
    }

    /**
     * Définit la ville de référence de l'utilisateur.
     * @param ville La nouvelle ville de référence de l'utilisateur.
     */
    public void setVille(final String ville) {
        this.ville = ville;
    }

    /**
     * Obtient la liste des trajets de l'historique de l'utilisateur.
     * @return La liste des trajets de l'historique.
     */
    public List<Trajet> getHistorique() {
        return historique;
    }

    /**
     * Définit la liste des trajets de l'historique de l'utilisateur.
     * @param historique La nouvelle liste des trajets de l'historique.
     */
    public void setHistorique(List<Trajet> historique) {
        this.historique = historique;
    }

    /**
     * Supprime l'historique des trajets de l'utilisateur.
     */
    public void supprimerHistorique() {
        historique.clear();
    }

    /**
     * Ajoute un trajet à l'historique de l'utilisateur.
     * @param trajet Le trajet à ajouter.
     */
    public void addHistorique(Trajet trajet) {
        this.historique.add(trajet);
    }

    /**
     * Supprime un trajet à l'historique de l'utilisateur.
     * @param trajet Le trajet à supprimer.
     */
    public void removeHistorique(Trajet trajet){
        this.historique.remove(trajet);
    }

    /**
     * Supprime un trajet à l'historique de l'utilisateur.
     * @param trajet L'index du trajet à supprimer.
     */
    public void removeHistorique(int index){
        this.historique.remove(index);
    }

    /**
     * Obtient la liste des trajets favoris de l'utilisateur.
     * @return La liste des trajets favoris.
     */
    public List<Trajet> getFavoris() {
        return favoris;
    }

    /**
     * Définit la liste des trajets favoris de l'utilisateur.
     * @param favoris La nouvelle liste des trajets favoris.
     */
    public void setFavoris(List<Trajet> favoris) {
        this.favoris = favoris;
    }

    /**
     * Ajoute un trajet aux favoris de l'utilisateur.
     * @param trajet Le trajet à ajouter.
     */
    public void addFavoris(Trajet trajet) {
        this.favoris.add(trajet);
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
    
}
