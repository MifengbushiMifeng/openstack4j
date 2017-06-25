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
