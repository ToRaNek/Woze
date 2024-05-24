package Version2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import Version1.VerificationData;

public class DataExtractor {

    public static List<String> lireData(String cheminFichier) {
        List<String> data = new ArrayList<>();
        File fichier = new File(cheminFichier);

        try {
            if (!fichier.exists()) {
                fichier.createNewFile();
                return data; // Retourne une liste vide si le fichier n'existe pas et a été créé
            }

            try (BufferedReader br = new BufferedReader(new FileReader(cheminFichier))) {
                String ligne;
                boolean premiereLigne = true;

                while ((ligne = br.readLine()) != null) {
                    if (premiereLigne) {
                        premiereLigne = false;
                        continue; // Ignore la première ligne parce qu'elle n'a pas de données utilisables (départ;arrivee;transport;prix;emission;temps)
                    }
                    String elements = ligne;
                    data.add(elements);
                }
            }
        } catch (IOException e) {
            System.out.println("Reading Error : "+e.getMessage());
            e.printStackTrace();
        }

        return data;
    }


    public static String[] listeData(String cheminFichier) {
        List<String> dataList = lireData(cheminFichier);
        String[] data = new String[dataList.size()];
        for (int i = 0; i < data.length; i++) {
            data[i] = dataList.get(i);
        }
        return dataList.toArray(new String[0]);
    }

    // méthode qui verifie les données 
    public static boolean verif(String[] data) {
        VerificationData verificationData = new VerificationData();
        // Vérifier la validité des données
        boolean isValid = verificationData.dataIsValid(data, ville);
        System.out.println("Toutes les données sont valides : " + isValid + '\n');
        return isValid;
    }

    private static final String path_villes = "E3/res/data/data_villes.csv";
    private static final String path_cor = "E3/res/data/data_correspondances.csv";

    public static String[] data_villes = listeData(cheminFichier);
    public static String[] data_correspondances = listeData(cheminFichier);


}
