package com.github.oliverpavey.websocketclock;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Clock tests.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClockTest {

	/**
	 * Check that the clock does not have a pre-set value for the time.
	 */
	@Test
	public void testClockTimeInitiallyNull() {
		
		Clock clock = new Clock();
		
		assertEquals(null, clock.getTime());
	}

	/**
	 * Check that clock retains and returns a value set for the time.
	 */
	@Test
	public void testClockTimeCanBeSetAndRetrieved() {
		
		Clock clock = new Clock();
		
		clock.setTime("12:00");
		
		assertEquals("12:00", clock.getTime());
	}
}
