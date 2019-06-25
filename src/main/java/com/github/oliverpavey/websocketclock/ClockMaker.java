package com.github.oliverpavey.websocketclock;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

/**
 * Class to encapsulate the making of clocks.  
 * 
 * N.B. The time on a clock never changes. For a new time make a new clock.
 * 
 * @author Oliver Pavey
 *
 */
@Component
public class ClockMaker {

	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm.ss");

	Supplier<Date> dateSupplier = () -> new Date();
	
	/**
	 * Make a clock containing the current time.
	 * 
	 * @return	new Clock object.
	 */
	public Clock makeClock() {
		return new Clock( sdf.format( dateSupplier.get() ) );
	}
}
