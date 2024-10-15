package com.codegen.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codegen.domain.GenTable;
import com.codegen.service.DBService;
import com.codegen.service.GenCodeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.base.common.APIReturnData;
import com.base.common.TablePage;

/**
 * @author 作者: 天涯浪子:
 * @version 创建时间：2021年6月30日 下午4:51:26
 * 
 */
@RestController
@RequestMapping("api/v1/gen")
public class GenCodeController {

	@Autowired
	GenCodeService genCodeService;

	@Autowired
	DBService dbUtil;

	@PostMapping("/down")
	public void genCode(HttpServletResponse response, @RequestBody GenTable genTable) throws IOException {
		String bussineName = genTable.getBusinessName();
		genTable.setBusinessNameBig(bussineName.substring(0, 1).toUpperCase() + bussineName.substring(1));
		byte[] data = genCodeService.downloadCode(genTable);
		genCode(response, data);
	}

	@PostMapping("/batchDown")
	public void batchDown(HttpServletResponse response, @RequestBody List<GenTable> genTables) throws IOException {
		byte[] data = genCodeService.downloadCode(genTables);
		genCode(response, data);
	}

	@GetMapping("/page")
	public APIReturnData getTableList(TablePage tabPage) throws SQLException {
		APIReturnData apiReturnData = new APIReturnData();
		List<GenTable> tableList = dbUtil.getTableList();
		int pageNum = tabPage.getPageNum();
		int pageSize = tabPage.getPageSize();
		Page page = new Page(pageNum, pageSize);
		int total = tableList.size();
		page.setTotal(total);
		int startIndex = (pageNum - 1) * pageSize;
		int endIndex = Math.min(startIndex + pageSize, total);
		page.addAll(tableList.subList(startIndex, endIndex));
		PageInfo pageInfo = new PageInfo<>(page);
		apiReturnData.putData("data", pageInfo);
		apiReturnData.success();
		return apiReturnData;
	}

	/**
	 * 生成zip文件
	 */
	private void genCode(HttpServletResponse response, byte[] data) throws IOException {
		response.reset();
		response.setHeader("Content-Disposition", "attachment; filename=\"ruoyi.zip\"");
		response.addHeader("Content-Length", "" + data.length);
		response.setContentType("application/octet-stream; charset=UTF-8");
		IOUtils.write(data, response.getOutputStream());
	}
}
