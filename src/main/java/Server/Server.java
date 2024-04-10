package Server;

import business.FilmManager;
import protocol.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    private static FilmManager fm;

    public static void main(String[] args) {

        try(ServerSocket ls = new ServerSocket(FilmService.PORT)){
            fm = new FilmManager();

            boolean validSession = true;
            while(validSession){
                Socket ds = ls.accept();

                try(Scanner clientInput = new Scanner(ds.getInputStream());
                PrintWriter clientOutput = new PrintWriter(ds.getOutputStream())){
                    String request = clientInput.nextLine();
                    System.out.println(ds.getInetAddress() + ":" + ds.getPort());

                    String response = null;

                    String[] components = request.split(FilmService.DELIMITER);
                    switch (components[0]) {
                        case FilmService.
                    }
                }

            }

        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + QuoteService.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }

    }

}
