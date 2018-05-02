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
	<script type="text/javascript">
	
	$(document).ready(function() {
			var tr_count = $('#oil_tank_table').children().length - 2;
			
			//聚焦第一个输入框
			$("#code").focus();
			//dynamic Table
			$('#table_tr_add').click(function(){
				
				//1:创建一个tr
				var tag = tr_count++;
				<s:if test="codeFlag">
                // 编码对照动态tr
                var tr = '<tr name="delTr">'+
                '<td><input type="text" id="dicitems['+tag+'].code" class="checkCode" name="dicitems['+tag+'].code" class="middle" maxlength="255" /></td>'+
                '<td><input type="text" id="dicitems['+tag+'].name" name="dicitems['+tag+'].name" class="middle" maxlength="255"/></td>'+
                '<td><c:if test="${fn:startsWith(code, \"entity_\") }"><select name="dicitems['+tag+'].val1"><c:forEach items="${types}" var="tt"><option value="${tt.label}" <c:if test="${tt.label == child.val1}">selected="selected"</c:if>>${tt.value}</option></c:forEach></select></c:if><c:if test="${!fn:startsWith(code, \"entity_\") }"><input type="text" class="middle" id="dicitems['+tag+'].val1" name="dicitems['+tag+'].val1"  maxlength="255" value="${child.val1}" /></c:if></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val2" name="dicitems['+tag+'].val2" class="middle" maxlength="255"/></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val3" name="dicitems['+tag+'].val3" class="middle" maxlength="255"/></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val4" name="dicitems['+tag+'].val4" class="middle" maxlength="255"/></td>'+
                '<td><input type="button" value="删除" name="table_delete"/></td>'+
                '</tr>';
                </s:if>
                <s:else>
                // 数据字典、方式类别表动态tr
                var tr = '<tr name="delTr">'+
                '<td><input type="text" id="dicitems['+tag+'].code" class="checkCode" name="dicitems['+tag+'].code" class="middle" maxlength="255" /></td>'+
                '<td><input type="text" id="dicitems['+tag+'].name" name="dicitems['+tag+'].name" class="middle" maxlength="255"/></td>'+
                '<td><c:if test="${fn:startsWith(code, \"entity_\") }"><select name="dicitems['+tag+'].val1"><c:forEach items="${types}" var="tt"><option value="${tt.label}" <c:if test="${tt.label == child.val1}">selected="selected"</c:if>>${tt.value}</option></c:forEach></select></c:if><c:if test="${!fn:startsWith(code, \"entity_\") }"><input type="text" class="middle" id="dicitems['+tag+'].val1" name="dicitems['+tag+'].val1"  maxlength="255" value="${child.val1}" /></c:if></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val2" name="dicitems['+tag+'].val2" class="middle" maxlength="255"/></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val3" name="dicitems['+tag+'].val3" class="middle" maxlength="255"/></td>'+
                '<td><input type="text" id="dicitems['+tag+'].val4" name="dicitems['+tag+'].val4" class="middle" maxlength="255"/></td>'+
                '<td><input type="text" id="dicitems['+tag+'].description" name="dicitems['+tag+'].description" class="middle" maxlength="255"/></td>'+
                '<td><input type="button" value="删除" name="table_delete"/></td>'+
                '</tr>';
                </s:else>
				var newTr = $(tr);
				//2:把tr添加到table里面
				$('#oil_tank_table').append(newTr);
				
				$('*[name="table_delete"]').click(function(event){
					$.each($('*[name="table_delete"]'),function(index,v){
						if(v==event.target){
							var delTr = $('*[name="delTr"]:eq('+index+')');
							delTr.remove();
						}
					});
				});
				$("input[name*='dicitems["+tag+"].code']").rules("add", {required:true,isRepeat: true});
				$("input[name*='dicitems["+tag+"].name']").rules("add", {required:true});
				<c:if test="${codeFlag}">
				$("input[name*='dicitems["+i+"].val1']").rules("add", {required:true});
				$("input[name*='dicitems["+i+"].val2']").rules("add", {required:true});
                </c:if>
			});

			$('*[name="table_delete"]').click(function(event){
				$.each($('*[name="table_delete"]'),function(index,v){
					if(v==event.target){
						var delTr = $(event.target).closest("tr");
						var tid = delTr.children().first().children("input:hidden").val();
						$.ajax({
						   type: "POST",
						   url: "dic!deleteDicItem.action?oil="+tid+"&code=${code}",
						   cache: false,
						   success: function(msg){
						   }
						});
						delTr.remove();
					}
				});
			});
			
			//为inputForm注册validate函数
			$.validator.addMethod("isRepeat",function(value,element,params){
				var flag = true;
				$(".checkCode").each(function(index,obj){
					if(obj.id !== element.id){
						if(obj.value === element.value){
							flag = false;
						}
					}
				});
				return flag;
			},$.format("编码不能重复！！！"));
			$.validator.addMethod("isEntityPre",function(value,element,params){
				var flag = false;
                if(value.toLowerCase().indexOf(params) === 0){
                    flag = true;
                };
				return flag;
			},$.format("编码必须以{}开头！！！"));
			$("#inputForm").validate({
				rules: {
					"code": {
				        <c:if test="${flag}">
                            isEntityPre:"entity_",
                        </c:if>
				        <c:if test="${codeFlag}">
                            isEntityPre:"code_",
                        </c:if>
						required: true,
						maxlength: 255,
						remote: "dic!checkCode.action?oldId=" + '${id}'
					}
					,"name": {
						required: true
					}
				},
				messages: {
					"code": {
    					<c:if test="${flag}">
                            isEntityPre:"编码必须以\"entity_\"开头",
                        </c:if>
        		        <c:if test="${codeFlag}">
        		        isEntityPre:"编码必须以\"code_\"开头",
                        </c:if>
						required: "请输入大类编码",
						remote: "编码已存在！",
	                    maxlength: "请输入一个长度最多是255的字符！"
					}
				}
			});

			for(var i=0;i<$(".dicitem_id").length;i++){
				$("input[name*='dicitems["+i+"].code']").rules("add", {required:true,isRepeat: true});
				$("input[name*='dicitems["+i+"].name']").rules("add", {required:true});
				<c:if test="${codeFlag}">
				$("input[name*='dicitems["+i+"].val1']").rules("add", {required:true});
				$("input[name*='dicitems["+i+"].val2']").rules("add", {required:true});
                </c:if>
			}
		});

	 	 function  save(){
	 		document.getElementById("nextPage").value=false;
	 		document.forms[0].action="dic!save.action";
			document.forms[0].submit();
		 }
	</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>${id==null?'新增类别':'修改类别' }</span></p>
  <div class="sitemap">
    <span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增类别':'修改类别' }</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="mainForm" action="dic!save.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<input type="hidden" name="flag" value="${flag}"/>
			<input type="hidden" name="codeFlag" value="${codeFlag}"/>
            <input type="hidden" name="diccode" value="${diccode}"/>
			<input type="hidden" name="nextPage" id="nextPage" value="true" />
			<table class="tab_cont" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" colspan="3"></th>
				    </tr>
                    <s:if test="codeFlag">
                        <!-- 编码对照html开始 -->
                        <tr>
                            <td  class="right"><span class="red">*</span>编码:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" id="code" tip="编码不能为空，切不可重复！！！" class="middle" name="code" maxlength="255" value="${code}" ${id != null? 'readonly="readonly"':"" }/></td>
                            <td  class="right"><span class="red">*</span>名称:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" id="name" tip="名称不能为空！！！" class="middle" name="name" maxlength="255" value="${name}"/></td>
                        </tr>
                        <tr>
                            <td  class="right">附加一:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input tip="针对数据实体定义，表示数据库表名" type="text" id="val1" class="middle" name="val1" maxlength="255" value="${val1}"/></td>
                            <td  class="right">附加二:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input tip="标记基础数据basedata Or 业务数据business" type="text" id="val2" class="middle" name="val2" maxlength="255" value="${val2}"/></td>
                        </tr>
                        <tr>
                            <td  class="right">描述:</td>
                            <td colspan="3">
                                <textarea rows="6" cols="60" id="description" class="middle" name="description">${description}</textarea>
                            </td>
                        </tr>
                        
                        <!-- 子表记录 -->
                        <tr>
                            <td colspan="4">
                                <div id="content" class="content">
                                <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
                                    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
                                        <tbody id="oil_tank_table">
                                            <tr>
                                              <th class="first" width="130">详细信息</th>
                                              <th class="last" colspan="6"><input type="button" value="增加一行" id="table_tr_add"/>
                                              <c:if test="${id!=null}">
														<input class="button" type="button" value="导入" onclick="opr_input('dic!importExcle.action?flag=${flag}&id=${entity.id }',false,'${authId}');"/>
											  </th>
											 </c:if>
                                            </tr>
                                            <tr>
                                                <td><span class="red">*</span>原编码:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                                                <td>
                                                    <span tip="源编码不能为空，切不可重复！！！"></span>
                                                    <span class="red">*</span>原名称:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="原名称不能为空"></span><span class="red">*</span>标准编码1:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="标准编码1不能为空"></span><span class="red">*</span>标准名称1:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                 <td>
                                                    <span tip="标准名称1不能为空"></span><span class="red">*</span>标准编码2:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="标准编码2不能为空"></span><span class="red">*</span>标准名称2:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td><span tip="标准名称2不能为空"></span>操作</td>
                                            </tr>
                                            <c:forEach items="${pageDicitem.result}" var="child" varStatus="st">
                                                <tr name="delTr">
                                                    <td>
                                                        <input type="hidden" class="dicitem_id" id="dic${st.index}.id" name="dicitems[${st.index}].id"  value="${child.id}"/>
                                                        <input type="text" class="middle checkCode" id="code${st.index}" name="dicitems[${st.index}].code"  maxlength="255" value="${child.code}"/>
                                                    </td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].name" name="dicitems[${st.index}].name"  maxlength="255" value="${child.name}"/></td>
                                                    <td>
                                                        <c:if test="${fn:startsWith(code, 'entity_') }">
                                                            <select name="dicitems[${st.index}].val1">
                                                                <c:forEach  items="${types}" var="tt">
                                                                    <option value="${tt.label}" 
                                                                        <c:if test="${tt.label == child.val1}">selected="selected"</c:if>
                                                                    >${tt.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${!fn:startsWith(code, 'entity_') }">
                                                            <input type="text" class="middle" id="dicitems[${st.index}].val1" name="dicitems[${st.index}].val1"  maxlength="255" value="${child.val1}" />
                                                        </c:if>
                                                    </td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val2" name="dicitems[${st.index}].val2"  maxlength="255" value="${child.val2}"/></td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val3" name="dicitems[${st.index}].val3"  maxlength="255" value="${child.val3}"/></td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val4" name="dicitems[${st.index}].val4"  maxlength="255" value="${child.val4}"/></td>
                                                    <td><input type="button" value="删除" name="table_delete"/></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                              </div>
                            </td>
                        </tr>
                        <!-- 编码对照html结束 -->
                        <!-- 分页区域-->
  					  	<input type="hidden" name="pageDicitem.pageNo" id="pageNo" value="${pageDicitem.pageNo}"/>
					 	<input type="hidden" name="pageDicitem.orderBy" id="orderBy" value="${pageDicitem.orderBy}"/>
						<input type="hidden" name="pageDicitem.order" id="order" value="${pageDicitem.order}"/>
						<input type="hidden" name="pageDicitem.pageSize" id="pageSize" value="10" />
						<input type="hidden" name="pageDicitem.excelExp" id="excelExp" value="${pageDicitem.excelExp}" />
                       <div id="pageDicitem" class="page">
  							<wlps:page page="${pageDicitem}" showPageSize="true" excelExp="false" />
  					  </div>
                        
                    </s:if>
                    <s:else>
                        <!-- 数据字典、方式类别html开始 -->
                        <tr>
                            <td  class="right"><span class="red">*</span>编码:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" id="code" tip="编码不能为空，切不可重复！！！" class="middle" name="code" maxlength="255" value="${code}" ${id != null? 'readonly="readonly"':"" }/></td>
                            <td  class="right"><span class="red">*</span>名称:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input type="text" id="name" tip="名称不能为空！！！" class="middle" name="name" maxlength="255" value="${name}"/></td>
                        </tr>
                        <tr>
                            <td  class="right">附加一:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input tip="针对数据实体定义，表示数据库表名" type="text" id="val1" class="middle" name="val1" maxlength="255" value="${val1}"/></td>
                            <td  class="right">附加二:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td><input tip="标记基础数据basedata Or 业务数据business" type="text" id="val2" class="middle" name="val2" maxlength="255" value="${val2}"/></td>
                        </tr>
                        <tr>
                            <td  class="right">附加三:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                            <td colspan="3"><input tip="针对数据实体定义，表示是否支持更新 yes" type="text" id="val3" class="middle" name="val3" maxlength="255" value="${val3}"/></td>
                        </tr>
                        <tr>
                            <td  class="right">描述:</td>
                            <td colspan="3">
                                <textarea rows="6" cols="60" id="description" class="middle" name="description">${description}</textarea>
                            </td>
                        </tr>
                        
                        <!-- 子表记录 -->
                        <tr>
                            <td colspan="4">
                                <div id="content" class="content">
                                <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
                                    <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
                                        <tbody id="oil_tank_table">
                                            <tr>
                                              <th class="first" width="130">详细信息</th>
                                              <th class="last" colspan="7"><input type="button" value="增加一行" id="table_tr_add"/></th>
                                              
                                            </tr>
                                            <tr>
                                                <td><span class="red">*</span>编码:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/></td>
                                                <td>
                                                    <span tip="编码不能为空，切不可重复！！！"></span>
                                                    <span class="red">*</span>名称:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="名称不能为空！！！"></span>附加一:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="针对数据实体，标识字段类型：S(String.class), I(Integer.class), L(Long.class), N(Double.class), D(Date.class), B(Boolean.class);"></span>附加二:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="针对业务数据：yes 标记为组合唯一键; no 标记为非组合唯一键，为空也可; hidden 针对自动代码生成而言，表示在jsp页面生成时，过滤不显示"></span>附加三:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="针对数据实体+自动代码生成而言，表示在jsp页面自动创建 domain 省公司 pipeline 管道 factory 炼厂 tostation 到站 flow 流向 storagedq 大区油库 stationto 发站 storage 油库 supplier 供货单位 oil 油品 dic dic下拉 对应的vla4为dic code"></span>附加四:<img class="tip" src="../images/s_info.gif" alt="" align="absmiddle"/>
                                                </td>
                                                <td>
                                                    <span tip="针对数据实体+自动代码生成而言，与val3同时出现 1、表示自动下拉对应的隐藏表单的name 2、dic下拉 对应的vla4为dic code"></span>描述:
                                                </td>
                                                <td>操作</td>
                                            </tr>
                                            <c:forEach items="${dicitem}" var="child" varStatus="st">
                                                <tr name="delTr">
                                                    <td>
                                                        <input type="hidden" class="dicitem_id" id="dic${st.index}.id" name="dicitems[${st.index}].id"  value="${child.id}"/>
                                                        <input type="text" class="middle checkCode" id="code${st.index}" name="dicitems[${st.index}].code"  maxlength="255" value="${child.code}"/>
                                                    </td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].name" name="dicitems[${st.index}].name"  maxlength="255" value="${child.name}"/></td>
                                                    <td>
                                                        <c:if test="${fn:startsWith(code, 'entity_') }">
                                                            <select name="dicitems[${st.index}].val1">
                                                                <c:forEach  items="${types}" var="tt">
                                                                    <option value="${tt.label}" 
                                                                        <c:if test="${tt.label == child.val1}">selected="selected"</c:if>
                                                                    >${tt.value}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </c:if>
                                                        <c:if test="${!fn:startsWith(code, 'entity_') }">
                                                            <input type="text" class="middle" id="dicitems[${st.index}].val1" name="dicitems[${st.index}].val1"  maxlength="255" value="${child.val1}" />
                                                        </c:if>
                                                    </td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val2" name="dicitems[${st.index}].val2"  maxlength="255" value="${child.val2}"/></td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val3" name="dicitems[${st.index}].val3"  maxlength="255" value="${child.val3}"/></td>
                                                    <td><input type="text" class="middle" id="dicitems[${st.index}].val4" name="dicitems[${st.index}].val4"  maxlength="255" value="${child.val4}"/></td>
                                                    <td><input type="text" class="middle" id="description" name="dicitems[${st.index}].description"  maxlength="255" value="${child.description}"/></td>
                                                    <td><input type="button" value="删除" name="table_delete"/></td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                              </div>
                            </td>
                        </tr>
                        <!-- 数据字典、方式类别html结束 -->
                        <input type="hidden" name="nextPage" id="nextPage" value="false" />
                    </s:else>
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="3">
							<security:authorize ifAnyGranted="A_base_DIC_add">
								<input class="button" type="submit" value="提交" onclick="save();"/>&nbsp;
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
