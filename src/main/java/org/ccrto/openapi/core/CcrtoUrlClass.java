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

}
