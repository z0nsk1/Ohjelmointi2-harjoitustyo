package Jasenrekisteri;

/**
 * @author z0nsk1
 * @version 19.3.2020
 *
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
