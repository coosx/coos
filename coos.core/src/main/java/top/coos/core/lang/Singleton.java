package top.coos.core.lang;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import top.coos.util.ClassUtil;
import top.coos.util.ReflectUtil;

/**
 * 单例类<br>
 * 提供单例对象的统一管理，当调用get方法时，如果对象池中存在此对象，返回此对象，否则创建新对象返回

 *
 */
public final class Singleton {
	private static Map<Class<?>, Object> pool = new ConcurrentHashMap<>();
	
	private Singleton() {}
	
	/**
	 * 获得指定类的单例对象<br>
	 * 对象存在于池中返回，否则创建，每次调用此方法获得的对象为同一个对象<br>
	 * 
	 * @param <T> 单例对象类型
	 * @param clazz 类
	 * @param params 构造方法参数
	 * @return 单例对象
	 */
	@SuppressWarnings("unchecked")
	public static <T> T get(Class<T> clazz, Object... params) {
		T obj = (T) pool.get(clazz);
		
		if(null == obj) {
			synchronized(Singleton.class) {
				obj = (T) pool.get(clazz);
				if(null == obj) {
					obj = ReflectUtil.newInstance(clazz, params);
					pool.put(clazz, obj);
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * 获得指定类的单例对象<br>
	 * 对象存在于池中返回，否则创建，每次调用此方法获得的对象为同一个对象<br>
	 * 
	 * @param <T> 单例对象类型
	 * @param className 类名
	 * @param params 构造参数
	 * @return 单例对象
	 */
	public static <T> T get(String className, Object... params) {
		final Class<T> clazz = ClassUtil.loadClass(className);
		return get(clazz, params);
	}
	
	/**
	 * 将已有对象放入单例中，其Class做为键
	 * 
	 * @param obj 对象
	 * @since 4.0.7
	 */
	public static void put(Object obj) {
		pool.put(obj.getClass(), obj);
	}
	
	/**
	 * 移除指定Singleton对象
	 * @param clazz 类
	 */
	public static void remove(Class<?> clazz) {
		pool.remove(clazz);
	}
	
	/**
	 * 清除所有Singleton对象
	 */
	public static void destroy() {
		pool.clear();
	}
}
