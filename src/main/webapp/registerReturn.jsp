<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 03/01/2023
  Time: 16:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="register.return"/></title>
  <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
  <link rel="stylesheet" type="text/css" href="style/main.css"/>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
  <style>
    p,
    label {
      font: 1rem 'Fira Sans', sans-serif;
    }

    input {
      margin: 0.4rem;
    }
  </style>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>

<h1 style="margin-left: 100px; margin-top: 200px; color: black;"><fmt:message key="register.return"/></h1>

<fieldset style="margin-left:50px; margin-top: 20px">
  <legend><fmt:message key="legend.damage"/></legend>
  <form action="${pageContext.request.contextPath}/controller" method="post">
    <input type="hidden" name="command" value="register_return">
  <div>
    <input type="radio" id="no" name="damage" value="false" checked onclick="myFunction1()">
    <label for="no"><fmt:message key="no"/></label>
  </div>
  <div>
    <input type="radio" id="yes" name="damage" value="true" onclick="myFunction1()">
    <label for="yes"><fmt:message key="yes"/></label>
  </div>
    <div id = "amount" style="display:none">
    <label for="money"><fmt:message key="money.repair"/></label>
    <input type="number" id="money" name="money" min="10" max="50 000 000" required><br><br>
    </div>
    <input type="hidden" name="order_id" value="${param.order_id}">
    <input type="hidden" name="car_id" value="${param.car_id}">
  <button type="submit" class="load-more"><fmt:message key="register.return"/></button>
  </form>
</fieldset>


<script>
  function myFunction1() {
    if(document.getElementById('true').checked) {
      document.getElementById("amount").style.display="block";
    }else if(document.getElementById('false').checked) {
      document.getElementById("amount").style.display="none";
    }
  }
</script>
</body>
</html>
