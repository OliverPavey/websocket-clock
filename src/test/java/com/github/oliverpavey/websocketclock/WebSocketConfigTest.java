package com.github.oliverpavey.websocketclock;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;

/**
 * WebSocketConfig tests.
 * 
 * @author Oliver Pavey
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class WebSocketConfigTest {

	/**
	 * Check that configureMessageBroker() creates a /topic broker
	 * and configures an /app destination prefix.
	 */
	@Test
	public void testConfigureMessageBrokerMakesExpectedCalls() {
		
		MessageBrokerRegistry registry = Mockito.mock(MessageBrokerRegistry.class);
		
		WebSocketConfig webSocketConfig = new WebSocketConfig();
		webSocketConfig.configureMessageBroker(registry);
		
		verify(registry, times(1)).enableSimpleBroker(anyString());
		verify(registry, times(1)).enableSimpleBroker("/topic");
		
		verify(registry, times(1)).setApplicationDestinationPrefixes(anyString());
		verify(registry, times(1)).setApplicationDestinationPrefixes("/app");
	}
	
	/**
	 * Check that the endpoint "websocket-clock" is registered, and
	 * that withSockJS() is called to enable fallback options.
	 */
	@Test
	public void testRegisterStompEndpointsMakesExpectedCalls() {
		
		StompWebSocketEndpointRegistration registration = Mockito.mock(StompWebSocketEndpointRegistration.class);
		StompEndpointRegistry registry = Mockito.mock(StompEndpointRegistry.class);
		
		when(registry.addEndpoint("websocket-clock")).thenReturn(registration);
		
		WebSocketConfig webSocketConfig = new WebSocketConfig();
		webSocketConfig.registerStompEndpoints(registry);
		
		verify(registry, times(1)).addEndpoint(anyString());
		verify(registry, times(1)).addEndpoint("websocket-clock");
		
		verify(registration, times(1)).withSockJS();
	}
}
