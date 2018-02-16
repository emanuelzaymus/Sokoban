/**
 * Ulozisko je miesto na platne, kde ma panacik presunut krabicu.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Ulozisko {
    private static final String FARBA_BODU = "red"; 
    private static final String FARBA_OKOLIA = "black";
    
    private Kruh bod;
    private Kruh okolie;
    
    private int polohaX;
    private int polohaY;
    
    /**
     * Konstruktor vytvori ulozisko na zadanej pozicii.
     * 
     * @param x suradnica X laveho horneho rohu uloziska
     * @param y suradnica Y laveho horneho rohu uloziska
     */
    public Ulozisko(int x, int y) {
        this.bod = new Kruh();
        this.bod.zmenPriemer(10);
        this.bod.zmenFarbu(Ulozisko.FARBA_BODU);
        this.bod.posunVodorovne(-10 + (Mapa.VELKOST_POLICKA * x));
        this.bod.posunZvisle(-50 + (Mapa.VELKOST_POLICKA * y));
        
        this.okolie = new Kruh();
        this.okolie.zmenPriemer(14);
        this.okolie.zmenFarbu(Ulozisko.FARBA_OKOLIA);
        this.okolie.posunVodorovne(-12 + (Mapa.VELKOST_POLICKA * x));
        this.okolie.posunZvisle(-52 + (Mapa.VELKOST_POLICKA * y));
        
        this.polohaX = x;
        this.polohaY = y;
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.okolie.zobraz();
        this.bod.zobraz();
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.okolie.skry();
        this.bod.skry();
    }
    
    /**
     * Vrati suradnicu X polohy uloziska.
     * 
     * @return polohaX 
     */
    public int getPolohaX() {
        return this.polohaX;
    }
    
    /**
     * Vrati suradnicu Y polohy uloziska.
     * 
     * @return polohaY 
     */
    public int getPolohaY() {
        return this.polohaY;
    }
}
