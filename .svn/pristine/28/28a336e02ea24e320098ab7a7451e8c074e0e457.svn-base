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
<title>Center</title>
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
a {text-decoration: none; color: #777; }
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
.ele{
	float: left; 
	width: 90px;  
	height: 100%; 
	font-size: 14px; 
	padding-top: 12px;
}
.top{
	float: right; 
	width: 300px;
	height: 100%; 
}
a:hover, a:focus {
    color: #FFF;
    background-color: #090909;
}
</style>
<script type="text/javascript">
    var corelayout,innerLayout;
    $(function(){
        corelayout = $("body").layout(corelayout_settings);
        corelayout.loadCookie("west_layout");
        

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
    
</script>
</head>
<body>

<!-- 顶部区域 -->
<div class="ui-layout-north">
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#101010">
  <tr>
    <td height="50" >
        <div class="top" >
        	<div class="ele" >  
        		<a href="${ctx}/channel/ad/channel-ad!queryall.action" target="layout_center"  style="cursor: pointer;">统计分析</a>
            </div>
        	<div class="ele" >
        		<a href="${ctx}/account/user!modify.action" target="layout_center"  style="cursor: pointer;">我的账号</a>
        	</div>
        	<div class="ele" style="margin-left: 28px;"> 
        		<a href="javascript:exitsystem()"  style="cursor: pointer;">退出</a>
        	</div>
        </div>
        
    </td>
  </tr>
</table>
</div>


<!-- 内容区域 -->
<div class="ui-layout-center" style="overflow: hidden;background: url('${ctx}/images/web/main_20.gif') repeat-y scroll right 0 transparent;padding-right:2px">
    <iframe scrolling="auto" class="ui-layout-center" name="layout_center" height="100%" width="100%" frameborder="0" src="${ctx}/channel/ad/channel-ad!queryall.action?_=<fmt:formatDate value="${date}" pattern="yyyyMMddHHmmssSSS"/>"> 浏览器不支持嵌入式框架，或被配置为不显示嵌入式框架。</iframe>
</div>



</body>
</html>
