package model;

import java.io.Serializable;

public class Review implements Serializable {

    // variáveis de instância
    private String id, user_id, business_id;
    private float stars;
    private int useful, funny, cool;
    private String date;
    private String text;

    /**
     * Construtor por omissão do tipo Review.
     */
    public Review() {
        this.id = this.business_id = this.user_id = null;
        this.stars = this.useful = this.funny = this.cool = 0;
        this.date = null;
        this.text = null;
    }

    /**
     * Construtor parametrizado do tipo Review.
     * @param id Review id
     * @param u_id User id
     * @param b_id Business id
     * @param s Stars
     * @param u Useful
     * @param f Funny
     * @param c Cool
     * @param d Date
     * @param t Text
     */
    public Review(String id, String u_id, String b_id,
                  float s, int u, int f, int c, String d, String t) {
        this.id = id;
        this.user_id = u_id;
        this.business_id = b_id;
        this.stars = s;
        this.useful = u;
        this.funny = f;
        this.cool = c;
        this.date = d;
        this.text = t;
    }

    /**
     * Construtor de cópia do tipo Review.
     * @param r Review a copiar
     */
    public Review(model.Review r) {
        this.id = r.getID();
        this.business_id = r.getBusinessIDFromReview();
        this.user_id = r.getUserIDFromReview();
        this.stars = r.getStars();
        this.useful = r.getUseful();
        this.funny = r.getFunny();
        this.cool = r.getCool();
        this.date = r.getDate();
        this.text = r.getText();
    }

    /**
     * Método que devolve o id de uma Review.
     * @return Id
     */
    public String getID() {
        return this.id;
    }

    /**
     * Método que atualiza o id de uma Review.
     * @param id Novo id
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Método que devolve o id de um Business numa Review.
     * @return Id
     */
    public String getBusinessIDFromReview() {
        return this.business_id;
    }

    /**
     * Método que atualiza o id de um Business numa Review.
     * @param id Novo id
     */
    public void setBusinessIDFromReview(String id) {
        this.business_id = id;
    }

    /**
     * Método que devolve o id de um User numa Review.
     * @return Id
     */
    public String getUserIDFromReview() {
        return this.user_id;
    }

    /**
     * Método que atualiza o id de um User numa Review.
     * @param id Novo id
     */
    public void setUserIDFromReview(String id) {
        this.user_id = id;
    }

    /**
     * Método que devolve as stars.
     * @return stars
     */
    public float getStars() {
        return this.stars;
    }

    /**
     * Método que atualiza as stars.
     * @param s Novas stars
     */
    public void setStars(float s) {
        this.stars = s;
    }

    /**
     * Método que devolve o useful.
     * @return usefull
     */
    public int getUseful() {
        return this.useful;
    }

    /**
     * Método que atualiza o useful.
     * @param u Novo useful
     */
    public void setUseful(int u) {
        this.useful = u;
    }

    /**
     * Método que devolve o funny.
     * @return funny
     */
    public int getFunny() {
        return this.funny;
    }

    /**
     * Método que atualiza o funny.
     * @param f Novo funny
     */
    public void setFunny(int f) {
        this.funny = f;
    }

    /**
     * Método que devolve o cool.
     * @return cool
     */
    public int getCool() {
        return this.cool;
    }

    /**
     * Método que atualiza o cool.
     * @param c Novo cool
     */
    public void setCool(int c) {
        this.cool = c;
    }

    /**
     * Método que devolve a data.
     * @return date
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Método que atualiza a data.
     * @param d Nova data
     */
    public void setDate(String d) {
        this.date = d;
    }

    /**
     * Método que devolve o text.
     * @return text
     */
    public String getText() {
        return this.text;
    }

    /**
     * Método que atualiza o text.
     * @param t Novo text
     */
    public void setText(String t) {
        this.text = t;
    }

    /**
     * Método que devolve o ano de uma review.
     * @return ano
     */
    public int getAno(){
        return Integer.parseInt(this.date.substring(0,4));
    }

    /**
     * Método que devolve o mes de uma review.
     * @return mes
     */
    public int getMes(){
        return Integer.parseInt(this.date.substring(5,7));
    }

    /**
     * Método que devolve a informação de uma Review em formato String.
     * @return String com info textual
     */
    public String toString(){
        StringBuilder sb = new StringBuilder("|Review|");
        sb.append(" Id: ").append(this.id);
        sb.append(" Id Business: ").append(this.business_id);
        sb.append(" Is User: ").append(this.user_id);
        sb.append(" stars: ").append(this.stars);
        sb.append(" funny: ").append(this.funny);
        sb.append(" useful: ").append(this.useful);
        sb.append(" cool: ").append(this.cool);
        sb.append(" data: ").append(this.date);
        sb.append(" text: ").append(this.text);
        return sb.toString();
    }

    /**
     * Método de clonagem de uma Review.
     * @return Objeto do tipo Review
     */
    public model.Review clone() {
        return new model.Review(this);
    }

    /**
     * Método de igualdade entre duas Reviews.
     * Redefinição do método equals de Object.
     * @param obj Objeto a ser comparado
     * @return true, caso sejam iguais, false caso contrário
     */
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        model.Review j = (model.Review) obj;
        return  this.id.equals(j.getID()) &&
                this.business_id.equals(j.getBusinessIDFromReview()) &&
                this.user_id.equals(j.getUserIDFromReview()) &&
                this.stars == ( j.getStars()) &&
                this.useful == (j.getUseful()) &&
                this.funny == (j.getFunny()) &&
                this.cool == (j.getCool()) &&
                this.date == (j.getDate()) &&
                this.text.equals(j.getText());
    }

    /**
     * Método que verifica a validade de um Business.
     * @return true caso seja valido, false caso contrário
     */
    private static boolean isValidReview(String[] campos) {
        return   campos.length == 9  &&
                !campos[0].isEmpty() &&
                !campos[1].isEmpty() &&
                !campos[2].isEmpty() &&
                !campos[3].isEmpty() &&
                !campos[4].isEmpty() &&
                !campos[5].isEmpty() &&
                !campos[6].isEmpty() &&
                !campos[7].isEmpty();
    }

    /**
     * Método que cria uma Review a partir de uma linha.
     * @return Review
     */
    public static model.Review initReviewFromLine(String line) {
        String[] campos = line.split(";");
        if (isValidReview(campos))
            return new model.Review(campos[0], campos[1], campos[2],
                    Float.parseFloat(campos[3]),
                    Integer.parseInt(campos[4]),
                    Integer.parseInt(campos[5]),
                    Integer.parseInt(campos[6]),
                    campos[7],
                    campos[8]);
        else return null;
    }
}
