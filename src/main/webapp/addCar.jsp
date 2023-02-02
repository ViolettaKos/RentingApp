<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 23/12/2022
  Time: 21:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>


<html>
<head>
  <title><fmt:message key="title.pageAdminNewCar"/></title>
  <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
  <link rel="stylesheet" type="text/css" href="style/main.css"/>

  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>

</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<div style="margin-top: 150px" class="container">
  <div class="registration mx-auto d-block w-100">

    <div class="page-header text-center">
      <h1><fmt:message key="reg.car"/></h1>
    </div>

    <form name="member-registration" action="${pageContext.request.contextPath}/controller" method="post"
          class="form-validate form-horizontal well" enctype="multipart/form-data">
      <input type="hidden" name="command" value="add_car">
      <fieldset>
        <div class="form-group">
          <label><fmt:message key="select.brand"/> *</label>
          <input onchange="myFunction(this.value)" name="brand" type="text" class="col-sm-6 custom-select custom-select-sm" required list="brand">
              <datalist id="brand">
              <c:forEach var="brand" items="${requestScope.brand}">
            <option value="${brand}">${brand}</option>
              </c:forEach>
              </datalist>
        </div>

        <div id="choose" class="form-group" style="display: none">
          <label><fmt:message key="upload.logo"/>:</label>
          <input type = "file" id="logo" name = "logo" size = "50"/>
        </div>

        <div class="form-group">
          <label><fmt:message key="name"/> *</label>
          <input type="text" class="form-control" required name="name"
                 pattern="^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$"
                 title="<fmt:message key="warn.Latin"/>">
        </div>
        <div class="form-group">
          <label><fmt:message key="qualityC"/> *</label>
          <input type="text" class="form-control" required name="quality_class"
                 pattern="^[A-E]"
                 title="<fmt:message key="warn.class"/>">
        </div>
        <div class="form-group">
          <label><fmt:message key="price"/> *</label>
          <input type="number" class="form-control" required name="price"
                 pattern="^\d+$"
                 max="10000"
                 min="10"
                 title="<fmt:message key="warn.digits"/>">
        </div>
        <div class="form-group">
        <label><fmt:message key="upload.image"/>: *</label>
          <input type = "file" name = "file" id="file" size = "50" required/>
        </div>
        <div class="d-flex justify-content-between align-items-center" style="margin-top: 30px">
          <div class="form-group d-flex justify-content-start">
            <button type="submit" class="submit"><fmt:message key="btn.submit"/></button>
          </div>
        </div>
      </fieldset>
    </form>
  </div>
</div>


<script>
  function myFunction(val) {

    var list = [
      <c:forEach items="${requestScope.brand}" var="elem" varStatus="currentStatus">
      "${elem}"
      <c:if test="${not currentStatus.last}">
      ,
      </c:if>
      </c:forEach>
    ];
    const isItemInSet = list.includes(val);
    if(isItemInSet) {
      document.getElementById("choose").style.display="none";
    }
    else if(!isItemInSet) {
      document.getElementById("choose").style.display="block";
    }
  }
</script>
</body>
</html>
