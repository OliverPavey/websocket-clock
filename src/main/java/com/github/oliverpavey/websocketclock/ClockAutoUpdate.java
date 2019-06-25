package com.github.oliverpavey.websocketclock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Class to schedule broadcasts of new Clocks over "/topic/clock" topic.
 * 
 * @author Oliver Pavey
 *
 */
@Component
@EnableScheduling
public class ClockAutoUpdate {

	@Autowired
	ClockMaker clockMaker;
	
	@Autowired
	SimpMessagingTemplate simpMessagingTemplate;
	
	/**
	 * Broadcast new Clock on "/topic/clock" topic.
	 * 
	 * Spring will cally this function every 5 seconds (i.e. every 5,000 milliseconds)
	 */
	@Scheduled(fixedDelay=5_000)
	public void tick() {
		simpMessagingTemplate.convertAndSend("/topic/clock", clockMaker.makeClock() ); 
	}

}
