<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增资源':'修改资源' }</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script>

		$(document).ready(function() {
			//聚焦第一个输入框
	    	$("#label").focus();
			// 字符验证    
			var exp = /^\S+$/;
			jQuery.validator.addMethod("idName", function(value, element) {
		 	 return this.optional(element) || exp.test(value);   
			}, "资源字符串中不能有空格！");
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					"label": {
						required: true
					},
					"resString": {
						required: true,
						idName: true,
						remote: "resource!checkResString.action?oldId=" + '${id}'
					}
				},
				messages: {
					"label": {
						required: "请输入资源名称"
					},
					"resString": {
						required: "请输入资源字符串",
						remote: "资源字符串已存在"
					}
				}
			})	
		});
	</script>
	
	
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增资源</span>':'&nbsp;»&nbsp;<span>修改资源</span>' }</p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增资源':'修改资源' }</h2></div>
    
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="resource!save.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    </div>
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last"></th>
				    </tr>
				    <tr>
						<td class="right"><span class="red">*</span>资源名称: </td>
						<td><input type="text" name="label" size="40" id="label" maxlength="255" value="${label}"/></td>
					</tr>
					<tr>
						<td class="right"><span class="red">*</span>资源字符串:${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td><input type="text" id="resString" name="resString" size="40" maxlength="255" value="${resString}" tip="资源对应的url地址"/></td>
					</tr>
					<tr>
						<td class="right">资源描述:</td>
						<td><input type="text" id="description" name="description" size="40" maxlength="255" value="${description}"/></td>
					</tr>
					<tr>
						<td class="right">资源类型:</td>
						<td>
							<s:select name="resType" id="resType" list="resTypeLabelValueList" listKey="value" listValue="label" theme="simple"/>
						</td>
					</tr>			
					<tr>
						<th class="first" width="130"></th>
						<th class="last">
							<security:authorize ifAnyGranted="A_sys_resource_add">
								<input class="button" type="submit" value="提交"/>&nbsp;
							</security:authorize>
							<input class="button" type="button" value="返回" onclick="history.back()"/>
						</th>
					</tr>
				</tbody>
			</table>
	    </div>
  </form>
</div>
</body>
</html>
