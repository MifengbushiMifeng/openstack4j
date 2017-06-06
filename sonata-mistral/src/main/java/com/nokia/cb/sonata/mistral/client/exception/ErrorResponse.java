/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 *
 *
 */

package com.nokia.cb.sonata.mistral.client.exception;

import java.io.Serializable;

/**
 * Created by smendel on 2017.
 */
public class ErrorResponse implements Serializable{


    private static final long serialVersionUID =  7039283120377484897L;

    private String externalId;

    private Error error;

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return String.valueOf(error);
    }



}
