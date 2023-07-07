package model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

public class Business implements Serializable {

    // variáveis de instância
    private String id, name, city, state;
    private List<String> categories;

    /**
     * Construtor por omissão do tipo Business.
     */
    public Business(){
        this.id = this.name = this.city = this.state = null;
        this.categories = new ArrayList<>();
    }

    /**
     * Construtor parametrizado do tipo Business.
     * @param id Id
     * @param nome Nome
     * @param cidade Cidade
     * @param estado State
     * @param categorias Categorias
     */
    public Business(String id, String nome, String cidade, String estado, List<String> categorias){
        this.id = id;
        this.name = nome;
        this.city = cidade;
        this.state = estado;
        this.categories = new ArrayList<>(categorias);
    }

    /**
     * Construtor de cópia do tipo Business.
     * @param b Business a ser copiado
     */
    public Business(model.Business b){
        this.id = b.getID();
        this.name = b.getName();
        this.city = b.getCity();
        this.state = b.getState();
        this.categories = b.getCategories();
    }

    /**
     * Método que devolve as categorias de um Business.
     * @return Categorias
     */
    private List<String> getCategories() {
        return new ArrayList<>(this.categories);
    }

    /**
     * Método que atualiza as categorias de um Business.
     * @param categorias Novas categorias
     */
    private void setCategories(List<String> categorias){
        this.categories = new ArrayList<>(categorias);
    }

    /**
     * Método que devolve o id de um Business.
     * @return Id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Método que atualiza o id de um Business.
     * @param id Novo id
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Método que devolve o nome de um Business.
     * @return Nome
     */
    public String getName() {
        return this.name;
    }

    /**
     * Método que atualiza o nome de um Business.
     * @param name Novo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que devolve a cidade de um Business.
     * @return Cidade
     */
    public String getCity() {
        return this.city;
    }

    /**
     * Método que atualiza a cidade de um Business.
     * @param city Nova cidade
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Método que devolve o estado de um Business.
     * @return Estado
     */
    public String getState() {
        return this.state;
    }

    /**
     * Método que atualiza o state de um Business.
     * @param state Novo estado
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Método que devolve a informação de um Business em formato String.
     * @return String com info textual
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("|Business|");
        sb.append(" Id: ").append(this.id);
        sb.append(" Nome: ").append(this.name);
        sb.append(" City: ").append(this.city);
        sb.append(" State: ").append(this.state);
        sb.append(" Categorias: ").append(this.categories);
        return sb.toString();
    }

    /**
     * Método de clonagem de um Business.
     * @return Objeto do tipo Business
     */
    public model.Business clone() {
        return new model.Business(this);
    }

    /**
     * Método de igualdade entre dois Business.
     * Redefinição do método equals de Object.
     * @param obj Objeto a ser comparado
     * @return true, caso sejam iguais, false caso contrário
     */
    public boolean equals(Object obj){
        if (this == obj) return true;
        if (obj == null || this.getClass() != obj.getClass()) return false;
        model.Business j = (model.Business) obj;
        return  this.id.equals(j.getID()) &&
                this.name.equals( j.getName()) &&
                this.city.equals(j.getCity()) &&
                this.state.equals(j.getState()) &&
                this.categories.equals(j.getCategories());
    }

    /**
     * Método que verifica a validade de um Business.
     * @return true caso seja valido, false caso contrário
     */
    private static boolean isValidBusiness(String[] campos) {
        return   campos.length == 5 || campos.length == 4  &&
                !campos[0].isEmpty() &&
                !campos[1].isEmpty() &&
                !campos[2].isEmpty() &&
                !campos[3].isEmpty();
    }

    /**
     * Método que cria um Business a partir de uma linha.
     * @return Business
     */
    public static model.Business initBusinessFromLine(String line) {
        String[] campos = line.split(";");
        String[] aux = new String[5];
        if (campos.length == 4) {
            aux[0] = campos[0];
            aux[1] = campos[1];
            aux[2] = campos[2];
            aux[3] = campos[3];
            aux[4] = "";
        }
        else{
            aux = campos;
        }

        if (isValidBusiness(aux))
            return new model.Business(aux[0], aux[1], aux[2], aux[3],
                    new ArrayList<>(Arrays.asList(aux[4].split(","))));
        else return null;
    }

}
