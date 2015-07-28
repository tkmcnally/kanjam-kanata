package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import constants.Constants;
import forms.TicketValidationForm;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.h2.util.StringUtils;
import play.Play;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import services.TicketBudService;
import com.ticketbud.api.singleticket.SingleTicketResponse;

import javax.validation.Valid;

import java.io.IOException;

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

    public static Result authenticateTicketBud() {
        HttpGet httpGet = new HttpGet("https://api.ticketbud.com/oauth/authorize?response_type=code&client_id=&redirect_url=your_callback_url");
        HttpClient httpClient = HttpClientBuilder.create().build();

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpGet);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ok(httpResponse.getEntity().toString());
    }

    public static Result ticketBudCallBack() {
        JsonNode json = request().body().asJson();
        return ok(json);
    }
}
