<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 02/01/2023
  Time: 09:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="reject"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
    <style>
        textarea {
            width: 80%;
            height: 150px;
            padding: 12px 20px;
            box-sizing: border-box;
            border: 2px solid #ccc;
            border-radius: 4px;
            background-color: #f8f8f8;
            resize: none;
        }
    </style>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<h1 style="margin-left: 100px; margin-top: 200px; color: black;"><fmt:message key="rejection.order"/></h1>
<h5 style="margin-left: 100px; margin-top: 20px; color: black;"><fmt:message key="reject.warn"/></h5>

    <form style="margin-left: 100px; margin-top: 50px" action="${pageContext.request.contextPath}/controller" method="post">
        <textarea name="comment" placeholder="<fmt:message key="type.reason"/>" required></textarea>
        <br>
        <input type="hidden" name="command" value="reject">
        <input type="hidden" name="id" value="${param.order_id}">
        <input type="hidden" name="car_id" value="${param.car_id}">
        <input class="load-more" style="width: 150px; border-radius: 25px" type="submit" value="<fmt:message key="send"/>">
    </form>

</body>
</html>
