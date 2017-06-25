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
