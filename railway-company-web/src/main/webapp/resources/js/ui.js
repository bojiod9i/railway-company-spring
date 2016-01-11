$(function () {
    var dialog;
    var scheduleIndex = 0;

    $(".buy-btn").on('click', function () {
        var trainId = this.closest('tr').getElementsByClassName("trainId")[0].textContent;
        document.getElementById('booking-title').textContent = 'Booking ticket by train N' + trainId;
        document.getElementById('trainIdForBooking').setAttribute('value', trainId);
        document.getElementById('buyTicketDialog').style.display = 'block';
        dialog.dialog("open");
    });

    $("#timetableFromDate").datepicker({
        dateFormat: "dd/mm/yy",
        onClose: function (selectedDate) {
            $("#timetableToDate").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#timetableToDate").datepicker({
        dateFormat: "dd/mm/yy",
        onClose: function (selectedDate) {
            $("#timetableFromDate").datepicker("option", "maxDate", selectedDate);
        }
    });

    $("#scheduleFromDate").datepicker({
        dateFormat: "dd/mm/yy",
        onClose: function (selectedDate) {
            $("#scheduleToDate").datepicker("option", "minDate", selectedDate);
        }
    });
    $("#scheduleToDate").datepicker({
        dateFormat: "dd/mm/yy",
        onClose: function (selectedDate) {
            $("#scheduleFromDate").datepicker("option", "maxDate", selectedDate);
        }
    });

    $(".tab-panel").tabs();

    dialog = $("#buyTicketDialog").dialog({
        autoOpen: false,
        width: 'auto',
        height: 'auto'
    });

    $('.map-dialog-window').dialog({
        width: 'auto',
        height: 'auto',
        autoOpen: false
    });

    $('#message-dialog').dialog({
        width: 'auto',
        height: 'auto'
    });

    $('.combobox').combobox({
    });

    $('.schedule-date-picker').datepicker({
        dateFormat: "dd/mm/yy"
    });

    $('#addSchedule').click(function () {
        copyElement();

        $('#departureStationIdField' + scheduleIndex).combobox();

        $('#arrivalStationIdField' + scheduleIndex).combobox();

        $('#departureDate' + scheduleIndex).datepicker({
            dateFormat: "dd/mm/yy"
        });

        $('#arrivalDate' + scheduleIndex).datepicker({
            dateFormat: "dd/mm/yy"
        });

        $('#departureTime' + scheduleIndex).timepicker({});

        $('#arrivalTime' + scheduleIndex).timepicker({});

        ++scheduleIndex;
    });

    function copyElement() {
        var original = document.getElementById('template_schedule');
        var clone = original.cloneNode(true);

        clone.id = "schedule" + scheduleIndex;

        function recurse(element, targetId) {
            for (var i = 0; i < element.childNodes.length; i++) {
                if (element.childNodes[i].id == targetId) {
                    element.childNodes[i].id = element.childNodes[i].id + scheduleIndex;
                    element.childNodes[i].name = element.childNodes[i].name + scheduleIndex;
                } else {
                    recurse(element.childNodes[i], targetId);
                }
            }
        }

        recurse(clone, 'departureStationIdField', false);
        recurse(clone, 'departureDate', false);
        recurse(clone, 'departureTime', false);
        recurse(clone, 'arrivalStationIdField', false);
        recurse(clone, 'arrivalDate', false);
        recurse(clone, 'arrivalTime', false);
        recurse(clone, 'cost', false);

        clone.style.display = 'block';
        document.getElementById('add-schedules-form').appendChild(clone);
    }
});