<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<jsp:include page="head.jsp">
    <jsp:param name="title" value="Blog - Wat ben je van plan om te doen vandaag?"/>
</jsp:include>
<body>
<jsp:include page="header.jsp">
    <jsp:param name="title" value="Blog - Wat ben je van plan om te doen vandaag?"/>
</jsp:include>
<main>
    <a class="btn btn-primary mb-5 mt-5" href="Controller" role="button" id="backB">Back</a>
    <div class="card col-sm-10 mb-5 p-2">
        <h1>Wat ben je van plan om te doen vandaag?</h1>
        <p>Lorem Ipsum is slechts een proeftekst uit het drukkerij- en zetterijwezen.
            Lorem Ipsum is de standaard proeftekst in deze bedrijfstak sinds de 16e eeuw,
            toen een onbekende drukker een zethaak met letters nam en ze door elkaar husselde om een font-catalogus te maken.
            Het heeft niet alleen vijf eeuwen overleefd maar is ook, vrijwel onveranderd, overgenomen in elektronische letterzetting.
            Het is in de jaren '60 populair geworden met de introductie van Letraset vellen met Lorem Ipsum passages en meer recentelijk
            door desktop publishing software zoals Aldus PageMaker die versies van Lorem Ipsum bevatten.</p>
    </div>
    <div class="card col-sm-10 mb-5 p-2">
        <h3>Comments</h3>
        <div class="card p-2" id="commentSection">

        </div>
    </div>

    <div class="card col-sm-10 mb-5 p-2">
        <h3>Post a comment</h3>
        <p>
            <label for="comment">Comment:</label>
            <TEXTAREA id="comment" name="comment" rows=1 COLS=40>Comment</TEXTAREA>
        </p>
        <p>
            <input type="submit" id="postCommentB" value="Post comment">
        </p>
    </div>


</main>
<script type="text/javascript" src="js/blog.js"></script>
<jsp:include page="footer.jsp">
    <jsp:param name="title" value="Home" />
</jsp:include>
</body>
</html>