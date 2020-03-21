package fxJasenrekisteri;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Jasenet {
    private static final int    MAX_JASENIA     = 5;
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
     * Lisï¿½tï¿½ï¿½n uusi jï¿½sen, jos vanha taulukko tï¿½yttyy, luodaan uusi, 
     * johon kopioidaan vanhan taulukon alkiot
     * @param jasen lisï¿½ttï¿½vï¿½ jasen
     * @throws SailoException jos liikaa jï¿½senia
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa Jasenia(:D)");
        alkiot[lkm] = jasen;
        lkm++;
    }
    
    
    /**
     * @param i jï¿½senen indeksi
     * @return alkiot taulukon i:s jï¿½sen
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
        throw new SailoException("Tiedoston " + tiedostonNimi + " luku ei viela onnistu");
    }
    
    
    /**
     * Getteri palauttaa jï¿½senten lukumï¿½ï¿½rï¿½n kyseisessï¿½ joukkueessa
     * @return jï¿½senten lukumï¿½ï¿½rï¿½n
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param args ei kï¿½ytï¿½ssï¿½
     */
    public static void main(String[] args) {
        Jasenet jasenet = new Jasenet();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        
        jasen1.tiedot();
        jasen2.tiedot();
        
        /*jasen1.tulosta(System.out);
        jasen2.tulosta(System.out);*/        
        
        try {
            jasenet.lisaa(jasen1);
            jasenet.lisaa(jasen2);
            
            for (int i = 0; i < jasenet.getLkm(); i++) {
                Jasen jasen = jasenet.anna(i);
                System.out.println("Jäsen nro: " + i);
                jasen.tulosta(System.out);
            }   
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
