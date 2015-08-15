package models;

/**
 * Created by missionary on 15-08-14.
 */
public class TeamAction {

    public enum Status {
        SUCCESS, NOT_OWNER, NOT_VALID
    }

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
