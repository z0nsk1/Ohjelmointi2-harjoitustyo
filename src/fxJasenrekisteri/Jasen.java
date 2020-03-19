package fxJasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Jasen {
    private int     jasenId;
    private String  nimi            = "";
    private int     svuosi          = 0;
    private int     pelinumero      = 0;
    private String  puh             = "";
    private int     jPaikalla       = 0;
    private int     jPoissa         = 0;
    private double  aktiivisuus     = 0;
    private int     cooper          = 0;
    private String  jLisatietoja    = "";
    
    private static int seuraavaNro  = 1;
    
    
    /**
     * Jasenen muodostaja
     */
    public Jasen() {
        //
    }
    
    
    /**
     * @return jasenen nimi
     */
    public String getNimi() {
        return nimi;
    }
    
    
    /**
     * Asetetaan jasenelle tiedot
     */
    public void tiedot() {
        nimi = "Mallikas Mikko";
        svuosi = 1984;
        puh = "0123456789";
        cooper = 3350;
        jPaikalla = 10;
        jPoissa = 0;
        aktiivisuus = 100;
        jLisatietoja = "Motivaatio korkea";
        pelinumero = 5;
    }
    
    
    /**
     * @param out fghj
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", jasenId, 3) + ", " + nimi + ", " + pelinumero);
        out.println("  puh: " + puh);
        out.println("  " + jLisatietoja);
        out.println("Aktiivisuus: " + aktiivisuus);
        out.println("Cooper: " + cooper);
        out.println("Paikalla: " + jPaikalla + " | Poissa: " + jPoissa);
        out.println("Syntymävuosi: " + svuosi);
    }
    
    
    /**
     * Tulostetaan henkilön tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * @return Rekisteröidään jäsen ja annetaan sille id
     */
    public int rekisteroi() {
        jasenId = Jasen.seuraavaNro;
        seuraavaNro++;
        return jasenId;
    }
    
    /**
     * @return palauttaa jäsenen id:n
     */
    public int getTunnusNro() {
        return jasenId;
    }
    
    /**
     * @param args ei käytössä toistaiseksi
     */
    public static void main(String[] args) {
        //
    }
}
