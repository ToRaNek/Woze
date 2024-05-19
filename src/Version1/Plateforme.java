package Version1;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.*;

/**
 * La classe Plateforme représente une plateforme de transport.
 */
public class Plateforme {

    private ArrayList<Arete> aretes;
    private MultiGrapheOrienteValue currentGraphe;
    private TypeCout currentCrit;
    private ArrayList<String> villes;
    private ArrayList<Structure> structures;
    
    /**
     * Constructeur de la classe Plateforme.
     * @param data Les données pour initialiser la plateforme.
     */
    public Plateforme(String[] data) {
        structures = new ArrayList<>();
        aretes = new ArrayList<>();
        villes = new ArrayList<>();

        for (String entree : data) {
            String[] split = entree.split(";");

            ModaliteTransport modalite = ModaliteTransport.valueOf(split[2].toUpperCase());

            String ville_depart = split[0];
            String ville_arrivee = split[1];

            Structure depart, arrivee;

            // DEPART
            depart = createOrGetStructure(ville_depart, modalite);
            // ARRIVEE
            arrivee = createOrGetStructure(ville_arrivee, modalite);
            // COUTS
            double prix = Math.round(Double.parseDouble(split[3])*100)/100;
            double co2 = Math.round(Double.parseDouble(split[4])*100)/100;
            double temps = Math.round(Double.parseDouble(split[5])*100)/100;

            // ARETES ALLEE - RETOUR
            Arete allee = new Arete(depart, arrivee, modalite,prix, co2, temps);
            add2Arete(allee);
        }
    }

    /**
     * Ajoute une arête à la liste des arêtes de la plateforme.
     * @param arete L'arête à ajouter.
     */
    public void add1Arete(Arete arete) {
        aretes.add(arete);
    }

