package version3.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import version3.user.Voyageur;
import version3.utils.algorithm.Algorithme;
import version3.graphe.Plateforme;
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
            System.out.println("3. Mes informations");
            System.out.println("4. Changer d'utilisateur");
            System.out.println("5. Quitter");
            System.out.print("Votre choix: ");
            choice = Verifications.getValidIntInput(scanner, 5);
            clearTerminal();
    
            switch (choice) {
                case 1:
                    displayGraph();
                    break;
                case 2:
                    searchPath();
                    break;
                case 3:
                    displayUserInfo();
                    break;
                case 4:
                    changeUser();
                    break;
                case 5:
                    scanner.close();
                    System.out.println("Au revoir !");
                    break;
                default:
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choice != 6);
    }


    public static void searchPath() {
        clearTerminal();

        // Affichage et sélection de la ville de départ
        System.out.println("De quelle ville souhaitez-vous partir ?");
        List<String> villes = p.getVilles();
        for (int i = 0; i < villes.size(); i++) {
            System.out.println((i + 1) + ". " + villes.get(i));
        }
        int choixVilleDepart = Verifications.getValidIntInput(scanner, p.getVilles().size()) - 1;
        String villeDepart = villes.get(choixVilleDepart);

        // Affichage et sélection de la ville d'arrivée
        System.out.println("À quelle ville souhaitez-vous arriver ?");
        for (int i = 0; i < villes.size(); i++) {
            System.out.println((i + 1) + ". " + villes.get(i));
        }
        int choixVilleArrivee = Verifications.getValidIntInput(scanner, p.getVilles().size()) - 1;
        String villeArrivee = villes.get(choixVilleArrivee);


        // Nombre de chemins à trouver
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        int k = Verifications.getValidIntInput(scanner);

        // Nombre de critères à utiliser
        System.out.println("Combien de critères souhaitez-vous utiliser (1, 2 ou 3) ?");
        int nombreDeCriteres = Verifications.getValidIntInput(scanner, 3);

        // Critères et poids
        Map<TypeCout, Double> poidsMaximaux = new HashMap<>();
        for (int i = 1; i <= nombreDeCriteres; i++) {
            TypeCout critere = null;
            double poids = Double.MAX_VALUE;

            switch (i) {
                case 1:
                    critere = user.getCritere(); // À adapter selon votre logique
                    System.out.println("Entrez le poids maximal pour le critère " + critere + ": ");
                    poids = Verifications.getValidDoubleInput(scanner);
                    poidsMaximaux.put(critere, poids);
                    break;

                case 2:
                    // Vérification et choix du deuxième critère
                    String choixCritere2 = "";
                    do {
                        System.out.println("Choisissez un deuxième critère (temps, prix, CO2): ");
                        for (TypeCout tc : TypeCout.values()) {
                            if (!poidsMaximaux.containsKey(tc)) {
                                System.out.println(tc);
                            }
                        }
                        choixCritere2 = scanner.next().toUpperCase();
                        if (!Verifications.estCritereValide(choixCritere2)) {
                            System.out.println("Critère invalide, veuillez saisir un critère valide (temps, prix, CO2).");
                        }
                    } while (!Verifications.estCritereValide(choixCritere2));

                    critere = TypeCout.valueOf(choixCritere2);
                    System.out.println("Entrez le poids maximal pour le critère " + critere + ": ");
                    poids = Verifications.getValidDoubleInput(scanner);
                    poidsMaximaux.put(critere, poids);
                    break;

                case 3:
                // Vérification et choix du troisième critère
                String choixCritere3;
                do {
                    System.out.println("Choisissez un troisième critère (temps, prix, CO2): ");
                    for (TypeCout tc : TypeCout.values()) {
                        if (!poidsMaximaux.containsKey(tc)) {
                            System.out.println(tc);
                        }
                    }
                    choixCritere3 = scanner.next().toUpperCase();

                    // Vérification que le critère saisi est valide
                    if (Verifications.estCritereValide(choixCritere3)) {
                        break;
                    } else {
                        System.out.println("Critère invalide, veuillez choisir parmi temps, prix ou CO2.");
                    }
                } while (true);

                critere = TypeCout.valueOf(choixCritere3);
                System.out.println("Entrez le poids maximal pour le critère " + critere + ": ");
                poids = Verifications.getValidDoubleInput(scanner);
                poidsMaximaux.put(critere, poids);
            }
        }

        // Demander le nombre de modalités de transport souhaitées
        System.out.println("Combien de modalités de transport souhaitez-vous utiliser (1, 2 ou 3) ?");
        int nombreDeModalites = Verifications.getValidIntInput(scanner, 3);

        // Modalités de transport
        List<ModaliteTransport> modalitesChoisies = new ArrayList<>(nombreDeModalites);
        List<ModaliteTransport> modalitesDisponibles = Arrays.asList(ModaliteTransport.values());

        switch (nombreDeModalites) {
            case 1:
                System.out.println("Choisissez une modalité de transport (train, bus, avion): ");
                afficherModalitesDisponibles(modalitesDisponibles);
                int choixModalite1 = Verifications.getValidIntInput(scanner,3) - 1;
                modalitesChoisies.add(modalitesDisponibles.get(choixModalite1));
                break;

            case 2:
                System.out.println("Choisissez deux modalités de transport (train, bus, avion): ");
                afficherModalitesDisponibles(modalitesDisponibles);
                int choixModalite2a = Verifications.getValidIntInput(scanner, 3) - 1;
                modalitesChoisies.add(modalitesDisponibles.get(choixModalite2a));
                modalitesDisponibles.remove(choixModalite2a);
                afficherModalitesDisponibles(modalitesDisponibles);
                int choixModalite2b = Verifications.getValidIntInput(scanner, 2) - 1;
                modalitesChoisies.add(modalitesDisponibles.get(choixModalite2b));
                break;

            case 3:
                modalitesChoisies.addAll(modalitesDisponibles);
                break;
        }

        // Recherche des chemins en utilisant kpccUltime avec les noms de villes et les paramètres choisis
        List<Chemin> chemins = Algorithme.kpccUltime(p, villeDepart, villeArrivee, poidsMaximaux, modalitesChoisies, k);
        chemins = Plateforme.reductionAffichageChemins(chemins);

        // Affichage des résultats
        afficherResultats(chemins, villeDepart, villeArrivee, user.getCritere(), modalitesChoisies);
    }

    private static void afficherModalitesDisponibles(List<ModaliteTransport> modalitesDisponibles) {
        for (int i = 0; i < modalitesDisponibles.size(); i++) {
            System.out.println((i + 1) + ". " + modalitesDisponibles.get(i));
        }
    }

    private static void afficherResultats(List<Chemin> chemins, String villeDepart, String villeArrivee, TypeCout critere, List<ModaliteTransport> modalites) {
        if (chemins.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + critere + " avec les modalités spécifiées.");
        } else if (chemins.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + villeDepart + " à " + villeArrivee + " selon le critère " + critere + " avec les modalités spécifiées:");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        } else {
            System.out.println("Les " + chemins.size() + " plus courts chemins trouvés de " + villeDepart + " à " + villeArrivee + " selon le critère " + critere + " avec les modalités spécifiées sont :");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString());
            }
        }
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
            
            final int choix = Verifications.getValidIntInput(scanner, 6);
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
            
            int choix = Verifications.getValidIntInput(scanner, 2);
            while (choix < 1 || choix > 2) {
                System.out.println("Choix invalide, veuillez réessayer.");
                choix = Verifications.getValidIntInput(scanner, 2);
            }
            
            if (choix == 1) {
                System.out.println("Choisissez un utilisateur existant :");
                for (int i = 0; i < p.getUsers().size(); i++) {
                    System.out.println((i + 1) + ". " + p.getUsers().get(i).getNom() + " " + p.getUsers().get(i).getPrenom());
                }
        
                int choixUser = Verifications.getValidIntInput(scanner, p.getUsers().size());
                while (choixUser < 1 || choixUser > p.getUsers().size()) {
                    System.out.println("Choix invalide, veuillez réessayer.");
                    choixUser = Verifications.getValidIntInput(scanner, p.getUsers().size());
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
