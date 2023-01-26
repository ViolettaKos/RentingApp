<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 22/01/2023
  Time: 14:26
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
    <title><fmt:message key="log.in"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>

<%--LOG IN FORM--%>

<div style="margin-top: 150px" class="container">
    <div class="registration mx-auto d-block w-100">

        <tags:alertTag msg="${requestScope.msg}"/>

        <div class="page-header text-center">
            <h1><fmt:message key="log.in"/></h1>
        </div>

        <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
              class="form-validate form-horizontal well">
            <input type="hidden" name="command" value="login" />
            <fieldset>
                <div class="form-group">
                    <label><fmt:message key="enter.username"/></label>
                    <input type="text" class="form-control" required name="login"
                           value="${requestScope.login}">
                </div>
                <div class="form-group">
                    <label><fmt:message key="enter.pass"/></label>
                    <input type="password" class="form-control" required name="password">
                </div>

                <div class="d-flex justify-content-between align-items-center">
                    <div class="form-group d-flex justify-content-start">
                        <button type="submit" class="submit"><fmt:message key="log.in"/></button>
                    </div>
                    <div class="form-check form-group d-flex justify-content-end">
                        <a style="color: green" href="restorePass.jsp"><fmt:message key="forgot.pass"/></a>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
