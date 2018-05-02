<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/common/taglibs.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>客户查询后台</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style type="text/css">
#error_l{font-size: 12px;text-align: left; color:#ff0000;;margin-top: -10px; vertical-align: bottom;height: 20px}
body{margin: 0;overflow: hidden;}
.STYLE1 {color: #000000;font-size: 12px;}
.STYLE3 {color: #528311; font-size: 12px;}
.STYLE4 {color: #42870a;font-size: 12px;}
</style>
<script type="text/javascript" src="${ctx}/js/jQuery/jquery.1.4.2.js"></script>
<script src="${ctx}/js/jQuery/validate/jquery.validate.js" type="text/javascript"></script>
<script type="text/javascript">

    var web = document.location;
    if (top.location !== self.location) {
      top.location.href="${ctx}/";
    }
    $(function(){
        $("#j_username").focus();
        $('#submit').hover(function(){
            $(this).attr('src','${ctx}/images/login/login_01_c.jpg');
        },function(){
            $(this).attr('src','${ctx}/images/login/login_01_r.jpg');
        }).submit();
    });
</script>
</head>
<body>
<form id="loginForm" action="${ctx}/securityLogin.action" method="post">
<table width="100%" height="100%" border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td  height="30" bgcolor="#fdfdc8">&nbsp;</td>
  </tr>
  <tr>
    <td height="608" background="${ctx}/images/login/login_03.gif"><table width="862" border="0" align="center" cellpadding="0" cellspacing="0">
      <tr>
        <td height="266" background="${ctx}/images/login/login_04.png">&nbsp;</td>
      </tr>
      <tr>
        <td height="95">
        <table id="tb" width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="424" height="95" background="${ctx}/images/login/login_06.gif">&nbsp;</td>
            <td width="183" background="${ctx}/images/login/login_07.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="21%" height="30"><div align="center"><span class="STYLE3">用户</span></div></td>
                <td width="79%" height="30">
                <input type='text' id='j_username' name='j_username' class="required input" 
                <s:if test="not empty param.error"> value='${sessionScope.SPRING_SECURITY_LAST_USERNAME}'</s:if>/>
                </td>
              </tr>
              <tr>
                <td height="30"><div align="center"><span class="STYLE3">密码</span></div></td>
                <td height="30"><input type='password' id='j_password' name='j_password' class="required input"/></td>
              </tr>
              <tr>
                <td height="30">&nbsp;</td>
                <td height="30">
                <input type="image" name="Submit"  src="${ctx}/images/login/login.gif" /><img src="${ctx}/images/login/reset.gif" width="41" height="22" border="0" onclick="document.getElementById('loginForm').reset()" style="cursor: pointer;" /><span class="STYLE1" style="height: 25px;line-height: 25px;vertical-align:top;"><input type="checkbox" name="_spring_security_remember_me" />记住密码</span>
                </td>
              </tr>

            </table></td>
            <td width="255" background="${ctx}/images/login/login_08.gif">&nbsp;</td>
          </tr>
        </table>
        </td>
      </tr>
      <tr>
        <td height="247" valign="top" background="${ctx}/images/login/login_09.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="22%" height="30">&nbsp;</td>
            <td width="56%" style="text-align: right;font-size: 12px;color: red;padding-right:65px">${param.error!=null?"您输入的帐号或密码错误，请确认":""}</td>
            <td width="22%">&nbsp;</td>
          </tr>
          <tr>
            <td>&nbsp;</td>
            <td height="30"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="44%" height="20">&nbsp;</td>
                <td width="56%" class="STYLE4">版本 2014V1.0 </td>
              </tr>
            </table></td>
            <td>&nbsp;</td>
          </tr>
        </table>
        </td>
      </tr>
    </table></td>
  </tr>
  <tr>
    <td bgcolor="#e7ed4d">&nbsp;</td>
  </tr>
</table>
</form>
<map name="Map">
<area shape="rect" coords="3,3,36,19" id="submit" href="#" />
<area shape="rect" coords="40,3,78,18" id="reset" href="#" />
</map>
</body>
</html>
