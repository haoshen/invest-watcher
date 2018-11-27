
var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#mytable').bootstrapTable({
            url: '/invest/allHolds',    //请求后台的URL（*）
            method: 'get',                     //请求方式（*）
            toolbar: '#toolbar',                //工具按钮用哪个容器
            striped: true,                      //是否显示行间隔色
            cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                     //是否启用排序
            queryParams: oTableInit.queryParams,//传递参数（*）
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber: 1,                       //初始化加载第一页，默认第一页
            pageSize: 10,                       //每页的记录行数（*）
            pageList: [5, 10, 20],              //可供选择的每页的行数（*）
            strictSearch: false,
            clickToSelect: false,                //是否启用点击选中行
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            cardView: false,                    //是否显示详细视图
            detailView: false,                   //是否显示父子表
            showColumns: true,
            showRefresh: true,
            showExport: true,
            showFooter: false,
            showPaginationSwitch: true,
            detailView: true,
            responseHandler: responseHandler,
            detailFormatter: detailFormatter,
            columns: [
                    {
                        title: '品种',
                        field: 'investId',
                    },
                    {
                        title: '方向',
                        field: 'direction',
                        formatter: directionFormatter,
                    },
                    {
                        title: '持仓状态',
                        field: 'status',
                        formatter: statusFormatter,
                    },
                    {
                        title: '持仓数量',
                        field: 'currentNum',
                        formatter: currentNumFormatter,
                    },
                    {
                        title: '持仓均价',
                        field: 'currentPrice',
                    },
                    {
                        title: '已平仓利润',
                        field: 'profit',
                        formatter: profitFormatter,
                    },
                    {
                        title: '评论',
                        field: 'comment',
                    },
                ]
        });
    };

    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
            limit: params.limit,   //页面大小
            offset: params.offset,  //页码
            userId: Number(Cookies.get("user_id")),
        };
        return temp;
    };
    return oTableInit;
};

// 处理ajax返回的数据
function responseHandler(res) {
    return res;
}

function directionFormatter(value) {
    if (value == 0 ) {
        return "<span style='color: red;'>做多</span>";
    } else {
        return "<span style='color: green;'>做空</span>";
    }
}

function getSpanByValue(value) {
    if (value > 0 ) {
        return "<span style='color: red;'>" + value + "</span>";
    } else if (value < 0) {
        return "<span style='color: green;'>" + value + "</span>";
    } else {
        return "<span>" + value + "</span>";
    }
}

function currentNumFormatter(value) {
    return getSpanByValue(value);
}

function profitFormatter(value) {
    return getSpanByValue(parseInt(value));
}

function recordsFormatter(value) {
    const records = JSON.parse(value);
    const html = [];
    $.each(records, (index, record) => {
        // console.log(record);
        var operType;
        if(record.operType == 0) {
            operType = "<span style='color: red;'>开仓</span>";;
        } else {
            operType = "<span style='color: green;'>平仓</span>";;
        }
        var currentNum = record.currentNum;
        var currentPrice = record.currentPrice;
        var currentProfit = parseInt(record.currentProfit);
        var operTime = record.operTime;
        var operNum = record.operNum;
        var operPrice = record.operPrice;
        var profit = parseInt(record.operProfit);
        var operProfit = Math.abs(profit);
        var profitType;
        if (profit > 0) {
            profitType = `<span style='color: red;'>盈利${operProfit}</span>`;
        } else if(profit < 0){
            profitType = `<span style='color: green;'>亏损${operProfit}</span>`;
        } else {
            profitType = `<span>盈利${operProfit}</span>`;
        }

        html.push(`<p>${operTime} ：原有数量${currentNum}，均价${currentPrice}，利润${currentProfit} => ${operType}数量${operNum}，均价${operPrice}，${profitType}</p>`);
    });
    return "<p>操作记录：</p>" + html.join(' ');
}

function statusFormatter(value) {
    if (value == 0 ) {
        return "<span style='color: red;'>持仓中</span>";
    } else {
        return "<span>已结束</span>";
    }
}

function detailFormatter(index, row) {
    return recordsFormatter(row.records);
}


// 登录绑定
function initOperDialog() {
    $("#operHold").bootstrapValidator({
        //		提示的图标
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',			// 有效的
            invalid: 'glyphicon glyphicon-remove',		// 无效的
            validating: 'glyphicon glyphicon-refresh'	// 刷新的
        },
//		属性对应的是表单元素的名字
        fields: {
//			匹配校验规则
            num: {
                // 规则
                validators: {
                    message: '数量无效',	// 默认提示信息
                    notEmpty: {
                        message: '数量不能为空'
                    },
                    regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^\d+(\.\d+)?$/,
                        message: '含有非法字符.'
                    },
                    fun: {
                        message: 'fun函数无效的示例'
                    }
                }
            },
            price: {
                validators: {
                    message: '价格无效',
                    notEmpty: {
                        message: '价格不能为空'
                    },
                    regexp: {
                        regexp: /^\d+(\.\d+)?$/,
                        message: '含有非法字符'
                    },
                }
            },
        }
    }).on('success.form.bv', function (e) { // 表单校验成功
        /*禁用默认提交的事件 因为要使用ajax提交而不是默认的提交方式*/
        e.preventDefault();
        /*获取当前的表单*/
        var form = $(e.target);	// 可以通过选择器直接选择
        // console.log(form.serialize());	// name=root&password=123456
        $.ajax({
            type: "post",
            url: "/invest/processAccount",
            data: `userId=${Cookies.get("user_id")}&${form.serialize()}`,
            dataType: 'json',
            success: function (response) {
                console.log(response);
                if (response != null && response.result) {
                    location.href = 'hold.html';
                    alert("操作成功");
                } else {
                    alert("操作失败");
                }
            }
        });
    });
    //	重置功能
    $(".pull-left[type='reset']").on('click', function () {
        $('#login').data('bootstrapValidator').resetForm();
    });
}


$(function () {
    var oTable = new TableInit();
    oTable.Init();
    // 防止height问题
    $(window).resize(function () {
        $('#mytable').bootstrapTable('resetView');
    });
    initOperDialog();
});


















