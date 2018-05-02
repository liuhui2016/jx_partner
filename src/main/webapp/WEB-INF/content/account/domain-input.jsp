<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${id==null?'新增域':'修改域' }</title>
    <%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
    <link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>

<script>
        $(document).ready(function() {
            $("#label").focus();
            $("#inputForm").validate({
                rules: {
                    label: {
                        required: true,
                        maxlength: 255,
                        remote: "domain!checkDomainLabel.action?oldId=" + '${id}'
                    },
                    code: {
                        required: true,
                        maxlength: 255,
                        remote: "domain!checkDomainCode.action?oldId=" + '${id}'
                    },
                    description: {
                        maxlength:255   
                     },
                    shortName: {
                    	 required: true  
                     },
                     alias: {
                         maxlength:2,
                         minlength:2
                     },
					 position:{
	                    	number:true
	                    }
                },    
                messages: {
                    label: {
                    required: "域名称不能为空！",
                    remote: "该域名称已存在！",
                    maxlength: "请输入一个长度最多是255的字符！"
                    },
                    code: {
                    required: "域编码不能为空！",
                    remote: "该域编码已存在！",
                    maxlength: "请输入一个长度最多是255的字符！"
                    },
                    description: {
                        maxlength: "请输入一个长度最多是255的字符！"
                    },
                    shortName: {
                    	required: "简称不能为空！"
                    },
                    alias: {
                        maxlength:"长度为只能2位"
                    },
					position:{
						number:"排列序号只能为数字"
					}
                }
            });
        });
    </script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增域</span>':'&nbsp;»&nbsp;<span>修改域</span>' }</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">

  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增域':'修改域' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="domain!save.action" method="post">
    <!-- 列表区域-->
      <div id="content" class="content input">
        <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
            <input name="id" value="${id}" type="hidden" />
            <input name="base" value="true" type="hidden" />
                <input name="authId" value="${authId}" type="hidden" />
                <c:if test="${id == null}">
	                <input name="autoCode" value="1020000000000000000" type="hidden" />
	                <input type="hidden" name="pId" value="1"/>
                </c:if>
                <table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
                    <tbody>
                        <tr>
                          <th class="first" width="130">标准信息</th>
                          <th class="last"></th>
                        </tr>
                        <tr>
                            <td  class="right"><span class="red">*</span>域 名 称: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" name="label" class="large" id="label" value="${label}" tip="前台显示名称"/></td>
                        </tr>
                        <tr>
                            <td  class="right"><span class="red">*</span>简&nbsp;&nbsp; 称: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" name="shortName" class="large" id="shortName" value="${shortName}" tip="简称不能为空！"/></td>
                        </tr>
                        <tr>
                            <td  class="right">别&nbsp;&nbsp; 名: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" name="alias" class="large" id="alias" value="${alias}" tip="请填写两位字母、数字或组合！"/></td>
                        </tr>
                        <!--
                        <tr>
                            <td  class="right"><span class="red">*</span>域名（Name）: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" name="name" class="meddle" id="name" value="${name}" tip="为方便使用，请输入角色的相关描述"/></td>
                        </tr>
                        -->
                        <tr>
                            <td  class="right"><span class="red">*</span>域 编 码: <img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" name="code" class="meddle" id="code" value="${code}" tip="域所属级别编码，具有唯一性"/></td>
                        </tr>
                        <tr>
                            <td class="right"><span class="red">*</span>状态:</td>
                        <td>
                        <s:radio list="#{true:'正常',false:'作废'}" name="state" theme="simple" id="enabled" value="%{id!=null?state:true}" > </s:radio>
                        </td>
                        </tr>
                        <tr>
							<td  class="right">排列序号:</td>
							<td ><input type="text" id="position" name="position" class="normal" value="${position}"/></td>
						</tr>
                        <tr>
                          <th class="first" width="130">附加信息</th>
                          <th class="last"></th>
                        </tr>
                        <tr>
                            <td  class="right">域 描 述: </td>
                            <td><textarea name="description" id="description" class="large">${description}</textarea></td>
                        </tr>
                        <tr>
                            <th class="first" width="130"></th>
                            <th class="last"><security:authorize
                                ifAnyGranted="A_sys_domain_add">
                                <input class="button" type="submit" value="提交" />&nbsp;
                        </security:authorize> <input class="button" type="button" value="返回"
                                onclick="history.back()" /></th>
                        </tr>
                    </tbody>
                </table>
        </div>
      </div>
  </form>
</div>
</body>
</html>