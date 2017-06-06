/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import java.util.List;


public class ActionListResponse {

    private List<Action> actions;

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    @Override
    public String toString() {
        return "ActionListResponse{" +
                "actions=" + actions +
                '}';
    }
}
