package com.codegen.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemarkerService {
	private static Template temp;

	public static Template initConfig(String ftlName) throws IOException {
		Configuration conf = new Configuration(Configuration.VERSION_2_3_28);
		String templatePath = FreemarkerService.class.getResource("/").getPath() + "/gen/rbacftl";
		conf.setDirectoryForTemplateLoading(new File(templatePath));
		conf.setObjectWrapper(new DefaultObjectWrapper(Configuration.VERSION_2_3_28));
		return conf.getTemplate(ftlName);
	}

	public static List<String> getTemplateList() {
		List<String> templates = new ArrayList<String>();
		templates.add("controller.ftl");
		templates.add("pojo.ftl");
		templates.add("mapper.ftl");
		templates.add("mapper.xml.ftl");
		templates.add("service.ftl");
		templates.add("vue.ftl");
		return templates;
	}

	public static void generator(Map<String, Object> map, String fileName) throws IOException, TemplateException {
		FileWriter fw = new FileWriter(fileName);
		BufferedWriter bw = new BufferedWriter(fw);
		temp.process(map, bw);
		bw.flush();
		fw.close();
	}
}
