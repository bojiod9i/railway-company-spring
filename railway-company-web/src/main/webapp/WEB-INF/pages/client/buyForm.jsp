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

    <script>
        $(window).on("load", function () {
            var departureStationId, arrivalStationId, passenger, firstname, lastname, birthday;

            $('#acceptDialog').dialog({
                width: '550px',
                height: 'auto'
            });

            $("#birthday").datepicker({
                dateFormat: "dd/mm/yy"
            });

            $("#passengerSelector").combobox({
                        change: function () {
                            if ($("#passengerSelector").val() != -1) {
                                $('#firstname').prop('disabled', true);
                                $('#firstname').css('background', '#B3B8BC');
                                $('#lastname').prop('disabled', true);
                                $('#lastname').css('background', '#B3B8BC');
                                $('#birthday').prop('disabled', true);
                                $('#birthday').css('background', '#B3B8BC');
                            } else {
                                $('#firstname').prop('disabled', false);
                                $('#firstname').css('background', 'white');
                                $('#lastname').prop('disabled', false);
                                $('#lastname').css('background', 'white');
                                $('#birthday').prop('disabled', false);
                                $('#birthday').css('background', 'white');
                            }
                        }
                    }
            );

            $('#buyTicketForm').on('submit', function () {
                var valid = true;
                departureStationId = $('#departureStationIdForBuyTicket').val();
                arrivalStationId = $('#arrivalStationIdForBuyTicket').val();
                passenger = $('#passenger').val();
                firstname = $('#firstname').val();
                lastname = $('#lastname').val();
                birthday = $('#birthday').val();

                if (departureStationId == "") {
                    $('#invalid_station_departure').show();
                    valid = false;
                }
                if (arrivalStationId == "") {
                    $('#invalid_station_arrival').show();
                    valid = false;
                }
                if (passenger != -1) {
                    if (firstname == "") {
                        $('#invalid_firstname').show();
                        valid = false;
                    }
                    if (lastname == "") {
                        $('#invalid_lastname').show();
                        valid = false;
                    }
                    if (birthday == "") {
                        $('#invalid_birthday').show();
                        valid = false;
                    }
                }
                if (valid) {
                    $.ajax({
                        type: "GET",
                        url: "<c:url value='/getCost'/>?trainId=${trainId}&&startStationId=" + departureStationId + "&&endStationId=" + arrivalStationId,
                        success: function (cost) {
                            $('#costField').append(cost);
                            $('#acceptDialog').("open");
                        },
                        error: function (cost) {
                            window.location.replace("errorPage");
                        }
                    });
                }
                return false;
            });
            $('#proofForm').on('submit', function () {
                $('<input />').attr('type', 'hidden')
                        .attr('name', "startStationId")
                        .attr('value', departureStationId)
                        .appendTo('#form');
                $('<input />').attr('type', 'hidden')
                        .attr('name', "endStationId")
                        .attr('value', arrivalStationId)
                        .appendTo('#form');
                if (passenger == -1) {
                    $('<input />').attr('type', 'hidden')
                            .attr('name', "firstname")
                            .attr('value', firstname)
                            .appendTo('#form');
                    $('<input />').attr('type', 'hidden')
                            .attr('name', "lastname")
                            .attr('value', lastname)
                            .appendTo('#form');
                    $('<input />').attr('type', 'hidden')
                            .attr('name', "birthday")
                            .attr('value', birthday)
                            .appendTo('#form');
                } else {
                    $('<input />').attr('type', 'hidden')
                            .attr('name', "passengerId")
                            .attr('value', passenger)
                            .appendTo('#form');
                }
                return true;
            });
        });
    </script>

    <title><spring:message code="app.title"/></title>

