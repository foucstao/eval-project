package ${packageName}.mapper;

import org.apache.ibatis.annotations.Mapper;

import ${packageName}.pojo.${ClassName};

import java.util.List;

@Mapper
public interface ${ClassName}Mapper{

/**
* 根据id数组批量删除数据
*
* @param array id数组
* @return
*/
int deletesByPrimaryKey(Long[] array);

/**
* 插入一条数据
*
* @param t 插入对象
* @return
*/
int insert(${ClassName} ${className});

/**
* 修改一条数据
*
* @param t 修改对象
* @return
*/
int updateByPrimaryKey(${ClassName} ${className});

/**
* 根据id查看数据
*
* @param id
* @return
*/
${ClassName} selectByPrimaryKey(Long id);

/**
* 查看所有数据，可根据名称进行模糊查询
*
* @param
* @return
*/
List<${ClassName}> selectList(String condition);
}