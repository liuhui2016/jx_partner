/*--------------------------------------------------|
| core 1.0 | 										|
|---------------------------------------------------|
| Copyright (c) 2009-2010 qincai123.com				|
|                                                   |
| 核心JS方法定义:                                    |
| 涉及分页、select操作、导航、excel导出...等通用方法. |
|                                                   |
| Author: ZhangXin                                  |
| Updated: 15.05.2013                               |
|--------------------------------------------------*/
/*******************************************************************************
 * 更新记录： 
 * 20.06.2010  分页 js特效  lixiangboopr_update( 
 * 16.07.2010  List增删改   liuyufeng
 * 18.07.2010  统一默认注册提示信息框 javen
 ******************************************************************************/

function importExcelInit(url, authId, p) {
	url = ctx + url
	url += (url.indexOf("?") == -1) ? "?authId=" + authId : "&authId=" + authId;
	if (p) {
		parent.location.href = url;
	} else {
		location.href = url;
	}
}
function importExcel(url, authId) {
	if (!$("div").is("#dialog_div")) {
		$(
				"<div id='dialog_div'><img src='" + ctx
						+ "/images/loadingAnimation.gif'/></div>").hide()
				.appendTo(document.body);
	}
	$("#dialog_div")
			.dialog(
					{
						autoOpen : true,
						modal : true,
						resizable : false,
						height : 60,
						width : 250,
						open : function(event, ui) {
							$(".ui-dialog-titlebar").hide();
							$(".ui-dialog")
									.removeClass(
											"ui-widget ui-widget-content ui-corner-all ui-draggable");
						}
					});
	var au = ctx + url + "?authId=" + authId;
	$("#exportForm").action = au;
	$("#exportForm").submit();
}
function jumpPage(pageNo) {
	$("#pageNo").val(pageNo);
	$("#excelExp").val("false");
	$("#mainForm").submit();
}

function sort(orderBy, defaultOrder) {
	if ($("#orderBy").val() == orderBy) {
		if ($("#order").val() == "") {
			$("#order").val(defaultOrder);
		} else if ($("#order").val() == "desc") {
			$("#order").val("asc");
		} else if ($("#order").val() == "asc") {
			$("#order").val("desc");
		}
	} else {
		$("#orderBy").val(orderBy);
		$("#order").val(defaultOrder);
	}
	$("#excelExp").val("false");
	$("#mainForm").submit();
}

//查询操作
function search() {
	$("#order").val("");
	$("#orderBy").val("");
	$("#pageNo").val("1");
	//$("#pageNo").val();
	$("#excelExp").val("false");
	$("#mainForm").submit();
}




/** **************** 操作select option *********** */
function deleteOption(object, index) {
	object.options[index] = null;
}

function addOption(object, text, value) {
	var defaultSelected = true;
	var selected = true;
	var optionName = new Option(text, value, defaultSelected, selected)
	object.options[object.length] = optionName;
}

function copySelected(fromObject, toObject) {
	for ( var i = 0, l = fromObject.options.length; i < l; i++) {
		if (fromObject.options[i].selected)
			addOption(toObject, fromObject.options[i].text,
					fromObject.options[i].value);
	}
	for ( var i = fromObject.options.length - 1; i > -1; i--) {
		if (fromObject.options[i].selected)
			deleteOption(fromObject, i);
	}
}

function noSelected(object) {
	for ( var i = object.options.length - 1; i > -1; i--) {
		object.options[i].selected = false;
	}
}
function copyAll(fromObject, toObject) {
	for ( var i = 0, l = fromObject.options.length; i < l; i++) {
		addOption(toObject, fromObject.options[i].text,
				fromObject.options[i].value);
	}
	for ( var i = fromObject.options.length - 1; i > -1; i--) {
		deleteOption(fromObject, i);
	}
}

