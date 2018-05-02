<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8" import="java.sql.*,java.io.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>净喜服务承诺</title>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/jquery.js"></script>
 <style type='text/css'>
	div{word-break:break-all;}
	div p{color:black;font-size:36px;}
 </style>
 <script type="text/javascript">
 window.onload=function(){ 
	 if(document.documentElement.scrollHeight <= document.documentElement.clientHeight) { 
		 bodyTag = document.getElementsByTagName('body')[0]; 
		 bodyTag.style.height = document.documentElement.clientWidth / screen.width * screen.height + 'px'; 
		 } 
 setTimeout(function() { 
 window.scrollTo(0, 1) 
 }, 0); 
 }; 
 </script>

</head>
<body bgcolor="#efeff4 ">
<%-- <% --%>
<%-- 	//驱动程序名    --%>
<%-- 	String driverName = "com.mysql.jdbc.Driver";   --%>
<%-- 	//数据库用户名    --%>
<%-- 	String userName = "root";   --%>
<%-- 	//密码    --%>
<%-- 	String userPasswd = "123456";   --%>
<%-- 	//数据库名    --%>
<%-- 	String dbName = "jxdb";   --%>
<%-- 	//表名    --%>
<%-- 	String tableName = "jx_version";   --%>
<%-- 	//联结字符串    --%>
<%-- 	String url = "jdbc:mysql://192.168.1.37:3306/" + dbName + "?user="   --%>
<%-- 	        + userName + "&password=" + userPasswd;   --%>
<%-- 	Class.forName("com.mysql.jdbc.Driver").newInstance();   --%>
<%-- 	Connection connection = DriverManager.getConnection(url);   --%>
<%-- 	Statement statement = connection.createStatement();   --%>
<%-- 	String sql = "SELECT * FROM " + tableName;   --%>
<%-- 	ResultSet rs = statement.executeQuery(sql);  --%>
	
<%-- 	while(rs.next()){ --%>
<%-- %> --%>
<%-- 	<td><% out.print(rs.getString("apk_digest")); %></td> --%>
<%-- <% --%>
<%-- 	} --%>
<%-- %> --%>
<div>
<h1>净喜物联网净水机介绍</h1>
	<p>一、产品功能（多级过滤、星级保障、水质清甜、安全健康）：</p>
	<p>第一级：PP滤芯：</p>
	<p>精度5微米，能有效滤除的水中的铁锈、泥沙、虫卵等大颗粒杂质；</p>
	<p>第二级：CTO块状活性炭滤芯：</p>
	<p>能有效去除水中的有机物、余氯及其他放射性物质、杀虫剂、农药残余物、有机溶剂、化学污染等，去除异色异味，保护RO膜；</p>
	<p>第三级：RO膜滤芯：</p>
	<p>过滤精度达万分之一微米，可滤除水中的无机盐、重金属离子、有机物、胶体、细菌、病毒等，确保水质纯净安全；</p>
	<p>第四级：复合能量矿化滤芯：</p>
	<p>释放对人体有益的矿物质，调节水的酸碱度，增加水的含氧量和水的活性，更利于人体吸收。</p>
	<!-- <p>第五级：矿化滤芯：</p> -->
	<!-- <p>释放对人体有益的矿物质，增加水的含氧量和水的活性，更利于人体吸收。</p> -->
	<br>
	<p>二、产品十大特点（你无我有，你有我优）：</p>
	<p>一）产品外观简约时尚、美观大方，为家庭增色，为家人增光；</p>
	<p>二）产品配置7寸触控液晶显示屏，可播放视频，随时查看产品使用状态；</p>
	<p>三）产品可实时显示天气状况、温度等与生活相关的信息，直观方便；</p>
	<p>四）产品可连接无线WIFI，产品数据与手机数据同步更新，实现远程监控；</p>
	<p>五）产品拥有专属APP，一台机绑定一个手机号，可实现手机APP发布信息，屏上显示功能；</p>
	<p>六）产品可扩展性强，通过互联网技术推送用户感兴趣的信息和资讯，可增加在线商城等各类服务；</p>
	<p>七）产品原创设计社区服务版块，通过APP在线发布用户需求信息和用户提供服务信息，在同一社区内实现更方便的交流沟通和便捷服务，促进智慧社区、和谐社区的实现；</p>
	<p>八）产品系统可不断更新，持续为用户提供更优质的社区服务和互联网增值服务；</p>
	<p>九）产品出水即热，可按功能需求选择出水温度，无水胆，杜绝千滚水、死水和二次污染；</p>
	<p>十）产品出水既保证了水质纯净，又保证了水的活性和微量元素，安全健康。</p>
	<br>
	<p>三、产品六大技术优势（科技永不止步，创新源于需求）：</p>
	<p>一）采用源自美国航天领域世界先进的RO反渗透技术，过滤精度万分之一微米，确保水质安全纯净；</p>
	<p>二）采用天然椰壳活性炭烧结技术，使活性炭表面积成倍增加，对异色异味和有害物质的吸附阻挡能力大大增加；</p>
	<p>三）采用天然矿化技术，对滤后纯水自然矿化，增加水的活性，释放有益微量元素，使水分子更容易被人体吸收，促进人体新陈代谢；</p>
	<p>四）采用全新稀土膜加热技术，出水即热，可瞬间让水温达到100℃，泡面、冲茶等非常方便，杜绝饮用千滚水、死水和二次污染水，能耗低，安全环保；</p>
	<p>五）采用互联网、大数据、云计算、物联网等技术，实现人机互联，远程监控，数据同步，平台共享；</p>
	<p>六）采用自主研发和原创信息编程技术、计算机控制技术率先实现净水器与互联网深度融合，实现Android和IOS两大系统的专属APP同步上线，且可保障后续更新，增加用户体验。</p>
	
</div>
<div>
<h1>净喜售后服务政策</h1>
	<p>一、售后服务政策（一次源于品牌，一生源于服务）：</p>
	<p>在线预约，按时安装，24小时全天响应；</p>
	<p>使用状态，随时监控，系统及时预警提醒；</p>
	<p>付费续费，手机支付，保障设备正常运行；</p>
	<p>系统更新，APP提醒，在线升级全新服务；</p>
	<p>三年免费，终身服务。</p>
	<p>百分百免费安装；</p>
	<p>百分百免费换芯；</p>
	<p>百分百免费维修；</p>
	<p>百分百免费升级；</p>
	<p>二、服务收费</p>
    <p>1、在线选型下单，并在线支付服务费和产品押金，获免费安装产品、全年所有维护全包服务，无额外收费。</p>
    <p>产品包年服务费：壁挂式净水机：998元/年；损坏遗失等赔偿价：8800元/台</p>
	<p>台式净水机：1280元/年；损坏遗失等赔偿价：10800元/台</p>
	<p>立式净水机：1480元/年；损坏遗失等赔偿价：12800元/台</p>
	<!-- <p>产品流量计费：0.48元/升（流量预付费按包年金额预付）</p> -->
	<p>产品押金：500元</p>
    <p>2、三年期满，产品所有权归用户，所交产品押金自动转为终身服务费。如有产品需维修或换芯，则按标准收取相关维修配件费。</p>
    <p>3、用户不按时支付服务费，产品自动断水停机。在此之前，系统会通过APP提醒用户，我们和客服人员也会跟进提醒用户。</p>
	
</div>

</body>
</html>