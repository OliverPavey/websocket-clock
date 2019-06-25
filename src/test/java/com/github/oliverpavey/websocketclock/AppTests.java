package com.github.oliverpavey.websocketclock;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * App tests.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppTests {

	@Autowired
	ApplicationContext applicationContext;
	
	/**
	 * Check that all application @Component classes are available in the context.
	 */
	@Test
	public void contextLoads() {

		assertTrue( applicationContext.containsBeanDefinition("app") );
		assertTrue( applicationContext.containsBeanDefinition("appController") );
		assertTrue( applicationContext.containsBeanDefinition("clockAutoUpdate") );
		assertTrue( applicationContext.containsBeanDefinition("clockMaker") );
		assertTrue( applicationContext.containsBeanDefinition("webSocketConfig") );
	}
}
