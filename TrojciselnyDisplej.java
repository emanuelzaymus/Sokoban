/**
 * Trojciselny displej riadi tri segmentove cislice a posiela im spravy.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class TrojciselnyDisplej {
    private SegmentovaCislica jednotky;
    private SegmentovaCislica desiatky;
    private SegmentovaCislica stovky;
    
    private int hodnota;
    
    /**
     * Konstruktor postupne inicializouje tri segmentove cislice - jednotky, desiatky, stovky. 
     * Hodnotu nastavi na nulu.
     * 
     * @param x X-ova suradnica laveho horneho rohu celeho displeja
     * @param y Y-ova suradnica laveho horneho rohu celeho displeja
     */
    public TrojciselnyDisplej(int x, int y) {
        this.jednotky = new SegmentovaCislica(x + SegmentovaCislica.SIRKA * 2, 5);
        this.desiatky = new SegmentovaCislica(x + SegmentovaCislica.SIRKA, 5);
        this.stovky = new SegmentovaCislica(x, 5);
        
        this.hodnota = 0;
    }
    
    /**
     * Posle spravu na inkrementaciu hodnotky. Nova hodnota sa aj zobrazi.
     */
    public void pridaj() {
        this.hodnota++;
        
        this.jednotky.zobraz(this.hodnota % 10);
        this.desiatky.zobraz(this.hodnota / 10 % 10);
        this.stovky.zobraz(this.hodnota / 100);
    }
    
    /**
     * Posle spravu na dekrementaciu hodnotky. Nova hodnota sa aj zobrazi.
     */
    public void uber() {
        this.hodnota -= 3;
        this.pridaj();
    }
    
    /**
     * Vynuleje displej. Nulova hodnota sa aj zobrazi.
     */
    public void vynuluj() {
        this.hodnota = -1;
        
        this.pridaj();
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.jednotky.zobrazCislicu();
        this.desiatky.zobrazCislicu();
        this.stovky.zobrazCislicu();
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.jednotky.skryCislicu();
        this.desiatky.skryCislicu();
        this.stovky.skryCislicu();
    }
}
