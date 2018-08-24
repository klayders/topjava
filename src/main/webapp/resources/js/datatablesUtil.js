function makeEditable() {

    var pathFilter = ajaxUrl + "filter";
    $.post(pathFilter, function (data) {
        datatableApi.clear().rows.add(data).draw();});
    $(document).ajaxError(function (event, jqXHR, options, jsExc) {
        failNoty(jqXHR);
    });

    // solve problem with cache in IE: https://stackoverflow.com/a/4303862/548473
    $.ajaxSetup({cache: false});
}


function add() {
    $("#detailsForm").find(":input").val("");
    $("#editRow").modal();
}
function deletes() {
    $(".strr").click(function () {
        deleteRow($(this).attr("id"));
    });
    $.ajaxSetup({cache: false});
}
function deleteRow(id) {
    $.ajax({
        url: ajaxUrl + id,
        type: "DELETE",
        success: function () {
            updateTable();
            successNoty("Deleted");
        }
    });
}

function updateTable() {
    $.get(ajaxUrl, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });
}

function save() {
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




function filter() {
    var filterId = $(".filter").attr("id");
    var form = $('#' + filterId);
    $.ajax({
        url: ajaxUrl + "filter",
        type: "POST",
        data: jQuery(form).serialize(),
        success: function () {
         updateTableByFilter();
            successNoty("filtred");
        }
    });
    $.ajaxSetup({cache: true});
}

function updateTableByFilter() {
    var pathFilter = ajaxUrl + "filter";
    $.post(pathFilter, function (data) {
        datatableApi.clear().rows.add(data).draw();
    });

}










var failedNote;

function closeNoty() {
    if (failedNote) {
        failedNote.close();
        failedNote = undefined;
    }
}

function successNoty(text) {
    closeNoty();
    new Noty({
        text: "<span class='fa fa-lg fa-check'></span> &nbsp;" + text,
        type: 'success',
        layout: "bottomRight",
        timeout: 1000
    }).show();
}

function failNoty(jqXHR) {
    closeNoty();
    failedNote = new Noty({
        text: "<span class='fa fa-lg fa-exclamation-circle'></span> &nbsp;Error status: " + jqXHR.status,
        type: "error",
        layout: "bottomRight"
    }).show();
}