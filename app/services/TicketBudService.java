package services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticketbud.api.singleticket.SingleTicketResponse;
import constants.Constants;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import play.Application;
import play.Play;
import play.api.Configuration;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import play.Logger;

/**
 * Created by missionary on 15-07-28.
 */
public class TicketBudService {

    private HttpWebService httpWebService;

    public TicketBudService() {
        httpWebService = new HttpWebService();
    }

    public SingleTicketResponse getTicketById(final String eventId, final String ticketId) {

        String access_token = Play.application().configuration().getString(Constants.TicketBudService.API_PREFIX + Constants.TicketBudService.ACCESS_TOKEN);
        String target = Play.application().configuration().getString(Constants.TicketBudService.API_PREFIX + Constants.WebService.TARGET);

        Map<String, String> params = new HashMap<String, String>();
        params.put(Constants.TicketBudService.ACCESS_TOKEN, access_token);
        params.put(Constants.TicketBudService.EVENT_ID, eventId);
        params.put(Constants.TicketBudService.TICKET_ID, ticketId);
        params.put(Constants.WebService.TARGET, target);

        HttpGet httpGet = null;
        try {
            httpGet = httpWebService.createRequest(params);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            Logger.debug("URI Syntax error.");
        }

        HttpResponse httpResponse = httpWebService.sendRequest(httpGet);

        return mapResponseToTicket(httpResponse);

    }


    public SingleTicketResponse mapResponseToTicket(final HttpResponse httpResponse) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "";
        SingleTicketResponse jsonTicket = new SingleTicketResponse();
        if(httpResponse.getEntity() != null) {
            try {
                jsonString = EntityUtils.toString(httpResponse.getEntity());
                jsonTicket = objectMapper.readValue(jsonString, SingleTicketResponse.class);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.debug("IOException: Invalid JSON returned from request.");
                Logger.debug("JSON: " + httpResponse.getEntity());
            }
        } else {
            Logger.debug("Response was null.");
            Logger.debug("HttpResponse.getEntity(): " + httpResponse.getEntity());
        }

        return jsonTicket;
    }
}
