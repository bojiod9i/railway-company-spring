<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
    <meta name="PageType" content="ContentPage"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="keywords" content="railway, trip, travel"/>

    <link href="<c:url value='/resources/css/jquery-ui-themes-1.11.4/jquery-ui.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/combo.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/table.css'/>" rel="stylesheet"/>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    <script src="<c:url value='/resources/js/combo.js'/>"></script>
    <script src="<c:url value='/resources/js/ui.js'/>"></script>
    <c:choose>
        <c:when test="${pageContext.response.locale == 'ru'}">
            <script src="https://api-maps.yandex.ru/2.1/?lang=ru_RU" type="text/javascript"></script>
        </c:when>
        <c:otherwise>
            <script src="https://api-maps.yandex.ru/2.1/?lang=en_US" type="text/javascript"></script>
        </c:otherwise>
    </c:choose>

    <title><spring:message code="app.title"/></title>

</head>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="clientPanel.jsp"/>

        <c:if test="${!empty foundTrains}">
            <div class="wide-form-box">
                <h2 class="title"><spring:message code="timetable.for.label"/>
                    <c:choose>
                        <c:when test="${pageContext.response.locale == 'ru'}">
                            ${station.ruName}
                        </c:when>
                        <c:otherwise>
                            ${station.name}
                        </c:otherwise>
                    </c:choose>
                </h2>
                <table class="simple-little-table" cellspacing='0'>
                    <tr>
                        <td><spring:message code="train.number.label"/></td>
                        <td><spring:message code="train.direction.label"/></td>
                        <td><p><spring:message code="train.departure.label"/></p>

                            <p class="hint">
                                <spring:message code="from.label"/>
                                <c:choose>
                                    <c:when test="${pageContext.response.locale == 'ru'}">
                                        ${station.ruName}
                                    </c:when>
                                    <c:otherwise>
                                        ${station.name}
                                    </c:otherwise>
                                </c:choose>
                            </p>
                        </td>
                        <td><p><spring:message code="train.arrival.label"/></p>
                        </td>
                        <td><spring:message code="ticket.buy.label"/></td>
                    </tr>
                    <c:forEach var="train" items="${foundTrains}">
                        <c:forEach var="route" items="${train.routes}" varStatus="status">
                            <c:if test="${status.first}">
                                <c:choose>
                                    <c:when test="${pageContext.response.locale == 'ru'}">
                                        <c:set var="fromStation" scope="request"
                                               value="${route.departureStation.ruName}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="fromStation" scope="request"
                                               value="${route.departureStation.name}"/>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
                            <c:if test="${status.last}">
                                <c:choose>
                                    <c:when test="${pageContext.response.locale == 'ru'}">
                                        <c:set var="toStation" scope="request" value="${route.arrivalStation.ruName}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="toStation" scope="request" value="${route.arrivalStation.name}"/>
                                    </c:otherwise>
                                </c:choose>
                                <c:set var="arrivalDate" scope="request" value="${route.arrivalDate}"/>
                            </c:if>
                            <c:if test="${route.departureStation eq station}">
                                <c:set var="departureDate" scope="request" value="${route.departureDate}"/>
                            </c:if>
                        </c:forEach>
                        <tr>
                            <td>
                                <a href="<c:url value='/lookTrain?trainId=${train.id}'/>">
                                        ${train.id}
                                </a>
                            </td>
                            <td>
                                    ${fromStation} - ${toStation}
                            </td>
                            <td>
                                <fmt:formatDate value="${departureDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${arrivalDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <td>
                                <form action="/client/buyTicket" method="GET">
                                    <input type="hidden" name="trainId" value="${train.id}">
                                    <input type="hidden" name="startStationId" value="${station.id}">
                                    <input class="simple-btn" type="submit"
                                           value="<spring:message code="ticket.dobuy.label"/>">
                                </form>
                            </td>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>

    <jsp:include page="../messages.jsp"/>

    <jsp:include page="../userFunctions.jsp"/>

</div>
</body>
</html>
