package com.base.common.utils.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;

import com.base.common.exception.BaseException;
import com.base.common.utils.StringUtils;

/**
 * 文件处理工具类
 * 
 */
public class FileUtils extends org.apache.commons.io.FileUtils {
	/** 字符常量：斜杠 {@code '/'} */
	public static final char SLASH = '/';

	/** 字符常量：反斜杠 {@code '\\'} */
	public static final char BACKSLASH = '\\';

	public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

	private static int tempLength = 1024 * 4;

	/**
	 * 输出指定文件的byte数组
	 * 
	 * @param filePath 文件路径
	 * @param os       输出流
	 * @return
	 */
	public static void writeBytes(String filePath, OutputStream os) throws IOException {
		FileInputStream fis = null;
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new FileNotFoundException(filePath);
			}
			fis = new FileInputStream(file);
			byte[] b = new byte[1024];
			int length;
			while ((length = fis.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath 文件
	 * @return
	 */
	public static boolean deleteFile(String filePath) {
		boolean flag = false;
		File file = new File(filePath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 文件名称验证
	 * 
	 * @param filename 文件名称
	 * @return true 正常 false 非法
	 */
	public static boolean isValidFilename(String filename) {
		return filename.matches(FILENAME_PATTERN);
	}

	/**
	 * 检查文件是否可下载
	 * 
	 * @param resource 需要下载的文件
	 * @return true 正常 false 非法
	 */
	public static boolean checkAllowDownload(String resource) {
		// 禁止目录上跳级别
		if (StringUtils.contains(resource, "..")) {
			return false;
		}

		// 检查允许下载的文件规则
		if (ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, FileTypeUtils.getFileType(resource))) {
			return true;
		}

		// 不在允许下载的文件规则
		return false;
	}

	/**
	 * 下载文件名重新编码
	 * 
	 * @param request  请求对象
	 * @param fileName 文件名
	 * @return 编码后的文件名
	 */
	public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException {
		final String agent = request.getHeader("USER-AGENT");
		String filename = fileName;
		if (agent.contains("MSIE")) {
			// IE浏览器
			filename = URLEncoder.encode(filename, "utf-8");
			filename = filename.replace("+", " ");
		} else if (agent.contains("Firefox")) {
			// 火狐浏览器
			filename = new String(fileName.getBytes(), "ISO8859-1");
		} else if (agent.contains("Chrome")) {
			// google浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		} else {
			// 其它浏览器
			filename = URLEncoder.encode(filename, "utf-8");
		}
		return filename;
	}

	/**
	 * 返回文件名
	 *
	 * @param filePath 文件
	 * @return 文件名
	 */
	public static String getName(String filePath) {
		if (null == filePath) {
			return null;
		}
		int len = filePath.length();
		if (0 == len) {
			return filePath;
		}
		if (isFileSeparator(filePath.charAt(len - 1))) {
			// 以分隔符结尾的去掉结尾分隔符
			len--;
		}

		int begin = 0;
		char c;
		for (int i = len - 1; i > -1; i--) {
			c = filePath.charAt(i);
			if (isFileSeparator(c)) {
				// 查找最后一个路径分隔符（/或者\）
				begin = i + 1;
				break;
			}
		}

		return filePath.substring(begin, len);
	}

	/**
	 * 是否为Windows或者Linux（Unix）文件分隔符<br>
	 * Windows平台下分隔符为\，Linux（Unix）为/
	 *
	 * @param c 字符
	 * @return 是否为Windows或者Linux（Unix）文件分隔符
	 */
	public static boolean isFileSeparator(char c) {
		return SLASH == c || BACKSLASH == c;
	}

	/**
	 * 下载文件名重新编码
	 *
	 * @param response     响应对象
	 * @param realFileName 真实文件名
	 * @return
	 */
	public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
		String percentEncodedFileName = percentEncode(realFileName);

		StringBuilder contentDispositionValue = new StringBuilder();
		contentDispositionValue.append("attachment; filename=").append(percentEncodedFileName).append(";").append("filename*=").append("utf-8''").append(percentEncodedFileName);

		response.setHeader("Content-disposition", contentDispositionValue.toString());
	}

	/**
	 * 百分号编码工具方法
	 *
	 * @param s 需要百分号编码的字符串
	 * @return 百分号编码后的字符串
	 */
	public static String percentEncode(String s) throws UnsupportedEncodingException {
		String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
		return encode.replaceAll("\\+", "%20");
	}

	public static void writeFile(String data, String path) {
		BufferedWriter output = null;
		try {
			output = new BufferedWriter(new FileWriter(path));
			output.write(data);
			output.flush();
			output.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					throw new RuntimeException("关闭失败");
				}
			}
		}

	}

