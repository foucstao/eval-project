package com.base.common.config;

import java.io.File;

import lombok.Data;

@Data
public class FileDirConfig {

	public static String sysDir;
	/** 根目录位置 */
	public static String baseDir;

	/** 上传的临时文件生成位置 */
	public static String tempDir;

	/** 导入模板下载位置 */
	public static String templateDir;

	public static void setDir() {
		sysDir = System.getProperty("user.dir");
		baseDir = System.getProperty("user.dir") + "/personImg/";
		tempDir = baseDir + "temp/";
		templateDir = baseDir + "template/";

		createDir(baseDir);
		createDir(tempDir);
		createDir(templateDir);
	}

	private static void createDir(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			file.mkdir();
		}
	}
}
