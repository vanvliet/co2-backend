let stompClient = null;
let roomsList = null;

$slb = $("#sensor-list-accordion")


const connect = () => {
    const socket = new SockJS('/ws/room-conditions');
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
    const response = await fetch(window.location.origin + '/rooms');
    roomsList = await response.json();
    roomsList.map(room => upsertRoom(room));
}

const getBgColour = (c02Value) => {
    return !c02Value ? "grey" :
        c02Value < 500 ? "ligthgreen" :
            c02Value < 800 ? "green" :
                c02Value < 1000 ? "orange" : "red"

}

const upsertRoom = (room) => {
    const li = document.getElementById(room.name) ? document.getElementById(room.name) : createListItem("roomsList", room)

    const roomIndex = roomsList.findIndex(r => r.name === room.name)
    roomIndex === -1 ? roomsList.push(room) : roomsList[roomIndex] = room;

    const bgcolour = getBgColour(room.condition.co2)
    li.setAttribute("class", `list-group-item ${bgcolour}`);

    const conditionCO2 = room.condition.co2 ? room.condition.co2 : "-"
    li.innerHTML = room.name + ": " + conditionCO2

    const selectedRoom = document.getElementById("kamerNaam").innerHTML
    console.log(selectedRoom + ", " + room.name)
    if (selectedRoom === room.name) {
        setRoomValues(room)
    }
}

const setRoomValues = (room) => {
    console.log("in setRoomValues: " + room.name)
    document.getElementById("kamerNaam").innerHTML = room.name;
    document.getElementById("kamerCO2").innerHTML = room.condition.co2 ? room.condition.co2 : "-";
    document.getElementById("kamerHumidity").innerHTML = room.condition.humidity ? room.condition.humidity : "-";
    document.getElementById("kamerTemp").innerHTML = room.condition.temperature ? room.condition.temperature : "-";
    document.getElementById("kamer").setAttribute("class", `${getBgColour(room.condition.co2)}`);
}

const fetchSensors = async () => {
    let list = document.getElementById('sensorsList');
    while (list.firstChild) {
        list.removeChild(list.firstChild);
    }
    const response = await fetch(window.location.origin + '//sensors');
    sensorsList = await response.json();
    sensorsList.map(sensor => upsertSensor(sensor));
    $("#sensorsList li").size() === 0 ? $slb.hide() : $slb.show()

}

const upsertSensor = (sensor) => {
    const li = document.getElementById(sensor.name) ? document.getElementById(sensor.name) : createListItem("sensorsList", sensor)
    const sensorName = document.createElement('p')
    sensorName.innerHTML = sensor.name
    const sensorForm = document.createElement('form')
    sensorForm.setAttribute("class", "form-inline")
    sensorForm.setAttribute('id', "sensor-form-" + sensor.name)
    const sensorInput = document.createElement('input')
    sensorInput.setAttribute("class", 'form-control')
    sensorInput.setAttribute('id', "sensor-input-" + sensor.name)
    sensorInput.setAttribute('placeholder', "Kamer naam")
    sensorForm.appendChild(sensorInput)
    li.appendChild(sensorName)
    // $("#"+sensor-form-"+sensor.name").hide()
    li.appendChild(sensorForm)
    $slb.show()
}

const createListItem = (elementId, item) => {
    const roomsListUl = document.getElementById(elementId);
    li = document.createElement('li')
    li.setAttribute("id", item.name);
    li.setAttribute("class", "list-group-item");
    roomsListUl.appendChild(li);
    li.addEventListener("click", function (e) {
        const clicked = document.getElementById(e.target.id);
        const roomIndex = roomsList.findIndex(r => r.name === e.target.id)
        console.log(roomIndex)
        if (roomIndex !== -1) {
            setRoomValues(roomsList[roomIndex]);
        }
    });
    return li;
}
$(document).ready(function () {
    $(".toggle-sidebar").click(function () {
        $("#sidebar").toggleClass("sideBarHide sideBarShow");
        $("#sidebar-arrow").toggleClass("bi-arrow-left bi-arrow-right");
        $("#content").toggleClass("col-md-12 col-md-9");
        return false;
    });
});
