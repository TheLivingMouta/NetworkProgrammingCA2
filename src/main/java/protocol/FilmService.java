package protocol;

public class FilmService {

    public static final String HOST = "localhost";

    public static  final int PORT  = 41235;

    //DELIMITER

    public static final String DELIMITER = "%%";

    //  REQUEST

    // GENERAL

    public static final String EXIT = "EXIT";

    public static final String SHUTDOWN = "SHUTDOWN";

    //USER

    public static final String REGISTER = "REGISTER";

    public static final String LOGIN = "LOGIN";

    public static final String LOGOUT = "LOGOUT";

    //FILM
    public static final String RATING = "RATING";

    public static final String SEARCH_TITLE = "SEARCH_TITLE";

    public static final String SEARCH_GENRE = "SEARCH_GENRE";

    //  ADMIN REQUEST FEATURE
    public static final String ADD_FILM = "ADD_FILM";

    public static final String REMOVE_FILM = "REMOVE_FILM";



    //  RESPONSE

    public static final String ADDED = "ADDED";

    public static final String REMOVED = "REMOVED";

    public static final String REJECTED = "REJECTED";

    public static final String SUCCESS_ADMIN = "SUCCESS_ADMIN";

    public static final String SUCCESS_USER = "SUCCESS_USER";

    public static final String FAILED = "FAILED";

    public static final String SUCCESS = "SUCCESS";

    public static final String INVALID_RATING_SUPPLIED = "INVALID_RATING_SUPPLIED";

    public static final String NOT_LOGGED_IN = "NOT_LOGGED_IN";

    public static final String NO_MATCH_FOUND = "NO_MATCH_FOUND";

    public static final String EXISTS = "EXISTS";

    public static final String INSUFFICIENT_PERMISSIONS = "INSUFFICIENT_PERMISSIONS";

    public static final String INVALID_REQUEST = "INVALID_REQUEST";

    public static final String GOODBYE = "GOODBYE";

    public static final String SHUTTING_DOWN = "SHUTTING_DOWN";

}
