<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sprSec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="PageType" content="ContentPage"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="keywords" content="railway, trip, travel"/>

    <link href="<c:url value='/resources/css/jquery-ui-themes-1.11.4/jquery-ui.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    <script src="<c:url value='/resources/js/combo.js'/>"></script>
    <script src="<c:url value='/resources/js/ui.js'/>"></script>

    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>

    <title> Railway Company </title>

    <script type="text/javascript">
        function blockPasswordInputField() {
            var check = $('#genPassFlag').prop("checked");
            if (check) {
                $('#password').val('').prop('disabled', true);
                $('#password').css('background', '#B3B8BC');
                $('#confirm_password').val('').prop('disabled', true);
                $('#confirm_password').css('background', '#B3B8BC');
            } else {
                $('#password').prop('disabled', false);
                $('#password').css('background', 'white');
                $('#confirm_password').prop('disabled', false);
                $('#confirm_password').css('background', 'white');
            }
        }

        function isValidEmail(email) {
            var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return reg.test(email);
        }

        $(window).on("load", function () {
            blockPasswordInputField();

            $('#joinForm').on('submit', function () {
                var valid = true;
                var email = $('#email').val();
                var password = $('#password').val();
                var confirmPassword = $('#confirm_password').val();
                var genPass = $('#genPassFlag').prop("checked");

                if (email == "" || !isValidEmail(email)) {
                    $('#invalid_email_msg').show();
                    valid = false;
                }
                if (!genPass) {
                    if (password == "" || confirmPassword == "") {
                        if (password == "") {
                            $('#invalid_password').show();
                            valid = false;
                        }
                        if (confirmPassword == "") {
                            $('#invalid_confirm_password').show();
                            valid = false;
                        }
                    } else {
                        if (password != confirmPassword) {
                            $('#password_different_error').show();
                            valid = false;
                        }
                    }
                }
                return valid;
            });
        });
    </script>

</head>

<body>
<div class="wrapper">

    <jsp:include page="header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <ul>
            <li><a href="#tabs-1"> <spring:message code="join.label"/> </a>
        </ul>

        <div id="tabs-1">
            <div class="form-box">
                <h2><spring:message code="join.form.label"/></h2>

                <form id="joinForm" action="<c:url value='/registerNewUser'/>" method="POST">
                    <div class="form-row">
                        <div class="form-label">
                            <label for="email"><spring:message code="email.label"/>: </label>
                        </div>
                        <div class="form-field">
                            <input type="text" class="ui-widget-content" id="email" name="email"/>
                        </div>
                        <span class="error-msg" id="invalid_email_msg" style="display:none;"><spring:message
                                code="email.input.error"/></span>
                    </div>

                    <div class="form-row">
                        <div>
                            <label>
                                <input id="genPassFlag" name="generatePassword" onchange="blockPasswordInputField()"
                                       checked="" type="checkbox">
                                <spring:message code="password.generate.label"/>
                            </label>
                        </div>
                    </div>

                    <div class="form-row">
                        <div class="form-label">
                            <label for="password"><spring:message code="password.label"/>: </label>
                        </div>
                        <div class="form-field">
                            <input type="password" class="ui-widget-content" id="password" name="password"/>
                        </div>
                        <span class="error-msg" id="invalid_password" style="display:none;"><spring:message
                                code="password.empty.error"/></span>
                        <span class="error-msg" id="password_different_error" style="display:none;"><spring:message
                                code="passwords.different.error"/></span>
                    </div>

                    <div class="form-row">
                        <div class="form-label">
                            <label for="confirm_password"><spring:message code="password.confirm.label"/>: </label>
                        </div>
                        <div class="form-field">
                            <input type="password" class="ui-widget-content" id="confirm_password"
                                   name="confirm_password"/>
                        </div>
                        <span class="error-msg" id="invalid_confirm_password" style="display:none;"><spring:message
                                code="password.confirm.empty.error"/></span>
                    </div>

                    <div class="form_submit">
                        <input class="btn-submit" type="submit" value="<spring:message code="join.doit.label"/>">
                    </div>
                </form>
            </div>
        </div>
    </div>
    <c:if test="${!empty errorMsg}">
        <div class="dialog" id="message-dialog" title="Message">
            <p>${errorMsg}</p>
        </div>
    </c:if>
</div>
</body>
</html>
