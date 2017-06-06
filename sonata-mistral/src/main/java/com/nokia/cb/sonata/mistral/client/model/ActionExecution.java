/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

@SuppressWarnings("unused")
public class ActionExecution {
    private String name;

    private String stateInfo;

    private Date createdAt;

    private String workflowName;

    private String taskExecutionId;

    private String taskName;

    private Date updatedAt;

    private String tags;

    private String state;

    private String output;

    private String input;

    private String accepted;

    private String id;

    private String description;

    private String params;

    public ActionExecution() {
        // For JAXB
    }

    @JsonProperty("state_info")
    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @JsonProperty("workflow_name")
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @JsonProperty("task_execution_id")
    public String getTaskExecutionId() {
        return taskExecutionId;
    }

    public void setTaskExecutionId(String taskExecutionId) {
        this.taskExecutionId = taskExecutionId;
    }

    @JsonProperty("task_name")
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @JsonProperty("updated_at")
    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getAccepted() {
        return accepted;
    }

    public void setAccepted(String accepted) {
        this.accepted = accepted;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    @Override
    public String toString() {
        return "ActionExecution{" +
                "name='" + name + '\'' +
                ", stateInfo='" + stateInfo + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", workflowName='" + workflowName + '\'' +
                ", taskExecutionId='" + taskExecutionId + '\'' +
                ", taskName='" + taskName + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", tags='" + tags + '\'' +
                ", state='" + state + '\'' +
                ", output='" + output + '\'' +
                ", accepted='" + accepted + '\'' +
                ", id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                '}';
    }
}


