import java.util.Arrays;

import controllers.routes;
import models.SecurityRole;

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.PlayAuthenticate.Resolver;
import com.feth.play.module.pa.exceptions.AccessDeniedException;
import com.feth.play.module.pa.exceptions.AuthException;

import models.Team;
import play.*;
import play.mvc.*;
import play.mvc.Http.*;
import play.libs.F.*;
import views.html.pageNotFound;

import static play.mvc.Results.*;
public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {
        PlayAuthenticate.setResolver(new Resolver() {

            @Override
            public Call login() {
                // Your login page
                return routes.Application.login();
            }

            @Override
            public Call afterAuth() {
                // The user will be redirected to this page after authentication
                // if no original URL was saved
                return routes.Profile.profile();
            }

            @Override
            public Call afterLogout() {
                return routes.Application.index();
            }

            @Override
            public Call auth(final String provider) {
                // You can provide your own authentication implementation,
                // however the default should be sufficient for most cases
                return com.feth.play.module.pa.controllers.routes.Authenticate
                    .authenticate(provider);
            }

            @Override
            public Call askMerge() {
                return routes.Application.index();
            }

            @Override
            public Call askLink() {
                return routes.Application.index();
            }

            @Override
            public Call onException(final AuthException e) {

                // more custom problem handling here...
                return super.onException(e);
            }
        });

        initialData();
    }
    public Promise<Result> onHandlerNotFound(RequestHeader request) {
        return Promise.<Result>pure(notFound(
            pageNotFound.render()
        ));
    }

    private void initialData() {
        if (SecurityRole.find.findRowCount() == 0) {
            for (final String roleName : Arrays
                .asList(controllers.Application.USER_ROLE)) {
                final SecurityRole role = new SecurityRole();
                role.roleName = roleName;
                role.save();
            }
        }
    }
}