</head>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="clientPanel.jsp"/>

        <form id="buyTicketForm" action="<c:url value='/client/buy'/>" method="POST">
            <div id="add-schedules-form" class="form-box">
                <h2 class="title"><spring:message code="ticket.buy.title"/> №${train.id} </h2>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="station.departure.label"/> </label>
                    </div>
                    <div class="form-field">
                        <select id="departureStationIdForBuyTicket" class="combobox" name="departureStationId">
                            <option value=""></option>
                            <c:forEach var="route" items="${train.routes}">
                                <c:choose>
                                    <c:when test="${pageContext.response.locale == 'ru'}">
                                        <c:set var="departureStationName" scope="request"
                                               value="${route.departureStation.ruName}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="departureStationName" scope="request"
                                               value="${route.departureStation.name}"/>
                                    </c:otherwise>
                                </c:choose>

                                <option value="${route.departureStation.id}"
                                        <c:if test="${route.departureStation.id eq startStationId}">
                                            selected="selected"
                                        </c:if>
                                        >${departureStationName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <span id="invalid_station_departure" class="error-msg" style="display:none;"> <spring:message
                            code="station.departure.input.error"/> </span>
                </div>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="station.arrival.label"/> </label>
                    </div>
                    <div class="form-field">
                        <select id="arrivalStationIdForBuyTicket" class="combobox">
                            <option value=""></option>
                            <c:forEach var="route" items="${train.routes}">
                                <c:choose>
                                    <c:when test="${pageContext.response.locale == 'ru'}">
                                        <c:set var="arrivalStationName" scope="request"
                                               value="${route.arrivalStation.ruName}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="arrivalStationName" scope="request"
                                               value="${route.arrivalStation.name}"/>
                                    </c:otherwise>
                                </c:choose>

                                <option value="${route.arrivalStation.id}"
                                        <c:if test="${route.arrivalStation.id eq endStationId}">
                                            selected="selected"
                                        </c:if>
                                        >${arrivalStationName}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <span id="invalid_station_arrival" class="error-msg" style="display:none;"><spring:message
                            code="station.arrival.input.error"/> </span>
                </div>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="passenger.label"/> </label>
                    </div>
                    <div class="form-field">
                        <select id="passengerSelector">
                            <option value="-1"></option>
                            <c:forEach var="passenger" items="${user.passengers}">
                                <option value="${passenger.id}">
                                        ${passenger.firstName} ${passenger.firstName} <fmt:formatDate
                                        value="${passenger.birthday}" pattern="dd/MM/yyyy"/>
                                </option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="passenger.firstname.label"/> </label>
                    </div>
                    <div class="form-field">
                        <div class="form-field">
                            <input type="text" class="ui-widget-content" id="firstname" name="firstname"/>
                        </div>
                    </div>
                    <span id="invalid_firstname" class="error-msg" style="display:none;"><spring:message
                            code="passenger.firstname.input.error"/></span>
                </div>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="passenger.lastname.label"/> </label>
                    </div>
                    <div class="form-field">
                        <div class="form-field">
                            <input type="text" class="ui-widget-content" id="lastname" name="lastname"/>
                        </div>
                    </div>
                    <span id="invalid_lastname" class="error-msg" style="display:none;"><spring:message
                            code="passenger.lastname.input.error"/> </span>
                </div>

                <div class="form-row">
                    <div class="form-label">
                        <label> <spring:message code="passenger.birthday.label"/> </label>
                    </div>
                    <div class="form-field">
                        <div class="form-field">
                            <input type="text" class="ui-widget-content" id="birthday" name="birthday"/>
                        </div>
                    </div>
                    <span id="invalid_birthday" class="error-msg" style="display:none;"><spring:message
                            code="passenger.birthday.input.error"/> </span>
                </div>
                <div class="form_submit">
                    <input class="btn-submit" type="submit" value="<spring:message code="ticket.dobuy.label"/>">
                </div>
            </div>
        </form>

    </div>

    <jsp:include page="../userFunctions.jsp"/>

    <jsp:include page="../messages.jsp"/>
</div>

<div id="acceptDialog" title="<spring:message code="purchase.proof.label"/>">
    <form id="proofForm" action="<c:url value='/client/buy'/>" method="POST">
        <input type="hidden" name="trainId" value="${trainId}"/>

        <div class="form-box">
            <h2 class="title"><spring:message code="purchase.proof.label"/></h2>

            <h3 id="costField"><spring:message code="ticket.cost.label"/>:</h3>

            <div class="form_submit">
                <input class="btn-submit" type="submit" value="<spring:message code="ticket.dobuy.label"/>">
            </div>
        </div>
    </form>
</div>

</body>
</html>