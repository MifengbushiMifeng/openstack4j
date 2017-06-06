/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;


@SuppressWarnings("unused")
public class MistralErrorResponse extends Throwable {

    private String debuginfo;

    private String faultcode;

    private String faultstring;

    public MistralErrorResponse() {
        // For JAXB
    }

    public String getDebuginfo() {
        return debuginfo;
    }

    public void setDebuginfo(String debuginfo) {
        this.debuginfo = debuginfo;
    }

    public String getFaultcode() {
        return faultcode;
    }

    public void setFaultcode(String faultcode) {
        this.faultcode = faultcode;
    }

    public String getFaultstring() {
        return faultstring;
    }

    public void setFaultstring(String faultstring) {
        this.faultstring = faultstring;
    }

    @Override
    public String toString() {
        return "MistralErrorResponse{" +
                "debuginfo='" + debuginfo + '\'' +
                ", faultcode='" + faultcode + '\'' +
                ", faultstring='" + faultstring + '\'' +
                "} " + super.toString();
    }
}


