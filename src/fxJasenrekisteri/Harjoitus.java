package fxJasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Harjoitus {
    private String  harjoitusId = "202003201700";;
    private int     paikalla      = 0;   //paikalla olleen jasenen id
    private int     poissa;              
    private String  hLisatietoja  = "";
    
    
    /**
     * Asetetaan harjoituksen tiedot
     */
    public void hTiedot() {
        paikalla = 1; //jatkossa kutsuu kyseista jasenta jolla on tama id
        poissa = -1; //poissa olleen jasenen id, -1 jos kukaan ei poissa/paikalla
        hLisatietoja = "Hyvin porukkaa paikalla";
    }
    
    
    /**
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(harjoitusId);
        out.println(paikalla); 
        out.println(poissa);
        out.println(hLisatietoja);
    }
    
    
    /**
     * Tulostetaan harjoituksen tiedot
     * @param os tietovirta johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Otetaan id tamanhetkisesta ajasta (oletus kun luodaan harjoitus)
     */
    public void pvm() {
        DateTimeFormatter muoto = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime aika = LocalDateTime.now();
        harjoitusId = muoto.format(aika);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Harjoitus harj = new Harjoitus();
        harj.pvm();
        harj.hTiedot();
        harj.tulosta(System.out);
    }
}
