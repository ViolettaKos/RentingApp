<%--
  Created by IntelliJ IDEA.
  User: Vi
  Date: 06/12/2022
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>
<style>
  .containerA{
    text-align: center;
  }
  .pagination{
    padding: 30px 0;
  }
  .pagination ul{
    margin-right: auto; margin-left: auto;
    padding: 0;
    list-style-type: none;
  }
  .pagination a{
    display: inline-block;
    padding: 10px 18px;
    color: #222;
  }
  a{
    text-decoration: none;
  }
  p, li, a{
    font-size: 14px;
  }
  .p12 a:first-of-type, .p12 a:last-of-type, .p12 .is-active{
    background-color: green;
    color: #fff;
    font-weight: bold;
  }
</style>
<html>
<head>
    <title><fmt:message key="book"/></title>
    <link rel="stylesheet" type="text/css" href="css/registration.css"/>

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
    <li><a href="landing.jsp" style="font-size: 18px"><fmt:message key="main"/></a></li>
    <li><a href="${pageContext.request.contextPath}/displayBookPage" style="color: green; font-size: 18px"><fmt:message key="book"/></a></li>
    <li><a href="${pageContext.request.contextPath}/displayUser" style="font-size: 18px"><fmt:message key="profile"/></a></li>
    <li><a href="contacts.jsp" style="font-size: 18px"><fmt:message key="contacts"/></a></li>
  </ul>


  <c:choose>
  <c:when test="${sessionScope.loggedUser eq null}">
  <form style="margin: -60px 25px 0px 25px" action="login.jsp">
    <button class="log_in_button" type="submit"><fmt:message key="button.login"/></button>
  </form>
  <form action="register.jsp">
    <button class="register_button" type="submit"><fmt:message key="button.register"/></button>
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
<c:when test="${sessionScope.user ne null || sessionScope.admin ne null || sessionScope.manager ne null }">
<form style="margin: -60px 25px 0px 25px" action="${pageContext.request.contextPath}/login" method="post">
        <button class="log_in_button" type="submit" name="action" value="logout"><fmt:message key="button.logout"/></button>
      </form>
      <form method="post">
        <select class="language" style="margin-top: 20px" name="locale" onchange='submit();'>
          <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
          <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
        </select>
      </form>
      <div><img class="logoImage" style="position: fixed; margin-left: 50px; margin-top: -35px" src="${pageContext.request.contextPath}/images/logo.png">
      </div>
      </nav>
    </c:when>
  </c:choose>


<c:if test="${sessionScope.user eq null && sessionScope.admin eq null && sessionScope.manager ne null}">
  <nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
    <div>
      <form action="${pageContext.request.contextPath}/manager" method="get">
        <button type="submit" name="action" value="view-orders" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="orders"/></button>
      </form>
    </div>
  </nav>
</c:if>

<c:if test="${sessionScope.user eq null && sessionScope.manager eq null && sessionScope.admin ne null}">
  <nav style="margin-top: 77px; background-color: #EDFCED; height: 50px;">
    <div>
      <form action="${pageContext.request.contextPath}/admin" method="get">
        <button type="submit" name="action" value="cars" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.cars"/></button>
        <button type="submit" name="action" value="'user'" style="border: none; margin-right: 20px; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.users"/></button>
        <button type="submit" name="action" value="'manager'" style="border: none; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.managers"/></button>
      </form>
    </div>
  </nav>
</c:if>

<c:if test="${sessionScope.user ne null}">
  <nav style="margin-top: 75px; background-color: #EDFCED; height: 50px;">
    <div>
      <form action="${pageContext.request.contextPath}/displayOrder" method="get">
        <button type="submit" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="my_orders"/></button>
      </form>
    </div>
  </nav>
</c:if>








