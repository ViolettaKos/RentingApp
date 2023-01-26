<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 23/12/2022
  Time: 20:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>


<html>
<head>
  <title><fmt:message key="edit"/></title>
  <link rel="stylesheet" type="text/css" href="style/main.css"/>
  <link rel="stylesheet" type="text/css" href="style/navbar.css"/>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>

<div style="margin-top: 150px" class="container">
  <div class="registration mx-auto d-block w-100">

    <tags:alertTag msg="${requestScope.msg}"/>

    <div class="page-header text-center">
      <h1><fmt:message key="edit.info"/></h1>
    </div>

    <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
          class="form-validate form-horizontal well">
      <input type="hidden" name="command" value="edit" />
      <c:set var="user" value="${sessionScope.logged}"/>
      <fieldset>
        <div class="form-group">
          <label><fmt:message key="first_name"/> *</label>
          <input type="text" class="form-control" required name="firstname"
                 pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                 title="<fmt:message key="register.name"/>"
                  value="${user.firstName}">
        </div>
        <div class="form-group">
          <label><fmt:message key="last_name"/> *</label>
          <input type="text" class="form-control" required name="lastname"
                 pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                 title="<fmt:message key="register.name"/>"
                 value="${user.lastName}">
        </div>
        <div class="form-group">
          <label><fmt:message key="username"/> *</label>
          <input type="text" class="form-control" required name="username"
                 pattern="^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){2,18}[a-zA-Z0-9]$"
                 title="<fmt:message key="register.login"/>">
        </div>
        <div class="form-group">
          <label><fmt:message key="password"/> *</label>
          <input type="password" class="form-control" required name="pass"
                 pattern="^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"
                 title="<fmt:message key="register.login"/>">
        </div>
        <div class="form-group">
          <label><fmt:message key="email"/> *</label>
          <input type="email" class="form-control" required name="email"
                 pattern="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@\[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$"
                 title="<fmt:message key="register.email"/>"
                 value="${user.email}">
        </div>
        <div class="form-group">
          <label><fmt:message key="telephone.number"/> *</label>
          <input type="text" class="form-control" required name="telephone"
                 pattern="^(?:\+38)?(?:[0-9] ?){9}[0-9]$"
                 title="<fmt:message key="register.phone"/>"
                 value="${user.telephone}">
        </div>
        <div class="d-flex justify-content-between align-items-center">
          <div class="form-group d-flex justify-content-start">
            <button type="submit" class="submit"><fmt:message key="btn.submit"/></button>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
</div>





</body>
</html>
