package filmapp.server;

import filmapp.business.*;
import filmapp.protocol.FilmService;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static FilmManager fm;
    private static UserManager um;

    public static void main(String[] args) {

        try (ServerSocket ls = new ServerSocket(FilmService.PORT)) {
            fm = new FilmManager();
            um = new UserManager();

            boolean validSession = true;
            while (validSession) {
                Socket ds = ls.accept();
                filmapp.server.FilmClientHandler filmHandler = new filmapp.server.FilmClientHandler(ds, fm, um);
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


