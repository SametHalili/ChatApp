<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Blog" />
</jsp:include>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Chat" />
</jsp:include>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/chatCss.css">
<main>
    <div class="container">
        <h2>Welcome <span id="mainUser">${user.getUserId()}</span>!</h2>
        <form method="post" action="Controller?action=LogOut">
            <input type="submit" id="logoutbutton" value="Log Out">
        </form>

        <div class="card col-sm-10 mb-5 p-2">
            <div class="card-body">
               <p>Current Status: <span id="currStat"> </span></p>

                <p>Change Status:
                    <input type="text" name="userStatus" id="newStatus" list="userStats">
                    <datalist id="userStats">
                        <option value="Online">
                        <option value="Offline">
                        <option value="Away">
                    </datalist>
                    </select>
                </p>
                <input type="button" id="chgStatusB" value="Change Status"/>
            </div>
        </div>

        <div class="card col-sm-10 mb-5 p-2">
            <div class="card-body">
                <h2>Friends</h2>
                <h3>Add friend</h3>
                <p>Write the name of the friend you want to add:</p>
                <input type="text" name="Friend" id="newFriend">
                <input type="button" class="btn btn-primary" id="addFriendB" value="Add this friend!">
                <div class="mt-5">
                    <h3>Friendslist</h3>
                    <input type="button" class="btn btn-primary" id="toggleFriendList" value="Show/Hide friendlist">
                    <table class="table" id="tableFriendlist">
                        <thead>
                        <tr>
                            <th scope="col">Username</th>
                            <th scope="col">Status</th>
                        </tr>
                        </thead>
                        <tbody id="friendList">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>
<script type="text/javascript" src="js/statusAndFriendlist.js"></script>


<jsp:include page="footer.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
</body>
</html>
