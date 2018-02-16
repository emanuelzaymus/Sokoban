/**
 * Krabica, ktoru dokaze posuvat panacik do styroch smerov.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Krabica {
    private static final String FARBA_KRABICE_NEOZNACENA = "green";
    private static final String FARBA_KRABICE_OZNACENA = "red";
    private static final String FARBA_VNUTRA = "yellow";
    private static final String FARBA_KRUZKU = "green";
    
    private Stvorec krabica;
    private Stvorec vnutro;
    private Kruh kruzok;
    
    private int polohaX;
    private int polohaY;
    
    private boolean jeNaUlozisku;
    
    /**
     * Konstruktor vytvori krabicu na zadanej pozicii.
     * 
     * @param x suradnica X laveho horneho rohu krabice
     * @param y suradnica Y laveho horneho rohu krabice
     */
    public Krabica(int x, int y) {
        this.krabica = new Stvorec();
        this.krabica.zmenFarbu(Krabica.FARBA_KRABICE_NEOZNACENA);
        this.krabica.posunVodorovne(-60 + (Mapa.VELKOST_POLICKA * x));
        this.krabica.posunZvisle(-50 + (Mapa.VELKOST_POLICKA * y));
        
        this.vnutro = new Stvorec();
        this.vnutro.zmenFarbu(Krabica.FARBA_VNUTRA);
        this.vnutro.posunVodorovne(-55 + (Mapa.VELKOST_POLICKA * x));
        this.vnutro.posunZvisle(-45 + (Mapa.VELKOST_POLICKA * y));
        this.vnutro.zmenStranu(20);
        
        this.kruzok = new Kruh();
        this.kruzok.zmenFarbu(Krabica.FARBA_KRUZKU);
        this.kruzok.zmenPriemer(10);
        this.kruzok.posunVodorovne(-10 + (Mapa.VELKOST_POLICKA * x));
        this.kruzok.posunZvisle(-50 + (Mapa.VELKOST_POLICKA * y));
        
        this.polohaX = x;
        this.polohaY = y;
        
        this.jeNaUlozisku = false;
    }
    
    /**
     * Posunie sa do zadaneho smeru presne o jednu velkost policka.
     * 
     * @param smer smer posunu
     */
    public void posun(SmerPosunu smer) {
        if (smer == SmerPosunu.VPRAVO || smer == SmerPosunu.VLAVO) {
            int posunX = Mapa.VELKOST_POLICKA * smer.getPosunX();
            
            this.krabica.posunVodorovne(posunX);
            this.vnutro.posunVodorovne(posunX);
            this.kruzok.posunVodorovne(posunX);
            
            this.polohaX += smer.getPosunX();
        } else {
            int posunY = Mapa.VELKOST_POLICKA * smer.getPosunY();
            
            this.krabica.posunZvisle(posunY);
            this.vnutro.posunZvisle(posunY);
            this.kruzok.posunZvisle(posunY);
            
            this.polohaY += smer.getPosunY();
        }
    }
    
    /**
     * Vrati odpoved na otazku, ci je krabica na ulozisku (vyhernej pozicii).
     * 
     * @return jeNaUlozisku
     */
    public boolean getJeNaUlozisku() {
        return this.jeNaUlozisku;
    }
    
    /**
     * Zmeni vyherny stav krabice podla vstupu.
     * Ak krabica dosiahne vyherny stav, tak sa prefarbi. Ak ho krabica opat strati, tak sa prefarbi na povodnu farbu.
     * 
     * @param naUlozisku ci je krabica na ulozisku alebo nie.
     */
    public void zmenStav(boolean naUlozisku) {
        if (naUlozisku == this.jeNaUlozisku) {
            return;
        }
        this.jeNaUlozisku = !this.jeNaUlozisku;

        if (this.jeNaUlozisku) {
            this.krabica.zmenFarbu(Krabica.FARBA_KRABICE_OZNACENA);
            this.vnutro.zobraz();
            this.kruzok.zmenFarbu(Krabica.FARBA_KRABICE_OZNACENA);
        } else {
            this.krabica.zmenFarbu(Krabica.FARBA_KRABICE_NEOZNACENA);
            this.vnutro.zobraz();
            this.kruzok.zmenFarbu(Krabica.FARBA_KRABICE_NEOZNACENA);
        }
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.krabica.zobraz();
        this.vnutro.zobraz();
        this.kruzok.zobraz();
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.krabica.skry();
        this.vnutro.skry();
        this.kruzok.skry();
    }
    
    /**
     * Vrati aktualnu suradnicu X polohy krabice.
     * 
     * @return polohaX 
     */
    public int getPolohaX() {
        return this.polohaX;
    }
    
    /**
     * Vrati aktualnu suradnicu Y polohy krabice.
     * 
     * @return polohaY 
     */
    public int getPolohaY() {
        return this.polohaY;
    }
}
