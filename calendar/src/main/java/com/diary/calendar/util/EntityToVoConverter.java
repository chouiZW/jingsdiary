package com.diary.calendar.util;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Entity转VO的通用工具类（包含批量转换功能）
 * 支持单个或多个Entity转换为一个VO，以及List<Entity>转List<VO>
 */
public class EntityToVoConverter {

    /**
     * 将单个Entity转换为VO
     * @param entity 源Entity对象
     * @param voClass 目标VO类
     * @param <E> Entity类型
     * @param <V> VO类型
     * @return 转换后的VO对象
     */
    public static <E, V> V convertSingle(E entity, Class<V> voClass) {
        if (entity == null) {
            return null;
        }
        try {
            V vo = voClass.getDeclaredConstructor().newInstance();
            copyProperties(entity, vo);
            return vo;
        } catch (Exception e) {
            throw new RuntimeException("Entity转VO失败: " + e.getMessage(), e);
        }
    }

    /**
     * 将List<Entity>转换为List<VO>
     * @param entityList 源Entity列表
     * @param voClass 目标VO类
     * @param <E> Entity类型
     * @param <V> VO类型
     * @return 转换后的VO列表
     */
    public static <E, V> List<V> convertList(List<E> entityList, Class<V> voClass) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        
        return entityList.stream()
                .map(entity -> convertSingle(entity, voClass))
                .collect(Collectors.toList());
    }

    /**
     * 将List<Entity>转换为List<VO>，支持自定义转换逻辑
     * @param entityList 源Entity列表
     * @param voClass 目标VO类
     * @param converter 自定义转换器
     * @param <E> Entity类型
     * @param <V> VO类型
     * @return 转换后的VO列表
     */
    public static <E, V> List<V> convertList(List<E> entityList, Class<V> voClass, 
                                           EntityToVoFunction<E, V> converter) {
        if (entityList == null || entityList.isEmpty()) {
            return new ArrayList<>();
        }
        
        List<V> result = new ArrayList<>(entityList.size());
        for (E entity : entityList) {
            if (entity != null) {
                V vo = converter.convert(entity);
                result.add(vo);
            }
        }
        return result;
    }

    /**
     * 将多个Entity转换为一个VO
     * @param entities Entity对象数组
     * @param voClass 目标VO类
     * @param <V> VO类型
     * @return 转换后的VO对象
     */
    public static <V> V convertMultiple(Object[] entities, Class<V> voClass) {
        if (entities == null || entities.length == 0) {
            return null;
        }
        try {
            V vo = voClass.getDeclaredConstructor().newInstance();
            for (Object entity : entities) {
                if (entity != null) {
                    copyProperties(entity, vo);
                }
            }
            return vo;
        } catch (Exception e) {
            throw new RuntimeException("多个Entity转VO失败: " + e.getMessage(), e);
        }
    }

    /**
     * 将多个Entity转换为一个VO，支持属性映射配置
     * @param entityMap Entity对象映射，key为别名，value为Entity对象
     * @param voClass 目标VO类
     * @param mappingConfig 属性映射配置
     * @param <V> VO类型
     * @return 转换后的VO对象
     */
    public static <V> V convertMultipleWithMapping(
            Map<String, Object> entityMap, 
            Class<V> voClass, 
            Map<String, Map<String, String>> mappingConfig) {
        if (entityMap == null || entityMap.isEmpty()) {
            return null;
        }
        try {
            V vo = voClass.getDeclaredConstructor().newInstance();
            
            for (Map.Entry<String, Object> entry : entityMap.entrySet()) {
                String alias = entry.getKey();
                Object entity = entry.getValue();
                
                if (entity != null && mappingConfig.containsKey(alias)) {
                    Map<String, String> fieldMapping = mappingConfig.get(alias);
                    copyPropertiesWithMapping(entity, vo, fieldMapping);
                }
            }
            return vo;
        } catch (Exception e) {
            throw new RuntimeException("多个Entity转VO（带映射）失败: " + e.getMessage(), e);
        }
    }

    /**
     * 将多个Entity列表转换为VO列表（每个VO对应多个Entity的组合）
     * @param entityLists 多个Entity列表的映射
     * @param voClass 目标VO类
     * @param mappingConfig 属性映射配置
     * @param <V> VO类型
     * @return 转换后的VO列表
     */
    public static <V> List<V> convertMultipleListsWithMapping(
            Map<String, List<? extends Object>> entityLists,
            Class<V> voClass,
            Map<String, Map<String, String>> mappingConfig) {
        
        if (entityLists == null || entityLists.isEmpty()) {
            return new ArrayList<>();
        }

        // 找到最长的列表作为基准
        int maxSize = entityLists.values().stream()
                .mapToInt(list -> list != null ? list.size() : 0)
                .max()
                .orElse(0);

        if (maxSize == 0) {
            return new ArrayList<>();
        }

        List<V> result = new ArrayList<>(maxSize);

        for (int i = 0; i < maxSize; i++) {
            Map<String, Object> currentEntities = new HashMap<>();
            
            for (Map.Entry<String, List<? extends Object>> entry : entityLists.entrySet()) {
                String key = entry.getKey();
                List<? extends Object> list = entry.getValue();
                
                if (list != null && i < list.size()) {
                    currentEntities.put(key, list.get(i));
                }
            }

            V vo = convertMultipleWithMapping(currentEntities, voClass, mappingConfig);
            result.add(vo);
        }

        return result;
    }

    /**
     * 复制对象属性（基本的同名属性复制）
     * @param source 源对象
     * @param target 目标对象
     */
    private static void copyProperties(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        Field[] sourceFields = sourceClass.getDeclaredFields();
        Field[] targetFields = targetClass.getDeclaredFields();

        // 创建目标字段映射，提高查找效率
        Map<String, Field> targetFieldMap = new HashMap<>();
        for (Field field : targetFields) {
            field.setAccessible(true);
            targetFieldMap.put(field.getName(), field);
        }

        // 遍历源字段，寻找匹配的目标字段
        for (Field sourceField : sourceFields) {
            sourceField.setAccessible(true);
            String fieldName = sourceField.getName();
            
            // 跳过静态字段和transient字段
            if (java.lang.reflect.Modifier.isStatic(sourceField.getModifiers()) ||
                java.lang.reflect.Modifier.isTransient(sourceField.getModifiers())) {
                continue;
            }

            if (targetFieldMap.containsKey(fieldName)) {
                try {
                    Field targetField = targetFieldMap.get(fieldName);
                    Object value = sourceField.get(source);
                    
                    // 类型兼容性检查
                    if (isAssignable(value, targetField.getType())) {
                        targetField.set(target, value);
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                    continue;
                }
            }
        }
    }

    /**
     * 根据映射配置复制对象属性
     * @param source 源对象
     * @param target 目标对象
     * @param fieldMapping 字段映射配置，key为源字段名，value为目标字段名
     */
    private static void copyPropertiesWithMapping(Object source, Object target, Map<String, String> fieldMapping) {
        if (source == null || target == null || fieldMapping == null || fieldMapping.isEmpty()) {
            return;
        }

        Class<?> sourceClass = source.getClass();
        Class<?> targetClass = target.getClass();

        // 获取目标类的所有字段
        Map<String, Field> targetFieldMap = new HashMap<>();
        Field[] targetFields = targetClass.getDeclaredFields();
        for (Field field : targetFields) {
            field.setAccessible(true);
            targetFieldMap.put(field.getName(), field);
        }

        // 获取源类的所有字段
        Map<String, Field> sourceFieldMap = new HashMap<>();
        Field[] sourceFields = sourceClass.getDeclaredFields();
        for (Field field : sourceFields) {
            field.setAccessible(true);
            sourceFieldMap.put(field.getName(), field);
        }

        // 根据映射配置复制属性
        for (Map.Entry<String, String> mappingEntry : fieldMapping.entrySet()) {
            String sourceFieldName = mappingEntry.getKey();
            String targetFieldName = mappingEntry.getValue();

            if (sourceFieldMap.containsKey(sourceFieldName) && targetFieldMap.containsKey(targetFieldName)) {
                try {
                    Field sourceField = sourceFieldMap.get(sourceFieldName);
                    Field targetField = targetFieldMap.get(targetFieldName);
                    
                    Object value = sourceField.get(source);
                    
                    // 类型兼容性检查
                    if (isAssignable(value, targetField.getType())) {
                        targetField.set(target, value);
                    }
                } catch (IllegalAccessException e) {
                    // 忽略无法访问的字段
                    continue;
                }
            }
        }
    }

    /**
     * 检查值是否可以赋值给目标类型
     * @param value 值
     * @param targetType 目标类型
     * @return 是否兼容
     */
    private static boolean isAssignable(Object value, Class<?> targetType) {
        if (value == null) {
            return !targetType.isPrimitive();
        }
        return targetType.isAssignableFrom(value.getClass());
    }

    // 自定义转换函数接口
    @FunctionalInterface
    public interface EntityToVoFunction<E, V> {
        V convert(E entity);
    }
}