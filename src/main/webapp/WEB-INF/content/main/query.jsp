<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp" %>
<style type="text/css">
.content td, th {white-space:normal;}
p{line-height: 150%;text-indent: 20px}
h2{margin-bottom: 10px;}
</style>
</head>

<body>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>欢迎登录综合信息查询展示</h2></div>

  <!-- 列表区域-->
  <div id="content" class="content">
    <div>
        <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
                  <tbody>
                  <tr>
                    <td valign="top" colspan="2">
                        <h2 style="color:#000">综合信息查询</h2>
                        <p> 综合信息查询主要实现系统集成后的综合信息汇总查询，将其它系统、外部系统的信息整合后的完整信息展现。</p>
						<p>(1)炼厂资源信息：掌握所属区域内各炼厂每天统销炼油产品分品种产量、成品油库存量、生产进度安排及炼厂交货情况等；</p>
						<p>(2)运力资源信息：对所属区域内公路、铁路、管道、海运运力资源的基本信息、状态等进行动态查询；</p>
						<p>(3)库存信息：对所属区域内各油库库存情况进行动态查询；</p>
						<p>(4)销售信息：销售公司对所属区域内各种型号成品油的销售情况进行动态查询；</p>
						<p>(5)气象信息：对各地区的气象信息进行动态查询；</p>
						<p>(6)二次配送：对所属区域内各省销售公司的二次配送完成量、成本、车辆使用情况等进行查询。</p>
                    </td>
                    <td width="7%">&nbsp;</td>
                  </tr>

                </tbody></table>
    </div>
  </div>
  </div>

  
  <!-- 分页区域-->
  <div id="page" class="page">
 
  </div>  
  

</body>
</html>

