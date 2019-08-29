package henriperfoll.cetej36projetofinal.model;

import java.util.Date;

public class MovieReview {

    private String movieName;
    private String review;

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
}
