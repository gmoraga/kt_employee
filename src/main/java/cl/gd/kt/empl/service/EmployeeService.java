package cl.gd.kt.empl.service;

import cl.gd.kt.empl.model.Employee;
import cl.gd.kt.empl.model.commons.Count;
import cl.gd.kt.empl.model.commons.Paginator;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

public interface EmployeeService {

    void getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler);

    void getEmployeeById (Long id, Handler<AsyncResult<Employee>> resultHanlder);

    void postEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    void putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    void deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler);

    void getEmployeesPaginated (Paginator page, Handler<AsyncResult<List<Employee>>> resultHandler);

    void getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler);
}
