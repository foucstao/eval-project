package com.base.common.utils.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 图片处理工具类
 *
 */
public class ImageUtils {
	private static final Logger log = LoggerFactory.getLogger(ImageUtils.class);

	public static byte[] getImage(String imagePath) {
		InputStream is = getFile(imagePath);
		try {
			return IOUtils.toByteArray(is);
		} catch (Exception e) {
			log.error("图片加载异常 {}", e);
			return null;
		} finally {
			IOUtils.closeQuietly(is);
		}
	}

	public static InputStream getFile(String imagePath) {
		try {
			byte[] result = readFile(imagePath);
			result = Arrays.copyOf(result, result.length);
			return new ByteArrayInputStream(result);
		} catch (Exception e) {
			log.error("获取图片异常 {}", e);
		}
		return null;
	}

	/**
	 * 读取文件为字节数据
	 * 
	 * @param key 地址
	 * @return 字节数据
	 */
	public static byte[] readFile(String url) {
		InputStream in = null;
		ByteArrayOutputStream baos = null;
		try {
			// 网络地址
			URL urlObj = new URL(url);
			URLConnection urlConnection = urlObj.openConnection();
			urlConnection.setConnectTimeout(30 * 1000);
			urlConnection.setReadTimeout(60 * 1000);
			urlConnection.setDoInput(true);
			in = urlConnection.getInputStream();
			return IOUtils.toByteArray(in);
		} catch (Exception e) {
			log.error("访问文件异常 {}", e);
			return null;
		} finally {
			IOUtils.closeQuietly(baos);
		}
	}

	public static String toBase64(String imagePath) {
		byte[] data = null;
		try (InputStream in = new FileInputStream(imagePath);) {
			data = new byte[in.available()];
			in.read(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		Encoder encoder = Base64.getEncoder();
		String encodedText = encoder.encodeToString(data);
		return encodedText;
	}

	public static File toImage(String base64Image, String fileName) {
		File tempFile = new File(fileName);
		try (OutputStream out = new FileOutputStream(tempFile);) {
			Decoder decoder = Base64.getDecoder();
			byte[] tempbytes = decoder.decode(base64Image);
			out.write(tempbytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return tempFile;
	}
}
