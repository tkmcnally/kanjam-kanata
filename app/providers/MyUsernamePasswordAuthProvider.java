package providers;

import com.feth.play.module.mail.Mailer.Mail.Body;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider;
import com.feth.play.module.pa.providers.password.UsernamePasswordAuthUser;
import com.google.inject.Inject;
import controllers.routes;
import forms.RegistrationForm;
import models.LinkedAccount;
import models.Team;
import models.TokenAction;
import models.TokenAction.Type;
import models.User;
import play.Application;
import play.Logger;
import play.data.Form;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;
import play.i18n.Lang;
import play.i18n.Messages;
import play.mvc.Call;
import play.mvc.Http.Context;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static play.data.Form.form;

public class MyUsernamePasswordAuthProvider extends
    UsernamePasswordAuthProvider<String, MyLoginUsernamePasswordAuthUser, MyUsernamePasswordAuthUser, MyUsernamePasswordAuthProvider.MyLogin, RegistrationForm> {

    private static final String SETTING_KEY_VERIFICATION_LINK_SECURE =
        SETTING_KEY_MAIL + "." + "verificationLink.secure";
    private static final String SETTING_KEY_PASSWORD_RESET_LINK_SECURE =
        SETTING_KEY_MAIL + "." + "passwordResetLink.secure";
    private static final String SETTING_KEY_TEAM_INVITATION_LINK_SECURE =
        SETTING_KEY_MAIL + "." + "teamInvitationLink.secure";
    private static final String SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET =
        "loginAfterPasswordReset";

    private static final String EMAIL_TEMPLATE_FALLBACK_LANGUAGE = "en";

    @Override protected List<String> neededSettingKeys() {
        final List<String> needed = new ArrayList<String>(super.neededSettingKeys());
        needed.add(SETTING_KEY_VERIFICATION_LINK_SECURE);
        needed.add(SETTING_KEY_PASSWORD_RESET_LINK_SECURE);
        needed.add(SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET);
        return needed;
    }

    public static MyUsernamePasswordAuthProvider getProvider() {
        return (MyUsernamePasswordAuthProvider) PlayAuthenticate
            .getProvider(UsernamePasswordAuthProvider.PROVIDER_KEY);
    }

    public static class MyIdentity {

        public MyIdentity() {
        }

        public MyIdentity(final String email) {
            this.email = email;
        }

        @Required @Email public String email;

    }


    public static class MyLogin extends MyIdentity implements
        com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.UsernamePassword {

        @Required @MinLength(5) public String password;

        @Override public String getEmail() {
            return email;
        }

        @Override public String getPassword() {
            return password;
        }
    }


    public static class MySignup extends MyLogin {

        @Required @MinLength(5) public String repeatPassword;

        public String validate() {
            if (password == null || !password.equals(repeatPassword)) {
                return Messages.get("playauthenticate.password.signup.error.passwords_not_same");
            }
            return null;
        }
    }


    public static final Form<RegistrationForm> SIGNUP_FORM = form(RegistrationForm.class);
    public static final Form<MyLogin> LOGIN_FORM = form(MyLogin.class);

    @Inject public MyUsernamePasswordAuthProvider(Application app) {
        super(app);
    }

    protected Form<RegistrationForm> getSignupForm() {
        return SIGNUP_FORM;
    }

    protected Form<MyLogin> getLoginForm() {
        return LOGIN_FORM;
    }

    @Override
    protected com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.SignupResult signupUser(
        final MyUsernamePasswordAuthUser user) {
        final User u = User.findByUsernamePasswordIdentity(user);
        if (u != null) {
            if (u.emailValidated) {
                // This user exists, has its email validated and is active
                return SignupResult.USER_EXISTS;
            } else {
                // this user exists, is active but has not yet validated its
                // email
                return SignupResult.USER_EXISTS_UNVERIFIED;
            }
        }
        // The user either does not exist or is inactive - create a new one
        @SuppressWarnings("unused") final User newUser = User.create(user);
        // Usually the email should be verified before allowing login, however
        // if you return
        // return SignupResult.USER_CREATED;
        // then the user gets logged in directly
        return SignupResult.USER_CREATED_UNVERIFIED;
    }

    @Override
    protected com.feth.play.module.pa.providers.password.UsernamePasswordAuthProvider.LoginResult loginUser(
        final MyLoginUsernamePasswordAuthUser authUser) {
        final User u = User.findByUsernamePasswordIdentity(authUser);
        if (u == null) {
            return LoginResult.NOT_FOUND;
        } else {
            if (!u.emailValidated) {
                return LoginResult.USER_UNVERIFIED;
            } else {
                for (final LinkedAccount acc : u.linkedAccounts) {
                    if (getKey().equals(acc.providerKey)) {
                        if (authUser.checkPassword(acc.providerUserId, authUser.getPassword())) {
                            // Password was correct
                            return LoginResult.USER_LOGGED_IN;
                        } else {
                            // if you don't return here,
                            // you would allow the user to have
                            // multiple passwords defined
                            // usually we don't want this
                            return LoginResult.WRONG_PASSWORD;
                        }
                    }
                }
                return LoginResult.WRONG_PASSWORD;
            }
        }
    }

    @Override protected Call userExists(final UsernamePasswordAuthUser authUser) {
        return routes.Signup.exists();
    }

    @Override protected Call userUnverified(final UsernamePasswordAuthUser authUser) {
        return routes.Signup.unverified();
    }

    @Override protected MyUsernamePasswordAuthUser buildSignupAuthUser(final RegistrationForm signup,
        final Context ctx) {
        return new MyUsernamePasswordAuthUser(signup);
    }

    @Override protected MyLoginUsernamePasswordAuthUser buildLoginAuthUser(final MyLogin login,
        final Context ctx) {
        return new MyLoginUsernamePasswordAuthUser(login.getPassword(), login.getEmail());
    }


    @Override protected MyLoginUsernamePasswordAuthUser transformAuthUser(
        final MyUsernamePasswordAuthUser authUser, final Context context) {
        return new MyLoginUsernamePasswordAuthUser(authUser.getEmail());
    }

    @Override protected String getVerifyEmailMailingSubject(final MyUsernamePasswordAuthUser user,
        final Context ctx) {
        return Messages.get("kanjam.password.verify_signup.subject");
    }

    @Override protected String onLoginUserNotFound(final Context context) {
        context.flash().put(controllers.Application.FLASH_ERROR_KEY,
            Messages.get("kanjam.password.login.unknown_user_or_pw"));
        return super.onLoginUserNotFound(context);
    }

    @Override protected Body getVerifyEmailMailingBody(final String token,
        final MyUsernamePasswordAuthUser user, final Context ctx) {

        final boolean isSecure =
            getConfiguration().getBoolean(SETTING_KEY_VERIFICATION_LINK_SECURE);
        final String url = routes.Signup.verify(token).absoluteURL(ctx.request(), isSecure);

        final Lang lang = Lang.preferred(ctx.request().acceptLanguages());
        final String langCode = lang.code();

        final User newUser = new User();
        newUser.email = user.getEmail();
        newUser.firstName = user.getFirstName();
        newUser.lastName = user.getLastName();
        newUser.name = user.getName();

        final String html =
            getEmailTemplate("views.html.email.verify_email", langCode, url, token,
                newUser, user.getEmail(), null);
        final String text =
            getEmailTemplate("views.txt.email.verify_email", langCode, url, token,
                newUser, user.getEmail(), null);

        return new Body(text, html);
    }

    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    @Override protected String generateVerificationRecord(final MyUsernamePasswordAuthUser user) {
        return generateVerificationRecord(User.findByAuthUserIdentity(user));
    }

    protected String generateVerificationRecord(final User user) {
        final String token = generateToken();
        // Do database actions, etc.
        TokenAction.create(Type.EMAIL_VERIFICATION, token, user, null);
        return token;
    }

    protected String generatePasswordResetRecord(final User u) {
        final String token = generateToken();
        TokenAction.create(Type.PASSWORD_RESET, token, u, null);
        return token;
    }


    protected String generateTeamInvitationRecord(final User u, final String targetEmail) {
        final String token = generateToken();
        TokenAction.create(Type.TEAM_INVITE, token, u, targetEmail);


        return token;
    }

    protected String getPasswordResetMailingSubject(final User user, final Context ctx) {
        return Messages.get("kanjam.password_reset_email.subject");
    }

    protected String getTeamInvitationMailingSubject(final User user, final Context ctx) {
        return Messages.get("kanjam.team_invitiation_email.subject");
    }

    protected Body getTeamInvitationMailingBody(final String token, final Team team, final User user,
        final Context ctx) {

        final boolean isSecure =
            getConfiguration().getBoolean(SETTING_KEY_TEAM_INVITATION_LINK_SECURE);
        final String url = routes.Profile.joinTeam(String.valueOf(team.id), token).absoluteURL(
            ctx.request(), isSecure);

        final Lang lang = Lang.preferred(ctx.request().acceptLanguages());
        final String langCode = lang.code();

        final String html =
            getEmailTemplate("views.html.email.team_invitation", langCode, url, token,
                user, user.email, team);
        final String text =
            getEmailTemplate("views.txt.email.team_invitation", langCode, url, token,
                user, user.email, team);

        return new Body(text, html);
    }

    protected Body getPasswordResetMailingBody(final String token, final User user,
        final Context ctx) {

        final boolean isSecure =
            getConfiguration().getBoolean(SETTING_KEY_PASSWORD_RESET_LINK_SECURE);
        final String url = routes.Signup.resetPassword(token).absoluteURL(ctx.request(), isSecure);

        final Lang lang = Lang.preferred(ctx.request().acceptLanguages());
        final String langCode = lang.code();

        final String html =
            getEmailTemplate("views.html.email.password_reset", langCode, url, token,
                user, user.email, null);
        final String text =
            getEmailTemplate("views.txt.email.password_reset", langCode, url, token,
                user, user.email, null);

        return new Body(text, html);
    }

    public void sendPasswordResetMailing(final User user, final Context ctx) {
        final String token = generatePasswordResetRecord(user);
        final String subject = getPasswordResetMailingSubject(user, ctx);
        final Body body = getPasswordResetMailingBody(token, user, ctx);
        sendMail(subject, body, getEmailName(user));
    }

    public void sendTeamInvitationEmail(final Team team, final User user, final String invitee, final Context ctx) {
        final String token = generateTeamInvitationRecord(user, invitee);
        final String subject = getTeamInvitationMailingSubject(user, ctx);
        final Body body = getTeamInvitationMailingBody(token, team, user, ctx);
        sendMail(subject, body, invitee);
    }

    public boolean isLoginAfterPasswordReset() {
        return getConfiguration().getBoolean(SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET);
    }

    protected String getVerifyEmailMailingSubjectAfterSignup(final User user, final Context ctx) {
        return Messages.get("kanjam.password.verify_email.subject");
    }

    protected String getEmailTemplate(final String template, final String langCode,
        final String url, final String token, final User user, final String email, final Team team) {
        Class<?> cls = null;
        String ret = null;
        try {
            cls = Class.forName(template + "_" + langCode);
        } catch (ClassNotFoundException e) {
            Logger.warn("Template: '" + template + "_" + langCode
                + "' was not found! Trying to use English fallback template instead.");
        }
        if (cls == null) {
            try {
                cls = Class.forName(template + "_" + EMAIL_TEMPLATE_FALLBACK_LANGUAGE);
            } catch (ClassNotFoundException e) {
                Logger.error(
                    "Fallback template: '" + template + "_" + EMAIL_TEMPLATE_FALLBACK_LANGUAGE
                        + "' was not found either!");
            }
        }
        if (cls != null) {
            Method htmlRender = null;
            try {
                htmlRender =
                    cls.getMethod("render", String.class, String.class, User.class, String.class, Team.class);
                ret = htmlRender.invoke(null, url, token, user, email, team).toString();

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }

    protected Body getVerifyEmailMailingBodyAfterSignup(final String token, final User user,
        final Context ctx) {

        final boolean isSecure =
            getConfiguration().getBoolean(SETTING_KEY_VERIFICATION_LINK_SECURE);
        final String url = routes.Signup.verify(token).absoluteURL(ctx.request(), isSecure);

        final Lang lang = Lang.preferred(ctx.request().acceptLanguages());
        final String langCode = lang.code();

        final String html =
            getEmailTemplate("views.html.email.verify_email", langCode, url, token,
                user, user.email, null);
        final String text =
            getEmailTemplate("views.txt.email.verify_email", langCode, url, token,
                user, user.email, null);

        return new Body(text, html);
    }

    public void sendVerifyEmailMailingAfterSignup(final User user, final Context ctx) {

        final String subject = getVerifyEmailMailingSubjectAfterSignup(user, ctx);
        final String token = generateVerificationRecord(user);
        final Body body = getVerifyEmailMailingBodyAfterSignup(token, user, ctx);
        sendMail(subject, body, getEmailName(user));
    }

    private String getEmailName(final User user) {
        return getEmailName(user.email, user.name);
    }
}
