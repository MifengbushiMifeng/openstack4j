/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */


package com.nokia.cb.sonata.mistral.client.request.filters;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.util.UUID;

/**
 * Created by smendel on 2017.
 */
public class TraceabilityFilter implements ClientRequestFilter {

    public static final String X_CLIENT_REQUEST_ID = "x-client-request-id";
    public static final String USER_AGENT = "user-agent";
    private static ThreadLocal<ClientRequestContext> clientRequestContextThreadLocal = new ThreadLocal<>();
    private String userAgent;

    public TraceabilityFilter(String userAgent) {
        this.userAgent = userAgent;
    }

    public static ClientRequestContext getClientRequestContext() {
        return clientRequestContextThreadLocal.get();
    }

    public void filter(ClientRequestContext clientRequestContext) {

        clientRequestContextThreadLocal.set(clientRequestContext);

        MultivaluedMap<String, Object> headers = clientRequestContext.getHeaders();
        headers.add(USER_AGENT, userAgent);
        headers.add(X_CLIENT_REQUEST_ID, UUID.randomUUID().toString());


    }

}
