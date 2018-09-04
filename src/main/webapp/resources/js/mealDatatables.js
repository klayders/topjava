const ajaxUrl = "ajax/profile/meals/";
let datatableApi;

function updateTable() {
    $.ajax({
        type: "GET",
        url: ajaxUrl + "filter",
        data: $("#filter").serialize()
    }).done(updateTableByData);
}

function clearFilter() {
    $("#filter")[0].reset();
    $.get(ajaxUrl, updateTableByData);
}

$(function () {
    datatableApi = $("#datatable").DataTable({
        "ajax": {
            "url": ajaxUrl,
            "dataSrc": ""
        },
        "paging": false,
        "info": true,
        "columns": [
            {
                "data": "dateTime",
                "render": function (date, type, row) {
                    if (type === "display") {
                        return date.substring(0, 16).replace("T", " ");
                    }
                    return date;
                }
            },
            {
                "data": "description"
            },
            {
                "data": "calories"
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderEditBtn
            },
            {
                "defaultContent": "",
                "orderable": false,
                "render": renderDeleteBtn
            }
        ],
        "order": [
            [
                0,
                "desc"
            ]
        ],
            "createdRow": function (row, data, dataIndex) {
                $(row).addClass(data.exceed ? 'exceeded' : 'normal');
            },
        "initComplete": makeEditable
    });
    $('#startDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                maxDate: $('#endDate').val() ? $('#endDate').val() : false
            })
        }
    });
    $('#endDate').datetimepicker({
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y-m-d',
        onShow:function( ct ){
            this.setOptions({
                minDate: $('#startDate').val() ? $('#startDate').val() : false
            })
        }
    });
    $('#startTime').datetimepicker({
        datepicker:false,
        format:'H:i',
        onShow:function( ct ){
            this.setOptions({
                maxTime: $('#endTime').val() ? $('#endTime').val() : false
            })
        }
    });
    $('#endTime').datetimepicker({
        datepicker:false,
        format:'H:i',
        onShow:function( ct ){
            this.setOptions({
                minTime: $('#startTime').val() ? $('#startTime').val() : false
            })
        }
    });
    $('#dateTime').datetimepicker({
        format: 'Y-m-d H:i',
        formatDate: 'Y-m-d H:i'
    });
});