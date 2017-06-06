/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@SuppressWarnings("unused")
public class Workbook {

    private String name;

    private Date createdAt;

    private Date updatedAt;

    private List tags;

    private String id;

    private String definition;

    private String scope;

    private String input;

    public Workbook() {
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

    @Override
    public String toString() {
        return "Workflow{" +
                "name='" + name + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                ", tags=" + tags +
                ", id='" + id + '\'' +
                ", definition='" + definition + '\'' +
                ", scope='" + scope + '\'' +
                ", input='" + input + '\'' +
                '}';
    }
}
