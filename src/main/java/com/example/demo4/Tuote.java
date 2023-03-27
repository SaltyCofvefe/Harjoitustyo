package com.example.demo4;

/**
 *Tuote-luokka.
 * @author Jesse Puustinen
 * @version 1.0 2023/03/27
 */

public class Tuote {
    private String nimi;
    private int maara;
    private double hinta;

    /**

     Konstruktori, joka luo uuden tuotteen annetulla nimellä, maaralla ja hinnalla.
     @param nimi tuotteen nimi
     @param maara tuotteen maara
     @param hinta tuotteen hinta
     */
    public Tuote(String nimi, int maara, double hinta) {
        this.nimi = nimi;
        this.maara = maara;
        this.hinta = hinta;
    }
    /**

     Metodi, joka palauttaa tuotteen nimen.
     @return tuotteen nimi
     */
    public String getNimi() {
        return nimi;
    }
    /**

     Metodi, joka asettaa tuotteen nimen.
     @param nimi tuotteen nimi
     */
    public void setNimi(String nimi) {
        this.nimi = nimi;
    }
    /**

     Metodi, joka palauttaa tuotteen maaran.
     @return tuotteen määrä
     */
    public int getMaara() {
        return maara;
    }
    /**

     Metodi, joka asettaa tuotteen maaran.
     @param maara tuotteen maara
     */
    public void setMaara(int maara) {
        this.maara = maara;
    }
    /**

     Metodi, joka palauttaa tuotteen hinnan.
     @return tuotteen hinta
     */
    public double getHinta() {
        return hinta;
    }
    /**

     Metodi, joka asettaa tuotteen hinnan.
     @param hinta tuotteen hinta
     */
    public void setHinta(double hinta) {
        this.hinta = hinta;
    }
}