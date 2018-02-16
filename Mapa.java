import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Mapa nacitava jednotlive levely zo suborov a poskytuje ich triede Sokoban.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Mapa {
    public static final int VELKOST_PLATNA_X = 24;
    public static final int VELKOST_PLATNA_Y = 10;
    public static final int POCET_LEVELOV = 11;
    public static final int VELKOST_POLICKA = 30;
    
    private static Mapa instancia = new Mapa();
    
    private ArrayList<Krabica> krabice;
    private ArrayList<Ulozisko> uloziska;
    private ArrayList<Stena> steny;
    
    private boolean[][] poleStien;
    
    private int zaciatocnaPoziciaPanacikaX;
    private int zaciatocnaPoziciaPanacikaY;
    
    /**
     * Konstruktor inicializuje atributy mapy.
     */
    private Mapa() {
        this.krabice = new ArrayList<Krabica>();
        this.uloziska = new ArrayList<Ulozisko>();
        this.steny = new ArrayList<Stena>();

        this.poleStien = new boolean[Mapa.VELKOST_PLATNA_Y][Mapa.VELKOST_PLATNA_X];
        
        this.zaciatocnaPoziciaPanacikaX = 0;
        this.zaciatocnaPoziciaPanacikaY = 0;
    }
    
    /**
     * Vrati instanciu mapy.
     * 
     * @return instancia
     */
    public static Mapa getInstancia() {
        return Mapa.instancia;
    }
    
    /**
     * Nacita level zo subora na zaklade poradoveho cisla levelu.
     * 
     * @param poradie poradove cislo levelu
     */
    public void nacitajMapu(int poradie) throws IOException {
        if (poradie < 0 || poradie > Mapa.POCET_LEVELOV) {
            return;
        }
        
        String nazovLevelu = "Levely/Level " + poradie + ".txt";
        File subor = new File(nazovLevelu);
        if (!subor.exists()) {
            return;
        }
        
        Scanner citac = new Scanner(subor);
        
        int riadky = 0;
        while (citac.hasNextLine()) {
            String riadok = citac.nextLine();
            
            char[] znaky = riadok.toCharArray();
            for (int stlpce = 0; stlpce < riadok.length(); stlpce++) {
                this.poleStien[riadky][stlpce] = znaky[stlpce] == '#';
                switch (znaky[stlpce]) {
                    case '#':
                        this.steny.add(new Stena(stlpce, riadky));
                        break;
                    case 'K':
                        this.krabice.add(new Krabica(stlpce, riadky));
                        break;
                    case 'U':
                        this.uloziska.add(new Ulozisko(stlpce, riadky));
                        break;
                    case 'P':
                        this.zaciatocnaPoziciaPanacikaX = stlpce;
                        this.zaciatocnaPoziciaPanacikaY = riadky;
                        break;
                    default:
                        break;
                }
            }
            riadky++;
        }
        
        citac.close();
    }
    
    /**
     * Zobrazi vytvorenu mapu.
     */
    public void zobrazMapu() {
        for (Stena aktualna : this.steny) {
            aktualna.zobraz();
        }
        
        for (Krabica aktualna : this.krabice) {
            aktualna.zobraz();
        }
        
        for (Ulozisko aktualne : this.uloziska) {
            aktualne.zobraz();
        }
    }
    
    /**
     * Skryje vytvorenu mapu.
     */
    public void skryMapu() {
        for (Stena aktualna : this.steny) {
            aktualna.skry();
        }
        
        for (Krabica aktualna : this.krabice) {
            aktualna.skry();
        }
        
        for (Ulozisko aktualne : this.uloziska) {
            aktualne.skry();
        }
    }
    
    /**
     * Vymaze vytvorenu mapu.
     */
    public void vymazMapu() {
        this.steny.clear();
        this.krabice.clear();
        this.uloziska.clear();
        
        for (int y = 0; y < Mapa.VELKOST_PLATNA_Y; y++) {
            for (int x = 0; x < Mapa.VELKOST_PLATNA_X; x++) {
                this.poleStien[y][x] = false;
            }
        }
    }
    
    /**
     * Vrati kopiu pola stien.
     * 
     * @return poleStien vrati klon atributu
     */
    public boolean[][] getMapa() {
        return this.poleStien.clone();
    }
    
    /**
     * Vrati kopiu pola krabic.
     * 
     * @return krabice vrati klon atributu
     */
    public ArrayList<Krabica> getKrabice() {
        return (ArrayList<Krabica>)this.krabice.clone();
    }
    
    /**
     * Vrati kopiu pola ulozisk.
     * 
     * @return uloziska vrati klon atributu
     */
    public ArrayList<Ulozisko> getUloziska() {
        return (ArrayList<Ulozisko>)this.uloziska.clone();
    }
    
    /**
     * Vrati zaciatocnu suradnicu X polohy panacika.
     * 
     * @return zaciatocnaPoziciaPanacikaX
     */
    public int getZaciatocnaPoziciaPanacikaX() {
        return this.zaciatocnaPoziciaPanacikaX;
    }
    
    /**
     * Vrati zaciatocnu suradnicu Y polohy panacika.
     * 
     * @return zaciatocnaPoziciaPanacikaY
     */
    public int getZaciatocnaPoziciaPanacikaY() {
        return this.zaciatocnaPoziciaPanacikaY;
    }
}
