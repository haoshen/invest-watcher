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
            url: "invest/processAccount",
            data: `userId=${Cookies.get("user_id")}&${form.serialize()}`,
            dataType: 'json',
            success: function (response) {
                console.log(response);
                if (response != null && response.result) {
                    location.href = 'hold_history.html';
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
    initOperDialog();
});


















