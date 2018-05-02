<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>办公自动化系统</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style type="text/css">
<!--
.login {
    margin: 0 auto; }

.login .lay {
    width: 1000px;
}

.STYLE1 {
    color: #000000; font-size: 12px;
}

#error_l {
    font-size: 12px; text-align: left; color: #ff0000;; margin-top: -10px; vertical-align: bottom; height: 20px
}

.input {
    width: 160px; height: 17px; border: solid 1px #C8C8C8; font-size: 12px;
}

form {
    margin: 0; padding: 0;
}
-->
</style>
<script type="text/javascript" src="${ctx}/js/jQuery/jquery.1.4.2.js"></script>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">
	var web = document.location;
	if (top.location !== self.location) {
		top.location.href = "${ctx}/";
	}
	$(function() {
		$("#j_username").focus();

	});

	function login() {
		var u = $("#j_username").val();
		var p = $("#j_password").val();
		var r = $("#j_rememberMe").val();
		$.get("${ctx}/api/login.action", {
			j_username : u,
			j_password : p,
			j_rememberMe : r
		}, function(data) {
			alert("Data Loaded: " + data);
		});
	}

	function create() {
		window.parent.location = "${ctx}/system/system!sslogin.action?type=create";
	}
	function remove() {
		window.parent.location = "${ctx}/system/system!sslogin.action?type=remove";
	}
	function normal() {
		window.parent.location = "${ctx}/system/system!sslogin.action";
	}
</script>
</head>

<body>


<div style="border: 1px solid #ccc;">
<b>weblogic.Name</b>:<%= System.getProperty("weblogic.Name") %><br/>
<b>LocalHost</b>:<%=java.net.InetAddress.getLocalHost().getHostAddress() %><br/>
<b>session id</b>: <%=session.getId() %>.<br/>
</div>

<div class="login"><c:if test="${sessionScope.SPRING_SECURITY_CONTEXT.authentication.principal==null }">
    <div id="contv">
    <div class="tip">
    <div class="tit">用户登录</div>
    <div onclick="$('#fo').hide();" class="close"></div>
    </div>
    <form onsubmit="login();">
    <div style="padding: 8px 18px 0pt;">
    <div>用户名 <input type="text" class="medium" id="j_username" /></div>
    <div>密 码 <input type="password" class="medium" id="j_password" /></div>
    <div style="line-height: 20px; text-align: center;"><input type="checkbox" checked="checked" id="j_rememberMe" />记住密码
    <input type="submit" value="登录" onclick="" /></div>
    <div style="line-height: 20px; text-align: right;"><span style="margin-right: 20px; color: red;" id="lshow"></span>
    <span style="color: green;"><a>忘记密码?</a></span></div>
    </div>
    </form>
    </div>
</c:if>

</div>

<br />

<div style="border: 1px solid #ccc;">
<button onclick="create();">createSession</button>
&nbsp; &nbsp;
<button onclick="remove();">removeSession</button>
&nbsp; &nbsp;
<button onclick="normal();">null</button>
<br />
<br />
<%
    String type = request.getParameter("type");
    if (type != null && type.equals("create")) {
        session.setAttribute("aaa", "aaaaaaaa");
    }
    if (type != null && type.equals("remove")) {
        session.removeAttribute("aaa");
    }
%> ${sessionScope.aaa}</div>
<div>${sessionScope.SPRING_SECURITY_LAST_USERNAME} ${sessionScope.SPRING_SECURITY_CONTEXT.authentication}</div>
</body>
</html>