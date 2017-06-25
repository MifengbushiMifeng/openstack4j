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

import com.nokia.cb.sonata.mistral.client.model.MistralErrorResponse;


@SuppressWarnings("unused")
public class MistralHttpException extends MistralException{

    private int httpStatus;
    private MistralErrorResponse mistralErrorResponse;

    public MistralHttpException(String message, int httpStatus) {
        this(message, httpStatus, null, null);
    }

    public MistralHttpException(int httpStatus, MistralErrorResponse mistralErrorResponse) {
        this(mistralErrorResponse.getFaultstring(), httpStatus, mistralErrorResponse, null);
    }

    public MistralHttpException(String message, int httpStatus, MistralErrorResponse mistralErrorResponse, Exception e) {
        super(message, e);
        this.httpStatus = httpStatus;
        this.mistralErrorResponse = mistralErrorResponse;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public MistralErrorResponse getMistralErrorResponse() {
        return mistralErrorResponse;
    }

    @Override
    public String toString() {
        return "MistralHttpException{" +
                "httpStatus=" + httpStatus +
                ", mistralErrorResponse=" + mistralErrorResponse +
                "} " + super.toString();
    }
}
