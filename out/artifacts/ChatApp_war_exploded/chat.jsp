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
                <input type="button" class="btn btn-primary" id="chgStatusB" value="Change Status"/>
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
                    <table class="table">
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
<script type="text/javascript" src="js/chat.js"></script>

<jsp:include page="footer.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
</body>
</html>
