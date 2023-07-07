package model;

import java.util.*;

public class Estatisticas extends GestReviews {

    // variáveis de instância
    Map<String,Integer> sats1;
    Map<Integer, List<String>> reviewsMes; // Mes, Lista de ReviewId

    /**
     * Construtor de cópia do tipo Estatisticas.
     * @param stats stats
     */
    public Estatisticas(GestReviews stats) {
        super(stats);
        this.sats1 = new HashMap<>();
        this.reviewsMes = new HashMap<>();
    }

    /**
     * Método que calcula todos os dados referentes às estatísticas 1.
     */
    public void stats1() {
        Map<String,Integer> negociosAvaliados = new HashMap<>();
        Map<String,Integer> usersComReviews = new HashMap<>();
        int reviewsSemImpacto = 0;

        for (Review r : super.getCatalogoR().getReviews().values()){
            negociosAvaliados.putIfAbsent(r.getBusinessIDFromReview(), 1);
            usersComReviews.putIfAbsent(r.getUserIDFromReview(), 1);

            if((r.getFunny() + r.getCool() + r.getUseful()) == 0)
                reviewsSemImpacto++;
        }

        this.sats1.put("totalNegocios",super.getCatalogoB().getBusinesses().size());
        this.sats1.put("negociosAvaliados",negociosAvaliados.size());
        this.sats1.put("negociosNaoAvaliados",(super.getCatalogoB().getBusinesses().size() - negociosAvaliados.size()));
        this.sats1.put("totalUsers",super.getCatalogoU().getUsers().size());
        this.sats1.put("usersComReviews",usersComReviews.size());
        this.sats1.put("usersSemReviews",(super.getCatalogoU().getUsers().size() - usersComReviews.size()));
        this.sats1.put("reviewsSemImpacto",reviewsSemImpacto);
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * dados das estatísticas 1.
     * @return String com info textual
     */
    public String toStringStats1() {
        StringBuilder sb = new StringBuilder("\nNome dos ficheiros: ");
        sb.append(super.getFiles().toString()).append('\n');
        sb.append("Nº de reviews errados: ").append(super.getReviewsErradas()).append("\n");
        sb.append("Nº de negócios: ").append(this.sats1.get("totalNegocios")).append("\n");
        sb.append("Nº de diferentes negócios avaliados: ").append(this.sats1.get("negociosAvaliados")).append("\n");
        sb.append("Nº de negócios não avaliados: ").append(this.sats1.get("negociosNaoAvaliados")).append("\n");
        sb.append("Nº de users: ").append(this.sats1.get("totalUsers")).append("\n");
        sb.append("Nº de users que fizeram reviews: ").append(this.sats1.get("usersComReviews")).append("\n");
        sb.append("Nº de users que não fizeram reviews: ").append(this.sats1.get("usersSemReviews")).append("\n");
        sb.append("Nº de reviews com 0 impacto: ").append(this.sats1.get("reviewsSemImpacto"));
        return sb.toString();
    }

    /**
     * Método que calcula todos os dados referentes às estatísticas 2.
     */
    public void stats2(){
        Map<String, Review> reviews = super.getCatalogoR().getReviews();
        for (Review r : reviews.values()){
            if (this.reviewsMes.containsKey(r.getMes()))
                this.reviewsMes.get(r.getMes()).add(r.getID());
            else
                this.reviewsMes.put(r.getMes(), new ArrayList<>(Collections.singleton(r.getID())));
        }
    }

    /**
     * Método que calcula a média das classificações de todas as reviews feitas
     * mês a mês. Os resultados são armazenados numa lista de tamanho 13, um índice
     * para cada mês, e na última posição encontra-se a média global.
     * @return Lista com as médias
     */
    private List<Float> mediaClassReviews(){
        List<Float> medias = new ArrayList<>();
        float total = 0;
        int reviews = 0;
        Map<String, Review> mapReviews = super.getCatalogoR().getReviews();
        for (List<String> ids : this.reviewsMes.values()){
            float totalMes = 0;
            for (String reviewId : ids){
                totalMes += mapReviews.get(reviewId).getStars();
            }
            medias.add(totalMes/ids.size());
            total += totalMes;
            reviews += ids.size();
        }
        medias.add(total/reviews);
        return medias;
    }

    /**
     * Método que conta quantos users distintos fizeram reviews
     * dependendo da lista de reviews ids que recebe.
     * @param ids Lista de reviews ids
     * @return Número de users distintos
     */
    private int usersDistintos(List<String> ids){
        Map<String, Integer> aux = new HashMap<>();
        Map<String, Review> reviews = super.getCatalogoR().getReviews();
        for (String id : ids){
            String userId = reviews.get(id).getUserIDFromReview();
            aux.putIfAbsent(userId, 1);
        }
        return aux.size();
    }

    /**
     * Método que calcula o número de users distintos que fizeram
     * reviews mês a mês. O resultado é armazenado numa lista de
     * tamanho 12.
     * @return Lista com o número de users
     */
    private List<Integer> usersDistintosAno(){
        List<Integer> users = new ArrayList<>();
        for (List<String> ids : this.reviewsMes.values()){
            users.add(this.usersDistintos(ids));
        }
        return users;
    }

    /**
     * Método que armazena numa string toda a informação relativa aos
     * daddos das estatísticas 2.
     * @return String com info textual
     */
    public String toStringStats2() {
        int reviews = 0;
        for (List<String> ids : this.reviewsMes.values()) reviews += ids.size();
        List<Float> medias = this.mediaClassReviews();
        List<Integer> users = this.usersDistintosAno();
        int totalUsers = 0;
        for (Integer num : users) totalUsers += num;
        StringBuilder sb = new StringBuilder("\n");
        sb.append("          | Reviews por mês |   Media   | Users distintos |\n");
        sb.append("Janeiro   |      ").append(this.reviewsMes.get(1).size()).append("      | ").append(medias.get(0)).append(" |       ").append(users.get(0)).append("     |\n");
        sb.append("Fevereiro |      ").append(this.reviewsMes.get(2).size()).append("      | ").append(medias.get(1)).append(" |       ").append(users.get(1)).append("     |\n");
        sb.append("Março     |      ").append(this.reviewsMes.get(3).size()).append("      | ").append(medias.get(2)).append(" |       ").append(users.get(2)).append("     |\n");
        sb.append("Abril     |      ").append(this.reviewsMes.get(4).size()).append("      | ").append(medias.get(3)).append(" |       ").append(users.get(3)).append("     |\n");
        sb.append("Maio      |      ").append(this.reviewsMes.get(5).size()).append("      | ").append(medias.get(4)).append("  |       ").append(users.get(4)).append("     |\n");
        sb.append("Junho     |      ").append(this.reviewsMes.get(6).size()).append("      | ").append(medias.get(5)).append(" |       ").append(users.get(5)).append("     |\n");
        sb.append("Julho     |      ").append(this.reviewsMes.get(7).size()).append("      | ").append(medias.get(6)).append(" |       ").append(users.get(6)).append("     |\n");
        sb.append("Agosto    |      ").append(this.reviewsMes.get(8).size()).append("      | ").append(medias.get(7)).append(" |       ").append(users.get(7)).append("     |\n");
        sb.append("Setembro  |      ").append(this.reviewsMes.get(9).size()).append("      | ").append(medias.get(8)).append("  |       ").append(users.get(8)).append("     |\n");
        sb.append("Outubro   |      ").append(this.reviewsMes.get(10).size()).append("      | ").append(medias.get(9)).append(" |       ").append(users.get(9)).append("     |\n");
        sb.append("Novembro  |      ").append(this.reviewsMes.get(11).size()).append("      | ").append(medias.get(10)).append(" |       ").append(users.get(10)).append("     |\n");
        sb.append("Dezembro  |      ").append(this.reviewsMes.get(12).size()).append("      | ").append(medias.get(11)).append(" |       ").append(users.get(11)).append("     |\n");
        sb.append("Total     |     ").append(reviews).append("      | ").append(medias.get(12)).append(" |      ").append(totalUsers).append("     |\n");
        return sb.toString();
    }

}