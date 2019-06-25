package com.github.oliverpavey.websocketclock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Test application (spike) to validate use of websocket for continuous update.
 * 
 * For a basic introductino to websocket with Spring see:
 * https://spring.io/guides/gs/messaging-stomp-websocket/
 * 
 * And for details of triggering an update from Java (rather than from Javascript on the client) see: 
 * https://stackoverflow.com/questions/32826309/how-to-invoke-spring-stomp-web-socket-method-from-rest-client
 * 
 * @author Oliver Pavey
 *
 */
@SpringBootApplication
public class App {

	/**
	 * Start Spring Boot application.
	 * 
	 * @param args	Command line arguments.
	 */
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

}
