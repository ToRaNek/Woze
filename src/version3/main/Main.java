package version3.main;

import java.util.List;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.Chemin;
import version3.user.Voyageur;
import version3.graphe.Algorithme;
import version3.graphe.Plateforme;
import version3.graphe.Structure;
import version3.utils.data.extract.VilleDataExtractor;
import version3.utils.verifications.Verifications;
import version3.graphe.TypeCout;

public class Main {
    
    private static Plateforme p;
    private static Voyageur user;
    private static Scanner scanner = new Scanner(System.in);

    public static void clearTerminal(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static void createPlateforme() {
        p = new Plateforme();
    }

    public static void createUser() {
        System.out.println("Bonjour et bienvenue sur Woze, votre Plateforme de Comparaison d’itinéraires de transport.\n");

        System.out.println("Quel est votre Prénom ?");
        final String prenom = scanner.nextLine();

        System.out.println("Quel est votre Nom ?");
        final String nom = scanner.nextLine();

        final String villeDepart = "Aucune";

        clearTerminal();
        System.out.println("Quel critère de recherche d'itinéraire vous semble le plus adapté ? (Temps/Co2/Prix)");
        String critere = scanner.nextLine().toUpperCase();
        while(!Verifications.estCritereValide(critere)){
            System.out.println("Critère invalide. (Temps/Co2/Prix)");
            critere = scanner.nextLine().toUpperCase();
        };
        final TypeCout crit = TypeCout.valueOf(critere);
    
        clearTerminal();
        user = new Voyageur(prenom, nom, villeDepart, crit);
        p.addUser(user);
        p.setCurrentUser(user);
    }

    public static void displayMenu() {
        int choice;
        do {
            System.out.println("\nMenu:");
            System.out.println("1. Voir le graphe");
            System.out.println("2. Chercher un chemin");
            System.out.println("3. Chercher un chemin avec un poids");
            System.out.println("4. Mes informations");
            System.out.println("5. Changer d'utilisateur");
            System.out.println("6. Quitter");
            System.out.print("Votre choix: ");
            choice = Verifications.getValidIntInput(scanner);
            clearTerminal();
    
            switch (choice) {
                case 1:
                    displayGraph();
                    break;
                case 2:
                    searchPath();
                    break;
                case 3:
                    searchPathWithWeight();
                    break;
                case 4:
                    displayUserInfo();
                    break;
                case 5:
                    changeUser();
                    break;
                case 6:
                    scanner.close();
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choice != 6);
    }
    

    public static void changeUser() {
        System.out.println("Êtes-vous sûr de vouloir changer d'utilisateur ? (Oui/Non)");
        final String confirmation = scanner.nextLine().toUpperCase();
        if ("OUI".equals(confirmation) || "O".equals(confirmation)) {
            chooseUser();
        } else if ("NON".equals(confirmation) || "N".equals(confirmation)) {
            System.out.println("Changement d'utilisateur annulé.");
        } else {
            System.out.println("Choix invalide. Retour au menu principal.");
        }
    }

    public static void displayGraph() {
        System.out.println(p.getCurrentGraphe());
    }

    public static void searchPath() {
        if (p == null || user == null) {
            System.out.println("Veuillez d'abord créer une plateforme et un utilisateur.");
            return;
        }

        clearTerminal();

        // Départ
        System.out.println("De quelle structure souhaitez-vous partir ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureDepart = Verifications.getValidIntInput(scanner) - 1;
        final Structure depart = p.getStructures().get(choixStructureDepart);

        // Arrivée
        System.out.println("À quelle structure souhaitez-vous arriver ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureArrivee = Verifications.getValidIntInput(scanner) - 1;
        final Structure arrivee = p.getStructures().get(choixStructureArrivee);

        // Nombre de chemins à trouver
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        final int k = Verifications.getValidIntInput(scanner);


        // Recherche des chemins
        List<Chemin> chemins = Algorithme.simplePCC(p.getCurrentGraphe(), depart, arrivee, k);
        chemins = Plateforme.reductionAffichageChemins(chemins);

        // Affichage des résultats
        if (chemins.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + ".");
        } else if (chemins.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + ":");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        } else {
            System.out.println("Les " + chemins.size() + " plus courts chemins trouvés de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + " sont :");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        }
    }

    public static void searchPathWithWeight() {
        if (p == null || user == null) {
            System.out.println("Veuillez d'abord créer une plateforme et un utilisateur.");
            return;
        }
    
        clearTerminal();
    
        // Départ
        System.out.println("De quelle structure souhaitez-vous partir ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureDepart = Verifications.getValidIntInput(scanner) - 1;
        final Structure depart = p.getStructures().get(choixStructureDepart);
    
        // Arrivée
        System.out.println("À quelle structure souhaitez-vous arriver ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i).getNom());
        }
        final int choixStructureArrivee = Verifications.getValidIntInput(scanner) - 1;
        final Structure arrivee = p.getStructures().get(choixStructureArrivee);

        // Nombre de chemins à trouver
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        final int k = Verifications.getValidIntInput(scanner);
        
    
        // Poids spécifique du trajet
        System.out.println("Quel poids ne doit pas excéder le trajet ?");
        final double poidsMax = Verifications.getValidDoubleInput(scanner);
    
        // Deuxième critère
        System.out.println("Quel est le deuxième critère (temps, prix ou CO2) ?");
        final String critere2 = scanner.next();
        final TypeCout critere2Enum = TypeCout.valueOf(critere2.toUpperCase());
        clearTerminal();

    
        // Recherche des chemins
        List<Chemin> chemins = Algorithme.KPlusCourtsChemins(p, depart, arrivee, user.getCritere(), k, critere2Enum, poidsMax);
        clearTerminal();
        chemins = Plateforme.reductionAffichageChemins(chemins);
    
        // Affichage des résultats
        if (chemins.isEmpty()) {
            clearTerminal();
            System.out.println("Aucun chemin trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + " et le poids spécifié.");
        } else {
            clearTerminal();
            System.out.println("Chemin trouvé de " + depart.getNom() + " à " + arrivee.getNom() + " selon le critère " + user.getCritere() + " et le poids spécifié:");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        }
    }
    

    public static void deleteUserData() {
        System.out.println("Êtes-vous sûr de vouloir supprimer vos données ? (Tapez 'CONFIRMATION' pour confirmer)");
        final String confirmation = scanner.nextLine().toUpperCase();
    
        if ("CONFIRMATION".equals(confirmation)) {
            p.delUser(user); // supp l'utilisateur de la plateforme
            user = null;
            System.out.println("Vos données ont été supprimées.");
            chooseUser(); // choix d'un nouvel utilisateur ou création d'un nouveau
        } else {
            System.out.println("Suppression annulée.");
        }
    }

    public static void displayUserInfo() {
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
            
            final int choix = Verifications.getValidIntInput(scanner);
            boolean verif = true;
            switch (choix) {
                case 1:
                    // Nom
                     clearTerminal();    
                    System.out.print("Entrez le nouveau nom: ");
                    final String nouveauNom = scanner.nextLine();
                    user.setNom(nouveauNom);
                    break;
                case 2:
                    // Prénom
                    clearTerminal();
                    System.out.print("Entrez le nouveau prénom: ");
                    final String nouveauPrenom = scanner.nextLine();
                    user.setPrenom(nouveauPrenom);
                    break;
                case 3:
                    // ville de référence note : supprimé au lancement
                    // donc initialement c'est établit à : "Aucune"
                    clearTerminal();
                    while (verif) {
                        System.out.print("Entrez la nouvelle ville: ");
                        final String nouvelleVille = scanner.nextLine().toUpperCase();
                        if (p.containsVille(nouvelleVille)) {
                            user.setVille(nouvelleVille);
                            verif = false;
                        } else {
                            System.out.println("Cette ville n'existe pas.");
                        }
                    }

                    break;
                case 4:
                    // Critère (TEMPS, PRIX, CO2)
                    clearTerminal();
                    while (verif) {
                        System.out.print("Entrez le nouveau critère (TEMPS, PRIX, CO2): ");
                        final String nouveauCritere = scanner.nextLine().toUpperCase();
                        try {
                            user.setCritere(TypeCout.valueOf(nouveauCritere));
                            verif = false;
                        } catch (IllegalArgumentException e) {
                            System.out.println("Critère invalide.");
                        }
                    }
                   
                    break;
                case 5:
                    // Supprimer les données utilisateur
                    clearTerminal();
                    deleteUserData();
                    break;
                case 6:
                    clearTerminal();
                    return; // Retour au menu principal
                default:   
                    // Cas où on rentre un autre int
                    System.out.println("Option invalide. Veuillez réessayer.");
            }
    
            // Affichage des informations mises à jour
            System.out.println("\nInformations mises à jour:");
            System.out.println(user);
        }    }

    public static boolean verifyData(String [] datav) {
        final Verifications verificationsData = new Verifications();
        // Vérifier la validité des données
        final boolean isValid = verificationsData.dataIsValid(datav );
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
            
            int choix = Verifications.getValidIntInput(scanner);
            while (choix < 1 || choix > 2) {
                System.out.println("Choix invalide, veuillez réessayer.");
                choix = Verifications.getValidIntInput(scanner);
            }
            
            if (choix == 1) {
                System.out.println("Choisissez un utilisateur existant :");
                for (int i = 0; i < p.getUsers().size(); i++) {
                    System.out.println((i + 1) + ". " + p.getUsers().get(i).getNom() + " " + p.getUsers().get(i).getPrenom());
                }
        
                int choixUser = Verifications.getValidIntInput(scanner);
                while (choixUser < 1 || choixUser > p.getUsers().size()) {
                    System.out.println("Choix invalide, veuillez réessayer.");
                    choixUser = Verifications.getValidIntInput(scanner);
                }
        
                user = p.getUsers().get(choixUser - 1);
                System.out.println("Utilisateur sélectionné : " + user);
                p.setCurrentUser(user);
            } else if (choix == 2) {
                createUser();
            }
        }    }

    public static void main(String[] args) {
        if (verifyData(VilleDataExtractor.data_villes)) {
            // Création de la plateforme
            createPlateforme();

            // Choix de l'utilisateur
            chooseUser();

            displayMenu();
        }

        
    }
}
