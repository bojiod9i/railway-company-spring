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
            });
        },
        error: function (data) {
            window.location.replace("errorPage");
        }
    });

});