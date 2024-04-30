package filmapp.business;

import filmapp.model.Film;

import java.util.ArrayList;
import java.util.List;

/**
 * Manages film operations including adding, removing, and retrieving films based on various criteria.
 * This class initializes with a default list of films and provides methods to manipulate and query the film list.
 */
public class FilmManager {

    private final ArrayList<Film> filmList = new ArrayList<Film>();

    /**
     * Populates the film list with default data.
     */
    private  void bootstrapFlimList() {
        Film f1 = new Film("Ready Player One", "Action drama", 8.6, 1234);
        Film f2 = new Film("The Adventures of Tintin", "Action/family", 10, 243000);
        Film f3 = new Film("Dragon ball super: Super Hero", "Action/Animated", 9.7, 98765);
        Film f4 = new Film("The boy and the Heron", "Weird/Drama", 8.6, 1243567);

        synchronized (filmList) {
            filmList.add(f1);
            filmList.add(f2);
            filmList.add(f3);
            filmList.add(f4);

        }
    }

    /**
     * Initializes the FilmManager and populates it with a predefined set of films.
     */
    public FilmManager(){
        bootstrapFlimList();
    }

    /**
     * Adds a new film with specified title and genre to the film list.
     *
     * @param title the title of the new film
     * @param genre the genre of the new film
     * @return true if the film was added successfully, false if the film already exists
     */
    public boolean addFilm(String title, String genre) {
        Film newfilm = new Film(title, genre);

        synchronized (filmList) {
            if (filmList.contains(newfilm)) {
                return false;

            } else {
                filmList.add(newfilm);
                return true;
            }
        }
    }

    /**
     * Removes a film by title.
     *
     * @param title the title of the film to be removed
     * @return true if the film was successfully removed, false otherwise
     */
    public boolean removeFilm(String title) {
        Film existingfilm = new Film(title);

        boolean flag = false;

        synchronized (filmList) {
            filmList.remove(existingfilm);
        }
        return flag;
    }

    /**
     * Retrieves a film by its title.
     *
     * @param title the title of the film to retrieve
     * @return the Film object if found, null otherwise
     */
    public Film getFilmByTitle(String title) {
        Film existingfilm = new Film(title);

        synchronized (filmList) {
            for (Film film : filmList) {
                if (film.getTitle().equals(title)) {
                    return existingfilm;
                }
            }
            return null;
        }
    }

    /**
     * Retrieves all films matching a specified genre.
     *
     * @param genre the genre to search for
     * @return a list of films matching the specified genre
     */
    public List<Film> getFilmByGenre(String genre) {
        List<Film> filmsByGenre = new ArrayList<>();

        synchronized (filmList) {
            for (Film film : filmList) {
                if (film.getGenre().equals(genre)) {
                    filmsByGenre.add(film);
                }
            }
            return filmsByGenre;
        }
    }

    /**
     * Rates a specified film by adding a rating.
     *
     * @param title the title of the film to rate
     * @param rating the rating to add to the film
     */
    public void rateFilm(String title, double rating) {
        Film film = getFilmByTitle(title);
            if (film != null) {
                synchronized (film) {
                    film.setNumberOfRatings(film.getNumberOfRatings() + 1);
                    film.setTotalRatings(film.getTotalRatings() + rating);
                }
            }
        }

    public void displayFilms() {
        int count = 1;
        synchronized (filmList) {
            for (Film film : filmList) {
                System.out.println(count++ + " " + film.getTitle() + " " + film.getGenre() + " " + film.getTotalRatings() + " " + film.getNumberOfRatings());
            }
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
