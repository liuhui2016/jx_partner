<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增用户':'修改用户' }</title>
	<%@ include file="/common/head.jsp" %>
    <script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<style type="text/css">
	.hidden{display: none;}
	</style>
	<script>
		$.fn.check = function(mode) {
			var mode = mode || 'on'; // if mode is undefined, use 'on' as default
			return this.each(function() {
				switch(mode) {
				case 'on':
					this.checked = true;
					break;
				case 'off':
					this.checked = false;
					break;
				case 'toggle':
					this.checked = !this.checked;
					break;
				}
			});
		};	

		$(document).ready(function() {
			//聚焦第一个输入框
			$("#loginName").focus();
			$.validator.addMethod("isNotNull",function(value,element,params){
				return $("#checkedUseRoleIds").children().length > 0;
			},$.format("用户角色不能为空！"));
			//为inputForm注册validate函数
			$("#inputForm").validate({
				rules: {
					username: {
						required: true,
						remote: "user!checkLoginName.action?oldId=" + '${id}'
					},
					name: "required",
					password: {
						${id==null?'required: true,':''}
						minlength:3
					},
					passwordConfirm: {
						equalTo:"#password"
					},
					email: {
						email:"email",
						required: true,
						remote: "user!checkEmailName.action?oldId=" + '${id}'
					},
					checkedUseRoleIds:{
						isNotNull:true
					},entryDate: "required",birthDate: "required",sex: "required"
					
				},
				messages: {
					username: {
						remote: "用户登录名已存在"
					},
					passwordConfirm: {
						equalTo: "输入与上面相同的密码"
					},
					email: {
						remote: "Email已存在"
					}
				},
				submitHandler:function(form) {
					selectAll();

					if($('#passwordConfirm').val().length>0){
						$('#hidepassword').val("");
					}

				 	$('#inputForm')[0].submit();
				 	
				 }
				
			});
			
			$("#domainId").change(function(){
				getPcom();
			});
		});
		
		function copyAllAdd(fromObject, toObject) {
		    for ( var i = 0, l = fromObject.options.length; i < l; i++) {
		    	addOptionAdd(toObject, fromObject.options[i].text,
		                fromObject.options[i].value);
		    }
		}
		function addOptionAdd(object, text, value) {
		    var defaultSelected = false;
		    var selected = false;
		    var optionName = new Option(text, value, defaultSelected, selected);
		    object.options[object.length] = optionName;
		}
		function leftToRight(tb,str){
				//alert(tb.value);
				//alert(str);
				if(str=="use"){
					var allRole = document.getElementById("allRoleForUse");
					var useRoles = document.getElementById("checkedUseRoleIds");
					if(tb.value==" >"){
						copySelected(allRole,useRoles);
					}else{
						copyAll(allRole,useRoles);
					}
				}
				if(str=="grant"){
					var allRole = document.getElementById("allRoleForGrant");
					var grantRoles = document.getElementById("checkedGrantRoleIds");
					if(tb.value==" >"){
						copySelected(allRole,grantRoles);
					}else{
						copyAll(allRole,grantRoles);
					}

				}
			}
			function rightToLeft(tb,str){
				if(str=="use"){
					var allRole = document.getElementById("allRoleForUse");
					var useRoles = document.getElementById("checkedUseRoleIds");
					if(tb.value=="< "){
						copySelected(useRoles,allRole);
					}else{
						copyAll(useRoles,allRole);
					}
				}
				if(str=="grant"){
					var allRole = document.getElementById("allRoleForGrant");
					var grantRoles = document.getElementById("checkedGrantRoleIds");
					if(tb.value=="< "){
						copySelected(grantRoles,allRole);
					}else{
						copyAll(grantRoles,allRole);
					}

				}
			}
			function selectAll(){
				var useRoles = document.getElementById("checkedUseRoleIds");
				var grantRoles = document.getElementById("checkedGrantRoleIds");
				for(var i=0;i<useRoles.options.length;i++){
					useRoles.options[i].selected =true;
				}
				for(var i=0;i<grantRoles.options.length;i++){
					grantRoles.options[i].selected =true;
				}
			}
			function repwdOncus(){
				document.getElementById('passwordConfirm').disabled="";
			}
	</script>
</head>

<body>
<!-- 用户路径 -->
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增用户</span>':'&nbsp;»&nbsp;<span>修改用户</span>' }</p>
  <div class="sitemap">
    <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
