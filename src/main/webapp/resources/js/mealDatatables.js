var ajaxUrl = "ajax/meals/";
var datatableApi;
function updateTable() {
    $.ajax({
        type: "POST",
        url: ajaxUrl + 'filter',
        data: $('#filter').serialize(),
        success: function (data) {
            updateTableByData(data);
        }
    });
    return false;
}
$(function () {
    datatableApi = $('#datatable').dataTable(
        {
            "bPaginate": false,
            "bInfo": true,
            "aoColumns": [
                {
                    "mData": "dateTime"
                },
                {
                    "mData": "description"
                },
                {
                    "mData": "calories"
                },
                {
                    "sDefaultContent": "Edit",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "Delete",
                    "bSortable": false
                }
            ],
            "aaSorting": [
                [
                    0,
                    "desc"
                ]
            ]
        });
    $('#filter').submit(function () {
        updateTable();
        return false;
    });
    makeEditable();
});