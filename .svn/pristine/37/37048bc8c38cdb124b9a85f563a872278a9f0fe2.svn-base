<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>用户信息</title>
<%@ include file="/common/head.jsp"%>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
</head>

<body>
<div class="currloca">
<p>${auth.fullMenu}&nbsp;»&nbsp;<span>用户信息</span></p>
<div class="sitemap"><span id="add2custom"></span> <span id="showMap"><img
    onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航"
    src="../images/map.gif" /></span></div>
</div>
<div class="container">
<div class="itemtitle">
<h2>用户信息</h2>
</div>

<!-- 附加信息-->
<div id="message" class="message"><span style="font-weight: bold; font-size: 14px;"><!-- 提示：资源列表... --></span></div>

<!-- 列表区域-->

<div id="content" class="content input">
<div id="indiv" style="width: 100%; OVERFLOW-X: auto;">
<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
    <tbody>
        <tr>
            <th class="first" width="130">标准信息</th>
            <th class="last"></th>
        </tr>
        <tr>
            <td class="titd">登录名:</td>
            <td class="notd">${entity.username}</td>
        </tr>
        <tr>
            <td class="titd">用户名:</td>
            <td class="notd">${entity.realName}</td>
        </tr>
        <tr>
            <td class="titd">创建者:</td>
            <td class="notd">${entity.createBy}</td>
        </tr>
        <tr>
            <td class="titd">创建时间:</td>
            <td class="notd"><fmt:formatDate value="${entity.createTime}" pattern="yyyy-MM-dd HH:mm" /></td>
        </tr>
        <tr>
            <td class="titd">邮箱:</td>
            <td class="notd">${entity.email}</td>
        </tr>
        <tr>
            <td class="titd">电话:</td>
            <td class="notd">${entity.phone}</td>
        </tr>
        <tr>
            <td class="titd">所属组织:</td>
            <td class="notd">${entity.domain.label}</td>
        </tr>
        <tr>
            <td class="titd">用户角色:</td>
            <td class="notd"><c:forEach items="${entity.useRoles}" var="use">
									${use.name} <br>
            </c:forEach></td>
        </tr>
       

        <tr>
            <td class="titd">所属组织:</td>
            <td class="notd">${entity.domain.label}</td>
        </tr>
        <tr>
            <td class="titd">支配角色:</td>
            <td class="notd"><c:forEach items="${entity.grantRoles}" var="grant">
									${grant.name} <br>
            </c:forEach></td>
        </tr>
        <tr>
            <td class="titd">账户可用:</td>
            <td class="notd">${entity.enabled?"是":"否"}</td>
        </tr>
        <tr>
            <td class="titd">帐号过期:</td>
            <td class="notd">${entity.accountExpired?"过期":"使用"}</td>
        </tr>
        <tr>
            <td class="titd">帐号锁定:</td>
            <td class="notd">${entity.accountLocked?"锁定":"解锁"}</td>
        </tr>
        <tr>
            <td class="titd">证书过期:</td>
            <td class="notd">${entity.credentialsExpired?"过期":"使用"}</td>
        </tr>
        
        <tr>
            <th class="first" width="130"></th>
            <th class="last"><input class="button" type="button" value="返回"
                onclick="history.back();location.href=reload;" /></th>
        </tr>

    </tbody>
</table>
</div>

</div>
</div>

</body>
</html>
