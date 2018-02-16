/**
 * Tlacidlo je trieda na vykreslenie roznych typov tlacidiel.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Tlacidlo {
    public static final int SIRKA = 115;
    public static final int VYSKA = 57;
    private static final int OKRAJ = 5;
    
    private TypTlacidla typ;
    private int x;
    private int y;
    
    private Obdlznik pozadie;
    private Obdlznik olemovanie;
    
    private Elipsa stopa;
    private Kruh pata;
    private Stvorec stvorec;
    
    private Kruh hodiny;
    private Obdlznik rucickaV;
    private Obdlznik rucickaM;
    
    private Kruh kruzok;
    
    /**
     * Konstruktor inicializuje vsetky casti tlacidla.
     * 
     * @param typ tlacidla, ktore chcelme vytvorit
     * @param x suradnica X laveho horneho rohu tlacidla
     * @param y suradnica Y laveho horneho rohu tlacidla
     */
    public Tlacidlo(TypTlacidla typ, int x, int y) {
        this.typ = typ;
        this.x = x;
        this.y = y;
        
        this.pozadie = new Obdlznik();
        this.pozadie.zmenStrany(Tlacidlo.SIRKA, Tlacidlo.VYSKA);
        this.pozadie.posunVodorovne(-60 + x);
        this.pozadie.posunZvisle(-50 + y);
        this.pozadie.zmenFarbu("red");
        
        this.olemovanie = new Obdlznik();
        this.olemovanie.zmenStrany(Tlacidlo.SIRKA - Tlacidlo.OKRAJ * 2, Tlacidlo.VYSKA - Tlacidlo.OKRAJ * 2);
        this.olemovanie.posunVodorovne(-60 + x + Tlacidlo.OKRAJ);
        this.olemovanie.posunZvisle(-50 + y + Tlacidlo.OKRAJ);
        this.olemovanie.zmenFarbu("black");
        
        switch (typ) {
            case KROK_SPAT:
                this.stopa = null;
                this.pata = null;
                this.stvorec = null;
                break;
            case RESTART:
                this.hodiny = null;
                this.rucickaV = null;
                this.rucickaM = null;
                break;
            case EXIT:
                this.kruzok = null;
                break;
            default:
                return;
        }
        this.piktogram();
    }
    
    /**
     * Vrati X-ovu suradnicu tlacidla.
     * 
     * @return x
     */
    public int getX() {
        return this.x;
    }
    
    /**
     * Vrati Y-ovu suradnicu tlacidla.
     * 
     * @return y
     */
    public int getY() {
        return this.y;
    }
    
    /**
     * Zobrazi sa.
     */
    public void zobraz() {
        this.pozadie.zobraz();
        this.olemovanie.zobraz();
        switch (this.typ) {
            case KROK_SPAT:
                this.stopa.zobraz();
                this.stvorec.zobraz();
                this.pata.zobraz();
                break;
            case RESTART:
                this.hodiny.zobraz();
                this.rucickaV.zobraz();
                this.rucickaM.zobraz();
                break;
            case EXIT:
                this.kruzok.zobraz();
                break;
            default:
                return;
        }
    }
    
    /**
     * Skryje sa.
     */
    public void skry() {
        this.pozadie.skry();
        this.olemovanie.skry();
        switch (this.typ) {
            case KROK_SPAT:
                this.stopa.skry();
                this.stvorec.skry();
                this.pata.skry();
                break;
            case RESTART:
                this.hodiny.skry();
                this.rucickaV.skry();
                this.rucickaM.skry();
                break;
            case EXIT:
                this.kruzok.skry();
                break;
            default:
                return;
        }
    }
    
    /**
     * Vykresli specificky piktogram na tlacidlo.
     */
    private void piktogram() {
        switch (this.typ) {
            case KROK_SPAT:
                this.stopa = new Elipsa();
                this.stopa.zmenFarbu("yellow");
                this.stopa.posunVodorovne(-5 + this.x);
                this.stopa.posunZvisle(-47 + this.y);
                
                this.stvorec = new Stvorec();
                this.stvorec.zmenFarbu("black");
                this.stvorec.posunVodorovne(0 + this.x);
                this.stvorec.posunZvisle(-37 + this.y);
                
                this.pata = new Kruh();
                this.pata.zmenFarbu("yellow");
                this.pata.zmenPriemer(24);
                this.pata.posunVodorovne(55 + this.x);
                this.pata.posunZvisle(-44 + this.y);
                break;
            case RESTART:
                this.hodiny = new Kruh();
                this.hodiny.zmenFarbu("yellow");
                this.hodiny.posunVodorovne(22 + this.x);
                this.hodiny.posunZvisle(-48 + this.y);
                
                this.rucickaV = new Obdlznik();
                this.rucickaV.zmenStrany(3, 12);
                this.rucickaV.posunVodorovne(-4 + this.x);
                this.rucickaV.posunZvisle(-33 + this.y);
                
                this.rucickaM = new Obdlznik();
                this.rucickaM.zmenStrany(7, 3);
                this.rucickaM.posunVodorovne(-3 + this.x);
                this.rucickaM.posunZvisle(-24 + this.y);
                break;
            case EXIT:
                this.kruzok = new Kruh();
                this.kruzok.posunVodorovne(22 + this.x);
                this.kruzok.posunZvisle(-48 + this.y);
                this.kruzok.zmenFarbu("red");
                break;
            default:
                return;
        }
    }
}
