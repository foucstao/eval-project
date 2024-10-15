package ${packageName}.controller;

import java.util.List;
import java.io.IOException;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.base.common.APIReturnData;
import ${packageName}.pojo.${ClassName};
import com.base.common.excel.ExcelUtil;
import ${packageName}.service.${ClassName}Service;
import com.base.common.query.QueryFilter;
import com.base.controller.BaseAPIController;

@RestController
@RequestMapping("api/v1/${businessName}")
public class ${ClassName}Controller extends BaseAPIController {

	@Autowired
	${ClassName}Service ${className}Service;

	@PostMapping("/page")
	public APIReturnData list(@RequestBody QueryFilter queryFilter) {
		startPage();
		List<${ClassName}> list = ${className}Service.selectList(queryFilter.getQueryList());
		return getDataTable(list);
	}
	
	@PostMapping
	public APIReturnData add(@Validated @RequestBody ${ClassName} ${className}) {
		${className}Service.insert(${className});
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("添加成功");
		apiReturnData.putData("object", ${className});
		return apiReturnData;
	}

	@PutMapping
	public APIReturnData edit(@Validated @RequestBody ${ClassName} ${className}) {
		${className}Service.update(${className});
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("修改成功");
		return apiReturnData;
	}

	@GetMapping("/{id}")
	public APIReturnData getInfo(@PathVariable Long id) {
		${ClassName} ${className} = ${className}Service.selectById(id);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success();
		apiReturnData.putData("data", ${className});
		return apiReturnData;
	}
	
	@PostMapping("/export")
	public void export(HttpServletResponse response, String queryList) throws IOException {
		List<${ClassName}> list = ${className}Service.selectList(strToQueryList(queryList));
		ExcelUtil<${ClassName}> util = new ExcelUtil<${ClassName}>(${ClassName}.class);
		util.exportExcel(response, list, "${className}");
	}

	@DeleteMapping("/{ids}")
	public APIReturnData remove(@PathVariable Long[] ids) {
		${className}Service.delete(ids);
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.success("删除成功");
		return apiReturnData;
	}
}
