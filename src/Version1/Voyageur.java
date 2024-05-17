package Version1;

import java.util.ArrayList;
import java.util.List;

/**
 * La classe Voyageur représente un utilisateur du système.
 */
public class Voyageur {
    /** La liste des ID d'utilisateurs supprimés */
    private final static List<Integer> IdTrash = new ArrayList<>();

    /**  Variables statiques pour gérer les ID */
    private static int prochaineID = 0;

    /** L'ID de l'utilisateur */
    private int id;

    /** Le prénom de l'utilisateur */
    private String prenom;
    
    /** Le nom de l'utilisateur */
    private String nom;
    
    /** La ville de référence de l'utilisateur */
    private Structure ville;

    /** Le critère de l'utilisateur */
    private TypeCout critere;

    /**
     * Constructeur de la classe Voyageur.
     * @param prenom Le prénom de l'utilisateur.
     * @param nom Le nom de l'utilisateur.
     * @param ville La ville de référence de l'utilisateur.
     * @param critere Le critère de l'utilisateur.
     */
    public Voyageur(String prenom, String nom, Structure ville, TypeCout critere) {
        if (!IdTrash.isEmpty()) {
            // Utiliser l'ID d'un utilisateur supprimé s'il y en a
            this.id = IdTrash.remove(0);
        } else {
            // Incrémenter l'ID s'il n'y a pas d'utilisateur supprimé
            this.id = ++prochaineID;
        }
        this.prenom = prenom;
        this.nom = nom;
        this.ville = ville;
        this.critere = critere;
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
     * Définit le prénom de l'utilisateur.
     * @param prenom Le nouveau prénom de l'utilisateur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Définit le nom de l'utilisateur.
     * @param nom Le nouveau nom de l'utilisateur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Obtient la ville de référence de l'utilisateur.
     * @return La ville de référence de l'utilisateur.
     */
    public Structure getVille() {
        return ville;
    }

    /**
     * Définit la ville de référence de l'utilisateur.
     * @param ville La nouvelle ville de référence de l'utilisateur.
     */
    public void setVille(Structure ville) {
        this.ville = ville;
    }

    /**
     * Obtient le critère de l'utilisateur.
     * @return Le critère de l'utilisateur.
     */
    public TypeCout getCritere() {
        return critere;
    }

    /**
     * Définit le critère de l'utilisateur.
     * @param critere Le nouveau critère de l'utilisateur.
     */
    public void setCritere(TypeCout critere) {
        this.critere = critere;
    }

    /**
     * Supprime l'utilisateur et libère son ID pour une future réutilisation.
     */
    public void addIdToIdTrash() {
        IdTrash.add(this.id);
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
    public static void setProchaineID(int prochaineID) {
        Voyageur.prochaineID = prochaineID;
    }

    /**
     * Définit l'ID de l'utilisateur.
     * @param id Le nouvel ID de l'utilisateur.
     */
    public void setId(int id) {
        this.id = id;
    }
    
    /**
     * Renvoie une représentation textuelle de l'utilisateur sous forme de chaîne de caractères.
     * @return Une représentation textuelle de l'utilisateur.
     */
    @Override
    public String toString() {
        return "Voyageur [id=" + id + ", prenom=" + prenom + ", nom=" + nom + ", ville=" + ville + "]";
    }
}
