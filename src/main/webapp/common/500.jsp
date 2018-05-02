<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="org.slf4j.Logger,org.slf4j.LoggerFactory" %>

<%
	Throwable ex = null;
	if (exception != null)
		ex = exception;
	if (request.getAttribute("javax.servlet.error.exception") != null)
		ex = (Throwable) request.getAttribute("javax.servlet.error.exception");

	//记录日志
	Logger logger = LoggerFactory.getLogger("500.jsp");
	logger.error(ex.getMessage(), ex);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href="${ctx}/css/core.css" type="text/css" rel="stylesheet"/>
	<title>500 - 系统内部错误</title>
</head>

<body>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>系统发生内部错误.</h2></div>

  <!-- 列表区域-->
  <div id="content" class="content">
  
<pre>
<% ex.printStackTrace(new java.io.PrintWriter(out));%>
</pre>
<div><a href="<c:url value="/"/>">返回首页</a></div>

</div>
</div>
</body>
</html>
