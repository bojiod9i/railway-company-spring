<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="form-box">
    <h2 class="title"><spring:message code="station.timetable.label"/></h2>

    <form action="<c:url value='/stationTimetable'/>" method="GET">
        <div class="form-row stationSelection" id="timetableStation">
            <div class="form-label">
                <label><spring:message code="station.label"/> </label>
            </div>
            <div class="form-field">
                <select class="combobox" id="timetableStationField" name="stationId">
                    <option value=""></option>
                </select>
                <img onclick="javascript:$('#timetableStationDialog').dialog('open')"
                     src="<c:url value='/resources/images/map.png'/>" alt="Map"
                     style="vertical-align: middle; height:1.2em">

                <div class="map-dialog-window" id="timetableStationDialog" title="Station Selection">
                    <div class="map" id="timetableStationMap"></div>
                </div>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="station.input.error"/></span>
        </div>

        <div class="form-row">
            <div class="form-label">
                <label for="timetableFromDate"><spring:message code="date.from.label"/>: </label>
            </div>
            <div class="form-field">
                <input type="text" class="ui-widget-content" id="timetableFromDate" name="timetableFromDate"/>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="date.from.input.error"/></span>
        </div>

        <div class="form-row">
            <div class="form-label">
                <label for="timetableToDate"><spring:message code="date.to.label"/>: </label>
            </div>
            <div class="form-field">
                <input type="text" class="ui-widget-content" id="timetableToDate" name="timetableToDate"/>
            </div>
            <span class="error-msg" style="display:none;"><spring:message code="date.to.input.error"/></span>
        </div>
        <div class="form_submit">
            <input class="btn-submit" type="submit" value="<spring:message code="show.label"/>">
        </div>
    </form>
</div>
