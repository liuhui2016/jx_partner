<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp" %>
<style type="text/css">
<!--

-->
</style>

<script>

</script>

<style>
.tab_cont th, .tab_cont td {
height:22px;
line-height:22px;
text-align:left;
font-size: 8pt;
}
.content td, th {
padding:1px 10px;
white-space:normal;
}
</style>
    <script>
    function getT(times){
        var today=new Date(times);
        var month=today.getMonth()+1;
        var year=(today.getYear() > 200) ? today.getYear() : 1900 + today.getYear();
        var tt = year+'-'+month+'-'+today.getDate()+' '+today.getHours()+':'+today.getMinutes()+'.'+today.getMilliseconds();
       return tt; 
      }    
    function getTT(times){
         var today=new Date();
         var tt =  Math.round(today.getTime()-times)/3600000;        
         return FormatNumber(tt,3)+' h'; 
    }
    function FormatNumber(srcStr,nAfterDot){
        　　var srcStr,nAfterDot;
        　　var resultStr,nTen;
        　　srcStr = ""+srcStr+"";
        　　strLen = srcStr.length;
        　　dotPos = srcStr.indexOf(".",0);
        　　if (dotPos == -1){
        　　　　resultStr = srcStr+".";
        　　　　for (i=0;i<nAfterDot;i++){
        　　　　　　resultStr = resultStr+"0";
        　　　　}
        　　　　return resultStr;
        　　}
        　　else{
        　　　　if ((strLen - dotPos - 1) >= nAfterDot){
        　　　　　　nAfter = dotPos + nAfterDot + 1;
        　　　　　　nTen =1;
        　　　　　　for(j=0;j<nAfterDot;j++){
        　　　　　　　　nTen = nTen*10;
        　　　　　　}
        　　　　　　resultStr = Math.round(parseFloat(srcStr)*nTen)/nTen;
        　　　　　　return resultStr;
        　　　　}
        　　　　else{
        　　　　　　resultStr = srcStr;
        　　　　　　for (i=0;i<(nAfterDot - strLen + dotPos + 1);i++){
        　　　　　　　　resultStr = resultStr+"0";
        　　　　　　}
        　　　　　　return resultStr;
        　　　　}
        　　}
        }
    
    </script>
</head>

