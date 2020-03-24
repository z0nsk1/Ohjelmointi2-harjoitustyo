package fxJasenrekisteri;

import java.util.List;

/**
 * Joukkueluokka, joka johtaa Jasenet- ja Harjoitukset-luokkaan
 * @author mikko
 * @version 19 Mar 2020
 *
 */
public class Joukkue {
    
    Jasenet jasenet = new Jasenet();
    Harjoitukset harjoitukset = new Harjoitukset();
    
    /**
     * Lisaa uuden jasenen
     * @param jasen Lisattava jasen
     * @throws SailoException jos ei toimi
     */
    public void lisaa(Jasen jasen) throws SailoException {
        jasenet.lisaa(jasen);
    }
    
    
    /**
     * Lisää uuden harjoituksen
     * @param har lisättävä harjoitus
     */
    public void lisaa(Harjoitus har) {
        harjoitukset.lisaa(har);
    }
    
    
    /**
     * @param i indeksi
     * @return viite i j�seneen
     * 
     */
    public Jasen annaJasen(int i) {
        return jasenet.anna(i);
    }
    
    
    /**
     * Antaa harjoitukset, joissa jasen on ollut paikalla
     * @param jasen tutkittava jasen
     * @return lista harjoituksista, joissa paikalla
     */
    public List<Harjoitus> annaHarjoitukset(Jasen jasen) {
        return harjoitukset.annaHarjoitukset(jasen.getTunnusNro());
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
            
            int id1 = jasen1.getTunnusNro();
            int id2 = jasen2.getTunnusNro();
            
            Harjoitus harj11 = new Harjoitus(); harj11.hTiedot(id1); joukkue.lisaa(harj11); harj11.asetaHarjoitusId(false);
            Harjoitus harj12 = new Harjoitus(); harj12.hTiedot(id2); joukkue.lisaa(harj12); harj12.asetaHarjoitusId(true);
            Harjoitus harj21 = new Harjoitus(); harj21.hTiedot(id1); joukkue.lisaa(harj21); harj21.asetaHarjoitusId(false);
            Harjoitus harj22 = new Harjoitus(); harj22.hTiedot(id2); joukkue.lisaa(harj22); harj22.asetaHarjoitusId(true);
            
            jasen1.tulosta(System.out);
            System.out.println();
            jasen2.tulosta(System.out);
            System.out.println();
            harj11.tulosta(System.out);
            System.out.println();
            harj12.tulosta(System.out);
            System.out.println();
            harj21.tulosta(System.out);
            System.out.println();
            harj22.tulosta(System.out);
            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
