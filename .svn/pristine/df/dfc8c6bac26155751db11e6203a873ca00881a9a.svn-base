<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS">
    <c:redirect url="/login.action" />
</security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp"%>
<style type="text/css">
<!--
-->
</style>

</head>

<body>
<div class="currloca">
<p>${auth.fullMenu}</p>
<div class="sitemap"><span style="display: block; float: left"><s:actionmessage theme="custom" /></span> <span
    id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19"
    height="18" title="添加到常用操作" src="../images/add_custom.gif" /></span> <span id="showMap"><img
    onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航"
    src="../images/map.gif" /></span></div>
</div>

<div class="container"><!-- 内容区域 -->
<div class="itemtitle">
<h2>${auth.label}</h2>
</div>

<!-- 附加信息-->
<div id="message" class="message"><br>
This page allows you to change the level at which WLXM logs its messages. It is useful if you are debugging Syetem.
<p>Note that any changes you make here are <i>not persisted</i> across server restarts.<br>
You will need to edit 'WEB-INF/classes/log4j.properties' to change levels permanently.</p>
<p>Logging will go to the console.</p>
</div>

<!-- 列表区域-->
<div id="content" class="content">

<div id="indiv" style="width: 100%; OVERFLOW-X: auto;"><!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Properties -->
<br />
<table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">

    <tbody>
        <tr>
            <th class="first" width="330">Package name</th>
            <th>Logging Level</th>
            <th class="last">操作</th>
        </tr>
    </tbody>
    <tbody id="listSearch">
        <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
            <td style="font-size: 10pt; line-height: 18px;">&nbsp; ${rootLogger.name}</td>
            <td><b>${rootLogger.level}</b></td>
            <td>
            <span>
            <c:forEach items="${availablelevels}" var="a">
              <a href="logging!uplevel.action?levelName=${a}&loggerName=${rootLogger.name}&authId=${authId}">${a}</a> 
            </c:forEach>
            </span></td>
        </tr>    
    <c:forEach items="${loggers}" var="l">
        <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
            <td style="font-size: 10pt; line-height: 18px;">&nbsp; ${l.name}</td>
            <td><b>${l.level}</b></td>
            <td><span> 
            <c:forEach items="${availablelevels}" var="a">
              <a href="logging!uplevel.action?levelName=${a}&loggerName=${l.name}&authId=${authId}">${a}</a> 
            </c:forEach>
            </span></td>
        </tr>
    </c:forEach>
    </tbody>

</table>


</div>
</div>
</div>

</body>
</html>
