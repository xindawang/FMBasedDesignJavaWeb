/**
 * Created by ACER on 2017/11/18.
 */
$(function () {

    var deviceInfoTable = $('#deviceInfoTable').table({
        url: '/data/device',
        type: "GET",// 请求方式
        columns: [
            {
                title: '序号',
                increment: 1,
                width: '50px'
            },
            {
                title: '计划代号',
                data: 'device_id'
            },
            {
                title: '计划名称',
                data: 'device_name'
            },
            {
                title: '时间',
                data: 'upload_time',
            },
            {
                title : '说明',
                data : 'securityLevel',
                number : { //通过number元素转换所传参数
                    'ONE' : '机密',
                    'TWO' : '秘密',
                    'THREE' : '内部',
                    'FOUR' : '非密'
                }
            },
            {
                title: '操作',
                element: "<a class='btn btn-primary submitInfo' >查看</a>"+"<button  class='btn btn-primary delete'>删除</button>"
            },
            {
                title: '最近邻',
                element: "<button  class='btn btn-danger specify'>指定</button>"+"<button  class='btn btn-primary delete'>取消</button>"
            },
            {
                title: '贝叶斯',
                element: "<button  class='btn btn-danger specify'>指定</button>"+"<button  class='btn btn-primary delete'>取消</button>"
            },
            {
                title: '高斯过程回归',
                element: "<button  class='btn btn-danger specify'>指定</button>"+"<button  class='btn btn-primary delete'>取消</button>"
            }],
        afterDraw: function () {
            var that = this;

            $("button.specify").each(function (index, element) {
                $(this).click(function () {
                    var deviceId = that.list[index].device_id
                    $.ajax({
                        url: "/algorithm/specify" + deviceId,
                        type: "get",
                        success: function (result) {
                            if (result == 1) {
                                alert("指定成功!");
                            } else {
                                alert("指定失败!");
                            }
                        }
                    });
                });
            });

            $("button.delete").each(function (index, element) {
                if (that.list[index].status == "编制中" && custId == that.list[index].responsible.id) {
                    $(this).click(function () {
                        var scheduleId = that.list[index].id
                        var scheduleName = that.list[index].name
                        $(this).attr('data-toggle', "modal")
                        $(this).attr('data-target', "#myModal")

                        $("#confirmDeleteInfo").text("是否确认删除" + scheduleName + "？");
                        $("#confirmDelete").click(function () {
                            $.ajax({
                                url: "/ih/schedules/" + scheduleId,
                                type: "delete",
                                success: function (result) {
                                    if (result == 1) {
                                        alert("删除成功!");
                                        var c = plansTable.simplePagination.pagination('getPagesCount');
                                        plansTable.simplePagination.pagination('selectPage', c);
                                        window.location.reload();
                                    } else {
                                        alert("删除失败!");
                                    }
                                }
                            });
                        })
                    })
                } else {
                    $(this).attr('disabled', "disabled");
                    $(this).attr('style', "background:#ffffff")
                }
            })

        }
    })

})