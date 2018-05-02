<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>角色信息</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
    
    <!-- jsTree  -->
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.hotkeys.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/jquery.metadata.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/lib/sarissa.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/jquery.tree.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/_jquery.tree.rtl.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.checkbox.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.contextmenu.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.cookie.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.hotkeys.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.metadata.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.themeroller.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.xml_flat.js"></script>
	<script type="text/javascript" src="${ctx}/js/jQuery/jsTree/plugins/jquery.tree.xml_nested.js"></script>
    <link rel="stylesheet" href="${ctx}/js/jQuery/jsTree/themes/checkbox/style.css" type="text/css"></link>
    <!--  -->
    <style>table {border-collapse:inherit;}</style>

    
	<script>
		//权限树 
		$(function (){
		    // 遍历生成权限树
		    <c:forEach items="${auths}" var="a" varStatus="vs">
			$("#tree_${a.id}").tree({
	   			selected: [<s:iterator var="checkId" value="checkedAuthIds" status="sta">"${checkId}"<s:if test="! #sta.last">,</s:if></s:iterator>],
	    		data : { type : "json", async : false }, ui : { theme_name : "classic" },
		 		types:{ "default" : { draggable : false} }, 
		 		callback : { 
					onload : function (t) { t.settings.data.opts.static = true;},
					beforedata : function (node, tree) { if(node == false) {tree.settings.data.opts.static = ${a.formatNameString}; } }//初始显示 
				}
			});
			</c:forEach>
		});
    </script>
	
	
</head>

<body>

<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>角色信息</span></p>
  <div class="sitemap">
    <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
<div class="itemtitle"><h2>角色信息</h2></div>

<div class="container">
  <!-- 内容区域 -->
    <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
      <!-- 列表区域-->
  <div id="content" class="content input">
    <div id="indiv" >
	    <table class="tab_cont" cellspacing="0" cellpadding="0" border="0" align="left">
		    <tbody>
		        <tr>
			      <th class="first" width="130">标准信息</th>
			      <th class="last"></th>
			    </tr>
			    <tr>
				<td class="right">角 色 名/编码:</td>
				<td>${entity.name}/${entity.code}</td>
			</tr>
			<tr>
				<td class="right">角色描述:</td>
				<td>${entity.description}</td>
			</tr>
			<tr>
				<td valign="top">授予权限:</td>
				<td valign="top">
					<table cellpadding="0" cellspacing="0" border="0" style="white-space:nowrap;" width="100%">
						<tr>
						   <!-- jsTree -->
						    <c:forEach items="${auths}" var="a">
						    <td valign="top" style="border:0;">
						    	<div id="tree_${a.id}" class="treeDiv"></div>
						    </td>
						    </c:forEach>
						    <!-- jsTree end  -->
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>创建时间:</td>
				<td>${entity.createTime}</td>
			</tr>
			<tr>
				<th class="first" width="130"></th>
				<th class="last">
					<input class="button" type="button" value="返回" onclick="history.back()"/>
				</th>
			</tr>
		    </tbody>
		</table>
    </div>
  </div>
</div>
</body>
</html>
