<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div class="lang-panel">
    <a href="?lang=ru">
        <img src="<c:url value='/resources/images/flag_ru.png'/>" border="0"></a>
    <a href="?lang=en">
        <img src="<c:url value='/resources/images/flag_en.png'/>" border="0"></a>
</div>

<div class="logo-header">
    <security:authorize access="hasRole('ROLE_OWNER')">
        <a href="<c:url value='/owner/index'/>">
            <img height="100" alt="Railway Logo" src="<c:url value='/resources/images/logo.png'/>">
        </a>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_EMPLOYEE')">
        <a href="<c:url value='/employee/index'/>">
            <img height="100" alt="Railway Logo" src="<c:url value='/resources/images/logo.png'/>">
        </a>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_CLIENT')">
        <a href="<c:url value='/'/>">
            <img height="100" alt="Railway Logo" src="<c:url value='/resources/images/logo.png'/>">
        </a>
    </security:authorize>
    <security:authorize access="!isAuthenticated()">
        <a href="<c:url value='/'/>">
            <img height="100" alt="Railway Logo" src="<c:url value='/resources/images/logo.png'/>">
        </a>
    </security:authorize>
</div>
