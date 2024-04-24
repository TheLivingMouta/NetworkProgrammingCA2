package server;

import business.Film;
import business.FilmManager;
import business.User;
import business.UserManager;
import protocol.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class FilmClientHandler implements Runnable {

    private Socket dataSocket;
    private FilmManager fm;
    private UserManager um;
    private User lu;

    public FilmClientHandler(Socket dataSocket, FilmManager fm, UserManager um) {
        this.dataSocket = dataSocket;
        this.fm = fm;
        this.um = um;

    }

    public void run() {

        try (ServerSocket ls = new ServerSocket(FilmService.PORT)) {
            fm = new FilmManager();

            boolean validSession = true;
            while (validSession) {
                Socket ds = ls.accept();

                try (Scanner clientInput = new Scanner(ds.getInputStream());
                     PrintWriter clientOutput = new PrintWriter(ds.getOutputStream())) {
                    String request = clientInput.nextLine();
                    System.out.println(ds.getInetAddress() + ":" + ds.getPort());

                    String response = null;

                    String[] components = request.split(FilmService.DELIMITER);
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

                    clientOutput.println(response);
                    clientOutput.flush();

                }

            }
        }catch (IOException e) {
            System.out.println("IOException occurred on data socket when communicating with " + dataSocket.getInetAddress() + ":" + dataSocket.getPort());
            System.out.println(e.getMessage());
        }
    }


    private String login(String[] components) {

        String response;

        String username = components[1];
        String password = components[2];

        if(components.length != 3){
            response = FilmService.FAILED;
        } else {
            lu = um.validateUser(username, password);
            if(lu == null){
                response = FilmService.FAILED;
            } else {
                if(lu.getAdminStatus() != 1){
                    response = FilmService.SUCCESS_USER;
                } else  {
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

            boolean userAdded = um.addUser(username, password, 0);

            if(userAdded != false){
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

        if(components.length != 1){
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

        if (lu.getAdminStatus() != 0) {
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

        if(lu.getAdminStatus() != 0){
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

