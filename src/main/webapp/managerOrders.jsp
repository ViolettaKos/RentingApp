<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 26/12/2022
  Time: 20:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
    <title><fmt:message key="orders"/></title>
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

<h1 style="text-align: center; margin-top: 200px; color: black"><fmt:message key="orders"/></h1>

<jsp:include page="fragments/sortMenu.jsp"/>

<table style="margin-top: 50px; margin-left: auto; margin-right: auto">
  <thead>
  <tr>
    <td><fmt:message key="order.id"/></td>
    <td><fmt:message key="rent.days"/></td>
    <td><fmt:message key="total.price"/>, $</td>
    <td><fmt:message key="option"/></td>
    <td><fmt:message key="payment"/></td>
    <td><fmt:message key="status"/></td>
    <td><fmt:message key="info"/></td>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="order_db" items="${requestScope.orders}">
    <tr>
      <td><c:out value="${order_db.id}"/></td>
      <td><c:out value="${order_db.total_days}"/></td>
      <td><c:out value="${order_db.total_price}"/></td>
      <c:choose>
        <c:when test="${order_db.option eq true}">
          <td><fmt:message key="driver"/></td>
        </c:when>
        <c:otherwise>
          <td><fmt:message key="no.driver"/></td>
        </c:otherwise>
      </c:choose>
      <c:choose>
        <c:when test="${order_db.payed eq true}">
          <td><fmt:message key="payed"/></td>
        </c:when>
        <c:otherwise>
          <td><fmt:message key="unPaid"/></td>
        </c:otherwise>
      </c:choose>
      <c:choose>
        <c:when test="${order_db.rejected eq true}">
          <td><fmt:message key="rejected"/></td>
        </c:when>
        <c:otherwise>
          <td><fmt:message key="processing"/></td>
        </c:otherwise>
      </c:choose>
      <td>
        <form action="${pageContext.request.contextPath}/controller" method="get" style="display: inline-block">
          <input type="hidden" name="id" value="${order_db.id}">
          <button type="submit" name="command" value="display_order" style="padding: 0 10px 0 0; border: none; background: none; color: green; font-style: italic; font-size: 16px; text-decoration: underline"><fmt:message key="info"/></button>
        </form>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
<jsp:include page="fragments/pagination.jsp"/>
</body>
</html>
