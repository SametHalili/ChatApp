var chgStatusRqst = new XMLHttpRequest();
var addFriendRqst = new XMLHttpRequest();
var getFriendListRqst = new XMLHttpRequest();

getFriendListRecursive();

var newStatusB = document.getElementById("chgStatusB");
newStatusB.onclick = changeStatus;

var addFriendB = document.getElementById("addFriendB");
addFriendB.onclick = addFriend;

var logoutB = document.getElementById("logoutbutton");
logoutB.onclick = setOffline;

window.onload = start;


//UserStatus functions
function changeStatus() //changes status based on what the user inputs on the website
{
    var newStatusValue = document.getElementById("newStatus").value;

    var information = "newStatus=" + encodeURIComponent(newStatusValue);

    chgStatusRqst.open("POST", "Controller?asyncAction=changeStatus", true);
    chgStatusRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    chgStatusRqst.send(information);

    var currentStatus = document.getElementById("currStat");
    if (newStatusValue == "") {
        currentStatus.innerHTML = "online"
    }
    else {
        currentStatus.innerHTML = newStatusValue;
    }
}

function changeStatusWithStr(userStatus) //change status via the console if the user wants to
{
    var newStatusStr = userStatus.toString();
    var information = "newStatus=" + encodeURIComponent(newStatusStr);
    chgStatusRqst.open("POST", "Controller?asyncAction=changeStatus", true);
    chgStatusRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    chgStatusRqst.send(information);
    console.log(chgStatusRqst)
    var currentStatus = document.getElementById("currStat");
    if (newStatusStr === "") {
        currentStatus.innerHTML = "online"
    }
    else {
        currentStatus.innerHTML = newStatusStr;
    }
}

function setOnline() //sets the user as online properly and sets the created cookie as true
{

    if (document.cookie.toString() === "loggedIn=true")
        return;
    else {
        changeStatusWithStr("online");
        setCookie(true)
    }
}

function setOffline() {
    changeStatusWithStr("offline")
    deleteCookie("loggedIn")
}

function setCookie(boolLoggedIn) //creates a simple cookie with only one value; loggedIn
{                                //this allows us to properly know if the user is logged in or not
    document.cookie = "loggedIn=" + boolLoggedIn.toString()
}

function deleteCookie(name) //deletes the cookie on call
{
    document.cookie = name + '=; expires=Thu, 01 Jan 1970 00:00:01 GMT; path=/;';
}

function start() //for body onload
{
    if (document.cookie.toString() != "loggedIn=true") {
        setOnline();
        changeStatusWithStr("online");
    }

}

//Friendlist functions
function addFriend() //adds friend
{
    var username1 = document.getElementById("mainUser").textContent;
    var username2 = document.getElementById("newFriend").value;
    if (username2 == "") {
        alert("Empty username!")
    }
    else {
        var information = "username1=" + encodeURIComponent(username1) +
            "&username2=" + encodeURIComponent(username2)

        addFriendRqst.open("POST", "Controller?asyncAction=addFriend", true);
        addFriendRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        addFriendRqst.send(information);
        getFriendList()
    }
}


function getFriendList() {
    var currUsername = document.getElementById("mainUser").textContent;
    var information = "&username=" + encodeURIComponent(currUsername);
    getFriendListRqst.open("GET", "Controller?asyncAction=getFriends" + information, true)
    getFriendListRqst.onreadystatechange = getFriendListData;
    getFriendListRqst.send();
}

function getFriendListData() {
    if (getFriendListRqst.readyState == 4) {
        if (getFriendListRqst.status == 200) {
            var serverResponse = JSON.parse(getFriendListRqst.responseText);
            var friendList = serverResponse;
            var friendListTableBody = document.getElementById("friendList");
            while (friendListTableBody.firstChild) {
                friendListTableBody.removeChild(friendListTableBody.firstChild);
            }

            for (var i = 0; i < friendList.length; i++) {
                var username = friendList[i]["userId"];
                var status = friendList[i]["statusStr"];

                var friendListTableRow = document.createElement("tr");
                friendListTableRow.id = "tableRow" + i;

                var friendListTableDataUsername = document.createElement("td");
                friendListTableDataUsername.innerHTML = username;

                var friendListTableDataStatus = document.createElement("td");
                friendListTableDataStatus.innerHTML = status;

                var friendsListTableDataBtnChat = document.createElement("td");
                var btnChat = document.createElement('button');
                btnChat.setAttribute("type", "button");
                btnChat.setAttribute("id", "chat" + username);
                btnChat.setAttribute("onclick", "createChatForm(\"" + username + "\")");
                btnChat.appendChild(document.createTextNode("Chat with this user"));
                friendsListTableDataBtnChat.appendChild(btnChat);

                friendListTableRow.appendChild(friendListTableDataUsername);
                friendListTableRow.appendChild(friendListTableDataStatus);
                friendListTableRow.appendChild(friendsListTableDataBtnChat);

                friendListTableBody.appendChild(friendListTableRow);
            }
        }
    }
}

