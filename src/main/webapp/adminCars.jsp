<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 20/12/2022
  Time: 12:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
    <title><fmt:message key="admin.cars"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link rel="stylesheet" type="text/css" href="style/admin_tables.css"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
          integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
            crossorigin="anonymous"></script>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<h1 style="margin-left: 50px; margin-top: 200px; color: black"><fmt:message key="title.pageAdminCars"/></h1>


<form action="${pageContext.request.contextPath}/controller" method="get" style="margin-top: 30px; display: inline-block">
    <input type="hidden" name="command" value="add_car">
    <div style="margin-left: 50px;">
        <button type="submit"
                style="color: white; border-radius: 5px; background-color: green; border: green; width: 200px; height: 30px">
            <fmt:message key="reg.car"/></button>
    </div>
</form>
<jsp:include page="fragments/sortMenu.jsp"/>
<table style="margin-top: 50px; margin-left: auto; margin-right: auto">
    <thead>
    <tr>
        <td><fmt:message key="car.id"/></td>
        <td><fmt:message key="brand"/></td>
        <td><fmt:message key="qualityC"/></td>
        <td><fmt:message key="name"/></td>
        <td><fmt:message key="price"/>, $</td>
        <td><fmt:message key="edit"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="car_db" items="${requestScope.car}">
        <tr>
            <td><c:out value="${car_db.id}"/></td>
            <td><c:out value="${car_db.brand}"/></td>
            <td><c:out value="${car_db.quality_class}"/></td>
            <td><c:out value="${car_db.name}"/></td>
            <td><c:out value="${car_db.price}"/></td>
            <td>

                <form action="${pageContext.request.contextPath}/controller" method="get" style="display: inline-block">
                    <input type="hidden" name="command" value="edit_car">
                    <input type="hidden" name="id" value="${car_db.id}">
                    <button type="submit" style="padding: 0 10px 0 0; border: none; background: none; color: green; font-style: italic; font-size: 16px; text-decoration: underline"><fmt:message key="edit"/></button>
                </form>
                <form action="${pageContext.request.contextPath}/controller" method="get" style="display: inline-block">
                    <input type="hidden" name="command" value="delete_car">
                    <input type="hidden" name="id" value="${car_db.id}">
                    <button type="submit" style="padding: 0; border: none; background: none; color: red; font-style: italic; font-size: 16px; text-decoration: underline"><fmt:message key="delete"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>

<jsp:include page="fragments/pagination.jsp"/>


</body>
</html>
