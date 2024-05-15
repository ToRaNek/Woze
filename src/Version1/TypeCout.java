package Version1;
/**
 * Cette énumération définit les différents types de coûts possibles.
 */
public enum TypeCout {
    
    /**
     * Coût en émissions de CO2.
     */
    CO2(0.0," kg" ),
    
    /**
     * Coût en temps.
     */
    TEMPS(0.0," min" ),
    
    /**
     * Coût en prix.
     */
    PRIX(0.0, " €");

    private double cout; 
    private final String SYMBOLE;

    /**
     * Constructeur de TypeCout.
     * @param cout Le coût initial.
     * @param symbole Le symbole associé au type de coût.
     */
    TypeCout(double cout, String symbole){
        this.cout = cout;
        this.SYMBOLE = symbole;
    }

    /**
     * Obtient le coût actuel.
     * @return Le coût actuel.
     */
    public double getCout(){
        return cout;
    }

    /**
     * Obtient le symbole associé au type de coût.
     * @return Le symbole associé.
     */
    public String getSymbole(){
        return SYMBOLE;
    }

    /**
     * Définit un nouveau coût.
     * @param nouveau_cout Le nouveau coût à définir.
     */
    public void setCout(int nouveau_cout){
        this.cout = nouveau_cout;
    }

    /**
     * Affiche le coût avec son symbole.
     * @return Une représentation sous forme de chaîne de caractères du coût avec son symbole.
     */
    public String display(){
        return cout + SYMBOLE;
    }

    /** 
     * Redéfinition de la méthode toString pour afficher le nom du type de coût avec son symbole.
     * @return Une représentation sous forme de chaîne de caractères du nom du type de coût avec son symbole.
     */
    @Override
    public String toString() {
        return name();
    }

}
