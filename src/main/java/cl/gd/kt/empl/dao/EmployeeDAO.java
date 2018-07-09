package cl.gd.kt.empl.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cl.gd.kt.empl.dao.query.QueryEmployeeUtil;
import cl.gd.kt.empl.exception.AppException;
import cl.gd.kt.empl.model.Employee;
import cl.gd.kt.empl.model.commons.Count;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeDAO extends JdbcRepositoryWrapper {

    private static final String ERROR_EMPLOYEE = "The employee is not registered";
    private static final String ERROR_REGISTERED_EMPLOYEE = "The employee is already registered";

    public EmployeeDAO(Vertx vertx, JsonObject config) {
        super(vertx, config);
    }

    /**
     * 
     * @param resultHandler
     * @return
     */
    public void getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler) {
        this.retrieveAll(QueryEmployeeUtil.getEmployeesSql())
        .map(rawList -> rawList.stream().map(Employee::new).collect(Collectors.toList())).setHandler(resultHandler);
    }

    /**
     * 
     * @param id
     * @param resultHandler
     * @return
     */
    public void getEmployeById (Long id, Handler<AsyncResult<Employee>> resultHandler) {
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
    }

    /**
     * 
     * @param employee
     * @param resultHandler
     * @return
     */
    public EmployeeDAO postEmployee(Employee employee, Handler<AsyncResult<Long>> resultHandler) {
        this.executeOneResult(employee.convert(), QueryEmployeeUtil.postEmployeeSql(), jsonObjectAsyncResult -> {
        	if (jsonObjectAsyncResult.succeeded()) {
                Long seq = jsonObjectAsyncResult.result().isPresent() ? (Long) jsonObjectAsyncResult.result().get().getValue("id") : null;
                if (seq != null) {
                	resultHandler.handle(Future.succeededFuture(seq));
                } else {
                	resultHandler.handle(Future.failedFuture(new AppException(ERROR_REGISTERED_EMPLOYEE)));
                }
            } else {
                resultHandler.handle(Future.failedFuture(jsonObjectAsyncResult.cause()));
            }
        });
        return this;
    }

    /**
     * 
     * @param employee
     * @param resultHandler
     * @return
     */
    public void putEmployee(Employee employee, Handler<AsyncResult<Void>> resultHandler) {
        this.executeNoResult(employee.convertToPut(), QueryEmployeeUtil.putEmployeeSql(), resultHandler);
    }

    /**
     * 
     * @param id
     * @param resultHandler
     * @return
     */
    public void deleteEmployee(String id, Handler<AsyncResult<Void>> resultHandler) {
        this.executeNoResult(new JsonArray().add(id), QueryEmployeeUtil.deleteEmployeeSql(), resultHandler);
    }

    /**
     * 
     * @param page
     * @param limit
     * @param resultHandler
     * @return
     */
    public void getByPage(int page, int limit, Handler<AsyncResult<List<Employee>>> resultHandler) {
        this.retrieveByPage(page, limit, QueryEmployeeUtil.getByPageSql())
        .map(rawList -> rawList.stream().map(Employee::new).collect(Collectors.toList())).setHandler(resultHandler);
    }

    /**
     * 
     * @param isActive
     * @param resultHandler
     * @return
     */
    public void getEmployeesCount(String isActive, Handler<AsyncResult<Count>> resultHandler) {
        this.retrieveOneResultWithParams(new JsonArray().add(isActive), QueryEmployeeUtil.getEmployeesCountSql(), jsonObjectAsyncResult -> {
            if (jsonObjectAsyncResult.succeeded()) {
                Count count = new Count(jsonObjectAsyncResult.result());
                resultHandler.handle(Future.succeededFuture(count));
            } else {
                resultHandler.handle(Future.failedFuture(jsonObjectAsyncResult.cause()));
            }
        });
    }
}
