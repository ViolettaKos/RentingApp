
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="book"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<c:set var="car" value="${requestScope.car}"/>
<img src="/RentingApp_war_exploded/img/cars/${car.id}.jpg"
     style="float:left; width: 700px; height: 500px; margin-left: 50px; margin-right: 100px">
<h2 style="margin-top: 180px; margin-bottom: 30px">${car.brand} ${car.name}</h2>

<div class="row m-0 bg-light">
    <div class="col-md-4 col-6 ps-30 pe-0 my-4">
        <p class="text-muted"><fmt:message key="brand"/></p>
        <p class="h5">${car.brand}<span class="ps-1"></span></p>
    </div>
    <div class="col-md-4 col-6  ps-30 my-4">
        <p class="text-muted"><fmt:message key="name"/></p>
        <p class="h5 m-0">${car.name}
        </p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="qualityC"/></p>
        <p class="h5 m-0">${car.quality_class}<span class="ps-1"> <fmt:message key="class"/></span></p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="availability"/></p>
<c:choose>
        <c:when test="${car.available eq true}">
            <p class="h5 m-0" style="color: green"><fmt:message key="available"/></p>
        </c:when>
        <c:otherwise>
            <p class="h5 m-0" style="color: red"><fmt:message key="no.available"/></p>
        </c:otherwise>
</c:choose>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="car.id"/></p>
        <p class="h5 m-0">${car.id}
        </p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="price"/></p>
        <p class="h5 m-0"><fmt:formatNumber value="${car.price}"
                                            type="currency"/></p>
    </div>
</div>
<c:choose>
    <c:when test="${sessionScope.role eq 'user' && sessionScope.logged.blocked eq false && car.available eq true}">
        <form action="orderPage.jsp" method="get" style="margin-top: 50px">
            <input type="hidden" name="car_id" value="${car.id}">
            <input type="hidden" name="price" value="${car.price}">
            <button class="card-car-button" type="submit" style="margin-left: 240px"><fmt:message key="book"/></button>
        </form>
    </c:when>
    <c:when test="${sessionScope.logged.blocked eq true}">
        <button type="button" disabled="disabled" style="margin-left: 240px; margin-top: 20px;
    font-size: 16px;
    font-weight: bold;
    color: white;
    right: 50%;
    background-color: #D1E2C4;
    border-radius: 30px;
    border: none;
    text-align: center;
    text-decoration: none;
    padding: 10px 80px;
    cursor: pointer;"><fmt:message key="book"/></button>
        <p style="text-align: center; margin-top: 10px; color: darkred; font-style: italic"><fmt:message key="warn.block"/></p>
    </c:when>
    <c:when test="${car.available eq false}">
        <button type="button" disabled="disabled" style="margin-left: 240px; margin-top: 20px;
    font-size: 16px;
    font-weight: bold;
    color: white;
    right: 50%;
    background-color: #D1E2C4;
    border-radius: 30px;
    border: none;
    text-align: center;
    text-decoration: none;
    padding: 10px 80px;
    cursor: pointer;"><fmt:message key="book"/></button>
        <p style="text-align: center; margin-top: 10px; color: darkred; font-style: italic"><fmt:message key="warn.availability"/></p>
    </c:when>
    <c:otherwise>
        <button type="button" disabled="disabled" style="margin-left: 240px; margin-top: 20px;
    font-size: 16px;
    font-weight: bold;
    color: white;
    right: 50%;
    background-color: #D1E2C4;
    border-radius: 30px;
    border: none;
    text-align: center;
    text-decoration: none;
    padding: 10px 80px;
    cursor: pointer;"><fmt:message key="book"/></button>
        <p style="text-align: center; margin-top: 10px; color: darkred; font-style: italic"><fmt:message key="warn.access"/></p>
    </c:otherwise>
</c:choose>
</body>
</html>
