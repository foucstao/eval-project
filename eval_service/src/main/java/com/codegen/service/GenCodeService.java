package com.codegen.service;

import java.io.ByteArrayOutputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codegen.domain.GenTable;
import com.codegen.domain.GenTableCloumn;
import com.base.common.utils.JsonFormatUtil;
import freemarker.template.Template;

/**
 * @author 作者: 天涯浪子:
 * @version 创建时间：2021年6月30日 下午5:07:33
 * 
 */
@Service
public class GenCodeService {

	private static final Logger log = LoggerFactory.getLogger(GenCodeService.class);

	/** 项目空间路径 */
	private static final String PROJECT_PATH = "main/java";

	/** mybatis空间路径 */
	private static final String MYBATIS_PATH = "main/resources/mapper";

	/** vue空间路径 */
	private static final String VUE_PATH = "main/resources/vue";

	@Autowired
	private DBService dbUtil;

	public byte[] downloadCode(GenTable genTable) {
		genTable.setClassName(DBService.lineToBigHump(genTable.getTableName()));
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		generatorCode(genTable, zip);
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	public byte[] downloadCode(List<GenTable> genTables) {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		ZipOutputStream zip = new ZipOutputStream(outputStream);
		for (GenTable table : genTables) {
			log.info("生成文件");
			generatorCode(table, zip);
		}
		IOUtils.closeQuietly(zip);
		return outputStream.toByteArray();
	}

	public void generatorCode(GenTable genTable, ZipOutputStream zip) {
		List<String> templates = FreemarkerService.getTemplateList();
		for (String template : templates) {
			try {
				// 渲染模板
				Template tmp = FreemarkerService.initConfig(template);
				StringWriter sw = new StringWriter();
				tmp.process(getCodeMap(genTable), sw);
				// 添加到zip
				zip.putNextEntry(new ZipEntry(getFileName(template, genTable)));

				IOUtils.write(sw.toString(), zip, "UTF-8");
				IOUtils.closeQuietly(sw);
				zip.flush();
				zip.closeEntry();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public Map<String, Object> getCodeMap(GenTable genTable) throws Exception {
		String packageName = genTable.getPackageName();
		String bussineName = genTable.getBusinessName();
		genTable.setBusinessNameBig(bussineName.substring(0, 1).toUpperCase() + bussineName.substring(1));
		genTable.setClassName(DBService.lineToBigHump(genTable.getTableName()));
		String className = genTable.getClassName();
		String tableName = genTable.getTableName();
		Map<String, Object> map = new HashMap<>(3);
		GenTableCloumn pkCloumn = dbUtil.getPKColumnByTableName(tableName);
		List<GenTableCloumn> tableCloumnList = dbUtil.getColumnByTableName(tableName);
		map.put("packageName", packageName);
		map.put("ClassName", className);
		map.put("className", JsonFormatUtil.toLowerCaseFirstOne(className));
		map.put("tableName", tableName);
		map.put("pkCloumn", pkCloumn);
		map.put("fieldList", tableCloumnList);
		map.put("listSize", tableCloumnList.size());
		map.put("businessName", genTable.getBusinessName());
		map.put("businessNameBig", genTable.getBusinessNameBig());
		map.put("moduleName", genTable.getModuleName());
		return map;
	}

	public static String getFileName(String template, GenTable genTable) {
		// 文件名称
		String fileName = "";

		// 包路径
		String packageName = genTable.getPackageName();

		// 大写类名
		String className = genTable.getClassName();

		String javaPath = PROJECT_PATH + "/" + StringUtils.replace(packageName, ".", "/");

		if (template.contains("pojo.ftl")) {
			fileName = javaPath + "/pojo/" + className + ".java";
		} else if (template.contains("mapper.ftl")) {
			fileName = javaPath + "/mapper/" + className + "Mapper.java";
		} else if (template.contains("service.ftl")) {
			fileName = javaPath + "/service/" + className + "Service.java";
		} else if (template.contains("controller.ftl")) {
			fileName = javaPath + "/controller/" + className + "Controller.java";
		} else if (template.contains("mapper.xml.ftl")) {
			fileName = MYBATIS_PATH + "/" + className + "Mapper.xml";
		} else if (template.contains("vue.ftl")) {
			fileName = VUE_PATH + "/" + className + ".vue";
		}
		return fileName;
	}

}
