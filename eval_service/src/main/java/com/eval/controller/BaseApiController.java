package com.eval.controller;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.base.common.APIReturnData;
import com.base.common.TablePage;
import com.base.common.query.BaseQuery;
import com.base.common.utils.DateUtils;
import com.base.common.utils.JsonFormatUtil;
import com.base.common.utils.StringUtils;
import com.base.common.utils.request.ServletUtils;

public class BaseApiController {
    
    /**
     * 查询字符串转义 
     */
    public List<BaseQuery> strToQueryList(String queryList){
        List<BaseQuery> quriesList = JsonFormatUtil.strToObj(queryList,new TypeReference<List<BaseQuery>>(){
        });
        return quriesList;
    }

    /*
     * 请求分页数据  ！！！！！
     */
    @SuppressWarnings({"rowtypes","unchecked"})   //指示编译器忽略相应的警告
    protected APIReturnData getDataTable(List<?> list) {
		APIReturnData apiReturnData = new APIReturnData();
		apiReturnData.putData("tableList", list);
		apiReturnData.putData("total", new PageInfo(list).getTotal());
		return apiReturnData;
	}

    /*
     * date 类型转换
     */
    @InitBinder 
    public void initBinder(WebDataBinder binder){
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				setValue(DateUtils.parseDate(text));
			}
		});
    } 

    /**
	 * 设置请求分页数据
	 */
	protected TablePage startPage() {
		TablePage tablePage = new TablePage();
		String pageNum = ServletUtils.getParameter("pageNum");
		String pageSize = ServletUtils.getParameter("pageSize");
		if (StringUtils.isNotEmpty(pageNum) && StringUtils.isNotEmpty(pageSize)) {
            
			PageHelper.startPage(Integer.parseInt(pageNum), Integer.parseInt(pageSize));
		}
		return tablePage;
	}


}
