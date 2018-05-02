<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!-- saved from url=(0035)http://www.lmuch.com/?channel=12003 -->
<html>
<head lang="en">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>more love the world</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
<link rel="stylesheet" href="css/style.css">
<script src="js/analytics.js"></script>
<script src="js/ca-pub-8318007300590679.js"></script>
<script src="js/jquery-1.8.3.min.js"></script>
</head>
<body>
	<div class="main">
		<div class="top">
			<!-- 天气-->
			<div class="weather">
				<span class="logo"><img
					src="images/lmuch_logo.png" alt=""></span>
				<!--<span class="yun"><img src="static/img/yun.png" alt=""/></span>-->
				<!--<em>18C</em>-->
			</div>
			<!-- 搜索框-->
			<div class="search">
				<form id="myform" action="http://www.google.com/search"
					class="search-form">
					<input type="hidden" name="cx"
						value="partner-pub-8318007300590679:9924903174"><input
						type="submit" class="serInput"><input id="kw" type="text"
						name="q" placeholder="Input your want" class="serText">
				</form>
			</div>
			<!-- a链接-->
			<!--<div class="link">-->
			<!--<a href="javascript:;">Pharoah</a>-->
			<!--<a href="javascript:;">Shaken</a>-->
			<!--<a href="javascript:;">Arizona</a>-->
			<!--<a href="javascript:;">Emotional</a>-->
			<!--<a href="javascript:;">Pharoah</a>-->
			<!--</div>-->
		</div>
		<div class="cont">
			<ul class="sIcon g-clr">
				<c:forEach items="${hotWords}" var="a" varStatus="c">
				<li>
					<a href="${a.dumpDownUrl}" target="_blank"><span>
						<img src="${a.iconUrl}" alt=""></span>
						${a.title} </a></li>
				</c:forEach>
			</ul>
			
			<div class="hot">
				<h3>Popular Recommend</h3>
				<ul class="g-clr">
					<c:forEach items="${recommends}" var="a" varStatus="c">
					<li><a
						href="${a.dumpDownUrl}"
						target="_blank"><span><img
								src="${a.iconUrl}" alt=""></span>
							${a.title} </a></li>
					</c:forEach>
				</ul>
				<!--<a href="" class="more" target="_blank"> more</a>-->
			</div>

			<ul class="list">
				<li><span><img
						src="images/News.png" alt=""></span><a
					href="http://www.cnnmobile.com/" target="_blank">CNN</a><a
					href="http://m.cbsnews.com/" target="_blank">CBS</a><a
					href="http://foxnews.com/" target="_blank">FOX</a><a
					href="http://m.yahoo.com/" target="_blank">Yahoo</a><a
					href="http://m.usatoday.com/" target="_blank">Usatoday</a></li>
				<li><span><img
						src="images/Social.png" alt=""></span><a
					href="http://m.facebook.com/" target="_blank">Facebook</a><a
					href="http://mobile.twitter.com/" target="_blank">Twitter</a><a
					href="http://www.linkedin.com/" target="_blank">LinkedIn</a><a
					href="http://m.google.com/app/plus" target="_blank">Google+</a></li>
				<li><span><img
						src="images/Shopping.png" alt=""></span><a
					href="http://www.ebay.com/" target="_blank">eBay</a><a
					href="http://www.groupon.com/mobile" target="_blank">Groupon</a><a
					href="http://mobile.buy.com/" target="_blank">Buy.com</a><a
					href="http://www.amazon.in/gp/aw/?tag=yiipol-21" target="_blank">Amazon</a></li>
				<li><span><img
						src="images/Music.png" alt=""></span><a
					href="http://mp3.com/" target="_blank">MP3</a><a
					href="http://music.yahoo.com/" target="_blank">Yahoo</a><a
					href="http://bigpondmusic.com/mobile" target="_blank">Bigpond</a><a
					href="http://www.mtv.com/" target="_blank">MTV</a><a
					href="http://m.metrolyrics.com/" target="_blank">Lyrics</a></li>
				<li><span><img
						src="images/Video.png" alt=""></span><a
					href="http://m.youtube.com/" target="_blank">YouTube</a><a
					href="http://vimeo.com/m" target="_blank">Vimeo</a><a
					href="http://www.hulu.com/" target="_blank">Hulu</a><a
					href="http://www.bebo.com/c/videocntnt" target="_blank">Bebo</a><a
					href="http://video.pbs.org/" target="_blank">PBS Video</a></li>
				<li><span><img
						src="images/Sports.png" alt=""></span><a
					href="http://m.espn.go.com/" target="_blank">ESPN</a><a
					href="http://m.cbssports.com/" target="_blank">Fox Sports</a><a
					href="http://www.cbssports.com/" target="_blank">CBS sports</a><a
					href="http://nfl.com/" target="_blank">NFL</a><a
					href="http://nba.com/" target="_blank">NBA</a></li>
				<li><span><img
						src="images/Game.png" alt=""></span><a
					href="https://zynga.com/" target="_blank">zynga</a><a
					href="http://www.higames.cc/" target="_blank">higames</a><a
					href="https://games.yahoo.com/" target="_blank">yahoo games</a><a
					href="http://zh.y8.com/" target="_blank">y8</a></li>
			</ul>

		</div>
	</div>
	<div id="lateralTips">
		<p>请在竖屏下浏览</p>
	</div>


	
</body>
</html>