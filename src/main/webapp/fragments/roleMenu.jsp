<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 24/01/2023
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language"/>

<c:if test="${sessionScope.logged ne null}">
<nav style="margin-top: 89px; background-color: #EDFCED; height: 50px;">
<c:if test="${sessionScope.role eq 'user'}">
    <div>
      <form action="${pageContext.request.contextPath}/controller" method="get">
          <input type="hidden" name="command" value="show_my_orders">
        <button type="submit" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="my_orders"/></button>
      </form>
    </div>
</c:if>
<c:if test="${sessionScope.role eq 'manager'}">
    <div>
      <form action="${pageContext.request.contextPath}/controller" method="get">
        <button type="submit" name="action" value="view-orders" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="orders"/></button>
      </form>
    </div>
</c:if>

<c:if test="${sessionScope.role eq 'admin'}">
    <div>
      <form action="${pageContext.request.contextPath}/controller" method="get">
        <button type="submit" name="command" value="show_admin_cars" style="border: none; margin-left: 50px; margin-right: 20px; margin-top: 10px; background-color: #EDFCED; text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.cars"/></button>
        <button type="submit" name="command" value="show_admin_users" style="border: none; margin-right: 20px; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.users"/></button>
        <button type="submit" name="command" value="show_admin_managers" style="border: none; margin-top: 10px; background-color: #EDFCED;text-decoration: underline; font-style: italic; font-size: 18px;"><fmt:message key="btn.managers"/></button>
      </form>
    </div>
</c:if>
</nav>
    </c:if>