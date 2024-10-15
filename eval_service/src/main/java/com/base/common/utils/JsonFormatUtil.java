package com.base.common.utils;

import java.io.IOException;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonFormatUtil {
	/**
	 * 单位缩进字符串。
	 */
	private static String SPACE = "   ";

	public static String toLowerCaseFirstOne(String s) {
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}

	/**
	 * 返回格式化JSON字符串。
	 * 
	 * @param json 未格式化的JSON字符串。
	 * @return 格式化的JSON字符串。
	 */
	public static String formatJson(String json) {
		StringBuffer result = new StringBuffer();

		int length = json.length();
		int number = 0;
		char key = 0;

		// 遍历输入字符串。
		for (int i = 0; i < length; i++) {
			// 1、获取当前字符。
			key = json.charAt(i);

			// 2、如果当前字符是前方括号、前花括号做如下处理：
			if ((key == '[') || (key == '{')) {
				// （1）如果前面还有字符，并且字符为“：”，打印：换行和缩进字符字符串。
				if ((i - 1 > 0) && (json.charAt(i - 1) == ':')) {
					result.append('\n');
					result.append(indent(number));
				}

				// （2）打印：当前字符。
				result.append(key);

				// （3）前方括号、前花括号，的后面必须换行。打印：换行。
				result.append('\n');

				// （4）每出现一次前方括号、前花括号；缩进次数增加一次。打印：新行缩进。
				number++;
				result.append(indent(number));

				// （5）进行下一次循环。
				continue;
			}

			// 3、如果当前字符是后方括号、后花括号做如下处理：
			if ((key == ']') || (key == '}')) {
				// （1）后方括号、后花括号，的前面必须换行。打印：换行。
				result.append('\n');

				// （2）每出现一次后方括号、后花括号；缩进次数减少一次。打印：缩进。
				number--;
				result.append(indent(number));

				// （3）打印：当前字符。
				result.append(key);

				// （4）如果当前字符后面还有字符，并且字符不为“，”，打印：换行。
				if (((i + 1) < length) && (json.charAt(i + 1) != ',')) {
					result.append('\n');
				}

				// （5）继续下一次循环。
				continue;
			}

			// 4、如果当前字符是逗号。逗号后面换行，并缩进，不改变缩进次数。
			/*
			 * if ((key == ',')) { result.append(key); result.append('\n');
			 * result.append(indent(number)); continue; }
			 */

			// 5、打印：当前字符。
			result.append(key);
		}

		return result.toString();
	}

	/**
	 * 返回指定次数的缩进字符串。每一次缩进三个空格，即SPACE。
	 * 
	 * @param number 缩进次数。
	 * @return 指定缩进次数的字符串。
	 */
	private static String indent(int number) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < number; i++) {
			result.append(SPACE);
		}
		return result.toString();
	}

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 对象转str
	 *
	 * @param obj
	 * @param include
	 * @return
	 */
	public static String objToStr(Object obj, Include include) {
		try {
			if (include != null) {
				objectMapper.setSerializationInclusion(include);
			}
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 对象转str
	 *
	 * @param obj
	 * @return
	 */
	public static String objToStr(Object obj) {
		return objToStr(obj, null);
	}

	/**
	 * str转对象(简单对象)
	 *
	 * @param str
	 * @param clazz
	 * @return T
	 */
	public static <T> T strToObj(String str, Class<T> clazz) {
		try {
			return objectMapper.readValue(str, clazz);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * str转对象(复杂对象，List，Map等)
	 *
	 * @param str
	 * @param typeReference 【例1：单个对象】SysConfig sysConfig2 = strToObj(str, new
	 *                      TypeReference<SysConfig>() { });
	 *                      <p>
	 *                      【例2：list对象】List<SysConfig> list = strToObj(str, new
	 *                      TypeReference<List<SysConfig>>() { });
	 *                      <p>
	 *                      【例3：map对象】Map<String, SysConfig> map = strToObj(str, new
	 *                      TypeReference<Map<String, SysConfig>>() { });
	 * @return
	 */
	public static <T> T strToObj(String str, TypeReference<T> typeReference) {
		try {
			// 空值返回null
			if (StringUtils.isEmpty(str)) {
				return null;
			}
			return objectMapper.readValue(str, typeReference);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
