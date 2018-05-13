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

    chgStatusRqst.open("POST", "AsyncController?action=changeStatus", true);
    chgStatusRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    chgStatusRqst.send(information);

    var currentStatus = document.getElementById("currStat");
    if(newStatusValue == "")
    {
        currentStatus.innerHTML = "online"
    }
    else
    {
        currentStatus.innerHTML = newStatusValue;
    }
}

function changeStatusWithStr(userStatus) //change status via the console if the user wants to
{
    var newStatusStr = userStatus.toString();
    var information = "newStatus=" + encodeURIComponent(newStatusStr);
    chgStatusRqst.open("POST", "AsyncController?action=changeStatus", true);
    chgStatusRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
    chgStatusRqst.send(information);
    console.log(chgStatusRqst)
    var currentStatus = document.getElementById("currStat");
    if(newStatusStr === "")
    {
        currentStatus.innerHTML = "online"
    }
    else
    {
        currentStatus.innerHTML = newStatusStr;
    }
}

function setOnline() //sets the user as online properly and sets the created cookie as true
{

    if(document.cookie.toString() === "loggedIn=true")
        return;
    else
    {
        changeStatusWithStr("online");
        setCookie(true)
    }
}

function setOffline()
{
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
    if(document.cookie.toString() != "loggedIn=true")
    {
        setOnline();
        changeStatusWithStr("online");
    }

}

//Friendlist functions
function addFriend() //adds friend
{
    var username1 = document.getElementById("mainUser").textContent;
    var username2 = document.getElementById("newFriend").value;
    alert(username2)
    if(username2 == "")
    {
       alert("Wrong user!")
    }
    else
    {

        var information = "username1=" + encodeURIComponent(username1) +
            "&username2=" + encodeURIComponent(username2)

        addFriendRqst.open("POST", "AsyncController?action=addFriend", true);
        addFriendRqst.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        addFriendRqst.send(information);
        console.log(addFriendRqst)
        getFriendList()
    }
}

function getFriendList()
{
    var currUsername = document.getElementById("mainUser").textContent;
    var information = "&username=" + encodeURIComponent(currUsername);
    getFriendListRqst.open("GET", "AsyncController?action=getFriends" + information, true)
    getFriendListRqst.onreadystatechange = getFriendListData;
    getFriendListRqst.send();
    console.log(getFriendListRqst)
}

function getFriendListData()
{
    if (getFriendListRqst.readyState == 4)
    {
        if (getFriendListRqst.status == 200)
        {
            var serverResponse = JSON.parse(getFriendListRqst.responseText);
            var friendList = serverResponse;
            var friendListTableBody = document.getElementById("friendList");
            while (friendListTableBody.firstChild)
            {
                friendListTableBody.removeChild(friendListTableBody.firstChild);
            }

            for (var i = 0; i < friendList.length; i++)
            {
                var username = friendList[i]["userId"];
                var status = friendList[i]["statusStr"];

                var friendListTableRow = document.createElement("tr");

                var friendListTableDataUsername = document.createElement("td");
                friendListTableDataUsername.innerHTML = username;

                var friendListTableDataStatus = document.createElement("td");
                friendListTableDataStatus.innerHTML = status;

                friendListTableRow.appendChild(friendListTableDataUsername);
                friendListTableRow.appendChild(friendListTableDataStatus);

                friendListTableBody.appendChild(friendListTableRow);
            }
        }
    }
}

function getFriendListRecursive()
{
    getFriendList();

    setTimeout(getFriendListRecursive, 5000);
}