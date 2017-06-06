/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class CronTriggerListResponse {

    List<CronTrigger> cronTriggers;

    @JsonProperty("cron_triggers")
    public List<CronTrigger> getCronTriggers() {
        return cronTriggers;
    }

    public void setCronTriggers(List<CronTrigger> cronTriggers) {
        this.cronTriggers = cronTriggers;
    }

    @Override
    public String toString() {
        return "CronTriggerListResponse{" +
                "cronTriggersList=" + cronTriggers +
                '}';
    }
}
