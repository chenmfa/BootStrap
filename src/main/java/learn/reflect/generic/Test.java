package learn.reflect.generic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public class Test {

	private static List<Field> fields = new ArrayList<Field>();
	private static List<Method> methods = new ArrayList<Method>();

	public static void main(String[] args) {
		A a = new A();

		getAllFields(a.getClass());
		System.out.println(fields);
		// for (int i = 0; i < fields.size(); i++) {
		// System.out.println(fields.get(i));
		// }
		
		for (int i = 0; i < methods.size(); i++) {
			System.out.println(methods.get(i));
		}

	}

	private static void getAllFields(Class class1) {
		try {
			Field[] field = class1.getDeclaredFields();
			for (int i = 0; i < field.length; i++) {
				if (field[i].getType().getName().contains("com.huawei")) {
					getAllFields(field[i].getType());
				} else if (field[i].getType().getName()
						.equals("java.util.List")) {
					Type fc = field[i].getGenericType(); // 关键的地方，如果是List类型，得到其Generic的类型
					if (fc == null)
						continue;
					if (fc instanceof ParameterizedType) // 【3】如果是泛型参数的类型
					{
						ParameterizedType pt = (ParameterizedType) fc;
						Class genericClazz = (Class) pt
								.getActualTypeArguments()[0]; // 【4】
						getAllFields(genericClazz);
					}
				}
//				System.out.println(field[i]);
				fields.add(field[i]);

				Field f = field[i];

				String fieldName = f.getName();
				String stringLetter = fieldName.substring(0, 1).toUpperCase();

				// 获得相应属性的getXXX和setXXX方法名称
				String setName = "set" + stringLetter + fieldName.substring(1);

				// 获取相应的方法
				Method setMethod = class1.getMethod(setName, new Class[] { f.getType() });
				methods.add(setMethod);
			}

			Class clazz = class1.getSuperclass();
			if (clazz.getName().contains("com.huawei")) {
				getAllFields(clazz);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
