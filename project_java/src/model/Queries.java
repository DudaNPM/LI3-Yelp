package model;

import java.util.*;

public class Queries extends GestReviews {

    /**
     * Construtor de cópia do tipo Queries.
     * @param stats stats
     */
    public Queries(GestReviews stats) {
        super(stats);
    }

    /**
     * Método que calcula a lista ordenada alfabeticamente com os identificadores
     * dos negócios nunca avaliados e o seu respetivo total.
     * @return Lista de resultados
     */
    public List<String> query1() {
        Map<String, Integer> negociosAvaliados = new HashMap<>();
        List<String> negociosNaoAvaliados = new ArrayList<>();

        for (Review r : super.getCatalogoR().getReviews().values())
            negociosAvaliados.putIfAbsent(r.getBusinessIDFromReview(), 1);

        for (Business b : super.getCatalogoB().getBusinesses().values())
            if (!negociosAvaliados.containsKey(b.getID()))
                negociosNaoAvaliados.add(b.getID());

        negociosNaoAvaliados.sort(Comparator.naturalOrder());
        return negociosNaoAvaliados;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 1.
     * @param results Lista de resultados
     * @return String com info textual
     */
    public String toStringQ1(List<String> results){
        StringBuilder sb = new StringBuilder("\n| Query nº1 |\n");
        sb.append(" Ids de negocios não avaliados (por ordem alfabetica): \n").append(results).append('\n');
        sb.append(" Nº total de negócios não avaliados: ").append(results.size()).append('\n');
        return sb.toString();
    }

    /**
     * Método que dado um mês e um ano (válidos), determina o número total
     * global de reviews realizadas e o número total de users distintos que
     * as realizaram.
     * @param mes Mes
     * @param ano Ano
     * @return Lista de resultados
     */
    public List<Integer> query2(int mes, int ano){
        int reviews = 0;
        Map<String,Integer> users = new HashMap<>();

        for (Review r : super.getCatalogoR().getReviews().values()){
            if (r.getMes() == mes && r.getAno() == ano){
                reviews++;
                if (!users.containsKey(r.getUserIDFromReview()))
                    users.put(r.getUserIDFromReview(), 1);
            }
        }

        List<Integer> results = new ArrayList<>();
        results.add(reviews);
        results.add(users.size());
        return results;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 2.
     * @param results Lista de resultados
     * @return String com info textual
     */
    public String toStringQ2(List<Integer> results){
        StringBuilder sb = new StringBuilder("\n| Query nº2 |\n");
        sb.append(" Nº total de reviews: ").append(results.get(0)).append('\n');
        sb.append(" Nº total de users distintos: ").append(results.get(1)).append('\n');
        return sb.toString();
    }

    /**
     * Método que dado um código de utilizador, determina, para cada mês,
     * quantas reviews fez,quantos negócios distintos avaliou e que nota
     * média atribuiu
     * @param userId User id
     * @return Map com os resultados
     */
    public Map<Integer, List<Float>> query3(String userId){
        Map<Integer, List<Float>> results = new HashMap<>();
        Map<String, Integer> businesses = new HashMap<>();

        for (Review r : super.getCatalogoR().getReviews().values()){
            List<Float> aux = results.get(r.getMes()); // lista com results correspondentes ao mes da review
            if (aux == null) {
                aux = new ArrayList<>();
                aux.add((float) 0); aux.add((float) 0); aux.add((float) 0);
                results.put(r.getMes(), aux);
            }

            if (r.getUserIDFromReview().equals(userId)){
                // add review - posicao 0 da lista
                aux.set(0, aux.get(0) + 1);

                // add negocio distinto - posicao 1 da lista
                if (!businesses.containsKey(r.getBusinessIDFromReview())){
                    businesses.put(r.getBusinessIDFromReview(), 1);
                    aux.set(1, aux.get(1) + 1);
                }

                // sum classificacoes - posicao 2 da lista
                aux.set(2, aux.get(2) + r.getStars());

                // add a lista com altercoes feitas à hashtable
                results.put(r.getMes(), aux);
            }
        }

        // na posicao 2 de cada lista da table esta o sumatorio das classificacoes,ou seja,
        // é preciso dividir pelo numero de reviews para calcular a media.
        for (List<Float> lista : results.values())
            if (lista.get(2) != 0)
                lista.set(2, lista.get(2) / lista.get(0));

        return results;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 3.
     * @param results Map de resultados
     * @return String com info textual
     */
    public String toStringQ3(Map<Integer, List<Float>> results){
        StringBuilder sb = new StringBuilder("\n| Query nº3 |\n");
        sb.append(" Mês: [Nºreviews, Nºnegócios distintos avaliados, Nota média]\n");
        sb.append(" Janeiro: ").append(results.get(1)).append('\n');
        sb.append(" Fevereiro: ").append(results.get(2)).append('\n');
        sb.append(" Março: ").append(results.get(3)).append('\n');
        sb.append(" Abril: ").append(results.get(4)).append('\n');
        sb.append(" Maio: ").append(results.get(5)).append('\n');
        sb.append(" Junho: ").append(results.get(6)).append('\n');
        sb.append(" Julho: ").append(results.get(7)).append('\n');
        sb.append(" Agosto: ").append(results.get(8)).append('\n');
        sb.append(" Setembro: ").append(results.get(9)).append('\n');
        sb.append(" Outubro: ").append(results.get(10)).append('\n');
        sb.append(" Novembro: ").append(results.get(11)).append('\n');
        sb.append(" Dezembro: ").append(results.get(12)).append('\n');
        return sb.toString();
    }

    /**
     * Método que dado o código de um negócio, determina, mês a mês, quantas
     * vezes foi avaliado, por quantos users diferentes e a média de classificação.
     * @param businessId Business id
     * @return Map de resultados
     */
    public Map<Integer, List<Float>> query4(String businessId){
        Map<Integer, List<Float>> results = new HashMap<>();
        Map<String, Integer> users = new HashMap<>();

        for (Review r : super.getCatalogoR().getReviews().values()){
            List<Float> aux = results.get(r.getMes()); // lista com results correspondentes ao mes da review
            if (aux == null) {
                aux = new ArrayList<>();
                aux.add((float) 0); aux.add((float) 0); aux.add((float) 0);
                results.put(r.getMes(), aux);
            }

            if (r.getBusinessIDFromReview().equals(businessId)){
                // add review - posicao 0 da lista
                aux.set(0, aux.get(0) + 1);

                // add user distinto - posicao 1 da lista
                if (!users.containsKey(r.getUserIDFromReview())){
                    users.put(r.getUserIDFromReview(), 1);
                    aux.set(1, aux.get(1) + 1);
                }

                // sum classificacoes - posicao 2 da lista
                aux.set(2, aux.get(2) + r.getStars());

                // add a lista com altercoes feitas à hashtable
                results.put(r.getMes(), aux);
            }
        }

        // na posicao 2 de cada lista da table esta o sumatorio das classificacoes,ou seja,
        // é preciso dividir pelo numero de reviews para calcular a media.
        for (List<Float> lista : results.values())
            if (lista.get(2) != 0)
                lista.set(2, lista.get(2) / lista.get(0));

        return results;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 4.
     * @param results Map de resultados
     * @return String com info textual
     */
    public String toStringQ4(Map<Integer, List<Float>> results){
        StringBuilder sb = new StringBuilder("\n| Query nº4 |\n");
        sb.append(" Mês: [Nºreviews, Nºusers avaliadores, Nota média]\n");
        sb.append(" Janeiro: ").append(results.get(1)).append('\n');
        sb.append(" Fevereiro: ").append(results.get(2)).append('\n');
        sb.append(" Março: ").append(results.get(3)).append('\n');
        sb.append(" Abril: ").append(results.get(4)).append('\n');
        sb.append(" Maio: ").append(results.get(5)).append('\n');
        sb.append(" Junho: ").append(results.get(6)).append('\n');
        sb.append(" Julho: ").append(results.get(7)).append('\n');
        sb.append(" Agosto: ").append(results.get(8)).append('\n');
        sb.append(" Setembro: ").append(results.get(9)).append('\n');
        sb.append(" Outubro: ").append(results.get(10)).append('\n');
        sb.append(" Novembro: ").append(results.get(11)).append('\n');
        sb.append(" Dezembro: ").append(results.get(12)).append('\n');
        return sb.toString();
    }

    /**
     * Método que dado o código de um utilizador determina a lista de nomes
     * de negócios que mais avaliou (e quantos), ordenada por ordem decrescente
     * de quantidade e, para quantidades iguais, por ordem alfabética dos negócios
     * @param userId User id
     * @return Set com os pares de resultados
     */
    public TreeSet<Map.Entry<String, Integer>> query5(String userId){
        Map<String, Integer> results = new HashMap<>(); // id, quantidade

        Map<String, Business> businesses = super.getCatalogoB().getBusinesses();
        Map<String, Review> reviews = super.getCatalogoR().getReviews();

        for (Review r : reviews.values()){
            if (r.getUserIDFromReview().equals(userId)){
                String businessId = r.getBusinessIDFromReview();
                String businessName = businesses.get(businessId).getName();
                if (results.containsKey(businessName))
                    results.put(businessName, results.get(businessName) + 1);
                else results.put(businessName, 1);
            }
        }

        Comparator<AbstractMap.Entry<String, Integer>> comp = (p1, p2) -> {
            if (p1.getValue() > p2.getValue()) return -1;
            if (p1.getValue() < p2.getValue()) return 1;
            return p1.getKey().compareToIgnoreCase(p2.getKey());
        };
        TreeSet<AbstractMap.Entry<String, Integer>> pares = new TreeSet<>(comp);
        pares.addAll(results.entrySet());
        return pares;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 5.
     * @param results Set com os pares de resultados
     * @return String com info textual
     */
    public String toStringQ5(TreeSet<Map.Entry<String, Integer>> results){
        StringBuilder sb = new StringBuilder("\n| Query nº5 |\n");
        for (Map.Entry<String, Integer> par : results)
            sb.append(par.toString()).append('\n');
        return sb.toString();
    }

    /**
     * Método que determina o conjunto dos X negócios mais avaliados
     * (com mais reviews) em cada ano,indicando o número total de
     * distintos utilizadores que o avaliaram.
     * @param x Número de negócios
     * @return Set com os resultados
     */
    public TreeSet<Map.Entry<Integer, TreeSet<Map.Entry<String, Integer>>>> query6(int x){
        Map<Integer, Map<String, Integer>> resultsReviews = new HashMap<>();
        //  Map< ano   , Map<businessId, nºreviews>

        Map<Integer, Map<String, Map<String, Integer>>> resultsIds = new HashMap<>();
        //  Map< ano   , Map<businessId, Map<userId, 1>>

        Map<String, Review> mapReviews = super.getCatalogoR().getReviews();

        for (Review r : mapReviews.values()){
            int ano = r.getAno();

            String businessId = r.getBusinessIDFromReview();
            if (!resultsReviews.containsKey(ano)) {
                resultsReviews.put(ano, new HashMap<>());
                resultsReviews.get(ano).put(businessId, 1);
            } else{
                if (!resultsReviews.get(ano).containsKey(businessId)){
                    resultsReviews.get(ano).put(businessId,1);
                } else{
                    int reviews = resultsReviews.get(ano).get(businessId);
                    resultsReviews.get(ano).replace(businessId, reviews + 1);
                }
            }

            String userId = r.getUserIDFromReview();
            if (!resultsIds.containsKey(ano)) {
                resultsIds.put(ano, new HashMap<>());
                resultsIds.get(ano).put(businessId, new HashMap<>());
                resultsIds.get(ano).get(businessId).put(userId, 1);
            } else{
                if (!resultsIds.get(ano).containsKey(businessId)){
                    resultsIds.get(ano).put(businessId,new HashMap<>());
                    resultsIds.get(ano).get(businessId).put(userId, 1);
                } else{
                    resultsIds.get(ano).get(businessId).putIfAbsent(userId, 1);
                }
            }
        }

        Comparator<AbstractMap.Entry<String, Integer>> comp = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
        Map<Integer, TreeSet<AbstractMap.Entry<String, Integer>>> filtro = new HashMap<>(); // ano, lista de (idBusiness, nºreviews)
        for (Map.Entry<Integer, Map<String, Integer>> pair : resultsReviews.entrySet()){
            TreeSet<AbstractMap.Entry<String, Integer>> aux = new TreeSet<>(comp);
            aux.addAll(pair.getValue().entrySet());
            filtro.put(pair.getKey(), aux);
        }
        for (TreeSet<AbstractMap.Entry<String, Integer>> ano : filtro.values()) {
            while (ano.size() > x)
                ano.pollLast();
        }

        for (Map.Entry<Integer, TreeSet<AbstractMap.Entry<String, Integer>>> aux : filtro.entrySet()){
            int ano = aux.getKey();
            String businessId;
            for (Map.Entry<String, Integer> aux1 : aux.getValue()) {
                businessId = aux1.getKey();
                aux1.setValue(resultsIds.get(ano).get(businessId).size());
            }
        }

        Comparator<AbstractMap.Entry<Integer, TreeSet<AbstractMap.Entry<String, Integer>>>> comp2 = Map.Entry.comparingByKey();
        TreeSet<AbstractMap.Entry<Integer, TreeSet<AbstractMap.Entry<String, Integer>>>> filtroFinal = new TreeSet<>(comp2);
        filtroFinal.addAll(filtro.entrySet());
        return filtroFinal;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 6.
     * @param results Set com resultados
     * @return String com info textual
     */
    public String toStringQ6(TreeSet<Map.Entry<Integer, TreeSet<Map.Entry<String, Integer>>>> results){
        StringBuilder sb = new StringBuilder("\n| Query nº6 |\n");
        for (Map.Entry<Integer, TreeSet<Map.Entry<String, Integer>>> par : results)
            sb.append(par.toString()).append('\n');
        return sb.toString();
    }


    /**
     * Método que determina os códigos dos 3 negocios mais mais famosos
     * (em termos de numero de reviews), para cada cidade
     * por ordem decrescente de fama.
     * @return Set com os pares de resultados
     */
    public Map<String, TreeSet<AbstractMap.Entry<String, Integer>>> query7() {
        Map<String, Map<String, Integer>> resultsReviews = new HashMap<>();
        //  Map<city   , Map<businessId, nºreviews>

        Map<String, Business> businesses = super.getCatalogoB().getBusinesses();
        Map<String, Review> mapReviews = super.getCatalogoR().getReviews();

        for (Review r : mapReviews.values()) {
            String businessId = r.getBusinessIDFromReview();
            String city = businesses.get(businessId).getCity();
            if (!resultsReviews.containsKey(city)) {
                resultsReviews.put(city, new HashMap<>());
                resultsReviews.get(city).put(businessId, 1);
            } else {
                if (!resultsReviews.get(city).containsKey(businessId)) {
                    resultsReviews.get(city).put(businessId, 1);
                } else {
                    int reviews = resultsReviews.get(city).get(businessId);
                    resultsReviews.get(city).replace(businessId, reviews + 1);
                }
            }
        }
        Comparator<AbstractMap.Entry<String, Integer>> comp = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
        Map<String, TreeSet<AbstractMap.Entry<String, Integer>>> filtro = new HashMap<>(); // city, lista de (idBusiness, nºreviews)
        for (Map.Entry<String, Map<String, Integer>> pair : resultsReviews.entrySet()){
            TreeSet<AbstractMap.Entry<String, Integer>> aux = new TreeSet<>(comp);
            aux.addAll(pair.getValue().entrySet());
            filtro.put(pair.getKey(), aux);
        }
        for (TreeSet<AbstractMap.Entry<String, Integer>> city : filtro.values()) {
            while (city.size() > 3)
                city.pollLast();
        }
        return filtro;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 7.
     * @param filtro, string com resultados
     * @return String com info textual
     */
    public String toStringQ7(Map<String, TreeSet<AbstractMap.Entry<String, Integer>>> filtro) {
        StringBuilder sb = new StringBuilder("\n| Query nº7 |\n");
        for (Map.Entry<String, TreeSet<AbstractMap.Entry<String, Integer>>> pair : filtro.entrySet()){
            sb.append("\n").append(pair.getKey()).append("\n");
            for (Map.Entry<String, Integer> par : pair.getValue())
                sb.append(par.toString(), 0, 22).append('\n');
        }
        return sb.toString();

    }

    /**
     * Método que determina os códigos dos X utilizadores que avaliaram mais
     * negócios diferentes, indicando quantos,sendo o critério de ordenação
     * a ordem decrescente do número de negócios.
     * @param x Top x de utilizadores
     * @return Set com os pares de resultados
     */
    public TreeSet<Map.Entry<String, Integer>> query8(int x){
        Map<String, Map<String, Integer>> results = new HashMap<>(); // userId, businessId, 1

        Map<String, Review> reviews = super.getCatalogoR().getReviews();

        for (Review r : reviews.values()){
            String userId = r.getUserIDFromReview();
            String businessId = r.getBusinessIDFromReview();

            if (!results.containsKey(userId)){ // nao existe table para este user id
                Map<String, Integer> aux = new HashMap<>();
                aux.put(businessId, 1);
                results.put(userId, aux);
            } else {
                if (!results.get(userId).containsKey(businessId)) // nao existe ocorrencia do business id
                    results.get(userId).put(businessId, 1);
            }
        }

        Comparator<Map.Entry<String, Integer>> comp = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
        TreeSet<Map.Entry<String, Integer>> pairs = new TreeSet<>(comp);
        for (Map.Entry<String, Map<String, Integer>> par : results.entrySet()){
            Map.Entry<String, Integer> aux = new AbstractMap.SimpleEntry<>(par.getKey(), par.getValue().size());
            pairs.add(aux);
        }

        while (pairs.size() > x) pairs.pollLast();
        return pairs;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 8.
     * @param results Set com resultados
     * @return String com info textual
     */
    public String toStringQ8(TreeSet<Map.Entry<String, Integer>> results){
        StringBuilder sb = new StringBuilder("\n| Query nº8 |\n");
        for (Map.Entry<String, Integer> par : results)
            sb.append(par.toString()).append('\n');
        return sb.toString();
    }

    /**
     * Método que determina os códigos dos X utilizadores
     * que mais avaliaram um dado negocio,
     * indicando a media de classificaçao de cada utilizador nesse negocio
     * por ordem decrecente de reviews nesse negocio
     * @param x nº de utilizadores, id negocio
     * @return Set com os pares de resultados
     */
    public Map<String, List<Map.Entry<String, Integer>>> query9(String id, int x) {
        Map<String, Map<String, Integer>> resultsReviews = new HashMap<>();
        // Map<business, Map    <userId, nºreviews>

        Map<String, Review> mapReviews = super.getCatalogoR().getReviews();

        for (Review r : mapReviews.values()) {
            String userId = r.getUserIDFromReview();
            if (!resultsReviews.containsKey(id) && r.getBusinessIDFromReview().equals(id)) {
                resultsReviews.put(id, new HashMap<>());
                resultsReviews.get(id).put(userId, 1);
            }
            else if ( r.getBusinessIDFromReview().equals(id)) {
                if (!resultsReviews.get(id).containsKey(userId)) {
                    resultsReviews.get(id).put(userId, 1);
                } else {
                    int reviews = resultsReviews.get(id).get(userId);
                    resultsReviews.get(id).replace(userId, reviews + 1);
                }
            }
        }


        Comparator<AbstractMap.Entry<String, Integer>> comp = (p1, p2) -> p2.getValue().compareTo(p1.getValue());
        Map<String, List<AbstractMap.Entry<String, Integer>>> filtro = new HashMap<>(); // ano, lista de (idBusiness, nºreviews)
        for (Map.Entry<String, Map<String, Integer>> pair : resultsReviews.entrySet()) {
            List<AbstractMap.Entry<String, Integer>> aux = new ArrayList<>(pair.getValue().entrySet());
            aux.sort(comp);
            filtro.put(pair.getKey(), aux);
        }
        for (List<AbstractMap.Entry<String, Integer>> user : filtro.values()) {
            while (user.size() > x)
                user.remove(user.size()-1);
        }
        return filtro;
    }


    /**
     * Método que determina a media de classificaçao
     * atribuida a um negocio, por um dado utilizador
     * @param idU id do utilizador, idB id do negocio
     * @return Set com os pares de resultados
     */
    public float getClassif(String idU, String idB){
        float soma = 0;
        int n = 0;
        Map<String, Review> reviews = super.getCatalogoR().getReviews();
        for (Review r : reviews.values()) {
            if(r.getUserIDFromReview().equals(idU) && r.getBusinessIDFromReview().equals(idB)){
                soma = soma + r.getStars();
                n++;
            }
        }
        return (soma/n);
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 9.
     * @param filtro, string com resultados
     * @return String com info textual
     */
    public String toStringQ9(Map<String, List<AbstractMap.Entry<String, Integer>>> filtro) {
        StringBuilder sb = new StringBuilder("\n| Query nº9 |\n");
        for (Map.Entry<String, List<Map.Entry<String, Integer>>> pair : filtro.entrySet()){
            for (Map.Entry<String, Integer> par : pair.getValue())
                sb.append(par.toString(), 0, 22).append(" -> media de classificação: ")
                        .append(getClassif((par.toString().substring(0,22)), (pair.getKey()))).append("\n");
        }
        return sb.toString();

    }

    /**
     * Método que determina para cada estado,cidade a cidade,
     * a média de classificação de cada negócio.
     * @return Map com os resultados
     */
    public Map<String, Map<String, Map<String, List<Float>>>> query10(){
        Map<String, Map<String, Map<String, List<Float>>>> results = new HashMap<>();
        // Map (estado, Map (cidade, Map (id_negocio, Lista de class)))

        Map<String, Business> businesses = super.getCatalogoB().getBusinesses();
        Map<String, Review> reviews = super.getCatalogoR().getReviews();

        for (Review r : reviews.values()) {
            String businessId = r.getBusinessIDFromReview();
            Business business = businesses.get(businessId);
            String estado = business.getState();
            String cidade = business.getCity();
            if (!results.containsKey(estado)) { // nao existe table associada ao state
                results.put(estado, new HashMap<>());
                results.get(estado).put(cidade, new HashMap<>());
                List<Float> aux = new ArrayList<>();
                aux.add(r.getStars());
                results.get(estado).get(cidade).put(businessId, aux);
            } else { // existe table associada ao state
                if (!results.get(estado).containsKey(cidade)) { // nao existe table associada a cidade
                    results.get(estado).put(cidade, new HashMap<>());
                    List<Float> aux = new ArrayList<>();
                    aux.add(r.getStars());
                    results.get(estado).get(cidade).put(businessId, aux);
                } else { // existe table associada a cidade
                    if (!results.get(estado).get(cidade).containsKey(businessId)) { // nao existe table associada ao negocio
                        List<Float> aux = new ArrayList<>();
                        aux.add(r.getStars());
                        results.get(estado).get(cidade).put(businessId, aux);
                    } else { // existe table associada ao negocio
                        List<Float> aux = results.get(estado).get(cidade).get(businessId);
                        aux.add(r.getStars());
                        results.get(estado).get(cidade).replace(businessId, aux);
                    }
                }
            }
        }
        return results;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados da query 10.
     * @param results, map com os resultados
     * @return String com info textual
     */
    public String toStringQ10(Map<String, Map<String, Map<String, List<Float>>>> results){
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Map<String, Map<String, List<Float>>>> states : results.entrySet()){
            sb.append("\n| State: ").append(states.getKey()).append(" |\n");
            for (Map.Entry<String, Map<String, List<Float>>> citys : states.getValue().entrySet()){
                sb.append("City: ").append(citys.getKey()).append("\n");
                for (Map.Entry<String, List<Float>> businesses : citys.getValue().entrySet()){
                    Float sum = (float) 0;
                    for (Float classificacao : businesses.getValue())
                        sum += classificacao;
                    Float media = sum / businesses.getValue().size();
                    sb.append(" businessId: ").append(businesses.getKey()).append(" | media: ").append(media).append("\n");
                }
            }
        }
        return sb.toString();
    }
}