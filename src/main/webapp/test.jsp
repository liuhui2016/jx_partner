<%@ page language="java" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<body>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>	
 <script type="text/javascript" src="${pageContext.request.contextPath}/jquery.js"></script>
<script type="text/javascript">
/* 	
	function requestJson(){

	
	var jsonObj=JSON.stringify({"userid":"8","name":"hehehe","phone":"123456","area":"hehehehehehe","detail":"hahahahahaha","code":"750000","isdefault":"0"});
	$.ajax({
		type:'POST',
		url:'${pageContext.request.contextPath}/smvc/user/test/modifyAddress.v',
		contentType:'application/json;charset=utf-8',
		data:jsonObj,
		success:function(data){
			alert(data);
		}
		
	})

	}
	 */
 	 function requestJson1(){
 		var jsonObj=JSON.stringify({"ordno":"027450523181628","prono":"0a7e9758-aee1-419d-8f64-edfbb34f8ed0"});
		$.ajax({
			type:'POST',
			url:'${pageContext.request.contextPath}/smvc/setup/waterQuantity.v',
			contentType:'application/json;charset=utf-8',
			data:jsonObject,
			success:function(data){
				alert(data);
			}
			
		})

		} 
 
	function requestJson2(){

		var aaa=$("#username").val();
		var bbb=$("#username1").val();
		alert(aaa);
		alert(bbb);
		var jsonObject=JSON.stringify({"type":aaa,"type1":bbb});
		$.ajax({
			type:'POST',
			url:'${pageContext.request.contextPath}/smvc/setup/waterQuantity.v',
			contentType:'application/json;charset=utf-8',
			data:jsonObject,
			success:function(data){
				alert(data);
			}
			
		})

		}

</script>




</head>
<body>

	<input type="button" value="传入JSON" onclick="requestJson2()"> 
	<input type="text" id="username"/>
	<input type="text" id="username1"/>
		
<%-- <form enctype="multipart/form-data" action="${pageContext.request.contextPath }/TestServlet" method="post" >
		<input type="text" name="name"/><br/>
		<input type="file" name="photo"/><br/>
		<input type="file" name="photo"/><br/>
		<input type="submit" value="上传"/><br/>
</form>
 --%>
</body>
</html>
</body>
</html>