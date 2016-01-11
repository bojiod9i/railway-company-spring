<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<div class="form-box">
    <h2 class="title"><spring:message code="station.registration.label"/></h2>

    <form id="addStationForm" action="<c:url value='/employee/newStation'/>" method="POST">
        <div class="form-row">
            <div class="form-label">
                <label for="stationEnName"><spring:message code="station.name.en.label"/>:</label>
            </div>
            <div class="form-field">
                <input class="ui-widget-content" id="stationEnName" name="stationEnName"/>
            </div>
                        <span class="error-msg" id="invalid_en_station_name_msg" style="display:none;"><spring:message
                                code="station.name.en.empty.error"/></span>
        </div>
        <div class="form-row">
            <div class="form-label">
                <label for="stationRuName"><spring:message code="station.name.ru.label"/>:</label>
            </div>
            <div class="form-field">
                <input class="ui-widget-content" id="stationRuName" name="stationRuName"/>
            </div>
                        <span class="error-msg" id="invalid_ru_station_name_msg" style="display:none;"><spring:message
                                code="station.name.ru.empty.error"/></span>
        </div>
        <div class="form-row">
            <div class="form-label">
                <label for="stationLat"><spring:message code="station.latitude.label"/>:</label>
            </div>
            <div class="form-field">
                <input class="ui-widget-content" id="stationLat" name="stationLat"/>
            </div>
                        <span class="error-msg" id="invalid_lat_msg" style="display:none;"><spring:message
                                code="station.latitude.input.error"/></span>
        </div>
        <div class="form-row">
            <div class="form-label">
                <label for="stationLong"><spring:message code="station.longitude.label"/>:</label>
            </div>
            <div class="form-field">
                <input class="ui-widget-content" id="stationLong" name="stationLong"/>
            </div>
                        <span class="error-msg" id="invalid_long_msg" style="display:none;"><spring:message
                                code="station.longitude.input.error"/></span>
        </div>
        <div class="form_submit">
            <input class="btn-submit" type="submit" value="<spring:message code="register.label"/>">
        </div>
    </form>
</div>