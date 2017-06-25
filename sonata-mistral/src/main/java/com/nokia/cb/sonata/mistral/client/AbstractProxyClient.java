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

package com.nokia.cb.sonata.mistral.client;

import com.nokia.cb.sonata.mistral.client.request.filters.RequestModifierFilter;
import com.nokia.cb.sonata.mistral.client.request.filters.TraceabilityFilter;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.jboss.resteasy.client.jaxrs.engines.ApacheHttpClient4Engine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.Response;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

/**
 * Created by smendel on 2017.
 */
public abstract class AbstractProxyClient <E> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractProxyClient.class);

    private Class<E> errorResponseClass;

    public AbstractProxyClient(Class<E> errorResponseClass) {
        this.errorResponseClass = errorResponseClass;
    }

    protected <T> T createProxy(Class<T> proxyClass, String baseUri, String authToken) {

        // Use PoolingHttpClientConnectionManager in order to allow reuse of the same client
        // for multiple requests even concurrently from multiple threads
        ResteasyWebTarget target = getRestEasyWebTarget( baseUri, authToken );

        return target.proxy(proxyClass);
    }

    protected ResteasyWebTarget getRestEasyWebTarget( String baseUri, String authToken )
    {
        HttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create().setConnectionManager(cm);
        HttpClient httpClient = httpClientBuilder.build();

        // Create a RESTEasy engine to use Apache HttpClient
        ApacheHttpClient4Engine engine = new ApacheHttpClient4Engine(httpClient);

        ResteasyClient client = createClient(authToken, engine);
        return client.target(baseUri);
    }

    protected ResteasyClient createClient(String authToken, ClientHttpEngine engine) {
        ResteasyClient client = new ResteasyClientBuilder().httpEngine(engine).build();
        client.register(new TraceabilityFilter(AbstractProxyClient.class.getSimpleName() + "/" + getClass().getSimpleName()));
        client.register(new RequestModifierFilter());
        return client;
    }

    protected void validateResponse(Response response) {

        // Get the request object (stored on thread local in TraceabilityFilter)
        ClientRequestContext clientRequestContext = TraceabilityFilter.getClientRequestContext();

        // If this is an error response
        if (isHttpErrorResponse(response.getStatus())) {
            if (response.hasEntity()) {
                // Buffer entity so the response can be read as String if reading as EntityResponse will fail
                response.bufferEntity();

                Object responseContentType = response.getHeaders().getFirst("Content-Type");
                // If content type is JSON - try to read response into errorResponseClass
                if ("application/json".equals(responseContentType)) {
                    // Read the response body from the response
                    E errorResponse = translateJsonResponseToErrorResponse(clientRequestContext, response);
                    throw newHttpException(String.valueOf(errorResponse), response.getStatus(), clientRequestContext, errorResponse, null);

                } else { // Response is not JSON - read it as string
                    String responseBody = null;
                    try {
                        responseBody = response.readEntity(String.class);
                    } catch (Exception e1) {
                        LOGGER.warn("Cannot read error response as String: {}", response, e1);
                    }
                    throw newHttpException("Received '" + responseContentType + "' error response from server [" + responseBody + "]", response.getStatus(), clientRequestContext, null, null);
                }

            } else {
                throw newHttpException(response.getStatusInfo().getReasonPhrase(), response.getStatus(), clientRequestContext, null, null);
            }
        }

        if (response.getStatus() == HttpURLConnection.HTTP_MOVED_TEMP) {
            String responseBody = null;
            try {
                if (response.hasEntity()) {
                    responseBody = response.readEntity(String.class);
                }
            } catch (Exception e) {
                LOGGER.warn("Failed to read response body for response " + response.getStatusInfo(), e);
            }

            throw newHttpException("HTTP response was Moved Temporarily, probably authentication issue. response body: [" + responseBody + "]", response.getStatus(), clientRequestContext, null, null);
        }
    }

    protected E translateJsonResponseToErrorResponse(ClientRequestContext clientRequestContext, Response response) {
        try {
            return response.readEntity(errorResponseClass);
        } catch (Exception e) {
            String responseBody = null;
            try {
                responseBody = response.readEntity(String.class);
            } catch (Exception e1) {
                LOGGER.warn("Cannot read error response as ErrorResponse and not as String: {}", response, e1);
            }
            throw newHttpException("Failed to readEntity for error response [" + responseBody + "]", response.getStatus(), clientRequestContext, null, e);
        }
    }

    protected boolean validateResponseForDelete(Response response) {
        if (response.getStatus() == HttpURLConnection.HTTP_NOT_FOUND) {
            return false;
        }
        validateResponse(response);
        return true;
    }

    protected <T> T readEntity(final Response response, final Class<T> clazz) {
        LOGGER.debug("Read entity for {} from response {}", clazz, response);
        return readEntity(response, new Callable<T>() {
            @Override
            public T call() throws Exception {
                return response.readEntity(clazz);
            }

            @Override
            public String toString() {
                return String.valueOf(clazz);
            }
        });
    }

    protected <T> T readEntity(final Response response, final GenericType<T> genericType)
    {
        LOGGER.debug("Read entity for {} from response {}", genericType, response);
        return readEntity(response, new Callable<T>() {
            @Override
            public T call() throws Exception {
                return response.readEntity(genericType);
            }

            @Override
            public String toString() {
                return String.valueOf(genericType);
            }
        });
    }

    private <T> T readEntity(Response response, Callable<T> readEntityCallable) {
        // Validate that the response is not an error response
        validateResponse(response);

        // If there is no payload on the response
        if (!response.hasEntity()) {
            return null;
        }

        try {
            // Buffering entity so it can be read again as string if reading into clazz fails
            response.bufferEntity();
            return readEntityCallable.call();
        } catch (Exception e) {

            String responseBody;
            try {
                responseBody = response.readEntity(String.class);
            } catch (Exception e1) {
                throw newException("Exception reading response body from " + response, e1);
            }
            throw newException("Exception reading response " + response.getStatus() + " with body [" + responseBody + "] into " + readEntityCallable, e);
        }
    }

    protected boolean isHttpErrorResponse(int httpResponseCode) {
        return (httpResponseCode >= HttpURLConnection.HTTP_BAD_REQUEST);
    }

    protected void close(@Nullable Response response) {
        if (response != null) {
            response.close();
        }
    }

    protected abstract RuntimeException newException(String message, Exception e);

    protected abstract RuntimeException newHttpException(String message, int httpStatus, ClientRequestContext clientRequestContext, @Nullable E errorResponse, @Nullable Exception e);

}
