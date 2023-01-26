<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 26/12/2022
  Time: 15:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="contacts"/></title>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<img src="img/contact.jpg" style="margin-top:-150px;float: right" width="750px"; height="680px;">

<div style="float:none;"></div>
<h1 style="margin-left: 50px; margin-top:200px; color: black"><fmt:message key="contact.us"/></h1>
<h4 style="margin-left: 50px; margin-top:20px;"><fmt:message key="our.team"/></h4>

<h2 style="margin-left: 50px; margin-top:50px; color: black"><fmt:message key="our.email"/></h2>
<h4 style="margin-left: 50px; margin-top:10px; color: black">car.rent.tomcat@gmail.com</h4>

<h2 style="margin-left: 50px; margin-top:30px; color: black"><fmt:message key="our.telephone"/></h2>
<h4 style="margin-left: 50px; margin-top:10px; color: black">+380 (67) 768-72-06</h4>

<h2 style="margin-left: 50px; margin-top:30px;color: black"><fmt:message key="our.address"/></h2>
<h4 style="margin-left: 50px; margin-top:10px; color: black"><fmt:message key="address"/></h4>
</div>
</body>
</html>
