/*
 * (c) 2016 Nokia Proprietary - Nokia Internal Use
 */

package com.nokia.cb.sonata.mistral.client;

import com.nokia.cb.sonata.mistral.client.model.ActionExecutionUpdateRequest;
import com.nokia.cb.sonata.mistral.client.model.CronTriggerCreateRequest;
import com.nokia.cb.sonata.mistral.client.model.WorkflowExecutionCreateRequest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public interface MistralApi {

    @Path("/workflows")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    Response workflowCreate(String wf);

    @Path("/workflows")
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    Response workflowUpdate(String wf);

    @Path("/workflows/validate")
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    Response workflowValidate(String wf);

    @Path("/actions/validate")
    @POST
    @Consumes({MediaType.TEXT_PLAIN})
    Response actionValidate(String action);

    @Path("/workflows")
    @GET
    Response workflowList();

    @Path("/workflows/{name}")
    @DELETE
    Response workflowDelete(@PathParam("name") String workflowName);


    @Path("/workflows/{workflowIdentifier}")
    @GET
    Response workflowGet(@PathParam("workflowIdentifier") String workflowIdentifier);

    @Path("/workbooks")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    Response workbookCreate(String wb);

    @Path("/workbooks")
    @GET
    Response workbookList();

    @Path("/workbooks/{name}")
    @DELETE
    Response workbookDelete(@PathParam("name") String workbookName);

    @Path("/executions")
    @POST
    Response workflowExecutionCreate(WorkflowExecutionCreateRequest executionCreateRequest);

    @Path("/executions/{id}")
    @GET
    Response workflowExecutionGet(@PathParam("id") String workFlowExecutionId);

    @Path("/executions")
    @GET
    Response workflowExecutionList();

    @Path("/executions/{id}")
    @DELETE
    Response workflowExecutionDelete(@PathParam("id") String workflowExecutionId);

    @Path("/action_executions")
    @GET
    Response actionExecutionList();

    /*
     *
     * @param actionExecutionId Action execution ID to update
     * @param request The request to send
     * @return The Mistral API response
     */
    @Path("/action_executions/{id}")
    @PUT
    Response actionExecutionUpdate(@PathParam("id") String actionExecutionId,
                                   ActionExecutionUpdateRequest actionExecutionUpdateRequest);

    @Path("/cron_triggers")
    @POST
    Response cronTriggerCreate(CronTriggerCreateRequest cronTrigger);

    @Path("/cron_triggers")
    @GET
    Response cronTriggersList();

    @Path("/cron_triggers/{name}")
    @GET
    Response cronTrigger(@PathParam("name") String name);

    @Path("/cron_triggers/{name}")
    @DELETE
    Response cronTriggerDelete(@PathParam("name") String name);

    @Path("/actions")
    @POST
    @Produces({MediaType.TEXT_PLAIN})
    Response actionCreate(String actionDsl);

    @Path("/actions")
    @PUT
    @Produces({MediaType.TEXT_PLAIN})
    Response actionUpdate(String actionDsl);

    @Path("/actions")
    @GET
    Response actionList();

    @Path("/actions/{name}")
    @DELETE
    Response actionDelete(@PathParam("name") String name);
}
