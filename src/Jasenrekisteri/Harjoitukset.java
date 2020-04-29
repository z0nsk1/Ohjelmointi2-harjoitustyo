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


/**
 * @author z0nsk1
 * @version 19.3.2020
 * @example
 * <pre name="test_lisaa_poista">
 * // lisaa(harj), poista(harjoitus)
 * #import java.io.File;
 * // #import fxJasenrekisteri.SailoException;
 *  Harjoitukset harjoitukset = new Harjoitukset();
 *  Harjoitus harj1 = new Harjoitus(); harj1.parse("1|20191215|1500|1700|2|4|Hyva treeni");
 *  Harjoitus harj2 = new Harjoitus(); harj2.parse("2|20191220|1800|2000|5|1|Hyva treeni");
 *  Harjoitus harj3 = new Harjoitus(); harj3.parse("3|20191011|1600|1900|1|2|Hyva treeni"); 
 *  Harjoitus harj4 = new Harjoitus(); harj4.parse("4|20190907|1700|1830|3|4|Hyva treeni"); 
 *  Harjoitus harj5 = new Harjoitus(); harj5.parse("5|20180316|1800|1930|5|4|Hyva treeni"); 
 *  harjoitukset.lisaa(harj1);
 *  harjoitukset.lisaa(harj2);
 *  harjoitukset.lisaa(harj3);
 *  harjoitukset.lisaa(harj4);
 *  // List<Harjoitus> h = harjoitukset.annaHarrastukset(1);
 *  // h.size() === 1; 
 *  // h.get(0) === harj4;
 * </pre>
 * 
 * @example
 * <pre name="test_tiedosto">
 * #THROWS SailoException 
 * #import java.io.File;
 *  Harjoitukset harjoitukset = new Harjoitukset();
 *  Harjoitus harj1 = new Harjoitus(); harj1.parse("1|20191215|1500|1700|2|4|Hyva treeni");
 *  Harjoitus harj2 = new Harjoitus(); harj2.parse("2|20191220|1800|2000|5|1|Hyva treeni");
 *  Harjoitus harj3 = new Harjoitus(); harj3.parse("3|20191011|1600|1900|1|2|Hyva treeni"); 
 *  Harjoitus harj4 = new Harjoitus(); harj4.parse("4|20190907|1700|1830|3|4|Hyva treeni"); 
 *  Harjoitus harj5 = new Harjoitus(); harj5.parse("5|20180316|1800|1930|5|4|Hyva treeni"); 
 *  String tiedNimi = "testiJoukkue";
 *  File ftied = new File(tiedNimi+".dat");
 *  ftied.delete();
 *  harjoitukset.lueTiedostosta(tiedNimi); #THROWS SailoException
 *  harjoitukset.lisaa(harj1);
 *  harjoitukset.lisaa(harj2);
 *  harjoitukset.lisaa(harj3);
 *  harjoitukset.lisaa(harj4);
 *  harjoitukset.lisaa(harj5);
 *  harjoitukset.tallenna();
 *  harjoitukset = new Harjoitukset();
 *  harjoitukset.lueTiedostosta(tiedNimi);
 *  Iterator<Harjoitus> i = harjoitukset.iterator();
 *  i.next().toString() === harj1.toString();
 *  i.next().toString() === harj2.toString();
 *  i.next().toString() === harj3.toString();
 *  i.next().toString() === harj4.toString();
 *  i.next().toString() === harj5.toString();
 *  i.hasNext() === false;
 *  harjoitukset.lisaa(harj5);
 *  harjoitukset.tallenna();
 *  ftied.delete() === true;
 *  File fbak = new File(tiedNimi+".bak");
 *  fbak.delete() === true;
 * </pre>
 * 
 * @example
 * <pre name="test_iterator">
 * // iterator()
 * #PACKAGEIMPORT
 * #import java.util.*;
 * 
 *  Harjoitukset harjoitukset = new Harjoitukset();
 *  Harjoitus harj1 = new Harjoitus(); harjoitukset.lisaa(harj1);
 *  Harjoitus harj2 = new Harjoitus(); harjoitukset.lisaa(harj2);
 *  Harjoitus harj3 = new Harjoitus(); harjoitukset.lisaa(harj3);
 *  Harjoitus harj4 = new Harjoitus(); harjoitukset.lisaa(harj4);
 *  Harjoitus harj5 = new Harjoitus(); harjoitukset.lisaa(harj5);
 * 
 *  Iterator<Harjoitus> i2=harjoitukset.iterator();
 *  i2.next() === harj1;
 *  i2.next() === harj2;
 *  i2.next() === harj3;
 *  i2.next() === harj4;
 *  i2.next() === harj5;
 *  i2.next() === harj6;  #THROWS NoSuchElementException  
 *  
 *  int n = 0;
 *  int jnrot[] = {2,1,2,1,2};
 *  
 *  for ( Harjoitus har:harjoitukset ) { 
 *    har.getJasenNro() === jnrot[n]; n++;  
 *  }
 *  
 *  n === 5;
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
        File ftied = new File("MahottomatMestarit/harjoitukset.dat");

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
     * @param tunnusNroJ jasenen tunnusnumero
     * @return lista harjoituksista, joissa jasen on ollut paikalla
     */
    public List<Harjoitus> annaHarjoitukset(int tunnusNroJ) {
        List<Harjoitus> palautus = new ArrayList<Harjoitus>();
        for (Harjoitus har : harjoitukset) {
            if (har.getJasenNro() == tunnusNroJ) palautus.add(har);
        }
        return palautus;
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
        return null;
    }
}
