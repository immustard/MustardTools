package cn.buli_home.utils.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 实体工具类
 *
 * @author mustard
 * @version 1.0
 * Create by 2022/9/26
 */
public class EntityUtils {

    /**
     * 检查模型必填字段是否有值
     * @param model 模型
     * @param annotation 必有字段注解
     */
    public static boolean checkRequiredField(Object model, Class annotation) {
        if (null == model || null == annotation) {
            return false;
        }

        Method method = null;

        Class<?> clazz = model.getClass();
        Field[] fields1 = clazz.getSuperclass().getDeclaredFields(); // 超类属性
        Field[] fields2 = clazz.getDeclaredFields(); // 本类属性

        List<Field> list = new ArrayList<Field>(Arrays.asList(fields1));
        List<Field> list2 = Arrays.asList(fields2);
        list.addAll(list2);
        for (Field field : list) {
            boolean isStatic = Modifier.isStatic(field.getModifiers());
            if (isStatic) {
                continue;    //去除静态成员
            }

            Annotation ann = field.getAnnotation(annotation);
            if (null == ann) { continue; }

            String fieldName = field.getName();
            String getMethodName = p_getMethodName(fieldName);
            try {
                method = clazz.getMethod(getMethodName);
                if (StringUtils.isEmpty(StringUtils.convert2String(method.invoke(model)))) {
                    return false;
                }
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
                return false;
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
