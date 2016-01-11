<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
    $.ajax({
        type: "GET",
        url: "getAllStations",
        success: function (stationList) {
            $('.stationSelection').each(function (index, obj) {
                for (var i = 0; i < stationList.length; i++) {
                    $('#' + obj.id + 'Field').append('<option value="' + stationList[i].id + '">' +
                            <c:choose>
                                <c:when test="${pageContext.response.locale == 'ru'}">
                                    stationList[i].ruName
                                </c:when>
                                <c:otherwise>
                                    stationList[i].name
                                </c:otherwise>
                            </c:choose>
                    + '</option>'
                )
                    ;
                }
            });
        },
        error: function (data) {
            window.location.replace("errorPage");
        }
    });
</script>

<!-- Tabs-->
<ul>
    <li><a href="#tabs-1"> <spring:message code="train.look.label"/> </a>
    <li><a href="#tabs-2"> <spring:message code="station.new.label"/> </a>
    <li><a href="#tabs-3"> <spring:message code="train.new.label"/> </a>
</ul>

<!-- Content Tab 1 -->

<div id="tabs-1">
    <jsp:include page="lookTrainsForm.jsp"/>
</div>

<!-- Content Tab 2 -->

<div id="tabs-2">
    <jsp:include page="stationCreation.jsp"/>
</div>

<!-- Content Tab 3 -->

<div id="tabs-3">
    <jsp:include page="trainCreation.jsp"/>
</div>
