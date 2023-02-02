<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 24/12/2022
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<html>
<head>
    <title><fmt:message key="edit.car"/></title>
  <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
  <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>

<div style="margin-top: 150px" class="container">
  <div class="registration mx-auto d-block w-100">

    <div class="page-header text-center">
      <h1><fmt:message key="edit.car"/></h1>
    </div>

    <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
          class="form-validate form-horizontal well">
      <input type="hidden" name="command" value="edit_car">

      <c:set var="car" value="${requestScope.car}"/>

      <fieldset>
        <div class="form-group">
          <label><fmt:message key="brand"/> *</label>
          <input type="text" class="form-control" required name="brand"
                 pattern="^[a-zA-Z ]*$"
                 title="<fmt:message key="warn.Latin"/>"
                  value="${car.brand}">
        </div>
        <div class="form-group">
          <label><fmt:message key="name"/> *</label>
          <input type="text" class="form-control" required name="name"
                 pattern="^[a-zA-Z0-9 ]([._-](?![._-])|[a-zA-Z0-9 ]){0,18}[a-zA-Z0-9 ]$"
                 title="<fmt:message key="warn.Latin"/>"
                 value="${car.name}">
        </div>
        <div class="form-group">
          <label><fmt:message key="qualityC"/> *</label>
          <input type="text" class="form-control" required name="quality_class"
                 pattern="^[A-E]"
                 title="<fmt:message key="warn.class"/>"
                 value="${car.quality_class}">
        </div>
        <div class="form-group">
          <label><fmt:message key="price"/> *</label>
          <input type="number" class="form-control" required name="price"
                 pattern="^\d+$"
                 max="10000"
                 min="10"
                 title="<fmt:message key="warn.digits"/>"
                 value="${car.price}">
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <div class="form-group d-flex justify-content-start">
            <input type="hidden" name="id" value="${car.id}">
            <button type="submit" name="action" value="update" class="submit"><fmt:message key="btn.submit"/></button>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
</div>

</body>
</html>
