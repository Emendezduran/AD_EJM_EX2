package exa15;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

public class Plato implements Serializable {

    private String codigop;
    private String nomep;
    private HashMap<Componente, Integer> componentes = new HashMap<>();

    public Plato() {
        this("", "");
    }

    public Plato(String codigo, String nome) {
        this.codigop = codigo;
        this.nomep = nome;
    }

    public void setCodigop(String code) {
        this.codigop = code;
    }

    public String getCodigop() {
        return codigop;
    }

    public void setNomep(String nome) {
        this.nomep = nome;
    }

    public String getNomep() {
        return nomep;
    }

    public HashMap<Componente, Integer> getComponentes() {
        return componentes;
    }

    public String toString() {
        return "codigo plato : " + codigop + "\n"
                + "nome plato  : " + nomep + "\n"
                + "grasatotal : " + calcularContenidoTotal();
    }
    
    public static Integer calcularContenido(int peso, int grasa) {
        int contGrasa = peso / 100 * grasa;
        return contGrasa;
    }
    
    public Integer calcularContenidoTotal(){
        int cont = 0;
        for (Entry<Componente,Integer> entry : componentes.entrySet()){
            cont += calcularContenido(entry.getValue(), entry.getKey().getGraxa());
        }
        return cont;
    }

}
