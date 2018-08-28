var ajaxUrl = "ajax/meals/";
var datatableApi;

function filter() {
    $.ajax({
        url: ajaxUrl + "filter",
        type: 'POST',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
            successNoty("filter");
        }
    });
}
function saveMeal() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            filter();
            successNoty("Saved");
        }
    });
}
function deleteMeal() {
    $(".strr").click(function () {
        deleteRow($(this).attr("id"));
        filter();
    });
    $.ajaxSetup({cache: false});
}

function clearFilter() {
    $("#filter")[0].reset();
    updateTable();
}

// $(document).ready(function () {
$(function () {
    datatableApi = $("#datatable").DataTable({
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
    });
    makeEditable();
});