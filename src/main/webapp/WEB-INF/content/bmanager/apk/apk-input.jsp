<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${id==null?'新增版本':'修改版本' }</title>
<%@ include file="/common/head.jsp" %>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
<style>
.gonggao{
    position:absolute;
    top:60px;
    left:600px;
    width:500px;
    height:480px;
    border:1px solid #993300;
    background-color:#ffffff;
}
.gonggao h1{
    line-height:50px;
    /* font-size:24px; */
    font-family:"黑体";
    font-weight:bold;
    text-align:center;
}
.gonggao div{
    /* line-height:30px; */
   	/*font-size:14px;*/
    /* font-family:"黑体"; */
    text-align:center;
    /* color:#993300; */
}
.gonggao div a,.gonggao div a:visited{
    color:#993300;
}
</style>

<script>
$(document).ready(function() {
	//为inputForm注册validate函数
	$("#inputForm").validate({
		rules: {
			apk: {
				required: true,
				accept: "apk|APK|IPA|ipa|plist|PLIST"
			}
		}
	});
});
function clsGG(srcpath) {
	//alert(srcpath);
	window.document.getElementById("viewimg").src = srcpath;
	document.all("gonggao").style.display = "block";
}
function closediv() {
	document.all("gonggao").style.display = "none";
}

function typeChange(){
	var type = $("#type").val();
	if(type == '2'){
		$("#apkversion").show();
		$("#apkname").show();
	}else{
		$("#apkversion").css("display","none");
		$("#apkname").css("display","none");
	}
}

</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增版本</span>':'&nbsp;»&nbsp;<span>修改版本</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增版本':'修改版本' }</h2></div>
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
<!-- 查看原图  -->
<div id="gonggao" class="gonggao" style="display: none">
	<div style="text-align: center;">
		<img name="viewimg" id="viewimg" width="480" height="430" />
	</div>
	<div style="height: 15px;"></div>
	<div style="text-align: center;">
		<a href="javascript:closediv();"><font style="font-size:18px;">关闭</font></a>
	</div>
</div>  

  <form id="inputForm" action="apk!save.action" method="post" enctype="multipart/form-data" >
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    </div>
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" colspan="3"></th>
				    </tr>
				    <tr>
						<td class="right">平台:</td>
						<td colspan="3">
								<select id="type" name="type" onchange="typeChange()">
									<option value="0">平板</option>
									<option value="1">安卓</option>
									<option value="2">苹果</option>
								</select>
						</td>
					</tr>
				    <tr>
						<td class="right">APK摘要:</td>
						<td colspan="3"><input type="text" name="apkDigest" size="40" id="apkDigest" maxlength="255" /></td>
					</tr>
					 <tr id="apkversion" style="display: none">
						<td class="right">版本号:</td>
						<td colspan="3"><input type="text" name="apkVersion" size="40" id="apkVersion" maxlength="255" /></td>
					</tr>
					 <tr id="apkname"  style="display: none">
						<td class="right">版本名称:</td>
						<td colspan="3"><input type="text" name="systemVersion" size="40" id="systemVersion" maxlength="255" /></td>
					</tr>
					<tr>
						<td class="right"><span class="red">*</span>上传APK:</td>
						<td colspan="3">
							<input type="file" id="apk" name="apk" value="" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">是否强制升级:</td>
						<td colspan="3">
								<select id="mustupgrade" name="mustupgrade">
									<option value="0">否</option>
									<option value="1">是</option>
								</select>
						</td>
					</tr>
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="3">
							<input class="button" type="submit" value="提交"/>&nbsp;
							<input class="button" type="button" value="返回" onclick="history.back()"/>
						</th>
					</tr>
				</tbody>
			</table>
	    </div>
  </form>
</div>
</body>
</html>
