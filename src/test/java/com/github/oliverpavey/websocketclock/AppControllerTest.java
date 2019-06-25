package com.github.oliverpavey.websocketclock;

import static org.junit.Assert.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * AppController tests.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppControllerTest {

	@Autowired
	AppController appController;
	
	@Mock
	private ClockMaker mockClockMaker;

	@Mock
	private Clock mockClock = spy( new Clock() );
	
	/**
	 * Check redirection to thymeleaf template for home page.
	 */
	@Test
	public void testHome() {
		
		String result = appController.home();
		assertEquals("home", result);
	}

	/**
	 * Check /topic/clock endpoint creates exactly one new clock and returns it. 
	 */
	@Test
	public void testTimeInvokesClockMaker() {
		
		appController.clockMaker = mockClockMaker;
		
		when(mockClockMaker.makeClock()).thenReturn(mockClock);
		
		Clock time = appController.time();
		
		assertEquals( mockClock , time );
		verify(mockClockMaker, times(1)).makeClock();
	}
}
