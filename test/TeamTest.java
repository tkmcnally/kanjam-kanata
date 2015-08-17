

import com.feth.play.module.pa.PlayAuthenticate;
import com.feth.play.module.pa.user.AuthUser;
import com.google.inject.Inject;
import forms.RegistrationForm;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import play.Application;
import play.test.*;
import play.test.FakeApplication;
import providers.MyUsernamePasswordAuthUser;

import java.util.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

/**
 * Created by missionary on 15-08-17.
 */
public class TeamTest extends WithApplication {


    @Override
    protected FakeApplication provideFakeApplication() {

        final Map<String, String> additionalConfiguration = new HashMap<String, String>();
        additionalConfiguration.putAll(Helpers.inMemoryDatabase());

        additionalConfiguration.put("smtp.mock", "true");
        additionalConfiguration.put("logger.application", "WARN");


        final List<String> additionalPlugins = new ArrayList<String>();
        additionalPlugins.add(MyTestUserServicePlugin.class.getName());

        return Helpers.fakeApplication(
            additionalConfiguration,
            additionalPlugins,
            Collections.singletonList(service.MyUserServicePlugin.class.getName())
        );
    }

    @Test
    public void testUser() {

        running(this.app, () -> {

                /*String userFirstName = "Thomas";
                String userLastName = "McNally";
                String userEmail = "tk.mcnally@gmail.com";
                String userTicketId = "58a8016f7b33296cb0a103dbf73f2f5a1707e143";

                RegistrationForm registrationForm = new RegistrationForm();
                registrationForm.setFirstName(userFirstName);
                registrationForm.setLastName(userLastName);
                registrationForm.setTicketId(userTicketId);
                registrationForm.email = userEmail;

                MyUsernamePasswordAuthUser authUser =
                    new MyUsernamePasswordAuthUser(registrationForm);
                User newUser = User.create(authUser);

                User user = User.findByEmail(userEmail);
                assertThat(newUser.email).isEqualTo(user.email);*/

        });






    }

    public static class MyTestUserServicePlugin extends service.MyUserServicePlugin {

        private static AuthUser lastAuthUser;

        @Inject
        public MyTestUserServicePlugin(final Application app) {
            super(app);
        }

        @Override
        public void onStart() {
            PlayAuthenticate.setUserService(this);
        }

        @Override
        public Object save(final AuthUser authUser) {
            lastAuthUser = authUser;
            return super.save(authUser);
        }

        public static AuthUser getLastAuthUser() {
            return lastAuthUser;
        }

        public static void resetLasAuthUser() {
            lastAuthUser = null;
        }

    }

    @Before
    public void resetLastAuthUser() {
        MyTestUserServicePlugin.resetLasAuthUser();
    }

}
