package Classes;

import java.util.Objects;

public class Film {

    private String title;

    private String genre;

    private double totalRatings;

    private int numberOfRatings;

    public String getTitle() {
        return title;
    }

    public Film(String title, String genre, double totalRatings, int numberOfRatings) {
        this.title = title;
        this.genre = genre;
        this.totalRatings = totalRatings;
        this.numberOfRatings = numberOfRatings;
    }

    public Film(String title, String genre){
        this.title = title;
        this.genre = genre;
    }

    public Film(String title){
        this.title = title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(double totalRatings) {
        this.totalRatings = totalRatings / numberOfRatings;
    }

    public int getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(int numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    @Override
    public String toString() {
        return "Film{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", totalRatings=" + totalRatings +
                ", numberOfRatings=" + numberOfRatings +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(title, film.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, genre, totalRatings, numberOfRatings);
    }
}
