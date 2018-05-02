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
			jConfirm("导入成功："+sum+"条记录。<br/>是否要跳转页面？","产品统计系统",function(result){
				if(result){
					if ('${importModel}'=='1') {
						loadBack('${nameSpace}${extActionName}.action','${authId}');
					} else {
						loadBack('${nameSpace}${actionPath}.action','${authId}');
					}
				}
			});
		}
		var errormsg = $(".actionMessage").html();
		if(errormsg!="" && errormsg!=null){
			jError(errormsg,"产品统计系统");
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
  <table>
		<tr>
			<td><span><img src="${ctx}/images/home.png" alt="" />&nbsp;&nbsp; ${auth.fullMenu}</span></td>
		</tr>
	</table>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>数据导入</h2></div>
  <c:if test="${importModel=='1'}">
  	<form action="${extActionName}!importExcel.action?authId=${authId}" method="post" id="exportForm" enctype="multipart/form-data">
  </c:if>
  
  <c:if test="${importModel!='1'}">
	  <form action="${actionPath}!importExcel.action?authId=${authId}" method="post" id="exportForm" enctype="multipart/form-data">
  </c:if>
  <!-- 列表区域-->
  <div style="display: none"><s:actionmessage/></div>
  
  <table id="importexcel" class="tab_cont"  cellpadding="0" cellspacing="0">
	  <tr>
		  
		  <th class="first" width="10%"><h3>
		  	支持Excel电子表格的导入：
		  	  </h3></th>
		
		  <th class="last"></th>
	  </tr>
		  <tr>
		  <td width="20%" class="right">格&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;式：</td>
		  <td class="left">${poiRealName}</td>
	      </tr>
	 
	  <tr>
		  <td class="right">选择文件：</td>
		  <td><input class="button" type="file" id="file" name="file"/></td>
	  </tr>
	  <tr>
		  <td></td>
		  <td class="nol">
		  	<c:if test="${importModel=='1'}">
		  	  <input class="button" id="exportBtn" type="button" value="导入" onclick="importExcel('${nameSpace}${extActionName}!importExcel.action','${authId}')"/>
			  <input class="button" type="button" value="返回" onclick="loadBack('${nameSpace}${extActionName}.action','${authId}');" />&nbsp;&nbsp;&nbsp;
			  <a href="javascript: downLoad('/resources/exceFile/busi/${downLoadName}.xls','${ctx}/static-content?download=true')">模板文件下载</a>
		  	</c:if>
		  	<c:if test="${importModel!='1'}">
		  		<input class="button" id="exportBtn" type="button" value="导入" onclick="importExcel('${nameSpace}${actionPath}!importExcel.action','${authId}')"/>
			  <input class="button" type="button" value="返回" onclick="loadBack('${nameSpace}${actionPath}.action','${authId}');" />&nbsp;&nbsp;&nbsp;
			  <a href="javascript: downLoad('/resources/exceFile/busi/${downLoadName}.xls','${ctx}/static-content?download=true')">模板文件下载</a>
		  	</c:if>
		  </td>
	  </tr>
  </table>
  </form>
  </div>
</body>
</html>
