var stompClient = null;


function connectSocket() {
    var websocketPath = "/websocket";
    if (window.location.pathname.startsWith('/event')) {
        websocketPath = "/event/websocket";
    }
    var socket = new SockJS(websocketPath);    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/tenant/0', function (socketMessage) {
            console.log("Got Socket Message : " + JSON.parse(socketMessage.body).message);
        });
    });
}

function disconnectSocket() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}

function createPatient() {
    stompClient.send("/messages/create-patient", {}, "");
}

function createObservation() {
    stompClient.send("/messages/create-observation", {}, "");
}



