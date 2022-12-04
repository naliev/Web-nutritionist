const userAjaxUrl = "admin/users/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: userAjaxUrl
};

// $(document).ready(function () {
$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "name"
                },
                {
                    "data": "email"
                },
                {
                    "data": "roles"
                },
                {
                    "data": "enabled"
                },
                {
                    "data": "registered"
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
                    "asc"
                ]
            ]
        })
    );
});

function changeEnabled(checkbox) {
    let enable = checkbox.is(":checked");
    let id = checkbox.closest('tr').attr("id");
    $.ajax({
        url: ctx.ajaxUrl + id,
        type: "POST",
        data: {enabled: enable}
    }).done(function () {
        checkbox.closest('tr').attr("data-user-enabled", enable);
        successNoty("Changed");
    }).fail(function () {
        checkbox.closest('tr').attr("data-user-enabled", !enable);
        successNoty("Error");
    });
}