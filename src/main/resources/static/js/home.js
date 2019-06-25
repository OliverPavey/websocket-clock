/**
 * Link up to clock update notifications from server
 */

var stompClient = null;

/**
 * Update the clock in the web page display.
 * 
 * @param time	A string containing the time to show on the display.
 */
function updateClock(time) {
	$("#clock").text(time);
}

/**
 * Connect up to websocket, and wire in function to respond to '/topic/clock' message broadcasts
 */
function connect() {
	// Connect to websocket '/websocket-clock' (on same server that delivered webpage)
	var socket = new SockJS('/websocket-clock');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function(frame) {
		// Subscribe to '/topic/clock' update broadcasts
		stompClient.subscribe('/topic/clock', function(message) {
			updateClock(JSON.parse(message.body).time);
		});
		// Force immediate update on first connection
		forceUpdate(); 
	});
}

/**
 * Invoke an update of the clock on the display now (by asking server to broadcast time to all clients now). 
 */
function forceUpdate() {
	stompClient.send("/app/clock", {}, JSON.stringify({}));
}

/**
 * Start-up code to run when web page has loaded. 
 */
$(function() {
	// Connect 
	connect();
	// Call forceUpdate() when "Force Update" button is clicked in browser.
	$( "#update" ).click(function() { forceUpdate(); });
})