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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/registration.css"/>

</head>
<body>
<nav>
    <div class="nav-bg"></div>
    <ul style="font-family: system-ui;">
        <li><a href="landing.jsp"><fmt:message key="main"/></a></li>
        <li><a href="${pageContext.request.contextPath}/displayBookPage"><fmt:message key="book"/></a></li>
        <li><a href="${pageContext.request.contextPath}/displayUser"><fmt:message key="profile"/></a></li>
        <li><a href="contacts.jsp" style="color: green"><fmt:message key="contacts"/></a></li>
    </ul>
    <c:choose>
    <c:when test="${sessionScope.loggedUser eq null}">
    <form style="margin: -60px 25px 0px 25px" action="login.jsp">
        <button class="log_in_button" type="submit"><fmt:message key="button.login"/></button>
    </form>
    <form action="register.jsp">
        <button class="register_button" type="submit"><fmt:message key="button.register"/></button>
    </form>
    <form method="post">
        <select class="language" style="margin-top: 7px" name="locale" onchange='submit();'>
            <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
            <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
        </select>
    </form>
    <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -22px" src="${pageContext.request.contextPath}/images/logo.png">
    </div>
</nav>
</c:when>
<c:otherwise>
    <form style="margin: -60px 25px 0px 25px" action="${pageContext.request.contextPath}/login" method="post">
        <button class="log_in_button" type="submit" name="action" value="logout"><fmt:message key="button.logout"/></button>
    </form>
    <select class="language" style="margin-top: 18px" name="locale" onchange='submit();'>
        <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
        <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
    </select>
    <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -18px" src="${pageContext.request.contextPath}/images/logo.png">
    </div>
    </nav>
</c:otherwise>
</c:choose>

<c:if test="${sessionScope.user ne null}">
    <nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
        <div>
            <form action="${pageContext.request.contextPath}/displayOrder" method="get">
                <button type="submit" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="my_orders"/></button>
            </form>
        </div>
    </nav>
</c:if>

<c:if test="${sessionScope.manager ne null}">
    <nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
        <div>
            <form action="${pageContext.request.contextPath}/manager" method="get">
                <button type="submit" name="action" value="view-orders" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px; font-family: 'Segoe UI',serif"><fmt:message key="orders"/></button>
            </form>
        </div>
    </nav>
</c:if>

<c:if test="${sessionScope.admin ne null}">
    <nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
        <div>
            <form action="${pageContext.request.contextPath}/admin" method="get">
                <button type="submit" name="action" value="cars" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.cars"/></button>
                <button type="submit" name="action" value="'user'" style="border: none; margin-right: 20px; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.users"/></button>
                <button type="submit" name="action" value="'manager'" style="border: none; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.managers"/></button>
            </form>
        </div>
    </nav>
</c:if>


<img src="images/contact.jpg" style="margin-top:-150px;float: right"; width="750px"; height="680px;">

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
