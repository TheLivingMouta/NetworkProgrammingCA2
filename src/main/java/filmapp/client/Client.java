package filmapp.client;

import filmapp.protocol.FilmService;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Scanner userInput = new Scanner(System.in)) {
            Socket dataSocket = new Socket(FilmService.HOST, FilmService.PORT);
            Scanner input = new Scanner(dataSocket.getInputStream());
            PrintWriter output = new PrintWriter(dataSocket.getOutputStream(), true);

            boolean validSession = true;
            while (validSession) {
                try {
                    String message = generateRequest(userInput);
                    output.println(message);
                    output.flush();

                    if (message.startsWith(FilmService.EXIT)) {
                        validSession = false;
                        continue;
                    }

                    if (input.hasNextLine()) {  // Check if there's a line available to read
                        String response = input.nextLine();
                        System.out.println("Received from server: " + response);
                        if (response.equals(FilmService.GOODBYE)) {
                            validSession = false;
                        }
                    } else {
                        System.out.println("No response from server.");
                        validSession = false;
                    }
                } catch (NoSuchElementException e) {
                    System.out.println("Failed to read input: " + e.getMessage());
                    validSession = false;
                }
            }
        } catch (UnknownHostException e) {
            System.out.println("Host cannot be found. Try again later.");
        } catch (IOException e) {
            System.out.println("An IO Exception occurred: " + e.getMessage());
        }
        // Close the scanner only once you are done with all operations
    }

    private static void displayMenu() {
        System.out.println("\n--- Film Service Menu ---");
        System.out.println("0) Exit");
        System.out.println("1) Register");
        System.out.println("2) Login");
        System.out.println("3) Search Film by Title");
        System.out.println("4) Add Film");
        System.out.println("5) Rate Film");
        System.out.println("6) Logout");
        System.out.println("Please select an option:");

    }

    public static String generateRequest(Scanner userInput) {
        boolean valid = false;
        String request = null;

        while (!valid) {
            displayMenu();
            System.out.println("Choose an option: ");
            String choice = userInput.nextLine();

            switch (choice) {
                case "0":
                    System.out.println("Exiting the service.");
                    request = FilmService.EXIT;
                    valid = true;
                    break;
                case "1":
                    request = register(userInput);
                    valid = true;
                    break;
                case "2":
                    request = login(userInput);
                    valid = true;
                    break;
                case "3":
                    request = searchByTitle(userInput);
                    valid = true;
                    break;
                case "4":
                    request = addFilm(userInput);
                    valid = true;
                    break;
                case "5":
                    request = rateFilm(userInput);
                    valid = true;
                    break;
                case "6":
                    request = searchByGenre(userInput);
                    valid = true;
                    break;
                case "7":
                    System.out.println("Logging out.");
                    request = FilmService.LOGOUT;
                    valid = true;
                    break;
                default:
                    System.out.println("Invalid option, please select a valid one.");
                    System.out.println("------------------------------------");
            }
        }
        return request;
    }



    private static String register(Scanner userInput) {
        System.out.println("Enter username:");
        String username = userInput.nextLine();
        System.out.println("Enter password:");
        String password = userInput.nextLine();
        return FilmService.REGISTER + FilmService.DELIMITER + username + FilmService.DELIMITER + password;
    }

    private static String login(Scanner userInput) {
        System.out.println("Enter username:");
        String username = userInput.nextLine();
        System.out.println("Enter password:");
        String password = userInput.nextLine();
        return FilmService.LOGIN + FilmService.DELIMITER + username + FilmService.DELIMITER + password;
    }

    private static String addFilm(Scanner userInput) {
        System.out.println("Enter film title:");
        String title = userInput.nextLine();
        System.out.println("Enter film genre:");
        String genre = userInput.nextLine();
        return FilmService.ADD_FILM + FilmService.DELIMITER + title + FilmService.DELIMITER + genre;
    }

    private static String rateFilm(Scanner userInput) {
        System.out.println("Enter film title:");
        String title = userInput.nextLine();
        System.out.println("Enter your rating (0-10):");
        double rating = Double.parseDouble(userInput.nextLine());
        return FilmService.RATING + FilmService.DELIMITER + title + FilmService.DELIMITER + rating;
    }

    private static String searchByTitle(Scanner userInput) {
        System.out.println("Enter film title to search:");
        String title = userInput.nextLine();
        return FilmService.SEARCH_TITLE + FilmService.DELIMITER + title;
    }

    private static String searchByGenre(Scanner userInput) {
        System.out.println("Enter genre to search:");
        String genre = userInput.nextLine();
        return FilmService.SEARCH_GENRE + FilmService.DELIMITER + genre;
    }

    private static void handleGetResponse(String response) {
        String [] responseComponents = response.split(FilmService.DELIMITER);
        if(responseComponents.length == 2){
            System.out.println("Quote received:");
            System.out.println("\"" + responseComponents[0] + "\"");
            System.out.println("\t- " + responseComponents[1]);
        }else{
            System.out.println("Unrecognised response detected. Please try again later.");
        }
    }
}