<!-- 内容区域 -->
<div class="container">
  <div class="itemtitle"><h2>${id==null?'新增用户':'修改用户' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
	<form id="inputForm" action="user!save.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" colspan="3"></th>
				    </tr>
				    <tr>
						<td  class="right"><span class="red">*</span>登 录 名: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td>
						<s:if test="id==null">
							<input type="text" name="username" size="38" id="username" value="${username}" tip="用户登录帐号，具有唯一性"/>
						</s:if>
						<s:else>
							<input type="text" name="username" size="38" id="username" value="${username}" disabled="disabled"/>
						</s:else>
						</td>
						
						
						<td  class="right"><span class="red">*</span>用 户 名: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td><input type="text" id="realName" name="realName" size="38" 	<s:if test="id != null "> value="${realName}" </s:if>   tip="用户登录显示名字"/></td>
					</tr>
					
					<tr>
						<td  class="right">${id==null?'<span class="red">*</span>':''}密  码: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td>
						<s:if test="id==null">
							<input type="password" name="password" size="38" id="password" value="${password }"  autocomplete="off" onpaste="return false" tip="为方便使用，请输入角色的相关描述"/>
						</s:if>
						<s:else>
							<input type="password" name="password" size="38" id="password" onfocus="repwdOncus()"/>
							&nbsp;&nbsp;&nbsp;&nbsp;如修改密码、请填写
							<input type="hidden" id="hidepassword" name="hidepassword" value="${password}"/>
						</s:else>
						</td>
						<td  class="right">${id==null?'<span class="red">*</span>':''}确认密码: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td>
						<s:if test="id==null">
							<input type="password" name="passwordConfirm" size="38" id="passwordConfirm" tip="为方便使用，请输入角色的相关描述"/>
						</s:if>
						<s:else>
							<input type="password" name="passwordConfirm" size="38" id="passwordConfirm" disabled="disabled"/>
						</s:else>
						</td>
					</tr>

					<tr>
						<td  class="right"><span class="red">*</span>邮  箱: ${id==null?'<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>':''}</td>
						<td><input type="text" id="email" name="email" size="38" value="${email}" tip="用于沟通的邮箱地址，具有唯一性"/></td>
						<td  class="right">电  话: </td>
						<td><input type="text" id="phone" name="phone" size="38" value="${phone}"/></td>
					</tr>
					
					<tr>
						<td  class="right"><span class="red">*</span>用户角色: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td colspan="3">
							<table tip="用户所拥有的角色">
							<tr>
							<td>
							<select id="allRoleForUse" name="allRoleForUse" size="10" style="width:120px;height:110px" multiple="multiple">
							<c:forEach items="${allRoleList}" var="ar">
								<c:forEach items="${checkedUseRoleIds}" var="cur">
								<!--script>alert(${cur}+"  "+${ar.id});</script-->		
									<c:if test="${cur==ar.id}">	
									<c:set var="checkFlag" value="true" />
									</c:if>
								</c:forEach>
								<!--script>alert(${checkFlag});</script-->	
								<c:if test="${checkFlag!='true'}">
									<option value="${ar.id}">${ar.name}</option>
								</c:if>
								<c:set var="checkFlag" value="false" />	
							</c:forEach>
							</select>
							</td>
							<td>
							<table>
							<tr><td><input type="button" value=" >" onclick="leftToRight(this,'use')" /></td></tr>
							<tr><td><input type="button" value=">>" onclick="leftToRight(this,'use')" /></td></tr>
							<tr><td><input type="button" value="<<" onclick="rightToLeft(this,'use')"/></td></tr>
							<tr><td><input type="button" value="< " onclick="rightToLeft(this,'use')"/></td></tr>
							</table>
							</td>
							<td>
							<select id="checkedUseRoleIds" name="checkedUseRoleIds" size="10" style="width:120px;height:110px" multiple="multiple">
							<c:forEach items="${useRoleList}" var="ur">
								<option value="${ur.id}">${ur.name}</option>
							</c:forEach>
							</select>
							</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td  class="right">可支配角色: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td colspan="3">
						<table tip="用户可以控制的角色级别">
							<tr>
							<td>
							<select id="allRoleForGrant" name="allRoleForGrant" size="10" style="width:120px;height:110px" multiple="multiple">
							<c:forEach items="${allRoleList}" var="ar">
								<c:forEach items="${checkedGrantRoleIds}" var="cgr">
									<c:if test="${cgr==ar.id}">
										<c:set var="checkFlag" value="true"/>
									</c:if>
								</c:forEach>
								<c:if test="${checkFlag!='true'}">
									<option value="${ar.id}">${ar.name}</option>
								</c:if>
								<c:set var="checkFlag" value="false" />
							</c:forEach>
							</select>
							</td>
							<td>
							<table>
							<tr><td><input type="button" value=" >" onclick="leftToRight(this,'grant')"/></td></tr>
							<tr><td><input type="button" value=">>" onclick="leftToRight(this,'grant')"/></td></tr>
							<tr><td><input type="button" value="<<" onclick="rightToLeft(this,'grant')"/></td></tr>
							<tr><td><input type="button" value="< " onclick="rightToLeft(this,'grant')"/></td></tr>
							</table>
							</td>
							<td>
							<select id="checkedGrantRoleIds" name="checkedGrantRoleIds" size="10" style="width:120px;height:110px" multiple="multiple">
							<c:forEach items="${grantRoleList}" var="gr">
								<option value="${gr.id}">${gr.name}</option>
							</c:forEach>
							</select>
							</td>
							</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td  class="right">账户可用: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><span tip="当前账户是否可用"></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="enabled" value="true" <c:if test="${id eq null || enabled}">checked="checked"</c:if> />可用
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="enabled" value="false" <c:if test="${!enabled }">checked="checked"</c:if> />不可用
						</td>
						<td  class="right">帐号过期: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><span tip="当前账户是否过期"></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="accountExpired" value="true" <c:if test="${accountExpired}">checked="checked"</c:if> />过期
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="accountExpired" value="false" <c:if test="${!accountExpired}">checked="checked"</c:if> />不过期
						</td>
					</tr>
					
					<tr>
						<td  class="right">帐号锁定: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><span tip="帐号是否被锁定"></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="accountLocked" value="true" <c:if test="${accountLocked}">checked="checked"</c:if> />锁定
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="accountLocked" value="false" <c:if test="${!accountLocked}">checked="checked"</c:if> />不锁定
						</td>
						<td  class="right">证书过期: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
						<td><span tip="证书是否过期"></span>
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="credentialsExpired" value="true" <c:if test="${credentialsExpired}">checked="checked"</c:if> />过期
							&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="credentialsExpired" value="false" <c:if test="${!credentialsExpired}">checked="checked"</c:if> />不过期
						</td>
					</tr>
					
					<tr>
						
							<td  class="right"><span class="red">*</span>所 属 组: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
							<td style="padding-top: 8px;" colspan="3">
								<select id="domainId" class="text middle" name="domainId" tip="当前用户所属业务级别">
								<c:if test="${empty cats}">
									<option value="">无数据</option>
								</c:if>
                                <c:forEach items="${cats}" var="c">
	                            <option value="${c.id}" <c:if test="${c.id==domain.id}">selected="selected"</c:if>>${c.formatLabel}</option>
                                </c:forEach>                                    
				      			</select>
				      			<!-- &nbsp; <span style="color: red;">请慎重选择,选择后不能编辑</span> -->
							</td>
						<s:if test="id==null">
                        </s:if>	
						<s:else>
                        <!-- 
                            <td  class="right">所 属 组: <input type="hidden" name="domain.id" value="${domain.id}"/></td>
                            <td>
                            ${domain.label}
                            </td>
                             -->
						</s:else>
					</tr>
					<tr>
                        <td  class="right">客户ID: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                        <td colspan="3">
                        	<textarea id="customId" name="customId" rows="5" cols="100" tip="如果是商务，多个客户ID，以','隔开">${customId}</textarea>
                        </td>
                    </tr>
					<tr id="dxbcom" class="hidden">
                      <td class="right" width="130">所属大区分公司:</td>
                      <td>
                      <select id="subcode" name="subcode" ></select>
                      <wlps:dicSelect id="dcxb" dcode="dc_xb" name="dc_xb" className="hidden"></wlps:dicSelect>
                      <wlps:dicSelect id="dcdb" dcode="dc_db" name="dc_db" className="hidden"></wlps:dicSelect>
                      </td>
                    </tr>
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="3">
							<security:authorize ifAnyGranted="A_sys_user_add">
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
