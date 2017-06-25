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
import org.jetbrains.annotations.Contract;

@SuppressWarnings("unused")
public class ActionExecutionUpdateRequest {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private String state;
    @JsonProperty("output")
    private String outputAsJson;

    public ActionExecutionUpdateRequest(String state, String outputAsJson) {
        this.state = state;
        this.outputAsJson = outputAsJson;
    }

    public ActionExecutionUpdateRequest(String state, Object outputObject) {
        this(state, convertObjectToJson(outputObject));
    }

    @Contract("null -> null")
    private static String convertObjectToJson(Object outputObject) {
        if (outputObject == null) {
            return null;
        }

        // Mistral expects output to be a JSON String
        try {
            return OBJECT_MAPPER.writeValueAsString(outputObject);
        } catch (JsonProcessingException e) {
            throw new MistralException("Cannot convert output object to JSON: [" + outputObject + "]", e);
        }
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getOutputAsJson() {
        return outputAsJson;
    }

    public void setOutputAsJson(String outputAsJson) {
        this.outputAsJson = outputAsJson;
    }

    @Override
    public String toString() {
        return "ActionExecutionUpdateRequest{" +
                "state='" + state + '\'' +
                ", outputAsJson='" + outputAsJson + '\'' +
                '}';
    }
}