<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>**管理</title>
<%@ include file="/common/head.jsp" %>
<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
  <script type="text/javascript">
  $(document).ready(function() {
		$("#filter_EQS_sex").attr("value","${param['filter_EQS_sex']}");
		$("#filter_EQB_enabled").attr("value","${param['filter_EQB_enabled']}");
		$('#filter_EQD_birthDate').datepicker({changeMonth: true,changeYear: true,showButtonPanel: true,yearRange:'1900:2020'});
	});
  </script>
</head>

<body>
<div class="container" style="margin: 0;padding: 0; width: 98%;">
  <!-- 内容区域 -->
  <!--<div class="itemtitle"><h2>${domain.label}</h2></div>-->
  
  <form id="mainForm" action="user.action" method="post">
  
	<input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
	<input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
	<input type="hidden" name="page.order" id="order" value="${page.order}"/>
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
	<input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
	<input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
  
  <!-- 查询条件 -->
  <div id="filter" class="filter">
    <div id="item" class="item">
    <div class="where">
    	<div class="and">
	      <span>登录名:</span><input type="text" class="small" name="filter_LIKES_username" value="${param.filter_LIKES_username}" />
    	</div>
    	<div class="and">
	      <span>姓名:</span><input type="text" class="small" name="filter_LIKES__realName" value="${param.filter_LIKES__realName}" />
    	</div>
    	<div class="and">
	      <span>帐号可用:</span><select class="small" name="filter_EQB_enabled" id="filter_EQB_enabled"><option value="">全部</option><option value="true">是</option><option value="false" >否</option></select>
    	</div>
    	<div class="and">
	      <span>Email:</span><input type="text" class="small" name="filter_LIKES_email" value="${param.filter_LIKES_email }" />
    	</div>
    </div>
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
    <div><security:authorize ifAnyGranted="A_sys_user_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('user!input.action',true,'${authId}');"/></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_user_edit"><img src="../images/b_edit.gif" alt="修改" onclick="opr_update('user!input.action','mainForm',true,'${authId}');" /></security:authorize></div>
    <div><security:authorize ifAnyGranted="A_sys_user_delete"><img src="../images/b_del.gif" alt="删除" onclick="opr_delete('user!delAll.action','mainForm');" /></security:authorize></div>
    <!-- <div><img src="../images/import.gif" alt="导入" onclick="importExcelInit('${nameSpace}${enityName}!load.action','${authId}',true);" /></div> -->
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
    <tr>
      <th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
      <th><a href="javascript:sort('username','asc')">登录名</a></th>
      <th><a href="javascript:sort('realName','asc')">姓名</a></th>
      <th>用户角色</th>
      <th><a href="javascript:sort('enabled','asc')">账户可用</a></th>
      <th><a href="javascript:sort('domain.id','asc')">所属公司</a></th>
      <th><a href="javascript:sort('createBy','asc')">创建者</a></th>
      <th><a href="javascript:sort('createTime','asc')">创建时间</a></th>
      <th><a href="javascript:sort('email','asc')">电子邮箱</a></th>
      <th><a href="javascript:sort('accountLocked','asc')">帐号锁定</a></th>
      <th><a href="javascript:sort('accountExpired','asc')">帐号过期</a></th>
      <th><a href="javascript:sort('credentialsExpired','asc')">证书过期</a></th>
      <th class="last">支配角色</th>
    </tr>
    
    <s:iterator value="page.result">
    	<tr>
    		<td class="first"><input type="checkbox" value="${id}" id="id_${id}" name="ids"/></td>
    		<td nowrap="nowrap"><a href="user!detail.action?id=${id}&authId=${authId}" target="_parent">${username}</a></td>
            <td nowrap="nowrap">${realName}</td>
            <td nowrap="nowrap">${useRoleNames}</td>  
            <td nowrap="nowrap">${enabled?"是":"<del>不可用</del>"}</td>  
            <td nowrap="nowrap">${domain.label}</td>
			<td nowrap="nowrap">${createBy}</td>
			<td nowrap="nowrap"><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
            <td nowrap="nowrap">${email}</td>
			<td nowrap="nowrap">${accountLocked?"已锁":"未锁"}</td>  
			<td nowrap="nowrap">${accountExpired?"已过期":"有效"}</td>  
			<td nowrap="nowrap">${credentialsExpired?"已失效":"有效"}</td>  
			<td nowrap="nowrap">${grantRoleNames}</td>
    	</tr>
    </s:iterator>

    </tbody>
    </table>
    </div>
  </div>
  </form>
  
  <!-- 分页区域-->
  <div id="page" class="page">
  <wlps:page page="${page}" showPageSize="true" excelExp="false" /></div>
        

  
</div>

</body>
</html>