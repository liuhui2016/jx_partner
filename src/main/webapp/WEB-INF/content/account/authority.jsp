<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title></title>
	<%@ include file="/common/head.jsp" %>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${auth.label}</h2></div>
  
  <form id="mainForm" action="authority.action" method="post">
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
  
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div><security:authorize ifAnyGranted="A_sys_authority_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('authority!input.action',false,'${authId}');"/></security:authorize></div>
	<div id="operate">
		<s:if test="#request.resultList!=null">
			&nbsp;选择根权限:<s:select list="#request.resultList"  theme="simple" listKey="id" listValue="label" label="选择根权限" name="parentId" onchange="return document.forms[0].submit();" ></s:select>
		</s:if>
	</div>
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
		  <tr>
		      <th class="first">序号</th>
		      <th>权限名称</th>
		      <th>权限标识</th>
		      <th>是否菜单</th>
		      <th>绑定的菜单资源</th>
              <th>菜单图标</th>
		      <th>菜单路径</th>
		      <th>菜单顺序</th>
		      <th class="last">操作</th>
		    </tr>
		<c:if test="${empty resultList}">
          <tr>
          <td colspan="7">无结果集</td>
          </tr>
          </c:if>
          
         <s:if test="#request.resultList!=null">
                    <c:forEach items="${authority.allChildrenAndUs}" var="c" varStatus="s">
                    <tr>
                        <td>${s.index+1}</td>
                        <td style="text-align: left;padding-left: 10px;"><a href="authority!detail.action?id=${c.id}&authId=${authId}">${c.formatLabel}</a></td>
                        <td>${c.name}</td>
                        <td>${c.menu?'是':'否'}</td>
                        <td>${c.resource.resString}</td>
                        <td>${c.menuImg}</td>
                        <td><c:if test="${c.menu}">${c.fullMenu}</c:if></td>
                        <td><c:if test="${c.menu}">${c.position}</c:if></td>   
                        
                        <td>
                        <security:authorize ifAnyGranted="A_sys_authority_add">
                        <a href="authority!input.action?id=${c.id}&authId=${authId}&parentId=${parentId}"><img src="${ctx}/images/edt.gif" width="16" height="16" border="0" alt="修改"/></a> 
                        </security:authorize>
                        <security:authorize ifAnyGranted="A_sys_authority_delete">
                        <img src="${ctx}/images/del.gif" width="16" height="16" style="cursor: pointer;" onclick="delItem('authority!delete.action?id=${c.id}&authId=${authId}&parentId=${parentId}');" alt="删除"/>
                        </security:authorize >
                        </td>
                    </tr>        
                    </c:forEach>              
		</s:if>		
    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
</div>
</body>
</html>