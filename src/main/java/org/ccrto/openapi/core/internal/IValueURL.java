package org.ccrto.openapi.core.internal;

import org.ccrto.openapi.core.CcrtoUrlClass;

public interface IValueURL extends IValueObject {

	/**
	 * @return the {@link #title}
	 */
	String getTitle();

	/**
	 * @param title
	 *            the {@link #title} to set
	 */
	void setTitle(String title);

	/**
	 * @return the {@link #urlReference}
	 */
	String getUrlReference();

	/**
	 * @param urlReference
	 *            the {@link #urlReference} to set
	 */
	void setUrlReference(String urlReference);
	
	CcrtoUrlClass getUrlClass();
	
	void setUrlClass(CcrtoUrlClass urlClass);

}