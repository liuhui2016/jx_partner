<%@page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>内部办公自动化系统</title>
<%@ include file="/common/head.jsp" %>
<link rel="stylesheet" href="${ctx}/css/content.css" type="text/css" />

<style type="text/css">
a {font-size:16px} 
#未访问：蓝色、无下划线 
a:link {
	color: green; 
	text-decoration:none;
} 
#激活：红色 
a:active:{color: red; } 
#已访问：purple、无下划线 
a:visited {
	color:green;
	text-decoration:none;
}
a:hover {
	color: red; 
	text-decoration:underline;
} 
.currloca {
    width: 1500px;
}
.titt {
    width: 1500px;
}
.filter {
    width: 1500px;
}
.mainC {
    width: 1500px;
}
.page {
    width: 1500px;
}
</style>

<script type="text/javascript">
$(document).ready(function () {
	//$("#status").val("${param.filter_EQI_status}");
});
function changeStatus(userId,status){
	//alert(userId+'-'+status);
	if(confirm("确定操作吗?")){
		$.ajax({
			type: "GET",
			url: "mobile-user!freezeUser.action",
			data: "userId="+userId+"&status="+status,
			success: function(msg){
			  alert( msg );
			  $('#mainForm').submit();
			}
		});
	}
}
</script>

</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="24" align="absmiddle" height="24" title="添加到常用操作" src="${ctx}/images/favorite.png"/></span>
  </div>
</div>
   <div class="titt">
     <h2><img alt="" src="${ctx}/images/web/311.gif" align="absmiddle" />${auth.label}</h2>
   </div>
   <div class="mainC">
	<!-- 增删改查...操作菜单-->
	<div id="operate" class="operate">
	  <div><img src="${ctx}/images/b_add.gif" alt="增加" onclick="opr_input('mobile-user!input.action',false,'${authId}');"/></div>
	  <%-- <div><img src="${ctx}/images/b_edit.gif" alt="修改" onclick="opr_update('share!input.action','mainForm',false,'${authId}');" /></div> --%>
	  <%-- <div><img src="${ctx}/images/b_del.gif" alt="删除" onclick="opr_delete('apk!delAll.action','mainForm');" /></div> --%>
	</div>
   <form id="mainForm" action="mobile-user!list.action" method="post">
    <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${page.order}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
   <!-- 查询条件 -->
   <div id="filter" class="filter">
    <div id="item" class="item">
    <div class="where">
        <div class="and">
           <span>用户名:</span>
           <input type="text" class="small" name="filter_LIKES_username" value="${param['filter_LIKES_phonenum']}" />
        </div>
        <div class="and">
           <span>用户昵称:</span>
           <input type="text" class="small" name="filter_LIKES_nickname" value="${param['filter_LIKES_nickname']}" />
        </div>
    </div>

      <div class="space"></div>
      <div style="text-align: center;">
      <img src="${ctx}/images/b_select.gif" alt="" onclick="search();" class="pointer" align="middle"/>
      <img src="${ctx}/images/b_reset.gif" alt="" onclick="resetb();" class="pointer"align="middle"/>
      </div>
    </div>
    <div id="contral" class="contral pointer" onclick="contral(this);"><img src="${ctx}/images/f_close.gif" title="收起查询面板"/></div>
   </div>
  
  
   <div class="mainC">
    <!-- start--------------------------------------- -->
      <table class="mtab" cellpadding="2" cellspacing="1" border="0">
			<tr align="center">
				<th class="first"><input type="checkbox" id="box" name="box" onclick="checkedAll('ids')"/></th>
				<th>手机号</th>
				<th>用户名</th>
				<th>用户昵称</th>
				<th>学校</th>
				<th>专业</th>
				<th>邮箱</th>
				<th>用户头像</th>
				<th>性别</th>
				<th>出生日期</th>
				<th>注册时间</th>
				<!-- <th>用户状态</th>
				<th>操作</th> -->
			</tr> 
<!--  user_id,username,password,nickname,user_img,user_sex,user_birth,create_time,login_time,status  -->
			<c:forEach items="${page.result}" var="a" varStatus="c">
				<tr align="center">
					<td><input type="checkbox" value="${a.userId}" id="id_${a.userId}" name="ids"/></td>
					<td>${a.phonenum}</td>
					<td>${a.username}</td>
					<td>${a.nickname}</td>
					<td>${a.school}</td>
					<td>${a.major}</td>
					<td>${a.email}</td>
					<td>${a.userImg}</td>
					<td>
						<c:if test="${a.userSex == 0}">女</c:if>
						<c:if test="${a.userSex == 1}">男</c:if>
					</td>
					<td><fmt:formatDate value="${a.userBirth}" pattern="yyyy-MM-dd" /></td>
					<td><fmt:formatDate value="${a.createTime}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
					<%-- <td>
						<c:if test="${a.status == 0}"><span style="color:green;">启用</span></c:if>
						<c:if test="${a.status == 1}"><span style="color:red;">冻结</span></c:if>
					</td>
					<td>
						<c:if test="${a.status == 0}">
							<a id="freeze" href="#" onclick="changeStatus(${a.userId},1);">
								<span>冻结账号</span>
							</a>
						</c:if>
						<c:if test="${a.status == 1}">
							<a id="open" href="#"  onclick="changeStatus(${a.userId},0);">
								<span>启用账号</span>
							</a>
						</c:if>
					</td> --%>
				</tr>
			</c:forEach>
		</table>
		</div> 
	</div>
</form>
<!-- 分页区域-->
<div id="page" class="page">
<wlps:page page="${page}" showPageSize="true" excelExp="false" /></div>
<!-- end--------------------------------------- -->

</body>
</html>
