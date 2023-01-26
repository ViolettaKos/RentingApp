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
    <title><fmt:message key="registration"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>

    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>

</head>
<body>

<jsp:include page="fragments/header.jsp"/>

<%--REGISTRATION FORM--%>

<div style="margin-top: 150px" class="container">
    <div class="registration mx-auto d-block w-100">

        <tags:alertTag msg="${requestScope.msg}"/>

        <div class="page-header text-center">
            <h1><fmt:message key="sign.up"/></h1>
        </div>

        <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
              class="form-validate form-horizontal well">
            <input type="hidden" name="command" value="user_reg" />
            <fieldset>
                <div class="form-group">
                    <label><fmt:message key="first_name"/> *</label>
                    <input type="text" class="form-control" required name="firstname"
                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                           title="<fmt:message key="register.name"/>">
                </div>
                <div class="form-group">
                    <label><fmt:message key="last_name"/> *</label>
                    <input type="text" class="form-control" required name="lastname"
                           pattern="^[A-Za-zА-ЩЬЮЯҐІЇЄа-щьюяґіїє'\- ]{1,30}"
                           title="<fmt:message key="register.name"/>">
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
                    <label><fmt:message key="confirm.pass"/> *</label>
                    <input type="password" class="form-control" required name="repeated_pass">
                </div>
                <div class="form-group">
                    <label><fmt:message key="email.address"/> *</label>
                    <input type="email" class="form-control" required name="email"
                           pattern="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@\[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$"
                           title="<fmt:message key="register.email"/>">
                </div>
                <div class="form-group">
                    <label><fmt:message key="enter.telephone"/> *</label>
                    <input type="text" class="form-control" required name="telephone"
                           pattern="^(?:\+38)?(?:[0-9] ?){9}[0-9]$"
                           title="<fmt:message key="register.phone"/>">
                </div>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="form-group d-flex justify-content-start">
                        <button type="submit" class="submit"><fmt:message key="btn.submit"/></button>
                    </div>
                    <div class="form-check form-group d-flex justify-content-end">
                        <a style="color: green" href="login.jsp"><fmt:message key="log.instead"/></a>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>


</body>
</html>