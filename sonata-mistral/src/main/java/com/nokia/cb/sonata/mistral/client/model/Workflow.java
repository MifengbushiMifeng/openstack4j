/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class Workflow {

    private String name;

    private Date createdAt;

    private Date updatedAt;

    private List tags;

    private String id;

    private String definition;

    private String scope;

    private String input;

    private String projectId;

    public Workflow() {
        // For JAXB
    }


    public List getTags() {
        return tags;
    }

    public void setTags(List tags) {
        this.tags = tags;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @JsonProperty("project_id")
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()
                .add("name", name)
                .add("createdAt", createdAt)
                .add("updatedAt", updatedAt)
                .add("tags", tags)
                .add("id", id)
                .add("definition", definition)
                .add("scope", scope)
                .add("input", input)
                .add("projectId", projectId)
                .toString();
    }
}
