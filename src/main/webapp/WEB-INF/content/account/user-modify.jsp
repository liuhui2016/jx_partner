<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mini-Web 帐号管理</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	
	<script>
		$(document).ready(function() {
			$("#oldpassword").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					oldpassword:{
						required: true,
						remote: "user!checkPassword.action?eid="+encodeURIComponent('${entity.id}')
					},
					newpassword: {
						required: true,
						minlength:3
					},
					passwordConfirm: {
						equalTo:"#newpassword"
					}
				},
				messages: {
					oldpassword:{
						remote: "密码错误"
					},
					passwordConfirm: {
						equalTo: "输入与上面相同的密码"
					}
				}
			});
		});
	</script>
</head>

<body>
<div class="currloca">
  <p>系统管理&nbsp;»&nbsp;<span>密码修改</span>&nbsp;</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>密码修改</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>

	<form id="inputForm" action="user!updata.action" method="post">
		<input type="hidden"  name="entity.id" value="${entity.id}"/>
		<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last"></th>
				    </tr>
				    <tr>
				<td class="right">原始密码:</td>
				<td style="padding: 0 3px;"><input type="password" id="oldpassword"  name="oldpassword"   size="41" /></td>
			</tr>
			<tr>
				<td class="right">密码:</td>
				<td style="padding: 0 3px;"><input type="password" id="newpassword" name="newpassword" size="41" /></td>
			</tr>
			<tr>
				<td class="right">确认密码:</td>
				<td style="padding: 0 3px;"><input type="password" id="passwordConfirm" name="passwordConfirm" size="41" />
				</td>
			</tr>
			<tr>
				<th class="first" width="130"></th>
						<th class="last">
						<input class="button" type="submit" value="提交"/>&nbsp;
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</th>
			</tr>
			</tbody>
		</table>
	</form>
</div>
</body>
</html>
