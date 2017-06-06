/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import java.util.List;


public class WorkflowListResponse {

    private List<Workflow> workflows;

    public List<Workflow> getWorkflows() {
        return workflows;
    }

    public void setWorkflows(List<Workflow> workflows) {
        this.workflows = workflows;
    }

    @Override
    public String toString() {
        return "WorkflowListResponse{" +
                "workflows=" + workflows +
                '}';
    }
}
