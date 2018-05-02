<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>EHcache</title>
<%@ include file="/common/head.jsp" %>
<script type="text/javascript">
function clean(key){
    location.href = 'ehcache!gcCache.action?action=true&authId=${authId}&key='+key;
}
</script>
<style>
ol{margin: 0;padding: 0}
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

table td{padding: 3px;}
</style>
    <script>
    function getT(times){
          var today=new Date(times);
          var month=today.getMonth()+1;
          var year=(today.getYear() > 200) ? today.getYear() : 1900 + today.getYear();
          var tt = year+'-'+month+'-'+today.getDate()+' '+today.getHours()+':'+today.getMinutes()+'.'+today.getMilliseconds();
         return tt; 
        }
    </script>
</head>

<body >

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

  <!-- 附加信息-->
  <div id="message" class="message">
    This shows all caches ststus and hibernate spring conf...
  </div>  
  
  <!-- 列表区域-->
  <div id="content" class="content">

  <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
  
    <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ Cache Info -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: green;font-size: 14px;">Cache Operation</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td colspan="2" style="text-align: center;height:35px;">
                <input type='button' value=' 清除All缓存 ' onclick='clean("all");'/>&nbsp;
                <input type='button' value=' 二级缓存[domain] ' onclick='clean("domain");'/>&nbsp;
                <input type='button' value=' 二级缓存[Resources] ' onclick='clean("Resources");'/>&nbsp;
                <input type='button' value=' 二级缓存[Authority] ' onclick='clean("Authority");'/>&nbsp;
                <input type='button' value=' ehCache缓存[hour] ' onclick='clean("hourCache");'/>&nbsp;
                <input type='button' value=' ehCache缓存[day] ' onclick='clean("dayCache");'/>&nbsp;
                <input type='button' value=' ehCache缓存[halfMonth] ' onclick='clean("halfMonthCache");'/>&nbsp;
                <input type='button' value=' SpringSecurity缓存 ' onclick='clean("security");'/>
                
                </td>
            </tr>
    </tbody>

  </table>
  
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: green;font-size: 14px;">RunQian status</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td colspan="2" style="text-align: center;height:35px;">
<%
//out.print("当前已使用的非线性报表个数为："+com.runqian.report4.model.engine.ExtCellSet.getCurrentNonlinearCount()+"<br/>"); 
//out.print("当前使用授权文件："+com.runqian.report4.model.engine.ExtCellSet.getLicenseFileName()+"<br/>"); 
%>
                </td>
            </tr>
    </tbody>

  </table>  


    <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ EhCache Info -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: green;font-size: 14px;">EHcache pool监控</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
    <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
      <td colspan="2" style="text-align: center;height:35px;padding-bottom: 4px;">
      

<div>
    <span>CacheManager.ALL_CACHE_MANAGERS;</span>
    <table width="100%" cellspacing="0" cellpadding="0" bordercolor="#c0c0c0" border="1" style="border-collapse: collapse;" >
    <tr style="background-color: #E7F1FD;font-weight: bold;color: #2B7BAC;">
        <td>name</td>
        <td>CacheNames</td>
        <td>DiskStorePath</td>
        <td>status</td>
        <td>isNamed</td>
        <td>UUID</td>
    </tr>
    <% 
    java.util.List<net.sf.ehcache.CacheManager> knownCacheManagers = net.sf.ehcache.CacheManager.ALL_CACHE_MANAGERS;
    for(net.sf.ehcache.CacheManager cache:knownCacheManagers){
        
    %>
    <tr>
        <td><%=cache.getName()%></td>
        <td><%=org.apache.commons.lang.StringUtils.join(cache.getCacheNames(),";")%></td>
        <td><%=cache.getDiskStorePath()%></td>
        <td><%=cache.getStatus()%></td>
        <td><%=cache.isNamed()%></td>
        <td><%=cache.getClusterUUID()%></td>
    </tr>
    <%}%>
    </table>
