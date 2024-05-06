import java.util.List;

import fr.ulille.but.sae_s2_2024.*;


public class Example {
    // DATA (Exemple)
    private final String[] DATA = new String[]{
        "villeA;villeB;Train;60;1.7;80",
        "villeB;villeD;Train;22;2.4;40",
        "villeA;villeC;Train;42;1.4;50",
        "villeB;villeC;Train;14;1.4;60",
        "villeC;villeD;Avion;110;150;22",
        "villeC;villeD;Train;65;1.2;90"};

    public static void main(String[] args) {
        
        // G1
        MultiGrapheOrienteValue G1 = new MultiGrapheOrienteValue();

        // POINTS
        // TRAINS
        Lieu A_gare = new Ville("A", ModaliteTransport.TRAIN);
        Lieu B_gare = new Ville("B", ModaliteTransport.TRAIN);
        Lieu C_gare = new Ville("C", ModaliteTransport.TRAIN);
        Lieu D_gare = new Ville("D", ModaliteTransport.TRAIN);

        // AVIONS
        Lieu C_aeroport = new Ville("C", ModaliteTransport.AVION);
        Lieu D_aeroport = new Ville("D", ModaliteTransport.AVION);

        // AJOUTS DES POINTS
        G1.ajouterSommet(A_gare);
        G1.ajouterSommet(B_gare);
        G1.ajouterSommet(C_gare);
        G1.ajouterSommet(D_gare);

        G1.ajouterSommet(C_aeroport);
        G1.ajouterSommet(D_aeroport);


        // ARRETES
        // TRAINS
        Trancon trainTranconAB = new Arrete(A_gare, B_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconBA = new Arrete(B_gare, A_gare, ModaliteTransport.TRAIN);

        Trancon trainTranconAC = new Arrete(A_gare, C_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconCA = new Arrete(C_gare, A_gare, ModaliteTransport.TRAIN);

        Trancon trainTranconBC = new Arrete(B_gare, C_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconCB = new Arrete(C_gare, B_gare, ModaliteTransport.TRAIN);

        Trancon trainTranconBD = new Arrete(B_gare, D_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconDB = new Arrete(D_gare, B_gare, ModaliteTransport.TRAIN);

        Trancon trainTranconCD = new Arrete(C_gare, D_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconDC = new Arrete(D_gare, C_gare, ModaliteTransport.TRAIN);

        // AVIONS
        Trancon avionTranconCD = new Arrete(C_aeroport, D_aeroport, ModaliteTransport.AVION);
        Trancon avionTranconDC = new Arrete(D_aeroport, C_aeroport, ModaliteTransport.AVION);


 
        G1.ajouterArete(trainTranconAB, 80);
        G1.ajouterArete(trainTranconBA, 80);

        G1.ajouterArete(trainTranconAC, 50);
        G1.ajouterArete(trainTranconCA, 50);

        G1.ajouterArete(trainTranconBC, 60);
        G1.ajouterArete(trainTranconCB, 60);

        G1.ajouterArete(trainTranconBD, 40);
        G1.ajouterArete(trainTranconDB, 40);

        G1.ajouterArete(trainTranconCD, 90);
        G1.ajouterArete(trainTranconDC, 90);

        G1.ajouterArete(avionTranconCD, 22);
        G1.ajouterArete(avionTranconDC, 22);

        System.out.println(G1);

        // 3 CHEMINS
        List<Chemin> shortestPaths3 = AlgorithmeKPCC.kpcc(G1, A_gare, D_gare, 3);
        // System.out.println("Les 3 trajets recommandés de A à D sont:");
        // for (int i = 0; i < shortestPaths3.size(); i++) {
        //     Chemin path = shortestPaths3.get(i);
        //     System.out.println(i+1 +") Train de A à D en passent par " + path.aretes() + ". Durée : " + path.poids() + " minutes");
        // }

        // 10 CHEMINS
        List<Chemin> shortestPaths10= AlgorithmeKPCC.kpcc(G1, A_gare, D_gare, 10);
        // System.out.println("Les 10 trajets recommandés de A à D sont:");
        // for (int i = 0; i < shortestPaths10.size(); i++) {
        //     Chemin path = shortestPaths10.get(i);
        //     System.out.println(i+1 +") Train de A à D en passent par " + path.aretes() + ". Durée : " + path.poids() + " minutes");
        // }

        

    }
}