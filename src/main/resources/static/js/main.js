
function init(){

    document.getElementById("startServer").addEventListener("click",startServer);
    document.getElementById("startClients").addEventListener("click",runClients)
    document.getElementById("sendMessage").addEventListener("click", getMessage)
    document.getElementById("serverError").addEventListener("click", runErrorWithServer)
    document.getElementById("messageError").addEventListener("click", runErrorWithMessage)
    document.getElementById("clientError").addEventListener("click", runErrorWithClient)
    document.getElementById("serverFix").addEventListener("click", fixErrorWithServer)
    document.getElementById("clientFix").addEventListener("click", fixErrorWithClient)
    /*window.sessionStorage.setItem("userId", "1");
    window.addEventListener("resize", setHeight);*/
}