package version2;

import java.util.ArrayList;
import java.util.List;

// import org.junit.jupiter.params.provider.CsvFileSource;
import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Plateforme représente une plateforme de transport.
 */
public class Plateforme {

    // ATTRIBUTS :

    // TODO ajouter une liste de user et toutes les méthodes associé ( version 3)
    private Voyageur currentUser;
    private ArrayList<Voyageur> users;
    private ArrayList<Arete> aretes;
    private ArrayList<String> villes;
    private ArrayList<Structure> structures;
    private TypeCout currentCrit = Voyageur.getCritereDefaut();
    private MultiGrapheOrienteValue currentGraphe;

    // CONSTRUCTEURS :

    /**
     * Constructeur de la classe Plateforme.
     * @param data Les données pour initialiser la plateforme.
     */
    public Plateforme() {
        structures = new ArrayList<>();
        aretes = new ArrayList<>();
        villes = new ArrayList<>();
        users = new ArrayList<>();

        
        for (String data : DataExtractor.data_correspondances) {
            String[] split = data.split(";");
            // ville
            String ville = split[0];

            // ajoute la ville si elle existe pas 
            if (!containsVille(ville)) {
                villes.add(ville);
            }

            // modalité 1
            ModaliteTransport modalite1 = ModaliteTransport.valueOf(split[1].toUpperCase());
            // creation de structure si elle existe pas
            Structure struct1 = createOrGetStructure(ville, modalite1);

            // modalité 2
            ModaliteTransport modalite2 = ModaliteTransport.valueOf(split[2].toUpperCase());
            // creation de structure si elle existe pas
            Structure struct2 = createOrGetStructure(ville, modalite2);

            // COUTS
            double prix = Math.round(Double.parseDouble(split[5])*100)/100;
            double co2 = Math.round(Double.parseDouble(split[4])*100)/100;
            double temps = Math.round(Double.parseDouble(split[3])*100)/100;

            // ARETES ALLEE - RETOUR
            Arete correspondace = new Arete(struct1, struct2, null,temps, co2, prix); 
            add2Arete(correspondace);
        }

        for (String data : DataExtractor.data_villes) {
            String[] split = data.split(";");

            ModaliteTransport modalite = ModaliteTransport.valueOf(split[2].toUpperCase());

            String ville_depart = split[0];
            String ville_arrivee = split[1];

            Structure depart, arrivee;

            // DEPART
            depart = createOrGetStructure(ville_depart, modalite);
            // ARRIVEE
            arrivee = createOrGetStructure(ville_arrivee, modalite);
            // COUTS
            double prix = Math.round(Double.parseDouble(split[5])*100)/100;
            double co2 = Math.round(Double.parseDouble(split[4])*100)/100;
            double temps = Math.round(Double.parseDouble(split[3])*100)/100;

            // ARETES ALLEE - RETOUR
            Arete allee = new Arete(depart, arrivee, modalite,temps, co2, prix);
            add2Arete(allee);
        }  
        currentGraphe = buildGraph(Voyageur.getCritereDefaut());
        int cpt = 1;
        for (String data : DataExtractor.users) {
            String[] split = data.split(";");

            // TODO demander à gordon de faire ses vérifications aussi dans la classe associé
        
            // Vérifie si toutes les données sont présentes
            if (split.length >= 3 && split.length <= 5) {
                String prenom = split[0];
                String nom = split[1];
                String ville = split[2];
                String critere = (split.length >= 4) ? split[3] : null;
        
                // Vérifie si aucune des données est nulle
                if (prenom != null && nom != null && ville != null && (critere == null || Verification.estCritereValide(critere))) {
                    TypeCout critUser = (critere != null) ? TypeCout.valueOf(critere.toUpperCase()) : Voyageur.getCritereDefaut();
                    
                    if (split.length == 4) {
                        users.add(new Voyageur(prenom, nom, ville, critUser)); 
                    } else if (split.length == 3) {
                        users.add(new Voyageur(prenom, nom, ville)); 
                    } else if (split.length == 2) {
                        users.add(new Voyageur(prenom, nom)); 
                    } else if (split.length == 5) {
                        TypeCout crit = TypeCout.valueOf(split[4].toUpperCase());
                        users.add(new Voyageur(prenom, nom, ville, crit));
                    }
                } else {
                    System.out.println("Le fichier d'utilisateurs contient des données compromises ligne : " + cpt );
                }
            } else {
                System.out.println("Le fichier d'utilisateurs contient des données compromises ligne : " + cpt );
            }
            cpt++;
        }
        
    }

