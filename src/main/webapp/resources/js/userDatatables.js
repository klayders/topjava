var ajaxUrl = "ajax/admin/users/";
var datatableApi;

// $(document).ready(function () {

function saveUser() {
    var form = $("#detailsForm");
    $.ajax({
        type: "POST",
        url: ajaxUrl,
        data: form.serialize(),
        success: function () {
            $("#editRow").modal("hide");
            updateTable();
            successNoty("Saved");
        }
    });
}
function deleteUser() {
    $(".strr").click(function () {
        deleteRow($(this).attr("id"));
        updateTable();
    });
    $.ajaxSetup({cache: false});
}
function enable(chkbox, id) {
    var enabled = chkbox.is(":checked");
    $.ajax({
        url: ajaxUrl + id,
        type: 'POST',
        data: 'enabled=' + enabled,
        success: function () {
            updateTable();
            chkbox.closest('tr').toggleClass('disabled');
            successNoty(enabled ? 'common.enabled' : 'common.disabled');
        }
    });
}
$(function () {
    datatableApi = $("#datatable").DataTable({
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
                "desc"
            ]
        ]
    });
    makeEditable();
    $(':checkbox').each(function () {
        if (!$(this).is(":checked")) {
            $(this).closest('tr').css("text-decoration", "line-through");
        }
    });
});