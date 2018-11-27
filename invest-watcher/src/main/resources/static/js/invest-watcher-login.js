// 加载用户
function initUser() {
    var userId = Cookies.get("user_id");
    var userName = Cookies.get("user_name");
    if (userId && userName) {
        $("#userNameSpan").text(userName);
    } else {
        // 使用默认的test用户
        userId = 1;
        userName = "test";
        $("#userNameSpan").text(userName);
        var args = { expires: 7, path: "/"};
        Cookies.set("user_id", userId, args);
        Cookies.set("user_name", userName, args);
    }
}

// 登录绑定
function initLogin() {
    $("#login").bootstrapValidator({
        //		提示的图标
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',			// 有效的
            invalid: 'glyphicon glyphicon-remove',		// 无效的
            validating: 'glyphicon glyphicon-refresh'	// 刷新的
        },
//		属性对应的是表单元素的名字
        fields: {
//			匹配校验规则
            name: {
                // 规则
                validators: {
                    message: '用户名无效',	// 默认提示信息
                    notEmpty: {
                        message: '用户名不能为空'
                    },
                    regexp: {/* 只需加此键值对，包含正则表达式，和提示 */
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '只能是数字字母_.'
                    },
                    /*设置错误信息 和规则无关 和后台校验有关系*/
                    callback: {
                        message: '用户名错误'
                    },
                    fun: {
                        message: 'fun函数无效的示例'
                    }
                }
            },
            password: {
                validators: {
                    message: '密码无效',
                    notEmpty: {
                        message: '密码不能为空'
                    },
                    stringLength: {
                        min: 1,
                        max: 12,
                        message: '密码在12个字符内'
                    },
                    regexp: {
                        regexp: /^[a-zA-Z0-9_\.]+$/,
                        message: '含有非法字符'
                    },
                    callback: {
                        message: '密码不正确'
                    }
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
            url: "/user/verify",
            data: form.serialize(),
            dataType: 'json',
            success: function (response) {
                console.log(response);
                if (response != null && response.result != null) {
                    // 记录用户cookie
                    var args = { expires: 7, path: "/"};
                    Cookies.set("user_id", response.result.id, args);
                    Cookies.set("user_name", response.result.name, args);
                    $("#userNameSpan").text(response.result.name);
                    /*登录成功之后的跳转*/
                    location.href = 'about.html';
                    alert("登录成功");
                } else {
                    // 登录失败
                  	// 登录按钮点击后,默认不允许再次点击;登录失败要恢复登录按钮的点击
                    form.bootstrapValidator('disableSubmitButtons', false);
					// 指定触发某一个表单元素的的错误提示函数
                    //form.data('bootstrapValidator').updateStatus('name', 'INVALID', 'callback');
                    //form.data('bootstrapValidator').updateStatus('password', 'INVALID', 'callback');
                    alert("用户名或密码错误");
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
    initLogin();
});