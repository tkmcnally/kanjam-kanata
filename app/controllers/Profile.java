package controllers;

import be.objectify.deadbolt.java.actions.Group;
import be.objectify.deadbolt.java.actions.Restrict;
import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import forms.TeamActionForm;
import models.Team;
import models.TeamAction;
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

        final User player = User.findById(filledForm.get().getPlayerId());
        final User user = getLocalUser(session());

        final Team team = Team.getById(filledForm.get().getTeamId());

        TeamAction teamAction = new TeamAction();
        boolean removed = false;
        if(team != null) {
            //Validate current user is owner of the team.
            if(Long.compare(team.owner.id, user.id) != 0) {
                //Validate player is on the team.
                teamAction = Team.removePlayer(team, player);
            } else {
                teamAction.setStatus(TeamAction.Status.SUCCESS);
            }

            if(TeamAction.Status.SUCCESS.equals(teamAction.getStatus())) {
                flash(Application.FLASH_MESSAGE_KEY, Messages.get("kanjam.profile.removeplayer.success"));
            } else {
                flash(Application.FLASH_ERROR_KEY, Messages.get(teamAction.getErrorMessage()));
            }
        }

        return redirect(routes.Profile.profile());
    }

    public static Result invitePlayer(final String email) {
        return ok();
    }

    @Restrict(@Group(Application.USER_ROLE))
    public static Result joinTeam(final String team) {
        final User user = getLocalUser(session());
        Team oldTeam = Team.getByPlayer(user);
        Team newTeam = Team.getById(team);

        return ok(joinTeam.render(TEAM_ACTION_FORM, user, oldTeam, newTeam));
    }

    public static Result doJoinTeam() {
        com.feth.play.module.pa.controllers.Authenticate.noCache(response());
        final Form<TeamActionForm> filledForm = TEAM_ACTION_FORM
            .bindFromRequest();

        return ok();
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
}
