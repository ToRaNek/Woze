package version3.graphe;

import version3.utils.data.extract.VilleDataExtractor;
import version3.graphe.management.*;
import version3.user.management.UserManagement;
import version3.user.Voyageur;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import fr.ulille.but.sae_s2_2024.Trancon;

/**
 * La classe Plateforme représente une plateforme de transport.
 */
public class Plateforme {

    private Voyageur currentUser;
    private UserManagement users;
    private AreteManagement aretes;
    private VilleManagement villes;
    private StructureManagement structures;
    private TypeCout currentCrit = Voyageur.getCritereDefaut();
    private MultiGrapheOrienteValue currentGraphe;
    private HashMap<TypeCout, MultiGrapheOrienteValue> graphes;

    public Plateforme() {
        users = new UserManagement();
        aretes = new AreteManagement();
        villes = new VilleManagement();
        structures = new StructureManagement();
        graphes = new HashMap<>();

        initializeVilleData();
        initializeGraphs();
    }

    private void initializeVilleData() {
        for (final String data : VilleDataExtractor.data_villes) {
            final String[] split = data.split(";");
            final ModaliteTransport modalite = ModaliteTransport.valueOf(split[2].toUpperCase());
            final String villeDepart = split[0];
            final String villeArrivee = split[1];

            Structure depart = createOrGetStructure(villeDepart, modalite);
            Structure arrivee = createOrGetStructure(villeArrivee, modalite);

            final double prix = Math.round(Double.parseDouble(split[5]) * 100) / 100.0;
            final double co2 = Math.round(Double.parseDouble(split[4]) * 100) / 100.0;
            final double temps = Math.round(Double.parseDouble(split[3]) * 100) / 100.0;

            final Arete allee = new Arete(depart, arrivee, modalite, temps, co2, prix);
            aretes.addAreteWithReturn(allee);
        }
    }

    public Structure createOrGetStructure( String ville,  ModaliteTransport modalite) {
        return structures.createOrGetStructure(villes, ville, modalite);
    }

    private void initializeGraphs() {
        for (final TypeCout crit : TypeCout.values()) {
            final MultiGrapheOrienteValue g = buildGraph(crit.name());
            graphes.put(crit, g);
        }
        currentGraphe = graphes.get(Voyageur.getCritereDefaut());
    }

    public MultiGrapheOrienteValue buildGraph(final String critere) {
        MultiGrapheOrienteValue result = null;
        for (final TypeCout value : TypeCout.values()) {
            if (TypeCout.valueOf(critere.toUpperCase()) == value) {
                result = buildGraph(value);
            }
        }
        if (result == null) {
            System.err.println("Le critère n'est pas valide");
        }
        return result;
    }

    public MultiGrapheOrienteValue buildGraph(final TypeCout critere) {
        final MultiGrapheOrienteValue g = new MultiGrapheOrienteValue();
        for (final Structure structure : structures.getStructures()) {
            g.ajouterSommet(structure);
        }
        for (final Arete arete : aretes.getAretes()) {
            g.ajouterArete(arete, arete.getCout(critere));
        }
        currentGraphe = g;
        graphes.put(critere, g);
        return g;
    }
       
    // Méthode pour changer le critère actuel
    public void setCurrentCrit(TypeCout critere) {
        this.currentCrit = critere;
        // Re-construire le graphe avec le nouveau critère
        currentGraphe = graphes.get(critere);
    }

    // Méthode pour récupérer le critère actuel
    public TypeCout getCurrentCrit() {
        return currentCrit;
    }
    
    public void setCurrentGraph(TypeCout critere) {
        setCurrentCrit(critere);
    }

    // Méthode pour ajouter un utilisateur
    public void addUser(Voyageur user) {
        users.addUser(user);
    }

    // Méthode pour récupérer un utilisateur par son identifiant
    public Voyageur getUserById(int userId) {
        return users.getUserById(userId);
    }

