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
import java.util.logging.Logger;

/**
 * Created by missionary on 15-07-28.
 */
public class TicketBudService {

    private final static Logger LOG = Logger.getLogger(TicketBudService.class.getName());

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
            LOG.fine("URI Syntax error.");
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
                LOG.fine("IOException: Invalid JSON returned from request.");
                LOG.finest("JSON: " + httpResponse.getEntity());
            }
        } else {
            LOG.fine("Response was null.");
            LOG.finest("HttpResponse.getEntity(): " + httpResponse.getEntity());
        }

        return jsonTicket;
    }
}
