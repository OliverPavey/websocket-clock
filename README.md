# WebSocket Clock README.md

## The web-socket clock demo

This demo shows how a Spring Java MVC server can push updates to a Javascript enabled web-page.  The example project is configured for Thymeleaf, but not Thymeleaf features are used and the code should work equally well with JSP or static HTML.

## Display Preview

This is how the browser page looks when opened.

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"></link>
<div class="container" style="border-style: solid;">
<h1 class="display-4">WebSocket demo</h1>
<div class="jumbotron">
  <h1 class="display-2"><span id="clock">07:55.48</span>&nbsp;</h1>
  <p>The above clock should update: <span class="badge badge-secondary">(When running against a live server, but not in this document.)</span></p>
  <ul>
  <li>When the page is loaded.</li>
  <li>When the force update button is clicked on this browser.</li>
  <li>When the force update button is clicked on any other browser 
  (since the request to the server causes a push to all clients).</li>
  <li>When the server is scheduled to push the time (every 5 seconds).</li>
  </ul>
  <p>N.B. The auto-update is deliberately slow so that forced updates can be observed.</p>
</div>
<button id="update" class="btn btn-primary">Force Update</button><div>&nbsp;</div>
</div><br/>

As long as the server is running and the connection to the server is good 
the clock will update every five seconds.

## The Java code

`App.java` launches the Spring application.

`Clock.java` is a very simple class with a single private field (used to store a string representation of the time) which can be turned into JSON and sent to the client.

`ClockMaker.java` has just one method which supplies a new clock with (by default) the current server time.

`AppController.java` Contains two routed, one for the clock display page, and one for the `/topic/clock` endpoint.

```
@Controller
public class AppController {
    ...
	@MessageMapping("/clock")
	@SendTo("/topic/clock")
	public Clock time() {
		return clockMaker.makeClock();
	}
}
```

`ClockAutoUpdate.java` is a Spring scheduled event which pushes to the topic `/topic/clock`.  
```
@Component
@EnableScheduling
public class ClockAutoUpdate {
    ...
	@Scheduled(fixedDelay=5_000)
	public void tick() {
		simpMessagingTemplate.convertAndSend("/topic/clock", clockMaker.makeClock() ); 
	}
}

```

`WebSocketConfig.java` sets up the web socket message broker.

```
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/topic");
		registry.setApplicationDestinationPrefixes("/app");
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("websocket-clock").withSockJS();
	}
}
```

## The HTML and Javascript

`home.html` is the web-page which displays the clock.  It includes a placeholder to display the time `<span id="clock"></span>` and a button to force an update `<button id="update" class="btn btn-primary">Force Update</button>`.

It also includes the Javascript libraries needed by the web socket, and links to our custom script which links up to our websocket for the clock.

```
	<script src="/webjars/sockjs-client/sockjs.min.js"></script>
	<script src="/webjars/stomp-websocket/stomp.min.js"></script>
	<script src="/js/home.js"></script> 
```

(The other libraries loaded are for Bootstrap 4, a GUI presentation library.)

`home.js` calls `connect()` when the web page is loaded. That function subscribes to the topic `/topic/clock` and calls `updateClock()` passing in the decoded JSON payload containing the server time.

```
function connect() {
	var socket = new SockJS('/websocket-clock');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		stompClient.subscribe('/topic/clock', function(message) {
			updateClock(JSON.parse(message.body).time);
		});
		...
	});
}
```

The update button is wired up to send a request to `/app/clock` (passing an empty JSON body) which the server will respond to by an additional broadcast of the time on the `/topic/clock` topic.

```
stompClient.send("/app/clock", {}, JSON.stringify({}));
```

### References

- [WebSocket Protocol Wikipedia article](https://en.wikipedia.org/wiki/WebSocket)
- [Spring WebSocket getting started](https://spring.io/guides/gs/messaging-stomp-websocket/)
