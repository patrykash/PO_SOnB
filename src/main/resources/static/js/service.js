'use strict';
window.onload = init;
let serverId=0;
let serverError = false;

function startServer() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/server/start', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log("dziala")
        }
    };
}

function runClients(){
    console.log(serverError)
    console.log(serverId)
    if (serverError){
        reconnectClients(serverId)
    } else {
        startClients()
    }
}

function startClients() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/client/start', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImages(true)
            console.log("dziala")
        }
    };
}

function reconnectClients(clientId) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/client/connect/' + clientId, true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImages(true)
            console.log("dziala")
        }
    };
}


function sendMessage(message) {
    let send = new XMLHttpRequest();
    send.open('POST', '/server/send', true);
    send.setRequestHeader("Content-Type", "text/plain");
    send.send(message);
    //send.send(message);
    send.onload = function () {
        console.log("wysylanie")
        getAnswers()
        /*if(send.status )
        {
            console.log(send.responseText) ;

        }*/
    };
}

function getAnswers() {
    let getQuestion = new XMLHttpRequest();
    getQuestion.open('GET', '/client/read', true);
    getQuestion.send(null);
    getQuestion.onload = function () {
        console.log(JSON.parse(getQuestion.responseText))
        setAnswers(JSON.parse(getQuestion.responseText))
        console.log("odboieranie wiadomosci")
    };
}

function setAnswers(answers){
    for (let i = 0; i < answers.length; i++) {
        document.getElementById("client-message-"+ i).textContent = answers[i].message
        document.getElementById("code-message-"+ i).textContent = answers[i].bergerCode
        console.log(answers[i].correct)
        if (answers[i].correct){
            document.getElementById("client-correction-"+ i).textContent = 'Poprawna wiadomość'
        } else{
            document.getElementById("client-correction-"+ i).textContent = 'Blędna wiadomość'
        }
    }
}
function getMessage() {
    let message = document.getElementById("serverMessage").value.toString();
    console.log("wiadomosc z inputa : " + message);
    console.log("wiadomosc z inputa : " + message.length);
    sendMessage(message);
}

function runErrorWithServer() {
    serverId=1;
    serverError = true;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/server', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log("/error/server")
        }
    };
}

function runErrorWithCoding() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/code', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log("/error/code")
        }
    };
}

function runErrorWithClient() {
    let clientId = 1
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/client/'+clientId, true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImage(false, clientId)
            console.log('/error/client/'+clientId)
        }
    };
}

function fixErrorWithServer() {
    serverId =1;
    let clientId = 1
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/server/fix/'+clientId, true);
    xhr.send(null)
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log('/error/server/fix/'+clientId)
        }
    };
}

function fixErrorWithCoding() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/code/fix', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log('/error/code/fix')
        }
    };
}

function fixErrorWithClient() {
    let clientId = 1
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/client/fix/'+clientId, true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImage(true, clientId)
            console.log('/client/fix/'+clientId)
        }
    };
}

function setStatusImages(isOn){
    let images =  document.getElementsByClassName("status-img")
    for (let image of images) {
        if (isOn){
            image.src = '../img/online.svg'
        } else {
            image.src = '../img/offline.svg'
        }

    }
}
function setStatusImage(isOn, clientId){
    let images =  document.getElementsByClassName("status-img")
    if (isOn){
        images[clientId].src = '../img/online.svg'
    } else {
        images[clientId].src = '../img/offline.svg'
    }
}
