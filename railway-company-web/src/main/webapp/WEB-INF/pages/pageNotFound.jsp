<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <meta name="PageType" content="ContentPage"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta name="keywords" content="railway, trip, travel"/>

    <link href="<c:url value='/resources/css/jquery-ui-themes-1.11.4/jquery-ui.css'/>" rel="stylesheet">
    <script src="<c:url value='/resources/js/jquery.js'/>"></script>
    <script src="<c:url value='/resources/js/jquery-ui.js'/>"></script>
    <script src="<c:url value='/resources/js/ui.js'/>"></script>
    <link href="<c:url value='/resources/css/style.css'/>" rel="stylesheet"/>

    <title><spring:message code="errorPage.title"/></title>

</head>

<body>
<div class="wrapper">

    <jsp:include page="header.jsp"/>

    <div id="inputtabs" class="tab-panel">
        <!-- Tabs-->
        <ul>
            <li><a href="#tabs-1"> <spring:message code="errorPage.title"/> </a>
        </ul>

        <div id="tabs-1">
            <img width="40px" height="40px" style="margin: 30px;" src="<c:url value='/resources/images/error.png'/>"
                 align="left">

            <h2 class="title" style="margin: 30px;"><spring:message code="errorPage.pagenotfound.msg"/></h2>
            
        </div>
    </div>
</div>
</body>
</html>

