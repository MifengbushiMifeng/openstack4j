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

package com.nokia.cb.sonata.mistral.client.exception;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.ws.rs.client.ClientRequestContext;
import java.util.List;
import java.util.Map;

import static com.nokia.cb.sonata.mistral.client.request.filters.ServiceAuthFilter.AUTHORIZATION;
import static com.nokia.cb.sonata.mistral.client.request.filters.ServiceAuthFilter.REFRESH_TOKEN;


/**
 * Created by smendel on 2017.
 */
public class ServiceClientHttpException extends ServiceClientException {

    private final int httpStatus;
    private final ClientRequestContext clientRequestContext;
    private final ErrorResponse errorResponse;

    public ServiceClientHttpException(String message, int httpStatus) {
        this(message, httpStatus, null, null, null);
    }

    public ServiceClientHttpException(String message, int httpStatus, ClientRequestContext clientRequestContext, @Nullable ErrorResponse errorResponse, @Nullable Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
        this.clientRequestContext = clientRequestContext;
        this.errorResponse = errorResponse;
    }

    @Nullable
    public static String maskHeaderValueIfNeeded(String headerName, @Nullable String headerValue) {
        if ((headerName.equalsIgnoreCase(REFRESH_TOKEN) || headerName.equalsIgnoreCase(AUTHORIZATION)) && headerValue != null) {
            //Authorization header will not be printed to the log (NFVO-481).instead will be printed String length 9 with '***' in the middle.
            //In case the Authorization is too small will be printed only '***'
            headerValue = (headerValue.length() <= 9) ? "***" : StringUtils.abbreviateMiddle(headerValue, "***", 9);
        }
        return headerValue;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String toString() {
        return "ServiceClientHttpException{" +
                "httpStatus=" + httpStatus +
                ", clientRequestContext=" + toString(clientRequestContext) +
                ", errorResponse=" + errorResponse +
                "} " + super.toString();
    }

    private String toString(@Nullable ClientRequestContext clientRequestContext) {
        if (clientRequestContext == null) {
            return null;
        }

        StringBuilder str = new StringBuilder(clientRequestContext.getMethod() + " " + clientRequestContext.getUri());
        str.append("\nHeaders: [\n");

        for (Map.Entry<String, List<String>> entry : clientRequestContext.getStringHeaders().entrySet()) {
            String headerName = entry.getKey();
            str.append(headerName).append(": [");
            List<String> value = entry.getValue();
            for (int i = 0, valueSize = value.size(); i < valueSize; i++) {

                if (i > 0) {
                    str.append(", ");
                }

                String headerValue = value.get(i);
                str.append(maskHeaderValueIfNeeded(headerName, headerValue));
            }
            str.append("]\n");
        }

        str.append("]");

        return str.toString();
    }

}
