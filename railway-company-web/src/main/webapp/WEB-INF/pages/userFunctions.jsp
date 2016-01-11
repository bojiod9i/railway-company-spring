<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authentication var="principal" property="principal"/>
<div class="user-control-panel">
    <security:authorize access="hasRole('ROLE_OWNER')">
        <div class="ui-widget-header" style="padding: 5px">
            Hello ${principal.username}
        </div>
        <div class="user-function-list">
            <p><spring:message code="balance.my.label"/> ${balance}USD </p>

            <p><a href="<c:url value="/" />"> <spring:message code="mainpage.label"/> </a></p>

            <p><a id="upBalanceLink" href="<c:url value="/" />"> <spring:message code="balance.up.label"/> </a></p>

            <p><a href="<c:url value="/client/ticketsHistory" />"> <spring:message code="tickets.history.label"/> </a>
            </p>

            <p><a href="<c:url value="/employee/index" />"> <spring:message code="employee.func.label"/> </a></p>

            <p><a href="<c:url value="/owner/index" />"> <spring:message code="owner.func.label"/> </a></p>

            <p><a href="<c:url value="/j_spring_security_logout" />"> <spring:message code="logout.label"/> </a></p>
        </div>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_EMPLOYEE')">
        <div class="ui-widget-header" style="padding: 5px">
            Hello ${principal.username}
        </div>
        <div class="user-function-list">
            <p><spring:message code="balance.my.label"/> ${balance}USD </p>

            <p><a href="<c:url value="/" />"> <spring:message code="mainpage.label"/> </a></p>

            <p><a id="upBalanceLink" href="<c:url value="/" />"> <spring:message code="balance.up.label"/> </a></p>

            <p><a href="<c:url value="/client/ticketsHistory" />"> <spring:message code="tickets.history.label"/> </a>
            </p>

            <p><a href="<c:url value="/employee/index" />"> <spring:message code="employee.func.label"/> </a></p>

            <p><a href="<c:url value="/j_spring_security_logout" />"> <spring:message code="logout.label"/> </a></p>
        </div>
    </security:authorize>
    <security:authorize access="hasRole('ROLE_CLIENT')">
        <div class="ui-widget-header" style="padding: 5px">
            Hello ${principal.username}
        </div>
        <div class="user-function-list">
            <p><spring:message code="balance.my.label"/> ${balance}USD </p>

            <p><a href="<c:url value="/" />"> <spring:message code="mainpage.label"/> </a></p>

            <p><a id="upBalanceLink" href="<c:url value="/" />"> <spring:message code="balance.up.label"/> </a></p>

            <p><a href="<c:url value="/client/ticketsHistory" />"> <spring:message code="tickets.history.label"/> </a>
            </p>

            <p><a href="<c:url value="/j_spring_security_logout" />"> <spring:message code="logout.label"/> </a></p>
        </div>
    </security:authorize>
    <security:authorize access="!isAuthenticated()">

        <div class="ui-widget-header" style="padding: 5px">
            Hello anonymous
        </div>
        <div class="user-function-list">
            <p> Please
                <a href="<c:url value='/login'/>"> singIn </a> or
                <a href="<c:url value='/registerPage'/>"> register </a></p>
        </div>

    </security:authorize>
</div>

<script>
    $(window).on("load", function () {
        $('#upBalanceLink').click(function () {
            $('#replenishBalanceDialog').dialog("open");
            return false;
        });
        $('#replenishBalanceDialog').dialog({
            width: '550px',
            height: 'auto',
            autoOpen: false
        });
        $('#addPaymentForm').submit(function () {
            var payment = $('#payment').val();
            if (payment == "" || !(payment.match(/^\d*(?:\.\d{0,2}){0,1}$/)) || payment <= 0) {
                $('#invalid_payment_value').show();
                return false;
            }
        })
    });
</script>

<div id="replenishBalanceDialog" title="<spring:message code="balance.up.label"/>">
    <form id="addPaymentForm" action="<c:url value='/client/increaseBalance'/>" method="POST">
        <div class="form-box">
            <h2 class="title"><spring:message code="balance.up.label"/></h2>

            <div class="form-row">
                <div class="form-label">
                    <label><spring:message code="payment.label"/> (USD): </label>
                </div>
                <div class="form-field">
                    <input type="text" class="ui-widget-content" id="payment" name="outSum"/>
                </div>
                <span id="invalid_payment_value" class="error-msg" style="display:none;">
                    <spring:message code="payment.input.error"/></span>
            </div>
            <div class="form_submit">
                <input class="btn-submit" type="submit" value="<spring:message code="pay.label"/>">
            </div>
        </div>
    </form>
</div>