</div>
      
<br/>

<%
net.sf.ehcache.CacheManager cm = knownCacheManagers.get(1);
net.sf.ehcache.Cache ca=cm.getCache("hbCache");
java.util.Map<Object, net.sf.ehcache.Element> ess = com.google.common.collect.Maps.newHashMap();
for (Object obj : ca.getKeysNoDuplicateCheck()) {
    ess.put(obj, ca.getQuiet(obj));
}
request.setAttribute("hbcache",ca);
request.setAttribute("hbkeys",ess);
%>

<div>
    <span>HibernateCache-> ${hbcache.statistics}</span>
    <table width="100%" cellspacing="0" cellpadding="0" bordercolor="#c0c0c0" border="1" style="border-collapse: collapse;" >
    <tr style="background-color: #E7F1FD;font-weight: bold;color: #2B7BAC;">
        <td>缓存对象数量</td>
        <td>占用内存空间</td>
        <td></td>
        <td></td>
    </tr>
    <tr>
        <td><%=ess.size()%></td>
        <td><fmt:formatNumber value="${hbcache.memoryStoreSize}" pattern="#,#00.00#"/> kb</td>
        <td></td>
        <td></td>
    </tr>
    </table>
</div>
                    
<div>
    <span>hourCache-> ${hourCache.cache.statistics}</span>
    <table width="100%" cellspacing="0" cellpadding="0" bordercolor="#c0c0c0" border="1" style="border-collapse: collapse;" >
    <tr style="background-color: #E7F1FD;font-weight: bold;color: #2B7BAC;">
        <td>缓存对象关键字</td>
        <td>创建时间</td>
        <td>HitCount</td>
        <td>ExpirationTime</td>
        <td>Version</td>
        <td>SerializedSize</td>
        <td>TimeToIdle/Live</td>
    </tr>
    <c:forEach var="e" items="${hourCache.elements}">
    <tr>
        <td>${e.key}</td>
        <td><script>document.write(getT(${e.value.creationTime}));</script></td>
        <td>${e.value.hitCount}</td>
        <td><script>document.write(getT(${e.value.expirationTime}));</script></td>
        <td>${e.value.version}</td>
        <td>${e.value.serializedSize} byte</td>
        <td>${e.value.timeToIdle}/${e.value.timeToLive}</td>
    </tr>
    </c:forEach>
     <tr>
        <td colspan="5"></td>
        <td><b>SUM: <fmt:formatNumber value="${hourCache.cache.memoryStoreSize}" pattern="#,#00.00#"/> kb<b/></td>
        <td></td>
    </tr>
    </table>
</div>

<div>
    <span>hourCache-> ${dayCache.cache.statistics}</span>
    <table width="100%" cellspacing="0" cellpadding="0" bordercolor="#c0c0c0" border="1" style="border-collapse: collapse;" >
    <tr style="background-color: #E7F1FD;font-weight: bold;color: #2B7BAC;">
        <td>缓存对象关键字</td>
        <td>创建时间</td>
        <td>HitCount</td>
        <td>ExpirationTime</td>
        <td>Version</td>
        <td>SerializedSize</td>
        <td>TimeToIdle/Live</td>
    </tr>
    <c:forEach var="e" items="${dayCache.elements}">
    <tr>
        <td>${e.key}</td>
        <td><script>document.write(getT(${e.value.creationTime}));</script></td>
        <td>${e.value.hitCount}</td>
        <td><script>document.write(getT(${e.value.expirationTime}));</script></td>
        <td>${e.value.version}</td>
        <td>${e.value.serializedSize} byte</td>
        <td>${e.value.timeToIdle}/${e.value.timeToLive}</td>
    </tr>
    </c:forEach>
     <tr>
        <td colspan="5"></td>
        <td><b>SUM: <fmt:formatNumber value="${dayCache.cache.memoryStoreSize}" pattern="#,#00.00#"/> kb<b/></td>
        <td></td>
    </tr>
    </table>
