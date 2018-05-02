<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${id==null?'新增明细':'修改明细' }</title>
<%@ include file="/common/head.jsp" %>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
<style>
.gonggao{
			    position:absolute;
			    top:30px;
			    left:700px;
			    width:100px;
			    height:100px;
			    border:1px solid #993300;
			    background-color:#ffffff;
			}
			.gonggao h1{
			    line-height:50px;
			    font-size:24px;
			    font-family:"黑体";
			    font-weight:bold;
			    text-align:center;
			}
			.gonggao div{
			    line-height:30px;
			    font-size:18px;
			    font-family:"黑体";
			    text-align:center;
			    color:#993300;
			}
			.gonggao div a,.gonggao div a:visited{
			    color:#993300;
			}
</style>

<script>
function clsGG(srcpath) {
	//alert(srcpath);
	window.document.getElementById("viewimg").src = srcpath;
	document.all("gonggao").style.display = "block";
}
function closediv() {
	document.all("gonggao").style.display = "none";
}

$(document).ready(function() {
	//为inputForm注册validate函数
	$("#gegege").validate({
		rules: {
			color: {
				required: true,
			},
			prod_name: {
				required: true,
			},
		},submitHandler:function(form){
			var f = toneJudge();
			if(f){form.submit();}
        }
	});
});

function toneJudge(){
	var f = false;
	var pattern = /^#[0-9a-fA-F]{6}$/;
	var tone = $("#tone").val();
	if(!pattern.test(tone)){
		$("#toneId").text("你输入的颜色色值格式不正确或者为空");	
	}else{
		$("#toneId").text("");
		f = true;
	}
	return f;
}

</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增明细</span>':'&nbsp;»&nbsp;<span>修改明细</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增明细':'修改明细' }</h2></div>
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="gegege" action="prodetail!save.action" method="post" enctype="multipart/form-data" >
	  <div id="gonggao" class="gonggao" style="display: none">
				<img name="viewimg" id="viewimg" style="width: 50; height: 50" />
				<a href="javascript:closediv();">关闭</a>
			</div>
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
					<input type="hidden" id="prot_id" name ="prot_id" value="${prot_id}"></input>
					<tr>
						<td class="right"><span class="red">*</span>产品颜色 :</td>
						<td colspan="3">
							<input type="text" id="color" name="color" value="${color}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>颜色色值 :</td>
						<td colspan="3">
							<input type="text" id="tone" name="tone" value="${tone}" style="width: 320px" onchange="toneJudge()"/><span id="toneId" style="color:red"></span>
						</td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>明细产品名称:</td>
						<td colspan="3">
							<input type="text" id="prod_name" name="prod_name" value="${prodetail.prod_name}" style="width: 320px" />
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="right">明细产品图片id:</td> -->
<!-- 						<td colspan="3"> -->
							<input type="hidden" id="prod_picid" name="prod_picid" value="${prodetail.prod_picid}" style="width: 320px" />
<!-- 						</td> -->
<!-- 					</tr> -->
					<tr>
						<td class="right">图片url:</td>
						<td colspan="3">
							<input type="file" id="iconfile" name="iconfile" value="${pic_urls}" style="width: 320px" />
							<input type="hidden" id="pic_urls" name="pic_urls" value="${pic_urls}" />
							<c:if test="${pic_urls != null && pic_urls != ''}">
								&nbsp;&nbsp;<a href="#" id="icondelete" onclick="clsGG('${pic_urls}');">查看原图</a>
							</c:if>
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