function populate(form) {
	for ( var i = 0, l = form.selectedPublications.options.length; i < l; i++) {
		form.selectedPublications.options[i].selected = true;
	}
	for ( var i = 0, l = form.nonSelectPubs.options.length; i < l; i++) {
		form.nonSelectPubs.options[i].selected = true;
	}
	return true;
}
//获得选中项的索引    
jQuery.fn.getSelectedIndex = function(){     
    return jQuery(this).get(0).selectedIndex;     
}
 // 获得当前选中项的文本    
 jQuery.fn.getSelectedText = function (){   
    if (this.size()==0) { 
	    return "下拉框中无选项 ";   
    }else {   
        var index = this.getSelectedIndex();
        return  $(this).get( 0 ).options[index].text;
    }   
 }
/**
 * 分页标签的提交
 * 
 * @param pageNo
 *            当前页
 * @param onPageId
 * @param pageSize
 *            一页显示的条数
 * @return
 */
function onPage(pageNo, onPageId, pageSize) {
	if(pageSize){
		$("#pageSize").val(pageSize);
	}
	jumpPage(pageNo);
}
/**
 * 导出excel
 * 
 * @return
 */
function excelExprot() {
	$("#excelExp").val("true");
	$("#mainForm").submit();
}

/**
 * 重置操作
 */
function resetb() {
	$("#excelExp").val("false");
	$("#item input[type!='button']").each(function(i) {
		$(this).val('');
	});
	$("#item select").each(function(i, item) {
		$(this).val('');
		$(this)[0].selectedIndex = -1;
	});
	$("#mainForm").submit();
}

function delItem(url) {
	if (confirm("确定删除此记录吗? 删除后不能恢复!")) {
		location.href = url;
	}
}

function contral(obj) {

	$(obj).toggleClass("open").prev('div').toggleClass("hidden");
	if ($(obj).find('img').attr("src").match("close") != null) {
		// 算出固定上部分的高度
		$(".container").css("margin-top", $("#fixtitle").innerHeight());
		$(obj).find('img').attr("src", ctx + "/images/f_open.gif").attr(
				"title", "展开查询面板");
	} else {
		// 算出固定上部分的高度
		$(".container").css("margin-top", $("#fixtitle").innerHeight());
		$(obj).find('img').attr("src", ctx + "/images/f_close.gif").attr(
				"title", "收起查询面板");
	}
	$.cookie("show_hidden", $(obj).prev('div').attr("class"), {
		expires : 7,
		path : '/'
	});

	// 遇到ie6下当iframe出现纵向滚动条时同时会出现横向滚动条
	if ($.browser.msie && $.browser.version == "6.0") {
		if ($("html")[0].scrollHeight < $("html").height()) {
			$("html").css("overflowY", "hidden");
		} else {
			$("html").css("overflowY", "scroll");
		}
	}
}
/** ** CustomMenu***** */

function add2custom(authId) {
	if (authId == '') {
		jAlert("无法取到权限编号", "内部办公自动化系统");
	} else {
		$.ajax( {
			type : "POST",
			url : ctx + "/account/custom-menu!ajax.action",
			data : "authId=" + authId,
			success : function(msg) {
				jAlert(msg, "内部办公自动化系统");
			}
		});
	}
}

/*******************************************************************************
 * ** Tree and list type: manage的名字首字母小写 title：大分类 division: tree:树,list:列表
 */
