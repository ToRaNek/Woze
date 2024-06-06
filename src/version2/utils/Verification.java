package version2;

import java.util.Scanner;

import fr.ulille.but.sae_s2_2024.ModaliteTransport;

/**
 * Classe utilitaire pour valider un ensemble de données représentant des trajets entre villes.
 * Chaque ligne de données doit respecter un format spécifique :
 * `&lt;ville de départ&gt;;&lt;ville d'arrivée&gt;;&lt;mode de transport&gt;;&lt;distance&gt;;&lt;durée&gt;;&lt;prix&gt;`
 */
public class Verification {


    /**
     * Obtient le nombre de lignes dans un tableau de chaînes de caractères.
     *
     * @param ligne Le tableau de chaînes de caractères représentant les lignes de données.
     * @return Le nombre de lignes, ou 0 si le tableau est null.
     */
    public int getLine(String[] ligne) {
        return ligne == null ? 0 : ligne.length;
    }

    /**
     * Vérifie la validité d'un ensemble de données de trajets.
     *
     * @param data Le tableau de chaînes de caractères représentant les données, où chaque élément est une ligne de données.
     * @return true si toutes les lignes sont valides, false sinon.
     */
    public boolean dataIsValid(String[] data) {
        if (getLine(data) == 0) {
            return false;
        }
        for (String line : data) {
            if (!lineIsValid(line)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie la validité d'une ligne de données de trajet.
     *
     * @param uneLigne La chaîne de caractères représentant la ligne de données.
     * @return true si la ligne est valide, false sinon.
     */
    public boolean lineIsValid(String uneLigne) {
        String[] lineSeparator = uneLigne.split(";");
        if (lineSeparator.length != 6) {
            return false;
        }

        return villeIsValid(lineSeparator[0]) &&
                villeIsValid(lineSeparator[1]) &&
                transportIsValid(lineSeparator[2]) &&
                doubleIsValid(lineSeparator[3]) &&
                doubleIsValid(lineSeparator[4]) &&
                doubleIsValid(lineSeparator[5]);
    }

    /**
     * Vérifie si une chaîne de caractères représente un nombre décimal valide.
     *
     * @param s La chaîne de caractères à vérifier.
     * @return true si la chaîne représente un nombre décimal valide, false sinon.
     */
    public boolean doubleIsValid(String s) {
        return s.matches("\\d+(\\.\\d+)?");
    }

    /**
     * Vérifie si une chaîne de caractères représente un mode de transport valide (bus, train ou avion).
     *
     * @param transport La chaîne de caractères à vérifier.
     * @return true si le mode de transport est valide, false sinon.
     */
    public boolean transportIsValid(String transport) {
        return ModaliteTransport.BUS.name().equalsIgnoreCase(transport) ||
                ModaliteTransport.TRAIN.name().equalsIgnoreCase(transport) ||
                ModaliteTransport.AVION.name().equalsIgnoreCase(transport);
    }

    /**
     * Vérifie si une chaîne de caractères représente un nom de ville valide (lettres, tirets et apostrophes uniquement).
     *
     * @param ville La chaîne de caractères à vérifier.
     * @return true si le nom de ville est valide, false sinon.
     */
    public boolean villeIsValid(String ville) {
        return ville.matches("[a-zA-Z'-]+");
    }
    // V1 finis


    public boolean correspondanceIsValid(String[] data) {
        if (getLine(data) == 0) {
            return false;
        }
        for (String line : data) {
            if (!correspondanceLineIsValid(line)) {
                return false;
            }
        }
        return true;
    }

    public boolean correspondanceLineIsValid(String uneLigne) {
        String[] lineSeparator = uneLigne.split(";");
        if (lineSeparator.length != 6) {
            return false;
        }

        return villeIsValid(lineSeparator[0]) &&
                transportIsValid(lineSeparator[1]) &&
                transportIsValid(lineSeparator[2]) &&
                doubleIsValid(lineSeparator[3]) &&
                doubleIsValid(lineSeparator[4]) &&
                doubleIsValid(lineSeparator[5]);
    }

    // Méthode pour vérifier si le moyen de transport est valide, note :j'aurais pu faire avec TypeCout.valueOf() mais ça fonctionne
    public static boolean estMoyenTransportValide(String moyenTransport) {
       return moyenTransport.equals("TRAIN") || moyenTransport.equals("AVION") || moyenTransport.equals("BUS");
    }
    
    // Vérifie si le critère est valide
    public static boolean estCritereValide(String critere) {
        return critere.equals("TEMPS") || critere.equals("CO2") || critere.equals("PRIX");
    }
    // TODO : dans les futur version à supprimer ou a modifier pour ihm
    // fonction qui permet de ne pas avoir d'exception à cause du scanner pour les double( désolé si on avait pas le droit j'en pouvais plus ) 
    public static double getValidDoubleInput(Scanner scanner) {
            while (true) {
                try {
                    double input = Double.parseDouble(scanner.nextLine());
                    return input;
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide, veuillez saisir un nombre entier.");
                }
            }

        }

        // fonction qui permet de ne pas avoir d'exception à cause du scanner pour les int ( désolé si on avait pas le droit j'en pouvais plus ) 
        public static int getValidIntInput(Scanner scanner) {
            while (true) {
                try {
                    int input = Integer.parseInt(scanner.nextLine());
                    return input;
                } catch (NumberFormatException e) {
                    System.out.println("Entrée invalide, veuillez saisir un nombre entier.");
                }
            }
        }
}
