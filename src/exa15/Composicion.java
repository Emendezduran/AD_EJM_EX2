/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa15;

/**
 *
 * @author oracle
 */
public class Composicion {
    
    private String codp;
    private String codc;
    private int peso;


    public Composicion() {
    }

    public Composicion(String codp, String codc, int peso) {
        this.codp = codp;
        this.codc = codc;
        this.peso = peso;
    }

    public String getCodp() {
        return codp;
    }

    public void setCodp(String codp) {
        this.codp = codp;
    }

    public String getCodc() {
        return codc;
    }

    public void setCodc(String codc) {
        this.codc = codc;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "Composicion{" + "codp=" + codp + ", codc=" + codc + ", peso=" + peso + '}';
    }

    
}
