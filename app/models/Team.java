package models;

import com.avaje.ebean.Expr;
import com.avaje.ebean.ExpressionList;

import javax.persistence.*;

/**
 * Created by missionary on 15-08-14.
 */
@Entity
@Table(name="teams")
public class Team extends AppModel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    public Long id;

    @ManyToOne
    public User owner;

    @ManyToOne
    public User playerOne;

    @ManyToOne
    public User playerTwo;

    public String name;

    //Finder for Team models.
    public static final AppModel.Finder<Long, Team> find = new AppModel.Finder<Long, Team>(
        Long.class, Team.class);

    public static Team getById(final String id) {
        return getByIdEL(id).findUnique();
    }

    public static ExpressionList<Team> getByIdEL(final String id) {
        return find.where().eq("id", id);
    }

    public static Team getByPlayer(final User player) {
        try {
            return getByPlayerEL(player).findUnique();
        } catch (NullPointerException ex) {
            return null;
        }
    }

    public static ExpressionList<Team> getByPlayerEL(final User player) {
        return find.where().or(Expr.eq("playerOne.id", player.id),
            Expr.eq("playerTwo.id", player.id));
    }

    public static Team create(final User owner, final String name) {
        final Team team = new Team();
        team.owner = owner;
        team.playerOne = owner;
        team.name = name;

        team.save();

        return team;
    }

    public static Team addPlayer(final Team team, final User playerTwo) {
        if(playerTwo == null) {
            team.playerTwo = playerTwo;
            team.save();
        }

        return team;
    }

    public static TeamAction removePlayer(final Team team, final User user) {
        TeamAction teamAction = new TeamAction();
        teamAction.setStatus(TeamAction.Status.NOT_VALID);
        if(Long.compare(team.id, user.id) == 0) {
            if(team.playerTwo != null) {
                team.owner = team.playerTwo;
                team.playerOne = team.playerTwo;
                team.playerTwo = null;
                teamAction.setStatus(TeamAction.Status.SUCCESS);
                team.save();
            } else {
                team.delete();
            }
        } else if(team.playerTwo != null && Long.compare(team.playerTwo.id, user.id) == 0) {
            team.playerTwo = null;
            teamAction.setStatus(TeamAction.Status.SUCCESS);
            team.save();
        }

        return teamAction;
    }
}
