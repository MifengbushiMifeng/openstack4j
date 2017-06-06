/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 *
 *
 */

package com.nokia.cb.sonata.mistral.client.request.filters;

import com.nokia.cb.sonata.mistral.client.request.TokenProvider;
import com.sun.istack.internal.NotNull;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import java.io.IOException;

/**
 * Created by smendel on 2017.
 */
public class ServiceAuthFilter implements ClientRequestFilter{
    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER_AUTH_TOKEN_TYPE_PREFIX = "bearer ";
    public static final String REFRESH_TOKEN = "Refresh-Token";
    private String authorization;
    private TokenProvider tokenProvider;


    /**
     * @param authorization - authentication token to be sent for all requests
     */
    public ServiceAuthFilter(@NotNull String authorization) {
        this.authorization = BEARER_AUTH_TOKEN_TYPE_PREFIX + authorization;
    }

    /**
     * @param tokenProvider - A provider used to get a valid token before each request
     */
    public ServiceAuthFilter(@NotNull TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }


    /**
     * Adds authentication token header to the request
     */
    @Override
    public void filter(ClientRequestContext request) throws IOException {
        String authToken;
        String refreshToken = null;
        if (tokenProvider != null) {
            authToken = BEARER_AUTH_TOKEN_TYPE_PREFIX + tokenProvider.getValidToken();
            refreshToken = tokenProvider.getRefreshToken();
        } else {
            authToken = authorization;
        }

        if (!request.getHeaders().containsKey(AUTHORIZATION)) {
            request.getHeaders().add(AUTHORIZATION, authToken);
        }

        if (null != refreshToken && !request.getHeaders().containsKey(REFRESH_TOKEN)) {
            request.getHeaders().add(REFRESH_TOKEN, refreshToken);
        }
    }

}
