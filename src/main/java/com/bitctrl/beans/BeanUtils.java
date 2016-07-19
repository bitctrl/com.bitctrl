/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA.
 *
 * Contact Information:
 * BitCtrl Systems GmbH
 * Weißenfelser Straße 67
 * 04229 Leipzig
 * Phone: +49 341-490670
 * mailto: info@bitctrl.de
 */

package com.bitctrl.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * Enthält Hilfsmethoden für den Umgang mit Java Beans.
 * 
 * @author BitCtrl Systems GmbH, Schumann
 * @version $Id: BeanUtils.java 17938 2009-06-03 16:55:10Z Schumann $
 */
public final class BeanUtils {

	/**
	 * Kopiert eine Java-Bean. Es werden alle Properties kopiert, die über einen
	 * Getter und Setter verfügen. Sind beide vorhanden, müssen sie auch {@code
	 * public} sein.
	 * 
	 * @param source
	 *            die zu kopierende Bean.
	 * @return die Kopie der Bean.
	 * @throws IllegalArgumentException
	 *             wenn die Bean nicht kopierbar ist.
	 */
	public static Object copy(final Object source) {
		final Class<?> clazz = source.getClass();

		final BeanInfo info;
		try {
			info = Introspector.getBeanInfo(clazz);
		} catch (final IntrospectionException ex) {
			throw new IllegalArgumentException("Can not introspect bean: "
					+ clazz, ex);
		}

		final Object target;
		try {
			target = clazz.newInstance();
		} catch (final InstantiationException ex) {
			throw new IllegalArgumentException(
					"Can not copy interface or abstract class: " + clazz, ex);
		} catch (final IllegalAccessException ex) {
			throw new IllegalArgumentException(
					"Bean must have a public default constructor: " + clazz, ex);
		}

		for (final PropertyDescriptor pd : info.getPropertyDescriptors()) {
			final Method getter = pd.getReadMethod();
			final Method setter = pd.getWriteMethod();
			if (getter != null && setter != null) {
				try {
					setter.invoke(target, getter.invoke(source));
				} catch (final IllegalArgumentException ex) {
					throw new IllegalArgumentException(
							"Getter and setter have different type: " + clazz
									+ ", " + pd, ex);
				} catch (final IllegalAccessException ex) {
					throw new IllegalArgumentException(
							"Getter and setter must be public: " + clazz + ", "
									+ pd, ex);
				} catch (final InvocationTargetException ex) {
					throw new IllegalArgumentException(
							"Getter or setter throws an exception: " + clazz
									+ ", " + pd, ex);
				}
			}
		}

		return target;
	}

	/**
	 * Erstellt eine neue Instanz der übergebenen Bean-Klasse.
	 * 
	 * @param <T>
	 *            der Typ der Bean die angelegt werden soll.
	 * @param beanClass
	 *            die Klasse der Bean die angelegt werden soll.
	 * @return die neue Instanz der Bean.
	 */
	public static <T> T createInstance(final Class<T> beanClass) {
		try {
			return beanClass.newInstance();
		} catch (final InstantiationException ex) {
			throw new IllegalArgumentException(
					"Cannot create new instance of bean type " + beanClass
							+ ".", ex);
		} catch (final IllegalAccessException ex) {
			throw new IllegalArgumentException(
					"Cannot create new instance of bean type " + beanClass
							+ ".", ex);
		}
	}

	/**
	 * Liest den Wert der Property einer Java Bean.
	 * 
	 * @param target
	 *            eine Java Bean.
	 * @param propertyName
	 *            der Name der Property.
	 * @return der Wert.
	 */
	public static Object getProperty(final Object target,
			final String propertyName) {
		final BeanInfo beanInfo = getBeanInfo(target.getClass());

		for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			if (pd.getName().equals(propertyName)) {
				try {
					return pd.getReadMethod().invoke(target);
				} catch (final IllegalAccessException ex) {
					throw new IllegalArgumentException(
							"Cannot read property data.", ex);
				} catch (final InvocationTargetException ex) {
					throw new IllegalArgumentException(
							"Cannot read property data.", ex);
				}
			}
		}

