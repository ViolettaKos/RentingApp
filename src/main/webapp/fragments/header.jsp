<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session"/>
<fmt:setBundle basename="language"/>


<nav>
    <div class="nav-bg"></div>
    <ul style="font-family: system-ui;">
        <li><a href="mainPage.jsp" ><fmt:message key="main"/></a></li>
        <li><a href="bookPage.jsp"><fmt:message key="book"/></a></li>
        <li><a href="profilePage.jsp"><fmt:message key="profile"/></a></li>
        <li><a href="contactsPage.jsp"><fmt:message key="contacts"/></a></li>
    </ul>
    <c:choose>
        <c:when test="${sessionScope.logged eq null}">
            <form method="post">
                <select class="language"  name="locale" onchange='submit();'>
                    <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
                    <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
                </select>
            </form>

    <form action="login.jsp">
        <button class="log_in_button" type="submit"><fmt:message key="log.in"/></button>
    </form>
    <form action="signUp.jsp">
        <button class="register_button" type="submit"><fmt:message key="button.register"/></button>
    </form>
    </c:when>
    <c:otherwise>
        <form method="post">
            <select class="language" style="right: 220px " name="locale" onchange='submit();'>
                <option value="en_US" ${sessionScope.locale eq 'en_US' ? 'selected': ''} selected><fmt:message key="language.en"/></option>
                <option value="uk_UA" ${sessionScope.locale eq 'uk_UA' ? 'selected': ''}><fmt:message key="language.ua"/></option>
            </select>
        </form>
        <form action="${pageContext.request.contextPath}/controller" method="post">
            <button class="log_in_button" style="right: 50px" type="submit" name="command" value="log_out"><fmt:message key="button.logout"/></button>
        </form>
    </c:otherwise>
    </c:choose>
    <div><img class="logoImage" src="img/logo.png"></div>

</nav>