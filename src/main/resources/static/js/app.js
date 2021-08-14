let stompClient = null;

let roomsList = null;
let sensorList = null;

const connect = () => {
    const socket = new SockJS('/api/v1/ws/room-conditions');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/room-message', function (messageOutput) {
            const message = JSON.parse(messageOutput.body)
            message.isRoom ? upsertRoom(message) : upsertSensor(message);
        });
    });
}

const fetchRooms = async () => {
    const response = await fetch(window.location.origin + '/api/v1/rooms');
    roomsList = await response.json();
    roomsList.map(room => upsertRoom(room));
}

const upsertRoom = (room) => {
    const li = document.getElementById(room.name) ? document.getElementById(room.name) : createListItem("roomsList", room.name)
    const conditionText = room.condition.co2 ? room.condition.co2 : "-"
    li.innerHTML = room.name + ": " + conditionText
}

const fetchSensors = async () => {
    const response = await fetch(window.location.origin + '/api/v1/sensors');
    sensorsList = await response.json();
    sensorsList.map(sensor => upsertSensor(sensor));
}

const upsertSensor = (sensor) => {
    const li = document.getElementById(sensor.name) ? document.getElementById(sensor.name) : createListItem("sensorsList", sensor.name)
    li.innerHTML = sensor.name
}

const createListItem = (elementId, id) => {
    const roomsListUl = document.getElementById(elementId);
    li = document.createElement('li')
    li.setAttribute("id", id);
    li.setAttribute("class", "list-group-item");
    roomsListUl.appendChild(li);
    return li;
}