function showTreeAndList(type, title, division, name, level) {
	var fsrc, width, heigth;
	if (division == 'tree') {
		width = 400;
		height = 350;
		fsrc = ctx + "/common/selector!tree.action?type=" + type + "&name="
				+ name + "&level=" + level;
	} else if (division == 'list') {
		var ppageno = $("#parent_pageno").val();
		fsrc = ctx + "/common/selector!list.action?type=" + type + "&name="
				+ name + "&page.pageNo=" + Number(ppageno);
		width = 570;
		height = 355;
	} else {
		if (type == "portStationInfoTo") {
			type = "portStationInfo&filter_EQB_outset=true";
		} else if (type == "ToportStationInfo") {
			type = "portStationInfo&filter_EQB_outset=false";
		}
		var ppageno = $("#parent_pageno").val();
		fsrc = ctx + "/common/selector!lists.action?type=" + type + "&name="
				+ name + "&page.pageNo=" + Number(ppageno);
		width = 570;
		height = 355;
	}
	if (!$("div").is("#show_treeandlist")) {
		$(
				"<div id='show_treeandlist' style='padding:0.5em 6px;' title='"
						+ title + "选择'><img id='treeload' src='" + ctx
						+ "/images/loading.gif'/></div>").hide().appendTo(
				document.body);
	}
	$("#show_treeandlist")
			.dialog(
					{
						autoOpen : true,
						maximized : true,
						width : width,
						height : height,
						resizable : true,
						modal : true,
						position : 'top',
						open : function(event, ui) {
							$("#show_treeandlist")
									.append(
											"<iframe id='if_show_treeandlist' name='if_show_treeandlist' src='"
													+ fsrc
													+ "' scrolling='auto' width='100%' height='100%' frameborder='0' "
													+ "></iframe>");
							$("#show_treeandlist")
									.after(
											"<input id='selnm' type='hidden' value=''/>");
						},
						close : function(event, ui) {
							$(this).dialog("destroy");
							$("#show_treeandlist").remove();
						},
						buttons : {
							确定 : function() {
								var n = $("#selnm").val();
								if (division == 'tree') {
									$("#showtree" + name).val(n);
								} else {
									$("#showlist" + name).val(n);
								}
								$(this).dialog('close');
							}
						}
					});
}

/** ** Map***** */
function showMap() {
	if (!$("div").is("#show_map")) {
		$(
				"<div id='show_map' title='站点地图'><img id='mapload' src='" + ctx
						+ "/images/loading.gif'/></div>").hide().appendTo(
				document.body);
	}
	$("#show_map").dialog( {
		autoOpen : true,
		width : 650,
		height : 400,
		resizable : true,
		modal : true,
		position : 'top',
		open : function(event, ui) {
			$("#show_map").load(ctx + '/account/authority!map.action');
		},
		close : function(event, ui) {
			$(this).dialog("destroy");
		}
	});
}

/** ** List全选 ***** */
function checkedAll(id) {
	if ($('input[name="box"]').attr('checked')) {
		$('input[name="' + id + '"]').attr('checked', 'true');
	} else {
		$('input[name="' + id + '"]').removeAttr('checked');
	}
}
/** ** List增删改 ***** */
function opr_input(url, openParent, authId) {
	url = prepURL(url, "authId", authId);
	if (openParent) {
		parent.location.href = url;
	} else {
		location.href = url;
	}
}
/*******************************************************************************
 * ** 更新方法 update url:form的action formId：需要提交的form openParent：是否在父窗口打开
 * authId：权限id tip：出错提示信息 注意：确保每次只能修改一条记录
 */
function opr_update_over(url, formId, openParent, authId, tip) {

	var checked = $("#" + formId + " :checkbox:checked").not($('#box')).length;
	if (checked == 0) {
		jAlert(tip ? tip : '请选择需要修改的记录！！！', "办公室自动化系统");
	} else if (checked >= 2) {
		jAlert('一次只能操作一条记录！！！', "办公室自动化系统");
	} else {
		var str = prepURL(url, "id", getCheckedValue(formId));
		if (openParent) {
			parent.location.href = prepURL(str, "authId", authId);
		} else {
			location.href = prepURL(str, "authId", authId);
		}
	}

}
function opr_update_radio(url, formId, tip) {

	var checked = $("#" + formId + " :radio:checked").not($('#box')).length;
	if (checked == 0) {
		jAlert(tip ? '请选择需要' + tip + '的记录！！！' : '请选择需要修改的记录！！！',
				"办公室自动化系统");
	} else {
		jConfirm("确认要" + (tip ? tip : "变更") + "记录吗？", "办公室自动化系统",
				function(result) {
					if (result) {
						$("#" + formId + "").attr('action', url);
						$("#" + formId + "").submit();
					} else {
						$("#" + formId + " :radio").attr("checked", false);
					}
				});
	}

}
function opr_update(url, formId, openParent, authId) {
	opr_update_over(url, formId, openParent, authId, null);
}

