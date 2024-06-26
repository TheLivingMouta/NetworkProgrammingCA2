package filmapp.server;
import filmapp.business.*;
import filmapp.model.*;
import filmapp.protocol.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class FilmClientHandler implements Runnable {

    private final Socket dataSocket;
    private final FilmManager fm;
    private final UserManager um;
    private User lu;

    public FilmClientHandler(Socket dataSocket, FilmManager fm, UserManager um) {
        this.dataSocket = dataSocket;
        this.fm = fm;
        this.um = um;
    }

    public void run() {
        try (Scanner input = new Scanner(dataSocket.getInputStream());
             PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {

            boolean validSession = true;
            while (validSession) {
                String message = input.nextLine();
                System.out.println("Message received from " + dataSocket.getInetAddress()+":"+ dataSocket.getPort() + ": " + message);
                    String[] components = message.split(FilmService.DELIMITER);
                String response = null;

                    switch (components[0]) {
                        case FilmService.EXIT:
                            if (components.length == 1) {
                                validSession = false;
                                response = FilmService.GOODBYE;
                            } else {
                                response = FilmService.INVALID_REQUEST;
                            }
                            break;
                        case FilmService.SHUTDOWN:
                            if (components.length == 1 && lu.getAdminStatus() == 1) {
                                response = FilmService.SHUTTING_DOWN;
                            } else {
                                response = FilmService.INSUFFICIENT_PERMISSIONS;
                            }
                            break;
                        case FilmService.REGISTER:
                            response = register(components);
                            break;
                        case FilmService.LOGIN:
                            response = login(components);
                            break;
                        case FilmService.LOGOUT:
                            response = logout(components);
                            break;
                        case FilmService.RATING:
                            response = rateFilm(components);
                            break;
                        case FilmService.SEARCH_TITLE:
                            response = searchByName(components);
                            break;
                        case FilmService.SEARCH_GENRE:
                            response = searchByGenre(components);
                            break;
                        case FilmService.ADD_FILM:
                            response = addFilm(components);
                            break;
                        case FilmService.REMOVE_FILM:
                            response = removeFilm(components);
                            break;
                        default:
                            response = FilmService.INVALID_REQUEST;
                    }

                    output.println(response);
                    output.flush();

                }

    }catch (IOException e) {
        System.out.println("IOException occurred on data socket when communicating with " + dataSocket.getInetAddress() + ":" + dataSocket.getPort());
        System.out.println(e.getMessage());
    }
}


    private String login(String[] components) {

        String response = null;


        String username = components[1];
        String password = components[2];


        if (components.length != 3) {
            response = FilmService.FAILED;
        } else {
            lu = um.validateUser(username, password);
            if (lu == null) {
                response = FilmService.FAILED;
            } else {
                if (lu.getAdminStatus() == 0) {
                    response = FilmService.SUCCESS_USER;
                } else if (lu.getAdminStatus() == 1) {
                    response = FilmService.SUCCESS_ADMIN;
                }
            }
        }


        return response;

    }

    private String register(String[] components){
        String response;

        String username = components[1];
        String password = components[2];

        if(components.length != 3){
            response = FilmService.FAILED;
        } else {

            boolean userAdded = um.addUser(username, password);

            if(userAdded){
                response = FilmService.ADDED;
            } else {
                response = FilmService.REJECTED;
            }

        }
        return response;
    }

    private String logout(String[] components){
        String response;

        if(components.length != 1){
            response = FilmService.FAILED;
        } else {
            lu = null;
            response = FilmService.LOGOUT;
        }

        return response;
    }

    private String rateFilm(String[] components) {
        String response;

        String title = components[1];
        double ratingOutofTen = Double.parseDouble(components[2]);
        Film film = fm.getFilmByTitle(title);

        if (lu == null) {
            response = FilmService.NOT_LOGGED_IN;
        } else {
            if (film != null) {
                if (ratingOutofTen >= 0 && ratingOutofTen <= 10) {
                    film.setNumberOfRatings(film.getNumberOfRatings() + 1);
                    film.setTotalRatings(film.getTotalRatings() + ratingOutofTen);
                    response = FilmService.SUCCESS;
                } else {
                    response = FilmService.INVALID_RATING_SUPPLIED;
                }
            } else {
                response = FilmService.NO_MATCH_FOUND;
            }

        }
        return response;
    }
    private String searchByName(String[] components){
        String response;

        String title = components[1];
        Film film = fm.getFilmByTitle(title);

        if(components.length != 2){
            response = FilmService.NO_MATCH_FOUND;
        } else {
            response = film.toString();
        }
        return response;
    }

    private String searchByGenre(String[] components){

        String response;

        if(components.length != 1) {
            response = FilmService.INVALID_REQUEST;
        } else {
            String genre = components[1];
            List<Film> filmByGenre = fm.getFilmByGenre(genre);

            if(filmByGenre.isEmpty()){
                response = FilmService.NO_MATCH_FOUND;
            } else {
                for(Film film : filmByGenre){
                    if(film.getGenre().equals(genre)){
                        filmByGenre.add(film);
                    }
                }

                response = filmByGenre.toString();

            }

        }
        return response;
    }

    private String addFilm(String[] components) {
        String response;

        String title = components[1];
        String genre = components[2];

        if (lu.getAdminStatus() == 1) {
            if (fm.getFilmByTitle(title) != null) {
                response = FilmService.EXISTS;
            } else {

                fm.addFilm(title, genre);
                response = FilmService.ADDED;
            }

        } else {
            response = FilmService.INSUFFICIENT_PERMISSIONS;
        }
        return response;
    }

    private String removeFilm(String[] components){
        String response;

        String title = components[1];

        if(lu.getAdminStatus() == 1){
            Film film = fm.getFilmByTitle(title);

            if(film != null){
                fm.removeFilm(title);
                response = FilmService.REMOVED;
            } else  {
                response = FilmService.NO_MATCH_FOUND;
            }

        } else {
            response = FilmService.INSUFFICIENT_PERMISSIONS;
        }
        return response;
    }


}

