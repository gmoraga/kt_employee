package cl.gd.kt.empl.verticle.event;

import java.util.UUID;

import cl.gd.kt.empl.util.AppEnum;
import cl.gd.kt.empl.util.SystemUtil;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetSocket;
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublishEvent {

	private static final String LOCAL_HOST = "localhost";
	private static final String TYPE_SEND = "send";
	private static final String BUSINESS_DOMAIN = "EMPLOYEE";
	
	private int appPortAcquisitions;
	private int appPortLegal;
	private String eventEmployee;
	private Vertx internalVertx;
	
	public PublishEvent(Vertx vertx) {
		log.info("Start Publish Event kt_Employee...");
		
		this.appPortAcquisitions = SystemUtil.getEnvironmentIntValue(AppEnum.EVENT_PORT_ACQUISITIONS.name());
		this.appPortLegal = SystemUtil.getEnvironmentIntValue(AppEnum.EVENT_PORT_LEGAL.name());
		this.eventEmployee = SystemUtil.getEnvironmentStrValue(AppEnum.EVENT_EMPLOYEE.name());
		this.internalVertx = vertx;
		
		log.info("appPortAcquisitions: "+this.appPortAcquisitions);
		log.info("appPortLegal: "+this.appPortLegal);
		log.info("eventEmployee: "+this.eventEmployee);
	}
	
	/**
	 * 
	 * @param idBusiness
	 * @param typeEvent
	 */
	public void sendEmployeeMessage(Long idBusiness, String typeEvent) {
		Future<Boolean> fututeAcquisitions = Future.future();
		Future<Boolean> fututeLegal = Future.future();
		
		JsonObject message = new JsonObject();
		message.put("idBusiness", idBusiness);
		message.put("businessDomain", BUSINESS_DOMAIN);
		message.put("typeEvent", typeEvent);
		message.put("messageId", UUID.randomUUID().toString());
		
		log.info("message to send: "+message);
		
		NetClient client = this.internalVertx.createNetClient();
		sendEmployee(client, message, this.appPortAcquisitions, this.eventEmployee, fututeAcquisitions.completer());
		sendEmployee(client, message, this.appPortLegal, this.eventEmployee, fututeLegal.completer());
		
		CompositeFuture.all(fututeAcquisitions, fututeLegal).setHandler(handler -> client.close());
	}  
	
	/**
	 * 
	 * @param client
	 * @param message
	 * @param port
	 * @param address
	 * @param resultHandler
	 */
	private void sendEmployee(NetClient client, JsonObject message, int port, String address, Handler<AsyncResult<Boolean>> resultHandler) {
		Future<Boolean> future = Future.future();
		future.setHandler(resultHandler);
		
		client.connect(port, LOCAL_HOST, conn -> {
			if (conn.succeeded()) {
				NetSocket socket = conn.result();
				FrameHelper.sendFrame(TYPE_SEND, address, message, socket);
				socket.close();
			} else {
				log.error("Fail send message: "+message+", port: "+port+", address: "+address);
			}
			future.complete(Boolean.TRUE);
      });
	}
}