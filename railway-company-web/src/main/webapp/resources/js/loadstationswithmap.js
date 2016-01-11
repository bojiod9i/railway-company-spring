$(function () {
    $.ajax({
        type: "GET",
        url: "getAllStations",
        success: function (stationList) {
            $('.stationSelection').each(function (index, obj) {
                for (var i = 0; i < stationList.length; i++) {
                    $('#' + obj.id + 'Field').append('<option value="' + stationList[i].id
                        + '">' + stationList[i].name + '</option>');
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

});