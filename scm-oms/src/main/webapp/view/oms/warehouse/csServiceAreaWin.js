/**
 * 服务范围弹出框
 * @author Adolph
 */
var data = {};
var pay_or_receive = xmtc.getUrlVars("pay_or_receive");
var getData = function () {
    var codes = [];
    var names = [];
    var isFuelingUp = [];
    var cbks = $("#dateTable").find(":checkbox:checked");
    if (cbks.length > 0) {
        cbks.each(function () {
            codes.push($(this).attr("data-service_area_code"));
            names.push($(this).attr("data-service_area_name"));
            isFuelingUp.push($(this).attr("data-is_fueling_up"));
        });
        // 父页面的
        data["service_area_code"] = codes.toString();
        data["service_area_name"] = names.toString();
        data["is_fueling_up"] = isFuelingUp.toString();
    }
    return data;
};

layui.define(['laypage', 'layer', 'form', 'laydate'], function (exports) {
    var $ = layui.jquery, layer = layui.layer, form = layui.form();

    xmtc.getDictMap("fee_type");

    var table = $('#dateTable').DataTable({
        "aLengthMenu": [[5, 10], [5, 10]],
		"iDisplayLength": 5,
        ajax: {
            url: base + "/serviceArea/page",
            data: function (data) {
                data.service_area_code = $.trim($("#service_area_code").val());
                data.service_area_name = $.trim($("#service_area_name").val());
                data.pay_or_receive=pay_or_receive;
                data.status = "1";
            }
        },
        "columns": [                            // 自定义数据列
            {
                data: function (obj) {
                    return '<input type="checkbox" lay-skin="primary" lay-filter="oneChoose" data-id="' + obj.id + '" data-service_area_code="' + obj.service_area_code + '" data-service_area_name="' + obj.service_area_name + '" data-is_fueling_up="' + obj.is_fueling_up + '"/>';
                }
            },
            {data: 'service_area_code'},
            {data: 'service_area_name'}
        ],
        "stateSaveParams": function () {
            // 初始化完成调用事件
            form.render('checkbox');
        }
    }).on("dblclick", "tr", function () {//给tr或者td添加click事件
        var dt = table.row(this).data();//获取值的对象数据
        data["service_area_code"] = dt.service_area_code;
        data["service_area_name"] = dt.service_area_name;
        data["pay_or_receive"] = dt.pay_or_receive;
        data["is_fueling_up"] = dt.is_fueling_up;
        //子触发确定按钮
        parent.$(".layui-layer-btn0").click();
    });

    form.on('checkbox(allChoose)', function (data) {
        var child = $(data.elem).parents('table').find('tbody input[type="checkbox"]');
        child.each(function (index, item) {
            item.checked = data.elem.checked;
        });
        form.render('checkbox');
    });
    // 查询
    $("#btn-query").on('click', function () {
        table.ajax.reload();
    });
    $("#btn-reset").on('click', function () {
        $("select[name='dateTable_length']").attr("lay-ignore", "");
    });
    exports('csServiceAreaWin', {});
});