    /**
     * Ajoute une arête à la liste des arêtes de la plateforme, ainsi que son arête retour.
     * 
     * @param arete L'arête à ajouter.
     */
    public void add2Arete(Arete arete) {
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
    public boolean addVille(String ville) {
        return villes.add(ville);
    }

    /**
     * Vérifie si la plateforme contient une ville donnée.
     * 
     * @param ville Le nom de la ville à rechercher.
     * @return true si la ville est présente dans la plateforme, sinon false.
     */
    public boolean containsVille(String ville) {
        for (String v : villes) {
            if (v.equals(ville)) {
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
    public boolean existVilleStructureVersion(String ville) {
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
    public void addStructure(Structure structure) {
        structures.add(structure);
    }

    /**
     * Crée une nouvelle structure associée à une ville et à une modalité de transport spécifiques s'il n'existe pas déjà, sinon renvoie la structure existante.
     * 
     * @param ville La ville associée à la structure.
     * @param modalite La modalité de transport associée à la structure.
     * @return La structure créée ou existante.
     */
    public Structure createOrGetStructure(String ville, ModaliteTransport modalite) {
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
    public Structure getStructure(String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return structure;
            }
        }
        return null;
    }

    /**
     * Obtient la liste des arêtes de la plateforme.
     * @return La liste des arêtes.
     */
    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    /**
     * Retourne l'index de l'arête spécifiée dans la liste des arêtes de la plateforme.
     * 
     * @param arete L'arête dont on souhaite obtenir l'index.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(Arete arete) {
        return aretes.indexOf(arete);
    }

    /**
     * Retourne l'index de l'arête spécifiée par les noms de ses structures de départ et d'arrivée ainsi que la modalité de transport dans la liste des arêtes de la plateforme.
     * 
     * @param depart Le nom de la structure de départ de l'arête.
     * @param arrivee Le nom de la structure d'arrivée de l'arête.
     * @param modalite Le nom de la modalité de transport de l'arête.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(String depart, String arrivee, String modalite) {
        ModaliteTransport m = ModaliteTransport.valueOf(modalite.toUpperCase());
        Structure d = getStructure(Structure.nom(depart,m));
        Structure a = getStructure(Structure.nom(arrivee,m));
        return indexOf(d, a, m);
    }

    /**
     * Retourne l'index de l'arête spécifiée par ses structures de départ et d'arrivée ainsi que la modalité de transport dans la liste des arêtes de la plateforme.
     * 
     * @param depart La structure de départ de l'arête.
     * @param arrivee La structure d'arrivée de l'arête.
     * @param modalite La modalité de transport de l'arête.
     * @return L'index de l'arête dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(Structure depart, Structure arrivee, ModaliteTransport modalite) {
        int res = -1;
        for (Arete arete : aretes) {
            if (arete.getDepart() == depart && arete.getArrivee() == arrivee && arete.getModalite() == modalite) {
                res = indexOf(arete);
            }
        }
        return res;
    }

    /**
     * Retourne l'index de la structure spécifiée dans la liste des structures de la plateforme.
     * 
     * @param structure La structure dont on souhaite obtenir l'index.
     * @return L'index de la structure dans la liste, ou -1 si elle n'est pas présente.
     */
    public int indexOf(Structure structure) {
        return structures.indexOf(structure);
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
    public void removeArete(Arete arete) {
        aretes.remove(arete);
    }

    /**
     * Supprime l'arête à l'index spécifié de la liste des arêtes de la plateforme.
     * 
     * @param index L'index de l'arête à supprimer.
     */
    public void removeArete(int index) {
        removeArete(aretes.get(index));
    }

    /**
     * Supprime la structure spécifiée de la liste des structures de la plateforme.
     * 
     * @param structure La structure à supprimer.
     */
    public void removeStructure(Structure structure) {
        structures.remove(structure);
    }

    /**
     * Supprime la structure à l'index spécifié de la liste des structures de la plateforme.
     * 
     * @param index L'index de la structure à supprimer.
     */
    public void removeStructure(int index) {
        removeStructure(structures.get(index));
    }

    /**
     * Obtient le graphe actuellement utilisé par la plateforme.
     * 
     * @return Le graphe actuel utilisé par la plateforme.
     */
    public MultiGrapheOrienteValue getCurrentGraphe() {
        return currentGraphe;
    }

    /**
     * Définit le graphe à utiliser pour la plateforme.
     * 
     * @param currentGraphe Le graphe à utiliser pour la plateforme.
     */
    public void setCurrentGraphe(MultiGrapheOrienteValue currentGraphe) {
        this.currentGraphe = currentGraphe;
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
     * Définit le critère actuellement utilisé par la plateforme.
     * 
     * @param currentCrit Le critère à utiliser pour la plateforme.
     */
    public void setCurrentCrit(TypeCout currentCrit) {
        this.currentCrit = currentCrit;
    }

    /**
     * Construit un graphe à partir des structures et arêtes de la plateforme en utilisant le critère spécifié.
     * 
     * @param critere Le critère à utiliser pour construire le graphe.
     * @return Le graphe construit.
     */
    public MultiGrapheOrienteValue buildGraph(TypeCout critere) {
        MultiGrapheOrienteValue g = new MultiGrapheOrienteValue();
        for (Structure structure : structures) {
            g.ajouterSommet(structure);
        }
        for (Arete arete : aretes) {
            g.ajouterArete(arete, arete.getCout(critere));
        }
        setCurrentGraphe(g);
        return g;
    }

    /**
     * Construit un graphe à partir des structures et arêtes de la plateforme en utilisant le critère spécifié sous forme de chaîne de caractères.
     * 
     * @param critere Le critère à utiliser pour construire le graphe (sous forme de chaîne de caractères).
     * @return Le graphe construit.
     */
    public MultiGrapheOrienteValue buildGraph(String critere) {
        for (TypeCout value : TypeCout.values()) {
            if (TypeCout.valueOf(critere.toUpperCase()) == value) {
                return buildGraph(value);
            }
        }
        System.err.println("Le critère n'est pas valide");
        return null;
    }

    /**
     * Recherche les k plus courts chemins entre deux structures spécifiées dans le graphe donné.
     * 
     * @param graphe Le graphe dans lequel effectuer la recherche.
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param k Le nombre de chemins à trouver.
     * @return Une liste contenant les k plus courts chemins.
     */
    public static List<Chemin> chercherPlusCourtsChemins(MultiGrapheOrienteValue graphe, Structure depart, Structure arrivee, int k) {
        return AlgorithmeKPCC.kpcc(graphe, depart, arrivee, k);
    }

    /**
     * Recherche les k plus courts chemins entre deux structures spécifiées en utilisant le critère spécifié.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le critère à utiliser pour la recherche.
     * @param k Le nombre de chemins à trouver.
     * @return Une liste contenant les k plus courts chemins.
     */
    public List<Chemin> chercherPlusCourtsChemins(Structure depart, Structure arrivee, TypeCout crit, int k) {
        return AlgorithmeKPCC.kpcc(buildGraph(crit), depart, arrivee, k);
    }

    /**
     * Recherche les k plus courts chemins entre deux structures spécifiées en utilisant le critère spécifié sous forme de chaîne de caractères.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @param crit Le critère à utiliser pour la recherche (sous forme de chaîne de caractères).
     * @param k Le nombre de chemins à trouver.
     * @return Une liste contenant les k plus courts chemins.
     */
    public List<Chemin> chercherPlusCourtsChemins(Structure depart, Structure arrivee, String crit, int k) {
        return AlgorithmeKPCC.kpcc(buildGraph(crit), depart, arrivee, k);
    }

    /**
     * Vérifie si deux structures sont reliées dans le graphe actuel en utilisant le critère actuel.
     * 
     * @param depart La structure de départ.
     * @param arrivee La structure d'arrivée.
     * @return true si les structures sont reliées, false sinon.
     */
    public boolean isLinked(Structure depart, Structure arrivee) {
        if (depart == null || arrivee == null) {
            return false;
        }

        try {
            List<Chemin> chemins = chercherPlusCourtsChemins(currentGraphe, depart, arrivee, 1);
            return !chemins.isEmpty();
        } catch (IllegalArgumentException e) {
            System.out.println("Il n'existe pas de lien entre " + depart.getNom() + " et " + arrivee.getNom() + " par " + currentCrit);
            return false;
        }
    }

    /**
     * Obtient la liste des villes de la plateforme.
     * 
     * @return La liste des villes.
     */
    public ArrayList<String> getVilles() {
        return villes;
    }

    /**
     * Définit la liste des villes de la plateforme.
     * 
     * @param villes La liste des villes à définir.
     */
    public void setVilles(ArrayList<String> villes) {
        this.villes = villes;
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

    public void setAretes(ArrayList<Arete> aretes) {
        this.aretes = aretes;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }
    
}
    
