<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>管理</title>
    <%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
    <link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="${ctx}/js/jQuery/timepicker.js" type="text/javascript"></script>
	<script>

	$(document).ready(function() {
		$("#inputForm").validate({
			rules: {
				"subject": {
					required: true
				},
				"contents": {
					required: true
				}
			},
			messages: {
				"subject": {
					required: "主题不能为空"
				},
				"contents": {
					required: "内容不能为空"
				}
			},
			submitHandler: function(form) {
				selectAll();
			 	$('#inputForm')[0].submit();
			 	
			 }
			
		});	
	});
	function leftToRight(tb){
			var allRole = document.getElementById("allRoleForUse");
			var useRoles = document.getElementById("checkedUseRoleIds");
			if(tb.value==" >"){
				copySelected(allRole,useRoles);
			}else{
				copyAll(allRole,useRoles);
			}
		}
	function rightToLeft(tb){
		var allRole = document.getElementById("allRoleForUse");
		var useRoles = document.getElementById("checkedUseRoleIds");
		if(tb.value=="< "){
			copySelected(useRoles,allRole);
		}else{
			copyAll(useRoles,allRole);
		}
	}
	function selectAll(){			
		var useRoles = document.getElementById("checkedUseRoleIds");
		for(var i=0;i<useRoles.options.length;i++){
			useRoles.options[i].selected =true;
		}
	}
	function paths(){
		var pFilePath = document.getElementById("file").value;
		var temp_win = pFilePath.lastIndexOf("\\");
	    var temp_unix = pFilePath.lastIndexOf("/");
	    if (temp_win>0)
	    {
	        temp = temp_win;
	    }
	    else if (temp_unix>0)
	    {
	        temp = temp_unix;
	    }
	    else
	    {
	        temp = -1;    
	    }
	    file_name = pFilePath.substr(temp+1);
	    document.getElementById("filename").value= file_name;
	}
	function clears(){
		rightToLeft(this);
		$('#inputForm')[0].reset();
	}
	</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}</span></p>
  <div class="sitemap">
  <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
  	<span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'发送E-mail':'发送E-mail' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="sendmail!sendmail.action" method="post" enctype="multipart/form-data">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
			<table class="tab_cont"  width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">选择收件人</th>
				      <th class="last"></th>
				    </tr>				   
					<tr>
						<s:if test="id==null">
							<td  class="right">管理域: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
							<td style="padding-top: 8px;">
								<select id="domain.id" class="text middle" name="domainId" tip="选择管理域">
                                <option selected="selected"></option>
                                <c:forEach items="${cats}" var="c">
                                <c:if test="${c.class == 'class com.game.entity.account.Domain'}"></c:if>
                                <option value="${c.id}" <c:if test="${c.id==parent.id}">selected="selected"</c:if>>${c.formatLabel}</option>
                                
                                </c:forEach>                                    
				      			</select>
							</td>
						</s:if>	
						<s:else>
							<td colspan="2"><input type="hidden" name="domain.id" value="${domain.id}"/></td>
						</s:else>
					</tr>
					<tr>
						<td class="right">角色:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td>
							<table tip="选择角色">
							<tr>
							<td>
							<select id="allRoleForUse" name="allRoleForUse" size="10" style="width:120px;height:110px" multiple="multiple">
							<c:forEach items="${allRoleList}" var="ar">
							  <option value="${ar.id}">${ar.name}</option>
							</c:forEach>
							</select>
							</td>
							<td>
							<table>
							<tr><td><input type="button" value=" >" onclick="leftToRight(this)" /></td></tr>
							<tr><td><input type="button" value=">>" onclick="leftToRight(this)" /></td></tr>
							<tr><td><input type="button" value="<<" onclick="rightToLeft(this)"/></td></tr>
							<tr><td><input type="button" value="< " onclick="rightToLeft(this)"/></td></tr>
							</table>
							</td>
							<td>
							<select id="checkedUseRoleIds" name="checkedUseRoleIds" size="10" style="width:120px;height:110px" multiple="multiple">

							</select>
							</td>
							</tr>
							</table>
						</td>
					</tr>			
					<tr>
						<td class="right">手录收件人:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td ><input type="text" id="to" name="to" size="90" tip="输入多个邮件地址时请用空格隔开" maxlength="255" value="${to}"/></td>
					</tr>
					<tr>
						<td  class="right">抄送:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><input type="text" id="cc" name="cc" size="90" tip="输入多个邮件地址时请用空格隔开" maxlength="255" value="${cc}"/></td>
					</tr>
				    <tr>
				      <th class="first" width="130">Email内容</th>
				      <th class="last"></th>
				    </tr>						
					<tr>
						<td  class="right"><span class="red">*</span>主题:</td>
						<td><input type="text" id="subject" name="subject" size="90" maxlength="255" value="${subject}"/></td>
					</tr>
					<tr>
						<td  class="right"><span class="red">*</span>内容:</td>
						<td><textarea rows="10" cols="86" id="contents" name="contents">${contents}</textarea> </td>
					</tr>
					<tr>
						<td  class="right">类别:</td>
						<td><select tip="内容是HTML的或是文本的" id="html" name="html" >
			<option selected="selected" value="false">文本</option><option value="ture">HTML</option>
		</select></td>
					</tr>
					<tr>
						<td  class="right">附件:</td>
						<input type="hidden" id="filename" name="filename" value=""/>
						<td><input class="button" type="file" id="file" name="file" onmouseout="paths()"/></td>
					</tr>
					<tr>
						<th class="first" width="130"></th>
						<th class="last">
							
							<input class="button" type="submit" value="发送"/>&nbsp;
							<input class="button" type="button" value="清空" onclick="clears()"/>
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