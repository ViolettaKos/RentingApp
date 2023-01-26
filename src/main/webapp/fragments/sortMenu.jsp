<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language"/>

<form action="${pageContext.request.contextPath}/controller" method="get"
      style=" margin-left: 100px; display: inline-block">
    <input type="hidden" name="command" value="show_cars">

    <i style="margin-left: 250px; display: inline-block"><fmt:message key="select.records"/></i>
    <select class="form-control" id="records" name="recordsPerPage"
            style="height: 35px; width: 80px;margin-left: 10px; display: inline-block" onchange='submit();'>
        <option value="" selected disabled hidden>6</option>
        <option value="3" ${param.recordsPerPage eq "3" ? "selected" : ""}>3</option>
        <option value="6" ${param.recordsPerPage eq "6" ? "selected" : ""}>6</option>
        <option value="12" ${param.recordsPerPage eq "12" ? "selected" : ""}>12</option>
    </select>

    <i style="margin-left: 20px;"><fmt:message key="sort.by"/></i>
    <select name="sort" class="form-control" style="width: 150px; height: 35px; border-radius: 8px; margin-left: 10px;display: inline-block" onchange='submit();'>
        <option value="" disabled selected><fmt:message key="choose"/></option>
        <option value="name" ${param.sort eq "name" ? "selected" : ""}><fmt:message key="name"/></option>
        <option value="price" ${param.sort eq "price" ? "selected" : ""}><fmt:message key="price"/></option>
        <option value="brand" ${param.sort eq "brand" ? "selected" : ""}><fmt:message key="brand"/></option>
    </select>
    </label>
    <input type="hidden" name="currentPage" value="1">
    <button type="submit" style="color: white; border-radius: 5px; background-color: green; margin-left: 20px; border: green; width: 100px; height: 30px; display: none"><fmt:message key="btn.submit"/></button>
</form>
