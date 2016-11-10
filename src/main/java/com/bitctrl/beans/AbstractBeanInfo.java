/*
 * Java Common Library
 * Copyright (c) 2008 BitCtrl Systems GmbH
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or (at your option)
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

import java.beans.BeanDescriptor;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * Abstracte Implementierung der Beschreibung einer Java Bean. Die Klasse bietet
 * eine einheitliche Schnittstelle, um Übersetzungen für Namen und
 * Kurzbeschreibung von Java Bean und deren Properties. anzubieten
 * 
 * @author BitCtrl Systems GmbH, Falko Schumann
 */
public abstract class AbstractBeanInfo extends SimpleBeanInfo {

	// Die Descriptoren werden aus Performancegründen nach dem Erzeugen gecacht.
	private BeanDescriptor beanDescriptorCache;
	private PropertyDescriptor[] propertyDescriptorCache;

	/**
	 * Die Methode legt die zu beschreibende Java Bean fest und setzt deren
	 * übersetzten Namen und Kurzbeschreibung. Da sich die Bean zur Laufzeit
	 * i.&nbsp;d.&nbsp;R. nicht ändert wird das Ergebnis der Funktion intern
	 * statisch gecacht, so dass der Aufruf ab dem zweiten Mal schneller
	 * vonstatten geht.
	 * 
	 * {@inheritDoc}
	 * 
	 * @see AbstractBeanInfo#getBeanClass()
	 * @see #getDisplayName()
	 * @see #getShortDescription()
	 */
	@Override
	public BeanDescriptor getBeanDescriptor() {
		synchronized (AbstractBeanInfo.class) {
			if (beanDescriptorCache == null) {
				final Class<?> beanClass = getBeanClass();
				beanDescriptorCache = new BeanDescriptor(beanClass);

				beanDescriptorCache.setDisplayName(getDisplayName());
				beanDescriptorCache.setShortDescription(getShortDescription());
			}
		}
		return beanDescriptorCache;
	}

	/**
	 * Die Methode registriert die vorhandenen Properties und setzt deren
	 * übersetzte Namen und Kurzbeschreibungen. Da sich die Bean zur Laufzeit
	 * i.&nbsp;d.&nbsp;R. nicht ändert wird das Ergebnis der Funktion intern
	 * statisch gecacht, so dass der Aufruf ab dem zweiten Mal schneller
	 * vonstatten geht.
	 * 
	 * {@inheritDoc}
	 * 
	 * @see #getProperties()
	 * @see #getDisplayName(PropertyInfo)
	 * @see #getShortDescription(PropertyInfo)
	 * @see #getHiddenProperties()
	 * @see #getPreferredProperties()
	 * @see #getExpertProperties()
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		synchronized (AbstractBeanInfo.class) {
			if (propertyDescriptorCache == null) {
				final PropertyInfo[] propInfo = getProperties();
				final List<PropertyInfo> hiddenProps = Arrays.asList(getHiddenProperties());
				final List<PropertyInfo> preferredProps = Arrays.asList(getPreferredProperties());
				final List<PropertyInfo> expertProps = Arrays.asList(getExpertProperties());
				propertyDescriptorCache = new PropertyDescriptor[propInfo.length];

				try {
					for (int i = 0; i < propertyDescriptorCache.length; ++i) {
						final PropertyDescriptor prop;

						Class<?> beanClass = getBeanClass();
						String propName = propInfo[i].name();
						propName = propName.substring(0, 1).toUpperCase() + propName.substring(1);

						String readMethodName = null;
						try {
							readMethodName = beanClass.getMethod("get" + propName).getName();
						} catch (NoSuchMethodException ignored) {
							try {
								readMethodName = beanClass.getMethod("is" + propName).getName();
							} catch (NoSuchMethodException ex) {
								// dann eben nicht
							}
						}

						String writeMethodName = null;
						try {
							writeMethodName = beanClass.getMethod("set" + propName).getName();
						} catch (NoSuchMethodException ex) {
							// dann eben nicht
						}

						prop = new PropertyDescriptor(propInfo[i].name(), getBeanClass(), readMethodName,
								writeMethodName);
						prop.setDisplayName(getDisplayName(propInfo[i]));
						prop.setShortDescription(getShortDescription(propInfo[i]));
						if (hiddenProps.contains(propInfo[i])) {
							prop.setHidden(true);
						}
						if (preferredProps.contains(propInfo[i])) {
							prop.setPreferred(true);
						}
						if (expertProps.contains(propInfo[i])) {
							prop.setExpert(true);
						}
						propertyDescriptorCache[i] = prop;
					}
				} catch (final IntrospectionException ex) {
					propertyDescriptorCache = null;
					throw new IllegalStateException(ex);
				}
			}
		}

		return propertyDescriptorCache;
	}

	/**
	 * Gibt die Liste der Properties zurück der Java Bean zurück.
	 * 
	 * @return die Popertyliste.
	 */
	protected abstract PropertyInfo[] getProperties();

