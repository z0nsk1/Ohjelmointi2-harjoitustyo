package fxJasenrekisteri;
/**
 * Joukkueluokka, joka johtaa Jasenet- ja Harjoitukset-luokkaan
 * @author mikko
 * @version 19 Mar 2020
 *
 */
public class Joukkue {
    
    Jasenet jasenet = new Jasenet();
    //Harjoitukset harjoitukset = new Harjoitukset();
    
    /**
     * Lisaa uuden jasenen
     * @param jasen Lisattava jasen
     * @throws SailoException jos ei toimi
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     * @param i indeksi
     * @return viite i jäseneen
     * 
     */
    public Jasen annaJasen(int i) {
        return jasenet.anna(i);
    }
    
    
    /**
     *  Antaa jasenten lukumaaran
     * @return Jasenten lukumaaran
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    
    /**
     * Testiohjelma 
     * @param args ei kaytossa
     */
    public static void main (String args[]) {
        Joukkue joukkue = new Joukkue();
        
        try {
            Jasen jasen1 = new Jasen();
            Jasen jasen2 = new Jasen();
            
            jasen1.rekisteroi();
            jasen2.rekisteroi();
            
            jasen1.tiedot();
            jasen2.tiedot();
            
            joukkue.lisaa(jasen1);
            joukkue.lisaa(jasen2);
            
            /*jasen1.tulosta(System.out);
            jasen2.tulosta(System.out); */
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
