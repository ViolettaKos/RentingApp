<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 22/01/2023
  Time: 21:34
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="info.ProfileTitle"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>

<c:choose>
    <c:when test="${sessionScope.logged eq null}">
        <h1 style="text-align: center; margin-top:360px;"><fmt:message key="warn.unauthorized"/></h1>
    </c:when>
    <c:otherwise>
<c:set var="info" value="${sessionScope.logged}"/>
<h1 style="margin-left: 50px; margin-top: 200px; display: inline-block"><fmt:message key="info.ProfileTitle"/></h1>
    <form action="editProfile.jsp"  style="display: inline-block">
        <button type="submit" style="border: none; background-color: white ;margin-left:20px;text-decoration: underline; font-style: italic; font-size: 24px; color: green; font-weight: bold"><fmt:message key="edit"/></button>
    </form>
<hr style="color: green;height:2px;border-width:0; background-color: green">
<div style="margin-left: 50px">
    <h4><fmt:message key="username"/></h4>
    <h5>${info.username}</h5>
</div>
<br>
<div style="margin-left: 50px">
    <h4><fmt:message key="first_name"/></h4>
    <h5>${info.firstName}</h5>
</div>
<br>
<div style="margin-left: 50px">
    <h4><fmt:message key="last_name"/></h4>
    <h5>${info.lastName}</h5>
</div>
<br>
<div style="margin-left: 50px">
    <h4><fmt:message key="email.address"/></h4>
    <h5>${info.email}</h5>
</div>
<br>
<div style="margin-left: 50px">
    <h4><fmt:message key="phone_number"/></h4>
    <h5>${info.telephone}</h5>
</div>
<br>
<c:if test="${sessionScope.role eq 'user'}">
    <div style="margin-left: 50px">
        <h4> <fmt:message key="amount.account"/> <a href="#" onclick="openForm()" style="font-size: 18px; padding-left: 20px; color: green; font-style: italic"><fmt:message key="top.up"/> </a></h4>
        <h5 style="color: green; font-style: italic"><fmt:formatNumber value="${info.money}" type="currency"/></h5>
    </div>
<br>

<div class="form-popup" id="myForm">
    <form action="${pageContext.request.contextPath}/controller"  method="post" class="form-container">
        <input type="hidden" name="command" value="topUp">
        <h1><fmt:message key="top.up"/>:</h1>

        <label><b><fmt:message key="enter.amount"/></b></label>
        <input type="number" placeholder="<fmt:message key="enter.title"/>" name="amount" required
               max="9999" min="10">
        <button type="submit" class="btn"><fmt:message key="btn.submit"/></button>
        <button type="button" class="btn cancel" onclick="closeForm()"><fmt:message key="cancel"/></button>
    </form>
</div>
</c:if>
<script>
    function openForm() {
        document.getElementById("myForm").style.display = "block";
    }

    function closeForm() {
        document.getElementById("myForm").style.display = "none";
    }
</script>


    </c:otherwise>
</c:choose>
</body>
</html>
