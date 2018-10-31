let type = 'email';
let text;

function ChangeMessageType() {
    console.warn("Button pressed!");
    $('.btn-group-primary .btn').addClass('btn-primary');
    // var selected = $('#socNetworkChoose').val();
    // if (selected === 'email') {
    //     type = 'email';
    //     $('#field').show();
    //     $('#vkArea').hide();
    //     $('#smsArea').hide();
    // } else if (selected === 'vk') {
    //     type = 'vk';
    //     $('#field').hide();
    //     $('#vkArea').show();
    //     $('#smsArea').hide();
    //     text = document.getElementById("vkArea1");
    // }
    // else if (selected === 'sms') {
    //     type = 'sms';
    //     $('#field').hide();
    //     $('#vkArea').hide();
    //     $('#smsArea').show();
    //     text = document.getElementById("smsArea1");
    // }
}

//блок для редактора CKEditor
$(document).ready(function () {
    editor = CKEDITOR.replace('editor', {
        allowedContent: true
        //width: 'auto',
        //height: '300px',
    });
    editor.addCommand("infoCommend", {
        exec: function (edt) {
            $("#infoModal").modal('show');
        }
    });
    editor.ui.addButton('SuperButton', {
        label: "Info",
        command: 'infoCommend',
        toolbar: 'styles',
        icon: 'info.png'
    });
});

function mail(sendnow) {
    let date = $('#mailingDate').val();
    let templateText = CKEDITOR.instances['body1'].getData();
    let clientData = $('#clientData1').val();
    let x;
    if (type !== "email") {
        x = text.value;
    } else {
        x = "";
    }
    let wrap = {
        sendnow: sendnow,
        type: type,
        templateText: templateText,
        text: x,
        date: date,
        clientData: clientData
    };
    $.ajax({
        type: "POST",
        url: "/client/mailing/send",
        data: wrap,
        success: function (result) {
            console.log("success " + result);
        },
        error: function (e) {
            console.log("неверный формат записи, добавте clientData перед данными\n" + e);
        }
    });
}

$(document).ready(function () {
    $("#button-group-1 > button").click(function () {
        var thisButton = this;
        //console.log(thisButton);
        $("#button-group-1 > button").each(function (index, element) {
            //console.log("index: " + index, "element: " + element);
            if ($(element).filter(".btn-success")) {
                console.log(index, " ", element);
            }
            // $(element).removeClass("btn-secondary");
            // $(element).addClass("btn-secondary");
        });
        // $(thisButton).addClass("btn-info");
    });
});
//TODO Правильнее ли будет создать отдельные функции настройки элементов, а затем их вызывать здесь?
$(document).ready(function () {
    //настраиваем datarangepicker (по умолчанию предназначенный для выбора диапазона дат)
    let startDate = moment(new Date()).utcOffset(180); //устанавливаем минимальную дату и время по МСК (UTC + 3 часа )
    $('#mailingDate').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false, //не показывать номера недель
        "timePicker": true,
        "timePicker24Hour": true,
        "timePickerIncrement": 10,
        "locale": {
            "format": "DD.MM.YYYY HH:mm МСК",
            "separator": " - ",
            "applyLabel": "Apply",
            "cancelLabel": "Cancel",
            "fromLabel": "From",
            "toLabel": "To",
            "customRangeLabel": "Custom",
            "weekLabel": "W",
            "daysOfWeek": [
                "Mo",
                "Tu",
                "We",
                "Th",
                "Fr",
                "Sa",
                "Su"
            ],
            "monthNames": [
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec"
            ],
            "firstDay": 0
        },
        "linkedCalendars": false,
        "startDate": startDate,
        "minDate": startDate //стартовая дата будет совпадать с минимальной
    }, function (start, end, label) {
        console.log('New date range selected: ' + start.format('YYYY-MM-DD') + ' to ' +
            end.format('YYYY-MM-DD') + ' (predefined range: ' + label + ')');
    });
});

//Не срабатывает. Предназначен для установки текущей даты в стартовую и мин даты при открытии календаря
$('#mailingDate').on('showCalendar.daterangepicker', function (ev, picker) {
    let minDate = moment(new Date()).utcOffset(180); //устанавливаем минимальную дату и время по МСК (UTC + 3 часа)
    picker.minDate = minDate;
    picker.startDate = minDate;
});

//Настройка datepicker
// $(document).ready(function () {
//     var nowDate = new Date();
//     var minutes = Math.ceil((nowDate.getMinutes() + 1) / 10) * 10;
//     var minDate = new Date(nowDate.getFullYear(), nowDate.getMonth(), nowDate.getDate(), nowDate.getHours(), minutes, 0, 0);
//     var startDate = moment(minDate).utcOffset(180);
//     $('input[name="mailingDate"]').daterangepicker({
//         singleDatePicker: true,
//         timePicker: true,
//         timePickerIncrement: 10,
//         timePicker24Hour: true,
//         locale: {
//             format: 'DD.MM.YYYY HH:mm МСК'
//         },
//         minDate: startDate,
//         startDate: startDate
//     });
// });

/*$(document).ready(function () {
    editor.on('drop', function (e) {
        var files = e.data.dataTransfer.getFile(0);
        var reader = new FileReader();
        reader.onload = function () {
            if (CKEDITOR.instances["body1"].getData().includes("clientData")) {
                editor.insertText("\n\n" + this.result);
            } else {
                editor.insertText("\nclientData\n" + this.result);
            }
        };
        reader.readAsBinaryString(files);
    });
});*/

document.addEventListener('DOMContentLoaded', function () {
    var dropZone1 = document.getElementById("clientData1");
    dropZone1.addEventListener('drop', function (e) {
        event.preventDefault();
        var files = e.dataTransfer.files[0];
        var reader = new FileReader();
        reader.onload = function () {
            dropZone1.value += "\n" + this.result;
        };
        reader.readAsBinaryString(files);
    }, false);
}, false);