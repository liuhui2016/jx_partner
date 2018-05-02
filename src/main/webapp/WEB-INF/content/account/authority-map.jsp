<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table id="maptbl" border="0" cellspacing="0"
	cellpadding="0">
	<tr>
		<c:forEach items="${allAuth}" var="a">
			<c:if test="${a.parent==null}">
				<td valign="top">
			</c:if>
			<c:if test="${a.menu}">
				<ul>
					<b>${a.formatLabel}</b>
					<c:forEach items="${a.allChildrenList}" var="b">
						<c:if test="${b.menu}">
							<c:if test="${b.resource!=null}">
							<li><a href="${ctx}${fn:replace(b.resource, '*', '')}${fn:contains(b.resource.resString, '?')?'&':'?'}authId=${b.id}">${b.formatLabel}</a></li>
							</c:if>
							<c:if test="${b.resource==null}">
							<li><a href="#">${b.formatLabel}</a></li>
							</c:if>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
			<c:if test="${a.parent==null}">
				</td>
			</c:if>
		</c:forEach>
	</tr>
</table>
