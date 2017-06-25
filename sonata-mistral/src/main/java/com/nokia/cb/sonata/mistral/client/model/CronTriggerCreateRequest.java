/*
 * (c) 2017 Nokia Proprietary
 *
 * This software is licensed under the Apache 2 license, quoted below.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nokia.cb.sonata.mistral.client.exception.MistralException;

import java.util.Map;

@SuppressWarnings("unused")
public class CronTriggerCreateRequest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    // TODO(gpaz) add first-time, params, count.

    private String name;

    private String pattern;

    private String workflowName;

    private String workflowInput;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    @JsonProperty("workflow_name")
    public String getWorkflowName() {
        return workflowName;
    }

    public void setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
    }

    @JsonProperty("workflow_input")
    public String getWorkflowInput() {
        return workflowInput;
    }

    public void setWorkflowInput(String workflowInput) {
        this.workflowInput = workflowInput;
    }

    public void setInputs(Map<String, ?> inputs) {
        // Mistral expects for a String
        try {
            this.workflowInput = OBJECT_MAPPER.writeValueAsString(inputs);
        } catch (JsonProcessingException e) {
            throw new MistralException("Cannot convert input to JSON: [" + inputs + "]", e);
        }
    }

    @Override
    public String toString() {
        return "CronTriggerCreateRequest{" +
                "name='" + name + '\'' +
                ", pattern='" + pattern + '\'' +
                ", workflowName='" + workflowName + '\'' +
                ", inputs='" + workflowInput + '\'' +
                '}';
    }
}
