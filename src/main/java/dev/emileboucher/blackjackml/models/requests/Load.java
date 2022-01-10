package dev.emileboucher.blackjackml.models.requests;

import dev.emileboucher.blackjackml.api.requests.GetBuilder;

import java.net.URI;

/**
 * A GET request on the /load
 */
public class Load extends GetBuilder {
    /**
     * Add the ip of the server to the request
     * @param ip of the server
     * @return the RequestBuilder updated
     */
    @Override
    public Load addIp(String ip) {
        builder.uri(URI.create(ip + "load"));
        super.baseURL = URI.create(ip);
        return this;
    }
}
