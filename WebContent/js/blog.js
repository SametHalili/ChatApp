deleteCookie("loggedIn");

function deleteCookie(name) //deletes the cookie on call
{
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/;';
}

var webSocket;

var backB = document.getElementById("backB")
backB.onclick = closeSocket;

var sendB = document.getElementById("postCommentB")
sendB.onclick = send;

window.onload = openSocket;

function openSocket()
{
    webSocket = new WebSocket("ws://localhost:8080/comment");

    webSocket.onmessage = function(event)
    {
        writeResponse(event.data);
    };
}

function send()
{
    var text = document.getElementById("comment").value;
    webSocket.send(text);
}

function closeSocket()
{
    webSocket.close();
}

function writeResponse(text)
{
    var commentSection = document.getElementById("commentSection");
    var commentDiv = document.createElement("div");
    var anonName = document.createTextNode("Username: Anon");
    var comment = document.createTextNode("Message: " + text);

    var elementComment = document.createElement("p");
    var elementName = document.createElement("p");

    commentDiv.id = "card";

    elementName.appendChild(anonName);
    elementComment.appendChild(comment);
    commentDiv.appendChild(anonName);
    commentDiv.appendChild(elementComment);
    commentSection.appendChild(commentDiv);
}