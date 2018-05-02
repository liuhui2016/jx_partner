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
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}&nbsp;»&nbsp;<span>详细信息</span></p>
  <div class="sitemap">
  	<span id="add2custom"></span>
    <span id="showMap"><img onclick="showMap();" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="../images/map.gif"/></span>
  </div>
</div>

<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>详细信息</h2></div>
  
  <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>
  
  <form id="mainForm"  action="dic!detail.action" method="post">
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
			<table class="tab_cont" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" colspan="12"></th>
				    </tr>
                    <s:if test="codeFlag">
                        <!-- 编码对照html开始 -->
                            <tr>
                                <td  class="right">编码:</td>
                                <td>${entity.code}</td>
                                <td  class="right">名称:</td>
                                <td>${entity.name}</td>
                            </tr>
                            <tr>
                                <td  class="right">附加一:</td>
                                <td>${entity.val1}</td>
                                <td  class="right">附加二:</td>
                                <td>${entity.val2}</td>
                            </tr>
                            <tr>
                                <td  class="right">描述:</td>
                                <td colspan="3">${entity.description}</td>
                            </tr>
                        
                            <!-- 子表记录 -->
                          
                            <tr>
                                <td colspan="12">
                                    <div id="content" class="content input">
                                    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
                                        <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
                                            <tbody id="oil_tank_table">
                                                <tr>
                                                  <th class="first" width="130">详细信息</th>
                                                  <th class="last" colspan="7"></th>
                                                </tr>
                                                <tr>
                                                    <td>原编码:</td>
                                                    <td>原名称:</td>
                                                    <td>标准编码1:</td>
                                                    <td>标准名称1:</td>
                                                    <td>标准编码2:</td>
                                                    <td>标准名称2:</td>
                                                </tr>
                                                <c:forEach items="${pageDicitem.result}" var="child" varStatus="st">
                                                    <tr>
                                                        <td>
                                                            <input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.code}"/>
                                                        </td>
                                                        
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.name}"/></td>
                                                        <td>
                                                            
                                                                <c:if test="${fn:startsWith(entity.code, 'entity_') }">
                                                                    <c:forEach  items="${types}" var="tt">
                                                                            <c:if test="${tt.label == child.val1}">
                                                                            <input type="text" readonly="readonly" class="middle" maxlength="255"value="${tt.value}"/>
                                                                            </c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                                <c:if test="${!fn:startsWith(entity.code, 'entity_') }">
                                                                   <input type="text" readonly="readonly" class="middle" maxlength="255" value="${ child.val1}"/>
                                                                </c:if>
                                                            
                                                        </td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val2}"/></td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val3}"/></td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val4}"/></td>
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
                        <input type="hidden" name="id" id="id" value="${entity.id}"/>
  					  	<input type="hidden" name="pageDicitem.pageNo" id="pageNo" value="${pageDicitem.pageNo}"/>
					 	<input type="hidden" name="pageDicitem.orderBy" id="orderBy" value="${pageDicitem.orderBy}"/>
						<input type="hidden" name="pageDicitem.order" id="order" value="${pageDicitem.order}"/>
						<input type="hidden" name="authId" id="authId" value="${authId}"/>
						<input type="hidden" name="pageDicitem.pageSize" id="pageSize" value="${pageDicitem.pageSize }" />
						<input type="hidden" name="pageDicitem.excelExp" id="excelExp" value="${pageDicitem.excelExp}" />
						<input type="hidden" name="codeFlag" id="codeFlag" value="${codeFlag}" />
                       <div id="pageDicitem" class="page">
  							<wlps:page page="${pageDicitem}" showPageSize="true" excelExp="false" />
  					  </div>

						
                    </s:if>
                    <s:else>
                        <!-- 数据字典、方式类别html开始 -->
                            <tr>
                                <td  class="right">编码:</td>
                                <td>${entity.code}</td>
                                <td  class="right">名称:</td>
                                <td>${entity.name}</td>
                            </tr>
                            <tr>
                                <td  class="right">附加一:</td>
                                <td>${entity.val1}</td>
                                <td  class="right">附加二:</td>
                                <td>${entity.val2}</td>
                            </tr>
                            <tr>
                                <td  class="right">附加三:</td>
                                <td>${entity.val3}</td>
                                <td  class="right">描述:</td>
                                <td>${entity.description}</td>
                            </tr>
                            
                            <!-- 子表记录 -->
                            <tr>
                                <td colspan="12">
                                    <div id="content" class="content input">
                                    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
                                        <table class="tab_cont" width="100%" cellspacing="0" cellpadding="0" border="0" align="left">
                                            <tbody id="oil_tank_table">
                                                <tr>
                                                  <th class="first" width="130">详细信息</th>
                                                  <th class="last" colspan="7"></th>
                                                </tr>
                                                <tr>
                                                    <td>编码:</td>
                                                    <td>名称:</td>
                                                    <td>附加一:</td>
                                                    <td>附加二:</td>
                                                    <td>附加三:</td>
                                                    <td>附加四:</td>
                                                    <td>描述:</td>
                                                </tr>
                                                <c:forEach items="${entity.dicitem}" var="child" varStatus="st">
                                                    <tr>
                                                        <td>
                                                            <input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.code}"/>
                                                        </td>
                                                        
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.name}"/></td>
                                                        <td>
                                                                <c:if test="${fn:startsWith(entity.code, 'entity_') }">
                                                                    <c:forEach  items="${types}" var="tt">
                                                                            <c:if test="${tt.label == child.val1}">
                                                                             <input type="text" readonly="readonly" class="middle" maxlength="255" value="${tt.value}" /></c:if>
                                                                    </c:forEach>
                                                                </c:if>
                                                                <c:if test="${!fn:startsWith(entity.code, 'entity_') }">
                                                                   <input type="text" readonly="readonly" class="middle" maxlength="255" value="${ child.val1}"/>
                                                                </c:if>
                                                            
                                                        </td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val2}"/></td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val3}"/></td>
                                                        <td><input type="text" readonly="readonly" class="middle" maxlength="255" value="${child.val4}"/></td>
                                                        <td><input type="text" readonly="readonly" class="middle" value="${child.description}"/></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                  </div>
                                </td>
                            </tr>
                        <!-- 数据字典、方式类别html结束 -->
                    </s:else>
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="12">
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
