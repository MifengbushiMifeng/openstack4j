/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.exception;


public class MistralException extends RuntimeException{

    public MistralException(String message) {
        super(message);
    }

    public MistralException(String message, Exception e) {
        super(message, e);
    }
}
