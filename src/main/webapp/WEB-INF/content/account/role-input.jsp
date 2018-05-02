<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增角色':'修改角色' }</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
    
    <!-- jsTree  -->
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.metadata.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/jquery.tree.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/_jquery.tree.rtl.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.checkbox.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.contextmenu.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.hotkeys.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.metadata.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.themeroller.js"></script>
    <link rel="stylesheet" href="${ctx}/js/jQuery/jsTree/themes/checkbox/style.css" type="text/css"></link>
    <!-- 解决样式冲突 -->
    <style>table {border-collapse:inherit;}</style>
    
	<script>
		$(document).ready(function() {

			if($.browser.msie && ($.browser.version=="6.0" || $.browser.version=="7.0")){
				$(".container").css("margin-top","");
				$(".container").css("padding-top",$('#fixtitle').innerHeight());
			}
			//聚焦第一个输入框
			//$("#name").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
					rules: {
					name: {
						required: true,
						remote: "role!checkRoleName.action?oldId=" + '${id}'
					},
                    code: {
                        required: true,
                        remote: "role!checkRoleCode.action?oldId=" + '${id}'
                    }                    
				},
				messages: {
					name: {
						remote: "角色名已存在"
					},
                    code: {
                        remote: "角色编码已存在"
                    }                    
				}
			});
		});
		
		//权限树 
		$(function (){
		    var ids = [];
		    // 遍历生成权限树
		    <c:forEach items="${auths}" var="a" varStatus="vs">
			$("#tree_${a.id}").tree({
	   			selected: [<s:iterator var="checkId" value="checkedAuthIds" status="sta">"${checkId}"<s:if test="! #sta.last">,</s:if></s:iterator>],
	    		data : { type : "json", async : false }, ui : { theme_name : "checkbox" },
		 		types:{ "default" : { draggable : false} }, plugins : { checkbox : {three_state : false } },  // false can not cascading
		 		callback : { 
					onload : function (t) { t.settings.data.opts.static = true; t.settings.plugins.checkbox.three_state = true; },
					beforedata : function (node, tree) { if(node == false) {tree.settings.data.opts.static = ${a.formatNameString}; } },//初始显示 
					onchange : function() { 
						var idArray = [];  //保存选中的id
						jQuery.tree.plugins.checkbox.get_checked().each( function (i,node){	idArray.push(node.id); });
						ids[${vs.index}] = idArray;
						//把所有的被选中的id 赋值给隐藏域
						$("#array").val(ids.toString());
					}
				}
			});
			</c:forEach>
		});

    </script>

	
</head>

<body>

<div id="fixtitle">

<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增角色</span>':'&nbsp;»&nbsp;<span>修改角色</span>' }</p>
  <div class="sitemap">
  	  <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="itemtitle"><h2>${id==null?'新增角色':'修改角色' }</h2></div>
</div>

<div class="container" >
  <!-- 内容区域 -->
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form action="role!save.action" method="post" id="inputForm">
  	<!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="">
	    	<input type="hidden" name="id" value="${id}"/>
		<input type="hidden" name="authId" value="${authId}"/>
		<table class="tab_cont" cellspacing="0" cellpadding="0" border="0" align="left">
			<tbody>
			    <tr>
			      <th class="first" width="130">标准信息</th>
			      <th class="last"></th>
			    </tr>
            <tr>
                <td  class="right"><span class="red">*</span>角 色 名:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                <td><input type="text" id="name" title="请输入角色名称" name="name" size="40" value="${name}" class="required" tip="用来表明用户身份，且名称唯一"/></td>
            </tr>
            <tr>
                <td  class="right"><span class="red">*</span>角色编码:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                <td><input type="text" id="code" title="请输入角色编码" name="code" size="40" value="${code}" class="required" tip="编码唯一"/></td>
            </tr>
			<tr>
				<td  class="right">角色描述:</td>
				<td valign="top"><textarea id="description" name="description" rows="2" cols="35" tip="为方便使用，请输入角色的相关描述">${description}</textarea></td>
			</tr>
			<tr>
				<td  class="right">授予权限:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
				<td>
				<table cellpadding="0" cellspacing="0" border="0" style="white-space:nowrap;" width="100%">
					<tr>
					    <!-- jsTree -->
					    <c:forEach items="${auths}" var="a">
					    <td valign="top" style="border:0;">
					    <div id="tree_${a.id}" class="treeDiv" tip="必须为用户分配权限，否则无法使用任何功能"></div>
					    </td>
					    </c:forEach>
					    <!-- jsTree end  -->
						<!-- 保存权限树被选中的所有节点的 id 字符串 -->
						<input  type="hidden"  id="array" name="checkedArray"/>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<th class="first" width="130"></th>
				<th class="last">
					<security:authorize ifAnyGranted="A_sys_role_add">
						<input class="button" type="submit" value="提交"/>&nbsp;
					</security:authorize>
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</th>
			</tr>
			</tbody>
		</table>
	    </div>
      </div>
	</form>
</div>

</body>
</html>
