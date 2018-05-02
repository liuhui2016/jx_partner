package com.game.smvc.util;
/*
 * 不支持多文件上传的上传文件工具类
 */
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;


public class FileUploadUtil {
	
	
	public String uploadPictrue(HttpServletRequest request,String picPath)
			throws Exception {

		// 要执行文件上传的操作
		
		boolean isMultipartContent = ServletFileUpload.isMultipartContent(request);
		if (!isMultipartContent) {
			throw new RuntimeException("上传格式不是 multipart/form-data");
		}
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload sfu = new ServletFileUpload(factory);
	// 解析request对象，并得到一个表单项的集合
		String path=null;
		try {
			List<FileItem> fileItems = sfu.parseRequest(request);
		for (FileItem fileitem : fileItems) {
				 path =path+ processUploadField(fileitem,request,picPath);

			}

		}  catch (FileUploadException e) {
			e.printStackTrace();
		}
		return path;
	}


	
	private String processUploadField(FileItem fileitem,HttpServletRequest request,String picPath) throws Exception{
			// 得到文件输入流
			InputStream is = fileitem.getInputStream();
		   // 创建一个文件存盘的目录
			String directoryRealPath = picPath;
			File storeDirectory = new File(directoryRealPath);// 即代表文件又代表目录
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			// 得到上传的名子
			String filename = fileitem.getName();// 文件项中的值 F:\图片素材\小清新\43.jpg 或者
			System.out.println(filename);
			if (filename != null) {
//				解决不同浏览器上传时绝对路径不同问题
				filename = FilenameUtils.getName(filename);// 效果同上
			}
		// 解决文件同名的问题
			filename = UUID.randomUUID() + "_" + filename;
			// 目录打散
			String childDirectory = makeChildDirectory(storeDirectory, filename); // a/b
		// 上传文件，自动删除临时文件
			fileitem.write(new File(storeDirectory, childDirectory
					+ File.separator + filename));
			fileitem.delete();
			return childDirectory;
	}



	// 目录打散
	private String makeChildDirectory(File storeDirectory, String filename) {
		int hashcode = filename.hashCode();// 返回字符转换的32位hashcode码
		String code = Integer.toHexString(hashcode); // 把hashcode转换为16进制的字符
		String childDirectory = code.charAt(0) + File.separator
				+ code.charAt(1); // a/b
		// 创建指定目录
		File file = new File(storeDirectory, childDirectory);
		if (!file.exists()) {
			file.mkdirs();
		}
		return childDirectory;
	}


}
