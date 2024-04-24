package Server;

import business.Film;
import business.FilmManager;
import business.User;
import business.UserManager;
import protocol.FilmService;
import server.FilmClientHandler;

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
            um = new UserManager();

            boolean validSession = true;
            while (true) {
                Socket ds = ls.accept();
                FilmClientHandler filmHandler = new FilmClientHandler(ds, fm, um);
                Thread worker = new Thread(filmHandler);
                worker.start();


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


