package Jasenrekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import fi.jyu.mit.ohj2.WildChars;


/**
 * @author z0nsk1
 * @version 19.3.2020
 * @example
 * <pre name="test_lisaa_poista">
 * // lisaa(harj), poista(harjoitus)
 * #import java.io.File;
 * #import java.util.List;
 * // #import fxJasenrekisteri.SailoException;
 *  Harjoitukset harjotukset = new Harjoitukset();
 *  Harjoitus harj1 = new Harjoitus(); harj1.parse("1|20191215|1500|1700|2|4|Hyva treeni");
 *  Harjoitus harj2 = new Harjoitus(); harj2.parse("2|20191220|1800|2000|5|1|Hyva treeni");
 *  Harjoitus harj3 = new Harjoitus(); harj3.parse("3|20191011|1600|1900|1|2|Hyva treeni"); 
 *  Harjoitus harj4 = new Harjoitus(); harj4.parse("4|20190907|1700|1830|3|4|Hyva treeni"); 
 *  Harjoitus harj5 = new Harjoitus(); harj5.parse("5|20180316|1800|1930|5|4|Hyva treeni"); 
 *  harjotukset.lisaa(harj1);
 *  harjotukset.lisaa(harj2);
 *  harjotukset.lisaa(harj3);
 *  harjotukset.lisaa(harj4);
 *  List<Harjoitus> h = harjotukset.annaHarjoitukset(3);
 *  h.size() === 1; 
 *  h.get(0) === harj3;
 * </pre>
 */
public class Harjoitukset implements Iterable<Harjoitus> {
    
    private String      tiedostonNimi   = "";
    @SuppressWarnings("unused")
    private boolean     muutettu        = false;
    
    /** 
     * Taulukko harjoituksista (kokoelma)
     */
    private final Collection<Harjoitus> harjoitukset = new ArrayList<Harjoitus>();
    
    
    /**
     * Alustaja
     */
    public Harjoitukset() {
        // 
    }
    
    
    /**
     * @return harjoitukset-taulukon koko
     */
    public int getLkm() {
        return harjoitukset.size();
    }
    
    
    /**
     * 
     * @param harj lisattava harjoitus
     */
    public void lisaa(Harjoitus harj) {
        harjoitukset.add(harj);
    }
    
    
    /**
     * Aliohjelma tallentamiselle
     * @throws SailoException jos ei onnistu
     */
    public void tallenna() throws SailoException {
        File ftied = new File(tiedostonNimi);

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            for (int i = 1; i < 1+getLkm(); i++) {
                Collection<Harjoitus> har = annaHarjoitukset(i);
                if (har.size() == 0) continue;
                for (int j = 0; j < har.size(); j++ ) {
                    fo.println(har.toArray()[j]);
                }
            }
        } catch ( FileNotFoundException ex ) {
            throw new SailoException("Tiedosto " + ftied.getName() + " ei aukea");
        } catch ( IOException ex ) {
            throw new SailoException("Tiedoston " + ftied.getName() + " kirjoittamisessa ongelmia");
        }
    }
    
    
    /**
     * @param tiedosto tiedoston nimi
     * @throws SailoException jos tiedoston luku ei onnistu
     */
    public void lueTiedostosta(String tiedosto) throws SailoException {
        setTiedostonNimi(tiedosto);
        try ( BufferedReader fi = new BufferedReader(new FileReader(getTiedostonNimi())) ) {
            String rivi = "";

            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Harjoitus harj = new Harjoitus();
                harj.parse(rivi);
                lisaa(harj);
            }
            muutettu = false;
        } catch ( FileNotFoundException e ) {
            throw new SailoException("Tiedosto " + getTiedostonNimi() + " ei aukea");
        } catch ( IOException e ) {
            throw new SailoException("Ongelmia tiedoston kanssa: " + e.getMessage());
        }

    }
    
    
    /**
     * @param tiedosto tiedoston nimi
     */
    public void setTiedostonNimi(String tiedosto) {
        tiedostonNimi = tiedosto; 
    }


    private String getTiedostonNimi() {
        return tiedostonNimi;
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisest� tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
    }
    
    
    /**
     * Hakee kaikki harjoitukset, joissa jasen on ollut paikalla
     * @param tunnusNroH harjoituksen tunnusnumero
     * @return lista harjoituksista, joissa jasen on ollut paikalla
     */
    public List<Harjoitus> annaHarjoitukset(int tunnusNroH) {
        int tunnusH = tunnusNroH;
        List<Harjoitus> palautus = new ArrayList<Harjoitus>();
        for (Harjoitus har : harjoitukset) {
            int ha = har.getTunnusNro();
            if (ha == -1*tunnusH ) tunnusH = -1*tunnusNroH;
            if (ha == tunnusH) palautus.add(har);
        }
        return palautus;
    }
    
    
    
    
    /**
     * @param hakuehto ehto
     * @param k indexi
     * @param i tutkittava harjoitus
     * @return lista
     */
    public Collection<Harjoitus> etsi(String hakuehto, int k, int i) { 
        String ehto = "*"; 
        if ( hakuehto != null && hakuehto.length() > 0 ) ehto = hakuehto; 
        int hk = k; 
        if ( hk < 0 ) hk = 1;
        Collection<Harjoitus> loytyneet = new ArrayList<Harjoitus>(); 
        for (Harjoitus harjoitus : harjoitukset) { 
            if (WildChars.onkoSamat(harjoitus.anna(hk) ,ehto) && i == harjoitus.getTunnusNro()) loytyneet.add(harjoitus);   
        } 
        //  TODO: lajittelua varten vertailija  
        return loytyneet; 
    }
    
    
    /**
     * Testiohjelma luokkaa varten
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Harjoitukset harjoitukset1 = new Harjoitukset();
        Harjoitus testi1 = new Harjoitus();
        testi1.hTiedot(1);
        Harjoitus testi2 = new Harjoitus();
        testi2.hTiedot(2);
        
        harjoitukset1.lisaa(testi1);
        harjoitukset1.lisaa(testi2);
        
        testi1.tulosta(System.out);
        testi2.tulosta(System.out);
    }


    @Override
    public Iterator<Harjoitus> iterator() {
        // TODO Auto-generated method stub
        return harjoitukset.iterator();
    }
}
