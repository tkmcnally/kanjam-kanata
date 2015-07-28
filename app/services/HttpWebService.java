package services;


import constants.Constants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by missionary on 15-07-27.
 */
public class HttpWebService {

    private final static Logger LOG = Logger.getLogger(HttpWebService.class.getName());

    /**
     * Creates an HTTP GET request to the specified target along with the parameters passed.
     *
     * @param params - Parameters to be sent in the HTTP Get request.
     * @return new HTTPGet() - The HTTP Get request to be sent.
     * @throws URISyntaxException
     */
    public HttpGet createRequest(Map<String, String> params) throws URISyntaxException {
        String target  = params.get(Constants.WebService.TARGET) != null ? params.get(Constants.WebService.TARGET) : "";
        target = MessageFormat.format(target, params.get(Constants.TicketBudService.EVENT_ID), params.get(Constants.TicketBudService.TICKET_ID));
        URIBuilder uriBuilder = new URIBuilder(target);
        uriBuilder.setParameter(Constants.TicketBudService.ACCESS_TOKEN, params.get(Constants.TicketBudService.ACCESS_TOKEN));

        return new HttpGet(uriBuilder.build());
    }

    /**
     * Send HTTP GET request and return the HTTP response.
     *
     * @param httpRequest - HTTP GET request payload.
     * @return httpResponse - The response from the HTTP GET request.
     */
    public HttpResponse sendRequest(final HttpGet httpRequest) {


        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
            play.Logger.debug("IOException during HTTP GET Request. Details in finest level.");
            play.Logger.debug("HttpRequest: " + httpRequest.toString());
        }

        return httpResponse;
    }


}
