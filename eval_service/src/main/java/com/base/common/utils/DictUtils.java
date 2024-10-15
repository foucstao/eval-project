package com.base.common.utils;

import java.util.List;
import java.util.Map;

import com.base.pojo.SysDictData;
import com.base.service.SysDictDataService;

/**
 *          字典工具类
 */
public class DictUtils {

	private static Map<String, String> dictMap = null;

	public static String getDictCache(String key) {
		if (StringUtils.isEmpty(dictMap)) {
			setDictMap();
		}
		return dictMap.get(key);
	}

	public static void setDictMap() {
		SysDictDataService dictDataService = BeanUtils.getBean(SysDictDataService.class);
		List<SysDictData> datalist = dictDataService.selectList(null);
		datalist.forEach(item -> {
			dictMap.put(item.getDictValue(), item.getDictLabel());
		});
	}

}
