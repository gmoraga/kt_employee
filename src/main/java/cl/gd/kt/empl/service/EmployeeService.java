package cl.gd.kt.empl.service;

import cl.gd.kt.empl.model.Employee;
import cl.gd.kt.empl.model.commons.Count;
import cl.gd.kt.empl.model.commons.Paginator;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

public interface EmployeeService {

    EmployeeService getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler);

    EmployeeService getEmployeeById (Long id, Handler<AsyncResult<Employee>> resultHanlder);

    EmployeeService postEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService getEmployeesPaginated (Paginator page, Handler<AsyncResult<List<Employee>>> resultHandler);

    EmployeeService getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler);
}
