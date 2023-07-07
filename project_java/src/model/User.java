package model;

import java.io.Serializable;

public class User implements Serializable {

    // variáveis de instância
    private String id;
    private String name;
    // private List<String> friends;

    /**
     * Construtor por omissão do tipo User.
     */
    public User() {
        this.id = null;
        this.name = null;
    }

    /**
     * Construtor parametrizadodo tipo User.
     * @param id Id
     * @param nome Nome
     */
    public User(String id, String nome) {
        this.id = id;
        this.name = nome;
    }

    /**
     * Construtor de cópia do tipo User.
     * @param u User a copiar
     */
    public User(User u) {
        this.id = u.getID();
        this.name = u.getName();
    }

    /**
     * Método que devolve o id de um User.
     * @return user_id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Método que atualiza o id de um user.
     * @param id Novo id
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Método que devolve o nome de um User.
     * @return Name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Método que atualiza o nome de um user.
     * @param name Novo nome
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Método que devolve a informação de um User em formato String.
     * @return String com info textual
     */
    public String toString() {
        StringBuilder sb = new StringBuilder("|User|");
        sb.append(" Id: ").append(this.id);
        sb.append(" Nome: ").append(this.name);
        return sb.toString();
    }

    /**
     * Método de clonagem de um User.
     * @return Objeto do tipo User
     */
    public User clone(){
        return new User(this);
    }

    /**
     * Método de igualdade entre dois Users.
     * Redefinição do método equals de Object.
     * @param obj Objeto a ser comparado
     * @return true, caso sejam iguais, false caso contrário
     */
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        User j = (User) obj;
        return this.id.equals(j.getID()) &&
                this.name.equals(j.getName());
    }

    /**
     * Método que verifica a validade de um User.
     * @return true caso seja valido, false caso contrário
     */
    private static boolean isValidUser(String[] campos) {
        return   campos.length == 3  &&
                !campos[0].isEmpty() &&
                !campos[1].isEmpty();
    }

    /**
     * Método que cria um User a partir de uma linha.
     * @return User
     */
    public static User initUserFromLine(String line) {
        String[] campos = line.split(";");
        if (isValidUser(campos)) return new User(campos[0], campos[1]);
        else return null;
    }
}