<body>
<div class="currloca">
  <p>${auth.fullMenu}</p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="add2custom"><img class="pointer" onclick="add2custom('${authId}');return false;" id="aCustom" width="19" height="18" title="添加到常用操作" src="../images/add_custom.gif"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${auth.label}</h2></div>

  <!-- 附加信息-->
  <div id="message" class="message">
    This shows JVM status and system info.
  </div>  

  <!-- 列表区域-->
  <div id="content" class="content">

  <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
  
  
  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Info -->
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">System Info</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>System uptime</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;color: #ff0000;font-weight: bold;">
                <script>document.write(getTT(${applicationContext.startupDate}));</script></td>
            </tr>
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>StartupDate</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;color: #ff0000;font-weight: bold;">
                <script>document.write(getT(${applicationContext.startupDate}));</script>
                </td>
            </tr>
            <c:if test="${systemPropertiesHTML['weblogic.Name']!=null}">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>weblogic.Name</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;color: #ff0000;font-weight: bold;">
                ${systemPropertiesHTML['weblogic.Name']}
                </td>
            </tr>
            </c:if>
        <c:forEach var="e" items="${esinfo.props}">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>${e.key}</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">${e.value}</td>
            </tr>
        </c:forEach>
    </tbody>

  </table>
    
  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ Java memory.statistics -->
    <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">Java memory</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
        <c:forEach var="e" items="${esinfo.jvmStats}">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top">&nbsp; <b>${e.key}</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">${e.value}</td>
            </tr>
        </c:forEach>
    <tr>
      <td valign="top">&nbsp; <b>Memory Graph</b></td>
      <td>
        <table width="40%" cellspacing="0" cellpadding="0" border="0" style="float: left;">
              <tbody>
             <tr><td width="'<fmt:formatNumber value="${esinfo.systemInfoUtils.usedMemory/esinfo.systemInfoUtils.totalMemory}" type="percent"/>'" bgcolor="#cc3333"height="15" >
                    <a title="Used Memory">
                       <img width="100%" height="15" border="0" title="" alt="" src="${ctx}/images/spacer.gif"/>
                    </a>
                    </td>
                    <td width='<fmt:formatNumber value="${esinfo.systemInfoUtils.freeMemory/esinfo.systemInfoUtils.totalMemory}" type="percent"/>' bgcolor="#00cc00"height="15" >
                        <a title="Free Memory">
                            <img width="100%" height="15" border="0" title="" alt="" src="{ctx}/images/spacer.gif"/>
                        </a>
                    </td>
                </tr></tbody>
        </table>
            <b>&nbsp;&nbsp;
            <fmt:formatNumber value="${esinfo.systemInfoUtils.freeMemory/esinfo.systemInfoUtils.totalMemory}" type="percent" maxFractionDigits="2"/> Free </b>
            (使用: ${esinfo.systemInfoUtils.usedMemory} MB - 全部: ${esinfo.systemInfoUtils.totalMemory} MB)
            &nbsp;&nbsp;<span class="small">(<a href="system!doGarbageCollection.action?authId=${authId}">Force GC</a>)</span>
      </td>
    </tr>
    <tr>
      <td valign="top">&nbsp; <b>PermGen Memory Graph</b></td>
      <td>
        <table width="40%" cellspacing="0" cellpadding="0" border="0" style="float: left;">
              <tbody>
             <tr><td width="'<fmt:formatNumber value="${esinfo.systemInfoUtils.usedPermGenMemory/esinfo.systemInfoUtils.totalPermGenMemory}" type="percent"/>'" bgcolor="#cc3333"height="15" >
                    <a title="Used Memory">
                       <img width="100%" height="15" border="0" title="" alt="" src="${ctx}/images/spacer.gif"/>
                    </a>
                    </td>
                    <td width='<fmt:formatNumber value="${esinfo.systemInfoUtils.freePermGenMemory/esinfo.systemInfoUtils.totalPermGenMemory}" type="percent"/>' bgcolor="#00cc00"height="15" >
                        <a title="Free Memory">
                            <img width="100%" height="15" border="0" title="" alt="" src="{ctx}/images/spacer.gif"/>
                        </a>
                    </td>
                </tr></tbody>
        </table>
            <b>&nbsp;&nbsp;
            <fmt:formatNumber value="${esinfo.systemInfoUtils.freePermGenMemory/esinfo.systemInfoUtils.totalPermGenMemory}" type="percent" maxFractionDigits="2"/> Free </b>
            (使用: ${esinfo.systemInfoUtils.usedPermGenMemory} MB - 全部: ${esinfo.systemInfoUtils.totalPermGenMemory} MB)
      </td>
    </tr>
        
    <tr>
      <td valign="top">&nbsp; <b>Non-Heap Memory Graph (includes PermGen)</b></td>
      <td>
        <table width="40%" cellspacing="0" cellpadding="0" border="0" style="float: left;">
              <tbody>
             <tr><td width="'<fmt:formatNumber value="${esinfo.systemInfoUtils.usedNonHeapMemory/esinfo.systemInfoUtils.totalNonHeapMemory}" type="percent"/>'" bgcolor="#cc3333"height="15" >
                    <a title="Used Memory">
                       <img width="100%" height="15" border="0" title="" alt="" src="${ctx}/images/spacer.gif"/>
                    </a>
                    </td>
                    <td width='<fmt:formatNumber value="${esinfo.systemInfoUtils.freeNonHeapMemory/esinfo.systemInfoUtils.totalNonHeapMemory}" type="percent"/>' bgcolor="#00cc00"height="15" >
                        <a title="Free Memory">
                            <img width="100%" height="15" border="0" title="" alt="" src="{ctx}/images/spacer.gif"/>
                        </a>
                    </td>
                </tr></tbody>
        </table>
            <b>&nbsp;&nbsp;
            <fmt:formatNumber value="${esinfo.systemInfoUtils.freeNonHeapMemory/esinfo.systemInfoUtils.totalNonHeapMemory}" type="percent" maxFractionDigits="2"/> Free </b>
            (使用: ${esinfo.systemInfoUtils.usedNonHeapMemory} MB - 全部: ${esinfo.systemInfoUtils.totalNonHeapMemory} MB)
      </td>
    </tr>
    <tr>
      <td colspan="2">
      This lists detailed information about the various parts of memory that the Java virtual machine uses to store its data. The information it presents is not a lot of use without a detailed understanding of this particular VM's garbage collector and how it is configured. This page will generally only be useful to Atlassian's support engineers. Specifically, high usage of a particular memory pool does not necessarily indicate any problem.
      </td>
    </tr>
    
    <c:forEach items="${esinfo.memoryPoolInformation}" var="e">
                <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>${e.name}</b></td>
                <td valign="top">
                
        <table width="40%" cellspacing="0" cellpadding="0" border="0" style="float: left;">
              <tbody>
             <tr><td width="'<fmt:formatNumber value="${e.used/e.total}" type="percent"/>'" bgcolor="#cc3333"height="15" >
                    <a title="Used Memory">
                       <img width="100%" height="15" border="0" title="" alt="" src="${ctx}/images/spacer.gif"/>
                    </a>
                    </td>
                    <td width='<fmt:formatNumber value="${e.free/e.total}" type="percent"/>' bgcolor="#00cc00"height="15" >
                        <a title="Free Memory">
                            <img width="100%" height="15" border="0" title="" alt="" src="{ctx}/images/spacer.gif"/>
                        </a>
                    </td>
                </tr></tbody>
        </table>                
                
                <b>&nbsp;&nbsp;
                <fmt:formatNumber value="${e.free/e.total}" type="percent" maxFractionDigits="2"/> Free </b>
                (使用: <fmt:formatNumber value="${e.used/1024/1024}" type="number" maxFractionDigits="2"/> MB - 全部: <fmt:formatNumber value="${e.total/1024/1024}" type="number" maxFractionDigits="0"/> MB)</td>
            </tr>
    </c:forEach>
    
    </tbody>
  </table>
  
  


  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ System Properties -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">

    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">System Properties</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
        <c:forEach var="e" items="${systemPropertiesHTML}">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top">&nbsp; <b>${e.key}</b></td>
                <td valign="top" style="font-size: 8pt;line-height:22px;"><c:out value="${e.value}" escapeXml="false"></c:out></td>
            </tr>
        </c:forEach>
    </tbody>
    

    
    
    
                
        </table>
      </div>   
        	
  </div>
 
</div>

</body>
</html>

