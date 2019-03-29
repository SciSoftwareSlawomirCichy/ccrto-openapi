package org.ccrto.openapi.core.internal;

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

}