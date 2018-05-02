<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${auth.label}</title>
	<%@ include file="/common/head.jsp" %>
	
  <script type="text/javascript">

  //自动设置iframe的高度
  function reinitIframe(){
		var iframe = document.getElementById("userRightIFrame");
		var iframe_2 = document.getElementById("userLeftIFrame");
		try{
			var bHeight = iframe.contentWindow.document.body.scrollHeight;
			var dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
			var height = Math.min(bHeight, dHeight)+40;
			iframe.style.height =  height+"px";
			iframe_2.style.height = iframe.style.height;
		}catch (ex){}
	}
</script> 	
</head>

<body>

<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${auth.label}</h2></div>

  
  <!-- 列表区域-->
  <div>
    <div id="indiv" style="width:100%;overflow: hidden;">
    <table class="tab_cont_f" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
    	<tr>
          <td>
			<div id="userRightDIV" style="float:right;width:100%">
				<iframe id="userRightIFrame" onload="reinitIframe();" name="userRightIFrame" src="${ctx}/account/user.action?domainId=-1&authId=${authId}" scrolling="auto"  marginwidth="0" marginheight="0" frameborder="0" style="width:100%"></iframe>
			</div>
          </td>
        </tr>
    </tbody>
    </table>
    </div>
  </div>
</div>
</body>
</html>