<div class="container mt-5">

  <div class="d-flex justify-content-between align-items-center mb-3" style="padding-top: 120px">

    <span style="font-size: 24px; font-weight: bold; display: inline-block"><fmt:message key="our.cars"/></span>
    <form action="${pageContext.request.contextPath}/sortBookPage" method="get" style=" margin-left: 100px; display: inline-block">

      <i style="margin-left: 250px; display: inline-block"><fmt:message key="select.records"/></i>
      <select class="form-control" id="records" name="recordsPerPage" style="height: 35px; width: 80px;margin-left: 10px; display: inline-block" onchange='submit();'>
        <option value="" selected disabled hidden>6</option>
        <option value="3" ${param.recordsPerPage eq "3" ? "selected" : ""}>3</option>
        <option value="6" ${param.recordsPerPage eq "6" ? "selected" : ""}>6</option>
        <option value="12" ${param.recordsPerPage eq "12" ? "selected" : ""}>12</option>
      </select>

      <i style="margin-left: 20px;"><fmt:message key="sort.by"/></i>
      <select name="sort" class="form-control" style="width: 150px; height: 35px; border-radius: 8px; margin-left: 10px;display: inline-block" onchange='submit();'>
        <option value="" disabled selected><fmt:message key="choose"/></option>
        <option  value="name" ${param.sort eq "name" ? "selected" : ""}><fmt:message key="name"/></option>
        <option value="price" ${param.sort eq "price" ? "selected" : ""}><fmt:message key="price"/></option>
        <option value="brand" ${param.sort eq "brand" ? "selected" : ""}><fmt:message key="brand"/></option>
      </select>
      </label>
      <input type="hidden" name="currentPage" value="1">
      <button type="submit" style="color: white; border-radius: 5px; background-color: green; margin-left: 20px; border: green; width: 100px; height: 30px; display: none"><fmt:message key="btn.submit"/></button>
    </form>

  </div>



  <div class="row">
  <c:forEach var="car_db" items="${requestScope.cars}">
    <div class="col-md-4" style="padding-bottom: 50px">

      <div class="card">

        <div class="d-flex justify-content-between align-items-center">

          <div class="d-flex flex-row align-items-center time">

            <i class="fa fa-clock-o"></i>
            <small class="ml-1" style="color: green; font-weight: bold"><c:out value="${car_db.quality_class}"/> <fmt:message key="class"/></small>

          </div>
          <c:set var = "brand" value = "${car_db.brand}"/>
          <img src="${pageContext.request.contextPath}/images/brands/${fn:toLowerCase(brand)}.jpg">

        </div>


        <div class="text-center">

          <img src="${pageContext.request.contextPath}/images/cars/${car_db.car_id}.jpg" width="350" height="220">

        </div>

        <div class="text-center">

          <h5><c:out value="${car_db.brand} ${car_db.name}"/></h5>
          <span class="text-success"><fmt:formatNumber value="${car_db.price}"
                                                       type="currency"/><br/>
          </span>
          <br>
          <form action="${pageContext.request.contextPath}/showInfoCar" method="get">
            <input type="hidden" name="carimg" value="/car_rent_war_exploded/images/cars/${car_db.car_id}.jpg">
            <input type="hidden" name="id" value="${car_db.car_id}">
            <button class="card-car-button" type="submit"><fmt:message key="book"/></button>
          </form>
        </div>

      </div>

    </div>
    </c:forEach>
  </div>

</div>




<div class="containerA">
  <div class="pagination p12" style="margin-top: 30px;">
    <ul>
      <c:if test="${requestScope.currentPage != 1}">
        <a href="sortBookPage?sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}"><li><fmt:message key="previous"/></li></a>
      </c:if>

      <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
        <c:choose>
          <c:when test="${requestScope.currentPage eq i}">
            <a class="is-active">
                ${i}</a>
          </c:when>
          <c:otherwise>
            <a href="sortBookPage?sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}"><li>${i}</li></a>

          </c:otherwise>
        </c:choose>
      </c:forEach>

      <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
        <a href="sortBookPage?sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}"><li><fmt:message key="next"/></li></a>

      </c:if>
    </ul>
  </div>
</div>





</body>
</html>