function opr_delete(url, formId) {
	var checked = $("#" + formId + " :checkbox:checked").not($('#box')).length;
	if (checked === 0) {
		jAlert("请选择要删除的记录！！！", "办公室自动化系统");
		return false;
	} else {
		jConfirm("确定要删除记录吗？ 删除后不能恢复！请谨慎操作！", "办公室自动化系统", function(result) {
			if (result) {
				url = prepURL(url, "id", getCheckedValue(formId));
				$("#" + formId + "").attr('action', url);
				$("#" + formId + "").submit();
			} else {
				$("#" + formId + " :checkbox").attr("checked", false);
			}
		});
	}

}
function opr_delete_over(url, formId, status) {
	var checked = $(
			"#" + formId + " :checkbox[checked=true][alt != " + status + "]")
			.not($('#box')).length;
	if (checked > 0) {
		jAlert('只有未审核预案可以删除！！！', "办公室自动化系统");
	} else {
		opr_delete(url, formId);
	}
}
function opr_batchAudit(url, formId, authId) {
	var checked = $("#" + formId + " :checkbox:checked").not($('#box')).length;
	if (checked === 0) {
		jAlert('请选择需要审核的记录！！！', "办公室自动化系统");
	} else {
		jConfirm("确认要批量审核" + checked + "条记录吗？", "办公室自动化系统", function(
				result) {
			if (result) {
				$("#" + formId + "").attr('action', url);
				$("#" + formId + "").submit();
			} else {
				$("#" + formId + " :checkbox").attr("checked", false);
			}
		});
	}
}

function opr_batchAudit(url, formId, authId, tip) {
	var checked = $("#" + formId + " :checkbox:checked").not($('#box')).length;
	if (checked === 0) {
		jAlert('请选择需要' + (tip ? tip : '审核') + '的计划！！！', "办公室自动化系统");
	} else {
		jConfirm("确认要" + (checked > 1 ? "批量" : "") + (tip ? tip : "审核")
				+ checked + "条计划吗？", "办公室自动化系统", function(result) {
			if (result) {
				$("#" + formId + "").attr('action', url);
				$("#" + formId + "").submit();
			} else {
				$("#" + formId + " :checkbox").attr("checked", false);
			}
		});
	}
}

// 先调用callback函数后remove对话框
function myjConfirm(title, msg, callback) {
	if (!$("div").is("#dialog_alert")) {
		$("<div id='dialog_alert' title='" + title + "'>" + msg + "</div>")
				.hide().appendTo(document.body);
	}
	$("#dialog_alert").dialog( {
		draggable : false,
		autoOpen : true,
		resizable : false,
		width : 300,
		minHeight : 150,
		modal : false,
		open : function(event, ui) {
			$(".ui-widget-overlay").css("width", "100%");
			$("button").each(function(i) {
				if ($(this).html() == "取消")
					this.focus();
			})
		},
		close : function(event, ui) {
			$("#dialog_alert").dialog("destroy").remove();
		},
		buttons : {
			取消 : function() {
				if (callback)
					callback(false);
				$(this).dialog('close').remove();
			},
			确定 : function() {
				if (callback)
					callback(true);
				$(this).dialog('close').remove();
			}
		}
	})
}

// 批量审批并：每条记录赋予相同的审批备注，页面需要有id为"remark"的Text控件
function opr_batchAuditWithRemark(url, formId, authId, tip) {
	var checked = $("#" + formId + " :checkbox:checked").not($('#box')).length;
	if (checked === 0) {
		myjConfirm("办公室自动化系统", '请选择需要' + (tip ? tip : '审核') + '的记录！！！',
				null);
	} else {
		myjConfirm(
				"办公室自动化系统",
				"确认要"
						+ (checked > 1 ? "批量" : "")
						+ (tip ? tip : "审核")
						+ checked
						+ "条记录吗？<br/><br/>"
						+ (tip ? tip : "审核")
						+ "意见：<br/><textarea id='auditRemark' name='auditRemark' cols='40' rows='4'></textarea>",
				function(result) {
					if (result) {
						$("#" + formId + "").attr('action', url);
						$("#remark").val($("#auditRemark").val());
						$("#" + formId + "").submit();
					} else {
						$("#" + formId + " :checkbox").attr("checked", false);
					}
				});
	}
}

