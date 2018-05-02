/*--------------------------------------------------|
| alert 1.0 | 										|
|---------------------------------------------------|
| Copyright (c) 2009-2010 qc.com					|
|                                                   |
| 通用alert、confirm弹出层模式.                      |
|                                                   |
| Author: srain12                                 |
| Updated: 20.07.2010                               |
|--------------------------------------------------*/
/*******************************************************************************
 * 更新记录： 
 * 21.07.2010	回调方法实现.	srain12
 ******************************************************************************/
$(function() {
	$.alerts = {

		// alert方法
		alert : function(message, title, callback) {
			if (title == null) title = 'Alert';
			$.alerts._show(title, message, 'alert', function(result) {
				if (callback) callback(result);
			});
		},

		// confirm方法
		confirm : function(message, title, callback) {
			if (title == null) title = 'Confirm';
			$.alerts._show(title, message, 'confirm', function(result) {
				if (callback) callback(result);
			});
		},
		
		// error方法
		error : function(message, title, callback) {
			if (title == null) title = 'Error';
			$.alerts._show(title, message, 'error', function(result) {
				if (callback) callback(result);
			});
		},

		// 通过调用jQuery UI dialog来实现
		_show : function(title, msg, type, callback) {
			if (!$("div").is("#dialog_alert")) {
				$("<div id='dialog_alert' title='" + title + "'>" + msg
								+ "</div>").hide().appendTo(document.body);
			}
			switch (type) {
			case 'alert':
				$("#dialog_alert").dialog( {
					draggable : false,
					autoOpen : true,
					resizable : false,
					width : 300,
					minHeight:100,
					modal : true,
					open : function(event, ui) {
						$(".ui-widget-overlay").css("width","100%");
						$("button").each(function(i) {
							if ($(this).html() == "确定") this.focus();
						})
					},
					close : function(event, ui) {
						$("#dialog_alert").dialog( "destroy" ).remove();
					},
					buttons : {
						确定 : function() {
							$(this).dialog('close').remove();
							if (callback) callback(true);
						}
					}
				});
				break;
			case 'confirm':
				$("#dialog_alert").dialog( {
					draggable : false,
					autoOpen : true,
					resizable : false,
					width : 300,
					minHeight:100,
					modal : true,
					open : function(event, ui) {
						$(".ui-widget-overlay").css("width","100%");
						$("button").each(function(i,item) {
							if ($(this).text() == "取消") this.focus();
						});
					},
					close : function(event, ui) {
						$("#dialog_alert").dialog( "destroy" ).remove();
					},
					buttons : {
						确定 : function() {
							$(this).dialog('close').remove();
							if (callback) callback(true);
						},
						取消 : function() {
							$(this).dialog('close').remove();
							if (callback) callback(false);
						}
					}
				})
				break;
			case 'error':
				$("#dialog_alert").dialog( {
					draggable : true,
					autoOpen : true,
					resizable : true,
					width : 300,
					height:120,
					modal : true,
					open : function(event, ui) {
						$(".ui-widget-overlay").css("width","100%");
						$("button").each(function(i) {
							if ($(this).html() == "确定") this.focus();
						})
					},
					close : function(event, ui) {
						$("#dialog_alert").dialog( "destroy" ).remove();
					},
					buttons : {
						确定 : function() {
							$(this).dialog('close').remove();
							if (callback) callback(true);
						}
					}
				})
				break;
			}
		}
	}
	jAlert = function(message, title, callback) {
		$.alerts.alert(message, title, callback);
	};
	jConfirm = function(message, title, callback) {
		$.alerts.confirm(message, title, callback);
	};
	jError = function(message, title, callback) {
		$.alerts.error(message, title, callback);
	};
});

$(function() {
	$.showUpdownSels = {
		showUpdownSel:function(btnnm,divnm){
			if (!$("div").is("#updowndiv")) {
			$("<div id='updowndiv' style='display:none' class='updowndiv'><div class='inner-updowndiv'></div></div>").hide().appendTo(document.body);}
			$(".inner-updowndiv").html($("#"+divnm).html());
			$("#"+btnnm).button({icons: {primary: 'ui-icon-gear',secondary: 'ui-icon-triangle-1-s'}
	        }).click(function(){
	        	$("#updowndiv").toggle().css({top:0, left:0}).position({my: "left top",at: "left bottom",of: this});
				if($("#updowndiv").width()<=$("#selbtn").width()){$("#updowndiv").width($("#selbtn").width());}
	        })
	        $("#updowndiv").mouseout(function(){$("#updowndiv").hide();})
	        .mouseover(function(){$("#updowndiv").show();});
		}
	}
	showudsel = function(btnnm,divnm){
		$.showUpdownSels.showUpdownSel(btnnm,divnm);
	};
});