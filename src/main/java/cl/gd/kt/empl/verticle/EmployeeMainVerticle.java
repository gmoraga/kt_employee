package cl.gd.kt.empl.verticle;

import cl.gd.kt.empl.service.EmployeeService;
import cl.gd.kt.empl.service.EmployeeServiceImpl;
import cl.gd.kt.empl.verticle.api.EmployeeApiVerticle;
import cl.gd.kt.empl.verticle.event.PublishEvent;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.http.HttpServer;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

public class EmployeeMainVerticle extends RestApiVerticle {

	private static final String API_EMPLOYEE = "/api/v1/employees";
    private static final String API_HEALTHCHECK = API_EMPLOYEE + "/healthcheck";
	
	private static final String PORT = "http.port";
	
	//deployment options
	private DeploymentOptions svrOptsUltHg;

	//config
	private final JsonObject cfg = this.config();

	//worker pool
	private static final int WK_PL_SIZE_UHG = 120;

	//services
	private EmployeeService employeeService;
	
	@Override
	public void start() throws Exception {
		super.start();
	
		//Events
		PublishEvent publishEvent;
		
		//deployment options
		this.svrOptsUltHg = new DeploymentOptions().setWorkerPoolSize(WK_PL_SIZE_UHG).setConfig(this.cfg);

		//principal router
		Router router = Router.router(vertx);

		//body handler
		router.route().handler(BodyHandler.create());

		//events
		publishEvent = new PublishEvent(vertx);
		
		//services
		this.employeeService = new EmployeeServiceImpl(vertx, this.cfg, publishEvent);
		
		//routes
		this.loadRoute(router);

		//deploy
		this.deployRestVerticle(router);

		//cors support
		this.enableCorsSupport(router);

		//http server
		this.createHttpServer(router, this.cfg.getInteger(PORT));
	}

	/**
	 * 
	 * @param router
	 */
	private void loadRoute(Router router) {
		//healthcheck
		router.get(API_HEALTHCHECK).handler(this::apiHealthCheck);
	}

	/**
	 * 
	 * @param router
	 */
	private void deployRestVerticle(Router router) {
		//API
		vertx.deployVerticle(new EmployeeApiVerticle(router, this.employeeService), this.svrOptsUltHg);
	}

	/**
	 * Create http server for the REST service.
	 *
	 * @param router router instance
	 * @param port http port
	 * @return async result of the procedure
	 */
	@Override
	protected Future<Void> createHttpServer(Router router, int port) {
		Future<HttpServer> httpServerFuture = Future.future();
		vertx.createHttpServer().requestHandler(router::accept).listen(port, httpServerFuture.completer());
		return httpServerFuture.map(r -> null);
	}

	/**
	 * 
	 * @param context
	 */
	private void apiHealthCheck(RoutingContext context) {
		context.response().setStatusCode(200).setStatusMessage("OK").end();
	}

}
