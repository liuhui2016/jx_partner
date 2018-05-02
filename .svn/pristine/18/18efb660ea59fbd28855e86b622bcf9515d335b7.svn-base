<%@page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${id==null?'新增合伙人':'修改合伙人' }</title>
<%@ include file="/common/head.jsp" %>
<link rel="stylesheet" href="${ctx}/css/content.css" type="text/css" />
<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
<link rel='STYLESHEET' type='text/css' href='${tree}/common/style.css' />
<link rel="STYLESHEET" type="text/css" href="${tree}/dhtmlxtree.css" />
<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript" src="${ctx}/js/lhgdialog/lhgdialog.js?t=self&s=zxyg"></script>

<script type="text/javascript" src="${tree}/dhtmlxcommon.js"></script>
<script type="text/javascript" src="${tree}/dhtmlxtree.js"></script>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>

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
			PAR_NAME: {
				required: true,
				maxlength:20
			},
			PAR_ADDRESS: {
				required: true,
				maxlength:100
			},
			PAR_PHONE: {
				digits: true
			}
		},submitHandler:function(form){
			var f = phoneChange();
			var flag = checkArea();   
			if(flag && f){form.submit();}
        }
	});
});
function checkArea(){
	var level = $('#PAR_LEVEL option:selected') .val();
	var province = $("#s_province option:selected").val();
	var city = $("#s_city option:selected").val();
	var county = $("#s_county option:selected").val();
	var flag = false;
	if(level == 1&&(province=="省份"||city!="地级市"||county!="市、县级市")){
		$("#areaId").text("省级合伙人只需要填写省份!");
	}else if(level == 2&&(!(province!="省份"&&city!="地级市"&&county=="市、县级市"))){
		$("#areaId").text("地市级合伙人需要填写到地市!");
	}else if((level == 3||level == 4)&&(!(province!="省份"&&city!="地级市"&&county!="市、县级市"))){
		$("#areaId").text("区县合伙人及产品经理需要填写到区县!");
	}else{
		$("#areaId").text(" ");
		flag = true;
	}
	return flag;
}

function clsGG(srcpath) {
	window.document.getElementById("viewimg").src = srcpath;
	document.all("gonggao").style.display = "block";
}
function closediv() {
	document.all("gonggao").style.display = "none";
}

//选择弹出框
function selectDlg(url, dialogTitle) {
	  	// url = "push-apk!selectapk.action";
// 	  	var s_province = Document.getelementbyid(s_province);
	  	var aa = $("#PAR_LEVEL").val();
	  	var province = $("#s_province option:selected").val();
		var city = $("#s_city option:selected").val();
		var county = $("#s_county option:selected").val();
// 	  	alert(aa);
// 	  	alert(s_province);
		url = prepURL(url, "authId", "${authId}");
		url = prepURL(url, "par_level", aa);
		url = prepURL(url, "province", province);
		url = prepURL(url, "city", city);
		url = prepURL(url, "county", county);
		var dg = new $.dialog({ id:'poupup', skin:'zxyg',rang:true,title:dialogTitle,fixed:true,
			drag:true, resize:true,cancelBtn:false,iconTitle:false,width:800,height:460,page:url,
			top:50,left:200,dgOnLoad:function(){}
		});
		dg.ShowDialog();
}
function setvalue() {
	$("#s_province option").each(function(){
		if($(this).val() == '${s_province}'){
			$(this).attr("selected", true);
			change(1);
		}
	});
	$("#s_city option").each(function(){
		if($(this).val() == '${s_city}'){
			$(this).attr("selected", true);
			change(2);
		}
	});
	$("#s_county option").each(function(){
		if($(this).val() == '${s_county}'){
			$(this).attr("selected", true);
		}
	});
}

function phoneChange(){
	var phon = /^0?(13[0-9]|15[012356789]|18[0-9]|17[0-9])[0-9]{8}$/;
	var value = $("#PAR_PHONE").val();
	var f = false;
	 if (!phon.test(value)) {
		 $("#phoneSpan").text("你输入的号码不正确或者为空");
	 }else{
		 f = true;
		 $("#phoneSpan").text(" "); 
	 }
	 return f;
}


</script>

</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增合伙人</span>':'&nbsp;»&nbsp;<span>修改合伙人</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增合伙人':'修改合伙人' }</h2></div>
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
  <form id="inputForm" action="partner!save.action" method="post" enctype="multipart/form-data" >
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
						<td class="right"><span class="red">*</span>合伙人姓名:</td>
						<td colspan="2">
							<input  type="text" name="PAR_NAME" size="40" id="PAR_NAME" maxlength="150px" value="${pAR_NAME}"/>
							<span id="parNameSpan"></span>
						</td>
					</tr>
					<tr>
						<td class="right"><span class="red">*</span>合伙人级别:</td>
						<td colspan="3">
								<select id="PAR_LEVEL" name="PAR_LEVEL">
									<option value='1' <c:if test='${PAR_LEVEL== "1"}'>selected='selected'</c:if>>省</option> 
									<option value='2' <c:if test='${PAR_LEVEL== "2"}'>selected='selected'</c:if>>市</option>
									<option value="3" <c:if test='${PAR_LEVEL== "3"}'>selected='selected'</c:if>>区</option>
									<option value="4" <c:if test='${PAR_LEVEL== "4"}'>selected='selected'</c:if>>产品经理</option>
								</select>
						</td>
					</tr>
					
					<tr>
						<td class="right"><span class="red">*</span>合伙人所在地区:</td>
						<td>
							<div>
							    <select id="s_province" name="s_province"></select>  
								<select id="s_city" name="s_city"></select>  
								<select id="s_county" name="s_county"></select>
								<script class="resources library" src="${ctx}/js/area.js" type="text/javascript"></script>
					   			 <span id="areaId" style="color:red"></span>
								<script type="text/javascript">_init_area();setvalue();</script>
					    	</div>
					    </td>
					</tr>
					
					<tr>
						<td class="right">上级合伙人:</td>
						<td colspan="3">
							<input type="text" id="PAR_PARENT" name="PAR_PARENT" value="${PAR_PARENT}" style="width: 100px" />
							<input type="button" onclick="selectDlg('partner!selectresourcer.action', '选择上级合伙人');" value="选择上级合伙人"/>
						</td>
					</tr>
					
					<tr>
						<td class="right">上级合伙人编号:</td>
						<td colspan="3">
							<input type="text" id="parParentid" name="parParentid" value="${parParentid}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right"><span class="red">*</span>详细地址:</td>
						<td colspan="3">
							<input type="text" id="PAR_ADDRESS" name="PAR_ADDRESS" value="${PAR_ADDRESS}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right"><span class="red">*</span>合伙人联系电话:</td>
						<td colspan="3">
							<input type="text" id="PAR_PHONE" name="PAR_PHONE" value="${PAR_PHONE}" onchange="phoneChange()" style="width: 320px" /><span id="phoneSpan" style="color:red"></span>
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
