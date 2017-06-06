/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.cb.sonata.mistral.client.exception.MistralException;

import java.util.Map;

@SuppressWarnings("unused")
public class WorkflowExecutionCreateRequest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String workflowName;

    private String input;

    private String description;

    private String params;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParams() {
        return params;
    }

    public void setParams(Map<String, ?> params) {
        // Mistral expects for a String
        try {
            this.params = OBJECT_MAPPER.writeValueAsString(params);
        } catch (JsonProcessingException e) {
            throw new MistralException("Cannot convert params to JSON: [" + params + "]", e);
        }
    }

    public void setParams(String params) {
        this.params = params;
    }

    @JsonProperty("workflow_name")
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setInputs(Map<String, ?> inputs) {
        // Mistral expects for a String
        try {
            this.input = OBJECT_MAPPER.writeValueAsString(inputs);
        } catch (JsonProcessingException e) {
            throw new MistralException("Cannot convert input to JSON: [" + inputs + "]", e);
        }
    }

    @Override
    public String toString() {
        return "WorkflowExecutionCreateRequest{" +
                "workflowName='" + workflowName + '\'' +
                ", input='" + input + '\'' +
                ", description='" + description + '\'' +
                ", params='" + params + '\'' +
                '}';
    }

}
