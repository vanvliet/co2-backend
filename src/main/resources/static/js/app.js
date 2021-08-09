let stompClient = null;

function setConnected(connected) {

    document.getElementById('connect').disabled = connected;
    document.getElementById('disconnect').disabled = !connected;
    document.getElementById('conversationDiv').style.visibility = connected ? 'visible' : 'hidden';
    document.getElementById('response').innerHTML = '';
}

function connect() {

    const socket = new SockJS('/api/v1/ws/room-conditions');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/room-message', function (messageOutput) {

            showMessageOutput(JSON.parse(messageOutput.body));
        });
    });
}

function disconnect() {

    if (stompClient != null) {
        stompClient.disconnect();
    }

    setConnected(false);
    console.log("Disconnected");
}

function showMessageOutput(messageOutput) {

    const response = document.getElementById('response');
    const p = document.createElement('pre');
    p.innerHTML = JSON.stringify(messageOutput, undefined, 2);
    response.appendChild(p);
}
