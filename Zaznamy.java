import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import javax.swing.JOptionPane;
import java.util.Calendar;

/**
 * Zaznamy nacitavaju jednotlive vyherne tabulky levelov a zapisuju do nich nove zaznamy.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class Zaznamy {
    private static Zaznamy instancia;
    
    private ArrayList<String> zaznamy;
    
    /**
     * Konstruktor inicializuje atributy mapy.
     */
    private Zaznamy() {
        this.zaznamy = new ArrayList<String>();
    }
    
    /**
     * Vrati instanciu zaznamov.
     * 
     * @return instancia
     */
    public static Zaznamy getInstancia() {
        if (Zaznamy.instancia == null) {
            Zaznamy.instancia = new Zaznamy();
        }
        return Zaznamy.instancia;
    }
    
    /**
     * Prida zaznam o vyhre hraca do vysledkovej tabulky daneho levelu.
     * 
     * @param meno meno hraca
     * @param cisloLevelu poradove cislo levelu
     * @param pocetKrokov pocet krokov ktore hrac vykonal pocas levelu
     */
    public void pridajZaznam(String meno, int cisloLevelu, int pocetKrokov, long stotiny) throws IOException {
        if (cisloLevelu <= 0 || cisloLevelu >= Mapa.POCET_LEVELOV) {
            return;
        }
        
        this.nacitajZaznamy(cisloLevelu);
        
        Calendar c = Calendar.getInstance();
        String zaznam = String.format("%te.%<tm.%<tY  %<tH:%<tM:%<tS  %9d%21d,%-15d%s"
            , c, pocetKrokov, stotiny / 100, stotiny % 100, meno);
        this.zaznamy.add(zaznam);
        
        this.uloz(cisloLevelu);
    }
    
    /**
     * Zobrazi okno s vysledkovou tabulkov pre dany level.
     */
    public void vypis() {
        String vypis = new String();
        for (String aktualny : this.zaznamy) {
            vypis += aktualny + "\n";
        }
        
        JOptionPane.showMessageDialog(null, vypis);
    }
    
    /**
     * Nacita zo suboru vysledkovu tabulku pre dany level
     * 
     * @param level poradove cislo levelu
     */
    private void nacitajZaznamy(int level) throws IOException {
        this.vynuluj();
        
        File subor = new File("Vysledky/Vysledky z " + level + ". levelu.txt");
        if (!subor.exists()) {
            return;
        }
        
        Scanner citac = new Scanner(subor);
        String zaznam = new String();
        while (citac.hasNextLine()) {
            zaznam = citac.nextLine();
            this.zaznamy.add(zaznam);
        }
        
        citac.close();
    }
    
    /**
     * Ulozi do suboru do vysledkovej tabulky prisluchajucej levelu poslednu vyhru hraca.
     * 
     * @param level poradove cislo levelu
     */
    private void uloz(int level) throws IOException {
        File subor = new File("Vysledky/Vysledky z " + level + ". levelu.txt");
        PrintWriter zapisovac = new PrintWriter(new FileOutputStream(subor, true));
        
        zapisovac.println(this.zaznamy.get(this.zaznamy.size() - 1));
        
        zapisovac.close();
    }
    
    /**
     * Vynuluje pole zaznamov.
     */
    private void vynuluj() {
        this.zaznamy.clear();
    }
}
