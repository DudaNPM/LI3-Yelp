package model;

import java.io.*;
import java.util.*;

public class GestReviews implements Serializable {

    // variaveis de instancia
    private List<String> files;
    private CatalogoU CU;
    private CatalogoB CB;
    private CatalogoR CR;
    private int reviewsErradas;

    /**
     * Construtor por omissão do tipo GestReviews.
     */
    public GestReviews() {
        this.files = new ArrayList<>();
        this.CU = new CatalogoU();
        this.CB = new CatalogoB();
        this.CR = new CatalogoR();
    }

    /**
     * Construtor de cópia do tipo GestReviews.
     */
    public GestReviews(GestReviews gr) {
        this.files = gr.getFiles();
        this.CU = gr.getCatalogoU();
        this.CB = gr.getCatalogoB();
        this.CR = gr.getCatalogoR();
        this.reviewsErradas = gr.getReviewsErradas();
    }

    /**
     * Método que devolve o nome dos ficheiros.
     * @return Ficheiros
     */
    public List<String> getFiles() {
        return new ArrayList<>(this.files);
    }

    /**
     * Método que devolve o catalogo de users.
     * @return CU
     */
    public CatalogoU getCatalogoU() {
        return new CatalogoU(this.CU);
    }

    /**
     * Método que devolve o catalogo de business.
     * @return CB
     */
    public CatalogoB getCatalogoB() {
        return new CatalogoB(this.CB);
    }

    /**
     * Método que devolve o catalogo de reviews.
     * @return CR
     */
    public CatalogoR getCatalogoR() {
        return new CatalogoR(this.CR);
    }

    /**
     * Método que devolve o número de reviews erradas.
     * @return Número de reviews
     */
    public int getReviewsErradas() {
        return this.reviewsErradas;
    }

    /**
     * Método que devolve o número de reviews inválidas.
     * Mal formatadas assim como as que não têm um userId ou businessId registado.
     * @return Número de reviews inválidas
     */
    public int filtraReviews(){
        int contador = this.CR.getInvalidos();
        Map<String, User> users = this.CU.getUsers();
        Map<String, Business> businesses = this.CB.getBusinesses();
        Map<String, Review> reviews = this.CR.getReviews();
        for(Review r : reviews.values()){
            if(!businesses.containsKey(r.getBusinessIDFromReview()) && !users.containsKey(r.getUserIDFromReview())) {
                reviews.remove(r.getID());
                this.CR.setReviews(reviews);
                contador++;
            }
        }
        return contador;
    }

    /**
     * Método que lê os três ficheiros passados como argumento e inicia os respetivos catálogos.
     * @param users Users file name
     * @param businesses Business file name
     * @param reviews Reviews file name
     */
    public void load(String users, String businesses, String reviews) throws IOException {
        Leitura linhasU = new Leitura();
        Leitura linhasB = new Leitura();
        Leitura linhasR = new Leitura();

        linhasU.loadFile(users);
        this.CU.initCatalogoUFromLines(linhasU.getLinhas());
        linhasU.getLinhas().clear();

        linhasB.loadFile(businesses);
        this.CB.initCatalogoBFromLines(linhasB.getLinhas());
        linhasB.getLinhas().clear();

        linhasR.loadFile(reviews);
        this.CR.initCatalogoRFromLines(linhasR.getLinhas());
        linhasR.getLinhas().clear();

        this.files.add(users);
        this.files.add(businesses);
        this.files.add(reviews);
        this.reviewsErradas = filtraReviews();
    }

    /**
     * Método que guarda em ficheiro de objetos o objeto que recebe a mensagem.
     * @param filename Ficheiro
     */
    public void saveEstadoBin(String filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("db/" + filename));
        oos.writeObject(this);
        oos.close();
    }

    /**
     * Método que recupera uma instância de GestReviews de um ficheiro de objetos.
     * @param filename Ficheiro
     * @return gr GestReviews inicializado
     */
    public static GestReviews loadEstadoBin(String filename) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("db/" + filename));
        GestReviews gr = (GestReviews) ois.readObject();
        ois.close();
        return gr;
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados das estatísticas 1.
     * @return String com info textual
     */
    public String calcStats1(){
        Estatisticas stats = new Estatisticas(this);
        stats.stats1();
        return stats.toStringStats1();
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados das estatísticas 2.
     * @return String com info textual
     */
    public String calcStats2(){
        Estatisticas stats = new Estatisticas(this);
        stats.stats2();
        return stats.toStringStats2();
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 1.
     * @return String com info textual
     */
    public String calcQ1(){
        Queries q1 = new Queries(this);
        List<String> results = q1.query1();
        return q1.toStringQ1(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 2.
     * @param mes Mes
     * @param ano Ano
     * @return String com info textual
     */
    public String calcQ2(int mes, int ano){
        Queries q2 = new Queries(this);
        List<Integer> results = q2.query2(mes, ano);
        return q2.toStringQ2(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 3.
     * @param userId User id
     * @return String com info textual
     */
    public String calcQ3(String userId){
        Queries q3 = new Queries(this);
        Map<Integer, List<Float>> results = q3.query3(userId);
        return q3.toStringQ3(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 4.
     * @param businessId Business id
     * @return String com info textual
     */
    public String calcQ4(String businessId){
        Queries q4 = new Queries(this);
        Map<Integer, List<Float>> results = q4.query4(businessId);
        return q4.toStringQ4(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 5.
     * @param userId User id
     * @return String com info textual
     */
    public String calcQ5(String userId){
        Queries q5 = new Queries(this);
        TreeSet<Map.Entry<String, Integer>> results = q5.query5(userId);
        return q5.toStringQ5(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 6.
     * @param x Top x negócios
     * @return String com info textual
     */
    public String calcQ6(Integer x){
        Queries q6 = new Queries(this);
        TreeSet<Map.Entry<Integer, TreeSet<Map.Entry<String, Integer>>>> results = q6.query6(x);
        return q6.toStringQ6(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 7.
     * @return String com info textual
     */
    public String calcQ7(){
        Queries q7 = new Queries(this);
        Map<String, TreeSet<AbstractMap.Entry<String, Integer>>> filtro = q7.query7();
        return q7.toStringQ7(filtro);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 8.
     * @param x Top x users
     * @return String com info textual
     */
    public String calcQ8(Integer x){
        Queries q8 = new Queries(this);
        TreeSet<Map.Entry<String, Integer>> results = q8.query8(x);
        return q8.toStringQ8(results);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 9.
     * @return String com info textual
     */
    public String calcQ9(String s, int x){
        Queries q9 = new Queries(this);
        Map<String, List<Map.Entry<String, Integer>>> filtro = q9.query9(s, x);
        return q9.toStringQ9(filtro);
    }

    /**
     * Método que devolve uma string com info textual
     * relativo aos dados da query 10.
     * @return String com info textual
     */
    public String calcQ10(){
        Queries q10 = new Queries(this);
        Map<String, Map<String, Map<String, List<Float>>>> results = q10.query10();
        return q10.toStringQ10(results);
    }
}