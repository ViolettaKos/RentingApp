<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language"/>
<!DOCTYPE html>
<html>
<head>
    <title><fmt:message key="info.MainTitle"/></title>
    <link rel="stylesheet" type="text/css" href="../../style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="../../style/main.css"/>
</head>
<body>
<jsp:include page="../fragments/guestHeader.jsp"/>

<h1 class="front_text" style="padding-top: 300px"><fmt:message key="landing.get"/> <span class="greenWord"><fmt:message key="landing.rental"/></span> <fmt:message key="landing.min"/></h1>
<h4 class="my_h4"><fmt:message key="landing.text"/></h4>
<br><br>
<form action="book.jsp" method="get">
    <button class="rentButton" type="submit"><fmt:message key="rent"/></button>
</form>
<div><img class="car" src="../../img/car.png"></div>

</body>
</html>