    // GETTERS :

    /**
     * Obtient la liste des arêtes de la plateforme.
     * @return La liste des arêtes.
     */
    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    /**
     * Obtient le critère actuellement utilisé par la plateforme.
     * 
     * @return Le critère actuel utilisé par la plateforme.
     */
    public TypeCout getCurrentCrit() {
        return currentCrit;
    }

    /**
     * Obtient l'utilisateur actuellement utilisé par la plateforme.
     * 
     * @return L'utilisateur actuel utilisé par la plateforme.
     */
    public Voyageur getCurrentUser() {
        return currentUser;
    }

    /**
     * Obtient la liste des utilisteurs de la plateforme.
     * 
     * @return La liste des utilisteurs de la plateforme.
     */
    public ArrayList<Voyageur> getUsers() {
        return users;
    }
    /**
     * Obtient la liste des villes de la plateforme.
     * 
     * @return La liste des villes.
     */
    public ArrayList<String> getVilles() {
        return villes;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    /**
     * Obtient le graphe actuellement utilisé par la plateforme.
     * 
     * @return Le graphe actuel utilisé par la plateforme.
     */
    public MultiGrapheOrienteValue getCurrentGraphe() {
        return currentGraphe;
    }


    //SETTERS :

    /**
     * Définit le critère actuellement utilisé par la plateforme.
     * 
     * @param currentCrit Le critère à utiliser pour la plateforme.
     */
    public void setCurrentCrit(final TypeCout currentCrit) {
        this.currentCrit = currentCrit;
    }

    /**
     * Définit l'utilisateur actuellement utilisé par la plateforme.
     * 
     * @param currentCrit L'utilisateur qu'utilisera la plateforme.
     */
    public void setCurrentUser(Voyageur currentUser) {
        this.currentUser = currentUser;
        this.currentCrit = currentUser.getCritere();
        buildGraph(currentCrit);
    }

    //TODO enregister dans un csv à chaque modif
    /**
     * Définit la liste des utilisateurs.
     * 
     * @param currentCrit L'utilisateur qu'utilisera la plateforme.
     */
    public void setUsers(ArrayList<Voyageur> users) {
        this.users = users;
    }

    /**
     * Définit la liste des villes de la plateforme.
     * 
     * @param villes La liste des villes à définir.
     */
    public void setVilles(final ArrayList<String> villes) {
        this.villes = villes;
    }

    public void setAretes(final ArrayList<Arete> aretes) {
        this.aretes = aretes;
    }

    public void setStructures(final ArrayList<Structure> structures) {
        this.structures = structures;
    }

    /**
     * Définit le graphe à utiliser pour la plateforme.
     * 
     * @param currentGraphe Le graphe à utiliser pour la plateforme.
     */
    public void setCurrentGraphe(final MultiGrapheOrienteValue currentGraphe) {
        this.currentGraphe = currentGraphe;
    }

    // METHODES  :

    /**
     * Ajoute un utilisateur à la liste users de la plateforme.
     * @param arete L'utilisateur à ajouter.
     */
    public void addUser(final Voyageur user) {
        users.add(user);
        // TODO version 3 enregistrer dans le csv 
    }

    /**
     * Ajoute une arête à la liste des arêtes de la plateforme.
     * @param arete L'arête à ajouter.
     */
    public void add1Arete(final Arete arete) {
        aretes.add(arete);
    }

    /**
     * Ajoute une arête à la liste des arêtes de la plateforme, ainsi que son arête retour.
     * 
     * @param arete L'arête à ajouter.
     */
    public void add2Arete(final Arete arete) {
        // Ajout de l'arête originale
        add1Arete(arete);
        
        // Création de l'arête retour avec les villes inversées
        Arete retour = new Arete(arete.getArrivee(), arete.getDepart(), arete.getModalite(), arete.getCouts());
        
        // Ajout de l'arête retour
        add1Arete(retour);
    }

    /**
     * Ajoute une ville à la liste des villes de la plateforme.
     * 
     * @param ville Le nom de la ville à ajouter.
     * @return true si la ville a été ajoutée avec succès, sinon false.
     */
    public boolean addVille(final String ville) {
        return villes.add(ville);
    }

    /**
     * Vérifie si la plateforme contient une ville donnée.
     * 
     * @param ville Le nom de la ville à rechercher.
     * @return true si la ville est présente dans la plateforme, sinon false.
     */
    public boolean containsVille( String ville) {
        for (String v : villes) {
            if (v.toUpperCase().equals(ville.toUpperCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si la plateforme contient une structure spécifiée.
     * @param structureNom Le nom de la structure à vérifier.
     * @return true si la structure existe, false sinon.
     */
    public boolean containsStructure(String structureNom) {
        for (Structure structure : structures) {
            if (structure.getNom().equalsIgnoreCase(structureNom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si une structure associée à une ville donnée existe dans la plateforme.
     * 
     * @param ville Le nom de la ville à rechercher dans les structures.
     * @return true si une structure associée à la ville existe dans la plateforme, sinon false.
     */
    public boolean existVilleStructureVersion(final String ville) {
        boolean bool = false;
        for (Structure structure : structures) {
            if (structure.getVille().equals(ville)) {
                bool = true;
            }
        }
        return bool;
    }

    /**
     * Ajoute une structure à la liste des structures de la plateforme.
     * @param structure La structure à ajouter.
     */
    public void addStructure(final Structure structure) {
        structures.add(structure);
    }

    /**
     * Crée une nouvelle structure associée à une ville et à une modalité de transport spécifiques s'il n'existe pas déjà, sinon renvoie la structure existante.
     * 
     * @param ville La ville associée à la structure.
     * @param modalite La modalité de transport associée à la structure.
     * @return La structure créée ou existante.
     */
    public Structure createOrGetStructure(final String ville, final ModaliteTransport modalite) {
        Structure struct;
        boolean exist = contains(Structure.nom(ville, modalite));
        if (!exist) {
            if (!containsVille(ville)) {
                addVille(ville);
            }
            struct = new Structure(ville, modalite);
            addStructure(struct);
        } else {
            if (!containsVille(ville)) {
                addVille(ville);
            }
            struct = getStructure(Structure.nom(ville, modalite));
        }
        return struct;
    }

    /**
     * Obtient la structure avec le nom spécifié.
     * @param nom Le nom de la structure à obtenir.
     * @return La structure correspondante, ou null si elle n'est pas trouvée.
     */
    public Structure getStructure(final String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return structure;
            }
        }
        return null;
    }

    /**
     * Retourne l'index de l'arête spécifiée dans la liste des arêtes de la plateforme.
     * 
     * @param arete L'arête dont on souhaite obtenir l'index.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(final Arete arete) {
        return aretes.indexOf(arete);
    }

    /**
     * Retourne l'index de la structure spécifiée dans la liste des structures de la plateforme.
     * 
     * @param structure La structure dont on souhaite obtenir l'index.
     * @return L'index de la structure dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(final Structure structure) {
        return structures.indexOf(structure);
    }

    /**
     * Retourne l'index de l'arête spécifiée par ses structures de départ et d'arrivée ainsi que la modalité de transport dans la liste des arêtes de la plateforme.
     * 
     * @param depart La structure de départ de l'arête.
     * @param arrivee La structure d'arrivée de l'arête.
     * @param modalite La modalité de transport de l'arête.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(final Structure depart,final Structure arrivee,final ModaliteTransport modalite) {
        int res = -1;
        for (Arete arete : aretes) {
            if (arete.getDepart() == depart && arete.getArrivee() == arrivee && arete.getModalite() == modalite) {
                res = indexOf(arete);
            }
        }
        return res;
    }

    /**
     * Retourne l'index de l'arête spécifiée par les noms de ses structures de départ et d'arrivée ainsi que la modalité de transport dans la liste des arêtes de la plateforme.
     * 
     * @param depart Le nom de la structure de départ de l'arête.
     * @param arrivee Le nom de la structure d'arrivée de l'arête.
     * @param modalite Le nom de la modalité de transport de l'arête.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(final String depart,final String arrivee,final String modalite) {
        ModaliteTransport m = ModaliteTransport.valueOf(modalite.toUpperCase());
        Structure d = getStructure(Structure.nom(depart,m));
        Structure a = getStructure(Structure.nom(arrivee,m));
        return indexOf(d, a, m);
    }

    /**
     * Vérifie si une structure avec le nom spécifié existe dans la plateforme.
     * 
     * @param nom Le nom de la structure à vérifier.
     * @return true si la structure existe, false sinon.
     */
    public boolean contains(final String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Supprime l'arête spécifiée de la liste des arêtes de la plateforme.
     * 
     * @param arete L'arête à supprimer.
     */
    public void removeArete(final Arete arete) {
        aretes.remove(arete);
    }

    /**
     * Supprime l'arête à l'index spécifié de la liste des arêtes de la plateforme.
     * 
     * @param index L'index de l'arête à supprimer.
     */
    public void removeArete(final int index) {
        removeArete(aretes.get(index));
    }

    /**
     * Supprime la structure spécifiée de la liste des structures de la plateforme.
     * 
     * @param structure La structure à supprimer.
     */
    public void removeStructure(final Structure structure) {
        structures.remove(structure);
    }

    /**
     * Supprime la structure à l'index spécifié de la liste des structures de la plateforme.
     * 
     * @param index L'index de la structure à supprimer.
     */
    public void removeStructure(final int index) {
        removeStructure(structures.get(index));
    }

    /**
     * Construit un graphe à partir des structures et arêtes de la plateforme en utilisant le critère spécifié.
     * 
     * @param critere Le critère à utiliser pour construire le graphe.
     * @return Le graphe construit.
     */
    public MultiGrapheOrienteValue buildGraph(final TypeCout critere) {
        MultiGrapheOrienteValue g = new MultiGrapheOrienteValue();
        for (Structure structure : structures) {
            g.ajouterSommet(structure);
        }
        for (Arete arete : aretes) {
            g.ajouterArete(arete, arete.getCout(critere));
        }
        currentGraphe = g;
        return g;
    }

    /**
     * Construit un graphe à partir des structures et arêtes de la plateforme en utilisant le critère spécifié sous forme de chaîne de caractères.
     * 
     * @param critere Le critère à utiliser pour construire le graphe (sous forme de chaîne de caractères).
     * @return Le graphe construit.
     */
    public MultiGrapheOrienteValue buildGraph(final String critere) {
        for (TypeCout value : TypeCout.values()) {
            if (TypeCout.valueOf(critere.toUpperCase()) == value) {
                return buildGraph(value);
            }
        }
        System.err.println("Le critère n'est pas valide");
        return null;
    }
    // TODO

    

    /**
     * Recherche les k plus courts chemins entre deux structures spécifiées en utilisant le critère spécifié.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le critère à utiliser pour la recherche.
     * @param k Le nombre de chemins à trouver.
     * @return Une liste contenant les k plus courts chemins.
     */
    public List<Chemin> simplePCC(final Structure depart,final Structure arrivee,final TypeCout crit,final int k) {
        return (AlgorithmeKPCC.kpcc(buildGraph(crit), depart, arrivee, k));
    }

    /**
     * Vérifie si deux structures sont reliées dans le graphe actuel en utilisant le critère actuel.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @return true si les structures sont reliées, false sinon.
     */
    public boolean isLinked(final Structure depart,final Structure arrivee) {
        if (depart == null || arrivee == null) {
            return false;
        }

        try {
            List<Chemin> chemins = simplePCC(currentGraphe, depart, arrivee, 1);
            return !chemins.isEmpty();
        } catch (IllegalArgumentException e) {
            System.out.println("Il n'existe pas de lien entre " + depart.getNom() + " et " + arrivee.getNom() + " pour " + currentCrit);
            return false;
        }
    }

    /**
     * Vérifie si une structure est dans une ville.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @return true si les structures sont reliées, false sinon.
     */
    public boolean isInACity(final Structure struct) {
        boolean bool = false;
        for (String ville : villes) {
            if(struct.getVille().equals(ville)) bool = true;
        }
        return bool;
    }
    


    // METHODES STATIQUE :

    /**
     * Recherche les k plus courts chemins entre deux structures spécifiées dans le graphe donné.
     * 
     * @param graphe Le graphe dans lequel effectuer la recherche.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param k Le nombre de chemins à trouver.
     * @return Une liste contenant les k plus courts chemins.
     */
    public static List<Chemin> simplePCC(final MultiGrapheOrienteValue graphe,final Structure depart,final Structure arrivee,final int k) {
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }

    public static List<Trancon> reductionAffichage(Chemin chemin) {
        List<Trancon> arretesApresReduction = new ArrayList<Trancon>();
        ModaliteTransport derniereModalite = chemin.aretes().get(0).getModalite();
        arretesApresReduction.add(chemin.aretes().get(0));
        chemin.aretes().remove(0);
        for (Trancon trancon : chemin.aretes()) {
            if (trancon.getModalite() != derniereModalite) {
                arretesApresReduction.add(trancon);
            }
        }
        return arretesApresReduction;
    }


    // DISPLAY 
    /**
     * Affiche toutes les structures de la ville spécifiée.
     *
     * @param ville Le nom de la ville dont on souhaite afficher les structures.
     */
    public void showAllStructureOf(String ville) {
        System.out.println("Structures de la ville " + ville + " :");
        for (Structure structure : structures) {
            if (structure.getVille().equalsIgnoreCase(ville)) {
                System.out.println(structure);
            }
        }
    }

    /**
     * Affiche toutes les structures de chaque ville de la plateforme.
     */
    public void showAllStructures() {
        System.out.println("Toutes les villes de la plateforme :");
        for (String ville : villes) {
            System.out.println("Ville : " + ville);
            System.out.println("Structures associées :");
            for (Structure structure : structures) {
                if (structure.getVille().equalsIgnoreCase(ville)) {
                    System.out.println(structure);
                }
            }
            System.out.println(); // Ajout d'une ligne vide entre chaque ville
        }
    }

    /**
     * Affiche toutes les villes de la plateforme.
     */
    public void showAllVilles() {
        System.out.println("Toutes les villes de la plateforme :");
        for (String ville : villes) {
            System.out.println(ville);
        }
    }

    /**
     * Affiche toutes les villes ayant des structures de transport de type spécifié.
     *
     * @param modalite La modalité de transport dont on souhaite afficher les villes.
     */
    public void showAllVillesWithTransport(ModaliteTransport modalite) {
        System.out.println("Villes avec des structures de transport de type " + modalite + " :");
        for (Structure structure : structures) {
            if (structure.getModalite() == modalite) {
                System.out.println(structure.getVille());
            }
        }
    }

    /**
     * Affiche toutes les structures ayant la modalité de transport spécifiée.
     *
     * @param modalite La modalité de transport dont on souhaite afficher les structures.
     */
    public void showAllStructuresWithTransport(ModaliteTransport modalite) {
        System.out.println("Structures avec la modalité de transport de type " + modalite + " :");
        for (Structure structure : structures) {
            if (structure.getModalite() == modalite) {
                System.out.println(structure);
            }
        }
    }

    
    


    // TOSTRING :

    /**
     * Renvoie une chaîne de caractères contenant toutes les structures de la ville spécifiée.
     *
     * @param ville Le nom de la ville dont on souhaite obtenir les structures.
     * @return Une chaîne de caractères contenant les structures de la ville.
     */
    public String getAllStructuresOf(String ville) {
        StringBuilder result = new StringBuilder();
        result.append("Structures de la ville ").append(ville).append(" :\n");
        for (Structure structure : structures) {
            if (structure.getVille().equalsIgnoreCase(ville)) {
                result.append(structure).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Renvoie une chaîne de caractères contenant toutes les structures de chaque ville de la plateforme.
     *
     * @return Une chaîne de caractères contenant les structures de chaque ville.
     */
    public String getAllStructures() {
        StringBuilder result = new StringBuilder();
        result.append("Toutes les structures de chaque ville de la plateforme :\n");
        for (String ville : villes) {
            result.append("Ville : ").append(ville).append("\n");
            result.append("Structures associées :\n");
            for (Structure structure : structures) {
                if (structure.getVille().equalsIgnoreCase(ville)) {
                    result.append(structure).append("\n");
                }
            }
            result.append("\n"); // Ajout d'une ligne vide entre chaque ville
        }
        return result.toString();
    }

    /**
     * Renvoie une chaîne de caractères contenant toutes les villes de la plateforme.
     *
     * @return Une chaîne de caractères contenant toutes les villes.
     */
    public String getAllVilles() {
        StringBuilder result = new StringBuilder();
        result.append("Toutes les villes de la plateforme :\n");
        for (String ville : villes) {
            result.append(ville).append("\n");
        }
        return result.toString();
    }

    /**
     * Renvoie une chaîne de caractères contenant toutes les villes ayant des structures de transport de la modalité spécifiée.
     *
     * @param modalite La modalité de transport dont on souhaite obtenir les villes.
     * @return Une chaîne de caractères contenant les villes avec la modalité de transport spécifiée.
     */
    public String getVillesWithTransport(ModaliteTransport modalite) {
        StringBuilder result = new StringBuilder();
        result.append("Villes avec des structures de transport de type ").append(modalite).append(" :\n");
        for (Structure structure : structures) {
            if (structure.getModalite() == modalite) {
                result.append(structure.getVille()).append("\n");
            }
        }
        return result.toString();
    }

    /**
     * Renvoie une chaîne de caractères contenant toutes les structures ayant la modalité de transport spécifiée.
     *
     * @param modalite La modalité de transport dont on souhaite obtenir les structures.
     * @return Une chaîne de caractères contenant les structures avec la modalité de transport spécifiée.
     */
    public String getStructuresWithTransport(ModaliteTransport modalite) {
        StringBuilder result = new StringBuilder();
        result.append("Structures avec la modalité de transport de type ").append(modalite).append(" :\n");
        for (Structure structure : structures) {
            if (structure.getModalite() == modalite) {
                result.append(structure).append("\n");
            }
        }
        return result.toString();
    }


    /**
     * Renvoie une représentation textuelle de la plateforme sous forme de chaîne de caractères.
     * @return Une représentation textuelle de la plateforme.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Structures:\n");
        for (String ville : villes) {
            sb.append(ville).append("\n");
        }
    
        sb.append("Structures:\n");
        for (Structure structure : structures) {
            sb.append(structure).append("\n");
        }
    
        sb.append("\nAretes:\n");
        for (Arete arete : aretes) {
            sb.append(arete).append("\n");
        }
    
        return sb.toString();
    }
}
    
