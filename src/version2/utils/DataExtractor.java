package version2.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
        return data;
    }

    // private static final String path_villes = "res/version2/data/villes.csv";
    private static final String path_villes = "res/version2/data/data.csv";
    // private static final String path_cor = "res/version2/data/correspondances.csv";
    private static final String path_users = "res/version2/user/users.csv";

    public static String[] data_villes = listeData(path_villes);
    // public static String[] data_correspondances = listeData(path_cor);
    public static String[] users = listeData(path_users); // users pour la version 3

}
