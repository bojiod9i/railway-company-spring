<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script>
    function isValidEmail(email) {
        var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        return reg.test(email);
    }

    $(window).on("load", function () {
        $('#addEmployee').click(function () {
            $('#employDialog').dialog("open");
        });
        $('#employDialog').dialog({
            width: '550px',
            height: 'auto',
            autoOpen: false
        });
        $('#addEmployeeForm').submit(function () {
            var email = $('#emailInput').val();
            if (email == "" || !isValidEmail(email)) {
                $('#invalid_email_msg').show();
                return false;
            }
        })
    });
</script>

<div id="add-schedules-form" class="wide-form-box">
    <h2 class="title"><spring:message code="employee.list.label"/></h2>

    <div class="form-row">
        <input id="addEmployee" style="float: right; margin-bottom: 20px; margin-left: 20px; margin-right: 20px;"
               type="button" value='<spring:message code="employee.add.label"/>' class="simple-btn">
    </div>
    <table class="simple-little-table" cellspacing='0'>
        <tr>
            <td><spring:message code="email.label"/></td>
            <td><spring:message code="date.registration.label"/></td>
            <td><spring:message code="employee.dismiss.label"/></td>
        </tr>

        <c:forEach var="employee" items="${employeeSet}">
            <tr>
                <td>
                        ${employee.email}
                </td>
                <td>
                    <fmt:formatDate value="${employee.createDate}" pattern="dd-MM-yyyy HH:mm"/>
                </td>
                <td>
                    <form action="<c:url value='/owner/dismissEmployee'/>" method="POST">
                        <input type="hidden" name="employeeId" value="${employee.id}">
                        <input class="simple-btn" type="submit"
                               value="<spring:message code="employee.do.dismiss.label"/>">
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<div id="employDialog" title="<spring:message code="employ.label"/>">
    <form id="addEmployeeForm" action="<c:url value='/owner/addEmployee'/>" method="POST">
        <div class="form-box">
            <h2 class="title"><spring:message code="employ.label"/></h2>

            <div class="form-row">
                <div class="form-label">
                    <label><spring:message code="email.label"/>: </label>
                </div>
                <div class="form-field">
                    <input type="text" class="ui-widget-content" id="emailInput" name="email"/>
                </div>
                <span id="invalid_email_msg" class="error-msg" style="display:none;">
                    <spring:message code="email.input.error"/></span>
            </div>
            <div class="form_submit">
                <input class="btn-submit" type="submit" value="<spring:message code="join.doit.label"/>">
            </div>
        </div>
    </form>
</div>


