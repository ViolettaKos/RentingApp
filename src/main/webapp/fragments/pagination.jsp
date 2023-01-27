<%--
  Created by IntelliJ IDEA.
  User: suraw
  Date: 24/01/2023
  Time: 23:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<div class="containerA">
    <div class="pagination p12" style="margin-top: 30px;">
        <ul>
            <c:if test="${requestScope.currentPage != 1}">
                <a href="controller?command=${requestScope.command}&sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage-1}">
                    <li><fmt:message key="previous"/></li>
                </a>
            </c:if>

            <c:forEach begin="1" end="${requestScope.noOfPages}" var="i">
                <c:choose>
                    <c:when test="${requestScope.currentPage eq i}">
                        <a class="is-active">
                                ${i}</a>
                    </c:when>
                    <c:otherwise>
                        <a href="controller?command=${requestScope.command}&sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${i}">
                            <li>${i}</li>
                        </a>

                    </c:otherwise>
                </c:choose>
            </c:forEach>

            <c:if test="${requestScope.currentPage lt requestScope.noOfPages}">
                <a href="controller?command=${requestScope.command}&sort=${param.sort}&recordsPerPage=${requestScope.recordsPerPage}&currentPage=${requestScope.currentPage+1}">
                    <li><fmt:message key="next"/></li>
                </a>
            </c:if>
        </ul>
    </div>
</div>
