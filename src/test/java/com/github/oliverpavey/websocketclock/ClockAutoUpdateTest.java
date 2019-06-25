package com.github.oliverpavey.websocketclock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClockAutoUpdate tests.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClockAutoUpdateTest {

	/**
	 * Check that tick() will broadcast a new Clock on /topic/clock.
	 */
	@Test
	public void testTickSendsToTopicClock() {
		
		Clock clock = Mockito.mock(Clock.class);
		ClockMaker clockMaker = Mockito.mock(ClockMaker.class);
		SimpMessagingTemplate simpMessagingTemplate = Mockito.mock(SimpMessagingTemplate.class);
		
		when(clockMaker.makeClock()).thenReturn(clock);
		
		ClockAutoUpdate clockAutoUpdate = new ClockAutoUpdate();
		clockAutoUpdate.clockMaker = clockMaker;
		clockAutoUpdate.simpMessagingTemplate = simpMessagingTemplate;
		
		clockAutoUpdate.tick();
		
		verify(simpMessagingTemplate, times(1)).convertAndSend(anyString(), any(Clock.class));
		verify(simpMessagingTemplate, times(1)).convertAndSend("/topic/clock", clock);
	}

}
