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
        allowedContent: true,
        //customConfig: '/ckeditor/custom_config.js'
    });
    editor.addCommand("infoCommend", {
        exec: function () {
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
    console.warn(sendnow);
    let date = $('#messageSendingTime').val();
    console.warn(date);
    //let text = CKEDITOR.instances['body1'].getData();
    let text = CKEDITOR.instances.editor.getData();
    console.warn(text);
    let recipients = $('#addresses-area').val();
    console.warn(recipients);
    let x;
    if (type !== "email") {
        x = text.value;
    } else {
        x = "";
    }
    let wrap = {
        sendnow: sendnow,
        type: type,
        content: text,
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
        error: function (error) {
            console.log("неверный формат записи, добавте clientData перед данными\n" + error);
        }
    });
}

/**
 * Функция, переключающая состояние кнопок режима рассылки и перенастраивающая интерфейс редактора.
 * Например для функции отправки сообщений посредством СМС, в CKEditor отключаются все плагины форматирования
 * текста. Так же как и для отправки сообщений в Вк.
 */
$(document).ready(function () {
    $("#button-group-1 > button").click(function () {
        var btnPressed = this;
        //console.log(btnPressed);
        $("#button-group-1 > button").each(function (index, element) {
            if ($(element).hasClass("btn-info")) {
                $(element).removeClass("btn-info");
                $(element).addClass("btn-secondary");
            }
            $(btnPressed).addClass("btn-info");
        });
    });
});

/**
 * Функция, настраивающая datarangepicker
 */
$(document).ready(function () {
    let startDate = moment(new Date()).utcOffset(180); //устанавливаем минимальную дату и время по МСК (UTC + 3 часа )
    $('#messageSendingTime').daterangepicker({
        "singleDatePicker": true, //отключаем выбор диапазона дат (range)
        "showWeekNumbers": false,
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
            "daysOfWeek": ["Mo", "Tu", "We", "Th", "Fr", "Sa", "Su"],
            "monthNames": ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
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

//TODO Не срабатывает. Предназначен для установки текущей даты в стартовую и мин даты при открытии календаря
$('#messageSendingTime').on('showCalendar.daterangepicker', function (ev, picker) {
    let minDate = moment(new Date()).utcOffset(180); //устанавливаем минимальную дату и время по МСК (UTC + 3 часа)
    picker.minDate = minDate;
    picker.startDate = minDate;
});

$(document).ready(function () {
    $("#addresses-area").on("drop", function (event) {
        event.preventDefault();
        event.stopPropagation();
        let reader = new FileReader();
        let file = event.originalEvent.dataTransfer.files[0];
        //console.warn(file.name + " " + file.size);
        $("#file-info").text("Файл: " + file.name + ", Размер файла: " + file.size);
        reader.onload = function (event) {
            let content = event.target.result;
            $("#addresses-area").val(content.toLowerCase());
        };
        reader.onerror = function (event) {
            console.error("The file could not be read!" + event.target.error.code);
            let fileInfo = $("#file-info");
            //TODO переделать с проверкой, чтобы все было корректно
            $(fileInfo).text("The file could not be read!" + event.target.error.code);
            $(fileInfo).removeClass("badge-success");
            $(fileInfo).addClass("badge-warning");
        };
        reader.readAsText(file);
    })
});

$(document).ready(function () {
    $("#addresses-area")
        .on("dragover", function (event) {
            $(this).addClass("drop-zone-is-dragover");
        })
        .on("dragleave dragend drop", function () {
            $(this).removeClass("drop-zone-is-dragover");
        })
});