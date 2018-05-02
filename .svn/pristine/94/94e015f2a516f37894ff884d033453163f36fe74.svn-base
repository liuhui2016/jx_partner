<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>无标题文档</title>
<%@ include file="/common/head.jsp" %>
<style type="text/css">
html {height: 100%}
.bg {background: url(${ctx}/images/wel.jpg) no-repeat scroll 99% bottom transparent; margin: 0 4px 0 0; padding: 0;overflow: hidden;}
.tips {z-index: 20;width: 455px;}
.bg1 {background: url("${ctx}/images/web/tipbg1.gif") no-repeat scroll 0 0 transparent;height: 16px;
    padding: 3px 6px 0 28px; line-height: 20px;font-weight: bold;color: #1e445c;}
.bg2 {background: url("${ctx}/images/web/tipbg2.png") no-repeat scroll 0 0 transparent;height: 5px;}
.cont{background: #f8ffef;border-left: 1px solid #d4e4bf;border-right: 1px solid #d4e4bf;
height: auto !important; height: 30px; min-height: 30px;padding: 10px 0;}
.item{
    background: url("${ctx}/images/web/brightness.png") no-repeat scroll 0 0px transparent;
    margin: 0 20px;
    padding: 0 0 0 16px;
}
.item a{color: #1e445c;}    
.item a:HOVER{color: #f60;}   
.content td, th {white-space:normal;}
.line_table td{padding: 4px;}
.a_approve{font-weight: bold;color: green;}
.a_apply{color: blue;}
</style>
<script type="text/javascript">

</script>

</head>
<body class="bg">
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>欢迎界面</h2></div>

  <!-- 列表区域-->
  <div id="content" class="content">
    <div id="indiv">
        <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
                  <tbody>

                  
                  <tr valign="top">
                    <td>
                    <div class="tips" style="margin-top: 10px">
                    <div class="bg1">最新公告
                    <span style="float: right;">
                    <c:if test="${annoList.hasNext}">
                    <a href="${ctx}/system/announcement.action" style="font-family: Verdana;color: #043A76;">more</a>
                    </c:if>
                    </span></div>
                    <div class="cont">
                    <table width="100%" cellspacing="0" cellpadding="0" border="0" class="line_table" >
                      <tbody>
                      <s:if test="annoList.size == 0"><tr><td colspan="3"></td></tr></s:if>
                      <s:else>
                        <s:iterator value="annoList.result" status="stat">
                            <tr>
                                <td align="left"><div class="item" style="margin: 0"><a href="${ctx}/system/announcement!detail.action?id=${id}">${title}</a></div></td>
                                <!-- <td align="center">${createBy }</td>
                                <td align="center">${typeName}</td> -->
                                <td align="center"><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd"/></td>
                            </tr>
                        </s:iterator>
                      </s:else>
                    </tbody>
                   	</table>
                    
                    </div>
                    <div class="bg2"></div>
                    </div>
                    </td>
                  </tr>
            </tbody>
        </table>
    </div>
  </div>
  </div>


</body>
</html>