package ${packageName}.pojo;

import com.base.common.BaseEntity;
import java.util.Date;

import lombok.Data;

@Data
public class ${ClassName} extends BaseEntity {

<#list fieldList as field>
		/** ${field.comment}*/
     private ${field.javaType} ${field.nameHump};
     
</#list>
}