package version3.main;

import java.util.List;
import java.util.Scanner;
import fr.ulille.but.sae_s2_2024.Chemin;
import version3.utils.Verification;
import version3.user.Voyageur;
import version3.graphe.Plateforme;
import version3.graphe.Structure;
import version3.graphe.TypeCout;
import version3.utils.DataExtractor;

public class Main {
    
    private static Plateforme p;
    
    // TODO csv avec les infos users (version 3)
    private static Voyageur user;
    private static Scanner scanner = new Scanner(System.in);

    /**
    méthode pour "effacer" ce qu'il y a sur le terminale ( plus beau pour les yeux)
     */
    public static void clearTerminal(){
        // Code pour effacer le terminal
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    

    /**
     * M éthode qui crée la plateforme.
     */
    public static void createPlateforme() {
        // Création de la plateforme p
        p = new Plateforme();
        // System.out.println(p); 
    }

    /**
     * Créer utilisateur.
     */
    public static void createUser() {
        System.out.println("Bonjour et bienvenue sur Woze, votre Plateforme de Comparaison d’itinéraires de transport.\n");

        // prenom
        System.out.println("Quel est votre Prénom ?");
        final String prenom = scanner.nextLine();

        // nom
        System.out.println("Quel est votre Nom ?");
        final String nom = scanner.nextLine();



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
        final String villeDepart = "Aucune";

        // critere
        clearTerminal();
        System.out.println("Quel critère de recherche d'itinéraire vous semble le plus adapté ? (Temps/Co2/Prix)");
        String critere = scanner.nextLine().toUpperCase();
        System.out.println(critere);
        while(!Verification.estCritereValide(critere)){
            System.out.println("Critère invalide. (Temps/Co2/Prix)");
            critere = scanner.nextLine().toUpperCase();
        };
        final TypeCout crit = TypeCout.valueOf(critere);
    
        // user
        clearTerminal();
        user = new Voyageur(prenom, nom, villeDepart, crit);
        p.addUser(user); // graph creer par la meme occasion
        p.setCurrentUser(user);

    }

    /**
     * Affichacge du menu.
     */
    public static void AffichacgeMenu() {
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
            clearTerminal();
    
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
                    scanner.close();
                    System.out.println("Au revoir !");
                    break;
                default:
                    // choix invalide 
                    System.out.println("Choix invalide, veuillez réessayer.");
            }
        } while (choix != 5);
    }

    /**
     * Changer d'utilisateur.
     */
    public static void changerUtilisateur() {
        System.out.println("Êtes-vous sûr de vouloir changer d'utilisateur ? (Oui/Non)");
        final String confirmation = scanner.nextLine().toUpperCase();
        if ("OUI".equals(confirmation) || "O".equals(confirmation)) {
            chooseUser(); // Si l'utilisateur confirme, appelle
            // la méthode chooseUser() pour changer d'utilisateur
        } else if ("NON".equals(confirmation) || "N".equals(confirmation)) {
            // Si l'utilisateur ne confirme pas, retourne simplement au menu
            System.out.println("Changement d'utilisateur annulé.");
        } else {
            System.out.println("Choix invalide. Retour au menu principal.");
        }
    }

    /**
     * affiche le graphe 
     */
    public static void voirGraph() {
        System.out.println(p.getCurrentGraphe());
    }

    /**
     * Chercher un chemin
     */
    public static void chercherChemin() {
        // Départ
        System.out.println("De quelle structure souhaitez-vous partir ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i));
        }
        final int choixStructureDepart = Verification.getValidIntInput(scanner) - 1;
        final String structureDepart = p.getStructures().get(choixStructureDepart).getNom();
    
        // Arrivée
        System.out.println("À quelle structure souhaitez-vous arriver ?");
        for (int i = 0; i < p.getStructures().size(); i++) {
            System.out.println((i + 1) + ". " + p.getStructures().get(i));
        }
        final int choixStructureArrivee = Verification.getValidIntInput(scanner) - 1;
        final String structureArrivee = p.getStructures().get(choixStructureArrivee).getNom();
    
        final Structure depart = p.getStructure(structureDepart);
        final Structure arrivee = p.getStructure(structureArrivee);
    
        System.out.println("Combien de chemins souhaitez-vous trouver ?");
        final int k = Verification.getValidIntInput(scanner);
    
        System.out.println("Quel poids ne doit pas excéder le trajet ?");
        final double poidsMax = Verification.getValidDoubleInput(scanner);
    
        System.out.println("Quel est le deuxième critère (temps, prix ou CO2) ?");
        final String critere2 = scanner.next();
    
        final TypeCout critere1 = user.getCritere();
        final TypeCout critere2Enum = TypeCout.valueOf(critere2.toUpperCase());
    
        // Chemin
        clearTerminal();
        List<Chemin> chemins = p.KPlusCourtsChemins(depart, arrivee, critere1, k, critere2Enum, poidsMax);
        chemins = Plateforme.reductionAffichageChemins(chemins);
    
        if (chemins.isEmpty()) {
            System.out.println("Aucun chemin trouvé de " + structureDepart + " à " + structureArrivee + " selon le critère " + critere1 + ".");
        } else if (chemins.size() == 1) {
            System.out.println("Chemin le plus court trouvé de " + structureDepart + " à " + structureArrivee + " selon le critère " + critere1 + ":");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString()); // tous les chemins trouvé 
            }
        } else {
            System.out.println("Les " + chemins.size() + " plus courts chemins trouvés de " + structureDepart + " à " + structureArrivee + " selon le critère " + critere1 + " sont :");
            for (final Chemin chemin : chemins) {
                System.out.println(chemin.toString()); // tous les chemins trouvé 
            }
        }
    

    }
    
    /**
     * Supprimer les donnees d'un utilisateur.
     */
    public static void supprimerDonneesUtilisateur() {
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
    

    /**
     * Afficher les informations de l'utilisateur
     */
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
            
            final int choix = Verification.getValidIntInput(scanner);
    
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
                    System.out.print("Entrez la nouvelle ville: ");
                    final String nouvelleVille = scanner.nextLine().toUpperCase();
                    if (p.containsVille(nouvelleVille)) {
                        user.setVille(nouvelleVille);
                    } else {
                        System.out.println("Cette ville n'existe pas. Modification annulée.");
                    }
                    break;
                case 4:
                    // Critère (TEMPS, PRIX, CO2)
                    clearTerminal();
                    System.out.print("Entrez le nouveau critère (TEMPS, PRIX, CO2): ");
                    final String nouveauCritere = scanner.nextLine().toUpperCase();
                    try {
                        user.setCritere(TypeCout.valueOf(nouveauCritere));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Critère invalide. Modification annulée.");
                    }
                    break;
                case 5:
                    // Supprimer les données utilisateur
                    clearTerminal();
                    supprimerDonneesUtilisateur();
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
        }
    }
    

    /**
     * @param datav Données des villes.
     * @param datac Données des correspondances.
     * @return Méthode qui verifie les données .
     */
    public static boolean verifVilleEtCorespondances(String [] datav) {
        final Verification verificationData = new Verification();
        // Vérifier la validité des données
        final boolean isValid = verificationData.dataIsValid(datav );
        System.out.println("Toutes les données sont valides : " + isValid + '\n');
        return isValid;
    }


    /**
     * Choix de l'utilisateur. 
     */
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
    
    

    /**
     * @param args args main
     */
    public static void main(String[] args) {
        if (verifVilleEtCorespondances(DataExtractor.data_villes)) {
            // Création de la plateforme
            createPlateforme();
            chooseUser();
            AffichacgeMenu();
        }
    }
        

}