function prepURL(url, argName, argVal) {
	if (url.indexOf('?') == -1) {
		url += "?" + argName + "=" + argVal;
	} else {
		url += "&" + argName + "=" + argVal;
	}
	return url;
}
function getCheckedValue(formId) {
	return $("#" + formId + " :checkbox:checked").not($('#box')).val();
}

// post url data是一个 Object {parma1:value1}
// eg.gotoPost({contentPath : name},ctx+'/****.action');
function gotoPost(data, url) {
	$('#jsf').remove();
	$("body").append("<div id='jsf' style='display:none;'></div>");
	$('#jsf').append(
			"<form id='jsForm' method='post' action='" + url + "'></form>");
	for ( var key in data) {
		$('#jsForm').append(
				"<input name='" + key + "' value='" + data[key] + "'>");
		// alert(key+'->'+data[key]);
	}
	$('#jsForm')[0].submit();
}
/**
 * 通用下载用方法
 * @param url 文件名称+后缀
 */
function downfile(path){
	gotoPost({contentPath : path},ctx+'/static-content?download=true');
}
/**
 * 年月自定义标签
 * 
 * @param sel
 * @param name
 * @return
 */
function selYearMonth(sel, name) {
	$("[id=" + name+"]").val($("[id="+name+"_year]").val() + "" + $("[id="+name+"_month]").val());
}

/**
 * js replaceAll方法
 * 
 * @param s1 被替换的字符串
 * @param s2 替换成字符串
 * @return
 */
String.prototype.replaceAll =function(s1,s2) { 
    return this.replace(new RegExp(s1,"gm"),s2); 
};

// 统一默认注册提示信息框
$(document).ready(function() {
	// ajax请求时，loading...
	if(top.location==self.location){
		$("#mainload").ajaxStart(function(){
			$(this).show();
		});
		$("#mainload").ajaxStop(function(){
			$(this).hide();
		});
	}else{
		$("body").ajaxStart(function(){
			parent.$("#mainload").show();
		});
		$("body").ajaxStop(function(){
			parent.$("#mainload").hide();
		});
	}
	//去掉所有input的左右空格
	$("input[type!='hidden']").change(function(){
		$(this).val($.trim($(this).val()));
	});
	// 提示
	$(".tip").bt({
		contentSelector : "$(this).parent().next().children().eq(0).attr('tip')", // 取值
		// trigger: "click",
		padding : 8,
		width : 200,
		spikeLength : 5,
		spikeGirth : 8,
		cornerRadius : 2,
		fill : "#E4EEEE",
		strokeWidth : 1,
		strokeStyle : "#A5D1EC",
		cssStyles : {fontSize : '12px',	color : 'green'},
		positions : [ "right", "left", "top", "bottom" ],
		hoverIntentOpts : {interval : 0,timeout : 0}
	});
	var cookieCss = $.cookie("show_hidden");
	if (cookieCss != null) {
		$("#item").removeClass().addClass(cookieCss);
		if (cookieCss == 'item') {
			$("#contral img").attr("src",
					ctx + "/images/f_close.gif").attr("title",
					"收起查询面板");
		} else {
			$("#contral img").attr("src",
					ctx + "/images/f_open.gif").attr("title",
					"展开查询面板");
		}
	}

	// 算出固定上部分的高度
	$(".container").css("margin-top",
			$("#fixtitle").innerHeight());

	// 遇到ie6下当iframe出现纵向滚动条时同时会出现横向滚动条
	if ($.browser.msie
			&& ($.browser.version == "6.0" || $.browser.version == "7.0")) {
		$('#indiv').height(
				($('#indiv').innerHeight() + 17) + 'px');
		// 报表日期选择标签的样式控制
		$("#select_date").removeClass("and2").addClass("and4");
		$("#selectdate_label").css("padding-top", "3px");
	}
	//全局的后台操作结果信息提示
	//if ($("div").is("#actionMessage")) {
	//    var msg=$("#actionMessage span").html();
	//    jAlert(msg,'操作提示');
	//}
	
});