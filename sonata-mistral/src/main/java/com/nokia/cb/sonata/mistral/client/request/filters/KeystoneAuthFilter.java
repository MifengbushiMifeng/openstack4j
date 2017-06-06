/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */
package com.nokia.cb.sonata.mistral.client.request.filters;

import javax.annotation.Nullable;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;


/**
 * Adds auth token header to the request
 *
 * @author ponr
 */
public class KeystoneAuthFilter implements ClientRequestFilter {
    public static final String X_AUTH_TOKEN = "X-Auth-Token";

    private String authToken;


    /**
     * @param authToken - authentication token to be sent for all requests
     */
    public KeystoneAuthFilter(@Nullable String authToken) {
        this.authToken = authToken;
    }


    /**
     * Adds authentication token header to the request
     */
    @Override
    public void filter(ClientRequestContext request) throws IOException {
        if (authToken != null) {
            request.getHeaders().add(X_AUTH_TOKEN, authToken);
        }
    }
}
