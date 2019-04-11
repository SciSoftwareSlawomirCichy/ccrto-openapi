package org.ccrto.openapi.core.internal;

import org.ccrto.openapi.core.KnownLobMetadata;

public interface IValueLob extends IValueObject, IValueWithEncodedFlag {

	/**
	 * @return the {@link #metadataInfo}
	 */
	KnownLobMetadata getMetadataInfo();

	/**
	 * @param metadataInfo
	 *            the {@link #metadataInfo} to set
	 */
	void setMetadataInfo(KnownLobMetadata metadataInfo);

	/**
	 * @return the {@link #mimeType}
	 */
	String getMimeType();

	/**
	 * @param mimeType
	 *            the {@link #mimeType} to set
	 */
	void setMimeType(String mimeType);

}
