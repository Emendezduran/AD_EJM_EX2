/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exa15;

import java.io.Serializable;

/**
 *
 * @author oracle
 */
public class Componente implements Serializable{
    
    private String codc;
    private  String nomec;
    private int graxa;
    
     public Componente() {
    }
     
    public Componente(String codc, String nomec, int graxa) {
        this.codc = codc;
        this.nomec = nomec;
        this.graxa = graxa;
    }

    public String getCodc() {
        return codc;
    }

    public void setCodc(String codc) {
        this.codc = codc;
    }

    public String getNomec() {
        return nomec;
    }

    public void setNomec(String nomec) {
        this.nomec = nomec;
    }

    public int getGraxa() {
        return graxa;
    }

    public void setGraxa(int graxa) {
        this.graxa = graxa;
    }
    
    
}
