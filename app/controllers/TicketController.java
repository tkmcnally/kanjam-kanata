package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import constants.Constants;
import forms.TicketValidationForm;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.h2.util.StringUtils;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.TicketBudService;
import com.ticketbud.api.singleticket.SingleTicketResponse;

import javax.validation.Valid;

import java.io.IOException;
import java.net.URISyntaxException;

import static play.mvc.Results.ok;

/**
 * Created by missionary on 15-07-28.
 */
public class TicketController extends Controller {

    public static Result validateTicket() {

        Form<TicketValidationForm> validationFormWrapper = Form.form(TicketValidationForm.class).bindFromRequest();

        TicketValidationForm validationForm = validationFormWrapper.get();

        TicketBudService ticketBudService = new TicketBudService();

        boolean validated = false;

        String eventId = Play.application().configuration().getString(Constants.TicketBudService.API_PREFIX + Constants.TicketBudService.EVENT_ID);
        SingleTicketResponse jsonTicket = ticketBudService.getTicketById(eventId, validationForm.getTicketId());

        if(jsonTicket != null && jsonTicket.getTicket()!= null) {
            if(StringUtils.equals(jsonTicket.getTicket().getEmail(), validationForm.getEmail())) {
                validated = true;
            }
        }

        return ok(String.valueOf(validated));
    }

    public static Result authenticateTicketBud(final String code) {

        HttpClient httpClient = HttpClientBuilder.create().build();

        URIBuilder uriBuilder = null;
        try {
            uriBuilder = new URIBuilder("https://api.ticketbud.com/oauth/token");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        uriBuilder.setParameter("client_id", "6112d0bcb8a4e4f07a6c1222ee98ba14e2132f2636b712e20a86920bd35ba5a6");
        uriBuilder.setParameter("client_secret", "4987d95b2ac5d29bbb0c49ba01f258c54326377f6d0469c433028b82ba867c78");
        uriBuilder.setParameter("code", code);
        uriBuilder.setParameter("grant_type", "authorization_code");
        uriBuilder.setParameter("redirect_uri", "http://kanjam-kanata.herokuapp.com/api/ticketbud/callback");

        HttpPost httpPost = null;
        try {
            httpPost = new HttpPost(uriBuilder.build());
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        httpPost.setHeader("Content-Type", "application/json");

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpPost);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String json = "default";
        try {
            json = EntityUtils.toString(httpResponse.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ok(json);
    }

    public static Result ticketBudCallBack(final String code) {
        JsonNode json = request().body().asJson();
        return ok(json + " @ " + code);
    }
}
