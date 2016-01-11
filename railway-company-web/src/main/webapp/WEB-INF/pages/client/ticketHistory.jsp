<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <meta name="PageType" content="ContentPage"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="<c:url value='/resources/css/jquery-ui-themes-1.11.4/jquery-ui.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/jquery.ui.timepicker.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/combo.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/table.css'/>" rel="stylesheet"/>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    <script src="<c:url value='/resources/js/combo.js'/>"></script>
    <script src="<c:url value='/resources/js/ui.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery.ui.timepicker.js'/>"></script>

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

<script>
</script>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="clientPanel.jsp"/>

        <div class="wide-form-box">
            <h2 class="title"><spring:message code="tickets.label"/></h2>

            <table class="simple-little-table" cellspacing='0'>
                <tr>
                    <td><spring:message code="date.purchase"/></td>
                    <td><spring:message code="train.number.label"/></td>
                    <td><spring:message code="route.label"/></td>
                    <td><spring:message code="date.departure.label"/></td>
                    <td><spring:message code="date.arrival.label"/></td>
                    <td><spring:message code="passenger.label"/></td>
                    <td><spring:message code="cost.label"/></td>
                    <td><spring:message code="print.label"/></td>
                </tr>
                <c:forEach var="ticket" items="${tickets}">

                    <c:forEach var="route" items="${ticket.routes}" varStatus="status">
                        <c:set var="cost" scope="request" value="${route.cost + cost}"/>
                        <c:if test="${status.first}">
                            <c:set var="trainId" scope="request" value="${route.train.id}"/>
                            <c:set var="departureDate" scope="request" value="${route.departureDate}"/>
                            <c:choose>
                                <c:when test="${pageContext.response.locale == 'ru'}">
                                    <c:set var="fromStation" scope="request" value="${route.departureStation.ruName}"/>
                                </c:when>
                                <c:otherwise>
                                    <c:set var="fromStation" scope="request" value="${route.departureStation.name}"/>
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
                    </c:forEach>

                    <tr>${ticket.purchaseDate}</tr>

                    <tr>${trainId}</tr>

                    <tr>${fromStation} - ${toStation}</tr>

                    <tr>${departureDate}</tr>

                    <tr>${arrivalDate}</tr>

                    <tr>${ticket.passenger.firstName} ${ticket.passenger.lastName}</tr>

                    <tr>${cost}</tr>

                    <tr><a href="/"> <img src="<c:url value='/resources/images/pdf-icon.gif'/>"></a></tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <jsp:include page="../userFunctions.jsp"/>

    <jsp:include page="../messages.jsp"/>
</div>
</body>
</html>