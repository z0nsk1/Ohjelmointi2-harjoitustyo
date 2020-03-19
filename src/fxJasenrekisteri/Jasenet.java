package fxJasenrekisteri;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Jasenet {
    private static final int    MAX_JASENIA     = 20;
    private int                 lkm             =  0;
    private String              tiedostonNimi   = "";
    private Jasen               alkiot[]        = new Jasen[MAX_JASENIA];
    
    
    /**
     * Muodostaja
     */
    public Jasenet() {
        // 
    }
    
    
    /**
     * Lis�t��n uusi j�sen, jos vanha taulukko t�yttyy, luodaan uusi, 
     * johon kopioidaan vanhan taulukon alkiot
     * @param jasen lis�tt�v� jasen
     * @throws SailoException jos liikaa j�senia
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa J�seni�(:D)");
        alkiot[lkm] = jasen;
        lkm++;
    }
    
    
    /**
     * @param i j�senen indeksi
     * @return alkiot taulukon i:s j�sen
     * @throws IndexOutOfBoundsException jos indeksi on taulukon ulkopuolelta
     */
    public Jasen anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i) {
            throw new IndexOutOfBoundsException("Virheellinen indeksi: "+ i);
            }
        return alkiot[i]; 
    }
    
    
    /**
     * @param hakemisto tiedoston sijainti
     * @throws SailoException jos tiedoston luku ei onnistu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/MahottomatMestarit.dat";
        throw new SailoException("Tiedoston " + tiedostonNimi + " luku ei viel� onnistu");
    }
    
    
    /**
     * Getteri palauttaa j�senten lukum��r�n kyseisess� joukkueessa
     * @return j�senten lukum��r�n
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Jasenet jasenet = new Jasenet();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        
        try {
            jasenet.lisaa(jasen1);
            jasenet.lisaa(jasen2);
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
