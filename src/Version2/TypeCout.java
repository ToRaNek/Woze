package version2;

/**
 * Cette énumération définit les différents types de coûts possibles.
 */
public enum TypeCout {
    
    /**
     * Coût en émissions de CO2.
     */
    CO2(" kg"),
    
    /**
     * Coût en temps.
     */
    TEMPS(" min"),
    
    /**
     * Coût en prix.
     */
    PRIX(" €");

    /** Le symbole associé au type de coût */
    private final String SYMBOLE;

    /**
     * Constructeur de TypeCout.
     * @param symbole Le symbole associé au type de coût.
     */
    TypeCout(final String symbole) {
        this.SYMBOLE = symbole;
    }

    /**
     * Obtient le symbole associé au type de coût.
     * @return Le symbole associé.
     */
    public String getSymbole() {
        return SYMBOLE;
    }

    /**
     * Redéfinition de la méthode toString pour afficher le nom du type de coût avec son symbole.
     * @return Une représentation sous forme de chaîne de caractères du nom du type de coût avec son symbole.
     */
    @Override
    public String toString() {
        return name() + SYMBOLE;
    }
        

}
