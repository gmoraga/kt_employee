package com.kanodoe.talk.kitchen.employee.service;

import com.kanodoe.talk.kitchen.employee.model.commons.Count;
import com.kanodoe.talk.kitchen.employee.model.Employee;
import com.kanodoe.talk.kitchen.employee.model.commons.Page;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;

import java.util.List;

public interface EmployeeService {

    EmployeeService getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler);

    EmployeeService getEmployeeById (Long id, Handler<AsyncResult<Employee>> resultHanlder);

    EmployeeService postEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler);

    EmployeeService getEmployeesPaginated (Page page, Handler<AsyncResult<List<Employee>>> resultHandler);

    EmployeeService getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler);
}
