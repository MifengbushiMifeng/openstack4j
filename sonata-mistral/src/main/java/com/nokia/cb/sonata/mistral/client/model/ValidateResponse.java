/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;


public class ValidateResponse {
    private boolean valid;

    private String error;


    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


    @Override
    public String toString() {
        return "ValidateResponse{" +
                "valid=" + valid +
                ", error='" + error + '\'' +
                '}';
    }
}
