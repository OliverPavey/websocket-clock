package com.github.oliverpavey.websocketclock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Endpoints (including message mapping endpoints) for application
 * 
 * @author Oliver Pavey
 *
 */
@Controller
@EnableScheduling
public class AppController {

	/**
	 * Homepage mapping
	 * 
	 * @return	Reference to home.html Thymeleaf template
	 */
	@GetMapping("/")
	public String home() {
		return "home"; // src/main/resources/templates/home.html (Thymeleaf template)
	}
	
	@Autowired
	ClockMaker clockMaker;
	
	/**
	 * Message Mapping for "/app/clock" endpoint.
	 * 
	 * N.B. "/app" comes from WebSocketConfig calling setApplicationDestinationPrefixes("/app")
	 * 
	 * @return	Clock to JSONify and broadcast on @SentTo topic ("/topic/clock")
	 */
	@MessageMapping("/clock")
	@SendTo("/topic/clock")
	public Clock time() {
		return clockMaker.makeClock();
	}
}
