<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<%@ taglib uri="http://java.fckeditor.net" prefix="FCK" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>内容修改</title>
    <%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
    <link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
    <style type="text/css">
    .error{
        display : block;
    }
    </style>
    <script>
    $(function() {
        var dates = $('#start, #end').datepicker({
            changeMonth: true,
            numberOfMonths: 1,
            prevText:'前一月', 
            nextText:'后一月', 
            yearSuffix: '',  
            showButtonPanel: true,   
            onSelect: function(selectedDate) {
                var option = this.id == "start" ? "minDate" : "maxDate";
                var instance = $(this).data("datepicker");
                var date = $.datepicker.parseDate(instance.settings.dateFormat || $.datepicker._defaults.dateFormat, selectedDate, instance.settings);
                dates.not(this).datepicker("option", option, date);
            }
        });
        //为inputForm注册validate函数
        $.validator.addMethod("isNotNull",function(value,element,params){
				return FCKeditorAPI.GetInstance('content').GetXHTML(true).length > 0;
			},$.format("正文内容不能为空！"));
        $("#inputForm").validate({
            rules: {
                title: "required",
                content:{
        			isNotNull:true
        		},
                start: "required",
                end: "required"
            },
            messages: {
                title: {
                    required: "标题不能为空！"
                },
                start: {
                    required: "开始时间不能为空！"
                },
                content:{
					isNotNull:"正文内容不能为空！"
                },
                end: {
                    required: "结束时间不能为空！"
                }
            }
        });
    });
    </script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>${id==null?'新增公告':'修改公告' }</span></p>
  <div class="sitemap">
    <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增公告':'修改公告' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="inputForm" action="announcement!save.action" method="post">
      <!-- 列表区域-->
      <div id="content" class="content input">
        <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
            <input type="hidden" name="id" value="${id}"/>
            <input type="hidden" name="authId" value="${authId}"/>
            <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
                <tbody>
                    <tr>
                      <th class="first" width="130">标准信息</th>
                      <th class="last"></th>
                    </tr>
                    <tr>
                        <td  class="right">公告名称:</td>
                        <td><input type="text" name="title" size="40" id="title" maxlength="255" value="${title}"/></td>
                    </tr>
                    <tr>
                        <td  class="right">开始时间:</td>
                        <td><input type="text" id="start" readonly="readonly" name="start" size="40" maxlength="255" value="<fmt:formatDate value="${start}" pattern="yyyy-MM-dd"/>"/></td>
                    </tr>
                    <tr>
                        <td  class="right">结束时间:</td>
                        <td><input type="text" id="end" readonly="readonly" name="end" size="40" maxlength="255" value="<fmt:formatDate value="${end}" pattern="yyyy-MM-dd"/>"/></td>
                    </tr>
                    <tr>
                        <td  class="right">公告类型:</td>
                        <td>
                            <select name="type">
                                <s:iterator value="annoTypeList" id="obj">
                                    <option value="${obj.value}"
                                        <s:if test="#obj.value==type">
                                            selected = "selected"
                                        </s:if>
                                    >${obj.label}</option>
                                </s:iterator>
                            </select>
                        </td>
                    </tr>           
                    <tr>
                        <td  class="right"><span class="red">*</span>公告内容:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                        <td>
                            <FCK:editor instanceName="content" basePath="/js/fckeditor" value="${content}"></FCK:editor>
                        </td>
                    </tr>           
                    <tr>
                        <th class="first" width="130"></th>
                        <th class="last">
                            <security:authorize ifAnyGranted="A_sys_resource_add">
                                <input class="button" type="submit" value="提交"/>&nbsp;
                            </security:authorize>
                            <input class="button" type="button" value="返回" onclick="history.back()"/>
                        </th>
                    </tr>
                </tbody>
            </table>
        </div>
      </div>
  </form>
</div>
</body>
</html>
