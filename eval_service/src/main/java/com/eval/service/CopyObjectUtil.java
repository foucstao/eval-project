package com.eval.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.HashMap;

public class CopyObjectUtil extends BeanUtils {

    private static final Logger logger = LoggerFactory.getLogger(CopyObjectUtil.class);

    /**
     * 将source中与target类型和名称相同的属性，赋值给target的对应属性，并返回target
     *
     * @param source 源对象
     * @param target 目标对象
     * @param ignore 是否忽略空值
     */
    public static void copyProperties(Object source, Object target, boolean ignore) {
        List<Map<String, Object>> sourceFields = getFieldInfo(source);
        if (ObjectUtils.isEmpty(sourceFields)) {
            return;
        }
        for (Map sourceFieldMap : sourceFields) {
            try {
                Field field = target.getClass().getDeclaredField(sourceFieldMap.get("name").toString());
                // 源对象属性值为空 或属性类型不一致 则返回继续下一条
                if (ObjectUtils.isEmpty(sourceFieldMap.get("value")) && ignore) {
                    continue;
                } else if (!sourceFieldMap.get("type").equals(field.getType().toString())) {
                    continue;
                }
                field.setAccessible(true);
                field.set(target, sourceFieldMap.get("value"));
            } catch (Exception ex) {
                // 查看目标对象父类属性
                try {
                    Field superField = target.getClass().getSuperclass()
                            .getDeclaredField(sourceFieldMap.get("name").toString());
                    // 源对象属性值为空 或属性类型不一致 则返回继续下一条
                    if (ObjectUtils.isEmpty(sourceFieldMap.get("value")) && ignore) {
                        continue;
                    } else if (!sourceFieldMap.get("type").equals(superField.getType().toString())) {
                        continue;
                    }
                    superField.setAccessible(true);
                    superField.set(target, sourceFieldMap.get("value"));
                } catch (Exception e) {
                    continue;
                }
            }
        }
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter);
            return method.invoke(o);
        } catch (Exception ex) {
            // 未找到常规命名的get方法
            try {
                Method method = o.getClass().getMethod(fieldName);
                return method.invoke(o);
            } catch (Exception e) {
                logger.error("根据属性名获取属性值错误：", e.getMessage());
                return null;
            }
        }
    }

    /**
     * 获取属性名数组
     */
    private static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 获取属性类型(type)，属性名(name)，属性值(value)的map组成的list
     */
    private static List<Map<String, Object>> getFieldInfo(Object o) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (ObjectUtils.isEmpty(o)) {
            return null;
        }
        List<Field> fields = new ArrayList<>(Arrays.asList(o.getClass().getDeclaredFields()));
        // 如果存在父类，获取父类的属性值，类型，名称并添加到一起
        Class sc = o.getClass().getSuperclass();
        if (sc != null) {
            fields.addAll(Arrays.asList(sc.getDeclaredFields()));
        }
        for (Field field : fields) {
            Map<String, Object> infoMap = new HashMap<>();
            infoMap.put("type", field.getType().toString());
            infoMap.put("name", field.getName());
            infoMap.put("value", getFieldValueByName(field.getName(), o));
            list.add(infoMap);
        }
        return list;
    }
}
