<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 20/12/2022
  Time: 12:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <title><fmt:message key="title.pageAdminMg"/></title>
    <link rel="stylesheet" type="text/css" href="style/navbar.css"/>
    <link rel="stylesheet" type="text/css" href="style/main.css"/>
    <link rel="stylesheet" type="text/css" href="style/admin_tables.css"/>

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct" crossorigin="anonymous"></script>


</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<jsp:include page="fragments/roleMenu.jsp"/>


<h1 style="margin-left: 50px; margin-top: 200px; color: black"><fmt:message key="title.adminMg"/></h1>


<form action="addManager.jsp" style="margin-top: 30px; display: inline-block">
    <div style="margin-left: 50px;">
        <button type="submit" style="color: white; border-radius: 5px; background-color: green; border: green; width: 200px; height: 30px"><fmt:message key="reg.mg"/></button>
    </div>
</form>
<jsp:include page="fragments/sortMenu.jsp"/>
<table style="margin-top: 50px; margin-left: auto; margin-right: auto">
    <thead>
    <tr>
        <td><fmt:message key="first_name"/></td>
        <td><fmt:message key="last_name"/></td>
        <td><fmt:message key="login"/></td>
        <td><fmt:message key="email"/></td>
        <td><fmt:message key="phone_number"/></td>
        <td><fmt:message key="edit"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="manager_db" items="${requestScope.manager}">
        <tr>
            <td><c:out value="${manager_db.firstName}"/></td>
            <td><c:out value="${manager_db.lastName}"/></td>
            <td><c:out value="${manager_db.username}"/></td>
            <td><c:out value="${manager_db.email}"/></td>
            <td><c:out value="${manager_db.telephone}"/></td>
            <td>

                <form action="${pageContext.request.contextPath}/controller" method="get">
                    <input type="hidden" name="command" value="display_info">
                    <input type="hidden" name="login" value="${manager_db.username}">
                    <button type="submit" style="padding: 0 10px 0 0; border: none; background: none; color: red; font-style: italic; font-size: 16px; text-decoration: underline"><fmt:message key="edit"/></button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>

</table>

<jsp:include page="fragments/pagination.jsp"/>
</body>
</html>
