package fxJasenrekisteri;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * @author z0nsk1
 * @version 19.3.2020
 *
 */
public class Harjoitukset {
    
    private String tiedostonNimi = "";
    
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
     * @param hakemisto jossa tiedosto sijaitsee
     * @throws SailoException jos ei onnistu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".harj";
        throw new SailoException("Ei osata viel� lukea tiedostoa " + tiedostonNimi);
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
        throw new SailoException("Ei osata viel� tallentaa tiedostoa " + tiedostonNimi);
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
        testi1.asetaHarjoitusId(false);
        testi1.hTiedot(1);
        Harjoitus testi2 = new Harjoitus();
        testi2.asetaHarjoitusId(true);
        testi2.hTiedot(2);
        
        harjoitukset1.lisaa(testi1);
        harjoitukset1.lisaa(testi2);
        
        testi1.tulosta(System.out);
        testi2.tulosta(System.out);
    }
}
