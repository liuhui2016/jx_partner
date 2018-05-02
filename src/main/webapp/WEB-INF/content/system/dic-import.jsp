<%@ page contentType="text/html;charset=UTF-8" %>

<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${auth.label}</title>
	<%@ include file="/common/head.jsp" %>
	<style type="text/css">
	#importexcel{
		width:99%;
		margin:10px 10px;
	}
	#importexcel td{
		padding:3px;
	}
	li{
		list-style: none;
	}
	</style>
	<script type="text/javascript">
	$(function(){
		var sum ='${fileSize}';
		if(sum!=""){
			jConfirm("导入成功："+sum+"条记录。<br/>是否要跳转页面？","中国石油物流管理系统",function(result){
				if(result){
					loadBack('${nameSpace}${actionPath}.action','${authId}');
				}
			});
		}
		var errormsg = $(".actionMessage").html();
		if(errormsg!="" && errormsg!=null){
			jError(errormsg,"中国石油物流管理系统");
		}
	});
	function downLoad(date, url){
		gotoPost({contentPath : date},'${ctx}/static-content?download=true');
	}
	function loadBack(url,authId){
		var url = ctx+url+"?authId="+authId;
		location.href=url;
	}
	</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>数据导入</span></p>
  <div class="sitemap">
    <span style="display:block;float:left"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>数据导入</h2></div>
  <form action="${actionPath}!imporExcel.action?authId=${authId}" method="post" id="exportForm" enctype="multipart/form-data">
  <!-- 列表区域-->
  <div style="display: none"><s:actionmessage/></div>
  
  <table id="importexcel" class="tab_cont"  cellpadding="0" cellspacing="0">
	  <tr>
		  
		  <th class="first"><h3>
		  	支持Excel电子表格的导入，列头格式及基本操作如下：
		  	  </h3></th>
		
		  <th class="last"></th>
	  </tr>
		  <tr>
		  <td width="20%" class="right">格&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式：</td>
		  <td class="left">编码    名称    冗余字段1	冗余字段2	冗余字段3	冗余字段4   描述</td>
	      </tr>
	  <tr>
		  <td class="right">选择文件：</td>
		  <td>
		  	<input class="button" type="file" id="file" name="file"/>
		  	<input type="hidden" name="id" value="${id }"/>
		  </td>
	  </tr>
	  <tr>
		  <td></td>
		  <td class="nol">
		  	  <input class="button" type="submit" value="提交"/>
			  <input class="button" type="button" value="返回" onclick="history.back()" />&nbsp;&nbsp;&nbsp;
			  <a href="javascript: downLoad('/resources/exceFile/编码对照.xls','${ctx}/static-content?download=true')">模板文件下载</a>
		  </td>
	  </tr>
  </table>
  </form>
  </div>
</body>
</html>