function createChatForm(userId) {
    if (document.getElementById("chatMsgs" + userId) == null) {
        console.log("creating chat form")
        var chatBody = document.getElementById("chatBody");
        var chatAll = document.createElement('div');
        var chatMsgs = document.createElement('div');
        var chatUser = document.createElement('h4');
        var msg = document.createElement('div');
        var msgInput = document.createElement('input');
        var msgSend = document.createElement('button');
        var removeForm = document.createElement('button');

        removeForm.setAttribute("id", "deleteChatForm");
        removeForm.appendChild(document.createTextNode("Close"));
        removeForm.setAttribute("onclick", "removeElements(\"" + userId + "\")");


        chatAll.setAttribute("id", "chatAll" + userId);
        chatMsgs.setAttribute("id", "chatMsgs" + userId);
        chatMsgs.setAttribute("class", "pre-scrollable");
        chatUser.setAttribute("id", "chatUser" + userId);
        chatUser.setAttribute("class", "chatUser" + userId);
        msg.setAttribute("id", "msg" + userId);
        msgInput.setAttribute("id", "msgInput" + userId);
        msgSend.setAttribute("id", "msgInput");

        msgInput.setAttribute("type", "text");

        msgSend.setAttribute("onclick", "SendMessage(\"" + userId + "\")");
        msgSend.appendChild(document.createTextNode("Send!"));


        chatUser.appendChild(document.createTextNode("Chatting with: " + userId));

        chatAll.appendChild(chatUser);

        chatMsgs.appendChild(msg);

        chatAll.appendChild(chatMsgs);
        chatAll.appendChild(msgInput);
        chatAll.appendChild(msgSend);
        chatAll.appendChild(removeForm);
        chatBody.appendChild(chatAll);
        chatBody.appendChild(document.createElement('br'));
        getMessage(userId);
    }
}

function removeElements(userId) {
    document.getElementById("chatBody").removeChild(document.getElementById("chatAll" + userId));
}


function getMessage(currentUser) {
    $.ajax({
        type: "get",
        url: "./Controller?asyncAction=chatConversationGet",
        dataType: "json",
        success: function (chatConversations) {
            console.log(chatConversations);
            if (chatConversations.chatConversations.length > 0) {
                var chatConv = chatConversations.chatConversations;
                var toUser;
                var msgDiv;
                for (var i = 0; i < chatConv.length; i++) {
                    if(chatConversations.chatConversations[i].userId1 === currentUser
                        || chatConversations.chatConversations[i].userId2 === currentUser){
                        console.log("USER1: " + chatConversations.chatConversations[i].userId1);
                        console.log("USER2: " + chatConversations.chatConversations[i].userId2);
                        console.log("CURRENTLY LOGGED IN: " + chatConversations.loggedin);
                        if (chatConversations.chatConversations[i].userId1 != chatConversations.loggedin) {
                            toUser = chatConversations.chatConversations[i].userId1;
                            console.log("toUser: " +toUser);
                            msgDiv = document.getElementById("msg" + toUser);
                            console.log(msgDiv.id);
                            while (msgDiv.firstChild) {
                                console.log("if: deleting msg and the user is chatting to " + toUser);
                                msgDiv.removeChild(msgDiv.firstChild);
                            }
                        }
                        else {
                            toUser = chatConversations.chatConversations[i].userId2;
                            console.log("toUser: " +toUser);
                            msgDiv = document.getElementById("msg" + toUser);
                            console.log(msgDiv.id);
                            while (msgDiv.firstChild) {
                                console.log("else: deleting msg and the user is chatting to " + toUser);
                                msgDiv.removeChild(msgDiv.firstChild);
                            }
                        }

                        var msgs = chatConversations.chatConversations[i].messages;
                        console.log(msgs);
                        for (var j = 0; j < msgs.length; j++) {
                            var msgP = document.createElement('p');
                            msgP.appendChild(document.createTextNode("[" + msgs[j].dateTime + "]"));
                            msgP.appendChild(document.createElement('br'));
                            msgP.appendChild(document.createTextNode(msgs[j].sender + ": " + msgs[j].msg));
                            msgDiv.appendChild(msgP);
                        }
                        break;
                    }
                }
            }
            setTimeout(function(){
                getMessage(currentUser)
            }, 5000);
        }
    })
}

function SendMessage(userId) {
    $.ajax({
        type: "post",
        url: "./Controller?asyncAction=chatConversationSend",
        data: {
            userId: userId,
            msg: document.getElementById("msgInput" + userId).value
        },
        dataType: "json"
    });
}


function getFriendListRecursive() {
    getFriendList();

    setTimeout(getFriendListRecursive, 5000);
}

//jQuery
$(document).ready(function () {
    $("#toggleFriendList").click(function () {
        $("#tableFriendlist").toggle(1000);
    })
});


