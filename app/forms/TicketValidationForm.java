package forms;

import play.data.validation.Constraints;

/**
 * Created by missionary on 15-07-28.
 */
public class TicketValidationForm {

    @Constraints.Required
    @Constraints.Email
    protected String email;

    @Constraints.Required
    protected String ticketId;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

}
