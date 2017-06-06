/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
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
