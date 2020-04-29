package Jasenrekisteri;

import java.io.File;
import java.util.List;

/**
 * Joukkueluokka, joka johtaa Jasenet- ja Harjoitukset-luokkaan
 * @author mikko
 * @version 19 Mar 2020
 * @example
 * <pre name="testJAVA">
 * #import Jasenrekisteri.SailoException;
 *  private Joukkue joukkue;
 *  private Jasen jasen1;
 *  private Jasen jasen2;
 *  private int jasenId1;
 *  private int jasenId2;
 *  private Harrastus pitsi21;
 *  private Harrastus pitsi11;
 *  private Harrastus pitsi22; 
 *  private Harrastus pitsi12; 
 *  private Harrastus pitsi23;
 *  
 *  // @SuppressWarnings("javadoc")
 *  public void alustaKerho() {
 *    joukkue = new Joukkue();
 *    jasen1 = new Jasen(); jasen1.tiedot(); jasen1.rekisteroi();
 *    jasen2 = new Jasen(); jasen2.tiedot(); jasen2.rekisteroi();
 *    jasenId1 = jasen1.getTunnusNro();
 *    jasenId2 = jasen2.getTunnusNro();
 *    pitsi21 = new Harrastus(jid2); pitsi21.vastaaPitsinNyplays(jid2);
 *    pitsi11 = new Harrastus(jid1); pitsi11.vastaaPitsinNyplays(jid1);
 *    pitsi22 = new Harrastus(jid2); pitsi22.vastaaPitsinNyplays(jid2); 
 *    pitsi12 = new Harrastus(jid1); pitsi12.vastaaPitsinNyplays(jid1); 
 *    pitsi23 = new Harrastus(jid2); pitsi23.vastaaPitsinNyplays(jid2);
 *    try {
 *    kerho.lisaa(aku1);
 *    kerho.lisaa(aku2);
 *    kerho.lisaa(pitsi21);
 *    kerho.lisaa(pitsi11);
 *    kerho.lisaa(pitsi22);
 *    kerho.lisaa(pitsi12);
 *    kerho.lisaa(pitsi23);
 *    } catch ( Exception e) {
 *       System.err.println(e.getMessage());
 *    }
 *  }
 * </pre>
 */
public class Joukkue {
    
    Jasenet jasenet = new Jasenet();
    Harjoitukset harjoitukset = new Harjoitukset();
    
    /**
     * Lisaa uuden jasenen
     * @param jasen Lisattava jasen
     * @throws SailoException jos ei toimi
     * 
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
     * 
     * @throws SailoException jos ei toimi
     */
    public void tallenna() throws SailoException {
        String virhe = "";
        try {
            jasenet.tallenna();
        } catch ( SailoException ex ) {
            virhe = "Jasenet: " + ex.getMessage();
        }
        
        try {
            harjoitukset.tallenna();
        } catch ( SailoException ex ) {
            virhe += "Harjoitukset: " + ex.getMessage();
        }
        if ( !"".equals(virhe) ) throw new SailoException(virhe);
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
     * @param nimi tiedoston nimi
     * @throws SailoException ...
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        jasenet = new Jasenet(); 
        harjoitukset = new Harjoitukset();

        setTiedosto(nimi);
        jasenet.lueTiedostosta();
        harjoitukset.lueTiedostosta();
    }
    
    
    /**
     * Asettaa tiedostojen perusnimet
     * @param nimi uusi nimi
     */
    public void setTiedosto(String nimi) {
        File dir = new File(nimi);
        dir.mkdirs();
        String hakemistonNimi = "";
        if ( !nimi.isEmpty() ) hakemistonNimi = nimi +"/";
        jasenet.setTiedostonNimi(hakemistonNimi + "nimet.dat");
        harjoitukset.setTiedostonNimi(hakemistonNimi + "harjoitukset.dat");
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
            Harjoitus harj1 = new Harjoitus(id1);
            harj1.hTiedot(id1);
            joukkue.lisaa(harj1);
            Harjoitus harj2 = new Harjoitus(id1);
            harj2.hTiedot(id1);
            joukkue.lisaa(harj2);
            Harjoitus harj3 = new Harjoitus(id2);
            harj3.hTiedot(id2);
            joukkue.lisaa(harj3);
            Harjoitus harj4 = new Harjoitus(id2);
            harj4.hTiedot(id2);
            joukkue.lisaa(harj4);
            Harjoitus harj5 = new Harjoitus(id2);
            harj5.hTiedot(id2);
            joukkue.lisaa(harj5);

            
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }

}
