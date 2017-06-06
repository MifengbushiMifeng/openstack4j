/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.exception;

/**
 * Created by smendel on 2017.
 */
public class ServiceClientException extends RuntimeException {

    public ServiceClientException(String message, Exception e) {
        super(message, e);
    }
}
