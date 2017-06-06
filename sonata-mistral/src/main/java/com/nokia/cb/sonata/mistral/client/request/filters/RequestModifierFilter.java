/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */


package com.nokia.cb.sonata.mistral.client.request.filters;

import com.nokia.cb.sonata.mistral.client.request.RequestModifier;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by smendel on 2017.
 */
public class RequestModifierFilter implements ClientRequestFilter{

    public void filter(ClientRequestContext requestContext) throws IOException {
        MultivaluedMap<String, Object> headers = RequestModifier.getHeaders();
        for (Map.Entry<String, List<Object>> entry : headers.entrySet()) {
            String headerName = entry.getKey();
            for (Object value : entry.getValue()) {
                requestContext.getHeaders().add(headerName, value);
            }
        }
    }
}
