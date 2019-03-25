package priv.dengjl.bean.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class PropertyUtils {
	public static Map<String, Object> bean2Map(Object object)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

		Map<String, Object> map = new HashMap<>();
		for (PropertyDescriptor pd : propertyDescriptors) {
			if (pd.getPropertyType().isAssignableFrom(Class.class)) {
				continue;
			}
			String propertyName = pd.getName();
			Method readMethod = pd.getReadMethod();
			if (!readMethod.isAccessible()) {
				readMethod.setAccessible(true);
			}
			Object propertyValue = readMethod.invoke(object);
			map.put(propertyName, propertyValue);
		}
		return map;
	}

	public static <T> T map2Bean(Class<T> type, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		T obj = type.newInstance();
		for (PropertyDescriptor pd : propertyDescriptors) {
			if (pd.getPropertyType().isAssignableFrom(Class.class)) {
				continue;
			}
			String propertyName = pd.getName();
			if (map.containsKey(propertyName)) {
				Object value = map.get(propertyName);
				Method setter = pd.getWriteMethod();
				if (!setter.isAccessible()) {
					setter.setAccessible(true);
				}
				setter.invoke(obj, value);
			}
		}
		return obj;
	}

	public static <T> T map2Bean2(Class<T> type, Map<String, Object> map)
			throws IntrospectionException, IllegalAccessException, InstantiationException, InvocationTargetException {
		T obj = type.newInstance();
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			PropertyDescriptor pd = new PropertyDescriptor(key, type);
			Method writeMethod = pd.getWriteMethod();
			if (!writeMethod.isAccessible()) {
				writeMethod.setAccessible(true);
			}
			writeMethod.invoke(obj, value);
		}
		return obj;
	}
}
