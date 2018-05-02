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
function send(){
    //mailconf!testMail.action?authId=${authId}
    var to =$('#to').val();
    if(to){
        location.href="mailconf!testMail.action?authId=${authId}&to="+to;
    }else{
        jAlert("请输入接收邮箱地址");}
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
    <p>The table below shows the SMTP mail server currently configured for System.</p>
  </div>  

  <!-- 列表区域-->
  <div id="content" class="content">

  <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
 
  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Properties -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">

    <tbody>
        <tr>
            <th class="first" width="100" >名称</th>
            <th width="250">详细信息</th>
            <th class="last">操作</th>
        </tr>
    </tbody>
    <tbody id="listSearch">
        <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
            <td style="font-size: 8pt;line-height:22px;">&nbsp; <b>SMTP Server</b></td>
            <td  >
                <b>发件人地址</b>: ${entity.defaultfrom}<br/>
                <!-- b>Prefix</b>: [YCPS]<br/ -->
                <b>主机</b>: ${entity.host}<br/>
                <!--b>协议</b>: ${protocol}<br/-->
                <b>用户名</b>: ${entity.username}<br/>
            </td>
            <td>
                <a href="mailconf!input.action?id=${entity.id}&authId=${authId}">编辑</a>
                | <a onclick="javascript:$('#test').show();" style="cursor: pointer;">Send a Test Email</a></br>
                <div id="test" style="display: none;">
               接收邮箱地址: <input type="text" id="to" name="to"/> <input type="button" value="提交" onclick="send();"/></div>
            </td>
        </tr>
    </tbody>

  </table>
  
  
  </div>   
        	
  </div>
 
</div>

</body>
</html>

