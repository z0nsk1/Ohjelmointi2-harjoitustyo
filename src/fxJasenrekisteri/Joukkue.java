package fxJasenrekisteri;
/**
 * MahottomatMestari luokka, joka johtaa Jäsenet ja Harjoitukset luokkaan
 * @author mikko
 * @version 19 Mar 2020
 *
 */
public class Joukkue {
    private final Jasenet jasenet = new Jasenet();
    //private final Harjoitukset harjoitukset = new Harjoitukset();
    
    
    /**
     * Lisää uuden jäsenen
     * @param jasen Lisättävä jäsen
     * @throws SailoException jos ei toimi
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     *  Antaa jäsenten lukumäärän
     * @return Jäsenten lukumäärä
     */
    public int getJasenia() {
        return jasenet.getLkm();
    }
    
    
    /**
     * Testiohjelma 
     * @param args ei käytössä
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
            
            jasen1.tulosta(System.out);
            jasen2.tulosta(System.out);
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
