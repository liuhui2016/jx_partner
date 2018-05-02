<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增权限':'修改权限' }</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script>
		// 字符验证    
		jQuery.validator.addMethod("idName", function(value, element) {    
	 	 return this.optional(element) ||/^\w+$/.test(value);   
		}, "权限编码只能包括英文字母、数字和下划线");
		$(document).ready(function() {
			
			//聚焦第一个输入框
			$("#label").focus();
			$("#position").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
				 	 "name": {   
						required: true ,
						idName: true,
						remote: "authority!checkName.action?ids="+ '${id}'
				 	 },
					"label": {   
						required: true
						//remote: "authority!checkLabel.action?ids="  +'${id}'
		 	 		},
		 	 		"position":{
						number:true
		 	 		}
	        	},
	        	messages: {
	        		name: {
						required: "填写权限编码",
						remote:"权限编码已经存在"
					},
	        		label: {
						required: "填写权限名称",
						remote:"权限名称已经存在"
					}
				}
				
			});
			$('#pId').val('${parent.id}');
			$('#rId').val('${resource.id}');
				
			$('input[type=radio]').change(function(){
				if($('input[type=radio]:checked').val()==='true'){
					$("#menu").show();
				}else{
					$("#menu").hide();
				}
			});
            //初始化选择
            ${menu?'$("input[name=menu][value=true]")[0].checked=true;$("#menu").show()':''}
		});
	</script>
</head>

<body>
<div id="fixtitle">
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增权限</span>':'&nbsp;»&nbsp;<span>修改权限</span>' }</p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
<div class="itemtitle"><h2>${id==null?'新增权限':'修改权限' }</h2></div>
</div>
<div class="container">
  <!-- 内容区域 -->

  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form action="authority!save.action" method="post" id="inputForm">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv">
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<input type="hidden" name="oldname" value="${name}"/>
			<input type="hidden" name="parentId" value="${parentId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last"></th>
				    </tr>
				    <tr>
						<td  class="right"><span class="red">*</span>权限名称:${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td><input type="text" id="label" name="label" size="40" value="${label}" tip="前台显示名称，具有唯一性" /></td>
					</tr>
					<tr>
						<td  class="right"><span class="red">*</span>权限标识: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td><input type="text" id="name" name="name" size="40" value="${name}" tip="权限编码，具有唯一性，例如：sys_menu"/></td>
					</tr>
					<tr>
						<td  class="right">所属资源: </td>
						<td style="padding: 2px 10px;">
						<s:if test="#request.resourceList!=null">
						<table class="noborder">
								<tr>
								<c:forEach var="res" items="${resourceList}" varStatus="sta">
									<td class="noborder" >
                                    <input type="checkbox"  name="resIds" value="${res.id }" size="10" <c:forEach var="userdres" items="${resources}"><c:if test="${res.id == userdres.id}">checked</c:if></c:forEach> /><span title="${res.resString}"> ${res.label }</span>
                                    </td>
                                    <c:if test="${sta.count%6==0 }"></tr></c:if>
								</c:forEach>
							</table>
							</s:if>
							</td>
					</tr>
                    <tr>
                        <td  class="right">上级权限: </td>
                        <td>
                            
                             <select id="pId" class="text middle"  name="pid">
                                    <option value="">--根权限--</option>
                                    <c:forEach items="${authorityList}" var="c">
                                    <c:if test="${empty c.parent}"><optgroup label="${c.label}"></optgroup></c:if>
                                    <option  value=${c.id}>${c.formatLabel}</option>
                                    </c:forEach>
                            </select>
                        </td>
                    </tr>                    
					<tr>
						<td class="right">是否对应菜单<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><input type="radio" value="true" name="menu" tip="是否要作为菜单显示"/><span>是</span><input type="radio" value="false" name="menu" checked="checked"/><span>否</span></td>
					</tr>
                    <tbody id="menu" style="display: none;">
                    <tr>
                        <td  class="right">菜单图标: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                        <td><input type="text" id="menuImg" name="menuImg" size="40" value="${menuImg}" tip="菜单显示响应的图标"/></td>
                    </tr>
                    
					<tr>
						<td  class="right"><span class="red">*</span>排序数值: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><input type="text" id="position" name="position" size="40" value="${position}" tip="根据此数值进行排序显示"/></td>
					</tr>
					<tr>
						<td  class="right"><span class="red">*</span>权限资源: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td>
							 <select id="rId" class="text middle" name="rid" tip="菜单对应的资源类型">
				      				<option value="">--权限所对应的资源--</option>
				      				<c:forEach items="${resourceList}" var="c"><option value=${c.id}>${c.label}</option></c:forEach>
				      		</select>
				      	</td>
					</tr>
                    </tbody>

				    <tr>
				      <th class="first" width="130">附加信息</th>
				      <th class="last"></th>
				    </tr>
					<tr>
						<td  class="right">权限描述: </td>
						<td ><textarea cols="60" rows="4" id="description" name="description">${description}</textarea></td>
					</tr>
					<tr>
						<th class="first" width="130"></th>
						<th class="last">
						<security:authorize ifAnyGranted="A_sys_authority_add">
								<input class="button" type="submit" value="提 交"/>&nbsp;
						</security:authorize>
							<input class="button" type="button" value="返 回" onclick="history.back()"/>
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
