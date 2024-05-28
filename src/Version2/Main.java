package version2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import fr.ulille.but.sae_s2_2024.Chemin;

public class Main {

    private static Plateforme p;
    
    // TODO csv avec les infos users (version 3)
    private static Voyageur user;
    private static Scanner scanner = new Scanner(System.in);

    // méthode pour "effacer" ce qu'il y a sur le terminale ( plus beau pour les yeux)
    public static void clear(){
        // Code pour effacer le terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    

    // méthode qui crée la plateforme
    public static void createPlateforme() {
        // Création de la plateforme p
        p = new Plateforme();
        // System.out.println(p); 
    }

    public static void createUser() {
        System.out.println("Bonjour et bienvenue sur Woze, votre Plateforme de Comparaison d’itinéraires de transport.\n");

        // prenom
        System.out.println("Quel est votre Prénom ?");
        String prenom = scanner.nextLine();

        // nom
        System.out.println("Quel est votre Nom ?");
        String nom = scanner.nextLine();



        // // ville ( supprimer pour le moment)
        // clear();
        // System.out.println("De quelle ville venez-vous ?");
        // System.out.println(p.getVilles());
        // String ville = scanner.nextLine();
        // String villeDepart;
        // while(!p.containsVille(ville)) {
        //     System.out.println("Cette ville n'existe pas. Aucune ville ne vous sera associé.");
        //     System.out.println(p.getVilles());
        //      ville = scanner.nextLine();
        // }
        // villeDepart = ville;
        String villeDepart = "Aucune";

        // critere
        clear();
        System.out.println("Quel critère de recherche d'itinéraire vous semble le plus adapté ? (Temps/Co2/Prix)");
        String critere = scanner.nextLine().toUpperCase();
        System.out.println(critere);
        while(!Verification.estCritereValide(critere)){
            System.out.println("Critère invalide. (Temps/Co2/Prix)");
            critere = scanner.nextLine().toUpperCase();
        };
        TypeCout crit = TypeCout.valueOf(critere);
    
        // user
        clear();
        user = new Voyageur(prenom, nom, villeDepart, crit);
        p.addUser(user); // graph creer par la meme occasion
        p.setCurrentUser(user);

    }

    public static void menu() {
        int choix;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Voir le graph");
            System.out.println("2. Chercher un chemin");
            System.out.println("3. Mes informations");
            System.out.println("4. Changer d'utilisateur");
            System.out.println("5. Quitter");
            System.out.print("Votre choix: ");
            choix = Verification.getValidIntInput(scanner);
            clear();
    
            switch (choix) {
                case 1:
                    // affichage du graphe 
                    voirGraph();
                    break;
                case 2:
                    // algo kpcc
                    chercherChemin();
                    break;
                case 3:
                    // info user 
                    afficherInfosUtilisateur();
                    break;
                case 4:
                    // Changer d'utilisateur
                    changerUtilisateur();
                    break;
                case 5:
                    // quitter 
                    System.out.println("Au revoir !");
                    break;
                default:
                    // choix invalide 
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 5);
    }

    public static void changerUtilisateur() {
        System.out.println("Êtes-vous sûr de vouloir changer d'utilisateur ? (Oui/Non)");
        String confirmation = scanner.nextLine().toUpperCase();
        if (confirmation.equals("OUI") || confirmation.equals("O")) {
            chooseUser(); // Si l'utilisateur confirme, appelle la méthode chooseUser() pour changer d'utilisateur
        } else if (confirmation.equals("NON") || confirmation.equals("N")) {
            // Si l'utilisateur ne confirme pas, retourne simplement au menu
            System.out.println("Changement d'utilisateur annulé.");
        } else {
            System.out.println("Choix invalide. Retour au menu principal.");
        }
    }

    // affiche le graphe 
    public static void voirGraph() {
        System.out.println(p.getCurrentGraphe());
    }

    public static void chercherChemin() {
        // Départ
        // System.out.println("De quelle ville souhaitez-vous partir ?");
        // for (int i = 0; i < p.getVilles().size(); i++) {
        //     System.out.println((i + 1) + ". " + p.getVilles().get(i));
        // }
        // int choixVilleDepart = Verification.getValidIntInput(scanner) - 1;
        // String villeDepart = p.getVilles().get(choixVilleDepart);
    
        // System.out.println(p.getAllStructuresOf(villeDepart));
        System.out.println("De quelle structure souhaitez-vous partir ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i));
        }
        int choixStructureDepart = Verification.getValidIntInput(scanner) - 1;
        String structureDepart = p.getStructures().get(choixStructureDepart).getNom();
    
        // Arrivée
        // clear();
        // System.out.println("Quelle est votre destination ?");
        // for (int i = 0; i < p.getVilles().size(); i++) {
        //     System.out.println((i + 1) + ". " + p.getVilles().get(i));
        // }
        // int choixVilleArrivee = Verification.getValidIntInput(scanner) - 1;
        // String villeArrivee = p.getVilles().get(choixVilleArrivee);
    
        // System.out.println(p.getAllStructuresOf(villeArrivee));
        System.out.println("À quelle structure souhaitez-vous arriver ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i));
        }
        int choixStructureArrivee = Verification.getValidIntInput(scanner) - 1;
        String structureArrivee = p.getStructures().get(choixStructureArrivee).getNom();
    
        Structure depart = p.getStructure(structureDepart);
        Structure arrivee = p.getStructure(structureArrivee);
    
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        int k = Verification.getValidIntInput(scanner);
    
        System.out.println("Quel poids ne doit pas excéder le trajet ?");
        double poids_max = Verification.getValidDoubleInput(scanner);
    
        // Chemin
        clear();
        List<Chemin> chemins = p.simplePCC(depart, arrivee, user.getCritere(), k);
        List<String> chemins_max = new ArrayList<>();
        for (Chemin chemin : chemins) {
            String poidsString = chemin.toString().split("Poids: ")[1].replace(',', '.').replace(')', '0');
    
            double poidsChemin = Double.parseDouble(poidsString);
    
            if (poidsChemin <= poids_max) {
                chemins_max.add(chemin.toString());
            }
        }
    
        if (chemins_max.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + structureDepart + " à " + structureArrivee + " selon le critère " + user.getCritere() + ".");
        } else if (chemins_max.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + structureDepart + " à " + structureArrivee + " selon le critère " + user.getCritere() + ":");
        } else {
            System.out.println("Les " + chemins_max.size() + " plus courts chemins trouvés de " + structureDepart + " à " + structureArrivee + " selon le critère " + user.getCritere() + " sont :");
        }
    
