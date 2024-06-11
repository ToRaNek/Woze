package version3.utils;

/**
 * Classe utilitaire pour la validation des données utilisateur, potentiellement issues d'un fichier CSV.
 * Cette classe suppose un format de données spécifique où chaque ligne représente un utilisateur
 * et est structurée comme suit : "Nom;Prénom;Ville;Critère".
 */
public class UsersVerificator {

    /**
     * Obtient le nombre de lignes dans un tableau de chaînes donné (représentant les données CSV).
     *
     * @param ligne Le tableau de chaînes contenant les lignes de données.
     * @return Le nombre de lignes, ou 0 si l'entrée est nulle.
     */
    public int getLine(String[] ligne) {
        return ligne == null ? 0 : ligne.length;
    }

    /**
     * Valide les données utilisateur.
     *
     * @param data Le tableau de chaînes contenant les données utilisateur, où chaque ligne représente un utilisateur.
     * @return true si toutes les lignes des données sont valides, false sinon.
     */
    public boolean userIsValid(String[] data) {
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
     * Valide une seule ligne de données utilisateur.
     *
     * @param uneLigne La chaîne représentant une seule ligne de données utilisateur.
     * @return true si la ligne est valide, false sinon.
     */
    public boolean lineIsValid(String uneLigne) {
        String[] lineSeparator = uneLigne.split(";");
        if (lineSeparator.length != 4) {
            return false;
        }

        return nameSurnameVilleIsValid(lineSeparator[0]) &&
                nameSurnameVilleIsValid(lineSeparator[1]) &&
                nameSurnameVilleIsValid(lineSeparator[2]) &&
                critereIsValid(lineSeparator[3]);
    }

    /**
     * Valide une chaîne de nom, prénom ou ville.
     * <p>
     * Remarque : Cette méthode utilise une expression régulière simplifiée ([a-zA-Z'-]+) pour permettre une plus grande flexibilité dans les noms, prénoms et villes.
     * Cette approche est choisie pour prendre en compte la diversité des noms dans le monde réel (par exemple, les noms peu communs comme ceux donnés par Elon Musk à ses enfants) et pour éviter d'exclure des données valides avec des règles trop strictes.
     * Si une validation plus précise est requise, il est recommandé d'utiliser une expression régulière plus complexe ou des méthodes de validation distinctes pour chaque champ.
     *
     * @param s La chaîne à valider.
     * @return true si la chaîne est valide, false sinon.
     */
    public boolean nameSurnameVilleIsValid(String s) {
        return s.matches("[a-zA-Z'-]+");
    }

    /**
     * Valide une chaîne de critère.
     *
     * @param critere Le critère à valider.
     * @return true si le critère est l'un de "TEMPS", "CO2" ou "PRIX" (insensible à la casse), false sinon.
     */
    public boolean critereIsValid(String critere) {
        critere = critere.toUpperCase();
        return critere.equals("TEMPS") || critere.equals("CO2") || critere.equals("PRIX");
    }
}