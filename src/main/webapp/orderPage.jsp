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
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/registration.css"/>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
</head>
<body>
<nav>
    <div class="nav-bg"></div>
    <ul>
        <li><a href="landing.jsp"><fmt:message key="main"/></a></li>
        <li><a href="${pageContext.request.contextPath}/displayBookPage"><fmt:message key="book"/></a></li>
        <li><a href="${pageContext.request.contextPath}/displayUser"><fmt:message key="profile"/></a></li>
        <li><a href="contacts.jsp"><fmt:message key="contacts"/></a></li>    </ul>
    <form style="margin: -60px 25px 0px 25px" action="${pageContext.request.contextPath}/login" method="post">
        <button class="log_in_button" type="submit" name="action" value="logout"><fmt:message key="button.logout"/></button>
    </form>
    <form method="post">
        <select class="language" style="margin-top: 20px" name="locale" onchange='submit();'>
            <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
            <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
        </select>
    </form>
    <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -28px" src="${pageContext.request.contextPath}/images/logo.png">
    </div>
</nav>
<c:if test="${sessionScope.user ne null}">
    <nav style="margin-top: 88px; background-color: #EDFCED; height: 50px;">
        <div>
            <form action="${pageContext.request.contextPath}/displayOrder" method="get">
                <button type="submit" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="my_orders"/></button>
            </form>
        </div>
    </nav>
</c:if>


<div style="margin-top: 150px" class="container">
    <div class="registration mx-auto d-block w-100">


            <tags:alertTag msg="${requestScope.orderMsg}"/>


        <div class="page-header text-center">
            <h1 style="color: green"><fmt:message key="your.details"/></h1>
        </div>
            <c:set var="user" value="${requestScope.info_user}"/>
            <c:set var="car" value="${requestScope.car}"/>
        <form name="member-registration" action="${pageContext.request.contextPath}/order" method="post"
              class="form-validate form-horizontal well">
            <fieldset>
                <div class="form-group">
                    <label><fmt:message key="first_name"/></label>
                    <input type="text" class="form-control" name="firstName" value="${user.firstName}" readonly>
                </div>
                <div class="form-group">
                    <label><fmt:message key="last_name"/></label>
                    <input type="text" class="form-control" value="${user.lastName}" readonly>
                </div>
                <div class="form-group">
                    <label><fmt:message key="birthday"/>:</label>
                    <input type="date" class="form-control" required name="birthday" max="<ctg:minAge/>">
                </div>
                <div class="form-group">
                        <label><fmt:message key="choose"/>:</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" value="+" id="flexRadioDefault1">
                            <label class="form-check-label" for="flexRadioDefault1">
                                <fmt:message key="with.driver"/>
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="flexRadioDefault" id="flexRadioDefault2" value="-" checked>
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
                        <input type="hidden" name="price" value="${car.price}">
                        <input type="hidden" name="car_id" value="${car.car_id}">
                        <button type="submit" class="submit" style="margin-left: 100px"><fmt:message key="btn.submit"/></button>
                    </div>
                </div>
            </fieldset>
        </form>
    </div>
</div>


</body>
</html>
