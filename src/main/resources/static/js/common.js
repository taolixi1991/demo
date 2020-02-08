/**
 * Created by bearxyz on 2017/6/21.
 */

var CONSTANT = {

    // datatables常量
    DATA_TABLES: {
        DEFAULT_OPTION: { // DataTables初始化选项
            LANGUAGE: {
                sSearch: '<span class="input-group-addon"><i class="glyphicon glyphicon-search"></i></span>',
                sProcessing: "正在查询...",
                sLengthMenu: "_MENU_",
                sZeroRecords: "没有匹配结果",
                sInfo: "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
                sInfoEmpty: "显示第 0 至 0 项结果，共 0 项",
                sInfoFiltered: "(由 _MAX_ 项结果过滤)",
                sInfoPostFix: "",
                sUrl: "",
                sEmptyTable: "暂无数据",
                sLoadingRecords: "载入中...",
                sInfoThousands: ",",
                oPaginate: {
                    sFirst: "<<",
                    sPrevious: "<",
                    sNext: ">",
                    sLast: ">>"
                },
                "oAria": {
                    "sSortAscending": ": 以升序排列此列",
                    "sSortDescending": ": 以降序排列此列"
                }
            },
            processing: false,
            serverSide: true,
            searching: false
        },
        COLUMN: {
            CHECKBOX: {
                data: "id",
                width: "50px",
                sortable: false,
                'orderable': false,
                className: 'smart-form',
                render: function (data, type, row, meta) {
                    return '<label class="checkbox"><input name="check-id" type="checkbox" value="' + data + '" /><i> </i></label>';
                }
            }
        }
    }


};

function bind_select(control, url, placeholder, selected, trigger, defaultValue) {
    defaultValue = (defaultValue==null)?"":defaultValue;
    $.getJSON(url, function (data) {
        control.empty();
        control.append("<option value="+defaultValue+">&nbsp;" + placeholder + "</option>");
        $.each(data, function (i, item) {
                 if (item.text .indexOf("新唐万科") != -1){
                     control.append("<option   selected='selected'  value='" + item.id + "'>&nbsp;" + item.text + "</option>");
                 }else{
                     control.append("<option value='" + item.id + "'>&nbsp;" + item.text + "</option>");
                 }

        });
        if (selected) {
            if (trigger) control.val(selected).trigger('change');
            else
                control.val(selected);
        }
    });
}

function bind_dict_item(control, mask, placeholder, selected) {
    var url = '/common/getDict?mask=' + encodeURI(mask);
    bind_select(control, url, placeholder, selected);
}

function bind_newDict_item(control, mask, placeholder, selected) {
    var url = '/common/getNewDict?mask=' + encodeURI(mask);
    bind_select(control, url, placeholder, selected);
}

function form_post(ctrl, rules, messages, url, success) {
    return ctrl.validate({
        errorClass: 'invalid',
        errorElement: 'em',
        highlight: function (element) {
            $(element).parent().removeClass('state-success').addClass("state-error");
            $(element).removeClass('valid');
        },
        unhighlight: function (element) {
            $(element).parent().removeClass("state-error").addClass('state-success');
            $(element).addClass('valid');
        },
        rules: rules,
        messages: messages,
        onsubmit: true,
        submitHandler: function () {
            $.ajax({
                url: url,
                type: 'post',
                data: ctrl.serialize(),
                success: function (d) {
                    var e = JSON.parse(d);
                    if(e.code>0)
                        $.bigBox({
                            title: "操作错误！",
                            content:e.msg,
                            color: "#fc4b6c",
                            icon: "fa fa-warning shake animated",
                            timeout: 6000
                        });
                    else
                        success();
                },
                error: function (e) {
                    $.bigBox({
                        title: "系统内部错误！",
                        content: "该错误已被记录，请联系管理员，并稍后重试！" + e.msg,
                        color: "#C46A69",
                        icon: "fa fa-warning shake animated",
                        timeout: 6000
                    });
                    e.preventDefault();
                }
            });
        },

        // Do not change code below
        errorPlacement: function (error, element) {
            error.insertAfter(element.parent());
        }
    });
}

