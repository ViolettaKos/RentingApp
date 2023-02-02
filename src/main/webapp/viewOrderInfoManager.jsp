<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 26/12/2022
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="order"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>

<c:set var="order_db" value="${requestScope.orderInfo}"/>
<h1 style="margin-left: 100px; margin-top: 200px; color: black;"><fmt:message key="order.id"/>=<c:out
        value="${order_db.order_id}"/></h1>
<h4 style="margin-left: 100px;color: black;"><fmt:message key="username"/>: <c:out value="${order_db.login}"/></h4>
<h4 style="margin-left: 100px;color: black; padding-bottom: 20px;"><fmt:message key="car.id"/>: <c:out
        value="${order_db.car_id}"/></h4>
<c:choose>
    <c:when test="${order_db.rejected}">
        <h4 style="margin-left: 100px;color: black; padding-bottom: 20px;"><fmt:message key="status"/>: <a href="#" onclick="openForm()" style="color: red"><fmt:message
                key="rejected"/></a></h4>
    </c:when>
    <c:when test="${order_db.returned}">
        <i style="margin-left: 100px;color: red; padding-bottom: 10px; font-size: 18px"><fmt:message
                key="returned"/></i>
    </c:when>
</c:choose>
<div class="row m-0 bg-light" style="padding: 40px 40px 30px 100px">
    <c:choose>
    <c:when test="${order_db.payed}">
    <i style="text-decoration: underline; text-align: center; color: #3e8e41; font-size: 22px"><fmt:message
            key="status"/>:
        <fmt:message key="payed"/></i>
    </c:when>
    <c:otherwise>
    <i style="text-decoration: underline; text-align: center; color: red; font-size: 22px"><fmt:message
            key="status"/>:
        <fmt:message key="unPaid"/></i>
    </c:otherwise>
    </c:choose>

    <div class="col-md-4 col-6 ps-30 pe-0 my-4">
        <p class="text-muted"><fmt:message key="car"/></p>
        <p class="h5"><c:out value="${order_db.brand}"/> <c:out value="${order_db.name}"/></p>
    </div>
    <div class="col-md-4 col-6  ps-30 my-4">
        <p class="text-muted"><fmt:message key="option"/></p>
        <c:choose>
            <c:when test="${order_db.option}">
                <p class="h5 m-0"><fmt:message key="driver"/><p>
            </c:when>
            <c:otherwise>
                <p class="h5 m-0"><fmt:message key="no.driver"/></p>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="total.days"/></p>
        <p class="h5 m-0"><c:out value="${order_db.total_days}"/><span class="ps-1"> days</span></p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="from"/></p>
        <p class="h5 m-0"><c:out value="${order_db.from}"/></p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="to"/></p>
        <p class="h5 m-0"><c:out value="${order_db.to}"/></p>
    </div>
    <div class="col-md-4 col-6 ps-30 my-4">
        <p class="text-muted"><fmt:message key="total.price"/></p>
        <p class="h5 m-0"><c:out value="${order_db.total_price}"/>$</p>
    </div>

    <c:choose>
    <c:when test="${order_db.rejected || order_db.returned}">
        <div>
        <div style="float: left; padding: 20px; margin-left: 370px">
            <button class="card-car-button" type="submit" style=" background-color:#FFCCCB; width: 270px" disabled>
                <fmt:message key="reject"/></button>
        </div>
        <div style="float: left; padding: 20px;">
            <button class="card-car-button" type="submit" disabled><fmt:message
                    key="register.return"/></button>
        </div>
        </div>
        </c:when>
        <c:otherwise>
        <div>
                <div style="float: left; padding: 20px; margin-left: 370px">
                    <form action="rejectOrder.jsp">
                        <input type="hidden" name="order_id" value="${order_db.order_id}">
                        <input type="hidden" name="car_id" value="${order_db.car_id}">
                        <button class="card-car-button" type="submit" style=" background-color: darkred; width: 270px">
                            <fmt:message
                                    key="reject"/></button>
                    </form>
                </div>
                <c:if test="${order_db.payed}">
                <div style="float: left; padding: 20px;">
                    <form action="registerReturn.jsp">
                        <input type="hidden" name="order_id" value="${order_db.order_id}">
                        <input type="hidden" name="car_id" value="${order_db.car_id}">
                        <button class="card-car-button" type="submit"><fmt:message
                                key="register.return"/></button>
                    </form>
                </div>
                        </c:if>
                        <c:if test="${!order_db.payed}">
    <div style="float: left; padding: 10px;">
                            <button class="card-car-button" style="" type="submit" disabled><fmt:message
                                    key="register.return"/></button>
    </div>
                        </c:if>
        </div>
        </c:otherwise>
        </c:choose>


    <div class="form-popup" id="myForm" style="left: 50%; right: 0px; top: 50%; border: none;">
        <div class="form-container" style="margin-left: -150px; background-color: green ">
            <h3 style="text-align: center; color: white"><fmt:message key="reason"/></h3>

            <p style="text-align: center; color: white"><c:out value="${order_db.comment}"/></p>
            <button type="button" class="btn cancel" onclick="closeForm()"><fmt:message key="close"/></button>
        </div>
    </div>

    <script>
        function openForm() {
            document.getElementById("myForm").style.display = "block";
        }

        function closeForm() {
            document.getElementById("myForm").style.display = "none";
        }
    </script>


</body>
</html>

