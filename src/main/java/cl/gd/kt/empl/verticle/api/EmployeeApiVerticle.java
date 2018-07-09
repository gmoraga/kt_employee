package cl.gd.kt.empl.verticle.api;

import cl.gd.kt.empl.exception.AppException;
import cl.gd.kt.empl.model.Employee;
import cl.gd.kt.empl.model.commons.Paginator;
import cl.gd.kt.empl.service.EmployeeService;
import cl.gd.kt.empl.verticle.RestApiVerticle;
import cl.gd.kt.empl.verticle.util.ValidateEmployeeApiUtil;
import io.vertx.core.json.Json;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmployeeApiVerticle extends RestApiVerticle {
	//routes
	private static final String API_EMPLOYEE = "/api/v1/employees";
	private static final String API_EMPLOYEES_PAGINATED = API_EMPLOYEE + "/paginated";
	private static final String API_EMPLOYEE_COUNT = API_EMPLOYEE + "/count";
	private static final String API_EMPLOYEE_ID = API_EMPLOYEE + "/:id";
	//services
	private EmployeeService employeeService;

	/**
	 * 
	 * @param router
	 * @param notifyImpl
	 */
	public EmployeeApiVerticle(Router router, EmployeeService employeeService) {
		//routes
		this.loadRoute(router);

		//services
		this.employeeService = employeeService;
	}

	/**
	 * 
	 * @param router
	 */
	public void loadRoute(Router router) {
		// API
		router.post(API_EMPLOYEES_PAGINATED).handler(this::apiEmployeesPaginated);
		router.post(API_EMPLOYEE_COUNT).handler(this::apiEmployeesCount);

		router.get(API_EMPLOYEE).handler(this::apiEmployeeList);
		router.get(API_EMPLOYEE_ID).handler(this::apiGetEmployeeById);
		
		router.post(API_EMPLOYEE).handler(this::apiNewEmployee);
		router.put(API_EMPLOYEE_ID).handler(this::apiEditEmployee);
		router.delete(API_EMPLOYEE_ID).handler(this::apiDeleteEmployeeById);
	}

	/**
	 * 
	 * @param context
	 */
	private void apiEmployeeList (RoutingContext context) {
		context.vertx().executeBlocking(future -> {
			employeeService.getEmployees(resultHandler(context, Json::encodePrettily));
		}, false, resultHandler(context, Json::encodePrettily));
	}

	/**
	 * 
	 * @param context
	 */
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

	/**
	 * 
	 * @param context
	 */
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

	/**
	 * 
	 * @param context
	 */
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

	/**
	 * 
	 * @param context
	 */
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

	/**
	 * 
	 * @param context
	 */
	private void apiEmployeesPaginated (RoutingContext context) {
		context.vertx().executeBlocking(future -> {
			Paginator page = Json.decodeValue(context.getBodyAsString(), Paginator.class);
			try {
				ValidateEmployeeApiUtil.getPage(page);
				employeeService.getEmployeesPaginated(page, resultHandler(context, Json::encodePrettily));
			} catch (AppException e) {
				badRequest(context, e);
			}
		}, false, resultHandler(context));
	}

	/**
	 * 
	 * @param context
	 */
	private void apiEmployeesCount (RoutingContext context) {
		context.vertx().executeBlocking(future -> {
			String isActive = context.getBodyAsJson().getString("isActive");
			employeeService.getEmployeesCount(isActive, resultHandler(context, Json::encodePrettily));
		}, false, resultHandler(context, Json::encodePrettily));
	}


}