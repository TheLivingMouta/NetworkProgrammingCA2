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

    private static FilmManager filmManager;

    public static void main(String[] args) {

        try(ServerSocket serverSocket = new ServerSocket(FilmService.PORT)){
            filmManager = new FilmManager();

            boolean validSession = true;
            while(validSession){
                Socket socket = serverSocket.accept();

                try(Scanner clientInput = new Scanner(socket.getInputStream());
                PrintWriter clientOutput = new PrintWriter(socket.getOutputStream())){
                    String request = clientInput.nextLine();
                    System.out.println(socket.getInetAddress() + ":" + socket.getPort());

                    String response = null;

                    String[] components = request.split(FilmService.DELIMITER);
                    switch (components[0]) {
                        case FilmService.ADD_FILM ->

                    }
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
