$( document ).ready(function() {
    $('.input-group').datepicker({
        format: "dd.mm.yyyy",
        language: "ru",
        endDate: "-Infinity",
        startView: 1,
        clearBtn: true,
        calendarWeeks: true,
        autoclose: true,
        todayHighlight: true
    });
});