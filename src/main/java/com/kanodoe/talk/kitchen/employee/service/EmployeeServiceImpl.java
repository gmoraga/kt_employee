package com.kanodoe.talk.kitchen.employee.service;

import com.kanodoe.talk.kitchen.employee.dao.EmployeeDAO;
import com.kanodoe.talk.kitchen.employee.model.commons.Count;
import com.kanodoe.talk.kitchen.employee.model.Employee;
import com.kanodoe.talk.kitchen.employee.model.commons.Page;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

import java.util.List;

public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;

    public EmployeeServiceImpl(Vertx vertx, JsonObject config) {
        this.employeeDAO = new EmployeeDAO(vertx, config);
    }

    @Override
    public EmployeeService getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler) {

        employeeDAO.getEmployees(resultHandler);
        return this;
    }

    @Override
    public EmployeeService getEmployeeById (Long id, Handler<AsyncResult<Employee>> resultHandler) {

        employeeDAO.getEmployeById(id, resultHandler);
        return this;
    }

    @Override
    public EmployeeService postEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler) {

        employeeDAO.postEmployee (employee, resultHandler);

        return this;
    }

    @Override
    public EmployeeService putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler) {

        employeeDAO.putEmployee (employee, resultHandler);
        return this;
    }

    @Override
    public EmployeeService deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler) {

        employeeDAO.deleteEmployee (id, resultHandler);
        return this;
    }

    @Override
    public EmployeeService getEmployeesPaginated (Page page, Handler<AsyncResult<List<Employee>>> resultHandler) {

        employeeDAO.getByPage(page.getPage(), page.getLimit(), resultHandler);

        return this;
    }

    @Override
    public EmployeeService getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler) {

        employeeDAO.getEmployeesCount(isActive, resultHandler);

        return this;
    }
}
