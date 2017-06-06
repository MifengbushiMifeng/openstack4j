/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client.model;

import java.util.List;


public class WorkbookListResponse {

    private List<Workbook> workbooks;

    public List<Workbook> getWorkbooks() {
        return workbooks;
    }

    public void setWorkbooks(List<Workbook> workbooks) {
        this.workbooks = workbooks;
    }

    @Override
    public String toString() {
        return "WorkbookListResponse{" +
                "workbooks=" + workbooks +
                '}';
    }
}

