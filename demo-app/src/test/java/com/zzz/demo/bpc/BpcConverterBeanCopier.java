package com.zzz.demo.bpc;

import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.core.Converter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangliwei
 */
public class BpcConverterBeanCopier implements Converter {

    /**
     * @param obj     obj
     * @param clazz   clazz
     * @param context context
     * @return obj
     */
    @Override
    public Object convert(Object obj, Class clazz, Object context) {
        // 传入数据不为空才进行校验，否则不进行校验
        if (obj != null) {
            // 类型不匹配的才需进行转换，否则直接返回原有数据
            if (obj.getClass() != clazz) {
                // 输入为String类型是的类型转换
                if (obj instanceof String) {
                    String str = (String) obj;
                    if (clazz == Date.class) { // 输入String，输出Date
                        if (str == null || str.trim().length() == 0) {
                            return null;
                        }
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            return df.parse(str.trim());
                        } catch (Exception e) {
                            try {
                                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                                return df.parse(str.trim());
                            } catch (ParseException ex) {
                                return obj;
                            }
                        }
                    } else if (clazz == BigDecimal.class) { // 输入String，输出BigDecimal
                        if (StringUtils.isNotBlank(str)) {
                            try {
                                return new BigDecimal(str);
                            } catch (Exception e) {
                                return null;
                            }
                        } else {
                            return null;
                        }
                    } else if (clazz == double.class || clazz == Double.class) { // 输入String，输出double
                        if (StringUtils.isNotBlank(str)) {
                            try {
                                return Double.parseDouble(str);
                            } catch (NumberFormatException e) {
                                return 0D;
                            }
                        }
                    } else if (clazz == int.class || clazz == Integer.class) { // 输入String，输出int
                        if (StringUtils.isNotBlank(str)) {
                            try {
                                return Integer.parseInt(str);
                            } catch (NumberFormatException e) {
                                return 0;
                            }
                        }
                    } else if (clazz == long.class || clazz == Long.class) { // 输入String，输出long
                        if (StringUtils.isNotBlank(str)) {
                            try {
                                return Long.parseLong(str);
                            } catch (NumberFormatException e) {
                                return 0L;
                            }
                        }
                    }
                } else if (obj instanceof java.sql.Date) {
                    if (clazz == String.class) { // 输入Date ,输出String
                        try {
                            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            return df.format((Date) obj);
                        } catch (Exception e) {
                            return null;
                        }
                    }
                } else if (obj instanceof BigDecimal) {
                    BigDecimal bigDecimal = (BigDecimal) obj;
                    if (clazz == String.class) { // 输入BigDecimal ,输出String
                        if (bigDecimal != null) {
                            return bigDecimal.toString();
                        } else {
                            return null;
                        }
                    }
                } else if (obj.getClass() == double.class || obj instanceof Double) {
                    Double d = (Double) obj;
                    if (clazz == BigDecimal.class) { // 输入double，输出BigDecimal
                        if (d != null) {
                            return BigDecimal.valueOf(d);
                        } else {
                            return 0D;
                        }
                    }
                } else if (obj.getClass() == int.class || obj instanceof Integer) {
                    Integer d = (Integer) obj;
                    if (clazz == BigDecimal.class) { // 输入int，输出BigDecimal
                        if (d != null) {
                            return BigDecimal.valueOf(d);
                        } else {
                            return 0;
                        }
                    }
                } else if (obj.getClass() == float.class || obj instanceof Float) {
                    Float d = (Float) obj;
                    if (clazz == BigDecimal.class) { // 输入float，输出BigDecimal
                        if (d != null) {
                            return BigDecimal.valueOf(d);
                        } else {
                            return 0F;
                        }
                    }
                } else if (obj.getClass() == long.class || obj instanceof Long) {
                    Long d = (Long) obj;
                    if (clazz == BigDecimal.class) { // 输入long，输出BigDecimal
                        if (d != null) {
                            return BigDecimal.valueOf(d);
                        } else {
                            return 0L;
                        }
                    }
                } else if (obj.getClass() == short.class || obj instanceof Short) {
                    Short s = (Short) obj;
                    if (clazz == BigDecimal.class) { // 输入short，输出BigDecimal
                        if (s != null) {
                            return BigDecimal.valueOf(s);
                        } else {
                            return 0;
                        }
                    }
                }
                return null;
            } else {
                return obj;
            }
        } else {
            return null;
        }
    }
}
