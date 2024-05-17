package Version1;

import java.util.ArrayList;

import fr.ulille.but.sae_s2_2024.*;

public class Plateforme {

    private ArrayList<Structure> structures;
    private ArrayList<Arete> aretes;

    public Plateforme(String [] data){

        structures = new ArrayList<>();
        aretes = new ArrayList<>();

        for (String entree : data) {
            String[] split = entree.split(";");

            ModaliteTransport modalite = ModaliteTransport.valueOf(split[2].toUpperCase());


            String nom_depart = nom(split[0], modalite);
            String nom_arrivee = nom(split[1], modalite);
            
            Structure depart , arrivee;

            // DEPART
            if(!structures.contains(nom_depart)){
                depart = new Structure(nom_depart);
                structures.add(depart);
            }else{
                depart = getStructure(nom_depart);
            }

            // ARRIVEE
            if(!structures.contains(nom_arrivee)){
                arrivee = new Structure(nom_arrivee);
                structures.add(arrivee);
            }else{
                arrivee = getStructure(nom_arrivee);
            }

            // ARETE ALLEE - RETOUR
            Arete allee = new Arete(depart, arrivee, modalite, Double.parseDouble(split[3]), Double.parseDouble(split[4]),Double.parseDouble(split[5]) );
            Arete retour =new Arete(arrivee, depart, modalite, Double.parseDouble(split[3]), Double.parseDouble(split[4]),Double.parseDouble(split[5]));
            
            aretes.add(allee);
            aretes.add(retour);
        }
    }

    public boolean contains(final String nom){
        boolean bool = false;
        for (Structure structure : structures) {
            if(structure.getNom().equals(nom)) {
                bool = true ;
                break;
            }
        }

        return bool;
    }

    public Structure getStructure(String nom) {
        for (Structure structure : structures) {
            if (structure.getNom().equals(nom)) {
                return structure;
            }
        }
        return null; 
    }

    

    public static String nom(String structure, ModaliteTransport modalite ){
        String nom;
        switch (modalite) {
            case TRAIN:
                nom = "Gare_de_" + structure;
                break;
            case AVION:
                nom =  "Aéroport_de_" + structure;
                break;
            case BUS:
                nom =  "Arrêt_de_bus_de_" + structure;
                break;
            default:
                nom =  structure; // Si la modalité de transport n'est pas donnée, ça retourne simplement le nom d'origine ( bon après ça devrait pas arrivé mais on sait jamais).
        }
        return nom;
    }

    public ArrayList<Structure> getStructures() {
        return structures;
    }

    public void setStructures(ArrayList<Structure> structures) {
        this.structures = structures;
    }

    public ArrayList<Arete> getAretes() {
        return aretes;
    }

    public void setAretes(ArrayList<Arete> aretes) {
        this.aretes = aretes;
    }

    public void addArete(Arete arete){
        aretes.add(arete);
    }

    public void addStructure(Structure structure){
        structures.add(structure);
    }

    public void removeArete(Arete arete){
        aretes.remove(aretes);
    }
    public void removeArete(int index){
        removeArete(aretes.get(index));
    }

    public void removeStructure(Structure structure){
        structures.remove(structures);
    }
    public void removeStructure(int index){
        removeStructure(structures.get(index));
    }

    

    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("Structures:\n");
        for (Structure structure : structures) {
            sb.append(structure).append("\n");
        }
        
        sb.append("\nAretes:\n");
        for (Arete arete : aretes) {
            sb.append(arete).append("\n");
        }
        
        return sb.toString();
    }



}

