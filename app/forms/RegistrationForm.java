package forms;

import play.data.validation.Constraints;
import providers.MyUsernamePasswordAuthProvider;

/**
 * Created by missionary on 15-07-28.
 */
public class RegistrationForm extends MyUsernamePasswordAuthProvider.MySignup implements com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.UsernamePassword {

    @Constraints.Required protected String firstName;

    @Constraints.Required protected String lastName;

    @Constraints.Required protected String ticketId;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
