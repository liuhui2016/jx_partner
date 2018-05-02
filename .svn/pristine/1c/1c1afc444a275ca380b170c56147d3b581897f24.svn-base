<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<% 
java.util.Date date = new java.util.Date();
request.setAttribute("date",date);
%>
<security:authorize ifAnyGranted="ROLE_ANONYMOUS"><c:redirect url="/login.action"/></security:authorize>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>客户查询 后台 [登录用户:<security:authentication property="principal.username"/>]</title>
<%@ include file="/common/head.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/css/layout.css" />
<script type="text/javascript" src="${ctx}/js/jQuery/jquery.layout-latest.js"></script>
<script type="text/javascript" src="${ctx}/js/dtree.js"></script>
<link type="text/css" rel="stylesheet" href="${ctx}/css/dtree.css" />
<style type="text/css">
<!--
body {margin:0;padding: 0;}
td{font-size: 12px;}
.STYLE1 {font-size: 12px;color: #000;}
.STYLE3 {font-size: 12px;color: #033d61;}
a {text-decoration: none;}
.ui-layout-north ,.ui-layout-south {overflow:hidden;}
.pane, .ui-layout-pane {border: none;}
img{border: 0;}
.menu_title SPAN {FONT-WEIGHT: bold; LEFT: 3px; COLOR: #ffffff; POSITION: relative; TOP: 2px }
.menu_title2 SPAN {FONT-WEIGHT: bold; LEFT: 3px; COLOR: #FFCC00; POSITION: relative; TOP: 2px}
.treeloading{position:absolute;left:45%;top:40%;}
.titl_class2{height:20px;overflow:hidden;padding-left:2px;white-space:nowrap;}
.titl_class a{color: #000;}
.titl_class2 span{margin: 0 2px;}
/*.titl_class2 span{line-height:1.8em;margin:0 2px;padding:0 12px;float:left;height:20px;text-align:center;word-spacing:-0.4em;}*/
.titl_class_selected{font-weight:bold;color:#2D7BAC;}
.nav_class{color: #000;line-height: 20px;}
.nav_class img{margin:0 2px 1px;}
-->
</style>
<script type="text/javascript">
    var corelayout,innerLayout;
    $(function(){
        corelayout = $("body").layout(corelayout_settings);
        corelayout.loadCookie("west_layout");
        
        var westSelector = "body > .ui-layout-west"; // outer-west pane
        $("<span></span>").addClass("pin-button").prependTo( westSelector );
        corelayout.addPinBtn( westSelector +" .pin-button", "west");
        //$("<span></span>").attr("id", "west-closer" ).prependTo( westSelector );
        //corelayout.addCloseBtn("#west-closer", "west");
        menu(null);

        /******************/
        $(".titl_class2 span").click(function(){
            $(".titl_class2 span").removeClass("titl_class_selected");
            $(this).addClass("titl_class_selected");
        });
        
    });
    
    
    
    function menu(mid){
        $.getScript("main!menu.action?t="+new Date().getTime()+(mid!=null?"&id="+mid:""));
    }

    function exitsystem(){
        if (confirm('您确定要退出系统吗？'))
          window.parent.location="${ctx}/securityLogout.action"; 
    }
    
    function menuTo(authId,res){
        $("#tree").empty().html("<img src='${ctx}/images/loading.gif' class='treeloading' alt='loading' /> ");
        var cook =$.cookie("csd");
        var date = new Date();  
        date.setTime(date.getTime() - (1 * 24 * 60 * 60 * 1000)); 
        if(cook!=null){
            $.cookie("csd",null,{expires:date});
        }
        window.frames["layout_center"].location.href=ctx+res.replace("*","")+'?_='+new Date().getTime();
        menu(authId);
        corelayout.open('west','none','mouseover');
         
    }


    /*清除cookie*/
    function delCookie(cname){
        var cook =$.cookie(cname);
        if(cook!=null){
            $.cookie(cname,null,{ path: '/' });
        }
    }

    /* dtree.js中修改
     * 左侧树节点click,但不跳转页面
     */
    function menut(id){
        delCookie('show_hidden');
    }
    
    // 框架风格设置
    var corelayout_settings = {
        name: "corelayout" 
    ,   defaults: {
            size:                   "auto"
        ,   minSize:                5
        ,   paneClass:              "pane"      // default = 'ui-layout-pane'
        ,   resizerClass:           "resizer"   // default = 'ui-layout-resizer'
        ,   togglerClass:           "toggler"   // default = 'ui-layout-toggler'
        ,   buttonClass:            "button"    // default = 'ui-layout-button'
        ,   contentIgnoreSelector:  "span"      // 'paneSelector' for content to 'ignore' when measuring room for content
        ,   togglerLength_open:     35          // WIDTH of toggler on north/south edges - HEIGHT on east/west edges
        ,   togglerLength_closed:   35          // "100%" OR -1 = full height
        ,   hideTogglerOnSlide:     true        // hide the toggler when pane is 'slid open'
        ,   togglerTip_open:        "Close This Pane"
        ,   togglerTip_closed:      "Open This Pane"
        ,   resizerTip:             "Resize This Pane"
        //  effect defaults - overridden on some panes
        ,   fxName:                 "slide"     // none, slide, drop, scale
        ,   fxSpeed_open:           1000
        ,   fxSpeed_close:          2500
        ,   fxSettings_open:        { easing: "easeInQuint" }
        ,   fxSettings_close:       { easing: "easeOutQuint" }
    }
    ,   
        north: {
            size:                   "auto"      
        ,   closable:               false       
        ,   resizeWhileDragging:    true
        ,   spacing_open:           0           // cosmetic spacing
        ,   paneClass:              "pane"
        }
    , south: {
          size:                   "auto"
      ,   border:                 0
      ,   closable:               false   
      ,   resizeWhileDragging:    true
      ,   spacing_open:           0
      ,   paneClass:              "pane"
      }
    ,   west: {
            size:                   167
        ,   buttonClass:            "button"
        ,   spacing_closed:         21          // wider space when closed
        ,   togglerLength_closed:   21          // make toggler 'square' - 21x21
        ,   togglerAlign_closed:    "top"       // align to top of resizer
        ,   togglerLength_open:     0           // NONE - using custom togglers INSIDE west-pane
        ,   togglerTip_open:        "Close West Pane"
        ,   togglerTip_closed:      "Open West Pane"
        ,   resizerTip_open:        "Resize West Pane"
        ,   slideTrigger_open:      "mouseover"     // default
        ,   initClosed:             false
        ,   fxName:                 "drop" // none, slide, drop, scale
        ,   fxSpeed_open:           500
        ,   fxSpeed_close:          1000
        //, fxSettings_open:        { easing: "easeOutBounce" }
        //, onsizecontent_start:            function(){$("#layout_left_div").append(window.frames["layout_left"].document.body.innerHTML);}
        //, onshow:                 function(){ $("#layout_left_div").html(window.frames["layout_left"].document.body.innerHTML); }
        ,   closable:               true        // pane can open & close
        ,   resizable:              true        // when open, pane can be resized 
        ,   slidable:               true        // when closed, pane can 'slide open' over other panes - closes on mouse-out
        ,   initClosed:             false       // true = init pane as 'closed'
        ,   initHidden:             false
        ,   maskIframesOnResize:    true
        }
    ,   center: {
            size:                   "auto" 
        ,   minWidth:               800
        ,   maskIframesOnResize:    true
        
        }
    ,useStateCookie:true
    ,   cookie: {
        name:                   "west_layout"           // If not specified, will use Layout.name, else just "Layout"
    ,   autoSave:               true        // Save a state cookie when page exits?
    ,   autoLoad:               true        // Load the state cookie when Layout inits?
    //  Cookie Options
    ,   domain:                 ""
    ,   path:                   ""
    ,   expires:                ""          // 'days' to keep cookie - leave blank for 'session cookie'
    ,   secure:                 false
    //  List of options to save in the cookie - must be pane-specific
    ,   keys:                   "west.size,"+
                                "west.isClosed,"+
                                "west.isHidden"
    }
    ,onresizeall_start:function(){corelayout.deleteCookie();}
};
    $(document).ready(function () {
    	var value = "<security:authentication property="principal.username"/>";
    	var Json = JSON.stringify({"Json":value});
    	$.ajax({ //一个Ajax过程
    	       type: "post", //以post方式与后台沟通
    	       url:'${pageContext.request.contextPath}/smvc/filter/warning.v',
    	       contentType:'application/json;charset=utf-8',
    	       data:Json,
    	       success: function(data){
    				$("#spanId").text(data);
    	       }
    	 })
    });  
</script>
</head>
<body>

<!-- 顶部区域 -->
<div class="ui-layout-north">
<table width="100%" cellspacing="0" cellpadding="0" border="0">
    <tbody>
        <tr>
            <td height="11" background="${ctx}/images/web/main_03.gif">
            <img width="104" height="11" src="${ctx}/images/web/main_01.gif" />
            </td>
        </tr>
    </tbody>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0" background="${ctx}/images/web/main_07.gif">
  <tr>
    <td width="282" background="${ctx}/images/web/main_05.gif">&nbsp;</td>
    <td height="52" style="background: url('${ctx}/images/web/main_08.gif') no-repeat scroll right 0 transparent;white-space: nowrap;">
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="" height="26" style="padding: 5px 4px;">
            <span style="font-size: 18px;color: #000000;white-space:nowrap; ">
              <table width="100%" cellspacing="0" cellpadding="0" border="0">
              <tbody>
              <tr>
                <td class="titl_class" height="28">
                  <div class="titl_class2">
                    <span>
                        <a href="javascript:void(0);" style="cursor: pointer;" onclick="menuTo(null,'/main/right.action')">
                        <span class="nav_class"><img src="${ctx}/images/main_12_new.gif" width="14" height="14" align="absmiddle" />首页</span>
                        </a>
                    </span>                  
                    <c:forEach items="${auths}" var="auth">
                    <span>
                        <a href="javascript:void(0);" style="cursor: pointer;" onclick="menuTo('${auth.id}','${auth.resString}')">
                            <span class="nav_class"><c:if test="${auth.menuImg!=null}"><img src="${ctx}/images/menu/${auth.menuImg}" align="absmiddle" /></c:if>${auth.label}
                            </span>
                        </a>
                    </span>
                    </c:forEach>
                    <span>
                        <a href="${ctx}/account/user!modify.action" target="layout_center"  style="cursor: pointer;">
                        <span class="nav_class"><img src="${ctx}/images/main_22_new.gif" width="14" height="14" align="absmiddle" />个人密码修改</span>
                        </a>
                    </span>
                    <span>
                        <a href="javascript:layout_center.location.reload()"  style="cursor: pointer;">
                        <span class="nav_class"><img src="${ctx}/images/main_18_new.gif" width="14" height="14" align="absmiddle" />刷新</span>
                        </a>
                    </span>
                    <span>
                        <a href="javascript:exitsystem()"  style="cursor: pointer;">
                        <span class="nav_class"><img src="${ctx}/images/main_20_new.gif" width="14" height="14" align="absmiddle" />退出</span>
                        </a>
                    </span>
                    </div>
                </td>
              </tr>
            </tbody>
            </table>
             </span>
            </td>
          </tr>
        </table>
    </td>
    <td width="283" valign="bottom" nowrap="nowrap" background="${ctx}/images/web/main_09.gif" style="background-repeat: no-repeat;">
        <div align="right" style="padding: 0 4px 6px;"><span class="STYLE1"><security:authentication property="principal.realName"/> [ <security:authentication property="principal.domain.label"/> ]<br/>
        <fmt:formatDate value="${showTime}" pattern="yyyy年MM月dd日  EEEE"/></span></div>
    </td>
  </tr>
</table>
</div>

<!-- 左侧菜单区域 -->
<div class="ui-layout-west" style="background: url('${ctx}/images/web/main_16.gif') repeat-y scroll left 0 #ffffff;padding-left: 4px;">
    <table width="100%" border="0" cellpadding="0" cellspacing="0" style="background: #ffffff;" >
      <tr>
        <td height="22px" style="background: url('${ctx}/images/web/main_11.gif') repeat-x scroll left 0 transparent;"> </td>
      </tr>
      <tr>
        <td valign="top">
            <table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#ffffff">
              <tr align="left">
                <td style="padding-left: 8px;">
                <div id="tree" style="text-align: left"><img src="${ctx}/images/loading.gif" class='treeloading' alt='loading' /> </div>
                </td>
              </tr>
            </table>
            </td>
      </tr>
    </table>
</div>

<!-- 内容区域 -->
<div class="ui-layout-center" style="overflow: hidden;background: url('${ctx}/images/web/main_20.gif') repeat-y scroll right 0 transparent;padding-right:2px">
    <iframe scrolling="auto" class="ui-layout-center" name="layout_center" height="100%" width="100%" frameborder="0" src="${ctx}/main/right.action?_=<fmt:formatDate value="${date}" pattern="yyyyMMddHHmmssSSS"/>"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
</div>


<!-- 底部区域 -->
<div class="ui-layout-south">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="23" background="${ctx}/images/web/main_25.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="181" height="23" background="${ctx}/images/web/main_24.gif">&nbsp;</td>
        <td><div align="right" class="STYLE1">今天是：<fmt:formatDate value="${date}" pattern="yyyy年MM月dd日  EEEEE"/>  您有<span id="spanId" style="font-size:20px;color:red;font-weight:bold"></span>条滤芯更换信息需要处理</div></td>
        <td width="25"><img src="${ctx}/images/web/main_27.gif" width="25" height="23" /></td>
      </tr>
    </table></td>
  </tr>
</table>
</div>

</body>
</html>
