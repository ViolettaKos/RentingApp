<%--
  Created by IntelliJ IDEA.
  User: Vi
  Date: 06/12/2022
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<style>
    .containerA {
        text-align: center;
    }

    .pagination {
        padding: 30px 0;
    }

    .pagination ul {
        margin-right: auto;
        margin-left: auto;
        padding: 0;
        list-style-type: none;
    }

    .pagination a {
        display: inline-block;
        padding: 10px 18px;
        color: #222;
    }

    a {
        text-decoration: none;
    }

    p, li, a {
        font-size: 14px;
    }

    .p12 a:first-of-type, .p12 a:last-of-type, .p12 .is-active {
        background-color: green;
        color: #fff;
        font-weight: bold;
    }
</style>
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


<div class="container mt-5">

    <div class="d-flex justify-content-between align-items-center mb-3" style="padding-top: 120px">
        <span style="font-size: 24px; font-weight: bold; display: inline-block"><fmt:message key="our.cars"/></span>
        <jsp:include page="fragments/sortMenu.jsp"/>
    </div>


    <div class="row">
        <c:forEach var="car_db" items="${requestScope.car}">
            <div class="col-md-4" style="padding-bottom: 50px">

                <div class="card">

                    <div class="d-flex justify-content-between align-items-center">

                        <div class="d-flex flex-row align-items-center time">

                            <i class="fa fa-clock-o"></i>
                            <small class="ml-1" style="color: green; font-weight: bold"><c:out
                                    value="${car_db.quality_class}"/> <fmt:message key="class"/></small>

                        </div>
                        <c:set var="brand" value="${car_db.brand}"/>
                        <img src="img/brands/${fn:toLowerCase(brand)}.jpg">

                    </div>


                    <div class="text-center">

                        <img src="img/cars/${car_db.id}.jpg" width="350"
                             height="220">

                    </div>

                    <div class="text-center">

                        <h5><c:out value="${car_db.brand} ${car_db.name}"/></h5>
                        <span class="text-success"><fmt:formatNumber value="${car_db.price}"
                                                                     type="currency"/><br/>
          </span>
                        <br>
                        <form action="${pageContext.request.contextPath}/controller" method="get">
                            <input type="hidden" name="car_id" value="${car_db.id}">
                            <input type="hidden" name="command" value="show_car">
                            <button class="card-car-button" type="submit"><fmt:message key="book"/></button>
                        </form>
                    </div>

                </div>

            </div>
        </c:forEach>
    </div>

</div>

<jsp:include page="fragments/pagination.jsp"/>

</body>
</html>
