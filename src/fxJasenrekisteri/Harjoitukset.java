package fxJasenrekisteri;

import java.util.ArrayList;
import java.util.Collection;


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
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
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
        throw new SailoException("Ei osata vielä tallentaa tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * @param args ei kaytossa
     */
    public static void main(String[] args) {
        Harjoitukset harjoitukset1 = new Harjoitukset();
        Harjoitus testi1 = new Harjoitus();
        testi1.pvm();
        testi1.hTiedot();
        Harjoitus testi2 = new Harjoitus();
        testi2.pvm();
        testi2.hTiedot();
        
        harjoitukset1.lisaa(testi1);
        harjoitukset1.lisaa(testi2);

    }
}
