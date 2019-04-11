package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * KnownLobMetadata znane formaty danych przechowywane w polach typu LOB.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
@XmlType(name = "KnownLobMetadata")
@XmlEnum
public enum KnownLobMetadata {

	@XmlEnumValue("JSON")
	JSON, 
	@XmlEnumValue("ANY")
	ANY, 
	@XmlEnumValue("FILE")
	FILE, 
	@XmlEnumValue("XML")
	XML, 
	@XmlEnumValue("HTML")
	HTML, 
	@XmlEnumValue("TEXT")
	TEXT;

	public String getDefaultContentType() {
		switch (this) {
		case JSON:
			return "application/json";
		case XML:
			return "application/xml";
		case HTML:
			return "text/html";
		case FILE:
			return "application/octet-stream";
		default:
			return "text/plain";
		}
	}

	public static String getDefaultContentType(String metadataInfo) {
		try {
			KnownLobMetadata m = KnownLobMetadata.valueOf(metadataInfo);
			return m.getDefaultContentType();
		} catch (Exception e) {
			return "text/plain";
		}
	}

}
