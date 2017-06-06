/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.List;


public class WorkflowExecutionListResponse {

    @JsonProperty("executions")
    private List<WorkflowExecution> workflowExecutions;

    /**
     * @return {@link #workflowExecutions}
     */
    public List<WorkflowExecution> getWorkflowExecutions() {
        return workflowExecutions;
    }

    /**
     * @param workflowExecutions {@link #workflowExecutions}
     */
    public WorkflowExecutionListResponse setWorkflowExecutions(List<WorkflowExecution> workflowExecutions) {
        this.workflowExecutions = workflowExecutions;
        return this;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("workflowExecutions", workflowExecutions)
                .toString();
    }
}
