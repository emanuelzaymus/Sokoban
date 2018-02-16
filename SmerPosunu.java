/**
 * SmerPosunu - mnozina instancii ako sa moze posuvat panacik a krabica.
 * Kazda instancia si nesie aj udaj o posune po X-ovej aj Y-ovej osi.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public enum SmerPosunu {
    DOLE(0, 1),
    HORE(0, -1),
    VPRAVO(1, 0),
    VLAVO(-1, 0);
    
    private int posunX;
    private int posunY;
    
    /**
     * Konstruktor inicializuje atributy instanciam.
     */
    SmerPosunu(int posunX, int posunY) {
        this.posunX = posunX;
        this.posunY = posunY;
    }
    
    /**.
     * Vrati udaj o posune po X-ovej osy.
     * 
     * @return posunX posun danej instancie vodorovne
     */
    public int getPosunX() {
        return this.posunX;
    }
    
    /**.
     * Vrati udaj o posune po Y-ovej osy.
     * 
     * @return posunY posun danej instancie zvisle
     */
    public int getPosunY() {
        return this.posunY;
    }
}
