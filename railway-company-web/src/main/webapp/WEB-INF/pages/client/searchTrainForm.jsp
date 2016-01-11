<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="form-box">
    <h2 class="title"><spring:message code="route.planner.label"/></h2>

    <form action="<c:url value='/searchTrain'/>" method="GET">
        <div class="form-row stationSelection" id="departureStation">
            <div class="form-label">
                <label for="departureStationField"><spring:message code="station.departure.label"/></label>
            </div>
            <div class="form-field">
                <select class="combobox" id="departureStationField" name="departureStationId">
                    <option value=""></option>
                </select>
                <img onclick="javascript:$('#departureStationDialog').dialog('open')"
                     src="<c:url value='/resources/images/map.png'/>" alt="Map"
                     style="vertical-align: middle; height:1.2em">

                <div class="map-dialog-window" id="departureStationDialog" title="Selection on map">
                    <div class="map" id="departureStationMap"></div>
                </div>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="station.departure.input.error"/></span>
        </div>

        <div class="form-row stationSelection" id="arrivalStation">
            <div class="form-label">
                <label for="arrivalStationField"><spring:message code="station.arrival.label"/></label>
            </div>
            <div class="form-field">
                <select class="combobox" id="arrivalStationField" name="arrivalStationId">
                    <option value=""></option>
                </select>
                <img onclick="javascript:$('#arrivalStationDialog').dialog('open')"
                     src="<c:url value='/resources/images/map.png'/>" alt="Map"
                     style="vertical-align: middle; height:1.2em">

                <div class="map-dialog-window" id="arrivalStationDialog" title="Selection on map">
                    <div class="map" id="arrivalStationMap"></div>
                </div>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="station.arrival.input.error"/></span>
        </div>

        <div class="form-row">
            <h2 class="title"><spring:message code="date.range.label"/></h2>
        </div>

        <div class="form-row">
            <div class="form-label">
                <label for="scheduleFromDate"><spring:message code="date.from.label"/></label>
            </div>
            <div class="form-field">
                <input type="text" class="ui-widget-content" id="scheduleFromDate" name="scheduleFromDate"/>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="date.from.input.error"/></span>
        </div>

        <div class="form-row">
            <div class="form-label">
                <label for="scheduleToDate"><spring:message code="date.to.label"/></label>
            </div>
            <div class="form-field">
                <input type="text" class="ui-widget-content" id="scheduleToDate" name="scheduleToDate"/>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="date.to.input.error"/></span>
        </div>

        <div class="form_submit">
            <input class="btn-submit" type="submit" value="<spring:message code="search.doit.label"/>">
        </div>
    </form>
</div>