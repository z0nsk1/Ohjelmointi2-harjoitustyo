package fxJasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

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
        out.println(String.format("%02d", jasenId, 3) + ", " + nimi + ", " + pelinumero);
        out.println("puh: " + puh);
        out.println(jLisatietoja);
        out.println("Aktiivisuus: " + aktiivisuus);
        out.println("Cooper: " + cooper);
        out.println("Paikalla: " + jPaikalla + " | Poissa: " + jPoissa);
        out.println("Syntymavuosi: " + svuosi);
    }
    
    
    /**
     * Jasenen tiedot merkkijonona
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                svuosi + "|" +
                pelinumero + "|" +
                puh + "|" +
                jPaikalla + "|" +
                jPoissa + "|" +
                aktiivisuus + "|" +
                cooper + "|" +
                jLisatietoja + "|";
    }
    
    
    /**
     * Tulostetaan henkil�n tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * @return Rekister�id��n j�sen ja annetaan sille id
     */
    public int rekisteroi() {
        jasenId = seuraavaNro;
        seuraavaNro++;
        return jasenId;
    }
    
    /**
     * @return palauttaa j�senen id:n
     */
    public int getTunnusNro() {
        return jasenId;
    }
    
    
    private void setTunnusNro(int nr) {
        jasenId = nr;
        if (jasenId >= seuraavaNro) seuraavaNro = jasenId + 1;
    }


    /**
     * Erottaa tiedostosta tiedot
     * @param rivi tiedostossa luettava rivi
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        svuosi = Mjonot.erota(sb, '|', svuosi);
        pelinumero = Mjonot.erota(sb, '|', pelinumero);
        puh = Mjonot.erota(sb, '|', puh);
        jPaikalla = Mjonot.erota(sb, '|', jPaikalla);
        jPoissa = Mjonot.erota(sb, '|', jPoissa);
        aktiivisuus = Mjonot.erota(sb, '|', aktiivisuus);
        cooper = Mjonot.erota(sb, '|', cooper);
        jLisatietoja = Mjonot.erota(sb, '|', jLisatietoja);
    }
    
    /**
     * @param args ei k�yt�ss� toistaiseksi
     */
    public static void main(String[] args) {
        Jasen jasen1 = new Jasen();
        Jasen jasen2 = new Jasen();
        
        jasen1.rekisteroi();
        jasen2.rekisteroi();
        
        jasen1.tiedot();
        jasen2.tiedot();
        
        jasen1.tulosta(System.out);
        jasen2.tulosta(System.out);
    }
}
