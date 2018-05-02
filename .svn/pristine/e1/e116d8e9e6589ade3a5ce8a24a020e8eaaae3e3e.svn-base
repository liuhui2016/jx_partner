<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% request.setAttribute("now",new java.util.Date()); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>welcome</title>
    <%@ include file="/common/head.jsp" %>
	<script>
    function getT(times){
          var today=new Date(times);
          var month=today.getMonth()+1;
          var year=(today.getYear() > 200) ? today.getYear() : 1900 + today.getYear();
          var tt = year+'-'+month+'-'+today.getDate()+' '+today.getHours()+':'+today.getMinutes()+'.'+today.getMilliseconds();
         return tt; 
        }
	</script>
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

   
  <!-- 附加信息-->
  <div id="message" class="message">
    This shows a list of the users that have a session with System.
    <br/>当前时间：<fmt:formatDate value="${now}" pattern="yy/MM/dd kk:mm:ss EEEE"/>
  </div>  

  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div></div>
    
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
          
 <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody>
        <tr>
		  <th class="first"><b>Session Id</b></th>
		  <th><b>用户</b></th>
          <th><b>姓名</b></th>
          <th><b>角色</b></th>
          <th><b>Domain</b></th>
		  <th><b>IP Address</b></th>
		  <th><b>Last Accessed</b></th>
		  <th><b>Session Creation</b></th>
          <th class="last"><b>Life</b></th>
        </tr>
        </tbody>
        <tbody id="listSearch">
<c:forEach var="u" items="${applicationScope.userNames}">
		<tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
            <td>${u.key.id}</td>
            <td>${u.value.principal.username}</td>
            <td>${u.value.principal.realName}</td>
            <td>${u.value.principal.useRoleNames}</td>
            <td>${u.value.principal.domain.label}</td>
            <td title="${u.value.details.sessionId}">${u.value.details.remoteAddress}</td>
            <td><script>document.write(getT(${u.key.lastAccessedTime}));</script></td>
            <td><script>document.write(getT(${u.key.creationTime}));</script> </td>
            <td><fmt:formatNumber value="${(u.key.lastAccessedTime-u.key.creationTime)/60000}" pattern=".00m"/></td>     
        </tr>
</c:forEach>
        </tbody>
        </table>
      </div>      			


    </div>
    </div>
    
</body>
</html>