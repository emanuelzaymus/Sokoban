/**
 * Panacik, ktory sa dokaze pohybovat po platne do styroch smerov.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Panacik {
    private static final String FARBA_OKA = "yellow";
    
    private Kruh hlava;
    private Obdlznik usta;
    private Kruh oko1;
    private Kruh oko2;
    
    private int polohaX;
    private int polohaY;
    
    /**
     * Konstruktor vytvori panacika na zadanej pozicii.
     * 
     * @param x suradnica X laveho horneho rohu panacika
     * @param y suradnica Y laveho horneho rohu panacika
     */
    public Panacik(int x, int y) {
        this.hlava = new Kruh();
        this.hlava.posunVodorovne(-20 + (Mapa.VELKOST_POLICKA * x));
        this.hlava.posunZvisle(-60 + (Mapa.VELKOST_POLICKA * y));
        
        this.usta = new Obdlznik();
        this.usta.zmenStrany(14, 4);
        this.usta.posunVodorovne(-31 - 20 + (Mapa.VELKOST_POLICKA * x));
        this.usta.posunZvisle(30 - 60 + (Mapa.VELKOST_POLICKA * y));
        
        this.oko1 = new Kruh();
        this.oko1.zmenPriemer(5);
        this.oko1.zmenFarbu(Panacik.FARBA_OKA);
        this.oko1.posunVodorovne(7 - 20 + (Mapa.VELKOST_POLICKA * x));
        this.oko1.posunZvisle(8 - 60 + (Mapa.VELKOST_POLICKA * y));
        
        this.oko2 = new Kruh();
        this.oko2.zmenPriemer(5);
        this.oko2.zmenFarbu(Panacik.FARBA_OKA);
        this.oko2.posunVodorovne(18 - 20 + (Mapa.VELKOST_POLICKA * x));
        this.oko2.posunZvisle(8 - 60 + (Mapa.VELKOST_POLICKA * y));
        
        this.polohaX = x;
        this.polohaY = y;
    }
    
    /**
     * Posunie sa do zadaneho smeru presne o jednu velkost policka.
     * 
     * @param smer smer posunu
     */
    public void posun(SmerPosunu smer) {
        if (smer == SmerPosunu.VPRAVO || smer == SmerPosunu.VLAVO) {
            int posunX = Mapa.VELKOST_POLICKA * smer.getPosunX();
            
            this.hlava.posunVodorovne(posunX);
            this.usta.posunVodorovne(posunX);
            this.oko1.posunVodorovne(posunX);
            this.oko2.posunVodorovne(posunX);
            
            this.polohaX += smer.getPosunX();
        } else {
            int posunY = Mapa.VELKOST_POLICKA * smer.getPosunY();
            
            this.hlava.posunZvisle(posunY);
            this.usta.posunZvisle(posunY);
            this.oko1.posunZvisle(posunY);
            this.oko2.posunZvisle(posunY);
            
            this.polohaY += smer.getPosunY();
        }
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.hlava.zobraz();
        this.usta.zobraz();
        this.oko1.zobraz();
        this.oko2.zobraz();
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.hlava.skry();
        this.usta.skry();
        this.oko1.skry();
        this.oko2.skry();
    }
    
    /**
     * Vrati aktualnu suradnicu X polohy panacika.
     * 
     * @return polohaX 
     */
    public int getPolohaX() {
        return this.polohaX;
    }
    
    /**
     * Vrati aktualnu suradnicu Y polohy panacika.
     * 
     * @return polohaY 
     */
    public int getPolohaY() {
        return this.polohaY;
    }
}
