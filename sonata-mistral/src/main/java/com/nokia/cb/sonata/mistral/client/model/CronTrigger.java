/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Date;

@SuppressWarnings("unused")
public class CronTrigger {

    private String id;

    private String name;

    private String workflowId;

    private String workflowName;

    private String workflowParams;

    private String pattern;

    private String nextExecutionTime;

    private String remainingExecutions;

    private Date createdAt;

    private Date updatedAt;

    private String workflowInput;

    private String firstExecutionTime;

    private String scope;

    public CronTrigger() {
        // For JAXB
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("workflow_id")
    public String getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(String workflowId) {
        this.workflowId = workflowId;
    }

    @JsonProperty("workflow_name")
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @JsonProperty("workflow_params")
    public String getWorkflowParams() {
        return workflowParams;
    }

    public void setWorkflowParams(String workflowParams) {
        this.workflowParams = workflowParams;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @JsonProperty("next_execution_time")
    public String getNextExecutionTime() {
        return nextExecutionTime;
    }

    public void setNextExecutionTime(String nextExecutionTime) {
        this.nextExecutionTime = nextExecutionTime;
    }

    @JsonProperty("remaining_executions")
    public String getRemainingExecutions() {
        return remainingExecutions;
    }

    public void setRemainingExecutions(String remainingExecutions) {
        this.remainingExecutions = remainingExecutions;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("workflow_input")
    public String getWorkflowInput() {
        return workflowInput;
    }

    public void setWorkflowInput(String workflowInput) {
        this.workflowInput = workflowInput;
    }

    @JsonProperty("first_execution_time")
    public String getFirstExecutionTime() {
        return firstExecutionTime;
    }

    public void setFirstExecutionTime(String firstExecutionTime) {
        this.firstExecutionTime = firstExecutionTime;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("id", id)
                .add("name", name)
                .add("workflowId", workflowId)
                .add("workflowName", workflowName)
                .add("workflowParams", workflowParams)
                .add("pattern", pattern)
                .add("nextExecutionTime", nextExecutionTime)
                .add("remainingExecutions", remainingExecutions)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .add("workflowInput", workflowInput)
                .add("firstExecutionTime", firstExecutionTime)
                .add("scope", scope)
                .toString();
    }
}
