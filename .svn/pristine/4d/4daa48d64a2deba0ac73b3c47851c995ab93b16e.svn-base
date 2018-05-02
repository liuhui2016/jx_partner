<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${id==null?'新增滤芯寿命':'修改滤芯寿命' }</title>
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
					code: {
						required: true
					},
					pp: {
						required: true,
						digits: true
					},
					cto: {
						required: true,
						digits: true
					},
					ro: {
						required: true,
						digits: true
					},
					t33: {
						required: true,
						digits: true
					},
					wfr: {
						required: true,
						digits: true
					},
				},submitHandler:function(form){
					var f = codeChange();
					if(f){form.submit();}
		        }
			});
		});
		
		function clsGG(srcpath) {
			window.document.getElementById("viewimg").src = srcpath;
			document.all("gonggao").style.display = "block";
		}
		function closediv() {
			document.all("gonggao").style.display = "none";
		}
		
		function codeChange(){
			var f = true;
			var code = $("#code").val();
			var Json=JSON.stringify({"codeValue":code});
			$.ajax({ //一个Ajax过程
		       type: "post", //以post方式与后台沟通
		       url:'${pageContext.request.contextPath}/smvc/codes/codeA.v',
		       contentType:'application/json;charset=utf-8;',
		       data: Json,
		       async: false,  
		       success: function(data){
		      		if(data > 0){
		      			$("#spanId").text("你输入的地区已经存在");
		      			f = false;
		      		}else{
		      			$("#spanId").text(" ");
		      			f = true;
		      		}
		       }
		   });
			return f;
		}
	</script>
	
	
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增滤芯寿命</span>':'&nbsp;»&nbsp;<span>修改滤芯寿命</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增滤芯寿命':'修改滤芯寿命' }</h2></div>
    
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="filter-life!save.action" method="post" enctype="multipart/form-data">
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
						<td class="right"><span class="red">*</span>地区:</td>
						<td colspan="3"><input type="text" name="code" size="40" id="code" maxlength="255" onchange="codeChange()" value="${code}" /> <span id="spanId" style="color:red"></span></td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>pp滤芯使用使用时长(小时):</td>
						<td colspan="3"><input type="text" name="pp" size="40" id="pp" maxlength="255" value="${pp}"/></td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>cto活性炭滤芯时长(小时):</td>
						<td colspan="3"><input type="text" name="cto" size="40" id="cto" maxlength="255" value="${cto}"/></td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>ro膜滤芯使用时长(小时):</td>
						<td colspan="3"><input type="text" name="ro" size="40" id="ro" maxlength="255" value="${ro}"/></td>
					</tr>
					
					<%-- <tr>
						<td class="right"><span class="red">*</span>t33活性保鲜滤芯使用时长(小时):</td>
						<td colspan="3"><input type="text" name="t33" size="40" id="t33" maxlength="255" value="${t33}"/></td>
					</tr> --%>
					
					<tr>
						<td class="right"><span class="red">*</span>复合能量矿化滤芯(小时):</td>
						<td colspan="3"><input type="text" name="wfr" size="40" id="wfr" maxlength="255" value="${wfr}"/></td>
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
