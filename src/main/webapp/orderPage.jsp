<%--
  Created by IntelliJ IDEA.
  User: Vi
  Date: 09/12/2022
  Time: 20:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="your.order"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<div style="margin-top: 150px" class="container">
    <div class="registration mx-auto d-block w-100">

            <tags:alertTag msg="${requestScope.orderMsg}"/>

        <div class="page-header text-center">
            <h1 style="color: green"><fmt:message key="your.details"/></h1>
        </div>

        <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
              class="form-validate form-horizontal well">
            <input type="hidden" name="command" value="make_order">
            <fieldset>
                <div class="form-group">
                    <label><fmt:message key="first_name"/></label>
                    <input type="text" class="form-control" name="firstName" value="${sessionScope.logged.firstName}" readonly>
                </div>
                <div class="form-group">
                    <label><fmt:message key="last_name"/></label>
                    <input type="text" class="form-control" value="${sessionScope.logged.lastName}" readonly>
                </div>
                <div class="form-group">
                    <label><fmt:message key="birthday"/>:</label>
                    <input type="date" class="form-control" required name="age" max="<ctg:minAge/>">
                </div>
                <div class="form-group">
                        <label><fmt:message key="choose"/>:</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="option" value="true" id="flexRadioDefault1">
                            <label class="form-check-label" for="flexRadioDefault1">
                                <fmt:message key="with.driver"/>
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="option" id="flexRadioDefault2" value="false" checked>
                            <label class="form-check-label" for="flexRadioDefault2">
                                <fmt:message key="without.driver"/>
                            </label>
                        </div>
                </div>
                <div class="form-group">
                    <label><fmt:message key="lease"/>:</label>
                    <br>
                    <label><fmt:message key="from"/>:</label>
                    <input type="date" class="form-control" required name="from" min="<ctg:date/>">
                    <label><fmt:message key="to"/>:</label>
                    <input type="date" class="form-control" required name="to" min="<ctg:date/>">
                </div>
                <div class="d-flex justify-content-between align-items-center">
                    <div class="form-group d-flex justify-content-start">
                        <input type="hidden" name="price" value="${param.price}">
                        <input type="hidden" name="car_id" value="${param.car_id}">
                        <button type="submit" class="submit" style="margin-left: 100px"><fmt:message key="btn.submit"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>
</body>
</html>
