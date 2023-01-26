<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 23/12/2022
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>


<html>
<head>
  <title><fmt:message key="edit"/></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/registration.css"/>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/admin_tables.css"/>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


</head>
<body>
<nav>
  <div class="nav-bg"></div>
  <ul>
    <li><a href="landing.jsp"><fmt:message key="main"/></a></li>
    <li><a href="${pageContext.request.contextPath}/displayBookPage"><fmt:message key="book"/></a></li>
    <li><a href="${pageContext.request.contextPath}/displayUser"><fmt:message key="profile"/></a></li>
    <li><a href="contacts.jsp"><fmt:message key="contacts"/></a></li>
  </ul>
  <form style="margin: -60px 25px 0px 25px" action="${pageContext.request.contextPath}/login" method="post">
    <button class="log_in_button" type="submit" name="action" value="logout"><fmt:message key="button.logout"/></button>
  </form>
  <form method="post">
    <select class="language" style="margin-top: 20px" name="locale" onchange='submit();'>
      <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
      <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
    </select>
  </form>
  <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -18px" src="${pageContext.request.contextPath}/images/logo.png">
  </div>
</nav>
<c:if test="${sessionScope.admin ne null}">
<nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
  <div>
    <form action="${pageContext.request.contextPath}/admin" method="get">
      <button type="submit" name="action" value="cars" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.cars"/></button>
      <button type="submit" name="action" value="users" style="border: none; margin-right: 20px; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.users"/></button>
      <button type="submit" name="action" value="managers" style="border: none; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.managers"/></button>
    </form>
  </div>
</nav>
</c:if>

<div style="margin-top: 150px" class="container">
  <div class="registration mx-auto d-block w-100">
    <c:choose>
      <c:when test="${requestScope.msg ne null }">
        <hr>
        <div class="alert alert-danger">
          <strong>Oops </strong>${requestScope.msg}
          Try again
        </div>
        <hr>
      </c:when>
    </c:choose>

    <div class="page-header text-center">
      <h1><fmt:message key="edit.info"/></h1>
    </div>

    <form name="member-registration" action="${pageContext.request.contextPath}/adminManagers" method="post"
          class="form-validate form-horizontal well">
      <c:set var="user" value="${requestScope.user}"/>
      <fieldset>
        <div class="form-group">
          <label><fmt:message key="first_name"/> *</label>
          <input type="text" class="form-control" required name="firstname"
                 pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                 title="Must contain only Latin or Cyrillic letters!"
                  value="${user.firstName}">
        </div>
        <div class="form-group">
          <label><fmt:message key="last_name"/> *</label>
          <input type="text" class="form-control" required name="lastname"
                 pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                 title="Must contain only Latin or Cyrillic letters!"
                 value="${user.lastName}">
        </div>
        <div class="form-group">
          <label><fmt:message key="password"/> *</label>
          <input type="password" class="form-control" required name="pass"
                 pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                 title="Must contain only Latin letters, numbers or special symbols!">
        </div>
        <div class="form-group">
          <label><fmt:message key="email"/> *</label>
          <input type="email" class="form-control" required name="email"
                 pattern="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@\[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$"
                 title="Invalid Email Address!"
                 value="${user.email}">
        </div>
        <div class="form-group">
          <label><fmt:message key="telephone.number"/> *</label>
          <input type="text" class="form-control" required name="telephone"
                 pattern="^(?:\+38)?(?:[0-9] ?){9}[0-9]$"
                 title="Invalid Telephone Number!"
                 value="${user.telephone}">
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <div class="form-group d-flex justify-content-start">
            <input type="hidden" name="login" value="${user.username}">
            <button type="submit" name="action" value="update" class="submit"><fmt:message key="btn.submit"/></button>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
</div>





</body>
</html>
