package com.github.oliverpavey.websocketclock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Clock data model class, for sending to web page in reponse to Ajax request
 * 
 * @author Oliver Pavey
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clock {

	private String time;

}
