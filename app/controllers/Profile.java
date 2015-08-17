package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import forms.TeamActionForm;
import models.Team;
import models.TeamAction;
import models.TokenAction;
import models.User;
import play.data.Form;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import providers.MyUsernamePasswordAuthProvider;
import views.html.account.profile;
import views.html.joinTeam;

import static play.data.Form.form;

/**
 * Created by missionary on 15-08-14.
 */
public class Profile extends Controller {

    private static final Form<TeamActionForm> TEAM_ACTION_FORM = form(TeamActionForm.class);


    // Get the current user.
    public static User getLocalUser(final Http.Session session) {
        final AuthUser currentAuthUser = PlayAuthenticate.getUser(session);
        final User localUser = User.findByAuthUserIdentity(currentAuthUser);
        return localUser;
    }

    // GET - Profile page.
    public static Result profile() {
        Result result = ok();
        final User user = getLocalUser(session());
        final Team team = Team.getByPlayer(user);
        if(getLocalUser(session()) != null) {
            result = ok(profile.render(TEAM_ACTION_FORM, user, team));
        } else {
            result = redirect(routes.Application.index());
        }

        return result;
    }

    public static Result removePlayer() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<TeamActionForm> filledForm = TEAM_ACTION_FORM
            .bindFromRequest();

        final User player = User.findByEmail(filledForm.get().getPlayerEmail());
        final User user = getLocalUser(session());
        final Team team = Team.getByPlayer(user);

        TeamAction teamAction = new TeamAction();
        if(team != null) {

            teamAction = Team.removePlayer(team, player);

            if(TeamAction.Status.SUCCESS.equals(teamAction.getStatus())) {
                flash(Application.FLASH_MESSAGE_KEY, Messages.get("kanjam.profile.removeplayer.success"));
            } else if(TeamAction.Status.NOT_VALID_USER.equals(teamAction.getStatus())){
                flash(Application.FLASH_ERROR_KEY, Messages.get(teamAction.getErrorMessage(), filledForm.get().getPlayerEmail()));
            } else {
                flash(Application.FLASH_ERROR_KEY, Messages.get(teamAction.getErrorMessage(),filledForm.get().getTeamId()));
            }
        }

        return redirect(routes.Profile.profile());
    }

    public static Result invitePlayer() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<TeamActionForm> filledForm = TEAM_ACTION_FORM
            .bindFromRequest();

        final User user = getLocalUser(session());
        final MyUsernamePasswordAuthProvider provider =
            MyUsernamePasswordAuthProvider.getProvider();

        final Team team = Team.getByPlayer(user);
        if(team != null) {
            provider.sendTeamInvitationEmail(team, user, filledForm.get().getEmail(), ctx());
        } else {
            flash(Application.FLASH_ERROR_KEY, "You are not on a team!");
            redirect(routes.Profile.profile());
        }

        flash(Application.FLASH_MESSAGE_KEY, "An invitation email has been sent to '" + filledForm.get().getEmail() + "'.");
        return redirect(routes.Profile.profile());
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result joinTeam(final String teamId, final String token) {
        final User user = getLocalUser(session());
        Team oldTeam = Team.getByPlayer(user);
        Team newTeam = Team.getById(teamId);
        if(newTeam == null) {
            flash(Application.FLASH_ERROR_KEY, "Team [ID:" + teamId + "] does not exist!");
            return redirect(routes.Profile.profile());
        }

        if(oldTeam != null && Long.compare(oldTeam.id, newTeam.id) == 0) {
            flash(Application.FLASH_ERROR_KEY, "You are already on this team!");
            return redirect(routes.Profile.profile());
        }
        TeamActionForm teamActionForm = new TeamActionForm();
        teamActionForm.setToken(token);


        return ok(joinTeam.render(TEAM_ACTION_FORM.fill(teamActionForm), user, oldTeam, newTeam));
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result doJoinTeam() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<TeamActionForm> filledForm = TEAM_ACTION_FORM
            .bindFromRequest();


        final User user = getLocalUser(session());

        final TokenAction ta = tokenIsValid(filledForm.get().getToken(),
            TokenAction.Type.TEAM_INVITE, user.email);

        if(ta == null) {
            flash(Application.FLASH_ERROR_KEY, "Invite has expired or is invalid.");
            return redirect(routes.Profile.profile());
        }
        final Team targetTeam = Team.getById(filledForm.get().getTeamId());
        Team.addPlayer(targetTeam, user);
        TokenAction.deleteByToken(filledForm.get().getToken());

        return redirect(routes.Profile.profile());
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result createTeam() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<TeamActionForm> filledForm = TEAM_ACTION_FORM
            .bindFromRequest();

        final User user = getLocalUser(session());
        Team team = Team.getByPlayer(user);

        if(team != null) {
            flash(Application.FLASH_ERROR_KEY, "You are already on a team!");
        } else {
            team = Team.create(user, filledForm.get().getTeamName());
        }
        flash(Application.FLASH_MESSAGE_KEY, "Team '" + team.name + "' has been created.");
        return redirect(routes.Profile.profile());
    }

    /**
     * Returns a token object if valid, null if not
     *
     * @param token
     * @param type
     * @return
     */
    private static TokenAction tokenIsValid(final String token, final TokenAction.Type type, final String targetEmail) {
        TokenAction ret = null;
        if (token != null && !token.trim().isEmpty()) {
            final TokenAction ta = TokenAction.findByToken(token, type);
            if (ta != null && ta.isValid() && ta.targetEmail == targetEmail) {
                ret = ta;
            }
        }

        return ret;
    }
}
