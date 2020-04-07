package fxJasenrekisteri;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
    @SuppressWarnings("unused")
    private boolean             muutettu        = false;
    
    
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
            //} catch ( IOException e ) { // ei heitÃ¤ poikkeusta
            //  throw new SailoException("Tallettamisessa ongelmia: " + e.getMessage());
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
            String rivi = fi.readLine();
            if ( rivi == null ) throw new SailoException("Maksimikoko puuttuu");

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
        return tiedostonNimi + ".dat";
    }
    
    
    /**
     * Luetaan aikaisemmin annetun nimisestä tiedostosta
     * @throws SailoException jos tulee poikkeus
     */
    public void lueTiedostosta() throws SailoException {
        lueTiedostosta(getTiedostonNimi());
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
                System.out.println("Jï¿½sen nro: " + i);
                jasen.tulosta(System.out);
            }   
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    
}
