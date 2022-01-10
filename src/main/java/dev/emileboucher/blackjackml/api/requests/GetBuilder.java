package dev.emileboucher.blackjackml.api.requests;

/**
 * Builder for a classic GET request
 */
public abstract class GetBuilder extends RequestBuilder {
    /**
     * Set the type of request
     * @return the RequestBuilder updated
     */
    @Override
    public RequestBuilder addType() {
        builder.GET();
        return this;
    }
}