		throw new IllegalArgumentException("Bean "
				+ target.getClass().getName() + " has no property "
				+ propertyName + ".");

	}

	/**
	 * Setzt den Wert der Property einer Java Bean.
	 * 
	 * @param target
	 *            eine Java Bean.
	 * @param propertyName
	 *            der Name der Property.
	 * @param value
	 *            der neue Wert.
	 */
	public static void setProperty(final Object target,
			final String propertyName, final Object value) {
		final BeanInfo beanInfo = getBeanInfo(target.getClass());

		for (final PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
			if (pd.getName().equals(propertyName)) {
				try {
					pd.getWriteMethod().invoke(target, value);
				} catch (final IllegalAccessException ex) {
					throw new IllegalArgumentException(
							"Cannot set property data.", ex);
				} catch (final InvocationTargetException ex) {
					throw new IllegalArgumentException(
							"Cannot set property data.", ex);
				}
				return;
			}
		}

		throw new IllegalArgumentException("Bean "
				+ target.getClass().getName() + " has no property "
				+ propertyName + ".");

	}

	private static Map<Class<?>, BeanInfo> beanInfoCache = Collections
			.synchronizedMap(new WeakHashMap<Class<?>, BeanInfo>());

	/**
	 * Bestimmt zu einer Klasse die dazugehörige {@link BeanInfo}. Es wird die
	 * Bean Info-Klasse direkt geladen, weil
	 * {@link Introspector#getBeanInfo(Class)} eine Bean Info zurückgibt, deren
	 * Properties nicht mehr der Originalsortierung entsprechen. Sollte die Bean
	 * Info-Klasse nicht direkt geladen werden können, wird als Rückfallebene
	 * der Introspector gefragt.
	 * 
	 * @param beanClass
	 *            eine Klasse.
	 * @return die Bean Info zu der Klasse.
	 */
	public static BeanInfo getBeanInfo(final Class<?> beanClass) {
		if (beanInfoCache.containsKey(beanClass)) {
			return beanInfoCache.get(beanClass);
		}

		final ClassLoader loader = beanClass.getClassLoader();
		Class<? extends BeanInfo> beanInfoClass;
		BeanInfo info = null;
		try {
			beanInfoClass = (Class<? extends BeanInfo>) loader
					.loadClass(beanClass.getName() + "BeanInfo");
			info = beanInfoClass.newInstance();
		} catch (final ClassNotFoundException ex) {
			try {
				info = Introspector.getBeanInfo(beanClass);
			} catch (final IntrospectionException ex1) {
				throw new IllegalArgumentException(
						"Can not analyze Java Bean type " + beanClass + ".",
						ex1);
			}
		} catch (final InstantiationException ex) {
			throw new IllegalArgumentException(
					"Can not analyze Java Bean type " + beanClass + ".", ex);
		} catch (final IllegalAccessException ex) {
			throw new IllegalArgumentException(
					"Can not analyze Java Bean type " + beanClass + ".", ex);
		}

		beanInfoCache.put(beanClass, info);
		return info;
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine Textproperty handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine Textproperty handelt.
	 */
	public static boolean isText(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(String.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine ganze Zahl handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine ganze Zahl handelt.
	 */
	public static boolean isNumber(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(long.class)
				|| pd.getPropertyType().isAssignableFrom(Long.class)
				|| pd.getPropertyType().isAssignableFrom(int.class)
				|| pd.getPropertyType().isAssignableFrom(Integer.class)
				|| pd.getPropertyType().isAssignableFrom(short.class)
				|| pd.getPropertyType().isAssignableFrom(Short.class)
				|| pd.getPropertyType().isAssignableFrom(byte.class)
				|| pd.getPropertyType().isAssignableFrom(Byte.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine reele Zahl handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine reele Zahl handelt.
	 */
	public static boolean isRealNumber(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(float.class)
				|| pd.getPropertyType().isAssignableFrom(Float.class)
				|| pd.getPropertyType().isAssignableFrom(double.class)
				|| pd.getPropertyType().isAssignableFrom(Double.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine Boolean-Property handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine Boolean-Property handelt.
	 */
	public static boolean isBoolean(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(boolean.class)
				|| pd.getPropertyType().isAssignableFrom(Boolean.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine Zeitangabe handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine Zeitangabe handelt.
	 */
	public static boolean isTime(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(java.sql.Time.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um eine Datumsangabe handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um eine Datumsangabe handelt.
	 */
	public static boolean isDate(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(java.sql.Date.class);
	}

	/**
	 * Prüft, ob es sich bei einer Property um einen Zeitstempel handelt.
	 * 
	 * @param pd
	 *            ein Property Descriptor.
	 * @return {@code true}, wenn es sich um einen Zeitstempel handelt.
	 */
	public static boolean isTimestamp(final PropertyDescriptor pd) {
		return pd.getPropertyType().isAssignableFrom(java.util.Date.class)
				|| pd.getPropertyType().isAssignableFrom(
						java.sql.Timestamp.class);
	}

	/**
	 * Gibt den eine Bean als String zurück. Der zurückgegebene String
	 * entspricht dem üblichen allgemeinen {@code toString()} z.&nbsp;B. {@code
	 * Punkt[x=10, y=20]}.
	 * 
	 * @param bean
	 *            eine beliebige Java Bean.
	 * @return der String zur Bean.
	 */
	public static String toString(final Object bean) {
		if (bean == null) {
			return "null";
		}

		final Class<?> objClass = bean.getClass();
		final Method[] methods = objClass.getMethods();

		String buffer = bean.getClass().getName() + "[";
		for (int i = 0; i < methods.length; ++i) {
			final Method method = methods[i];
			if (!method.getName().equals("getClass")
					&& (method.getName().startsWith("get") || method.getName()
							.startsWith("is"))
					&& method.getParameterTypes().length == 0) {
				final String name = method.getName();
				if (name.startsWith("get")) {
					buffer += name.substring(3);
				} else {
					buffer += name.substring(2);
				}
				buffer += "=";
				try {
					buffer += method.invoke(bean);
				} catch (final Exception ex) {
					buffer += "[property unreadable]";
				}
				buffer += ", ";
			}
		}

		// ", " am Ende wieder entfernen
		buffer = buffer.substring(0, buffer.length() - 2);

		buffer += "]";

		return buffer;
	}

	private BeanUtils() {
		// nix
	}

}
