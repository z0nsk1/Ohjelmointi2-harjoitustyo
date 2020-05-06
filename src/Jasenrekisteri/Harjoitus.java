package Jasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author Jonni ja Mikko
 * @version 6.5.2020
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
        paivaMaara = 00000000;
        kloAloitus = 0;
        kloLopetus = 0;
        paikalla = jNro; //jatkossa kutsuu kyseista jasenta jolla on tama id
        poissa = -1; //poissa olleen jasenen id, -1 jos kukaan ei poissa/paikalla
        hLisatietoja = "";
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
     * Antaa k:n kentan sisallon merkkijonona
     * @param k monenenko kentan sisalto palautetaan
     * @return kentan sisalto merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + harjoitusId;
        case 1: return "" + paivaMaara;
        case 2: return "" + kloAloitus;
        case 3: return "" + kloLopetus;
        default: return "mehu";
        }
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
        if ( harjoitusId < 0 ) seuraavaNro = -1*harjoitusId + 1;
    }
    
    
    /**
     * @param nr asettaa jasenen paikalla olevaksi
     */
    public void setPaikalla(int nr) {
        paikalla = nr;
    }
    
    
    /**
     * muuttaa harjoituksen id negatiiviseksi, jolloin se poistuu kayttajalta nakyvista
     */
    public void setPoistaHarj() {
        harjoitusId = -1*harjoitusId;
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


    /**
     * @return poissa
     */
    public int getJPoissa() {
        return poissa;
    }

    
    /**
     * @return lisatietoja harjoituksesta
     */
    public String getHLisatietoja() {
        return hLisatietoja;
    }
    
    
    /**
     * @param s teksti
     * @return mahdollisen virheen
     */
    public String setPvm(String s) {
        if (!s.matches("[0-9]*")) return "Paivamaarassa saa olla pelkkia numeroita muodossa 'vvvvkkpp'(ei siis pisteitä ja pilkkuja)";
        if (s.length() > 8) return "Liikaa merkkeja";
        if (s.length() < 8) return "Liian vahan merkkeja";
        paivaMaara = Integer.parseInt(s);
        return null;
    }


    /**
     * @param s teksti
     * @return mahdollisen virheen
     */
    public String setAloitus(String s) {
        if (!s.matches("[0-9]*")) return "Aloitusajassa saa olla pelkkia numeroita muodossa 'hhmm' (ei siis pisteitä ja pilkkuja)";
        if (s.length() > 4) return "Liikaa merkkeja";
        if (s.length() < 4) return "Liian vahan merkkeja";
        kloAloitus = Integer.parseInt(s);
        return null;
    }


    /**
     * @param s teksti
     * @return mahdollisen virheen
     */
    public String setLopetus(String s) {
        if (!s.matches("[0-9]*")) return "Lopetusajassa saa olla pelkkia numeroita muodossa 'hhmm' (ei siis pisteitä ja pilkkuja)";
        if (s.length() > 4) return "Liikaa merkkeja";
        if (s.length() < 4) return "Liian vahan merkkeja";
        kloLopetus = Integer.parseInt(s);
        return null;
    }


    /**
     * @param s teskti
     * @return mahdollisen virheen
     */
    public String setLisat(String s) {
        if (s.contains("\n") | s.contains("\r")) return "Enteria ei saa kayttaa";
        hLisatietoja = s;
        return null;
    }
    
    
    /**
     * @param args ei k�yt�ss�
     */
    public static void main(String[] args) {
        Harjoitus harj = new Harjoitus();
        harj.hTiedot(1);
        harj.tulosta(System.out);
    }
}
