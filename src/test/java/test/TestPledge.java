package test;

/**
 * 测试
 * @author Administrator
 *
 */
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import java.net.URL;




public class TestPledge {

	public static void main(String[] args) throws Exception {
		StringBuffer sb = new StringBuffer();
		
/*		sb.append("{\"TRANSID\": \"8\",");
		sb.append("\"searchDate\": \"2017-03-27\"}");*/
		
/*		sb.append("{\"TRANSID\": \"11\",");
		sb.append("\"orderCount\": \"5\"}");*/
		
//		sb.append("{\"TRANSID\": \"3\",");
//		sb.append("\"numbers\": \"1,4,10\",");
//		sb.append("\"perNum\": \"2\",");
//		sb.append("\"location\": \"1,2,3,4,5\"}");
		
		
		sb.append("{\"TRANSID\": \"22\"}");
		
		getPostData(sb.toString());

	}
	

	private static void getPostData(String data) throws Exception {
		
		//String url = "http://192.168.1.44:8080/jx_smart/jx_smart/smvc/user/test/untabletbinding";
//		String url = "http://127.0.0.1:8080/CaiPiao_AG/xyft/data.html";
		
		//平板解绑:                                                                      
		//http://192.168.1.44:8080/jx_smart/smvc/user/test/untabletbinding 
		//解绑回调:
		String url = "http://ojb1sflri.bkt.clouddn.com/Smarthome_201704201800.apk";


		byte[] bits = data.getBytes("UTF-8");
		URL httpUrl = new URL(url);
		HttpURLConnection hc = (HttpURLConnection) httpUrl.openConnection();
		hc.setDoOutput(true);
		hc.setDoInput(true);
		hc.setRequestMethod("POST");
		hc.setRequestProperty("Content-Type", "application/octet-stream");
		if (bits != null)
			hc.setRequestProperty("Content-Length", bits.length + "");
		OutputStream out = hc.getOutputStream();
		if (bits != null)
			out.write(bits);
		out.flush();
		InputStream input = hc.getInputStream();
		ByteArrayOutputStream byteArr = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int k = 0;
		while ((k = input.read(bytes)) != -1) {
			byteArr.write(bytes, 0, k);
		}
		byteArr.flush();
		byte[] returnDatas = byteArr.toByteArray();
		byteArr.close();
		input.close();
		out.close();
		System.out.println(new String(returnDatas, "UTF-8"));
	}
}
