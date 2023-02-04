<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 04/02/2023
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="title.restore"/></title>

    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<div style="margin-top: 150px" class="container">
  <div class="registration mx-auto d-block w-100">

    <tags:alertTag msg="${requestScope.msg}"/>

    <div class="page-header text-center">
      <h1><fmt:message key="title.restore"/></h1>
    </div>

    <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
          class="form-validate form-horizontal well">
      <input type="hidden" name="command" value="restore" />
      <fieldset>
        <div class="form-group">
          <label><fmt:message key="enter.username"/></label>
          <input type="text" class="form-control" required name="login"
                 value="${requestScope.login}">
        </div>
        <div class="form-group">
          <label><fmt:message key="email.address"/></label>
          <input type="text" class="form-control" required name="email">
        </div>

        <div class="d-flex justify-content-between align-items-center">
          <div class="form-group d-flex justify-content-start">
            <button type="submit" class="submit"><fmt:message key="confiramtion"/></button>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
</div>


</body>
</html>