	public static String readFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			StringBuilder result = new StringBuilder();
			BufferedReader bufferread = null;
			try {
				bufferread = new BufferedReader(new FileReader(file));
				String s = null;
				while ((s = bufferread.readLine()) != null) {
					result.append(s);
				}
				bufferread.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bufferread != null) {
					try {
						bufferread.close();
					} catch (IOException e) {
						throw new RuntimeException("关闭失败");
					}
				}
			}
			return result.toString();
		} else {
			return "";
		}
	}

	public static void clearFiles(String workspaceRootPath) {
		File file = new File(workspaceRootPath);
		deleteFile(file);
	}

	public static void deleteFile(File file) {
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (int i = 0; i < files.length; i++) {
					deleteFile(files[i]);
				}
			}
		}
		file.delete();
	}

	public static boolean copyFile(String originalFilePath, String destinationFilePath) {
		boolean isSuccess = false;
		try (FileInputStream fileInputStream = new FileInputStream(originalFilePath); FileOutputStream fileOutputStream = new FileOutputStream(destinationFilePath);) {
			byte temp[] = new byte[tempLength];
			int length = 0;
			while ((length = fileInputStream.read(temp)) != -1) {
				fileOutputStream.write(temp, 0, length);
			}
			isSuccess = true;
		} catch (Exception e) {
		}
		return isSuccess;
	}

	/**
	 * 解压压缩包
	 * 
	 * @param srcFile
	 * @param destDirPath
	 * @param savePath
	 * @throws RuntimeException
	 * @throws IOException
	 */
	public static void unZip(MultipartFile srcFile, String destDirPath, String savePath) throws RuntimeException, IOException {
		File file = null;
		file = new File(savePath + srcFile.getOriginalFilename());
		if (!file.exists()) {
			throw new RuntimeException(file.getPath() + ",file is not found");
		}
		ZipFile zipFile = null;
		try {
			String encoding = System.getProperty("file.encoding");
			zipFile = new ZipFile(file, Charset.forName(encoding));
			Enumeration<?> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) entries.nextElement();
				if (entry.isDirectory()) {
					String dirPath = destDirPath + File.separator + entry.getName();
					File dir = new File(dirPath);
					dir.mkdirs();
				} else {
					File targetFile = new File(destDirPath + File.separator + entry.getName());
					targetFile.setExecutable(true);
					if (!targetFile.getParentFile().exists()) {
						targetFile.getParentFile().mkdirs();
					}
					targetFile.createNewFile();
					InputStream is = zipFile.getInputStream(entry);
					FileOutputStream fos = new FileOutputStream(targetFile);
					int len;
					byte[] buf = new byte[1024];
					while ((len = is.read(buf)) != -1) {
						fos.write(buf, 0, len);
					}
					fos.close();
					is.close();
				}
			}
		} catch (Exception e) {
			throw new BaseException(-1, "解压zip文件失败");
		} finally {
			if (zipFile != null) {
				try {
					zipFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
//            删除压缩包
			File del = new File(file.toURI());
			del.delete();
		}
	}

	/**
	 * @author 获取文件夹下所有文件
	 */
	public static List<File> getSubFiles(String desFile, List<File> fileList) {
		File file = new File(desFile);
		File[] files = file.listFiles();
		for (File fileIndex : files) {
			if (!fileIndex.exists()) {
				throw new NullPointerException("Cannot find " + fileIndex);
			} else if (fileIndex.isFile()) {
				fileList.add(fileIndex);
			} else {
				if (fileIndex.isDirectory()) {
					getSubFiles(fileIndex.getAbsolutePath(), fileList);
				}
			}
		}
		return fileList;
	}

	public static Resource loadAsResource(String dirname, String filename) {
		File picFile = new File(dirname + filename);
		if (picFile.exists()) {
			Path file = Paths.get(dirname).resolve(filename);
			Resource resource = null;
			try {
				resource = new UrlResource(file.toUri());
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return resource;
		} else {
			return null;
		}
	}
}