        for (String chemin : chemins_max) {
            System.out.println(chemin); // tous les chemins trouvé 
        }
    }
    
    public static void supprimerDonneesUtilisateur() {
        System.out.println("Êtes-vous sûr de vouloir supprimer vos données ? (Tapez 'CONFIRMATION' pour confirmer)");
        String confirmation = scanner.nextLine().toUpperCase();
    
        if (confirmation.equals("CONFIRMATION")) {
            p.delUser(user); // supp l'utilisateur de la plateforme
            user = null;
            System.out.println("Vos données ont été supprimées.");
            chooseUser(); // choix d'un nouvel utilisateur ou création d'un nouveau
        } else {
            System.out.println("Suppression annulée.");
        }
    }
    

    public static void afficherInfosUtilisateur() {
        System.out.println("Informations utilisateur:");
        System.out.println(user);
        
        while (true) {
            System.out.println("\nQue souhaitez-vous modifier ?");
            System.out.println("1. Nom");
            System.out.println("2. Prénom");
            System.out.println("3. Ville");
            System.out.println("4. Critère");
            System.out.println("5. Supprimer mes données");
            System.out.println("6. Retour au menu principal");
            System.out.print("Choisissez une option: ");
            
            int choix = Verification.getValidIntInput(scanner);
    
            switch (choix) {
                case 1:
                    // Nom
                     clear();    
                    System.out.print("Entrez le nouveau nom: ");
                    String nouveauNom = scanner.nextLine();
                    user.setNom(nouveauNom);
                    break;
                case 2:
                    // Prénom
                    clear();
                    System.out.print("Entrez le nouveau prénom: ");
                    String nouveauPrenom = scanner.nextLine();
                    user.setPrenom(nouveauPrenom);
                    break;
                case 3:
                    // ville de référence note : supprimé au lancement donc initialement c'est établit à : "Aucune"
                    clear();
                    System.out.print("Entrez la nouvelle ville: ");
                    String nouvelleVille = scanner.nextLine().toUpperCase();
                    if (p.containsVille(nouvelleVille)) {
                        user.setVille(nouvelleVille);
                    } else {
                        System.out.println("Cette ville n'existe pas. Modification annulée.");
                    }
                    break;
                case 4:
                    // Critère (TEMPS, PRIX, CO2)
                    clear();
                    System.out.print("Entrez le nouveau critère (TEMPS, PRIX, CO2): ");
                    String nouveauCritere = scanner.nextLine().toUpperCase();
                    try {
                        user.setCritere(TypeCout.valueOf(nouveauCritere));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Critère invalide. Modification annulée.");
                    }
                    break;
                case 5:
                    // Supprimer les données utilisateur
                    clear();
                    supprimerDonneesUtilisateur();
                    break;
                case 6:
                    clear();
                    return; // Retour au menu principal
                default:   
                    // Cas où on rentre un autre int
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
    
            // Affichage des informations mises à jour
            System.out.println("\nInformations mises à jour:");
            System.out.println(user);
        }
    }
    

    // méthode qui verifie les données 
    public static boolean verif(String [] datav, String [] datac) {
        Verification verificationData = new Verification();
        // Vérifier la validité des données
        boolean isValid = verificationData.dataIsValid(datav ) &&  verificationData.correspondanceIsValid(datac);
        System.out.println("Toutes les données sont valides : " + isValid + '\n');
        return isValid;
    }


    public static void chooseUser() {
        if (p.getUsers().isEmpty()) {
            System.out.println("Aucun utilisateur trouvé. Création d'un nouvel utilisateur.");
            createUser();
        } else {
            System.out.println("Que souhaitez-vous faire ?");
            System.out.println("1. Choisir un utilisateur existant");
            System.out.println("2. Créer un nouvel utilisateur");
            
            int choix = Verification.getValidIntInput(scanner);
            while (choix < 1 || choix > 2) {
                System.out.println("Choix invalide, veuillez réessayer.");
                choix = Verification.getValidIntInput(scanner);
            }
            
            if (choix == 1) {
                System.out.println("Choisissez un utilisateur existant :");
                for (int i = 0; i < p.getUsers().size(); i++) {
                    System.out.println((i + 1) + ". " + p.getUsers().get(i).getNom() + " " + p.getUsers().get(i).getPrenom());
                }
        
                int choixUser = Verification.getValidIntInput(scanner);
                while (choixUser < 1 || choixUser > p.getUsers().size()) {
                    System.out.println("Choix invalide, veuillez réessayer.");
                    choixUser = Verification.getValidIntInput(scanner);
                }
        
                user = p.getUsers().get(choixUser - 1);
                System.out.println("Utilisateur sélectionné : " + user);
                p.setCurrentUser(user);
            } else if (choix == 2) {
                createUser();
            }
        }
    }
    
    

    public static void main(String[] args) {
        if (verif(DataExtractor.data_villes, DataExtractor.data_correspondances)) {
            // Création de la plateforme
            createPlateforme();
            chooseUser();
            menu();
        }
    }
        

}
