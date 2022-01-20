'use strict';
window.onload = init;
let serverId=-1;
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
        setClientIdInServerStatus(getActiveClientsId())
        getClientsMessageStatus()
        /*if(send.status )
        {
            console.log(send.responseText) ;

        }*/
    };
}

function getActiveClientsId() {
    let clientsId = " ";
    for (let i = 0; i < 7; i++) {
        if (serverId !== i) {
            clientsId = clientsId + i + ",";
        }
    }
    return clientsId;
}
function setClientIdInServerStatus(clientsId) {
    let serverSendMessageContent = document.getElementById('serverSendMessage');
    serverSendMessageContent.textContent = "Wiadomość wysłana do :" + clientsId;


}

function getClientsMessageStatus() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/server/read', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            let messageStatus = JSON.parse(xhr.responseText)
            console.log("messageStatus: " + messageStatus[0])
            setMessageStatus(messageStatus)
            if (isError(messageStatus)){
                console.log("resedn");
                reSendMessage(messageStatus)
            }
            console.log("getClientsMessageStatus")
        }
    };
}
function isError(messageStatus){
    for (let i = 0; i < messageStatus.length; i++) {
        console.log("isError")
        console.log(messageStatus[i])
        if (messageStatus[i] === "ERROR") {
            return true;
        }
    }
    return false;
}
function setMessageStatus(messageStatus) {
    let messageStatusContent = ' ';
    for (let i = 0; i < messageStatus.length; i++) {
        if (messageStatus[i] === 'ERROR') {
            messageStatusContent = messageStatusContent + i + ', '
        }

    }
    messageStatusContent = messageStatusContent.substring(0, messageStatusContent.length -1 )
    let reMessage = document.getElementById('reMessage');
    messageStatusContent ="Prosba o retransmisje wiadomośći dla:" + messageStatusContent;
    reMessage.textContent = messageStatusContent;
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
        if (i===serverId){
            continue
        }
        document.getElementById("client-message-"+ i).textContent = answers[i].message
        document.getElementById("code-message-"+ i).textContent = answers[i].bergerCode
        console.log(answers[i].correct)
        setCorrectness(answers[i].correct, i)
    }
}

function getAnswer(clientId) {
    let getQuestion = new XMLHttpRequest();
    getQuestion.open('GET', '/client/read/'+clientId, true);
    getQuestion.send(null);
    getQuestion.onload = function () {
        console.log(JSON.parse(getQuestion.responseText))
        setAnswer(JSON.parse(getQuestion.responseText),clientId)
        console.log("odboieranie wiadomosci")
    };
}

function setAnswer(answer, clientId){
    document.getElementById("client-message-"+ clientId).textContent = answer.message
    document.getElementById("code-message-"+ clientId).textContent = answer.bergerCode
    console.log(answer.correct)
    setCorrectness(answer.correct, clientId)
}

function setCorrectness(isCorrect, clientId){
    if (isCorrect){
        document.getElementById("client-correction-"+ clientId).textContent = 'Poprawna wiadomość'
    } else{
        document.getElementById("client-correction-"+ clientId).textContent = 'Blędna wiadomość'
    }
}
function getMessage() {
    let message
    if(serverError){
        message = document.getElementById("tempServerMessage").value.toString();
    } else {
        message = document.getElementById("serverMessage").value.toString();
    }

    console.log("wiadomosc z inputa : " + message);
    console.log("wiadomosc z inputa : " + message.length);
    sendMessage(message);
}

function runErrorWithServer() {
    //serverId=1;
    document.getElementById("sendMessage").removeEventListener("click", getMessage)
    serverError = true;
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/server', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImage(false,0)
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

function runErrorWithMessage() {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/message', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log("/error/message")
        }
    };
}

function runErrorWithClient() {
    let clientId = getWorkingClientId()
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/client/'+clientId, true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log(clientId+1)
            setStatusImage(false, Number(clientId)+1)
            console.log('/error/client/'+clientId)
        }
    };
}

function getWorkingClientId(){
    return document.getElementById('workingClient').value.toString();
}

function fixErrorWithServer() {
    let clientId = getTempServerId();
    serverId =Number(clientId);
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/server/fix/'+clientId, true);
    xhr.send(null)
    xhr.onload = function () {
        if (xhr.status === 200){
            clearClientServerBody(clientId)
            createTemporaryMainServerBody(clientId)
            console.log('/error/server/fix/'+clientId)
        }
    };
}

function getTempServerId() {
    return document.getElementById('tempServer').value.toString();
}

function fixErrorWithCoding() {
    let xhr = new XMLHttpRequest();
    setStatusImages(false)
    sleep(2000).then(r => setStatusImage(true,0));

    xhr.open('GET', '/error/code/fix', true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            console.log('/error/code/fix')
        }
    };
}

function fixErrorWithClient() {
    let clientId = getBrokenClientId()
    let xhr = new XMLHttpRequest();
    xhr.open('GET', '/error/client/fix/'+clientId, true);
    xhr.send(null);
    xhr.onload = function () {
        if (xhr.status === 200){
            setStatusImage(true, Number(clientId)+1)
            console.log('/client/fix/'+clientId)
        }
    };
}
function getBrokenClientId() {
    return document.getElementById('brokenClientId').value.toString();
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
    if (serverError){
        images[0].src = '../img/offline.svg'
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

function clearClientServerBody(clientId){
    let clearedClient = document.getElementsByClassName('client-server')[clientId]
    while (clearedClient.childElementCount>1){
        clearedClient.children[1].remove()
    }
}

function createTemporaryMainServerBody(clientId){
    let tempLabel = document.createElement('label')
    tempLabel.setAttribute('for', 'tempServerMessage')
    tempLabel.textContent = 'Wiadomość :'
    tempLabel.style.marginTop = '15px'
    let tempInput = document.createElement('input')
    tempInput.setAttribute('id','tempServerMessage')
    tempInput.setAttribute('maxlength', '2')
    tempInput.classList.add('server-message')
    /*tempInput.style.marginTop = '15px'*/
    let tempButton = document.createElement('button')
    tempButton.setAttribute('id','tempSendMessage')
    tempButton.innerText = 'Wyślij'
    tempButton.classList.add('send-button')
    /*tempButton.style.marginTop = '15px*/
    tempButton.addEventListener("click",getMessage)
    let clearedClient = document.getElementsByClassName('client-server')[clientId]
    clearedClient.appendChild(tempLabel)
    clearedClient.appendChild(tempInput)
    clearedClient.appendChild(tempButton)

}

function reSendMessage(messageStatus){
    let test =document.getElementById('serverStatusMessage');
    let test2 = 'Status wiadomości: ';
    for (let i = 6; i > 0; i--) {
        if (i === 6) {
            sleep(1000 * i).then( () => reSend())
        }else {
            sleep(1000 * i).then( () => updateTime(6 - i))
        }


    }

    async function updateTime(time){
        test.textContent = test2 + 'Retransmiasja wiadomości za: ' + time + 'sekund'
        console.log(time)
    }

    function reSend() {
        for (let i = 0; i < messageStatus.length; i++) {
            console.log("messageStatus.length " + messageStatus.length)
            if (messageStatus[i] === "ERROR") {
                if (serverId > -1 && serverId<= i){
                    getAnswer(i+1)
                }else {
                    getAnswer(i);
                }

            }
        }
        test.textContent = test2 + 'Wiadomosc wysłana'
    }

}




function sleep(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}