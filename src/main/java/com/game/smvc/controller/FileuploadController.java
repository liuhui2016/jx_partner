package com.game.smvc.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONObject;
import com.game.bmanager.util.Constants;
import com.game.smvc.entity.result.DataResult;
import com.game.smvc.entity.result.Errors;
import com.game.smvc.entity.result.Result;
import com.game.util.pay.AliSignUtils;

public class FileuploadController extends HttpServlet {
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String picUrl = Constants.CONS_PROPERTIES.getValue("PIC_URL");
		// 要执行文件上传的操作
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			throw new RuntimeException("your form is not multipart/form-data");
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
	// 解析request对象，并得到一个表单项的集合
		try {
			List<FileItem> fileItems = sfu.parseRequest(request);
		for (FileItem fileitem : fileItems) {
				if (fileitem.isFormField()) {
					// 普通表单项
					processFormField(fileitem);
				} else {
					// 上传表单项
					String Childurl = processUploadField(fileitem,request);
					picUrl=picUrl+"/"+Childurl;
					List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				
					Map<String,Object> map = new HashMap<String, Object>();
					map.put("imgUrl", picUrl);
					list.add(map);
					response.getWriter().print(new DataResult(Errors.OK,list));
					return;
				}
			}

		}  catch (Exception e) {
			System.out.println("00");
			String jsonString = JSONObject.toJSONString(new Result(Errors.EXCEPTION_UNKNOW));
			response.getWriter().print(jsonString);
		}
		

		
	}


	
	private String processUploadField(FileItem fileitem,HttpServletRequest request) throws Exception{
		String childDirectory=null;

			// 得到文件输入流
			InputStream is = fileitem.getInputStream();

			// 创建一个文件存盘的目录
//			String directoryRealPath = this.getServletContext().getRealPath(
//					"/picture");
			String directoryRealPath=Constants.CONS_PROPERTIES.getValue("PIC_PATH");
			File storeDirectory = new File(directoryRealPath);// 即代表文件又代表目录
			
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			System.out.println(storeDirectory.getAbsolutePath());
			// 得到上传的名子
			String filename = fileitem.getName();// 文件项中的值 F:\图片素材\小清新\43.jpg 或者
			if (filename != null) {
//				解决不同浏览器上传时绝对路径不同问题
				filename = FilenameUtils.getName(filename);// 效果同上
			}
			SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",Locale.getDefault());
	        Date date = new Date();
	        String key = format.format(date);
			key=key+(int) (Math.random()*10000) + 1;
			// 解决文件同名的问题
			filename =key + "_" + filename;
			childDirectory = makeChildDirectory(storeDirectory, filename);
			String imgurl=childDirectory+"/"+filename;
			// 上传文件，自动删除临时文件
			fileitem.write(new File(storeDirectory, childDirectory
					+ File.separator + filename));
			fileitem.delete();
		return imgurl;
	}

	// 上传表单项
	private void processUploadField1(FileItem fileitem) {

		try {
			// 得到文件输入流
			InputStream is = fileitem.getInputStream();

			// 创建一个文件存盘的目录
//			String directoryRealPath = this.getServletContext().getRealPath(
//					"/WEB-INF/upload");	
			String directoryRealPath=Constants.CONS_PROPERTIES.getValue("PIC_PATH");
			File storeDirectory = new File(directoryRealPath);// 即代表文件又代表目录
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			// 得到上传的名子
			String filename = fileitem.getName();// 文件项中的值 F:\图片素材\小清新\43.jpg 或者
													// 43.jpg
			// 处理文件名
			/*
			 * 解决此问题： F:\\apache-tomcat-7.0.52\\webapps\\day18_00_upload\\upload\\F:\\图片素材\\小清新\\41.jpg
			 */
			if (filename != null) {
				// filename =
				// filename.substring(filename.lastIndexOf(File.separator)+1);
				filename = FilenameUtils.getName(filename);// 效果同上
			}

			// 解决文件同名的问题
			filename = UUID.randomUUID() + "_" + filename;

			// 目录打散
			// String childDirectory = makeChildDirectory(storeDirectory); //
			// 2015-10-19
			String childDirectory = makeChildDirectory(storeDirectory, filename); // 2015-10-19

			// 在storeDirectory下，创建完整目录下的文件
			File file = new File(storeDirectory, childDirectory
					+ File.separator + filename); // 绝对目录/日期目录/文件名
			// 通过文件输出流将上传的文件保存到磁盘
			FileOutputStream fos = new FileOutputStream(file);

			int len = 0;
			byte[] b = new byte[1024];
			while ((len = is.read(b)) != -1) {
				fos.write(b, 0, len);
			}
			fos.close();
			is.close();

			fileitem.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 目录打散
	private String makeChildDirectory(File storeDirectory, String filename) {
		int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码

		String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符

		String childDirectory = code.charAt(0) + "/"
				+ code.charAt(1); // a/b
		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}

	private void processFormField(FileItem fileitem) {
		try {
			String fieldname = fileitem.getFieldName();// 字段名
			String fieldvalue = fileitem.getString("UTF-8");// 字段值
			//fieldvalue = new String(fieldvalue.getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
