<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${packageName}.mapper.${ClassName}Mapper">

    <resultMap type="${ClassName}" id="BaseResultMap">
        <#list fieldList as field>
            <result column="${field.name}" property="${field.nameHump}"/>
        </#list>
    </resultMap>

    <sql id="select${ClassName}Vo">
        select <#list fieldList as field>${field.name}<#if field_index+1 != listSize>,</#if></#list> from ${tableName}
    </sql>

    <delete id="deletesByPrimaryKey" parameterType="java.lang.Long">
        delete from ${tableName}
        where ${pkCloumn.name} in
        <foreach item="id" collection="array" open="(" separator=","
                 close=")">
            <#noparse>#</#noparse>{id}
        </foreach>
    </delete>

    <insert id="insert" parameterType="${ClassName}">
        <selectKey keyProperty="${pkCloumn.nameHump}" order="AFTER" resultType="java.lang.Long">
            SELECT LAST_INSERT_ID()
        </selectKey>
        insert into ${tableName}
        (<#list fieldList as field><#if field??>
        <#if field.name!=pkCloumn.name>${field.name}<#if field_index+1 != listSize>,</#if></#if></#if></#list>
        )
        values
        (
        <#list fieldList as field><#if field??><#if field.name!=pkCloumn.nameHump><#if field.nameHump=='createTime'>sysdate() <#else><#noparse>#</#noparse>{${field.nameHump}}</#if><#if field_index+1 != listSize>,</#if> </#if></#if></#list>
        )
    </insert>

    <update id="updateByPrimaryKey" parameterType="${ClassName}">
        update ${tableName}
        set <#list fieldList as field><#if field??>
            <#if field.name!=pkCloumn.name>
                <#if field_index+1 != listSize>
                    <if test="${field.nameHump} !=null and ${field.nameHump} !='' ">
                        ${field.name}=<#if field.nameHump=='updateTime'>sysdate(),<#else><#noparse>#</#noparse>{${field.nameHump}},
                        </#if>
                    </if>
                  <#else>${field.name} = <#if field.nameHump=='updateTime'>sysdate() <#else><#noparse>#</#noparse>{${field.nameHump}}</#if>
                </#if>
            </#if>
            </#if>
        </#list>
        where ${pkCloumn.name} = <#noparse>#</#noparse>{${pkCloumn.nameHump}}
    </update>

    <select id="selectByPrimaryKey" parameterType="java.lang.Long" resultMap="BaseResultMap">
        <include refid="select${ClassName}Vo"/>
        where ${pkCloumn.name} = <#noparse>#</#noparse>{${pkCloumn.nameHump}}
    </select>

    <select id="selectList" resultMap="BaseResultMap">
        <include refid="select${ClassName}Vo"/>
        <where>
            <if test="condition !=null and condition !=''">
                <#noparse>${condition}</#noparse>
            </if>
        </where>
    </select>

</mapper>