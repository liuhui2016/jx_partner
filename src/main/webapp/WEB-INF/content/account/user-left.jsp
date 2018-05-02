<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<base target="mainframe">
<style type="text/css">
<!--
body {
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
td{font-size: 12px;}
.STYLE1 {
	font-size: 12px;
	color: #FFFFFF;
}
.STYLE3 {
	font-size: 12px;
	color: #033d61;
}
a {text-decoration: none;}
-->
</style>
<style type="text/css">
.menu_title SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #ffffff; POSITION: relative; TOP: 2px 
}
.menu_title2 SPAN {
	FONT-WEIGHT: bold; LEFT: 3px; COLOR: #FFCC00; POSITION: relative; TOP: 2px
}

</style>
    <!-- dTree  -->
    <script type="text/javascript" src="${ctx}/js/jQuery/jquery.1.4.2.js"></script>
    <script type="text/javascript" src="${ctx}/js/dtree.js"></script>
    <link type="text/css" rel="stylesheet" href="${ctx}/css/dtree.css" />
    <script type="text/javascript"> var ctx='${ctx}'</script>
</head>

<body>

<table width="10" height="100%" border="0" cellpadding="0" cellspacing="0">
<!-- 
  <tr>
    <td height="28" background="${ctx}/images/main_40.gif">
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="19%">&nbsp;</td>
        <td width="81%" height="20"><span class="STYLE1">操作菜单</span></td>
      </tr>
    </table>
    </td>
  </tr>
  -->
  <tr>
    <td valign="top">
    <table width="151" border="0" align="center" cellpadding="0" cellspacing="0">
    
	  <tr>
        <td>
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td><div id="tree">
            
<script>
    //权限树
    d = new dTree('d');
    d.add(${domain.id},-1,"${domain.label}","${ctx}/account/user.action?domainId=${domain.id}&authId=${authId}","${domain.description}","userRightIFrame");
    <c:forEach items="${domain.allChildrenListByStateTrue}" var="a">
    <c:if test="${a.class == 'class com.game.entity.account.Domain'}">
    d.add(${a.id}
            ,${a.parent.id}
            ,"${a.label}" 
            ,"${ctx}/account/user.action?domainId=${a.id}&authId=${authId}"
            ,"${a.description}","userRightIFrame"
            );
    </c:if>
    </c:forEach>
    document.write(d);    
    d.openAll();   
    </script>
            
            </div><td>
          </tr>
        </table></td>
      </tr>      
      
      
    </table></td>
  </tr>

</table>

</body>
</html>
