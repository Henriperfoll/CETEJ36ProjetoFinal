package henriperfoll.cetej36projetofinal.model;

import java.util.Comparator;
import java.util.Date;

public class MovieReview {

    private long id;
    private String movieName;
    private String review;
    private int score;

    public static Comparator<MovieReview> comparador = new Comparator<MovieReview>() {

        @Override
        public int compare(MovieReview m1, MovieReview m2) {

            int compAlfabetica = m1.getMovieName().compareToIgnoreCase(m2.getMovieName());
            return compAlfabetica;

        }
    };

    public String getMovieName() {
        return movieName;
    }

    @Override
    public String toString() {
        return getMovieName();
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public MovieReview(String movieName, String review) {
        this.movieName = movieName;
        this.review = review;
    }

    public MovieReview() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
