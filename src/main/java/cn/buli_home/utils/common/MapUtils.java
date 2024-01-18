package cn.buli_home.utils.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 字典工具类
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/26
 */
public class MapUtils {

    /**
     * 是否为空
     */
    public static boolean isEmpty(Map<?, ?> map) {
        return Objects.isNull(map) || map.isEmpty();
    }

    /**
     * 获取字符串value
     */
    public static String getStringValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return StringUtils.convert2String(map.get(key));
        } else {
            return null;
        }
    }

    /**
     * 获取整型value
     */
    public static Integer getIntegerValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return ObjectUtils.parseInt(map.get(key));
        } else {
            return null;
        }
    }

    /**
     * 获取长整型value
     */
    public static Long getLongValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return ObjectUtils.parseLong(map.get(key));
        } else {
            return null;
        }
    }

    /**
     * 获取双浮点value
     */
    public static Double getDoubleValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return ObjectUtils.parseDouble(map.get(key));
        } else {
            return null;
        }
    }

    /**
     * 获取布尔value
     */
    public static Boolean getBooleanValue(Map<String, Object> map, String key) {
        if (map.containsKey(key)) {
            return ObjectUtils.parseBoolean(map.get(key));
        } else {
            return null;
        }
    }

    /**
     * 模型转为字典
     *
     * @param model       模型
     * @param annotations 忽略字段注解
     */
    public static Map<String, Object> model2Map(Object model, Class... annotations) {
        if (model == null) {
            return new HashMap<String, Object>();
        }

        Map<String, Object> map = new HashMap<String, Object>();
        Method method;
        Field[] fields1 = model.getClass().getSuperclass().getDeclaredFields(); // 超类属性
        Field[] fields2 = model.getClass().getDeclaredFields(); // 本类属性
        List<Field> list = new ArrayList<Field>(Arrays.asList(fields1));
        List<Field> list2 = Arrays.asList(fields2);
        list.addAll(list2);

        FieldLabel:
        for (Field field : list) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;    //去除静态成员
            }

            String fieldName = field.getName();
            if (fieldName.equals("gmtModified")
                    || fieldName.equals("gmtCreate")
                    || fieldName.equals("deleted")
                    || fieldName.equals("create_time")
                    || fieldName.equals("update_time")
                    || fieldName.equals("valid")) {
                continue;
            }

            for (Class<Annotation> annotation : annotations) {
                Annotation ann = field.getAnnotation(annotation);
                if (null != ann) {
                    continue FieldLabel;
                }
            }

            String getMethodName = p_getMethodName(fieldName);
            try {
                method = model.getClass().getMethod(getMethodName);
                map.put(fieldName, method.invoke(model));
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException |
                     InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return map;
    }

    /**
     * 模型转为字典
     *
     * @param list        模型列表
     * @param annotations 忽略字段注解
     */
    public static List<Map<String, Object>> modelList2Map(List<?> list, Class... annotations) {
        if (null == list) {
            return new ArrayList<>();
        }
        return list.stream().map(e -> MapUtils.model2Map(e, annotations)).collect(Collectors.toList());
    }

    /**
     * 检查字典中必有字段是否有值 (默认不去除空白符)
     *
     * @param map  字典
     * @param keys 键数组
     */
    public static Boolean checkValueBlank(Map<String, Object> map, String... keys) {
        return checkValueBlank(map, false, keys);
    }

    /**
     * 检查字典中必有字段是否有值
     *
     * @param map          字典
     * @param isCheckBlank 是否去除空白符
     * @param keys         键数组
     */
    public static Boolean checkValueBlank(Map<String, Object> map, Boolean isCheckBlank, String... keys) {
        for (String key : keys) {
            Object obj = map.get(key);
            if (obj == null) {
                return false;
            } else {
                if (obj instanceof String) {
                    String s = (String) obj;

                    boolean condition = isCheckBlank ? (StringUtils.isEmptyWithoutBlank(s)) : (StringUtils.isEmpty(s));
                    if (condition) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static String p_getMethodName(String name) {
        if (name != null && name.length() > 1) {
            return "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }
}
