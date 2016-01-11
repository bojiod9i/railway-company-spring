<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<div class="form-box">
    <h2 class="title"><spring:message code="train.search.label"/></h2>

    <form action="<c:url value='/employee/searchTrains'/>" method="GET">

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
            <input class="btn-submit" type="submit" value="Show">
        </div>
    </form>
</div>
