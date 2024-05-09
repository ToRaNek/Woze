
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class DATATEST {
    //Je recupere le nombre de ligne du data
    public int getLine(String[] truc) {
        if (truc == null) {
            return 0;
        }
        return truc.length;
    }

    /*
    Cette méthode vérifie que le data n'est pas vide et si elle ne l'est pas elle commence à
    décompiler chaque ligne pour l'envoyer dans la méthode lineIsValid si une ligne n'est pas valide dataIsValid renverra false
    */
    public boolean dataIsValid(String[] truc) {
        int nbrligne = getLine(truc);
        if (nbrligne == 0){
            return false;
        }

        for (int i = 0; i < nbrligne; i++) {
            if (!lineIsValid(truc[i])) {
                return false;
            }
        }
        return true;
    }

    /*
    Cette méthode va séparer par colonne la ligne en paramètre grâce au ";" si le tableau n'a pas 6 valeurs,
    la ligne n'est pas valide, si elle a 6 valeurs, elle va vérifier chaque colonne pour vérifier que c'est bien une ville, un transport, ou un double
    On ne fait pas en sorte qu'ici si une ville s'appelle comme un transport exemple Avion retourne false, car oui, cette ville existe dans le 62
    */
    public boolean lineIsValid(String uneLigne) {
        String[] lineSeparator = uneLigne.split(";");
        if (lineSeparator.length != 6) {
            return false;
        }

        if (!villeIsValid(lineSeparator[0])) {
            return false;
        } else if (!villeIsValid(lineSeparator[1])) {
            return false;
        } else if (!transportIsValid(lineSeparator[2])) {
            return false;
        } else if (!doubleIsValid(lineSeparator[3])) {
            return false;
        } else if (!doubleIsValid(lineSeparator[4])) {
            return false;
        } else if (!doubleIsValid(lineSeparator[5])) {
            return false;
        }
        return true;
    }

    /*
    Elle vérifie que c'est bien un double
    */
    private boolean doubleIsValid(String s) {
        return s.matches("\\d+(\\.\\d+)?");
    }

    /*
    Elle vérifie que le type de transport est bien un transport du enum
    */
    private boolean transportIsValid(String s) {
        return  ModaliteTransport.BUS.name().equals(s.toUpperCase())   ||
                ModaliteTransport.TRAIN.name().equals(s.toUpperCase()) ||
                ModaliteTransport.AVION.name().equals(s.toUpperCase());
    }

    /*
    Elle vérifie qu'une ville ne peut contenir que des lettres, tiret et apostrophes
    */
    public boolean villeIsValid(String ville) {
        return ville.matches("[a-zA-Z'-]+");
    }

    public static void main(String[] args) {
        String[] data = new String[]{
                "villeA;villeB;Train;60;1.7;80",
                "villeB;villeD;Train;22;2.4;40",
                "villeA;villeC;Train;42;1.4;50",
                "villeB;villeC;Train;14;1.4;60",
                "villeC;villeD;Bus;110;150;22",
                "villeC;villeD;Train;65;1.2;90"
        };
        DATATEST dataTest = new DATATEST();

        /* Les tests pour vérifier que mes méthodes fonctionnent*/
        assertTrue("Toutes les données sont valides", dataTest.dataIsValid(data));
        assertTrue("Une ligne valide rend les données valides", dataTest.lineIsValid("villeA;villeB;train;60;1.7;80"));
        assertTrue("Un nombre double valide est reconnu", dataTest.doubleIsValid("3.14"));
        assertTrue("Un transport valide est reconnu", dataTest.transportIsValid("TraIn"));
        assertTrue("Une ville valide est reconnue", dataTest.villeIsValid("Lille"));

        assertFalse("Une ligne invalide rend les données invalides", dataTest.lineIsValid("villA;villeB;;60;1.7;80"));
        assertFalse("Une ligne avec un nombre double invalide rend les données invalides", dataTest.lineIsValid("villeA;villeB;Train;60;1.7abc;80"));
        assertFalse("Un nombre double invalide n'est pas reconnu", dataTest.doubleIsValid("abc"));
        assertFalse("Un transport invalide n'est pas reconnu", dataTest.transportIsValid("CAR"));
        assertFalse("Une ville invalide n'est pas reconnue", dataTest.villeIsValid("123ville"));

        System.out.println(dataTest.dataIsValid(data));
    }
}
*/