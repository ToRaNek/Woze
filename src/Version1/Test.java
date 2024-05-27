package version1;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
// classe de test
public class Test {
    private static Plateforme p;
    private static String[] data = new String[]{
        "Lille;Paris;Train;45;1.7;80",         
        "Lille;Paris;Avion;120;2.8;60",         
        "Lille;Paris;Bus;80;1.2;180",          
        "Lille;Marseille;Avion;150;3.5;90",    
        "Lille;Marseille;Train;30;3.5;90",    
        "Paris;Marseille;Train;10;2.2;120",     
        "Paris;Marseille;Avion;180;3.2;70",    
        "Paris;Marseille;Bus;230;1.3;660",      
        "Marseille;Paris;Train;85;2.5;110"      
    };
    private static Voyageur user;

    public static boolean verif() {
        VerificationData verificationData = new VerificationData();
        // Vérifier la validité des données
        boolean isValid = verificationData.dataIsValid(data);
        System.out.println("Toutes les données sont valides : " + isValid + '\n');
        return isValid;
    }
     public static void createPlateforme() {
        p = new Plateforme(data);
    }
    public static void createUser() {

        String prenom = "Gasbrion";

        String nom = "Cadelle";

        String villeDepart = "Aucune";

        TypeCout crit = TypeCout.PRIX; // critere prix 

        // voyageur
        user = new Voyageur(prenom, nom, villeDepart, crit);
        System.out.println(user.toString()+'\n');

        // graphe
        p.buildGraph(crit);
        System.out.println(p.getCurrentGraphe().toString()+'\n');

    }
    public static void chercherChemin(ModaliteTransport modalite,double poids_max ) {
        String villeDepart = "Lille";
        String villeArrivee = "Paris";

        Structure depart = p.getStructure(Structure.nom(villeDepart, modalite));
        Structure arrivee = p.getStructure(Structure.nom(villeArrivee, modalite));
        
        int k = 2; // nb trajet à afficher

        List<Chemin> chemins = p.chercherPlusCourtsChemins(depart, arrivee, user.getCritere(), k);

        List<String> chemins_max = new ArrayList<>();
        for (Chemin chemin : chemins) {
            String poidsString = chemin.toString().split("Poids: ")[1].replace(',', '.').replace(')', '0');

            double poidsChemin = Double.parseDouble(poidsString);
        
            if (poidsChemin <= poids_max) {
                chemins_max.add(chemin.toString());
            }
        }
        
    
        if (chemins_max.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + modalite.name() + ".");
        } else if (chemins_max.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + modalite.name() + ":");
        } else {
            System.out.println("Les " + chemins_max.size() + " plus courts chemins trouvés de " + villeDepart + " à " + villeArrivee + " selon le critère " + user.getCritere() + " et le moyen de transport " + modalite.name() + " sont :");
        }
        
        for (String chemin : chemins_max) {
            System.out.println(chemin); // Affiche chaque chemin trouvé 
        }
        System.out.println('\n');

    }

    
    public static void main(String[] args) {
        if (verif()) {
            // Création de la plateforme
            createPlateforme();
            createUser();
            chercherChemin(ModaliteTransport.BUS, 100);
            chercherChemin(ModaliteTransport.TRAIN, 300);
            chercherChemin(ModaliteTransport.AVION, 300);
        }
    }
        // V1 finis


}
