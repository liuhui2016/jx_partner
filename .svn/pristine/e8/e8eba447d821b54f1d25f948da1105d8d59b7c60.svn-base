<%@ page contentType="text/javaScript;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp"%>
        d = new dTree('d');
        <c:if test="${param.id==null}">d.add(0,-1,"常用菜单");</c:if>
        <c:forEach items="${authList}" var="a">
        d.add(${a.id}
                ,<c:if test="${param.id==null}">0</c:if><c:if test="${param.id!=null}">${a.rootid}</c:if>
                ,"${a.label}" 
                <c:if test="${a.resString!=null}">
                ,"${ctx}${fn:replace(a.resString, "*", "")}${fn:contains(a.resString, "?")?'&':'?'}authId=${a.id}"
                ,"${a.description}","layout_center","menut()"
                </c:if>
                );
        </c:forEach>
        //alert(d);
        $('#tree')[0].innerHTML = d; 
        d.openAll();