<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 06/01/2023
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="my_orders"/></title>
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


<h1 style="margin-left: 100px; margin-top: 200px; color: black;"><fmt:message key="my_orders"/></h1>
<c:forEach var="order" items="${requestScope.orders}">
    <br><br><br>
    <div class="row m-0 bg-light" style="padding: 40px 40px 30px 100px">
        <c:choose>
            <c:when test="${order.payed}">
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
            <p class="h5"><c:out value="${order.brand}"/> <c:out value="${order.name}"/></p>
        </div>
        <div class="col-md-4 col-6  ps-30 my-4">
            <p class="text-muted"><fmt:message key="option"/></p>
            <c:choose>
                <c:when test="${order.option}">
                    <p class="h5 m-0"><fmt:message key="driver"/><p>
                </c:when>
                <c:otherwise>
                    <p class="h5 m-0"><fmt:message key="no.driver"/></p>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
            <p class="text-muted"><fmt:message key="total.days"/></p>
            <p class="h5 m-0"><c:out value="${order.total_days}"/><span class="ps-1"> <fmt:message key="days"/></span>
            </p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
            <p class="text-muted"><fmt:message key="from"/></p>
            <p class="h5 m-0"><c:out value="${order.from}"/></p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
            <p class="text-muted"><fmt:message key="to"/></p>
            <p class="h5 m-0"><c:out value="${order.to}"/></p>
            </p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
            <p class="text-muted"><fmt:message key="total.price"/></p>
            <p class="h5 m-0"><c:out value="${order.total_price}"/>$</p>
        </div>
        <form action="paymentPage.jsp" method="get" style="margin-top: 10px">
            <input type="hidden" name="order_id" value="${order.order_id}">
            <input type="hidden" name="amount" value="${order.total_price}">
            <c:choose>
                <c:when test="${order.payed}">
                    <button class="card-car-button" type="submit" style="margin-left:575px" disabled><fmt:message
                            key="payed"/></button>
                    <p style="text-align: center; margin-top: 10px; color: darkred; font-style: italic"><fmt:message
                            key="warn.payed"/></p>
                </c:when>
                <c:otherwise>
                    <button class="card-car-button" type="submit" style="margin-left:575px"><fmt:message
                            key="pay"/></button>
                </c:otherwise>
            </c:choose>
        </form>
    </div>
</c:forEach>

</body>
</html>
