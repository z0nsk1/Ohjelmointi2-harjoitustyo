package Jasenrekisteri;

/**
 * @author Jonni ja Mikko
 * @version 6.5.2020
 */
public class SailoException extends Exception {
    
    private static final long serialVersionUID = 1L;

    /**
     * Korvaa virheilmoituksen parametrina tuodulla k�ytt�j�yst�v�llisemm�ll� viestill�
     * @param viesti Poikkeuksen viesti
     */
    public SailoException(String viesti) {
        super(viesti);
    }
}
