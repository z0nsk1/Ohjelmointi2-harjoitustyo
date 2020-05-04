package Jasenrekisteri;

import java.io.OutputStream;
import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * @author z0nsk1
 * @version 19.3.2020
 * @example
 * <pre name="test_rekisteroi">
 * // rekisteroi()
 *   Jasen jasen1 = new Jasen();
 *   jasen1.getTunnusNro() === 0;
 *   jasen1.rekisteroi();
 *   Jasen jasen2 = new Jasen();
 *   jasen2.rekisteroi();
 *   int n1 = jasen1.getTunnusNro();
 *   int n2 = jasen2.getTunnusNro();
 *   n1 === n2-1;
 * </pre>
 * 
 * @example
 * <pre name="test_parse">
 * // parse(String)
 *   Jasen jasen = new Jasen();
 *   jasen.parse("2|Erkki Esimerkki|2020|10|0123456789|0|0|100.0|3000|Esimerkki teksti");
 *   jasen.getTunnusNro() === 2;
 *   jasen.toString().startsWith("2|Erkki Esimerkki|2020|") === true; // on enemm�kin kuin 3 kentt��, siksi loppu |
 *
 *   jasen.rekisteroi();
 *   int n = jasen.getTunnusNro();     // Otetaan merkkijonosta vain tunnusnumero
 *   jasen.rekisteroi();           // ja tarkistetaan ett� seuraavalla kertaa tulee yht� isompi
 *   jasen.getTunnusNro() === n+1;
 * </pre>
 * 
 * @example
 * <pre name="test_equals">
 * // equals(Jasen)
 *   Jasen jasen1 = new Jasen();
 *   jasen1.parse("1|Erkki Esimerkki|2020|1|0123456789|0|0|100.0|3000|Esimerkki teksti");
 *   Jasen jasen2 = new Jasen();
 *   jasen2.parse("1|Erkki Esimerkki|2020|1|0123456789|0|0|100.0|3000|Esimerkki teksti");
 *   Jasen jasen3 = new Jasen();
 *   jasen3.parse("2|Marko Esimerkki|2000|1|0120000789|23|7|100.0|2000|Tosi rehti");
 *   
 *   jasen1.toString().equals(jasen2.toString()) === true;
 *   jasen2.toString().equals(jasen1.toString()) === true;
 *   jasen1.toString().equals(jasen3.toString()) === false;
 *   jasen3.toString().equals(jasen2.toString()) === false;
 *   jasen3.toString().equals(null)   === false;
 * </pre>
 * 
 * @example
 * <pre name="test">
 * //getAktiivisuus() 
 * Jasen jasen = new Jasen();
 * jasen.parse("2|Marko Esimerkki|2000|1|0120000789|23|7|100.0|2000|Tosi rehti");
 * jasen.getAktiivisuus() === "76.7"; 
 * </pre>
 */
public class Jasen {
    private int jasenId;
    private String nimi = "";
    private int svuosi = 0;
    private int pelinumero = 0;
    private String puh = "";
    private int jPaikalla = 0;
    private int jPoissa = 0;
    private double aktiivisuus = 0;
    private int cooper = 0;
    private String jLisatietoja = "";

    private static int seuraavaNro = 1;

    /**
     * @return jasenen nimi
     * @example
     * <pre name="test">
     * 
     * </pre>
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Asetetaan jasenelle tiedot
     */
    public void tiedot() {
        nimi = "Uusi jasen";
        svuosi = 0000;
        puh = "0000000000";
        cooper = 2000;
        jPaikalla = 0;
        jPoissa = 0;
        aktiivisuus = 100;
        jLisatietoja = "";
        pelinumero = 0;
    }


    /**
     * @param out fghj
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%02d", jasenId, 3) + ", " + nimi + ", "
                + pelinumero);
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
        return "" + getTunnusNro() + "|" + nimi + "|" + svuosi + "|"
                + pelinumero + "|" + puh + "|" + jPaikalla + "|" + jPoissa + "|"
                + aktiivisuus + "|" + cooper + "|" + jLisatietoja;
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
        if (jasenId >= seuraavaNro)
            seuraavaNro = jasenId + 1;
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
     * @return Syntymavuosi
     */
    public int getSVuosi() {
        return svuosi;
    }


    /**
     * @return pelinumero
     */
    public int getPelinumero() {
        return pelinumero;
    }


    /**
     * @return puhelinnumero
     */
    public String getPuh() {
        return puh;
    }


    /**
     * @return paikallaolot lkm
     */
    public int getPaikalla() {
        return jPaikalla;
    }


    /**
     * @return poissaolot lkm
     */
    public int getPoissa() {
        return jPoissa;
    }


    /**
     * @return aktiivisuus prosenttina
     */
    public String getAktiivisuus() {
        double pa = jPaikalla;
        double po = jPoissa;
        if (pa + po == 0)
            return "Ei tilastoja";
        return String.format("%.1f", pa * 100 / (pa + po));
    }


    /**
     * @return cooper
     */
    public int getCooper() {
        return cooper;
    }


    /**
     * @return lisatiedot
     */
    public String getLisatietoja() {
        return jLisatietoja;
    }


    /**
     * 
     * @param s nimi
     * @return null
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }


    /**
     * 
     * @param s vuosi
     * @return null
     */
    public String setSVuosi(String s) {
        if (!s.matches("[0-9]*")) return "Syntymavuodessa saa olla pelkkia numeroita";
        if (s.length() > 4) return "Liikaa merkkeja";
        if (s.length() < 4) return "Liian vahan merkkeja";
        svuosi = Integer.valueOf(s);
        return null;
    }


    /**
     * 
     * @param s puhelin
     * @return null
     */
    public String setPuh(String s) {
        if (!s.matches("[0-9]*")) return "Puhelinnumerossa saa olla pelkkia numeroita";
        if (s.length() > 10) return "Liikaa merkkeja";
        if (s.length() < 10) return "Liian vahan merkkeja";
        puh = s;
        return null;
    }


    /**
     * 
     * @param s cooper
     * @return null
     */
    public String setCooper(String s) {
        if (!s.matches("[0-9]*")) return "Cooperissa saa olla pelkkia numeroita";
        if (s.length() > 4) return "Liikaa merkkeja";
        cooper = Integer.valueOf(s);
        return null;
    }


    /**
     * @param s teksti
     * @return mahdolinen virge
     */
    public String setLisat(String s) {
        jLisatietoja = s;
        return null;
    }


    /**
     * @param s teksti
     * @return mahdolinen virge
     */
    public String setPeliNro(String s) {
        if (!s.matches("[0-9]*")) return "Cooperissa saa olla pelkkia numeroita";
        if (s.length() > 2) return "Liikaa merkkeja";
        pelinumero = Integer.valueOf(s);
        return null;
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
