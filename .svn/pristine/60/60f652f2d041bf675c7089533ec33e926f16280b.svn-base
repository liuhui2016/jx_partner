<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>管理</title>
    <%@ include file="/common/head.jsp" %>
    <script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
    <link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
    <script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
	<script src="${ctx}/js/jQuery/timepicker.js" type="text/javascript"></script>
	<script>
		function selectType(type){$('.selectType').hide(); $('#'+type).show(); }
        jQuery.validator.addMethod("userName", function(value, element) {   
            return this.optional(element) || /^[\u0391-\uFFE5\w]+$/.test(value);   
            }, "用户名只能包括中文字、英文字母、数字和下划线");         
		$(document).ready(function(){
			//聚焦第一个输入框
			$("#classname").focus();
			$("#inputForm").validate({
				rules: { 'name': { required: true, remote: "quartz!checkName.action?oldId=${id}"},
          				 'classname': {required: true,  remote: "quartz!checkClassname.action?oldId=${id}" }
				},
				messages: { 'name': { remote: "计划名已存在" },
							'classname': { remote: "classname已经存在" }
				}
			});
			//时间日期选择 定时发送
		
		    $('#startDate,#endDate').datepicker({
		    	showButtonPanel: true, duration: '', minDate: 0, showTime: true, constrainInput: true, time24h: true
		     });			

			//init
			$("#repeat").val('${repeat}');
			$("#millis").val('${millis}');
			$("#repeatInterval").val('${repeatInterval}');
			selectType("${cron?'cron':'simple'}");
		});
		
		function changeVal(){
			var cron = '${cron}';
			var repeat = '${repeat}';
			var repeatInterval = '${repeatInterval}';
			var cronStr = '${cronStr}';
			if($('#cron').val()!=cron || $('#repeat').val()!=repeat || $('#repeatInterval').val()!=repeatInterval || $('#cronStr').val()!=cronStr ){
				$('#change').val('true');
			}
			//alert($('#change').val());
			return true;
		}
		function oilautocompletem(){
			$("#typeid").autocomplete({
				minLength: 1,
				source:function(request, response) {
					$.post("quartz!getAcDataSource.action", {filter_LIKES_type_OR_shortName:request.term,type:"${type}"},response);
				}, 
				focus: function(event, ui) {
					$('#typeid').val(ui.item[2]);
					return false;
				}
			}).data( "autocomplete" )._renderItem = function( ul, item ) {
				return $( "<li></li>" )
				.data( "item.autocomplete", item )
				.append( "<a>" + item[2] + "</a>" )
				.appendTo( ul );
			};
		}
	</script>
</head>

<body>

<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增计划任务</span>':'&nbsp;»&nbsp;<span>修改计划任务</span>' }</p>
  <div class="sitemap">
    <span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增':'修改' }计划任务</h2></div>
    
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>

<form id="inputForm" action="quartz!save.action" method="post" onsubmit="return changeVal();">
      <div id="content" class="content input">
        <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
        </div>
        
