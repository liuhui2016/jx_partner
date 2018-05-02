<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp" %>
<style type="text/css">
.content td, th {white-space:normal;}
p{line-height: 150%;text-indent: 20px}
h2{margin-bottom: 10px;}
</style>
</head>

<body>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>欢迎登录企业文化</h2></div>

  <!-- 列表区域-->
  <div id="content" class="content">
  	<div>
   		<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
			      <tbody>
			      <tr>
			        <td valign="top" colspan="2">
			        		<h2 style="color:#000">企业文化</h2><p> 提供内部期刊。</p>
					</td>
			        <td width="7%">&nbsp;</td>
</tr>

			    </tbody></table>
    </div>
  </div>
  </div>

  
  <!-- 分页区域-->
  <div id="page" class="page">
 
  </div>  
  

</body>
</html>

