package Exceptions;

/**
 * Error that occurs then doing operation with the database using {@link DatabaseConnection.ConnectionInformation} Class
 */
public class ConnectionException extends RuntimeException {
    public ConnectionException(String message) {
        super(message);
    }
}
