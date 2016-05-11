package learn.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenmf13021
 * @date 2015年12月24日
 * @description spring的classutil 仿照来的
 */
public class ClassUtil {
  
  //存储基础数据类型包装类与基础类的映射
  private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);
  
  //存储基础类和基础数据类型包装类的映射
  private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);
  
  static{
    primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
    primitiveWrapperTypeMap.put(Byte.class, byte.class);
    primitiveWrapperTypeMap.put(Character.class, char.class);
    primitiveWrapperTypeMap.put(Double.class, double.class);
    primitiveWrapperTypeMap.put(Float.class, float.class);
    primitiveWrapperTypeMap.put(Integer.class, int.class);
    primitiveWrapperTypeMap.put(Long.class, long.class);
    primitiveWrapperTypeMap.put(Short.class, short.class);
    
    //Map.entrySet() 这个方法返回的是一个Set<Map.Entry<K,V>>，Map.Entry 是一个接口，
    //他的用途是表示一个映射项（里面有Key和Value），而Set<Map.Entry<K,V>>表示一个映射项的Set。
    for (Map.Entry<Class<?>, Class<?>> entry : primitiveWrapperTypeMap.entrySet()) {
      primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());
    }
  }
  
  public static Class<?> resolvePossiblePrimitiveType(Class<?> clz){
    return (clz.isPrimitive() && clz != void.class)?primitiveTypeToWrapperMap.get(clz):clz;
  }
  
  public static void main(String[] args){
    System.out.println(primitiveWrapperTypeMap);
    System.out.println(primitiveTypeToWrapperMap);
  }
}
