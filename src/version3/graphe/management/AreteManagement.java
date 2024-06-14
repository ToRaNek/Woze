package version3.graphe.management;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import version3.graphe.Algorithme;
import version3.graphe.Arete;
import version3.graphe.Structure;

public class AreteManagement {
    private ArrayList<Arete> aretes;

    public AreteManagement() {
        aretes = new ArrayList<>();
    }

    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    public void addArete(final Arete arete) {
        aretes.add(arete);
    }

    public void addAreteWithReturn(final Arete arete) {
        addArete(arete);
        final Arete retour = new Arete(arete.getArrivee(), arete.getDepart(), arete.getModalite(), arete.getCouts());
        addArete(retour);
    }

    public void removeArete(final Arete arete) {
        aretes.remove(arete);
    }

    public Arete getArete(MultiGrapheOrienteValue graphe, Structure struct1, Structure struct2){
        Arete areteReturned = null;
        if(isLinked(graphe, struct1, struct2)){
            for (final Arete arete : aretes) {
                if(arete.getArrivee().equals(struct2) && arete.getDepart().equals(struct1)) {
                    areteReturned = arete;
                }
            }
            // System.out.println("il n'y a pas d'arete associé à ces deux structures");
        }   
        return areteReturned;
    }

    public boolean isLinked(MultiGrapheOrienteValue graphe, final Structure depart,final Structure arrivee) {
        boolean result = true;
        if (depart == null || arrivee == null) {
            result = false;
        }

        try {
            final List<Chemin> chemins = Algorithme.simplePCC(graphe, depart, arrivee, 1);
            result = !chemins.isEmpty();
        } catch (IllegalArgumentException e) {
            System.out.println("Il n'existe pas de lien entre " + depart.getNom() + " et " + arrivee.getNom());
            result = false;
        }
        return result;
    }

    public int indexOf(final Arete arete) {
        return aretes.indexOf(arete);
    }

     public int indexOf(final Structure depart,final Structure arrivee,final ModaliteTransport modalite) {
        int res = -1;
        for (final Arete arete : aretes) {
            if (arete.getDepart() == depart && arete.getArrivee() == arrivee && arete.getModalite() == modalite) {
                res = indexOf(arete);
            }
        }
        return res;
    }

    public void removeArete(final int index) {
        removeArete(aretes.get(index));
    }
}
