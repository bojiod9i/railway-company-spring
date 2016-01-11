<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<form action="<c:url value='/employee/newTrain'/>" method="POST">
    <div id="add-schedules-form" class="wide-form-box">
        <h2 class="title"><spring:message
                code="train.registration.label"/></h2>

        <div class="form-row">
            <div class="label">
                <label> <spring:message
                        code="train.period.label"/> </label>
            </div>
            <div class="form-field">
                <select class="combobox frontElement" name="period">
                    <option value="1"> Single</option>
                    <option value="2"> Everyday</option>
                    <option value="3"> Every Week</option>
                    <option value="4"> Every Month</option>
                </select>
            </div>
            <div class="label">
                <label><spring:message
                        code="train.seats.label"/> </label>
            </div>
            <div class="form-field">
                <input class="ui-widget-content" id="seats" type="text" class="input" name="seats"/>
            </div>
            <input id="addSchedule" type="button" value='<spring:message
                                code="stay.add.label"/>' class="simple-btn">
            <span class="error-msg" style="display:none;">Please input seats </span>
        </div>
    </div>
    <div class="form_submit">
        <input class="btn-submit" type="submit" value="<spring:message
                                code="register.label"/>">
    </div>
</form>
<div class="stay-form" id="template_schedule" style="display: none">
    <div id='departureStationId' class="form-row stationSelection">
        <h3><spring:message code="train.departure.label"/></h3>

        <div class="label">
            <label> <spring:message code="station.label"/> </label>
        </div>
        <div class="form-field">
            <select id="departureStationIdField" name="departureStationId">
                <option value=""></option>
            </select>
        </div>
        <div class="label">
            <label><spring:message code="date.label"/></label>
        </div>
        <div class="form-field">
            <input class="ui-widget-content" type="text" maxlength="10" size="10"
                   id="departureDate" name="departureDate"/>
        </div>
        <div class="label">
            <label> <spring:message code="time.label"/> </label>
        </div>
        <div class="form-field">
            <input class="ui-widget-content" type="text" maxlength="5" size="5" id="departureTime"
                   name="departureTime"/>
        </div>
        <span class="error-msg" style="display:none;">Please fill all fields</span>
    </div>
    <div class="form-row stationSelection" id='arrivalStationId'>
        <h3><spring:message code="train.arrival.label"/></h3>

        <div class="label">
            <label><spring:message code="station.label"/></label>
        </div>
        <div class="form-field">
            <select id="arrivalStationIdField" name="arrivalStationId">
                <option value=""></option>
            </select>
        </div>
        <div class="label">
            <label><spring:message code="date.label"/></label>
        </div>
        <div class="form-field">
            <input class="ui-widget-content" type="text" maxlength="10" size="10"
                   id="arrivalDate" name="arrivalDate"/>
        </div>
        <div class="label">
            <label><spring:message code="time.label"/></label>
        </div>
        <div class="form-field">
            <input class="ui-widget-content" type="text" maxlength="5" size="5" id="arrivalTime"
                   name="arrivalTime"/>
        </div>
        <span class="error-msg" style="display:none;">Please fill all fields</span>
    </div>
    <div class="form-row">
        <div class="label">
            <label> <spring:message code="cost.label"/> </label>
        </div>
        <div class="form-field">
            <input class="ui-widget-content" type="text" maxlength="5" size="5" id="cost" name="cost"
                   name="departureTime"/>
        </div>
        <span class="error-msg" style="display:none;">Please fill all fields</span>
    </div>
</div>