    public Voyageur getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Voyageur currentUser) {
        this.currentUser = currentUser;
        this.currentCrit = currentUser.getCritere();
        graphes.get(currentCrit);
    }

    public ArrayList<Voyageur> getUsers() {
        return users.getUsers();
    }

    public void setUsers(UserManagement users) {
        this.users = users;
    }

    public ArrayList<Arete> getAretes() {
        return aretes.getAretes();
    }

    public Arete getArete(Structure struct1, Structure struct2){
        return aretes.getArete(currentGraphe, struct1, struct2);
    }

    public boolean isLinked(final Structure depart,final Structure arrivee) {
        boolean result = true;
        if (depart == null || arrivee == null) {
            result = false;
        }

        try {
            final List<Chemin> chemins = Algorithme.simplePCC(currentGraphe, depart, arrivee, 1);
            result = !chemins.isEmpty();
        } catch (IllegalArgumentException e) {
            System.out.println("Il n'existe pas de lien entre " + depart.getNom() + " et " + arrivee.getNom() + " pour " + currentCrit);
            result = false;
        }
        return result;
    }

    public void setAretes(AreteManagement aretes) {
        this.aretes = aretes;
    }

    public ArrayList<String> getVilles() {
        return villes.getVilles();
    }

    public void setVilles(VilleManagement villes) {
        this.villes = villes;
    }

    public ArrayList<Structure> getStructures() {
        return structures.getStructures();
    }

    public void setStructures(StructureManagement structures) {
        this.structures = structures;
    }

    public MultiGrapheOrienteValue getCurrentGraphe() {
        return currentGraphe;
    }

    public void setCurrentGraphe(MultiGrapheOrienteValue currentGraphe) {
        this.currentGraphe = currentGraphe;
    }

    public HashMap<TypeCout, MultiGrapheOrienteValue> getGraphes() {
        return graphes;
    }

    public void setGraphes(HashMap<TypeCout, MultiGrapheOrienteValue> graphes) {
        this.graphes = graphes;
    }

    public void addGrapheCritere(MultiGrapheOrienteValue g, TypeCout crit ) {
        graphes.put(crit, g);
    }

    public void delUser(final Voyageur user) {
        Voyageur.getIdTrash().add(user.getId());
        users.removeUser(user);
        currentUser=null;
    }

    public void add1Arete(final Arete arete) {
        aretes.addArete(arete);
    }

    public void add2Arete(final Arete arete) {
        // ajoute l'allee
        add1Arete(arete);
        
        // arête retour avec les villes inversées
        final Arete retour = new Arete(arete.getArrivee(), arete.getDepart(), arete.getModalite(), arete.getCouts());

        // ajoute l'arête retour
        add1Arete(retour);
    }

    public boolean addVille(final String ville) {
        return villes.addVille(ville);
    }
    
    public boolean containsVille( String ville) {
        return villes.containsVille(ville);
    }

    public boolean containsStructure(String structureNom) {
        return structures.contains(structureNom);
    }

    public Arete getArete(MultiGrapheOrienteValue graphe, Structure struct1, Structure struct2){
        return getArete(currentGraphe, struct1, struct2);
    }

    public boolean existVilleForAStructure(final String ville, StructureManagement structures) {
        return villes.existVilleForAStructure(ville, structures);
    }

    public void addStructure(final Structure structure) {
        structures.addStructure(structure);
    }

    public int indexOf(final Arete arete) {
        return aretes.indexOf(arete);
    }

    public int indexOf(final Structure structure) {
        return structures.indexOf(structure);
    }

    public int indexOf(final Structure depart,final Structure arrivee,final ModaliteTransport modalite) {
        return indexOf(depart, arrivee, modalite);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Structures:\n");
        for (final String ville : villes.getVilles()) {
            sb.append(ville).append("\n");
        }
    
        sb.append("Structures:\n");
        for (final Structure structure : structures.getStructures()) {
            sb.append(structure).append("\n");
        }
    
        sb.append("\nAretes:\n");
        for (final Arete arete : aretes.getAretes()) {
            sb.append(arete).append("\n");
        }
    
        return sb.toString();
    }

    /**
     * @param chemin Le chemin.
     * @return Réduit l'affichage du chemin.
     */
    public static Chemin reductionAffichageChemin(Chemin chemin) {
        final List<Trancon> delTrancon = new ArrayList<>();
        ModaliteTransport nextModalite;
        for (int idx = 1; idx < chemin.aretes().size()-2; idx++) {
            nextModalite = chemin.aretes().get(idx+1).getModalite();
            if (chemin.aretes().get(idx).getModalite() == nextModalite) {
                delTrancon.add(chemin.aretes().get(idx));
            } 
        }
        chemin.aretes().removeAll(delTrancon);
        System.out.println(delTrancon);
        return chemin;
    }

    /**
     * @param chemins La liste des chemin.
     * @return Réduit l'affiche des chemins d'une liste.
     */
    public static List<Chemin> reductionAffichageChemins(List<Chemin> chemins) {
        final List<Chemin> cheminsApresReduc = new ArrayList<Chemin>();
        for (final Chemin chemin : chemins) {
            cheminsApresReduc.add(reductionAffichageChemin(chemin));
        }
        return cheminsApresReduc;
    }

}