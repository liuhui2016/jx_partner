package com.game.smvc.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
	public static String getFileName(String fullFilePath) {
		if (fullFilePath == null) {
			return "";
		}
		int index1 = fullFilePath.lastIndexOf('/');
		int index2 = fullFilePath.lastIndexOf('\\');

		int index = index1 > index2 ? index1 : index2;
		if (index == -1) {
			return fullFilePath.substring(0, fullFilePath.lastIndexOf("."));
		}
		String fileName = fullFilePath.substring(index + 1,
				fullFilePath.lastIndexOf("."));
		return fileName;
	}

	public static List<File> getDirFiles(File dir) {
		List<File> files = new ArrayList();
		if (dir.isDirectory()) {
			for (File file : dir.listFiles()) {
				files.addAll(getDirFiles(file));
			}
		}
		if (dir.isFile()) {
			files.add(dir);
		}
		return files;
	}

	public static ByteOutputStream getByteOutputStream(InputStream inputStream)
			throws IOException {
		BufferedInputStream in = new BufferedInputStream(inputStream);
		ByteOutputStream out = new ByteOutputStream(1024);

		byte[] block = new byte[2048];
		int size = 0;
		while ((size = in.read(block)) != -1) {
			out.write(block, 0, size);
		}
		in.close();
		out.close();
		return out;
	}
}
