function init(){

    document.getElementById("startServer").addEventListener("click",startServer);
    document.getElementById("startClients").addEventListener("click",startClients)
    document.getElementById("sendMessage").addEventListener("click", getMessage)
    /*window.sessionStorage.setItem("userId", "1");
    window.addEventListener("resize", setHeight);*/
}