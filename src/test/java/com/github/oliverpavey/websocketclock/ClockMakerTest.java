package com.github.oliverpavey.websocketclock;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * ClockMaker test.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClockMakerTest {

	/**
	 * Simple date creator (for testing only).
	 * 
	 * year - the four digit year.
	 * month - the month between 1-12.
	 * day - the day of the month between 1-31.
	 * hours - the hours between 0-23.
	 * minutes - the minutes between 0-59.
	 * seconds - the seconds between 0-59.
	 */
	private Date easyDate(int year, int month, int day, int hours, int minutes, int seconds) {
		
		final String SIMPLE_DATE_FORMAT = "yyyy MM dd HH mm ss";
		final String SIMPLE_STRING_FORMAT = "%4d %2d %2d %2d %2d %2d";
		
		SimpleDateFormat sdf = new SimpleDateFormat(SIMPLE_DATE_FORMAT);
		String simpleDateString = String.format(SIMPLE_STRING_FORMAT, 
				year, month, day, hours, minutes, seconds);
		
		Date date = null;
		try {
			date = sdf.parse(simpleDateString);
		} catch (ParseException e) {
		}
		
		return date;
	}
	
	/**
	 * Check that a noon date is encoded as "12:00.00" by the ClockMaker.
	 */
	@Test
	public void testClockMakerAtNoon() {
		
		ClockMaker clockMaker = new ClockMaker();
		clockMaker.dateSupplier = () -> easyDate(2000, 06, 20, 12, 0, 0);
		
		Clock clock = clockMaker.makeClock();
		
		assertNotNull("Clock returned", clock);
		
		String time = clock.getTime();
		
		assertEquals("Time is as expected", "12:00.00", time);
	}
}
