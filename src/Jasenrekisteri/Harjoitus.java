package Jasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;

/**
 * @author z0nsk1
 * @version 19.3.2020
 * <pre name="test_rekisteroi">
 * // rekisteroi()
 *   Harjoitus harjoitus1 = new Harjoitus();
 *   harjoitus1.getTunnusNro() === 0;
 *   harjoitus1.rekisteroi(true);
 *   Harjoitus harjoitus2 = new Harjoitus();
 *   harjoitus2.rekisteroi(true);
 *   int n1 = harjoitus1.getTunnusNro();
 *   int n2 = harjoitus2.getTunnusNro();
 *   n1 === n2-1;
 * </pre>
 * 
 * @example
 * <pre name="test_parse">
 * // parse(String)
 *   Harjoitus harjoitus = new Harjoitus();
 *   harjoitus.parse("5|20200324|1600|1730|1|-1|Hyvin porukkaa paikalla|");
 *   harjoitus.getTunnusNro() === 5;
 *   harjoitus.toString().startsWith("5|20200324|1600|1730") === true;
 *
 *   harjoitus.rekisteroi(true);
 *   int n = harjoitus.getTunnusNro();
 *   harjoitus.rekisteroi(true);
 *   harjoitus.getTunnusNro() === n+1;
 * </pre>
 * 
 * @example
 * <pre name="test_equals">
 * // equals(harjoitus)
 *   Harjoitus harjoitus1 = new Harjoitus();
 *   harjoitus1.parse("0|20200324|1600|1730|1|-1|Hyvin porukkaa paikalla|");
 *   Harjoitus harjoitus2 = new Harjoitus();
 *   harjoitus2.parse("0|20200324|1600|1730|1|-1|Hyvin porukkaa paikalla|");
 *   Harjoitus harjoitus3 = new Harjoitus();
 *   harjoitus3.parse("2|20191215|1500|1700|2|4|Hyva treeni");
 *   
 *   harjoitus1.toString().equals(harjoitus2.toString()) === true;
 *   harjoitus2.toString().equals(harjoitus1.toString()) === true;
 *   harjoitus1.toString().equals(harjoitus3.toString()) === false;
 *   harjoitus3.toString().equals(harjoitus2.toString()) === false;
 *   harjoitus3.toString().equals(null)   === false;
 * </pre>
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
     * oletusmuodostaja
     */
    public Harjoitus() {
        //
    }
    
    
    /**
     * @param harjoitusId harjoituksen id
     */
    public Harjoitus(int harjoitusId) {
        this.harjoitusId = harjoitusId;
    }

    
    
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
     * @param i indeksi
     * @param s merkkijono
     * @return harjoituksen ID
     */
    public String asetaTiedot(int i, String s) {
        String st = s.trim();
        StringBuffer sb = new StringBuffer(st);

        switch (i) {
            case 0:
                setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
                return null;
            case 1:
                paivaMaara = Integer.parseInt(st);
                return null;
            case 2:
                kloAloitus = Integer.parseInt(st);
                return null;
            case 3: 
                kloLopetus = Integer.parseInt(st);
                return null;
            case 4:
                paikalla = Integer.parseInt(st);
                return null;
            case 5:
                poissa = Integer.parseInt(st);
                return null;
            case 6:
                hLisatietoja = st;
                return null;
            default:
                return "virhe";
        }
        
        
        
        /*paivaMaara = Mjonot.erota(sb, '|', paivaMaara);
        kloAloitus = Mjonot.erota(sb, '|', kloAloitus);
        kloLopetus = Mjonot.erota(sb, '|', kloLopetus);
        paikalla = Mjonot.erota(sb, '|', getJasenNro());
        poissa = Mjonot.erota(sb, '|', poissa);
        hLisatietoja = Mjonot.erota(sb, '|', hLisatietoja);*/
    }
    
    
    /**
     * Palauttaa paikalla olleen jasenen id
     * @return jasenen id
     */
    public int getJasenNro() {
        return paikalla;
    }
    
    
    private void setTunnusNro(int nr) {
        harjoitusId = nr;
        if ( harjoitusId >= seuraavaNro ) seuraavaNro = harjoitusId + 1;
    }
    
    
    /**
     * @param nr asettaa jasenen paikalla olevaksi
     */
    public void setPaikalla(int nr) {
        paikalla = nr;
    }
    
    
    /**
     * @param nr asettaa jasenen poissa olevaksi
     */
    public void setPoissa(int nr) {
        poissa = nr;
    }
    
    
    /**
     * @param viimeinen jos viimeinen, niin kasvatetaan harjoitusId
     * @return Rekister�id��n j�sen ja annetaan sille id
     */
    public int rekisteroi(boolean viimeinen) {
        harjoitusId = seuraavaNro;
        if (viimeinen)seuraavaNro++;
        return harjoitusId;
    }


    /**
     * @return harjoituksen id
     */
    public int getTunnusNro() {
        return harjoitusId;
    }
    
    
    /**
     * @return harjoituksen paivamaaran
     */
    public int getPv() {
        return paivaMaara;
    }


    /**
     * 
     * @param rivi tiedoston rivi jota ollaan lukemassa
     */
    public void parse(String rivi) {
        StringBuffer sb = new StringBuffer(rivi);
        for (int i = 0; i < 7; i++) {
            asetaTiedot(i, Mjonot.erota(sb, '|'));
        }
    }
    
    
    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Harjoitus harj = new Harjoitus();
        //harj.asetaTiedot();
        harj.hTiedot(1);
        harj.tulosta(System.out);
    }


    /**
     * @return paivamaara
     */
    public int getPvm() {
        return paivaMaara;
    }


    /**
     * @return aloitus kellonaika
     */
    public int getAloitus() {
        return kloAloitus;
    }


    /**
     * @return lopetus kellonaika
     */
    public int getLopetus() {
        return kloLopetus;
    }


    /**
     * @return paikalla
     */
    public int getJPaikalla() {
        return paikalla;
    }


    public int getJPoissa() {
        return poissa;
    }


    public String getHLisatietoja() {
        return hLisatietoja;
    }
}
