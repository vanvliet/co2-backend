var stompClient = null;
var roomsList = null;
var intervalId = null;

const connect = () => {
    const socket = new SockJS('/ws/room-conditions');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function () {
        stompClient.subscribe('/topic/room-message', function (messageOutput) {
            const message = JSON.parse(messageOutput.body)
            upsertRoom(message);
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
    if (selectedRoom === room.name) {
        setRoomValues(room)
    }
}

const setRoomValues = (room) => {
    document.getElementById("kamerNaam").innerHTML = room.name;
    document.getElementById("kamerCO2").innerHTML = room.condition.co2 ? room.condition.co2 : "-";
    document.getElementById("kamerHumidity").innerHTML = room.condition.humidity ? room.condition.humidity : "-";
    document.getElementById("kamerTemp").innerHTML = room.condition.temperature ? room.condition.temperature : "-";
    document.getElementById("kamer").setAttribute("class", `${getBgColour(room.condition.co2)}`);
    updateTicker(room);
    if (intervalId) {
        clearInterval(intervalId)
    }
    if (isValidDate(new Date(room.condition.lastUpdate))) intervalId = setInterval(updateTicker, 1000, room);
}

function isValidDate(d) {
    return d instanceof Date && !isNaN(d);
}

const updateTicker = (room) => {
    let lastUpdate = new Date(room.condition.lastUpdate)
    let now = new Date
    let sinceLastUpdate = Math.round((now.getTime() - lastUpdate.getTime()) / 1000)
    document.getElementById("lastUpdate").innerHTML =
        ! isValidDate(lastUpdate) ? 'nog geen informatie onvangen' :
        sinceLastUpdate < 3600 ? sinceLastUpdate + 's' : '> 1h ago';
}

const createListItem = (elementId, item) => {
    const roomsListUl = document.getElementById(elementId);
    li = document.createElement('li')
    li.setAttribute("id", item.name);
    li.setAttribute("class", "list-group-item");
    roomsListUl.appendChild(li);
    li.addEventListener("click", function (e) {
        const roomIndex = roomsList.findIndex(r => r.name === e.target.id)
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
