package com.kanodoe.talk.kitchen.employee.verticle;

import com.kanodoe.talk.kitchen.employee.exception.AppException;
import com.kanodoe.talk.kitchen.employee.model.Employee;
import com.kanodoe.talk.kitchen.employee.model.commons.Page;
import com.kanodoe.talk.kitchen.employee.service.EmployeeService;
import com.kanodoe.talk.kitchen.employee.service.EmployeeServiceImpl;
import com.kanodoe.talk.kitchen.employee.verticle.util.ValidateEmployeeApiUtil;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeVerticle extends RestAPIVerticle {

    private static final String API_EMPLOYEE = "/api/v1/employees";
    private static final String API_EMPLOYEES_PAGINATED = API_EMPLOYEE + "/paginated";
    private static final String API_EMPLOYEE_COUNT = API_EMPLOYEE + "/count";
    private static final String API_EMPLOYEE_ID = API_EMPLOYEE + "/:id";
    private static final String API_HEALTHCHECK = API_EMPLOYEE + "/healthcheck";

    private EmployeeService employeeService;

    @Override
    public void start() throws Exception {
        super.start();
        this.employeeService = new EmployeeServiceImpl(vertx, config());

        Router router = Router.router(vertx);
        // Body Handler
        router.route().handler(BodyHandler.create());

        this.loadRoute(router);

        this.enableCorsSupport(router);

        int port = config().getInteger("http.port");

        this.createHttpServer(router, port);
    }

    private void loadRoute (Router router) {

        // Healthcheck
        router.get(API_HEALTHCHECK).handler(this::apiHealthCheck);

        // API
        router.post(API_EMPLOYEES_PAGINATED).handler(this::apiEmployeesPaginated);
        router.post(API_EMPLOYEE_COUNT).handler(this::apiEmployeesCount);
        router.get(API_EMPLOYEE).handler(this::apiEmployeeList);
        router.get(API_EMPLOYEE_ID).handler(this::apiGetEmployeeById);
        router.post(API_EMPLOYEE).handler(this::apiNewEmployee);
        router.put(API_EMPLOYEE_ID).handler(this::apiEditEmployee);
        router.delete(API_EMPLOYEE_ID).handler(this::apiDeleteEmployeeById);
    }

    private void apiHealthCheck (RoutingContext context) {
        context.response().setStatusCode(200).setStatusMessage("OK").end();
    }

    private void apiEmployeeList (RoutingContext context) {
        context.vertx().executeBlocking(future -> employeeService.getEmployees(resultHandler(context, Json::encodePrettily)), false, resultHandler(context, Json::encodePrettily));
    }

    private void apiGetEmployeeById (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            String id = context.request().getParam("id");
            try {
                ValidateEmployeeApiUtil.getId(id);
                employeeService.getEmployeeById(Long.valueOf(id), resultHandler(context, Json::encodePrettily));
            } catch (AppException e) {
                badRequest(context, e);
            }
        }, false, resultHandler(context));
    }

    private void apiNewEmployee (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            Employee employee = Json.decodeValue(context.getBodyAsString(), Employee.class);
            try {
                ValidateEmployeeApiUtil.postEmployee(employee);
                employeeService.postEmployee(employee, resultHandler(context));
            } catch (AppException e) {
                badRequest(context, e);
            }
        }, false, resultHandler(context));
    }

    private void apiEditEmployee (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            String id = context.request().getParam("id");
            Employee employee = Json.decodeValue(context.getBodyAsString(), Employee.class);
            employee.setId(Long.valueOf(id));
            try {
                ValidateEmployeeApiUtil.postEmployee(employee);
                ValidateEmployeeApiUtil.getId(id);
                employeeService.putEmployee(employee, resultHandler(context));
            } catch (AppException e) {
                badRequest(context, e);
            }
        }, false, resultHandler(context));
    }

    private void apiDeleteEmployeeById (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            String id = context.request().getParam("id");
            try {
                ValidateEmployeeApiUtil.getId(id);
                employeeService.deleteEmployee (id, resultHandler(context));
            } catch (AppException e) {
                badRequest(context, e);
            }
        }, false, resultHandler(context));
    }

    private void apiEmployeesPaginated (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            Page page = Json.decodeValue(context.getBodyAsString(), Page.class);
            try {
                ValidateEmployeeApiUtil.getPage(page);
                employeeService.getEmployeesPaginated(page, resultHandler(context, Json::encodePrettily));
            } catch (AppException e) {
                badRequest(context, e);
            }
        }, false, resultHandler(context));
    }

    private void apiEmployeesCount (RoutingContext context) {
        context.vertx().executeBlocking(future -> {
            String isActive = context.getBodyAsJson().getString("isActive");
            employeeService.getEmployeesCount(isActive, resultHandler(context, Json::encodePrettily));
        }, false, resultHandler(context, Json::encodePrettily));
    }

}
