import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * Sokoban je riadiaca trieda celej aplikacie. Prijima spravy od triedy OvladanieSokobanu na posuny panacika 
 * a restartovanie levelov. Rozhoduje o moznych posunoch panacika a uspesnom ukonceni levelov.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Sokoban {
    private Mapa mapa;
    private Zaznamy zaznamy;
    
    private TrojciselnyDisplej krokovyDisplej;
    private TrojciselnyDisplej sekundovyDisplej;
    private Tlacidlo tlacSpat;
    private Tlacidlo tlacRestart;
    private Tlacidlo tlacExit;
    private boolean tlacidlaSuAktivne;
    
    private Panacik panacik;
    private ArrayList<Krabica> krabice;
    private ArrayList<Ulozisko> uloziska;
    
    private boolean[][] steny;
    private SmerPosunu smer;
    private SmerPosunu poslednySmer;
    private Krabica poslednaKrabica;
    private boolean uzSaPosunulSpat;
    
    private String meno;
    private int pocetKrokov;
    private int cisloMapy;
    
    private long stopkyZaciatok;
    private long stopkyKoniec;
    private long stvrtinySekundy;
    private boolean sekDispZastaveny;
    
    /**
     * Konstruktor inicializuje atributy sokobanu a spusti prvu mapu (prvy level).
     */
    public Sokoban() throws IOException {
        this.mapa = Mapa.getInstancia();
        this.zaznamy = Zaznamy.getInstancia();
        
        this.krokovyDisplej = new TrojciselnyDisplej(600, 5);
        this.sekundovyDisplej = new TrojciselnyDisplej(5, 5);
        this.tlacSpat = new Tlacidlo(TypTlacidla.KROK_SPAT, 600, 67);
        this.tlacRestart = new Tlacidlo(TypTlacidla.RESTART, 5, 67);
        this.tlacExit = new Tlacidlo(TypTlacidla.EXIT, 5, 238);
        this.tlacidlaSuAktivne = false;
        
        this.panacik = null;
        this.krabice = null;
        this.uloziska = null;
        
        this.steny = null;
        this.smer = null;
        this.poslednySmer = null;
        this.poslednaKrabica = null;
        this.uzSaPosunulSpat = true;
        
        this.meno = null;
        this.pocetKrokov = 0;
        this.cisloMapy = 0;
        
        this.stopkyZaciatok = 0;
        this.stopkyKoniec = 0;
        this.stvrtinySekundy = 0;
        this.sekDispZastaveny = false;
        
        this.dajMapu();
    }
    
    /**
     * Posunie panacika (a krabicu) podla vstupu ak je to mozne. Na konci skontroluje vyhru.
     * 
     * @param smer smer posunu
     */
    public void posunPanacika(SmerPosunu smer) throws IOException {
        this.smer = smer;
        Krabica krabicaPredoMnou = null;
        //this.poslednaKrabica = null;
        
        int posunX = this.panacik.getPolohaX() + this.smer.getPosunX();
        int posunY = this.panacik.getPolohaY() + this.smer.getPosunY();
        
        int polohaPanacikaX = this.panacik.getPolohaX();
        int polohaPanacikaY = this.panacik.getPolohaY();
        
        if (this.jeTuStena(polohaPanacikaX, polohaPanacikaY)) {
            return;
        }
        
        if (!this.mozemSaPosunut(polohaPanacikaX, polohaPanacikaY)) {
            if (this.jeTuStena(posunX, posunY)) {
                return;
            }
            
            if (this.mozemSaPosunut(posunX, posunY)) {
                krabicaPredoMnou = this.getKrabicaPodlaPolohy(posunX, posunY);
                if (krabicaPredoMnou == null) {
                    return;
                }
                
                krabicaPredoMnou.posun(this.smer);
                this.zmenStavKrabiceAkJeToPotrebne(krabicaPredoMnou);
            } else {
                return;
            }
        }
        
        this.panacik.posun(this.smer);
        this.poslednySmer = this.smer;
        this.uzSaPosunulSpat = false;
        this.pocetKrokov++;
        this.krokovyDisplej.pridaj();
        
        this.poslednaKrabica = krabicaPredoMnou;
        
        this.ukonciLevelADajDalsiAkVyhral();
    }
    
    /**
     * Restartuje level. Ak je level prvy alebo posledny, tak posunie na dalsi level.
     */
    public void restartujLevel() throws IOException {
        if (this.cisloMapy <= 0) {
            this.vypisManual();
            this.nacitajMeno();
            this.cisloMapy++;
        } else if (this.cisloMapy >= Mapa.POCET_LEVELOV) {
            this.cisloMapy++;
        }
        this.dajMapu();
    }
    
    /**
     * Caka na zadanie suradnic X a Y z mysi. Reaguje na talcidla Spat, Restart a Exit. 
     * Ak su tlacidla neaktivne, tak sa metoda nevykonava.
     */
    public void vyberSuradnice(int x, int y) throws IOException {
        if (!this.tlacidlaSuAktivne) {
            return;
        }
        
        if (x >= this.tlacSpat.getX() && x <= (this.tlacSpat.getX() + Tlacidlo.SIRKA) && 
            y >= this.tlacSpat.getY() && y <= (this.tlacSpat.getY() + Tlacidlo.VYSKA)) {
            this.krokSpat();
        } else if (x >= this.tlacRestart.getX() && x <= (this.tlacRestart.getX() + Tlacidlo.SIRKA) && 
                   y >= this.tlacRestart.getY() && y <= (this.tlacRestart.getY() + Tlacidlo.VYSKA)) {
            this.restartujLevel();
        } else if (x >= this.tlacExit.getX() && x <= (this.tlacExit.getX() + Tlacidlo.SIRKA) && 
                   y >= this.tlacExit.getY() && y <= (this.tlacExit.getY() + Tlacidlo.VYSKA)) {
            System.exit(0);
        }
    }
    
    /**
     * Pripocitava sekundy a zobrazuje ich na sekundovom displeji. Ak je displej zastaveny, tak sa metoda nevykonava.
     */
    public void tik() {
        if (this.sekDispZastaveny) {
            return;
        }
        this.stvrtinySekundy++;
        
        if (this.stvrtinySekundy % 4 == 0) {
            this.sekundovyDisplej.pridaj();
        }
    }
    
    /**
     * Ak hrac vyhral, ukonci level, zobrazi vyhernu tabulku daneho levelu a posle spravu na nacitanie dalsieho levelu.
     */
    private void ukonciLevelADajDalsiAkVyhral() throws IOException {
        for (Krabica aktualna : this.krabice) {
            if (!aktualna.getJeNaUlozisku()) {
                return;
            }
        }
        
        this.sekDispZastaveny = true;
        this.stopkyKoniec = System.nanoTime();
        long casStotiny = (this.stopkyKoniec - this.stopkyZaciatok) / 10000000;
        
        this.zaznamy.pridajZaznam(this.meno, this.cisloMapy, this.pocetKrokov, casStotiny);
        this.zaznamy.vypis();
        
        this.cisloMapy++;
        this.dajMapu();
    }
    
    /**
     * Panacik sa posunie o jeden krok spat. Ak posunul aj krabicu, tak ta sa tiez vrati o jeden krok spat.
     * Neda sa vykonat viackrat za sebou.
     */
    private void krokSpat() throws IOException {
        if (this.uzSaPosunulSpat) {
            return;
        }
        
        if (this.poslednaKrabica != null) {
            switch (this.poslednySmer) {
                case DOLE:
                    this.poslednaKrabica.posun(SmerPosunu.HORE);
                    break;
                case HORE:
                    this.poslednaKrabica.posun(SmerPosunu.DOLE);
                    break;
                case VPRAVO:
                    this.poslednaKrabica.posun(SmerPosunu.VLAVO);
                    break;
                case VLAVO:
                    this.poslednaKrabica.posun(SmerPosunu.VPRAVO);
                    break;
                default:
                    return;
            }
            this.zmenStavKrabiceAkJeToPotrebne(this.poslednaKrabica);
        }
        
        switch (this.poslednySmer) {
            case DOLE:
                this.posunPanacika(SmerPosunu.HORE);
                break;
            case HORE:
                this.posunPanacika(SmerPosunu.DOLE);
                break;
            case VPRAVO:
                this.posunPanacika(SmerPosunu.VLAVO);
                break;
            case VLAVO:
                this.posunPanacika(SmerPosunu.VPRAVO);
                break;
            default:
                return;
        }
        
        this.pocetKrokov -= 2;
        this.krokovyDisplej.uber();
        this.uzSaPosunulSpat = true;
    }
    
    /**
     * Nacita zo suboru a vypise manual hry.
     */
    private void vypisManual() throws IOException {
        File subor = new File("Manual/Manual.txt");
        if (!subor.exists()) {
            return;
        }
        String popis = new String();
        
        Scanner citac = new Scanner(subor);
        String riadok = new String();
        while (citac.hasNextLine()) {
            riadok = citac.nextLine();
            popis += riadok + "\n";
        }
        citac.close();
        
        JOptionPane.showMessageDialog(null, popis);
    }
    
    /**
     * Skryje a vymaze staru mapu a nacita a zostavi dalsi level.
     */
    private void dajMapu() throws IOException {
        if (this.cisloMapy > Mapa.POCET_LEVELOV) {
            this.cisloMapy = 0;
        }
        
        if (this.panacik != null) { 
            this.panacik.skry();
        }
        this.krokovyDisplej.skry();
        this.sekundovyDisplej.skry();
        this.tlacSpat.skry();
        this.tlacRestart.skry();
        this.tlacExit.skry();
        this.tlacidlaSuAktivne = false;
        
        this.uzSaPosunulSpat = true;
        this.poslednaKrabica = null;
        
        this.mapa.skryMapu();
        this.mapa.vymazMapu();
        
        this.mapa.nacitajMapu(this.cisloMapy);
        this.mapa.zobrazMapu();
        this.steny = this.mapa.getMapa();
        this.krabice = this.mapa.getKrabice();
        this.uloziska = this.mapa.getUloziska();
        
        this.panacik = new Panacik(this.mapa.getZaciatocnaPoziciaPanacikaX(), this.mapa.getZaciatocnaPoziciaPanacikaY());
        this.panacik.zobraz();
        
        if (this.cisloMapy > 0 && this.cisloMapy < Mapa.POCET_LEVELOV) {
            this.restartujDisplejePocitadlaTlacidla();
        }
    }
    
    /**
     * Vynuluje pocitadlo poctu krokov, displej pocitania krokov a sekund, zobrazi tlacidla Reset, Spat a Exit, 
     * spusti stopky.
     */
    private void restartujDisplejePocitadlaTlacidla() {
        this.pocetKrokov = 0;
        this.krokovyDisplej.vynuluj();
        this.krokovyDisplej.zobraz();
        
        this.stvrtinySekundy = 0;
        this.sekundovyDisplej.vynuluj();
        this.sekundovyDisplej.zobraz();
        
        this.tlacSpat.zobraz();
        this.tlacRestart.zobraz();
        this.tlacExit.zobraz();
        this.tlacidlaSuAktivne = true;
        
        this.sekDispZastaveny = false;
        this.stopkyZaciatok = System.nanoTime();
    }
    
    /**
     * Nacita meno hraca. Meno hraca nesmie byt prazdny retazec, inak bude nacitavat meno hraca odznovu.
     */
    private void nacitajMeno() {
        do {
            this.meno = JOptionPane.showInputDialog("Zadaj svoje meno:");
        } while (this.meno.equals(""));
    }
    
    /**
     * Skontroluje ci je na danych suradniciach stena.
     * 
     * @param x suradnica X danej pozicie
     * @param y suradnica Y danej pozicie
     * 
     * @return odpoved ci je na danych suradniciach stena
     */
    private boolean jeTuStena(int x, int y) {
        return this.steny[y + this.smer.getPosunY()][x + this.smer.getPosunX()];
    }
    
    /**
     * Skontroluje ci je na danych suradniciach krabica.
     * 
     * @param x suradnica X danej pozicie
     * @param y suradnica Y danej pozicie
     * 
     * @return odpoved ci je na danych suradniciach krabica
     */
    private boolean mozemSaPosunut(int x, int y) {
        if (this.getKrabicaPodlaPolohy(x + this.smer.getPosunX(), y + this.smer.getPosunY()) != null) {
            return false;
        }
        return true;
    }
    
    /**
     * Vrati krabicu podla suradnic XY.
     * 
     * @param x suradnica X danej pozicie
     * @param y suradnica Y danej pozicie
     * 
     * @return Krabica alebo null
     */
    private Krabica getKrabicaPodlaPolohy(int x, int y) {
        for (Krabica aktualna : this.krabice) {
            if (aktualna.getPolohaX() == x && aktualna.getPolohaY() == y)  {
                return aktualna;
            }
        }
        return null;
    }
    
    /**
     * Skontroluje ci je na danych suradniciach ulozisko.
     * 
     * @param x suradnica X danej pozicie
     * @param y suradnica Y danej pozicie
     * 
     * @return odpoved ci je na danych suradniciach ulozisko
     */
    private boolean jeTuUlozisko(int x, int y) {
        for (Ulozisko aktualne : this.uloziska) {
            if (aktualne.getPolohaX() == x && aktualne.getPolohaY() == y) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Zmeni vyherny stav danej krabice.
     * 
     * @param krabica ktorej meni stav
     */
    private void zmenStavKrabiceAkJeToPotrebne(Krabica krabica) {
        if (this.jeTuUlozisko(krabica.getPolohaX(), krabica.getPolohaY())) {
            krabica.zmenStav(true);
        } else {
            krabica.zmenStav(false);
        }
    }
}
