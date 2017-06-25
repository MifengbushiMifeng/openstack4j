/*
 * (c) 2017 Nokia Proprietary
 *
 * This software is licensed under the Apache 2 license, quoted below.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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
