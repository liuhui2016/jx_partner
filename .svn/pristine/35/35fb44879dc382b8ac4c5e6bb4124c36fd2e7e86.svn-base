<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>${auth.label}</title>
	<%@ include file="/common/head.jsp" %>
	<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script type="text/javascript">
	$(document).ready(function() {
	    $("#filter_GTD_createTime").datepicker($.datepicker.regional['zh-CN']);
	    $("#filter_LTD_createTime").datepicker($.datepicker.regional['zh-CN']);
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
  
  <form id="mainForm" action="role.action" method="post">
  	<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
	<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
	<input type="hidden" name="page.order" id="order" value="${page.order}"/>
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
	
  <!-- 查询条件 -->
  <div id="filter" class="filter">
    <div id="item" class="item and">
      <span>角色名:</span><input class="small" type="text" name="filter_LIKES_name" value="${param['filter_LIKES_name']}" size="9"/>
      <span>创建人:</span><input type="text" class="small" name="filter_LIKES_createBy" value="${param['filter_LIKES_createBy']}" size="9"/>
      <span>创建时间:</span><input type="text" class="small" id="filter_GTD_createTime" name="filter_GTD_createTime" value="${param['filter_GTD_createTime']}" size="9" readonly="readonly"/> ~
      <input type="text" id="filter_LTD_createTime"  class="small" name="filter_LTD_createTime" value="${param['filter_LTD_createTime']}" size="9" readonly="readonly"/>
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
    <div><security:authorize ifAnyGranted="A_sys_role_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('role!input.action',false,'${authId}');"/></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_role_add"><img src="../images/b_edit.gif" alt="修改" onclick="opr_update('role!input.action','mainForm',false,'${authId}');" /></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_role_delete"><img src="../images/b_del.gif" alt="删除" onclick="opr_delete('role!delAll.action','mainForm');" /></security:authorize></div>
   <!-- <div><img src="../images/import.gif" alt="导入" onclick="importExcelInit('${nameSpace}${enityName}!load.action','${authId}');" /></div> -->
  </div>
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
		  <tr>
		      <th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
		      <th><a href="javascript:sort('name','asc')">角名称</a></th>
              <th><a href="javascript:sort('code','asc')">编码</a></th>
              <th><a href="javascript:sort('createTime','asc')">创建时间</a></th>
              <th><a href="javascript:sort('createBy','asc')">创建人</a></th>
              <th><a href="javascript:sort('lastModifyTime','asc')">修改时间</a></th>
              <th><a href="javascript:sort('lastModifyBy','asc')">修改人</a></th>
		      <th class="last"><a href="javascript:sort('description','asc')">描述</a></th>
		    </tr>

			<s:iterator value="page.result">
				<tr>
					<td><input type="checkbox" value="${id}" id="id_${id}" name="ids"/></td>
					<td><a href="role!detail.action?id=${id}&authId=${authId}">${name}</a></td>
                    <td>${code}</td>
                    <td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd"/></td>
                    <td>${createBy}</td>
                    <td><fmt:formatDate value="${lastModifyTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                    <td>${lastModifyBy}</td>
					<td>${description}</td>
				</tr>
			</s:iterator>

    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  	<wlps:page page="${page}" showPageSize="true" excelExp="true" />
  </div>  
  
</div>
</body>
</html>
