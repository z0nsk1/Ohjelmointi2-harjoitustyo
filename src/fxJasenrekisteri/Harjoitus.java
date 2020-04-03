package fxJasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Harjoitus {
    private int     harjoitusId;
    private int     paivaMaara;
    private int     kloAloitus;
    private int     kloLopetus;
    private int     paikalla      = 0;   //paikalla olleen jasenen id
    private int     poissa;              
    private String  hLisatietoja  = "";
    
    private static int seuraavaNro  = 1;
    
    /**
     * Asetetaan harjoituksen tiedot
     * @param jNro jasenen id
     */
    public void hTiedot(int jNro) {
        paivaMaara = 20200324;
        kloAloitus = 1600;
        kloLopetus = 1730;
        paikalla = jNro; //jatkossa kutsuu kyseista jasenta jolla on tama id
        poissa = -1; //poissa olleen jasenen id, -1 jos kukaan ei poissa/paikalla
        hLisatietoja = "Hyvin porukkaa paikalla";
    }
    
    
    /**
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println("ID: " + harjoitusId);
        out.println("Paivamaara: " + paivaMaara); 
        out.println("Aloitus: " + kloAloitus); 
        out.println("Lopetus: " + kloLopetus); 
        out.println("Jasen paikalla: " + paikalla); 
        out.println("Jasen poissa: " + poissa);
        out.println("Harjoituksen lisatietoja: " + hLisatietoja);
    }
    
    
    /**
     * Tulostetaan harjoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Harjoituksen tiedot merkkijonona
     */
    @Override
    public String toString() {
        return "" +
                harjoitusId + "|" +
                paivaMaara + "|" +
                kloAloitus + "|" +
                kloLopetus + "|" +
                getJasenNro() + "|" +
                poissa + "|" +
                hLisatietoja + "|";
    }
    
    
    /**
     * Otetaan id tamanhetkisesta ajasta (oletus kun luodaan harjoitus)
     * @param eri Jos viitataan samaan harjoitukseen kuin edellinen
     * @return harjoituksen ID
     */
    public int asetaHarjoitusId(boolean eri) {
        harjoitusId = seuraavaNro;
        if (eri) seuraavaNro++;
        return harjoitusId;
        /*DateTimeFormatter muoto = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime aika = LocalDateTime.now();
        harjoitusId = muoto.format(aika);*/
    }
    
    
    /**
     * Palauttaa paikalla olleen jasenen id
     * @return jasenen id
     */
    public int getJasenNro() {
        return paikalla;
    }
    
    
    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Harjoitus harj = new Harjoitus();
        harj.asetaHarjoitusId(false);
        harj.hTiedot(1);
        harj.tulosta(System.out);
    }
}
