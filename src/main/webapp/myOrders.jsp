<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 06/01/2023
  Time: 22:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<html>
<head>
    <title><fmt:message key="my_orders"/></title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/registration.css"/>
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css">
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
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
<c:choose>
  <c:when test="${sessionScope.user eq null}">
    <form style="margin: -60px 25px 0px 25px" action="login.jsp">
      <button class="log_in_button" type="submit"><fmt:message key="button.login"/></button>
    </form>
    <form action="register.jsp">
      <button class="register_button" type="button"><fmt:message key="button.register"/></button>
    </form>
  <form method="post">
    <select class="language" style="margin-top: 7px" name="locale" onchange='submit();'>
      <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
      <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
    </select>
  </form>
    <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -30px" src="${pageContext.request.contextPath}/images/logo.png">
    </div>
    </nav>
  </c:when>
  <c:otherwise>
    <form style="margin: -60px 25px 0px 25px" action="${pageContext.request.contextPath}/login" method="post">
      <button class="log_in_button" type="submit" name="action" value="logout"><fmt:message key="button.logout"/></button>
    </form>
    <select class="language" style="margin-top: 18px" name="" required="required">
      <option href="language?locale=en"><fmt:message key="language.en"/></option>
      <option href="language?locale=ua"><fmt:message key="language.ua"/></option>
    </select>
    <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -18px" src="${pageContext.request.contextPath}/images/logo.png">
    </div>
    </nav>
  </c:otherwise>
</c:choose>

  <c:choose>
    <c:when test="${sessionScope.user eq null}">
      <h1 style="text-align: center; margin-top:360px;"><fmt:message key="warn.unauthorized"/></h1>
    </c:when>
    <c:otherwise>
      <h1 style="margin-left: 100px; margin-top: 200px; color: black;"><fmt:message key="my_orders"/></h1>
      <c:forEach var="order" items="${requestScope.myOrders}">
        <br><br><br>
      <div class="row m-0 bg-light" style="padding: 40px 40px 30px 100px">
        <c:choose>
          <c:when test="${order.payed}">
            <i style="text-decoration: underline; text-align: center; color: #3e8e41; font-size: 22px"><fmt:message key="status"/>:
              <fmt:message key="payed"/></i>
          </c:when>
          <c:otherwise>
            <i style="text-decoration: underline; text-align: center; color: red; font-size: 22px"><fmt:message
                    key="status"/>:
              <fmt:message key="unPaid"/></i>
          </c:otherwise>
        </c:choose>
        <div class="col-md-4 col-6 ps-30 pe-0 my-4">
          <p class="text-muted"><fmt:message key="car"/></p>
          <p class="h5"><c:out value="${order.brand}"/> <c:out value="${order.name}"/></p>
        </div>
        <div class="col-md-4 col-6  ps-30 my-4">
          <p class="text-muted"><fmt:message key="option"/></p>
          <c:choose>
            <c:when test="${order.option}">
              <p class="h5 m-0"><fmt:message key="driver"/><p>
            </c:when>
            <c:otherwise>
              <p class="h5 m-0"><fmt:message key="no.driver"/></p>
            </c:otherwise>
          </c:choose>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
          <p class="text-muted"><fmt:message key="total.days"/></p>
          <p class="h5 m-0"><c:out value="${order.total_days}"/><span class="ps-1"> <fmt:message key="days"/></span></p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
          <p class="text-muted"><fmt:message key="from"/></p>
          <p class="h5 m-0"><c:out value="${order.from}"/></p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
          <p class="text-muted"><fmt:message key="to"/></p>
          <p class="h5 m-0"><c:out value="${order.to}"/></p>
          </p>
        </div>
        <div class="col-md-4 col-6 ps-30 my-4">
          <p class="text-muted"><fmt:message key="total.price"/></p>
          <p class="h5 m-0"><c:out value="${order.total_price}"/>$</p>
        </div>
        <form action="${pageContext.request.contextPath}/paymentPage.jsp" method="get" style="margin-top: 10px">
          <input type="hidden" name="amount" value="${order.total_price}">
          <input type="hidden" name="order_id" value="${order.order_id}">
          <c:choose>
            <c:when test="${order.payed}">
              <button class="card-car-button" type="submit" style="margin-left:560px" disabled><fmt:message key="payed"/></button>
              <p style="text-align: center; margin-top: 10px; color: darkred; font-style: italic"><fmt:message key="warn.payed"/></p>
            </c:when>
            <c:otherwise>
              <button class="card-car-button" type="submit" style="margin-left:560px"><fmt:message key="pay"/></button>
            </c:otherwise>
          </c:choose>
        </form>
      </div>
    </c:forEach>

    </c:otherwise>
  </c:choose>
</body>
</html>
