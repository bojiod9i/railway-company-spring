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

    <c:if test="${!empty infoTrain}">
    var Station = function (params) {
        this.name = params.name;
        this.lat = params.lat;
        this.long = params.long;
    };

    var stations = [
        <c:forEach var="route" items="${infoTrain.routes}" varStatus="status">
        new Station({
            <c:choose>
            <c:when test="${pageContext.response.locale == 'ru'}">
            name: '${route.departureStation.ruName}',
            </c:when>
            <c:otherwise>
            name: '${route.departureStation.name}',
            </c:otherwise>
            </c:choose>
            lat: ${route.departureStation.latitude},
            long: ${route.departureStation.longitude}
        }),
        <c:if test="${status.last}">
        new Station({
            <c:choose>
            <c:when test="${pageContext.response.locale == 'ru'}">
            name: '${route.arrivalStation.ruName}',
            </c:when>
            <c:otherwise>
            name: '${route.arrivalStation.name}',
            </c:otherwise>
            </c:choose>
            lat: ${route.arrivalStation.latitude},
            long: ${route.arrivalStation.longitude}
        })
        </c:if>
        </c:forEach>
    ];

    ymaps.ready(function () {
        var map = new ymaps.Map('trainInfoMap', {
            center: [stations[0].lat, stations[0].long],
            zoom: 5,
            controls: []
        });
        var geometry = [];
        for (var i = 0; i < stations.length; i++) {
            geometry.push([stations[i].lat, stations[i].long]);
            var placemark = new ymaps.Placemark([stations[i].lat, stations[i].long], {
                hintContent: stations[i].name
            });
            map.geoObjects.add(placemark);
        }

        var properties = {};
        var options = {
            draggable: true,
            strokeColor: '#ff0000',
            strokeWidth: 5

        };
        var polyline = new ymaps.Polyline(geometry, properties, options);

        map.geoObjects.add(polyline);
    });
    </c:if>

</script>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="employeePanel.jsp"/>

        <c:if test="${!empty infoTrain}">
            <div class="wide-form-box">
                <h2 class="title"><spring:message code="train.number.label"/> ${infoTrain.id} </h2>

                <div id="trainInfoMap" class="map-on-page">
                </div>

                <table class="simple-little-table" cellspacing='0'>
                    <tr>
                        <td><spring:message code="route.label"/></td>
                        <td><spring:message code="date.departure.label"/></td>
                        <td><spring:message code="date.arrival.label"/></td>
                        <td><spring:message code="tickets.label"/></td>
                    </tr>
                    <c:forEach var="route" items="${infoTrain.routes}">
                        <tr>
                            <td>
                                <c:if test="${pageContext.response.locale == 'en'}">
                                    ${route.departureStation.name} - ${route.arrivalStation.name}
                                </c:if>
                                <c:if test="${pageContext.response.locale == 'ru'}">
                                    ${route.departureStation.ruName} - ${route.arrivalStation.ruName}
                                </c:if>
                            </td>
                            <td>
                                <fmt:formatDate value="${route.departureDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <td>
                                <fmt:formatDate value="${route.arrivalDate}" pattern="dd-MM-yyyy HH:mm"/>
                            </td>
                            <td>
                                <a href="<c:url value='/employee/lookSellingTickets?trainId=${infoTrain.id}'/>">
                                        ${fn:length(route.tickets)}/${infoTrain.seats} </a>
                            </td>
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