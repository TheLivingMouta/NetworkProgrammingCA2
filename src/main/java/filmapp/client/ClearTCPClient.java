package filmapp.client;

import filmapp.protocol.FilmService;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClearTCPClient {
    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        // Requests a connection
        try (Socket dataSocket = new Socket("localhost", FilmService.PORT)) {

            // Sets up communication lines
            // Create a Scanner to receive messages
            // Create a Printwriter to send messages
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                // Repeated:
                // Ask user for information to be sent
                System.out.println("Please enter a message to be sent:");
                String message = userInput.nextLine();
                // Send message to server
                output.println(message);
                // Flush message through to server
                output.flush();

                // Receive message from server
                String response = input.nextLine();
                // Display result to user
                System.out.println("Received from server: " + response);
            }

        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found at this moment. Try again later");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
        // Close connection to server
    }
}
