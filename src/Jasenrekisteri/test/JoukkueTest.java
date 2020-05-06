package Jasenrekisteri.test;
// Generated by ComTest BEGIN
import Jasenrekisteri.SailoException;
import static org.junit.Assert.*;
import org.junit.*;
import Jasenrekisteri.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.05.06 20:01:26 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class JoukkueTest {



  // Generated by ComTest BEGIN
  /** testJoukkue */
  @Test
  public void testJoukkue() {    // Joukkue: 12
    Joukkue joukkue; 
    Jasen jasen1; 
    Jasen jasen2; 
    int jasenId1; 
    int jasenId2; 
    Harjoitus harj1; 
    Harjoitus harj2; 
    Harjoitus harj3; 
    Harjoitus harj4; 
    Harjoitus harj5; 
    joukkue = new Joukkue(); 
    jasen1 = new Jasen(); jasen1.tiedot(); jasen1.rekisteroi(); 
    jasen2 = new Jasen(); jasen2.tiedot(); jasen2.rekisteroi(); 
    jasenId1 = jasen1.getTunnusNro(); 
    jasenId2 = jasen2.getTunnusNro(); 
    System.out.println(jasenId1 + ", " + jasenId2); 
    harj1 = new Harjoitus(); harj1.parse("1|20191215|1500|1700|2|4|Hyva treeni"); 
    harj2 = new Harjoitus(); harj2.parse("2|20191220|1800|2000|5|1|Hyva treeni"); 
    harj3 = new Harjoitus(); harj3.parse("3|20191011|1600|1900|1|2|Hyva treeni"); 
    harj4 = new Harjoitus(); harj4.parse("4|20190907|1700|1830|3|4|Hyva treeni"); 
    harj5 = new Harjoitus(); harj5.parse("5|20180316|1800|1930|5|4|Hyva treeni"); 
    try {
    joukkue.lisaa(jasen1); 
    joukkue.lisaa(jasen2); 
    joukkue.lisaa(harj1); 
    joukkue.lisaa(harj2); 
    joukkue.lisaa(harj3); 
    joukkue.lisaa(harj4); 
    joukkue.lisaa(harj5); 
    } catch ( Exception e) {
    System.err.println(e.getMessage()); 
    }
  } // Generated by ComTest END
}