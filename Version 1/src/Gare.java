import fr.ulille.but.sae_s2_2024.*;

public class Gare implements Lieu {
    String nom;

    public Gare(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return nom;
    }

}