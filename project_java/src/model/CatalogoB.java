package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogoB implements Serializable {

    // variaveis de instância
    private Map<String, Business> businesses;
    private int invalidos; // businesses mal formatados

    /**
     * Construtor por omissão do tipo CatalogoB.
     */
    public CatalogoB() {
        this.businesses = new HashMap<>();
        this.invalidos = 0;
    }

    /**
     * Construtor parametrizado do tipo CatalogoB.
     * @param businesses Map de businesses
     * @param invalidos Businesses mal formatados
     */
    public CatalogoB(Map<String, Business> businesses, int invalidos) {
        for (Business b : businesses.values())
            this.businesses.put(b.getID(), b.clone());
        this.invalidos = invalidos;
    }

    /**
     * Construtor de cópia do tipo CatalogoB.
     * @param c Catalogo de Businesses
     */
    public CatalogoB(CatalogoB c) {
        this.businesses = c.getBusinesses();
        this.invalidos = c.getInvalidos();
    }

    /**
     * Método que devolve a hashtable com os businesses.
     * @return businesses
     */
    public Map<String, Business> getBusinesses(){
        Map<String, Business> aux = new HashMap<>();
        for (Business b : this.businesses.values())
            aux.put(b.getID(), b.clone());
        return aux;
    }

    /**
     * método que devolve o número de businesses rejeitados.
     * @return Numero de businesses invalidos
     */
    public int getInvalidos() {
        return this.invalidos;
    }

    /**
     * Método que inicia um catalogo de business a partir de uma linha.
     * @param linhas lista das linhas lidas do ficheiro
     */
    public void initCatalogoBFromLines(List<String> linhas){
        for(String s: linhas){
            Business b = Business.initBusinessFromLine(s);
            if (b != null) this.businesses.put(b.getID(), b.clone());
            else this.invalidos++;
        }
    }

}
