package com.game.bmanager.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import com.game.bmanager.entity.ApkEntry;
import com.game.smvc.core.util.FileUtil;

/**
 * Apk反编译解析工具类
 * 
 * @author Crap
 */
public class ApkUtil {
	
	/**
     * 解析获取Apk实体
     *
     * @param apk Part servlet上传文件
     * @param outputPath String 输出文件夹
     * @param filename String 保存文件名
     * 
     * @author Crap
     */
	public static ApkEntry getApkEntry(File apk, String outputPath, String filename){
		final ApkEntry entry = new ApkEntry();
		try {
			String path = outputPath +"/"+filename;
			//设置apk 服务器路径
			entry.setApkPath(path);
			//entry.setApkPath("E:\test");
			entry.setApkSize(apk.length()+"");
			FileUtil.createDirs(outputPath, true);
			File apkFile = new File(path);
			FileUtils.copyFile(apk, apkFile);
			
			unZipApk(outputPath, filename);
			String fileDirName = filename.substring(0, filename.length() - 4);
			String apkDir = outputPath+"/"+fileDirName;
			File manifest = new File(apkDir+"/AndroidManifest.xml");
			
			//从apktool.yml中解析版本号
			BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(apkDir+"/apktool.yml")));
			String line;
			while((line = read.readLine()) != null){
				if(line.contains("versionName")){
					entry.setApkVersion(line.split(": ")[1].replace("'", ""));
				}
				if(line.contains("versionCode")){
					entry.setVersionCode(line.split(": ")[1].replace("'", ""));
				}
			}
			read.close();
			
			//查找apktool解析出来的变量
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document document = builder.parse(manifest);
	
			XPath xpath = XPathFactory.newInstance().newXPath();
			entry.setApkPackage(xpath.evaluate("/manifest/@package", document));
			
			final String apkIcon = xpath.evaluate("//application/@icon", document).split("/")[1];
			String apkName = xpath.evaluate("//application/@label", document).split("/")[1];
			File value = new File(apkDir+"/res/values/strings.xml");
			//从strings.xml中解析出appName
			document = builder.parse(value);
			entry.setApkName(xpath.evaluate("//resources/string[@name='"+apkName+"']", document));
			
			//查找icon文件
			FileUtil.getIcon(new File(apkDir+"/res"), apkIcon, entry);
			
			//拷贝出icon
			FileUtil.copyFile(entry.getApkIconPath(), apkDir+"/"+entry.getApkIcon().getName(), true);
			//设置icon,apk MD5
			entry.setApkIconMd5(OpUtil.encryptMD5File(new FileInputStream(entry.getApkIcon())));
			entry.setApkMd5(OpUtil.encryptMD5File(new FileInputStream(new File(path))));
			//设置icon,apk 相对于服务器路径
			String apkPath = Constants.CONS_PROPERTIES.getValue("apk.url");
			entry.setApkIconUrl(apkPath +"/"+fileDirName+"/"+entry.getApkIcon().getName());
			entry.setApkUrl(apkPath +"/"+filename);
			//清除目录下其他文件
			new File(apkDir).listFiles(new FileFilter() {
				@Override
				public boolean accept(File file) {
					if(!file.getName().equals(entry.getApkIcon().getName())){
						if(file.isDirectory()){
							try {
								FileUtil.deleteDir(file);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}else{
							file.delete();
						}
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return entry;
	}
	
	/**
     * apktool反编译apk
     *
     * @param path String
     * @param filename String
     * @throws IOException
     * 
     * @author Crap
     */
	public static void unZipApk(String path, String filename) throws IOException{
		String[] cmd = {"java", "-jar", path+"/apktool.jar", "d", "-f", path+"/"+filename, "-o", path+"/"+filename.substring(0, filename.length() - 4)};
		Process pc = Runtime.getRuntime().exec(cmd);
		BufferedReader err = new BufferedReader(new InputStreamReader(pc.getErrorStream()));
		String line;
		while((line = err.readLine()) != null){
			System.out.println(line);
		}
		err.close();
	}

	public static void main(String[] args) {
		ApkUtil.getApkEntry(null, "D:/IDE/apache-tomcat-7.0.57/webapps/software", "com.anjuke.android.app_025804.apk");
	}

    public static ApkEntry saveIOSApk(File apk, String outputPath, String filename) {
        ApkEntry entry = new ApkEntry();
        String path = outputPath +"/"+filename;
        try {
            //设置apk 服务器路径
            entry.setApkPath(path);
            entry.setApkSize(apk.length()+"");
            FileUtil.createDirs(outputPath, true);
            File apkFile = new File(path);
            FileUtils.copyFile(apk, apkFile);
            String apkPath = Constants.CONS_PROPERTIES.getValue("apk.url");
            entry.setApkUrl(apkPath +"/"+filename);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        return entry;
    }
}
