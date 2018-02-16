import java.io.IOException;

/**
 * OvladanieSokobanu - trieda spolupracujuca s triedou Manazer, posiela spravy Sokobanu na posuny panacika 
 * a restartovanie levelov.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class OvladanieSokobanu {
    private Sokoban sokoban;
    
    /**
     * Konstruktor vytvory novy Sokoban.
     */
    public OvladanieSokobanu() throws IOException {
        this.sokoban = new Sokoban();
    }
    
    /**
     * Posle spravu Sokobanu, aby dal dalsi level ak hrac vyhral.
     */
    public void aktivuj() throws IOException {
        this.sokoban.restartujLevel();
    }
    
    /**
     * Posle spravu Sokobanu, aby posunul panacika dolu ak je to mozne.
     */
    public void posunDole() throws IOException {
        this.sokoban.posunPanacika(SmerPosunu.DOLE);
    }
    
    /**
     * Posle spravu Sokobanu, aby posunul panacika hore ak je to mozne.
     */
    public void posunHore() throws IOException {
        this.sokoban.posunPanacika(SmerPosunu.HORE);
    }
    
    /**
     * Posle spravu Sokobanu, aby posunul panacika vpravo ak je to mozne.
     */
    public void posunVpravo() throws IOException {
        this.sokoban.posunPanacika(SmerPosunu.VPRAVO);
    }
    
    /**
     * Posle spravu Sokobanu, aby posunul panacika vlavo ak je to mozne.
     */
    public void posunVlavo() throws IOException {
        this.sokoban.posunPanacika(SmerPosunu.VLAVO);
    }
    
    /**
     * Caka na suradnice X a Y zadane mysou a posle spravu Sokobanu.
     */
    public void vyberSuradnice(int x, int  y) throws IOException {
        this.sokoban.vyberSuradnice(x, y);
    }
    
    /**
     * Pravidelne posiela spravu Sokobanu na vykonanie metody plynutia casu.
     */
    public void tik() {
        this.sokoban.tik();
    }
    
    /**
     * Zavrie celu aplikaciu.
     */
    public void zrus() {
        System.exit(0);
    }
}
