package ${packageName}.service;


import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ${packageName}.mapper.${ClassName}Mapper;
import ${packageName}.pojo.${ClassName};
import com.base.common.query.BaseQuery;
import com.base.common.query.QueryUtil;

import java.util.List;

@Service
public class ${ClassName}Service{

	private Logger logger = LoggerFactory.getLogger(${ClassName}Service.class);

	@Autowired
    ${ClassName}Mapper ${className}Mapper;

        public List<${ClassName}> selectList(List<BaseQuery> queryList) {
		String condition = QueryUtil.queryAll(queryList);
		return ${className}Mapper.selectList(condition);
		}

		public ${ClassName} selectById(Long id) {
		return  ${className}Mapper.selectByPrimaryKey(id);
		}

		public int insert(${ClassName} m) {
		return ${className}Mapper.insert(m);
		}

		public int update(${ClassName} m) {
		return ${className}Mapper.updateByPrimaryKey(m);
		}

		public int delete(Long[] ids) {
		return ${className}Mapper.deletesByPrimaryKey(ids);
		}
}
