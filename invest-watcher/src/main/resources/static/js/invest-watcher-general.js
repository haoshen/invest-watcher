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

$(function () {
    initUser();
});