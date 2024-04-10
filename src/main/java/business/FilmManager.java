package business;

import java.util.ArrayList;
import java.util.List;

public class FilmManager {

    private final ArrayList<Film> filmList = new ArrayList<Film>();

    private  void bootstrapFlimList(){
        Film f1 = new Film("Ready Player One", "Action drama", 8.6, 1234);
        Film f2 = new Film("The Adventures of Tintin", "Action/family", 10, 243000);
        Film f3 = new Film("Dragon ball super: Super Hero", "Action/Animated", 9.7, 98765);
        Film f4 = new Film("The boy and the Heron", "Weird/Drama", 8.6, 1243567);

        filmList.add(f1);
        filmList.add(f2);
        filmList.add(f3);
        filmList.add(f4);

    }

    public FilmManager(){
        bootstrapFlimList();
    }

    public boolean addFilm(String title, String genre){
        Film newfilm = new Film(title, genre);

        if(filmList.contains(newfilm)){
            return false;
        } else {
            filmList.add(newfilm);
            return true;
        }

    }

    public boolean removeFilm(String title){
        Film existingfilm = new Film(title);

        if(filmList.contains(existingfilm)){
            filmList.remove(existingfilm);
            return  true;
        } else {
            return false;
        }

    }

    public Film getFilmByTitle(String title){
        Film existingfilm = new Film(title);

        for(Film film : filmList){
            if(film.getTitle().equals(title)){
                return film;
            }
        }
        return null;
    }

    public List<Film> getFilmByGenre(String genre){
        List<Film> filmsByGenre = new ArrayList<>();
        for(Film film : filmList){
            if(film.getGenre().equals(genre)){
                filmsByGenre.add(film);
            }
        }
        return filmsByGenre;
    }

    public void rateFilm(String title, double rating){
        Film film = getFilmByTitle(title);
        if(film != null){
            film.setNumberOfRatings(film.getNumberOfRatings() + 1);
            film.setTotalRatings(film.getTotalRatings() + rating);
        }
    }

    public void displayFilms(){
        int count = 1;
        for (Film film : filmList) {
            System.out.println(count++ + " " + film.getTitle() + " " + film.getGenre() + " " + film.getTotalRatings() + " " + film.getNumberOfRatings());
        }
    }

    public static void main(String[] args) {
        FilmManager filmManager = new FilmManager();

        System.out.println("Display all films");
        filmManager.displayFilms();

        System.out.println("---------------------------");

        System.out.println("Adding a film");
        filmManager.addFilm("Brians Film", "Drama/Comedy");

        System.out.println("Display all films");
        filmManager.displayFilms();

        System.out.println("---------------------------");

        System.out.println("Getting a film by title");
        System.out.println(filmManager.getFilmByTitle("Brians Film"));

        System.out.println("---------------------------");

        System.out.println("Get film by genre");
        System.out.println(filmManager.getFilmByGenre("Drama/Comedy"));

        System.out.println("---------------------------");

        System.out.println("Rating a film");
        filmManager.rateFilm("Brians Film", 3.7);
        filmManager.rateFilm("Brians Film", 4.7);

        System.out.println("Display all films");
        filmManager.displayFilms();

        System.out.println("---------------------------");

        System.out.println("removing a Film");
        filmManager.removeFilm("Brians Film");

        System.out.println("Display all films");
        filmManager.displayFilms();

        System.out.println("---------------------------");

    }

}
