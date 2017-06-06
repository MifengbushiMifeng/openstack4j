/*
 * (c) today.year Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ActionExecutionListResponse {
    @JsonProperty("action_executions")
    private List<ActionExecution> actionExecutions;

    public List<ActionExecution> getActionExecutions() {
        return actionExecutions;
    }

    public void setActionExecutions(List<ActionExecution> actionExecutions) {
        this.actionExecutions = actionExecutions;
    }

    @Override
    public String toString() {
        return "ActionExecutionListResponse{" +
                "actionExecutions=" + actionExecutions +
                '}';
    }
}
