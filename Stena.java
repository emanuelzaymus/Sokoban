/**
 * Stena, ktora tvori prekazku pre panacika a krabice.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Stena {
    private static final String FARBA_OBVODU = "red";
    private static final String FARBA_VYPLNE = "black";
    
    private Stvorec obvodSteny;
    private Stvorec vyplnSteny;
    
    /**
     * Konstruktor vytvori stenu na zadanej pozicii.
     * 
     * @param x suradnica X laveho horneho rohu steny
     * @param y suradnica Y laveho horneho rohu steny
     */
    public Stena(int x, int y) {
        this.obvodSteny = new Stvorec();
        this.obvodSteny.zmenFarbu(Stena.FARBA_OBVODU);
        this.obvodSteny.posunVodorovne(-60 + (Mapa.VELKOST_POLICKA * x));
        this.obvodSteny.posunZvisle(-50 + (Mapa.VELKOST_POLICKA * y));
        
        this.vyplnSteny = new Stvorec();
        this.vyplnSteny.zmenFarbu(Stena.FARBA_VYPLNE);
        this.vyplnSteny.zmenStranu(26);
        this.vyplnSteny.posunVodorovne(-58 + (Mapa.VELKOST_POLICKA * x));
        this.vyplnSteny.posunZvisle(-48 + (Mapa.VELKOST_POLICKA * y));
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.obvodSteny.zobraz();
        this.vyplnSteny.zobraz();
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.obvodSteny.skry();
        this.vyplnSteny.skry();
    }
}
