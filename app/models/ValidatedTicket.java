package models;

/**
 * Created by missionary on 15-08-12.
 */
public class ValidatedTicket {

    public enum Status { VALIDATED, ERR_EMAIL, ERR_INVALID }

    private String errorMessage;

    private Status status;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setStatus(final Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }
}
