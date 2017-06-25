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
