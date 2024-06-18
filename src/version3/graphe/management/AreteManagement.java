package version3.graphe.management;

import java.util.ArrayList;
import java.util.List;

import fr.ulille.but.sae_s2_2024.Chemin;
import fr.ulille.but.sae_s2_2024.ModaliteTransport;
import fr.ulille.but.sae_s2_2024.MultiGrapheOrienteValue;
import version3.utils.algorithm.Algorithme;
import version3.graphe.Arete;
import version3.graphe.Structure;

/**
 * Classe pour la gestion des arêtes dans un graphe.
 */
public class AreteManagement {
    private ArrayList<Arete> aretes;

    /**
     * Constructeur par défaut initialisant la liste des arêtes.
     */
    public AreteManagement() {
        aretes = new ArrayList<>();
    }

    /**
     * Retourne la liste des arêtes.
     *
     * @return La liste des arêtes.
     */
    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    /**
     * Ajoute une arête à la liste.
     *
     * @param arete L'arête à ajouter.
     */
    public void addArete(final Arete arete) {
        aretes.add(arete);
    }

    /**
     * Ajoute une arête bidirectionnelle à la liste.
     *
     * @param arete L'arête à ajouter.
     */
    public void addAreteWithReturn(final Arete arete) {
        addArete(arete);
        final Arete retour = new Arete(arete.getArrivee(), arete.getDepart(), arete.getModalite(), arete.getCouts());
        addArete(retour);
    }

    /**
     * Supprime une arête de la liste.
     *
     * @param arete L'arête à supprimer.
     */
    public void removeArete(final Arete arete) {
        aretes.remove(arete);
    }

    /**
     * Obtient l'arête entre deux structures dans un graphe donné.
     *
     * @param graphe Le graphe dans lequel chercher l'arête.
     * @param struct1 La première structure de l'arête.
     * @param struct2 La deuxième structure de l'arête.
     * @return L'arête entre les deux structures, ou null si aucune arête n'est trouvée.
     */
    public Arete getArete(MultiGrapheOrienteValue graphe, Structure struct1, Structure struct2) {
        Arete areteReturned = null;
        if (isLinked(graphe, struct1, struct2)) {
            for (final Arete arete : aretes) {
                if (arete.getArrivee().equals(struct2) && arete.getDepart().equals(struct1)) {
                    areteReturned = arete;
                }
            }
        }
        return areteReturned;
    }

    /**
     * Vérifie s'il existe un lien entre deux structures dans un graphe donné.
     *
     * @param graphe Le graphe dans lequel vérifier le lien.
     * @param depart La structure de départ du lien.
     * @param arrivee La structure d'arrivée du lien.
     * @return true s'il existe un lien, false sinon.
     */
    public boolean isLinked(MultiGrapheOrienteValue graphe, final Structure depart, final Structure arrivee) {
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

    /**
     * Retourne l'index d'une arête spécifiée dans la liste.
     *
     * @param arete L'arête dont on cherche l'index.
     * @return L'index de l'arête, ou -1 si l'arête n'est pas trouvée.
     */
    public int indexOf(final Arete arete) {
        return aretes.indexOf(arete);
    }

    /**
     * Retourne l'index d'une arête spécifiée par ses structures de départ, d'arrivée et sa modalité dans la liste.
     *
     * @param depart La structure de départ de l'arête.
     * @param arrivee La structure d'arrivée de l'arête.
     * @param modalite La modalité de transport de l'arête.
     * @return L'index de l'arête, ou -1 si l'arête n'est pas trouvée.
     */
    public int indexOf(final Structure depart, final Structure arrivee, final ModaliteTransport modalite) {
        int res = -1;
        for (final Arete arete : aretes) {
            if (arete.getDepart() == depart && arete.getArrivee() == arrivee && arete.getModalite() == modalite) {
                res = indexOf(arete);
            }
        }
        return res;
    }

    /**
     * Supprime une arête de la liste en utilisant son index.
     *
     * @param index L'index de l'arête à supprimer.
     */
    public void removeArete(final int index) {
        removeArete(aretes.get(index));
    }
}
