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

    <title><spring:message code="app.title"/></title>

</head>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="employeePanel.jsp"/>

        <c:if test="${!empty foundTrains}">
            <div class="wide-form-box">
                <h2 class="title">Search train</h2>
                <table class="simple-little-table" cellspacing='0'>
                    <tr>
                        <td><spring:message code="train.number.label"/></td>
                        <td><spring:message code="train.direction.label"/></td>
                        <td><spring:message code="train.period.label"/></td>
                        <td><spring:message code="train.departure.label"/></td>
                        <td><spring:message code="train.arrival.label"/></td>
                        <%--<td><spring:message code="passenger.look.label"/></td>--%>
                    </tr>
                    <c:forEach var="train" items="${foundTrains}">
                        <tr>
                            <td>
                                <a href="<c:url value='/employee/lookTrain?trainId=${train.id}'/>">
                                        ${train.id}
                                </a>
                            </td>
                            <c:forEach var="route" items="${train.routes}" varStatus="status">
                                <c:if test="${status.first}">
                                    <td>
                                    <c:choose>
                                        <c:when test="${pageContext.response.locale == 'ru'}">
                                            ${route.departureStation.ruName} -
                                        </c:when>
                                        <c:otherwise>
                                            ${route.departureStation.name} -
                                        </c:otherwise>
                                    </c:choose>
                                    <c:set var="departureDate" scope="request" value="${route.departureDate}"/>
                                </c:if>
                                <c:if test="${status.last}">
                                    <c:choose>
                                        <c:when test="${pageContext.response.locale == 'ru'}">
                                            ${route.arrivalStation.ruName}
                                        </c:when>
                                        <c:otherwise>
                                            ${route.arrivalStation.name}
                                        </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <c:set var="arrivalDate" scope="request" value="${route.arrivalDate}"/>
                                </c:if>
                            </c:forEach>
                            <td>
                                    ${train.period}
                            </td>
                            <td>
                                <fmt:formatDate value="${departureDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${arrivalDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <%--<td>--%>
                                <%--<form action="/LookPassengersByTrain" method="POST">--%>
                                    <%--<input type="hidden" name="trainId" value="${train.id}">--%>
                                    <%--<input class="simple-btn" type="submit"--%>
                                           <%--value="<spring:message code="passenger.do.look.label"/>">--%>
                                <%--</form>--%>
                            <%--</td>--%>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </c:if>
    </div>

    <jsp:include page="../userFunctions.jsp"/>

    <jsp:include page="../messages.jsp"/>
</div>
</body>
</html>