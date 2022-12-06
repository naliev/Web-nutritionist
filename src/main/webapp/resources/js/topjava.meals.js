const mealAjaxUrl = "profile/meals/";
let formWithFilter;

const ctx = {
    ajaxUrl: mealAjaxUrl
}

function filter() {
    $.ajax({
        type: "GET",
        url: ctx.ajaxUrl + "filter",
        data: formWithFilter.serialize()
    }).done(function (data) {
        refreshTable(data);
    });
}

function clearFilter() {
    $.get(mealAjaxUrl, function (data) {
        formWithFilter.trigger("click");
        refreshTable(data);
    });
}

$(function () {
    formWithFilter = $('#mealsFilter')
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    );
});