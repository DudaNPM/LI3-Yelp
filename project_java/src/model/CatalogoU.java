package model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalogoU implements Serializable {

    // variaveis de instância
    private Map<String,User> users;
    private int invalidos; // users mal formatados

    /**
     * Construtor por omissão do tipo CatalogoU.
     */
    public CatalogoU() {
        this.users = new HashMap<>();
        this.invalidos = 0;
    }

    /**
     * Construtor parametrizado do tipo CatalogoU.
     * @param users Map de users
     * @param invalidos Users mal formatados
     */
    public CatalogoU(Map<String, User> users, int invalidos) {
        for (User u : users.values())
            this.users.put(u.getID(), u.clone());
        this.invalidos = invalidos;
    }

    /**
     * Construtor de cópia do tipo CatalogoU.
     * @param c Catalogo de Users
     */
    public CatalogoU(CatalogoU c) {
        this.users = c.getUsers();
        this.invalidos = c.getInvalidos();
    }

    /**
     * Método que devolve a hashtable com os users.
     * @return users
     */
    public Map<String, User> getUsers(){
        Map<String, User> aux = new HashMap<>();
        for (User u : this.users.values())
            aux.put(u.getID(), u.clone());
        return aux;
    }

    /**
     * método que devolve o número de users rejeitados.
     * @return Numero de users invalidos
     */
    public int getInvalidos() {
        return this.invalidos;
    }

    /**
     * Método que inicia um catalogo de users a partir de uma linha.
     * @param linhas lista das linhas lidas do ficheiro
     */
    public void initCatalogoUFromLines(List<String> linhas) {
        for(String s: linhas){
            User u = User.initUserFromLine(s);
            if (u != null) this.users.put(u.getID(), u.clone());
            else this.invalidos++;
        }
    }

}
