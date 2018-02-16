import java.io.IOException;

/**
 * SokobanMain - main trieda celej aplikacie.
 * 
 * @author Emanuel Zaymus
 * @version 2016/12/14
 */
public class SokobanMain {
    /**
     * Konstruktor vytvori instancie tried OvladanieSokobanu a Manazer a da manazerovy spravovat objekt ovladanie.
     */
    public static void main(String[] args) throws IOException {
        OvladanieSokobanu ovladanie = new OvladanieSokobanu();
        Manazer manazer = new Manazer();
        
        manazer.spravujObjekt(ovladanie);
    }
}