	/**
	 * Gibt die Liste der Properties zurück, die standardmäßig ausgeblendet
	 * werden sollen. Abgeleite Klassen können die Methode überschreiben. Die
	 * Standardimplementierung gibt eine leere Liste zurück.
	 * <p>
	 * <em>Hinweis:</em> Die versteckten Properties müssen auch in der Liste
	 * aller Properties enthalten sein.
	 * 
	 * @return die Liste der versteckten Properties oder ein leeres Feld,
	 *         niemals {@code null}.
	 * @see #getProperties()
	 */
	protected PropertyInfo[] getHiddenProperties() {
		return new PropertyInfo[0];
	}

	/**
	 * Gibt die Liste der Properties zurück, die als wichtig hervorgehoben
	 * werden sollen. Abgeleite Klassen können die Methode überschreiben. Die
	 * Standardimplementierung gibt eine leere Liste zurück.
	 * <p>
	 * <em>Hinweis:</em> Die wichtigen Properties müssen auch in der Liste aller
	 * Properties enthalten sein.
	 * 
	 * @return die Liste der wichtigen Properties oder ein leeres Feld, niemals
	 *         {@code null}.
	 * @see #getProperties()
	 */
	protected PropertyInfo[] getPreferredProperties() {
		return new PropertyInfo[0];
	}

	/**
	 * Gibt die Liste der Properties zurück, die nur fortgeschrittenen Nutzern
	 * angeboten werden sollen. Abgeleite Klassen können die Methode
	 * überschreiben. Die Standardimplementierung gibt eine leere Liste zurück.
	 * <p>
	 * <em>Hinweis:</em> Die Expertenproperties müssen auch in der Liste aller
	 * Properties enthalten sein.
	 * 
	 * @return die Liste der Expertenproperties oder ein leeres Feld, niemals
	 *         {@code null}.
	 * @see #getProperties()
	 */
	protected PropertyInfo[] getExpertProperties() {
		return new PropertyInfo[0];
	}

	/**
	 * Gibt den übersetzten Namen der Property zurück. Die Methode sollte in
	 * abgeleiteten Klassen überschrieben werden. Die Standardimplementierung
	 * gibt einfach den Propertynamen zurück.
	 * 
	 * @param info
	 *            eine Propertybeschreibung.
	 * @return der übersetzte Propertyname.
	 */
	protected String getDisplayName(final PropertyInfo info) {
		return info.name();
	}

	/**
	 * Gibt eine kurze Beschreibung der Property zurück. Diese kann z.&nbsp;B.
	 * von einem Editor als Tooltip angezeigt werden. Die Methode sollte in
	 * abgeleiteten Klassen überschrieben werden. Die Standardimplementierung
	 * gibt einfach den übersetzen Propertynamen zurück.
	 * 
	 * @param info
	 *            eine Propertybeschreibung.
	 * @return die kurze Beanbeschreibung.
	 * @see #getDisplayName(PropertyInfo)
	 */
	protected String getShortDescription(final PropertyInfo info) {
		return getDisplayName(info);
	}

	/**
	 * Gibt die Klasse der zu beschreibenden Java Bean zurück.
	 * 
	 * @return die Beanklasse.
	 */
	protected abstract Class<?> getBeanClass();

	/**
	 * Gibt den übersetzten Namen der Java Bean zurück. Die Methode sollte in
	 * abgeleiteten Klassen überschrieben werden. Die Standardimplementierung
	 * gibt einfach den Klassennamen (ohne Package) zurück.
	 * 
	 * @return der übersetzte Beanname.
	 */
	protected String getDisplayName() {
		return getBeanClass().getSimpleName();
	}

	/**
	 * Gibt eine kurze Beschreibung der Java Bean zurück. Diese kann z.&nbsp;B.
	 * von einem Editor als Tooltip angezeigt werden. Die Methode sollte in
	 * abgeleiteten Klassen überschrieben werden. Die Standardimplementierung
	 * gibt einfach den übersetzen Beannamen zurück.
	 * 
	 * @return die kurze Beanbeschreibung.
	 * @see #getDisplayName()
	 */
	protected String getShortDescription() {
		return getDisplayName();
	}

}
