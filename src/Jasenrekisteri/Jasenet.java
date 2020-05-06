package Jasenrekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Jonni ja Mikko
 * @version 6.5.2020
 * @example
 * <pre name="test_lisaa_poista">
 * #THROWS SailoException
 * #import java.io.File;
 *  Jasenet jasenet = new Jasenet();
 *  Jasen jasen1 = new Jasen(); jasen1.parse("1|Marko Esimerkki|1999|1|0320324789|23|7|0.0|2500|Tosi rehti");
 *  Jasen jasen2 = new Jasen(); jasen2.parse("2|Marko Esimerkki|2002|1|0923423789|30|0|0.0|2200|Juu");
 *  Jasen jasen3 = new Jasen(); jasen3.parse("5|Marko Esimerkki|1990|1|0825464489|15|15|0.0|2700|Ala laita tallasta"); 
 *  Jasen jasen4 = new Jasen(); jasen4.parse("3|Marko Esimerkki|1994|1|0522243789|13|17|0.0|2800|Lisatietoja"); 
 *  Jasen jasen5 = new Jasen(); jasen5.parse("4|Marko Esimerkki|1997|1|0328768789|10|20|0.0|2100|Hyvat kengat"); 
 *  jasenet.lisaa(jasen1);
 *  jasenet.lisaa(jasen2);
 *  jasenet.lisaa(jasen3);
 *  jasenet.lisaa(jasen4);
 *  // List<Jasenet> h = jasenet.annaJasenet(1);
 *  // h.size() === 1; 
 *  // h.get(0) === jasenet4;
 * </pre>
 * 
 * @example
 * <pre name="test_tiedosto">
 * #THROWS SailoException 
 * #import java.io.File;
 *  Jasenet jasenet = new Jasenet();
 *  Jasen jasen1 = new Jasen(); jasen1.parse("1|Marko Esimerkki|1999|1|0320324789|23|7|0.0|2500|Tosi rehti");
 *  Jasen jasen2 = new Jasen(); jasen2.parse("2|Marko Esimerkki|2002|1|0923423789|30|0|0.0|2200|Juu");
 *  Jasen jasen3 = new Jasen(); jasen3.parse("5|Marko Esimerkki|1990|1|0825464489|15|15|0.0|2700|Ala laita tallasta"); 
 *  Jasen jasen4 = new Jasen(); jasen4.parse("3|Marko Esimerkki|1994|1|0522243789|13|17|0.0|2800|Lisatietoja"); 
 *  Jasen jasen5 = new Jasen(); jasen5.parse("4|Marko Esimerkki|1997|1|0328768789|10|20|0.0|2100|Hyvat kengat"); 
 *  String tiedNimi = "testiJoukkue";
 *  File ftied = new File(tiedNimi+".dat");
 *  jasenet.lueTiedostosta(tiedNimi);
 *  jasenet.lisaa(jasen1);
 *  jasenet.lisaa(jasen2);
 *  jasenet.lisaa(jasen3);
 *  jasenet.lisaa(jasen4);
 *  jasenet.lisaa(jasen5);
 *  jasenet.tallenna();
 *  jasenet = new Jasenet();
 *  jasenet.lueTiedostosta(tiedNimi);
 *  Jasen i[] = new Jasen[5];
 *  i[0] = jasen1;
 *  i[1] = jasen2;
 *  i[2] = jasen3;
 *  i[3] = jasen4;
 *  i[4] = jasen5;
 *  i[0].toString() === jasen1.toString();
 *  i[1].toString() === jasen2.toString();
 *  i[2].toString() === jasen3.toString();
 *  i[3].toString() === jasen4.toString();
 *  i[4].toString() === jasen5.toString();
 *  i[5].toString(); #THROWS IndexOutOfBoundsException
 *  jasenet.tallenna();
 *  ftied.delete();
 * </pre>
 */
public class Jasenet {
    private static final int    MAX_JASENIA     = 10;
    private int                 lkm             =  0;
    private String              tiedostonNimi   = "";
    private Jasen               alkiot[]        = new Jasen[MAX_JASENIA];
    @SuppressWarnings("unused")
    private boolean             muutettu        = false;
    
    
    /**
     * Muodostaja
     */
    public Jasenet() {
        // 
    }
    
    
    /**
     * Lisataan uusi jasen, jos vanha taulukko tayttyy, kasvatetaan sita, 
     * johon kopioidaan vanhan taulukon alkiot
     * @param jasen lisattava jasen
     * @throws SailoException jos liikaa jasenia
     */
    public void lisaa(Jasen jasen) throws SailoException {
        if (lkm >= alkiot.length) {
            Jasen taulukko[] = new Jasen[lkm+10];
            for (int i = 0; i < alkiot.length; i++) {
                taulukko[i] = alkiot[i];
            }
            alkiot = taulukko;
        }
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
     * Tallentaa jasen tiedot
     * @throws SailoException jos ei toimi
     */
    public void tallenna() throws SailoException {
        File ftied = new File("MahottomatMestarit/nimet.dat");

        try ( PrintWriter fo = new PrintWriter(new FileWriter(ftied.getCanonicalPath())) ) {
            fo.println("Mahottomat Mestarit");
            for (int i = 0; i < getLkm(); i++) {
                Jasen jasen = anna(i);
                fo.println(jasen.toString());
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
            tiedostonNimi = fi.readLine();
            if ( tiedostonNimi == null ) throw new SailoException("Kerhon nimi puuttuu");
            String rivi = "";
            
            while ( (rivi = fi.readLine()) != null ) {
                rivi = rivi.trim();
                if ( "".equals(rivi) || rivi.charAt(0) == ';' ) continue;
                Jasen jasen = new Jasen();
                jasen.parse(rivi);
                lisaa(jasen);
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
     * Getteri palauttaa jasenten lukumaaran kyseisessa joukkueessa
     * @return jasenten lukumaaran
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Jasenet jasenet = new Jasenet();
        
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        
        jasen1.tiedot();
        jasen2.tiedot();
        
        try {
            jasenet.lisaa(jasen1);
            jasenet.lisaa(jasen2);
            
            for (int i = 0; i < jasenet.getLkm(); i++) {
                Jasen jasen = jasenet.anna(i);
                System.out.println("J�sen nro: " + i);
                jasen.tulosta(System.out);
            }   
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }   
}
