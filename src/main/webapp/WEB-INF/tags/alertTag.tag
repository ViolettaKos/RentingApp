<%@ attribute name="msg" required="true" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="language"/>

<c:if test="${not empty msg}">
    <hr>
    <div class="alert alert-danger">
        <strong><fmt:message key="oops"/> </strong>${msg}
        <fmt:message key="try"/>
    </div>
    <hr>
</c:if>