</div>


<div>
    <span>hourCache-> ${halfMonthCache.cache.statistics}</span>
    <table width="100%" cellspacing="0" cellpadding="0" bordercolor="#c0c0c0" border="1" style="border-collapse: collapse;" >
    <tr style="background-color: #E7F1FD;font-weight: bold;color: #2B7BAC;">
        <td>缓存对象关键字</td>
        <td>创建时间</td>
        <td>HitCount</td>
        <td>ExpirationTime</td>
        <td>Version</td>
        <td>SerializedSize</td>
        <td>TimeToIdle/Live</td>
    </tr>
    <c:forEach var="e" items="${halfMonthCache.elements}">
    <tr>
        <td>${e.key}</td>
        <td><script>document.write(getT(${e.value.creationTime}));</script></td>
        <td>${e.value.hitCount}</td>
        <td><script>document.write(getT(${e.value.expirationTime}));</script></td>
        <td>${e.value.version}</td>
        <td>${e.value.serializedSize} byte</td>
        <td>${e.value.timeToIdle}/${e.value.timeToLive}</td>
    </tr>
    </c:forEach>
     <tr>
        <td colspan="5"></td>
        <td><b>SUM: <fmt:formatNumber value="${halfMonthCache.cache.memoryStoreSize}" pattern="#,#00.00#"/> kb<b/></td>
        <td></td>
    </tr>
    </table>
</div>
                
                </td>
            </tr>            
    </tbody>

  </table>  




  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ Hinernate 监控 -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">Hinernate 监控</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>AllClassMetadata</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">
                <ul><c:forEach var="entry" items="${sessionFactory.allClassMetadata}"> <ol>${entry.key} -> ${entry.value} </ol></c:forEach></ul>
                </td>
            </tr>
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>AllCollectionMetadata</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">
                <ul><c:forEach var="entry" items="${sessionFactory.allCollectionMetadata}"> <ol>${entry.key} -> ${entry.value} </ol></c:forEach></ul>
                </td>
            </tr>  
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>二级缓存状态</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">
                start time:<script>document.write(getT(${sessionFactory.statistics.startTime}));</script><br/>
                ${sessionFactory.statistics}<br/>
                PrepareStatementCount:${sessionFactory.statistics.prepareStatementCount}<br/>
                SecondLevelCache命中次数:${sessionFactory.statistics.secondLevelCacheHitCount}<br/>
                SecondLevelCache错过次数:${sessionFactory.statistics.secondLevelCacheMissCount}<br/>
                SecondLevelCache放入次数:${sessionFactory.statistics.secondLevelCachePutCount}<br/>

                </td>
            </tr>                       
    </tbody>

  </table>





  <!-- +++++++++++++++++++++++++++++++++++++++++++++++++++ Spring状态监控 -->
  <br/>
  <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
        <tr>
            <th class="first" width="230" style="color: #ff0000;font-size: 16px;">Spring状态监控</th>
            <th class="last"></th>
        </tr>
    </tbody>
    <tbody id="listSearch">
               
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>StartupDate</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;color: #ff0000;font-weight: bold;">
                <script>document.write(getT(${applicationContext.startupDate}));</script>
                </td>
            </tr>    
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>Size</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">
                ${applicationContext.beanDefinitionCount}
                </td>
            </tr>               
            <tr class="mouseout" onmouseout="this.className='mouseout'" onmousemove="this.className='mouseover'">
                <td valign="top" width="20%">&nbsp; <b>SpringBean</b></td>
                <td valign="top" style="white-space:normal;font-size: 8pt;line-height:22px;">
                <ul>
                <c:forEach items="${applicationContext.beanDefinitionNames}" var="a">
                <ol>${a}</ol>
                </c:forEach>
                </ul>
                </td>
            </tr> 
    </tbody>

  </table>


</div>

</div>
</div>

</body>
</html>
