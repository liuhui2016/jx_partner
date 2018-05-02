<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增图片':'修改图片' }</title>
	<%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
	<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
	<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
	<style>
			.gonggao{
			    position:absolute;
			    top:80px;
			    left:200px;
			    width:330px;
			    height:500px;
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
$(document).ready(function() {
	//为inputForm注册validate函数
	$("#inputForm").validate({
		rules: {
			protype_id: {
				digits: true
			},
			pic_color: {
				required: true
			},
			pic_name: {
				required: true
			},
			iconfile: {
				accept: "jpg|bmp|gif|png|jpeg|JPG|BMP|PNG|GIF|JPEG"
			},
		},
	});
});
		
		function clsGG(srcpath) {
			//window.document.getElementById("viewimg").src= '${ctx}/showImage?imgfile='+srcpath;
			window.document.getElementById("viewimg").src = srcpath;
			document.all("gonggao").style.display = "block";
			// document.all("gonggao").style.display=(document.all("gonggao").style.display=="none")?"block":"none";
			//jQuery("#gonggao").toggle();        
		}
		function closediv() {
			document.all("gonggao").style.display = "none";
		}
	</script>
	
	
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增图片</span>':'&nbsp;»&nbsp;<span>修改图片</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增图片':'修改图片' }</h2></div>
    
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="picture!save.action" method="post" enctype="multipart/form-data">
  	<div id="gonggao" class="gonggao" style="display: none">
				<img name="viewimg" id="viewimg" style="width: 300; height: 500" />
				<a href="javascript:closediv();">关闭</a>
			</div>
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
						<td class="right">产品类别图片id:</td>
						<td>
							<select id="protype_id" name="protype_id">
									<option value='1' <c:if test='${protype_id== "1"}'>selected='selected'</c:if>>壁挂净水器</option> 
									<option value='2' <c:if test='${protype_id== "2"}'>selected='selected'</c:if>>台式净水器</option>
									<option value="3" <c:if test='${protype_id== "3"}'>selected='selected'</c:if>>立式净水器</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td class="right">图片颜色:</td>
						<td colspan="3"><input type="text" name="pic_color" size="40" id="pic_color" maxlength="255" value="${pic_color}"/></td>
					</tr>
					
					<tr>
						<td class="right">颜色色值:</td>
						<td colspan="3"><input type="text" name="pic_tone" size="40" id="pic_tone" maxlength="255" value="${pic_tone}"/></td>
					</tr>
					
					<tr>
						<td class="right">图片名:</td>
						<td colspan="3"><input type="text" name="pic_name" size="40" id="pic_name" maxlength="255" value="${pic_name}"/></td>
					</tr>
					
					<tr>
						<td class="right">图片url:</td>
						<td colspan="3">
							<input type="file" id="iconfile" name="iconfile" value="${pic_url}" style="width: 320px" />
							<input type="hidden" id="pic_url" name="pic_url" value="${pic_url}" />
							<c:if test="${pic_url != null && pic_url != ''}">
								&nbsp;&nbsp;<a href="#" id="icondelete" onclick="clsGG('${pic_url}');">查看原图</a>
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