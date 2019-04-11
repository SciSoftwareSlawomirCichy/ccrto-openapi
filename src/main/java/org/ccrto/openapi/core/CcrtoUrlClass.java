package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;

@XmlType(name = "CcrtoUrlClass")
@XmlEnum
public enum CcrtoUrlClass {

	/** obrazek */
	@XmlEnumValue("img")
	IMAGE("img"),
	/** Dokument Word, dokument */
	@XmlEnumValue("doc")
	DOCUMENT("doc"),
	/** Dokument Excel, arkusz kalkulacyjny */
	@XmlEnumValue("xls")
	EXCEL("xls"),
	/** Dokument PDF */
	@XmlEnumValue("pdf")
	PDF("pdf"),
	/** Ogólnie link zewnętrzny, niewiadomego typu */
	@XmlEnumValue("link")
	LINK("link");

	private final String name;

	private CcrtoUrlClass(String name) {
		this.name = name;
	}

	public static CcrtoUrlClass getClass(String type) {
		if (StringUtils.isBlank(type)) {
			return null;
		}
		for (CcrtoUrlClass urlType : CcrtoUrlClass.values()) {
			if (type.equals(urlType.name)) {
				return urlType;
			}
		}
		return null;
	}

	public static CcrtoUrlClass fromValue(String v) {
		CcrtoUrlClass urlType = getClass(v);
		if (urlType == null) {
			throw new IllegalArgumentException(v);
		}
		return urlType;
	}

	/**
	 * @return the {@link #name}
	 */
	public String getName() {
		return name;
	}

	public static CcrtoUrlClass valueOfMimeType(String mimeType) {
		if (mimeType.startsWith("image")) {
			return CcrtoUrlClass.IMAGE;
		}
		if (mimeType.equals("application/pdf")) {
			return CcrtoUrlClass.PDF;
		}
		if (mimeType.contains("msword")) {
			return CcrtoUrlClass.DOCUMENT;
		}
		if (mimeType.equals("application/vnd.openxmlformats-officedocument.wordprocessingml.document")) {
			return CcrtoUrlClass.DOCUMENT;
		}
		if (mimeType.contains("ms-excel")) {
			return CcrtoUrlClass.EXCEL;
		}
		if (mimeType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")) {
			return CcrtoUrlClass.EXCEL;
		}
		return CcrtoUrlClass.LINK;
	}

}
