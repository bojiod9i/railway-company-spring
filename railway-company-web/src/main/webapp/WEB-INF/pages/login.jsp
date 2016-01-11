<%@ page contentType="text/html;charset=UTF-8" language="java" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

    <title><spring:message code="app.title"/></title>

    <script type="text/javascript">
        function isValidEmail(email) {
            var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
            return reg.test(email);
        }

        $(window).on("load", function () {
            $('#signInForm').on('submit', function () {
                var valid = true;
                var email = $('#email').val();
                var password = $('#password').val();
                if (email == "" || !isValidEmail(email)) {
                    $('#invalid_email_msg').show();
                    valid = false;
                }
                if (password == "") {
                    $('#invalid_password').show();
                    valid = false;
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
        <!-- Tabs-->
        <ul>
            <li><a href="#tabs-1"> <spring:message code="login.label"/> </a>
        </ul>

        <div id="tabs-1">
            <div class="form-box">
                <h2><spring:message code="login.label"/></h2>

                <form id='signInForm' action="<c:url value='/j_spring_security_check'/>" method="POST">
                    <div class="form-row">
                        <div class="form-label">
                            <label for="email"><spring:message code="email.label"/>: </label>
                        </div>
                        <div class="form-field">
                            <input type="text" class="ui-widget-content" id="email" name="j_username"/>
                        </div>
                        <span id="invalid_email_msg" class="error-msg" style="display:none;"><spring:message
                                code="email.input.error"/></span>
                    </div>

                    <div class="form-row">
                        <div class="form-label">
                            <label for="password"><spring:message code="password.label"/>: </label>
                        </div>
                        <div class="form-field">
                            <input type="password" class="ui-widget-content" id="password" name="j_password"/>
                        </div>
                        <span id="invalid_password" class="error-msg" style="display:none;"><spring:message
                                code="password.empty.error"/></span>
                    </div>
                    <input class="btn-submit" type="submit" value="<spring:message code="singin.label"/>">
                </form>
                <form action="<c:url value='/registerPage'/>" method="GET">
                    <input class="btn-submit" type="submit" value="<spring:message code="join.label"/>">
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="messages.jsp"/>
</div>
</body>
</html>
