'use strict';
window.onload = init;


function startServer() {
        let getQuestion = new XMLHttpRequest();
        getQuestion.open('GET', '/server/start', true);
        getQuestion.send(null);
        getQuestion.onload = function () {
            console.log("dziala")
        };
}

function startClients() {
    let getQuestion = new XMLHttpRequest();
    getQuestion.open('GET', '/client/start', true);
    getQuestion.send(null);
    getQuestion.onload = function () {
        console.log("dziala")
    };
}

function sendMessage(message) {
    let send = new XMLHttpRequest();
    send.open('POST', '/server/send', true);
    send.setRequestHeader("Content-Type", "application/json");
    send.send(JSON.stringify(message));
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
        document.getElementById("client-message-"+ i).textContent = answers[i]
    }
}
function getMessage() {
    let message = document.getElementById("serverMessage").textContent;
    sendMessage(message);
}


