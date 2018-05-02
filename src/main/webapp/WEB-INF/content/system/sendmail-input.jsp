<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>管理</title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>	
	<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
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
  <div class="itemtitle"><h2><span>Email队列</span></h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;">发送失败的Email条数：${errorSize}</span>
  </div>
  <form id="inputForm" action="sendmail!sendErrorMail.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
			<table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
		        <tr>
				  <th class="first"><b>主题</b></th>
				  <th><b>接收人</b></th>
				  <th width="100" class="last"><b>操作</b></th>
		        </tr>
		        </tbody>
				<tbody>
				<c:forEach items="${lmm}" var="u">
				    <tr>
				      	<td>${u.subject}</td>
          				<td colspan="2"><c:forEach items="${u.allRecipients}" var="s">${s}  </c:forEach> </td>
          				<!--td><a href="sendmail!sendErrorMailOne.action?authId=${authId}&mim=${u}">重新发送</a></td-->
				    </tr>
				</c:forEach>	
				</tbody>			   
				<tbody>
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="2">
							<input class="button" type="submit" value="重新发送"/>&nbsp;
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
