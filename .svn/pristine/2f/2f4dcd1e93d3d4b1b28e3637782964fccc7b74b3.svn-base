<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>${id==null?'新增产品':'修改产品' }</title>
<%@ include file="/common/head.jsp" %>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<link href="${ctx}/js/jQuery/validate/jquery.validate.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="${ctx}/js/jQuery/ui/jquery.ui.datepicker-zh-CN.js"></script>
<style>
.gonggao{
			    position:absolute;
			    top:30px;
			    left:700px;
			    width:100px;
			    height:100px;
			    border:1px solid #993300;
			    background-color:#ffffff;
			}
			.gonggao h1{
			    line-height:50px;
			    font-size:24px;
			    font-family:"黑体";
			    font-weight:bold;
			    text-align:center;
			}
			.gonggao div{
			    line-height:30px;
			    font-size:18px;
			    font-family:"黑体";
			    text-align:center;
			    color:#993300;
			}
			.gonggao div a,.gonggao div a:visited{
			    color:#993300;
			}
</style>

<script>
function clsGG(srcpath) {
	//alert(srcpath);
	window.document.getElementById("viewimg").src = srcpath;
	document.all("gonggao").style.display = "block";
}
function closediv() {
	document.all("gonggao").style.display = "none";
}

</script>
</head>
<body>
<div class="currloca">
  <p>${auth.fullMenu}${id==null?'&nbsp;»&nbsp;<span>新增产品</span>':'&nbsp;»&nbsp;<span>修改产品</span>' }</p>
  <div class="sitemap">
  	<span style="display:block;float:left"><s:actionmessage theme="custom"/></span>
    <span id="showMap"><img onclick="showMap();return false;" id="sMap" class="pointer" width="64" height="18" title="后台导航" src="${ctx}/images/map.gif"/></span>
  </div>
</div>
<div class="container">
  <!-- 内容区域 -->
  <div class="itemtitle"><h2>${id==null?'新增产品':'修改产品' }</h2></div>
      <!-- 附加信息-->
  <div id="message" class="message">
    <span style="font-weight: bold;font-size: 14px;"><!-- 提示：资源列表... --></span>
  </div>

  <form id="inputForm" action="prototal!save.action" method="post" enctype="multipart/form-data" >
	  <div id="gonggao" class="gonggao" style="display: none">
				<img name="viewimg" id="viewimg" style="width: 50; height: 50" />
				<a href="javascript:closediv();">关闭</a>
			</div>
	  <!-- 列表区域-->
	  <div id="content" class="content input">
	    <div id="indiv" style="width:100%;OVERFLOW-X:auto;">
	    </div>
	    	<input type="hidden" name="id" value="${id}"/>
			<input type="hidden" name="authId" value="${authId}"/>
			<table class="tab_cont" width="" cellspacing="0" cellpadding="0" border="0" align="left">
				<tbody>
				    <tr>
				      <th class="first" width="130">标准信息</th>
				      <th class="last" colspan="3"></th>
				    </tr>
				    <tr>
						<td class="right">产品分类:</td>
						<td>
							<select id="prot_type" name="prot_type">
									<option value='1' <c:if test='${prot_type== "1"}'>selected='selected'</c:if>>壁挂净水机</option> 
									<option value='2' <c:if test='${prot_type== "2"}'>selected='selected'</c:if>>台式净水机</option>
									<option value="3" <c:if test='${prot_type== "3"}'>selected='selected'</c:if>>立式净水机</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td class="right">图片url:</td>
						<td colspan="3">
							<input type="file" id="iconfile" name="iconfile" value="${pic_url}" style="width: 320px" />
							<input type="hidden" id="pic_url" name="pic_url" value="${pic_url}" />
							<c:if test="${pic_url != null && pic_url != ''}">
								&nbsp;&nbsp;<a href="#" id="icondelete" onclick="clsGG('${pic_url}');">查看原图</a>
							</c:if>
						</td>
					</tr>
					
					<tr>
						<td class="right">产品分类名称:</td>
						<td colspan="3">
							<input type="text" id="prot_name" name="prot_name" value="${prot_name}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right">产品型号:</td>
						<td colspan="3">
							<input type="text" id="prod_typename" name="prod_typename" value="${prod_typename}" style="width: 320px" />
						</td>
					</tr>
<!-- 					<tr> -->
<!-- 						<td class="right">产品类别图片id:</td> -->
<!-- 						<td colspan="3"> -->
							<input type="hidden" id="prot_picid" name="prot_picid" value="${prot_picid}" style="width: 320px" />
<!-- 						</td> -->
<!-- 					</tr> -->
					
					<tr>
						<td class="right">额定电压/功率:</td>
						<td colspan="3">
							<input type="text" id="prod_hz" name="prod_hz" value="${prod_hz}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">加热功率:</td>
						<td colspan="3">
							<input type="text" id="prod_w" name="prod_w" value="${prod_w}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">进水压力:</td>
						<td colspan="3">
							<input type="text" id="prod_mpa" name="prod_mpa" value="${prod_mpa}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">环境温度:</td>
						<td colspan="3">
							<input type="text" id="prod_c" name="prod_c" value="${prod_c}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">净水流量:</td>
						<td colspan="3">
							<input type="text" id="prod_hl" name="prod_hl" value="${prod_hl}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">过滤方式:</td>
						<td colspan="3">
							<input type="text" id="prod_fl" name="prod_fl" value="${prod_fl}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">使用水范围:</td>
						<td colspan="3">
							<input type="text" id="prod_wt" name="prod_wt" value="${prod_wt}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">出水水质:</td>
						<td colspan="3">
							<input type="text" id="prod_iw" name="prod_iw" value="${prod_iw}" style="width: 320px" />
						</td>
					</tr>
					<tr>
						<td class="right">产品毛重:</td>
						<td colspan="3">
							<input type="text" id="prod_wx" name="prod_wx" value="${prod_wx}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right">产品净重:</td>
						<td colspan="3">
							<input type="text" id="prod_wd" name="prod_wd" value="${prod_wd}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right">产品尺寸:</td>
						<td colspan="3">
							<input type="text" id="prod_sz" name="prod_sz" value="${prod_sz}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right">包装尺寸:</td>
						<td colspan="3">
							<input type="text" id="prod_szi" name="prod_szi" value="${prod_szi}" style="width: 320px" />
						</td>
					</tr>
					
					<tr>
						<td class="right">产品状态:</td>
						<td>
							<select id="prot_status" name="prot_status">
									<option value='0' <c:if test='${prot_status== "0"}'>selected='selected'</c:if>>上架</option> 
									<option value='1' <c:if test='${prot_status== "1"}'>selected='selected'</c:if>>下架</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<th class="first" width="130"></th>
						<th class="last" colspan="3">
							<input class="button" type="submit" value="提交"/>&nbsp;
							<input class="button" type="button" value="返回" onclick="history.back()"/>
						</th>
					</tr>
				</tbody>
			</table>
	    </div>
  </form>
</div>
</body>
</html>