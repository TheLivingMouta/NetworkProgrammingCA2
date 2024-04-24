package Server;

import business.Film;
import business.FilmManager;
import business.User;
import business.UserManager;
import protocol.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

public class Server {

    private static FilmManager fm;
    private static UserManager um;
    private static User lu;

    public static void main(String[] args) {

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

        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + FilmService.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }


    }
}

