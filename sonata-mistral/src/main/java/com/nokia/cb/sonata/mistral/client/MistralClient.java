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

package com.nokia.cb.sonata.mistral.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Iterables;
import com.nokia.cb.sonata.mistral.client.exception.MistralException;
import com.nokia.cb.sonata.mistral.client.exception.MistralHttpException;
import com.nokia.cb.sonata.mistral.client.model.*;
import com.nokia.cb.sonata.mistral.client.request.filters.KeystoneAuthFilter;
import org.jboss.resteasy.client.jaxrs.ClientHttpEngine;
import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.client.IOSClientBuilder;
import org.openstack4j.model.identity.Endpoint;
import org.openstack4j.openstack.OSFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class MistralClient extends AbstractProxyClient<MistralErrorResponse> {

    private final Logger LOGGER = LoggerFactory.getLogger(MistralClient.class);
    private static final String MISTRAL_SERVICE_NAME = "mistralv2";
    private static final String MISTRAL_SERVICE_TYPE = "workflowv2";
    private static final String MISTRAL_URI_PROPERTY_NAME = "com.nokia.cb.sonata.mistral.url";
    private MistralApi mistralApi;

    /**
     * This will create a mistral client without keystone (stand alone mistral)
     * with the url that supplied.
     *
     * @param baseUri Mandatory base URI (e.g., "http://localhost:8989/v2/")
     */
    public MistralClient(String baseUri) {
        super(MistralErrorResponse.class);
        if (StringUtils.isEmpty(baseUri)) {
            throw new MistralException("missing mistral uri ");
        }
        this.mistralApi = createProxy(MistralApi.class, baseUri, null);
    }

    /**
     * This will create a mistral client with authentication to keystone
     * according to the endpoint in services list (mistral baseUrl will
     * be take from keystone endpoint-list).
     *
     * @param keystoneEndpoint keystone endpoint url for example : "http://10.5.150.5:5000/v2.0"
     * @param tenant           keystone tenant name
     * @param user             keystone user name
     * @param pass             keystone password
     */
    public MistralClient(String keystoneEndpoint, String tenant, String user, String pass) {
        super(MistralErrorResponse.class);
        Endpoint mistralEndpoint = null;
        IOSClientBuilder.V2 builder = OSFactory.builder()
                .endpoint(keystoneEndpoint)
                .tenantName(tenant)
                .credentials(user, pass);

        OSClient os = builder.authenticate();
        List<? extends Endpoint> endpoints = os.identity().listTokenEndpoints();

        for (Endpoint ep : endpoints) {
            if (ep.getName().equals(MISTRAL_SERVICE_NAME) && ep.getType().equals(MISTRAL_SERVICE_TYPE)) {
                mistralEndpoint = ep;
                break;
            }
        }

        if (mistralEndpoint == null) {
            throw new MistralException("Mistral endpoint not found on keystone " + keystoneEndpoint + ". Existing endpoints [ " + endpoints.toString() + " ]");
        }

        this.mistralApi = createProxy(MistralApi.class, mistralEndpoint.getPublicURL().toString(), os.getToken().getId());
    }

    @Override
    protected ResteasyClient createClient(String authToken, ClientHttpEngine engine) {
        // Creating JacksonConfig that has Mistral specific format (date format, ...)
        JacksonConfig jacksonConfig = new JacksonConfig();

        ResteasyClient client = super.createClient(authToken, engine);
        client.register(new KeystoneAuthFilter(authToken));
        client.register(jacksonConfig);

        return client;
    }


    @Override
    protected RuntimeException newException(String message, Exception e) {
        return new MistralException(message, e);
    }


    @Override
    protected RuntimeException newHttpException(String message, int httpStatus, ClientRequestContext clientRequestContext, MistralErrorResponse errorResponse, Exception e) {
        return new MistralHttpException(message, httpStatus, errorResponse, e);
    }


    public ValidateResponse workflowValidate(String workflow) {
        LOGGER.info("validate workflow [{}]", workflow);
        Response response = null;
        try {
            response = mistralApi.workflowValidate(workflow);
            return readEntity(response, ValidateResponse.class);
        } finally {
            close(response);
        }
    }

    public ValidateResponse actionValidate(String action) {
        LOGGER.info("validate action [{}]", action);
        Response response = null;
        try {
            response = mistralApi.actionValidate(action);
            return readEntity(response, ValidateResponse.class);
        } finally {
            close(response);
        }
    }


    public Workflow workflowCreate(String workflow) {
        return Iterables.getOnlyElement(workflowsCreate(workflow));
    }


    public List<Workflow> workflowsCreate(String workflow) {
        LOGGER.info("Create workflows [{}]", workflow);
        Response response = null;
        try {
            response = mistralApi.workflowCreate(workflow);
            return readEntity(response, WorkflowListResponse.class).getWorkflows();
        } finally {
            close(response);
        }
    }


    public Workflow workflowUpdate(String workflowDsl) {
        return Iterables.getOnlyElement(workflowsUpdate(workflowDsl));
    }


    public List<Workflow> workflowsUpdate(String workflowDsl) {
        LOGGER.info("Update workflows [{}]", workflowDsl);
        Response response = null;
        try {
            response = mistralApi.workflowUpdate(workflowDsl);
            return readEntity(response, WorkflowListResponse.class).getWorkflows();
        } finally {
            close(response);
        }
    }


    public boolean workflowDelete(String workflowName) {
        LOGGER.info("Delete workflow [{}]", workflowName);
        Response response = null;
        try {
            response = mistralApi.workflowDelete(workflowName);
            return validateResponseForDelete(response);
        } finally {
            close(response);
        }
    }


    public List<Workflow> workflowList() {
        LOGGER.debug("Workflow list");
        Response response = null;
        try {
            response = mistralApi.workflowList();
            return readEntity(response, WorkflowListResponse.class).getWorkflows();
        } finally {
            close(response);
        }
    }


    public Workbook workbookCreate(String workbook) {
        LOGGER.info("Create workbook [{}]", workbook);
        Response response = null;
        try {
            response = mistralApi.workbookCreate(workbook);
            return readEntity(response, Workbook.class);
        } finally {
            close(response);
        }
    }


    public boolean workbookDelete(String workbookName) {
        LOGGER.info("Delete workbook [{}]", workbookName);
        Response response = null;
        try {
            response = mistralApi.workbookDelete(workbookName);
            return validateResponseForDelete(response);
        } finally {
            close(response);
        }
    }


    public List<Workbook> workbookList() {
        LOGGER.debug("Workbook list");
        Response response = null;
        try {
            response = mistralApi.workbookList();
            return readEntity(response, WorkbookListResponse.class).getWorkbooks();
        } finally {
            close(response);
        }
    }


    public WorkflowExecution workflowExecutionCreate(WorkflowExecutionCreateRequest executionCreateRequest) {
        LOGGER.info("Create workflow execution {}", executionCreateRequest);
        Response response = null;
        try {
            response = mistralApi.workflowExecutionCreate(executionCreateRequest);
            return readEntity(response, WorkflowExecution.class);
        } finally {
            close(response);
        }
    }


    public List<ActionExecution> actionExecutionList() {
        LOGGER.debug("Action execution list");
        Response response = null;
        try {
            response = mistralApi.actionExecutionList();
            return readEntity(response, ActionExecutionListResponse.class).getActionExecutions();
        } finally {
            close(response);
        }
    }


    public ActionExecution actionExecutionUpdate(
            String actionExecutionId,
            ActionExecutionUpdateRequest actionExecutionUpdateRequest) {

        LOGGER.info("Update action-execution id [{}] with {}", actionExecutionId, actionExecutionUpdateRequest);
        Response response = null;
        try {
            response = mistralApi.actionExecutionUpdate(actionExecutionId, actionExecutionUpdateRequest);
            return readEntity(response, ActionExecution.class);
        } finally {
            close(response);
        }
    }


    public CronTrigger cronTriggerCreate(CronTriggerCreateRequest cronTrigger) {
        LOGGER.info("Create cron-trigger {}", cronTrigger);
        Response response = null;
        try {
            response = mistralApi.cronTriggerCreate(cronTrigger);
            return readEntity(response, CronTrigger.class);
        } finally {
            close(response);
        }
    }


    public List<CronTrigger> cronTriggerList() {
        LOGGER.debug("Cron-trigger list");
        Response response = null;
        try {
            response = mistralApi.cronTriggersList();
            return readEntity(response, CronTriggerListResponse.class).getCronTriggers();
        } finally {
            close(response);
        }
    }


    public CronTrigger cronTriggerGet(String cronTriggerName) {
        LOGGER.info("Get cron-trigger named [{}]", cronTriggerName);
        Response response = null;
        try {
            response = mistralApi.cronTrigger(cronTriggerName);
            return readEntity(response, CronTrigger.class);
        } finally {
            close(response);
        }
    }


    public boolean cronTriggerDelete(String cronTriggerName) {
        LOGGER.info("Delete cron-trigger named[{}]", cronTriggerName);
        Response response = null;
        try {
            response = mistralApi.cronTriggerDelete(cronTriggerName);
            return validateResponseForDelete(response);
        } finally {
            close(response);
        }
    }


    public Action actionCreate(String actionDsl) {
        return Iterables.getOnlyElement(actionsCreate(actionDsl));
    }


    public List<Action> actionsCreate(String actionDsl) {
        LOGGER.info("Creating actions with DSL [" + actionDsl + "]");
        Response response = null;
        try {
            response = mistralApi.actionCreate(actionDsl);
            return readEntity(response, ActionListResponse.class).getActions();
        } finally {
            close(response);
        }
    }


    public Action actionUpdate(String actionDsl) {
        return Iterables.getOnlyElement(actionsUpdate(actionDsl));
    }


    public List<Action> actionsUpdate(String actionDsl) {
        LOGGER.info("Updating actions with DSL [" + actionDsl + "]");
        Response response = null;
        try {
            response = mistralApi.actionUpdate(actionDsl);
            return readEntity(response, ActionListResponse.class).getActions();
        } finally {
            close(response);
        }
    }


    public List<Action> actionList() {
        LOGGER.debug("Action list");
        Response response = null;
        try {
            response = mistralApi.actionList();
            return readEntity(response, ActionListResponse.class).getActions();
        } finally {
            close(response);
        }
    }


    public boolean actionDelete(String actionName) {
        LOGGER.info("Action delete [{}]", actionName);
        Response response = null;
        try {
            response = mistralApi.actionDelete(actionName);
            return validateResponseForDelete(response);
        } finally {
            close(response);
        }
    }


    public WorkflowExecution workflowExecutionGet(String workFlowExecutionId) {
        LOGGER.info("GET EXECUTION DATA [{}]", workFlowExecutionId);
        Response response = null;
        try {
            response = mistralApi.workflowExecutionGet(workFlowExecutionId);
            return readEntity(response, WorkflowExecution.class);
        } finally {
            close(response);
        }

    }

    public Workflow workflowGet(String workflowIdentifier) {
        Response response = null;
        try {
            response = mistralApi.workflowGet(workflowIdentifier);
            return readEntity(response, Workflow.class);
        } finally {
            close(response);
        }
    }

    public boolean workflowExecutionDelete(String workflowExecutionId) {
        Response response = null;
        try {
            response = mistralApi.workflowExecutionDelete(workflowExecutionId);
            return validateResponseForDelete(response);
        } finally {
            close(response);
        }
    }

    public List<WorkflowExecution> workflowExecutionList() {
        Response response = null;
        try {
            response = mistralApi.workflowExecutionList();
            return readEntity(response, WorkflowExecutionListResponse.class).getWorkflowExecutions();
        } finally {
            close(response);
        }
    }

    public static class JacksonConfig
            implements ContextResolver<ObjectMapper> {

        public static final String OPENSTACK_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

        private final ObjectMapper objectMapper;


        public JacksonConfig() {

            objectMapper = new ObjectMapper();
            DateFormat dateFormat = new SimpleDateFormat(OPENSTACK_DATE_FORMAT);
            objectMapper.setDateFormat(dateFormat);
        }


        @Override
        public ObjectMapper getContext(Class<?> arg0) {
            return objectMapper;
        }
    }
}
