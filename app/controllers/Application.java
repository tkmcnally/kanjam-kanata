package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.user.AuthUser;
import forms.RegistrationForm;
import models.User;
import models.ValidatedTicket;
import play.api.Routes;
import play.data.Form;
import play.mvc.*;
import providers.MyUsernamePasswordAuthProvider;
import views.html.account.profile;
import views.html.index;
import views.html.login;
import views.html.restricted;
import views.html.signup;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Application extends Controller {

    public static final String FLASH_MESSAGE_KEY = "message";
    public static final String FLASH_ERROR_KEY = "error";
    public static final String USER_ROLE = "user";

    public static Result index() {
        return ok(index.render(null));
    }

    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        final User localUser = User.findByAuthUserIdentity(currentAuthUser);
        return localUser;
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result restricted() {
        final User localUser = getLocalUser(session());
        return ok(restricted.render(localUser));
    }

    public static Result login() {
        return ok(login.render(MyUsernamePasswordAuthProvider.LOGIN_FORM));
    }

    public static Result doLogin() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<MyUsernamePasswordAuthProvider.MyLogin> filledForm = MyUsernamePasswordAuthProvider.LOGIN_FORM
            .bindFromRequest();
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(login.render(filledForm));
        } else {
            // Everything was filled
            return UsernamePasswordAuthProvider.handleLogin(ctx());
        }
    }

    public static Result signup() {
        return ok(signup.render(MyUsernamePasswordAuthProvider.SIGNUP_FORM));
    }

    /*public static Result jsRoutes() {
        return ok(
            Routes
                .javascriptRouter("jsRoutes", controllers.routes.javascript.Signup.forgotPassword()))
            .as("text/javascript");
    }*/

    public static Result doSignup() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<RegistrationForm> filledForm = MyUsernamePasswordAuthProvider.SIGNUP_FORM
            .bindFromRequest();

        if(!filledForm.hasErrors()) {
            ValidatedTicket validatedTicket = TicketController.validateTicket(filledForm.get());
            if (validatedTicket.getStatus() == ValidatedTicket.Status.ERR_INVALID) {
                filledForm.reject("ticketId", validatedTicket.getErrorMessage());
            } else if(validatedTicket.getStatus() == ValidatedTicket.Status.ERR_EMAIL) {
                filledForm.reject("email", validatedTicket.getErrorMessage());
            }
        }
        if (filledForm.hasErrors()) {
            // User did not fill everything properly
            return badRequest(signup.render(filledForm));
        } else {
            // Everything was filled
            // do something with your part of the form before handling the user
            // signup
            return UsernamePasswordAuthProvider.handleSignup(ctx());
        }
    }


    public static Result profile() {
        return ok(profile.render(getLocalUser(session())));
    }


    public static String formatTimestamp(final long t) {
        return new SimpleDateFormat("yyyy-dd-MM HH:mm:ss").format(new Date(t));
    }

}
