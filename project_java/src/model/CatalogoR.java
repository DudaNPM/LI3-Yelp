package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogoR implements Serializable {

    // variaveis de instância
    private Map<String,Review> reviews;
    private int invalidos; // reviews mal formatadas

    /**
     * Construtor por omissão do tipo CatalogoR.
     */
    public CatalogoR() {
        this.reviews = new HashMap<>();
        this.invalidos = 0;
    }

    /**
     * Construtor parametrizado do tipo CatalogoR.
     * @param reviews Map de reviews
     * @param invalidos Reviews mal formatadas
     */
    public CatalogoR(Map<String, Review> reviews, int invalidos) {
        for (Review r : reviews.values())
            this.reviews.put(r.getID(), r.clone());
        this.invalidos = invalidos;
    }

    /**
     * Construtor de cópia do tipo CatalogoR.
     * @param c Catalogo de reviews
     */
    public CatalogoR(CatalogoR c) {
        this.reviews = c.getReviews();
        this.invalidos = c.getInvalidos();
    }

    /**
     * Método que devolve a hashtable com as reviews.
     * @return reviewC
     */
    public Map<String, Review> getReviews(){
        Map<String, Review> aux = new HashMap<>();
        for (Review r : this.reviews.values())
            aux.put(r.getID(), r.clone());
        return aux;
    }

    /**
     * Metodo que atualiza o map de reviews
     * @param reviews Novas reviews
     */
    public void setReviews(Map<String, Review> reviews){
        for (Review r : reviews.values())
            this.reviews.put(r.getID(), r.clone());
    }

    /**
     * método que devolve o número de reviews rejeitados.
     * @return Numero de reviews invalidos
     */
    public int getInvalidos() {
        return this.invalidos;
    }

    /**
     * Método que inicia um catalogo de reviews a partir de uma linha.
     * @param linhas lista das linhas lidas do ficheiro
     */
    public void initCatalogoRFromLines(List<String> linhas){
        for(String s: linhas){
            Review r = Review.initReviewFromLine(s);
            if (r != null) this.reviews.put(r.getID(), r.clone());
            else this.invalidos++;
        }
    }

}
