package Version1;

public class Main {
    public static void main(String[] args) {
        String [] data = new String[]{
            "villeA;villeB;Train;60;1.7;80",
            "villeB;villeD;Train;22;2.4;40",
            "villeA;villeC;Train;42;1.4;50",
            "villeB;villeC;Train;14;1.4;60",
            "villeC;villeD;Avion;110;150;22",
            "villeC;villeD;Train;65;1.2;90"
            };

        Plateforme plateforme = new Plateforme(data);
        System.out.println(plateforme);


        // Exemple d'utilisation de la méthode getStructure
        Structure villeA = plateforme.getStructure("Gare_de_villeA");
        Structure villeB = plateforme.getStructure("Gare_de_villeB");
        Structure villeC = plateforme.getStructure("Gare_de_villeC");
        Structure villeD = plateforme.getStructure("Gare_de_villeD");

        System.out.println(villeA); // Affiche "Gare_de_villeA"
        System.out.println(villeB); // Affiche "Gare_de_villeB"
        System.out.println(villeC); // Affiche "Gare_de_villeC"
        System.out.println(villeD); // Affiche "Gare_de_villeD"
    }
    
}
