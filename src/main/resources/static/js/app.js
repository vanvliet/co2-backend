let stompClient = null;

let roomsList = null;

function connect() {

    const socket = new SockJS('/api/v1/ws/room-conditions');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/room-message', function (messageOutput) {
            upsertRoom(JSON.parse(messageOutput.body));
        });
    });
}

function upsertRoom(room) {
    const li = document.getElementById(room.name) ? document.getElementById(room.name) : createLiItem(room.name)
    li.innerHTML = room.name + ", CO2: " + room.condition.co2;
}

function createLiItem(id) {
    const roomsListUl = document.getElementById('roomsList');
    li = document.createElement('li')
    li.setAttribute("id", id);
    roomsListUl.appendChild(li);
    return li;
}

const fetchRooms = async () => {
    const response = await fetch('http://localhost:8080/api/v1/rooms');
    roomsList = await response.json(); //extract JSON from the http response
    roomsList.map(room => upsertRoom(room));
}