function dt_create(ctrl, ajax, columns, check_all) {
    var responsiveHelper_dt_basic = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
    var dt = ctrl.DataTable({
        "sDom": "r" +
            "t" +
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
        "oLanguage": CONSTANT.DATA_TABLES.DEFAULT_OPTION.LANGUAGE,
        "processing": false,
        "ajax": ajax,
        "columns": columns,
        "autoWidth": true,
        "bStateSave": true,
        "order": [],
        "aLengthMenu": [30, 50, 100], //更改显示记录数选项
        "iDisplayLength": 30, //默认显示的记录数
        stateSave: true,
        "preDrawCallback": function () {
            if (!responsiveHelper_dt_basic) {
                responsiveHelper_dt_basic = new ResponsiveDatatablesHelper(ctrl, breakpointDefinition);
            }
        },
        "rowCallback": function (nRow) {
            responsiveHelper_dt_basic.createExpandIcon(nRow);
        },
        "drawCallback": function (oSettings) {
            responsiveHelper_dt_basic.respond();
        }
    });
    if (check_all) {
        dt.on("change", ":checkbox", function () {
            if ($(this).is("[name='check-all']")) {
                $(":checkbox[name='check-id']", ctrl).prop("checked", $(this).prop("checked"));
            } else {
                var checkbox = $(":checkbox[name='check-id']", ctrl);
                $(":checkbox[name='check-all']", ctrl).prop("checked", checkbox.length == checkbox.filter(":checked"));
            }
        });
    }
    return dt;
}

function getRowObj(obj) {
    var i = 0;
    while (obj.tagName.toLowerCase() != "tr") {
        obj = obj.parentNode;
        if (obj.tagName.toLowerCase() == "table") return null;
    }
    return obj;
}

function delRow(obj) {
    var tr = this.getRowObj(obj);
    if (tr != null) {
        tr.parentNode.removeChild(tr);
        item_count--;
    } else {
        throw new Error("the given object is not contained by the table");
    }
}

function dt_server_create(table, ajax, columns, check_all, orderBy, direction) {
    var order_by = 0;
    var order = "asc";
    if (direction != null && direction != "") {
        order_by = orderBy;
        order = direction;
    }
    var responsiveHelper_dt_basic = undefined;
    var breakpointDefinition = {
        tablet: 1024,
        phone: 480
    };
    var dt = table.DataTable({
        "sDom": "r" +
            "t" +
            "<'dt-toolbar-footer'<'col-sm-6 col-xs-12 hidden-xs'i><'col-xs-12 col-sm-6'p>>",
        "oLanguage": CONSTANT.DATA_TABLES.DEFAULT_OPTION.LANGUAGE,
        "processing": false,
        "ajax": ajax,
        "columns": columns,
        paging: true,
        serverSide: true,
        "autoWidth": true,
        "bLengthChange": false,
        "searching": false,
        "bStateSave": true,
        "aLengthMenu": [30, 50, 100], //更改显示记录数选项
        "iDisplayLength": 30, //默认显示的记录数
        "order": [[order_by, order]],
        "preDrawCallback": function () {
            if (!responsiveHelper_dt_basic) {
                responsiveHelper_dt_basic = new ResponsiveDatatablesHelper(table,
                    breakpointDefinition);
            }
        },
        "rowCallback": function (nRow) {
            responsiveHelper_dt_basic.createExpandIcon(nRow);
        },
        "drawCallback": function (oSettings) {
            responsiveHelper_dt_basic.respond();
        },
        fnDrawCallback: function (table) {
            $("#dt_basic_paginate").append("  到第 <input style='height:28px;line-height:28px;width:40px;' class='margin text-center' id='changePage' type='text'> 页  <a class='btn btn-default btn-sm' style='margin-bottom:5px;margin-right:5px;' href='javascript:void(0);' id='dataTable-btn'>确认</a> ");
            var oTable = $("#dt_basic").dataTable();
            $('#dataTable-btn').click(function (e) {
                if ($("#changePage").val() && $("#changePage").val() > 0) {
                    var redirectpage = $("#changePage").val() - 1;
                } else {
                    var redirectpage = 0;
                }
                oTable.fnPageChange(redirectpage);
            });
        }
    });
    if (check_all) {
        dt.on("change", ":checkbox", function () {
            if ($(this).is("[name='check-all']"))
                $(":checkbox[name='check-id']", table).prop("checked", $(this).prop("checked"));
            else {
                var checkbox = $(":checkbox[name='check-id']", table);
                $(":checkbox[name='check-all']", table).prop("checked", checkbox.length == checkbox.filter(":checked"));
            }
        });
    }
    return dt;
}


