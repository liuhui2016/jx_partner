var ua=navigator.userAgent.toLowerCase();  
var is=(ua.match(/\b(chrome|opera|safari|msie|firefox)\b/) || ['','mozilla'])[1];  
$.browser.is=is;  
var newurl,newtime,newwidth,newheight, timeinterval;
(function ($){
		this.version = '@1.0';
		this.layer = {'width' : 200, 'height': 100};
		this.title = '信息提示';
		this.time = 4000;
		this.anims = {'type' : 'slide', 'speed' : 600};
		this.distimer = null;
		this.inits = function(title, text,t){
			var hoverflag =true;
			var hflag =true;
			if($("#message").is("div")){ return; }
			$(document.body).prepend('<div id="message" style="bottom:0;right:0; outline: 0pt none; z-index: 9999; display: none; position: fixed;" class="ui-dialog ui-widget ui-widget-content ui-corner-all " tabindex="-1" role="dialog" aria-labelledby="ui-dialog-title-show_message"><div class="ui-dialog-titlebar ui-widget-header ui-corner-all ui-helper-clearfix" style="-moz-user-select: none;" unselectable="on"><span class="ui-dialog-title" id="ui-dialog-title-show_message" unselectable="on" style="-moz-user-select: none;">'+title+'</span><a href="#" id="uiDialogTitlebarmin" class="ui-dialog-titlebar-min ui-corner-all"><span>Min</span></a><a id="uiDialogTitlebarClose" class="ui-dialog-titlebar-close ui-corner-all" role="button" unselectable="on" style="-moz-user-select: none;cursor: pointer;"><span id="message_close" class="ui-icon ui-icon-closethick" unselectable="on" style="-moz-user-select: none;">close</span></a></div><div style="display: block; width: auto;" id="show_message" class="ui-dialog-content ui-widget-content">'+text+'</div></div>');			$("#message").css({width:this.layer.width,height:this.layer.height});
			$("#show_message").css('height',this.layer.height-40);
			distimer = setTimeout('this.close()', t);
			$("#message").hover(function(){if(hoverflag){clearTimeout(distimer);distimer = null;}
			},function(){if(hoverflag){distimer = setTimeout('this.close()', t);}});
			$("#uiDialogTitlebarmin").toggle(
					function(){	
						hoverflag = false;
						hflag = false;
						$("#message").animate({width:layer.width,height:layer.height-$("#show_message").outerHeight()},anims.speed);
						$("#show_message").	hide();
						$("#uiDialogTitlebarmin").removeClass().addClass("ui-dialog-titlebar-rest ui-corner-all").hover(
								function() {$("#uiDialogTitlebarmin").removeClass().addClass('ui-dialog-titlebar-rest ui-dialog-titlebar-rest-hover ui-corner-all ui-state-hover');},
								function() {$("#uiDialogTitlebarmin").removeClass('ui-dialog-titlebar-rest-hover ui-corner-all ui-state-hover');});
						$("#uiDialogTitlebarmin span").html("Max");
						$("#message").focus();
						clearTimeout(distimer);
						timeinterval=setInterval(function(){updateConten(0)},newtime);
					},
					function(){	
						hoverflag = true;
						hflag = false;
						$("#show_message").show();
						$("#message").animate({width:layer.width,height:layer.height},anims.speed);
						$("#uiDialogTitlebarmin").removeClass().addClass("ui-dialog-titlebar-min ui-corner-all").hover(
								function() {$("#uiDialogTitlebarmin").removeClass().addClass('ui-dialog-titlebar-min ui-dialog-titlebar-min-hover ui-corner-all ui-state-hover');},
								function() {$("#uiDialogTitlebarmin").removeClass('ui-dialog-titlebar-min-hover ui-corner-all ui-state-hover');});
						$("#uiDialogTitlebarmin span").html("Min");
						$("#message").focus();
						clearTimeout(timeinterval);
					}
				);
				if(hflag){
					$("#uiDialogTitlebarmin").hover(
						function() {$("#uiDialogTitlebarmin").addClass('ui-dialog-titlebar-min-hover ui-corner-all ui-state-hover');},
						function() {$("#uiDialogTitlebarmin").removeClass('ui-dialog-titlebar-min-hover ui-corner-all ui-state-hover');});
				}
				$("#uiDialogTitlebarClose").click(function(){		
					close();
				}).hover(
					function() {$("#uiDialogTitlebarClose").addClass('ui-state-hover');},
					function() {$("#uiDialogTitlebarClose").removeClass('ui-state-hover');});
		};
		this.show =  function(width,height,title,text,anim,time){			clearTimeout(distimer);
			clearTimeout(timestart);
			clearTimeout(timeinterval);
			distimer = null
			var msgwidth,msgheight,content;
			if($("#message").is("div")){ return; }
			if(width!=0 && width)this.layer.width = width;
			if(height!=0 && height)this.layer.height = height;
			var type,speed=400;
			if (typeof anim === 'string'){
				type=anim;
			}else if($.isArray(anim)){
				type=anim[0];
				speed=parseInt(anim[1]);
			}else{
				type=this.anims.type;
				speed=this.anims.speed;
			}
			if(type!=0 && type)this.anims.type = type;
			if(speed!=0 && speed){
				switch(speed){
					case 'slow' : ;break;
					case 'fast' : this.anims.speed = 200; break;
					case 'normal' : this.anims.speed = 400; break;
					default:					
						this.anims.speed = speed;
				}
			}
			if(title==0 || !title)title = title;
			if ( $.isArray(text)){
				content= text[0];
			}else if(typeof text === "string"){
				$.get(text,{tmp:new Date().getTime()},function(data){
				//	alert(data);
					content = data;
				})
			}else{content=text;}
			//alert(content);
			if(time>0){t = time}else{t=this.time};
			inits(title, content,t);
			switch(this.anims.type){
				case 'slide':$("#message").slideDown(this.anims.speed);break;
				case 'fade':$("#message").fadeIn(this.anims.speed);break;
				case 'show':$("#message").show(this.anims.speed);break;
				default:$("#message").slideDown(this.anims.speed);break;
			}

			if($.browser.is=='chrome'){
				setTimeout(function(){
					$("#message").remove();
					inits(title, text);
					$("#message").css("display","block");
				},anims.speed-(anims.speed/5));
			}
			rmmessage(t);
		};
		this.rmmessage = function(time){
			if(time>0){
				//distimer = setTimeout('this.close()', time);
				//setTimeout('$("#message").remove()', time+1000);
			}
		};
		this.close = function(){
			switch(this.anims.type){
				case 'slide':$("#message").slideUp(this.anims.speed);break;
				case 'fade':$("#message").fadeOut(this.anims.speed);break;
				case 'show':$("#message").hide(this.anims.speed);break;
				default:$("#message").slideUp(this.anims.speed);break;
			};
			setTimeout('$("#message").remove();', this.anims.speed);
			this.original();	
			clearTimeout(timeinterval);
			timestart = setTimeout(function(){popupIn()},newtime);
		}
		this.original = function(){	
			this.layer = {'width' : 200, 'height': 100};
			this.title = '信息提示';
			this.time = 4000;
			this.anims = {'type' : 'slide', 'speed' : 600};
		};
    msgpopup=function(width,height,title,text,anim,time){
    	this.show(width,height,title,text,anim,time);
    };
})(jQuery);

function updateConten(type){
	$.get(newurl,function(data){
		if(data == ''){timestart = setTimeout(function(){popupIn()},newtime);return;}
		var content="<ul>";
		$(data).each(function(i,item){
			var durl ="emergency!detail.action?id="+item[0];
			content = content+"<li><span class='poupmain'>"+item[1]+"</span> : <a href='#' onclick=mappopup('"+durl+"')><span class='pouptitle'>"+item[2]+"</spans></a></li>";
		})
		content=content+"</ul>";
		if(type == 1){
			msgpopup(newwidth,newheight,'应急信息提示', [content],'slide',6000);
		}else{
			$("#show_message").html(content);
		}
	});
}
function popupIn(){
	updateConten(1);
}
/**
 * 大屏右下角弹出框
 * @param url action地址
 * @param time 执行间隔时间
 * @param width 宽度 不能小于130
 * @param height 高度 不能小于28
 * @return
 */
function showmsg(url,time,width,height){
	if(!time || !url){return;}
	if(!width || width<130){width=250;}
	if(!height || height<28){height=150;}
	newwidth=width;
	newheight=height;
	newurl=url;
	time = parseInt(time)*1000;
	newtime = time;
	timestart = setTimeout(function(){popupIn()},time);
}
