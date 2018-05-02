<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<script>
function delItemMe(url){
    if(confirm("确定作废此记录吗? ")){
    	location.href = url;
	}
  }

function qiyong(url){
    if(confirm("确定启用此记录吗? ")){
    	location.href = url;
	}
  }
function showDomain(type){
  $('.'+type).toggle();
}
</script>
<title>${auth.label}</title>
<%@ include file="/common/head.jsp" %>
<style>
.Factory-2{display: none;}
.Storage-2{display: none;}
.Pipeline-2{display: none;}
.Storage-3{display: none;}
</style>
</head>
<body>
 <form id="mainForm" action="domain.action" method="post">
<div id="fixtitle">
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>
  <div class="itemtitle"><h2>${auth.label}</h2></div>
 
	<input type="hidden" name="domainId" id="domainId" value="${domainId}"/>
	<input type="hidden" name="authId" id="authId" value="${authId}"/>
    <!-- 查询条件 -->

</div>
  </form>
 <!-- 内容区域 -->
<div class="container">
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div><security:authorize ifAnyGranted="A_sys_domain_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('domain!input.action',false,'${authId}');"/></security:authorize></div>
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv">
    <table class="tab_cont" width="100%"  cellspacing="0" cellpadding="0" border="0" align="left">
    <tbody>
		  <tr>
		      <th class="first">序号</th>
		      <th>域名称</th>
		      <th>简     称</th>
		      <th>别     名</th>
		      <th>域编码</th>
              <th>Auto编码</th>
              <th>省份</th>
		      <!-- <th>域路径</th> -->
              <th>共享</th>
              <th>域描述</th>
              <th>排列序号</th>
		      <th class="last">操作</th>
		    </tr>
            <!-- 剔除非domain类型的集成子类 -->
                        <c:forEach items="${entity.allChildrenAndUs}" var="c" varStatus="s">
                            <tr>
                                <td>${s.index+1}</td>
                                <td style="text-align: left; padding-left: 10px;">
                                ${c.label}
                                </td>
                                <td>${c.shortName}</td>
                                <td>${c.alias}</td>
                                <td>${c.code}</td>
                                <td><fmt:formatNumber value="${c.autoCode}" type="number" pattern="#,####"/></td>
                                <td style="text-align: left;"><wlps:dic dcode="monitor_province_info" icode="${c.location}"></wlps:dic>  </td>
                                <td style="text-align: left;">${c.sharing?'是':''}</td>
                                <td style="text-align: left;">${c.description}</td>
                                <td style="text-align: left;">${c.position}</td>
                                <td><security:authorize ifAnyGranted="A_sys_domain_add"><a href="domain!input.action?id=${c.id}&authId=${authId}"><img src="${ctx}/images/edt.gif" width="16" height="16" border="0" alt="修改" /></a></security:authorize>
                                <c:if test="${c.state!=false}"><security:authorize ifAnyGranted="A_sys_domain_delete"><img src="${ctx}/images/del.gif" width="16" height="16" style="cursor: pointer;" onclick="delItemMe('domain!deleteByBoolean.action?id=${c.id}&authId=${authId}');" alt="作废"  title="作废"/></security:authorize></c:if>
                                <c:if test="${c.state!=true}"><security:authorize ifAnyGranted="A_sys_domain_delete"><img src="${ctx}/images/ok.png" width="16" height="16" style="cursor: pointer;" onclick="qiyong('domain!changeStatus.action?id=${c.id}&authId=${authId}');" alt="启用" title="启用" /></security:authorize></c:if>
                                </td>
                            </tr>
                        </c:forEach>

    </tbody>
    </table>
    </div>
  </div>
  <!-- 分页区域-->
  
</div>
</body>
</html>