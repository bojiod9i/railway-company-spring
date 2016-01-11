<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta name="PageType" content="ContentPage"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>

    <link href="<c:url value='/resources/css/jquery-ui-themes-1.11.4/jquery-ui.css'/>" rel="stylesheet">
    <link href="<c:url value='/resources/css/jquery.ui.timepicker.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/combo.css'/>" rel="stylesheet"/>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>

    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    <script src="<c:url value='/resources/js/combo.js'/>"></script>
    <script src="<c:url value='/resources/js/ui.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery.ui.timepicker.js'/>"></script>

    <title><spring:message code="app.title"/></title>

</head>

<body>
<div class="wrapper">

    <jsp:include page="../header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <jsp:include page="employeePanel.jsp"/>
    </div>

    <jsp:include page="../userFunctions.jsp"/>

    <jsp:include page="../messages.jsp"/>

</div>
</body>
</html>