<input type="hidden" name="id" value="${id}" />
<input type="hidden" name="authId" value="${authId}"/>
<input type="hidden" id="change" name="change" value="false" />
<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
    <tr>
        <th class="first" width="130">标准信息</th>
        <th class="last"></th>
    </tr>
    <tr>
        <td class="right">任务描述:</td>
        <td><input type="text" id="password" name="description" size="40" value="${description}" /></td>
    </tr>    
	<tr>
		<td class="right" width="80"><span class="red">*</span>计划名:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td><input type="text" name="name" size="40" id="name" value="${name}" ${name!=null?'readonly="readonly"':''} tip="计划任务名称,quartz运行池的唯一标识,英文数字或者下划线"/></td>
	</tr>
	<tr>
		<td class="right"><span class="red">*</span>Classname:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td><input type="text" name="classname" size="40" value="${classname}"  tip="执行计划的类名，包括完整包路径,eg.com.game.modules.schedule.job.ExampleJob"/></td>
	</tr>
    <tr>
        <td class="right">附加信息:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
        <td><input type="text" name="jobdata" size="40" value="${jobdata}"  tip="执行计划的附加信息"/></td>
    </tr>
	<tr>
		<td class="right">状态:</td>
		<td>
		<input type="radio" id="pause" name="pause" value="true" <c:if test="${pause}">checked="checked"</c:if>/>关闭   
		<input type="radio" id="pause" name="pause" value="false" <c:if test="${!pause}">checked="checked"</c:if>/>开启
		</td>
	</tr>
	<tr>
		<td class="right">类别:</td>
		
		<td>
		<input type="text" id="password" name="type" size="40" value="${type}" /></td>
	</tr>
	<tr>
		<td class="right">开始时间:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td>
		    <input tip="计划开启运行时间" type="text" name="startDate" id="startDate" value="<fmt:formatDate value="${startDate}" pattern="yyyy-MM-dd kk:mm"/>" size="16"/>
		</td>
	</tr>
	<tr>
		<td class="right">结束时间:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td><input tip="计划结束时间" type="text" name="endDate" id="endDate" value="<fmt:formatDate value="${endDate}" pattern="yyyy-MM-dd kk:mm"/>" size="16"/></td>
	</tr>
    <tr>
        <th class="first" width="130">计划执行方式</th>
        <th class="last"></th>
    </tr>    
	<tr>
		<td class="right">Job类型:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td>
		<input tip="计划执行方式
        1。SimpleTrigger：这是一个非常简单的类，我们可以定义作业的触发时间，并选择性的设定重复间隔和重复次数。
        2。CronTrigger：这个触发器的功能比较强大，而且非常灵活，类似Unix系统Cron执行方式
        " type="radio" onclick="selectType('simple')"  name="cron" ${cron?'':'checked="checked"' } value="false" />SimpleTrigger &nbsp;
		<input type="radio" onclick="selectType('cron')" name="cron" ${cron?'checked="checked"':'' } value="true" />CronTrigger		
		</td>
	</tr>
	<tbody id="simple" class="selectType">
	<tr>
		<td class="right">重复选择:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td>
		<select tip="重复执行的方式" id="repeat" name="repeat" >
			<option selected="selected" value="-1">Run continuously</option><option value="0">Run once</option><option value="1">Repeat 1</option><option value="2">Repeat 2</option><option value="3">Repeat 3</option><option value="4">Repeat 4</option><option value="5">Repeat 5</option><option value="10">Repeat 10</option><option value="20">Repeat 20</option>
		</select>		
		</td>
	</tr>
    <tr>
        <td class="right">间隔时间:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
        <td>
        <select tip="重复执行时间间隔" id="repeatInterval" name="repeatInterval" >
            <option value="60000">1 minute</option><option value="120000">2 minutes</option><option value="300000">5 minutes</option><option value="600000">10 minutes</option><option value="900000">15 minutes</option><option value="1800000">30 minutes</option><option selected="selected" value="3600000">1 hour</option><option value="7200000">2 hours</option><option value="10800000">3 hours</option><option value="21600000">6 hours</option><option value="43200000">12 hours</option><option value="86400000">24 hours</option>
        </select>
        </td>
    </tr>
    <tr>
        <td class="right">延迟启动(秒):<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
        <td>
        <select tip="开机延迟启动时间" id="millis" name="millis" >
            <option value="60000">1 minute</option><option value="120000">2 minutes</option><option value="300000">5 minutes</option><option value="600000">10 minutes</option><option value="900000">15 minutes</option><option value="1800000">30 minutes</option><option selected="selected" value="3600000">1 hour</option><option value="7200000">2 hours</option><option value="10800000">3 hours</option><option value="21600000">6 hours</option><option value="43200000">12 hours</option><option value="86400000">24 hours</option>
        </select>
        </td>
    </tr>
	</tbody>
	<tbody id="cron" class="selectType" style="display: none;">
	<tr>
		<td class="right">Cron串:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
		<td><input tip="一个Cron-表达式是一个由六至七个字段组成由空格分隔的字符串，其中6个字段是必须的而一个是可选的
        " type="text" name="cronStr" id="cronStr" size="40" value="${cronStr}" /></td>
	</tr>	
	</tbody>
	<tr>
	    <td class="right"></td>
		<td align="left">
			<input type="submit" value="提交" />&nbsp; 
			<input type="button" value="取消" onclick="history.back()"/>
		</td>
	</tr>
</table>
</div>
</form>
</div>

</body>
</html>