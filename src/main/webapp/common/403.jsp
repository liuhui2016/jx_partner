<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="${ctx}/css/core.css" type="text/css" rel="stylesheet"/>
	<title>403 - 缺少权限</title>
</head>

<body>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>错误提示</h2></div>

  <!-- 列表区域-->
  <div id="content" class="content">
  
<div style="padding-top: 20px;text-align: center;">
	<div><h2>你没有访问该页面的权限.</h2></div>
	<div> <a href="<c:url value="/securityLogout.action"/>">退出登录</a></div>
</div>

</div></div>
</body>
</html>