function check_batch_delete(url, dt, csrfToken, csrfHeader) {
    var ids = [];
    $('input[name="check-id"]:checked').each(function () {
        ids.push($(this).val());
    });
    if (ids != "") {
        $.SmartMessageBox({
                title: "注 意!",
                content: "确定要执行该操作吗？",
                buttons: '[取 消][确 定]'
            },
            function (ButtonPressed) {
                if (ButtonPressed === "确 定") {
                    $.ajax({
                        url: url,
                        type: 'POST',
                        data: {
                            ids: ids.toString()
                        },
                        beforeSend: function (request) {
                            if (csrfToken && csrfHeader) {
                                request.setRequestHeader(csrfHeader, csrfToken); // 添加 CSRF Token
                            }
                        },
                        success: function () {
                            if (dt) dt.DataTable().ajax.reload();
                        },
                        error: function (e) {
                            error_alert("该错误已被记录，请联系管理员，并稍后重试！");
                            e.preventDefault();
                        }
                    });
                }
            });
    } else {
        $.SmartMessageBox({
            title: "注 意!",
            content: "请先选择需要操作的数据项！",
            buttons: '[确 定]'
        });
    }
}

function error_alert(msg) {
    $.bigBox({
        title : "错误！",
        content : msg,
        color : "#C46A69",
        //timeout: 6000,
        icon : "fa fa-warning shake animated",
        number : "1",
        timeout : 6000
    });
}

function renderLocalDatetime(cellValue, options, rowObject) {
    if(cellValue!=null)
        return cellValue.year + "-" + cellValue.monthValue + "-" + cellValue.dayOfMonth + " " + cellValue.hour + ":" + cellValue.minute;
}

$.fn.modal.Constructor.prototype.enforceFocus = function () {}

jQuery(function ($) {
    // 备份jquery的ajax方法
    var _ajax = $.ajax;
    // 重写ajax方法，
    $.ajax = function (opt) {
        var _success = opt && opt.success || function (a, b) {};
        var _error = opt && opt.error || function (a, b) {};
        var _opt = $.extend(opt, {
            success: function (data, textStatus) {
                // 如果后台将请求重定向到了登录页，则data里面存放的就是登录页的源码，这里需要判断(登录页面一般是源码，所以这里只判断是否有html标签)
                if ((data + "").indexOf('login') != -1) {
                    window.location.href = "/login.html";
                    return;
                }
                _success(data, textStatus);
            },
            error: function (data, textStatus) {
                if ((data + "").responseText && (data + "").responseText.indexOf('login') != -1) {
                    window.location.href = "/login.html";
                    return;
                }
                _error(data, textStatus);
            }
        });
        return _ajax(_opt);
    };
});





function getTime(time) {
        var date=getDay(0);
    switch(time) {
        case "today":
            date=getDay(0);
            break;
        case "yesterday":
            date=getDay(-1);
            break;
        case "week":
            date=getDay(-7);
            break;
        case "month":
            date=getDay(-30);
            break;
        default:
            getDay(0);
    }
    return date;
}

function getDay(day){
    var today = new Date();
    var targetday_milliseconds=today.getTime() + 1000*60*60*24*day;
    today.setTime(targetday_milliseconds); //注意，这行是关键代码
    var tYear = today.getFullYear();
    var tMonth = today.getMonth();
    var tDate = today.getDate();
    tMonth = doHandleMonth(tMonth + 1);
    tDate = doHandleMonth(tDate);
    return tYear+"-"+tMonth+"-"+tDate;
}
function doHandleMonth(month){
    var m = month;
    if(month.toString().length == 1){
        m = "0" + month;
    }
    return m;
}
