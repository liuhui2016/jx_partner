<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp" %>
<style type="text/css">
<!--

-->
</style>

<script>

</script>

<style>
.tab_cont th, .tab_cont td {
height:22px;
line-height:22px;
text-align:left;
font-size: 8pt;
}
.content td, th {
padding:1px 10px;
white-space:normal;
}
</style>

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

  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div></div>
  </div>  

  <!-- 列表区域-->
  <div id="content" class="content">

  <div id="indiv" style="width:100%;OVERFLOW-X:auto;">


  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Properties -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">

    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">弹出框消息队列</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top">&nbsp; <b></b></td>
                <td valign="top" style="font-size: 8pt;line-height:22px;"></td>
            </tr>
    </tbody>
       
   </table>
      


  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Info -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">邮件队列</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
 
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b></b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;"></td>
            </tr>
    </tbody>

  </table>


  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Properties -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">

    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">短信息队列</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top">&nbsp; <b></b></td>
                <td valign="top" style="font-size: 8pt;line-height:22px;"></td>
            </tr>
    </tbody>
       
   </table>
      
      
  </div>   
        	
  </div>
 
</div>

</body>
</html>

