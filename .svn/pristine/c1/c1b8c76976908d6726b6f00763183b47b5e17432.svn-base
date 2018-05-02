<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% request.setAttribute("now",new java.util.Date()); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>welcome</title>
    <%@ include file="/common/head.jsp" %>
    <script>
        var OA_TIME = new Date(<fmt:formatDate value="${now}" pattern="yy,MM,dd,kk,mm,ss"/>);
        $(document).ready(function() {
            timeview();
            setTimeout(function(){ window.location.reload();}, 60000);
        });
        
        function timeview(){
          timestr=OA_TIME.toLocaleString();
          timestr=timestr.substr(timestr.indexOf(":")-2);
          $('#now1').html(timestr);
          OA_TIME.setSeconds(OA_TIME.getSeconds()+1);
          window.setTimeout( "timeview()", 1000 );
        }
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
  
  <form id="mainForm" action="resource.action" method="post">
    <input type="hidden" name="page.pageNo" id="pageNo" value="${page.pageNo}"/>
    <input type="hidden" name="authId" id="authId" value="${authId}"/>
    <input type="hidden" name="page.orderBy" id="orderBy" value="${page.orderBy}"/>
    <input type="hidden" name="page.order" id="order" value="${page.order}"/>
    <input type="hidden" name="page.pageSize" id="pageSize" value="${page.pageSize}" />
    <input type="hidden" name="page.excelExp" id="excelExp" value="${page.excelExp}" />
    
  <!-- 查询条件
  <div id="filter" class="filter">
    <div id="item" class="item">
      <span>资源字符串:</span><input type="text" name="filter_LIKES_resString" value="${param['filter_LIKES_resString']}" size="9"/>
      <span>名称:</span><input type="text" name="filter_LIKES_label" value="${param['filter_LIKES_label']}" size="9"/>
      <span>资源类型:</span>
      <div class="space"></div>
      <div style="text-align: center;">
      <security:authorize ifAnyGranted="A_sys_resource_select"><img src="../images/b_select.gif" alt="" onclick="search();" class="pointer" align="middle"/></security:authorize>
      <img src="../images/b_reset.gif" alt="" onclick="resetb();" class="pointer"align="middle"/>
      </div>
    </div>
    <div id="contral" class="contral pointer" onclick="contral(this);"><img src="../images/f_close.gif" title="收起查询面板"/></div>
  </div>
   -->
   
  <!-- 附加信息-->
  <div id="message" class="message">
    <div style="font-weight: bold;font-size: 14px;">当前时间：<fmt:formatDate value="${now}" pattern="yy/MM/dd EEEE"/> <span id="now1"></span></div>
        计划开启状态才可以载入内存运行，提供两种模式的Job调度
  </div>  

  <!-- 增删改查...操作菜单-->
  <div id="operate" class="operate">
    <div><security:authorize ifAnyGranted="A_sys_schedule_add"><img src="../images/b_add.gif" alt="增加" onclick="opr_input('quartz!input.action',false,'${authId}');"/></security:authorize></div>
    
  </div>
  
  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
          
 <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody>
        <tr>
          <th class="first"><b>计划名称</b></th>
          <th><b>计划描述</b></th>
          <th><b>附加信息</b></th><!--
          <th><b>Class</b></th>
          --><th><b>开启</b></th>
          <th><b>状态</b></th>
          <th><b>重复间隔</b></th>
          <th><b>运行状态</b></th>
          <th width="100" class="last"><b>操作</b></th>
        </tr>
        </tbody>
        <tbody id="listSearch">
      
      <c:forEach items="${allQuartzs}" var="q" varStatus="s">
        <c:if test="${s.first}"><c:set var="v">${q.type}</c:set></c:if>
        <c:if test="${s.first || v!=q.type}">
        <c:set var="v">${q.type}</c:set>
        <tr class="mouseout">
          <td colspan="8" style="height: 20px;line-height: 20px;background: #cceafd"><b><font color="green">${q.type!=null?q.type:'未定义分组'}</font></b></td>
        </tr>
        </c:if>
        <tr class="mouseout">
          <td><c:if test="${q.system}"><img src="${ctx}/images/lock.png" title="系统计划，谨慎操作"/> </c:if><span title="${q.description}">${q.name}</span></td>
          <td>${q.description}</td>
          <td>${q.jobdata}</td><!--
          <td>${q.classname}</td>
          --><td>${q.pause?'关':'开'}</td>
          <td style="line-height: 16px;">${q.jobTrigger.triggerStateHtml} ${q.jobTrigger.executing?'<br/><font color="red">(executing)</font>':''}
          </td>
          <td>${q.jobTrigger.quartzStr}</td>
          <td style="line-height: 16px;">
          <c:if test="${q.jobTrigger.trigger.startTime!=null}">
            Init: <fmt:formatDate value="${q.jobTrigger.trigger.startTime}" pattern="yyyy-MM-dd kk:mm:ss"/><br/>
         </c:if>
         <c:if test="${q.jobTrigger.trigger.previousFireTime!=null}">
            Prev: <fmt:formatDate value="${q.jobTrigger.trigger.previousFireTime}" pattern="yyyy-MM-dd kk:mm:ss"/><br/>
         </c:if>
         <c:if test="${q.jobTrigger.trigger.nextFireTime!=null}">
            Next: <fmt:formatDate value="${q.jobTrigger.trigger.nextFireTime}" pattern="yyyy-MM-dd kk:mm:ss"/>
         </c:if>
         </td>
         <td>
            <security:authorize ifAnyGranted="A_sys_schedule_edit">
            <a href="quartz!input.action?id=${q.id}&name=${q.name}&authId=${authId}"><img src="${ctx}/images/edt.gif" title="修改" /></a> 
            </security:authorize>
            <security:authorize ifAnyGranted="A_sys_schedule_delete">
            <c:if test="${!q.system}"><img style="cursor: pointer;" src="${ctx}/images/del.gif" title="删除" onclick="delItem('quartz!delete.action?id=${id}&name=${name}&authId=${authId}');"/></c:if>
            </security:authorize>
            <security:authorize ifAnyGranted="A_sys_schedule_start">
            <c:if test="${q.jobTrigger==null}">
            <a href="quartz!operate.action?op=start&name=${q.name}&authId=${authId}"><img src="${ctx}/images/control_start.png" title="开启计划" /></a>
            </c:if>
            </security:authorize>
            <c:if test="${q.jobTrigger.triggerState==0||q.jobTrigger.triggerState==1}">
            <security:authorize ifAnyGranted="A_sys_schedule_tigger">
            <a href="quartz!operate.action?op=trigger&name=${q.name}&authId=${authId}"><img src="${ctx}/images/arrow_refresh.png" title="触发" /></a>
            </security:authorize>
            <security:authorize ifAnyGranted="A_sys_schedule_stop">
            <a href="quartz!operate.action?op=stop&name=${q.name}&authId=${authId}"><img src="${ctx}/images/control_stop.png" title="终止计划" /></a>
            </security:authorize>
            </c:if>
            <security:authorize ifAnyGranted="A_sys_schedule_resume">
            <c:if test="${q.jobTrigger.triggerState==1}">
            <a href="quartz!operate.action?op=resume&name=${q.name}&authId=${authId}"><img src="${ctx}/images/control_play.png" title="恢复" /></a>
            </c:if>
            </security:authorize>
            <security:authorize ifAnyGranted="A_sys_schedule_pause">
            <c:if test="${q.jobTrigger.triggerState==0}">
            <a href="quartz!operate.action?op=pause&name=${q.name}&authId=${authId}"><img src="${ctx}/images/control_pause.png" title="暂停" /></a>
            </c:if>
            </security:authorize>
            <security:authorize ifAnyGranted="A_sys_schedule_interrupt">
            <c:if test="${q.jobTrigger.triggerState==4}">
            <a href="quartz!operate.action?op=interrupt&name=${q.name}&authId=${authId}"><img src="${ctx}/images/control_stop.png" title="强制中断" /></a>
            </c:if>
            </security:authorize>
            <!-- ${jobTrigger.triggerState} -->
          
          </td>
        </tr>
      </c:forEach>
        </tbody>
        </table>
      </div>                


    </div>
    </div>
    </form>
    </div>
</body>
</html>