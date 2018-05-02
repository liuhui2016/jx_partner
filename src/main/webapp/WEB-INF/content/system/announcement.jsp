<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ page import="com.game.modules.utils.SpringContextHolder" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>公告列表</title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
		$("#filter_EQI_type").attr("value","${param['filter_EQI_type']}");
		var dates = $('#filter_start,#filter_end').datepicker({
			changeMonth: true,
			numberOfMonths: 1,
			prevText:'前一月', 
		    nextText:'后一月', 
		    yearSuffix: '',  
		    showButtonPanel: true,
			onSelect: function(selectedDate) {
				var option = this.id == "filter_start" ? "minDate" : "maxDate";
				var instance = $(this).data("datepicker");
				var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
				dates.not(this).datepicker("option", option, date);
			}
		});
	    
	});
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
  
  <form id="mainForm" action="announcement.action" method="post">
  <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
	<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
	<input type="hidden" name="page.order" id="order" value="${page.order}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
	<input type="hidden" name="page.excelStr" id="excelStr" value="id-序号-S,title-名称-S,start-开始时间-D,end-结束时间-D,create-创建时间-D,createUser-创建人-S,type-类型-S"/>
	
	  <!-- 查询条件 -->
	  <div id="filter" class="filter">
	    <div id="item" class="item and">
	      <span>名称:</span><input type="text" class="small" name="filter_LIKES_title" value="${param.filter_LIKES_title }" size="9"/>
	      <span>开始时间:</span><input type="text" readonly="readonly" class="small" name="filter_GED_start" id="filter_start" value="${param.filter_GED_start }" size="9"/>
	      <span>结束时间:</span><input type="text" readonly="readonly" class="small" name="filter_LED_end" id="filter_end" value="${param.filter_LED_end }" size="9"/>
	      <span>创建人:</span><input type="text" class="small" name="filter_LIKES_createBy" id="filter_LIKES_createBy" value="${param.filter_LIKES_createBy }" size="9"/>
	      <span>类型:</span>
	      	<select name="filter_EQI_type" id="filter_EQI_type">
	      		<option value="">全部</option>
	      		<s:iterator value="annoTypeList">
	      			<option value="${value}">${label}</option>
	      		</s:iterator>
	      	</select>
	      <div class="space"></div>
	      <div style="text-align: center;">
	      <img src="../images/b_select.gif" alt="" onclick="search();" class="pointer" align="middle"/>
	      <img src="../images/b_reset.gif" alt="" onclick="resetb();" class="pointer"align="middle"/>
	      </div>
	    </div>
	    <div id="contral" class="contral pointer" onclick="contral(this);"><img src="../images/f_close.gif" title="收起查询面板"/></div>
	  </div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div><security:authorize ifAnyGranted="A_sys_announcement_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('announcement!input.action',false,'${authId}');"/></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_announcement_edit"><img src="../images/b_edit.gif" alt="修改" onclick="opr_update('announcement!input.action','mainForm',false,'${authId}');" /></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_announcement_delete"><img src="../images/b_del.gif" alt="删除" onclick="opr_delete('announcement!delAll.action','mainForm');" /></security:authorize></div>
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
		  <tr>
		      <th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
		      <th><a href="javascript:sort('id','asc')">序号</a></th>
		      <th>名称</th>
		      <th><a href="javascript:sort('start','asc')">开始时间</a></th>
		      <th><a href="javascript:sort('end','asc')">结束时间</a></th>
		      <th><a href="javascript:sort('createTime','asc')">创建时间</a></th>
		      <th><a href="javascript:sort('createBy','asc')">创建人</a></th>
              <th><a href="javascript:sort('lastModifyTime','asc')">最后更新时间</a></th>
              <th>更新人</th>
		      <th><a href="javascript:sort('type','asc')">类型</a></th>
		      <th class="last">状态</th>
		    </tr>
			
			<s:iterator value="page.result">
				<tr>
					<td><input type="checkbox" value="${id}" id="id_${id}" name="ids"/></td>
					<td height="22" >${id}</td>
					<td><a href="${ctx}/system/announcement!detail.action?id=${id}&authId=${authId}">${title}</a></td>
					<td><fmt:formatDate value="${start}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${end}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${createBy}</td>
                    <td><fmt:formatDate value="${lastModifyTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${lastModifyBy}</td>
					<td>
						<s:iterator value="annoTypeList" id="annoTypevalue">
							<s:if test="#annoTypevalue.value == type">
								<s:property value="#annoTypevalue.label"/>
							</s:if>
						</s:iterator>
					</td>
					<td>
						<s:if test="state==true">有效</s:if>
						<s:if test="state==false">结束</s:if>
					</td>
				</tr>
			</s:iterator> 
    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  	<wlps:page page="${page}" showPageSize="true" excelExp="false" />
  </div>  
  
</div>
</body>
</html>