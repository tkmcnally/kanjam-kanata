package providers;

import forms.RegistrationForm;
import models.KanJamIdentity;
import providers.MyUsernamePasswordAuthProvider.MySignup;

import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.feth.play.module.pa.user.NameIdentity;

public class MyUsernamePasswordAuthUser extends UsernamePasswordAuthUser
    implements KanJamIdentity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private final String firstName;
    private final String lastName;
    private final String ticketId;

    public MyUsernamePasswordAuthUser(final RegistrationForm signup) {
        super(signup.password, signup.email);
        this.firstName = signup.getFirstName();
        this.lastName = signup.getLastName();
        this.ticketId = signup.getTicketId();
    }

    /**
     * Used for password reset only - do not use this to signup a user!
     * @param password
     */
    public MyUsernamePasswordAuthUser(final String password) {
        super(password, null);
        firstName = null;
        lastName = null;
        ticketId = null;
    }

    @Override
    public String getName() {
        return firstName + " " + lastName;
    }

    @Override public String getTicketId() {
        return ticketId;
    }

    @Override public String getFirstName() {
        return firstName;
    }

    @Override public String getLastName() {
        return lastName;
    }
}
