<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Blog - Register" />
</jsp:include>
<body onload="deleteCookie()">
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Blog - Register" />
</jsp:include>
<main>
    <c:if test="${errors.size()>0 }">
        <div class="danger">
            <ul>
                <c:forEach var="error" items="${errors }">
                    <li>${error }</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>
    <div class="card col-sm-10 mb-5 p-2">
        <h3>Make a new account</h3>
        <form method="post" action="Controller?action=RegisterConfirm">
            <p>
                <label for="username">Your username </label>
                <input type="text" id="username" name="username" value="">
            </p>
            <p>
                <label for="password">Your password</label>
                <input type="password" id="password" name="password" value="t">
            </p>
            <p>
                <label for="passwordConfirmation">Confirm pw:</label>
                <input type="password" id="passwordConfirmation" name="passwordConfirmation" value="">
            </p>
            <p>
                <label for="email">Your email </label>
                <input type="email" id="email" name="email" value="">
            </p>
            <p>
                <label for="firstName">Your first name </label>
                <input type="text" id="firstName" name="firstName" value="">
            </p>
            <p>
                <label for="lastName">Your last name </label>
                <input type="text" id="lastName" name="lastName" value="">
            </p>
            <p>
                <label for="geslacht">Your gender </label>
                <input type="text" id="geslacht" name="geslacht" value="">
            </p>
            <p>
                <label for="leeftijd">Your age </label>
                <input type="number" id="leeftijd" name="leeftijd" value="">
            </p>
            <p>
                <input class="btn btn-primary" type="submit" id="registerB" value="Register">
            </p>
        </form>
        <a class="btn btn-primary" href="Controller" id="backB">Return</a>
    </div>
</main>
<script type="text/javascript" src="js/blog.js"></script>
<jsp:include page="footer.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
</body>
</html>