<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 17/12/2022
  Time: 14:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="success"/></title>
    <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap" rel="stylesheet">
</head>

<style>
    body {
        text-align: center;
        padding: 40px 0;
        background: #EBF0F5;
    }
    h1 {
        color: green;
        font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
        font-weight: 900;
        font-size: 40px;
        margin-bottom: 10px;
    }
    p {
        color: #404F5E;
        font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
        font-size:20px;
        margin: 0;
    }
    i {
        color: green;
        font-size: 100px;
        line-height: 200px;
        margin-left:-15px;
    }
    .card {
        background: white;
        padding: 60px;
        border-radius: 4px;
        box-shadow: 0 2px 3px #C8D0D8;
        display: inline-block;
        margin: 0 auto;
    }
    .log_in_button {
        margin-top: 20px;
        width: 150px;
        height: 50px;
        border-radius: 25px;
        border: 2px solid green;
        color: white;
        background-color: green;
        font-family: "Lucida Console", monospace;
        font-size: 18px;
        cursor: pointer;
    }


</style>

<body>

<div class="card">
    <div style="border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;">
        <i class="checkmark">✓</i>
    </div>
    <h1><fmt:message key="success"/></h1>
    <p><fmt:message key="received"/>;<br/> <fmt:message key="in.touch"/>!</p>
    <form action="${pageContext.request.contextPath}/displayOrder" method="get">
        <input class="log_in_button" type="submit" value="<fmt:message key="back"/>" />
    </form>
</div>




</body>
</html>
