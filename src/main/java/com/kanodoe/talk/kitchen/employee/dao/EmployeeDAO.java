package com.kanodoe.talk.kitchen.employee.dao;

import com.kanodoe.talk.kitchen.employee.dao.query.QueryEmployeeUtil;
import com.kanodoe.talk.kitchen.employee.exception.AppException;
import com.kanodoe.talk.kitchen.employee.model.commons.Count;
import com.kanodoe.talk.kitchen.employee.model.Employee;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EmployeeDAO extends JdbcRepositoryWrapper {

    private static final String ERROR_EMPLOYEE = "The employee is not registered";
    private static final String ERROR_REGISTERED_EMPLOYEE = "The employee is already registered";

    public EmployeeDAO (Vertx vertx, JsonObject config) {
        super(vertx, config);
    }


    public EmployeeDAO getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler) {

        this.retrieveAll(QueryEmployeeUtil.getEmployeesSql())
                .map(rawList -> rawList.stream()
                        .map(Employee::new)
                        .collect(Collectors.toList())
                )
                .setHandler(resultHandler);
        return this;

    }

    public EmployeeDAO getEmployeById (Long id, Handler<AsyncResult<Employee>> resultHandler) {

        this.executeOneResult(new JsonArray().add(id), QueryEmployeeUtil.getEmployeeByIdSql(), optionalAsyncResult -> {
            if (optionalAsyncResult.succeeded()) {
                Optional<JsonObject> jsonObjectOptional = optionalAsyncResult.result();
                if (jsonObjectOptional.isPresent()) {
                    resultHandler.handle(Future.succeededFuture(new Employee(jsonObjectOptional.get())));
                } else {
                    resultHandler.handle(Future.failedFuture(new AppException(ERROR_EMPLOYEE)));
                }
            } else {
                resultHandler.handle(Future.failedFuture(optionalAsyncResult.cause()));
            }
        });

        return this;
    }

    public EmployeeDAO postEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler) {
        this.executeNoResult(employee.convert(), QueryEmployeeUtil.postEmployeeSql(), rs -> {
            if (rs.succeeded()) {
                resultHandler.handle(Future.succeededFuture());
            } else {
                resultHandler.handle(Future.failedFuture(new AppException(ERROR_REGISTERED_EMPLOYEE)));
            }
        });

        return this;
    }

    public EmployeeDAO putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler) {
        this.executeNoResult(employee.convertToPut(), QueryEmployeeUtil.putEmployeeSql(), resultHandler);

        return this;
    }

    public EmployeeDAO deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler) {
        this.executeNoResult(new JsonArray().add(id), QueryEmployeeUtil.deleteEmployeeSql(), resultHandler);

        return this;
    }

    public EmployeeDAO getByPage (int page, int limit, Handler<AsyncResult<List<Employee>>> resultHandler) {
        this.retrieveByPage(page, limit, QueryEmployeeUtil.getByPageSql())
                .map(rawList -> rawList.stream()
                        .map(Employee::new)
                        .collect(Collectors.toList())
                )
                .setHandler(resultHandler);
        return this;
    }

    public EmployeeDAO getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler) {
        this.retrieveOneResultWithParams(new JsonArray().add(isActive), QueryEmployeeUtil.getEmployeesCountSql(), jsonObjectAsyncResult -> {
            if (jsonObjectAsyncResult.succeeded()) {
                Count count = new Count(jsonObjectAsyncResult.result());
                resultHandler.handle(Future.succeededFuture(count));
            } else {
                resultHandler.handle(Future.failedFuture(jsonObjectAsyncResult.cause()));
            }
        });

        return this;
    }
}
