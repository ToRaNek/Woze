import java.util.List;

import fr.ulille.but.sae_s2_2024.*;

public class Exemple {
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

        // ARRETES
        // TRAINS
        Trancon trainTranconAB = new Arrete(A_gare, B_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconAC = new Arrete(A_gare, C_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconBC = new Arrete(B_gare, C_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconBD = new Arrete(B_gare, D_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconCD = new Arrete(C_gare, D_gare, ModaliteTransport.TRAIN);

        Trancon trainTranconBA = new Arrete(B_gare, A_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconCA = new Arrete(C_gare, A_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconCB = new Arrete(C_gare, B_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconDB = new Arrete(D_gare, B_gare, ModaliteTransport.TRAIN);
        Trancon trainTranconDC = new Arrete(D_gare, C_gare, ModaliteTransport.TRAIN);

        // AVIONS
        Trancon avionTranconCD = new Arrete(C_aeroport, D_aeroport, ModaliteTransport.AVION);
        Trancon avionTranconDC = new Arrete(D_aeroport, C_aeroport, ModaliteTransport.AVION);

        // BUS
        Trancon aeroportToGareC = new Arrete(C_gare, C_aeroport, ModaliteTransport.BUS);
        Trancon gareToAeroportC = new Arrete(C_aeroport, C_gare, ModaliteTransport.BUS);
        Trancon aeroportToGareD = new Arrete(D_gare, D_aeroport, ModaliteTransport.BUS);
        Trancon gareToAeroportD = new Arrete(D_aeroport, D_gare, ModaliteTransport.BUS);

 
        G1.ajouterArete(trainTranconAB, 90);
        G1.ajouterArete(trainTranconAC, 60);
        G1.ajouterArete(trainTranconBC, 60);
        G1.ajouterArete(trainTranconBD, 30);
        G1.ajouterArete(trainTranconCD, 90);

        G1.ajouterArete(trainTranconBA, 90);
        G1.ajouterArete(trainTranconCA, 60);
        G1.ajouterArete(trainTranconCB, 60);
        G1.ajouterArete(trainTranconDB, 30);
        G1.ajouterArete(trainTranconDC, 90);
        // System.out.println(G1);

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


        MultiGrapheOrienteValue G2 = new MultiGrapheOrienteValue();

        // AJOUTS DES POINTS
        G2.ajouterSommet(A_gare);
        G2.ajouterSommet(B_gare);
        G2.ajouterSommet(C_gare);
        G2.ajouterSommet(D_gare);

        // ARRETES 
        // TRAINS
        G2.ajouterArete(trainTranconAB, 90);
        G2.ajouterArete(trainTranconAC, 60);
        G2.ajouterArete(trainTranconBC, 60);
        G2.ajouterArete(trainTranconBD, 30);
        G2.ajouterArete(trainTranconCD, 90);

        G2.ajouterArete(trainTranconBA, 90);
        G2.ajouterArete(trainTranconCA, 60);
        G2.ajouterArete(trainTranconCB, 60);
        G2.ajouterArete(trainTranconDB, 30);
        G2.ajouterArete(trainTranconDC, 90);

        // AVIONS
        G2.ajouterArete(avionTranconCD, 30); // + 60 si on vient de train CD
        G2.ajouterArete(avionTranconDC, 30);

        // // BUS
        // G2.ajouterArete(aeroportToGareC, 60);
        // G2.ajouterArete(gareToAeroportC, 60);

        // G2.ajouterArete(aeroportToGareD, 0);
        // G2.ajouterArete(gareToAeroportD, 0);


        System.out.println(G2);
        shortestPaths3 = AlgorithmeKPCC.kpcc(G2, A_gare, D_gare, 3);
        System.out.println("Les 3 trajets recommandés de A à A sont:");
        for (int i = 0; i < shortestPaths3.size(); i++) {
            Chemin path = shortestPaths3.get(i);
            System.out.println(i+1 +") Train de A_gare à A en passent par " + path.aretes() + ". Durée : " + path.poids() + " minutes");
        }

        // 10 CHEMINS
        shortestPaths10= AlgorithmeKPCC.kpcc(G2, A_gare, D_gare, 10);
        System.out.println("Les 10 trajets recommandés de A à A sont:");
        for (int i = 0; i < shortestPaths10.size(); i++) {
            Chemin path = shortestPaths10.get(i);
            System.out.println(i+1 +") Train de A_gare à A en passent par " + path.aretes() + ". Durée : " + path.poids() + " minutes");
        }


    }
}