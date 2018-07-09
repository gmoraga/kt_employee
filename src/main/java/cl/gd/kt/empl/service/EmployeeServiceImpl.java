package cl.gd.kt.empl.service;

import java.util.List;

import cl.gd.kt.empl.dao.EmployeeDAO;
import cl.gd.kt.empl.model.Employee;
import cl.gd.kt.empl.model.commons.Count;
import cl.gd.kt.empl.model.commons.Paginator;
import cl.gd.kt.empl.verticle.event.PublishEvent;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;

public class EmployeeServiceImpl implements EmployeeService {
	
	private static final String TYPE_EVENT_CREATE = "CREATE";
	private static final String TYPE_EVENT_UPDATE = "UPDATE";
	private static final String TYPE_EVENT_DELETE = "DELETE";

    private final EmployeeDAO employeeDAO;
    private final PublishEvent publishEvent;

    public EmployeeServiceImpl(Vertx vertx, JsonObject config, PublishEvent publishEvent) {
        this.employeeDAO = new EmployeeDAO(vertx, config);
        this.publishEvent = publishEvent;
    }

    @Override
    public void getEmployees (Handler<AsyncResult<List<Employee>>> resultHandler) {
        this.employeeDAO.getEmployees(resultHandler);
    }

    @Override
    public void getEmployeeById (Long id, Handler<AsyncResult<Employee>> resultHandler) {
        this.employeeDAO.getEmployeById(id, resultHandler);
    }

    @Override
    public void postEmployee(Employee employee, Handler<AsyncResult<Void>> resultHandler) {
        this.employeeDAO.postEmployee(employee, resultInsertHandler -> {
        	if (resultInsertHandler.succeeded()) {
        		this.publishEvent.sendEmployeeMessage(resultInsertHandler.result(), TYPE_EVENT_CREATE);
        		resultHandler.handle(Future.succeededFuture());
        	} else {
        		resultHandler.handle(Future.failedFuture(resultInsertHandler.cause()));
        	}
        });
    }

    @Override
    public void putEmployee (Employee employee, Handler<AsyncResult<Void>> resultHandler) {

        this.employeeDAO.putEmployee(employee, resultUpdateHandler -> {
        	if (resultUpdateHandler.succeeded()) {
        		this.publishEvent.sendEmployeeMessage(employee.getId(), TYPE_EVENT_UPDATE);
        		resultHandler.handle(Future.succeededFuture());
        	} else {
        		resultHandler.handle(Future.failedFuture(resultUpdateHandler.cause()));
        	}
        });
    }

    @Override
    public void deleteEmployee (String id, Handler<AsyncResult<Void>> resultHandler) {
        this.employeeDAO.deleteEmployee(id, resultDeleteHandler -> {
        	if (resultDeleteHandler.succeeded()) {
        		this.publishEvent.sendEmployeeMessage(Long.valueOf(id), TYPE_EVENT_DELETE);	
        		resultHandler.handle(Future.succeededFuture());
        	} else {
        		resultHandler.handle(Future.failedFuture(resultDeleteHandler.cause()));
        	}
        });
    }

    @Override
    public void getEmployeesPaginated (Paginator page, Handler<AsyncResult<List<Employee>>> resultHandler) {
        this.employeeDAO.getByPage(page.getPage(), page.getLimit(), resultHandler);
    }

    @Override
    public void getEmployeesCount (String isActive, Handler<AsyncResult<Count>> resultHandler) {
        this.employeeDAO.getEmployeesCount(isActive, resultHandler);
    }
}
