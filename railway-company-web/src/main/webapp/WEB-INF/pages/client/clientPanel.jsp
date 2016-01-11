<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<script>
    $.ajax({
        type: "GET",
        url: "<c:url value='/getAllStations'/>",
        success: function (stationList) {
            $('.stationSelection').each(function (index, obj) {
                for (var i = 0; i < stationList.length; i++) {
                    $('#' + obj.id + 'Field').append('<option value="' + stationList[i].id
                            + '">' +
                            <c:choose>
                                <c:when test="${pageContext.response.locale == 'ru'}">
                                    stationList[i].ruName
                                </c:when>
                                <c:otherwise>
                                stationList[i].name
                                </c:otherwise>
                            </c:choose>
                            + '</option>');
                }
                ymaps.ready(function () {
                    var map = new ymaps.Map(obj.id + 'Map', {
                        center: [55.7, 37.6],
                        zoom: 5,
                        controls: []
                    });
                    for (var i = 0; i < stationList.length; i++) {
                        var placemark = new ymaps.Placemark([stationList[i].latitude, stationList[i].longitude], {
                            hintContent: stationList[i].name
                        });
                        (function (station) {
                            placemark.events.add('click', function (e) {
                                $('#' + obj.id + 'Field').val(station.id);
                                var textToShow = $('#' + obj.id + 'Field').find(":selected").text();
                                $('#' + obj.id + 'Field').parent().find("span").find("input").val(textToShow);
                                $('#' + obj.id + 'Dialog').dialog('close');
                            });
                        })(stationList[i]);
                        map.geoObjects.add(placemark);
                    }
                });
            });
        },
        error: function (data) {
            window.location.replace("errorPage");
        }
    });
</script>

<!-- Tabs-->
<ul>
    <li><a href="#tabs-1"> <spring:message code="route.planner.label"/> </a>
    <li><a href="#tabs-2"> <spring:message code="station.label"/> </a>
</ul>

<!-- Content Tab 1 -->

<div id="tabs-1">
    <jsp:include page="searchTrainForm.jsp"/>
</div>

<!-- Content Tab 2 -->

<div id="tabs-2">
    <jsp:include page="stationTimetableForm.jsp"/>
</div>