package org.ccrto.openapi.core.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.stream.events.Attribute;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.CcrtoPropertyStatus;
import org.ccrto.openapi.core.ObjectFactory;
import org.w3c.dom.Element;

/**
 * 
 * ParserResult - pośredni wynik parsowania XML
 *
 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ParserResult {

	private final String tagName;
	private ParserResult parent;
	private ParserResult header;
	private java.util.Iterator<Attribute> attributes;

	private List<ParserResult> children;
	private Map<String, ParserResult> childrenMap;
	private final Object addChildLock = new Object();
	private String text;

	private final CcrtoPropertyStatus status;
	private String type;
	private final String version;
	private final String label;
	private final String id;
	private final Integer position;
	private final String isRequired;
	private final String updateable;
	private String isEncoded;
	private final String attrWithRequiredPosition;

	private boolean isLob = false;
	private boolean hasLobElements = false;
	private Object ccrtoObject;

	public ParserResult(Element element) {
		/* ustawienie danych na podstawie atrybutów elementu - START */
		String[] tagNameElements = element.getTagName().split("\\:");
		this.tagName = tagNameElements[tagNameElements.length - 1];
		this.type = element.getAttribute(ObjectFactory.ATTR_TYPE);
		this.version = element.getAttribute(ObjectFactory.ATTR_VERSION);
		this.label = element.getAttribute(ObjectFactory.ATTR_LABEL);
		this.id = element.getAttribute(ObjectFactory.ATTR_ID);
		this.isRequired = element.getAttribute(ObjectFactory.ATTR_IS_REQUIRED);
		this.updateable = element.getAttribute(ObjectFactory.ATTR_UPDATEABLE);
		this.attrWithRequiredPosition = element.getAttribute(ObjectFactory.ATTR_OBJECT_WITH_REQUIRED_POSITION);
		this.isEncoded = element.getAttribute(ObjectFactory.ATTR_IS_ENCODED);
		/* ustawienie danych na podstawie atrybutów elementu - KONIEC */

		/* ustawienie wartości pozycji */
		if (StringUtils.isNotBlank(element.getAttribute(ObjectFactory.ATTR_POSITION))) {
			this.position = Integer.parseInt(element.getAttribute(ObjectFactory.ATTR_POSITION));
		} else {
			this.position = null;
		}
		/* ustawianie statusu */
		String statusAttr = element.getAttribute(ObjectFactory.ATTR_STATUS);
		if (StringUtils.isNotBlank(statusAttr)) {
			this.status = CcrtoPropertyStatus.valueOf(statusAttr.toUpperCase());
		} else {
			this.status = CcrtoPropertyStatus.DEFAULT_STATUS;
		}
	}

	public ParserResult(String tagName, String type, String version, String label, String id, Integer position,
			String isRequired, String updateable, String isEncoded, String attrWithRequiredPosition, String status) {
		super();
		this.tagName = tagName;
		this.type = type;
		this.version = version;
		this.label = label;
		this.id = id;
		this.position = position;
		this.isRequired = isRequired;
		this.updateable = updateable;
		this.isEncoded = isEncoded;
		this.attrWithRequiredPosition = attrWithRequiredPosition;
		if (StringUtils.isNotBlank(status)) {
			this.status = CcrtoPropertyStatus.valueOf(status.toUpperCase());
		} else {
			this.status = CcrtoPropertyStatus.DEFAULT_STATUS;
		}
	}

	public ParserResult addChild(ParserResult child) {
		synchronized (addChildLock) {
			if (this.children == null) {
				this.children = new ArrayList<>();
			}
			if (this.childrenMap == null) {
				this.childrenMap = new HashMap<>();
			}
		}
		if (ObjectFactory.TAG_HEADER.equals(child.getTagName())) {
			this.header = child;
		} else {
			this.children.add(child);
			this.childrenMap.put(child.getTagName(), child);
		}
		return this;
	}

	public ParserResult getChildByTagName(String tagName) {
		if (this.childrenMap != null) {
			return this.childrenMap.get(tagName);
		}
		return null;
	}

	public boolean isArray() {
		if (this.childrenMap == null || this.children == null) {
			return false;
		}
		return this.childrenMap.size() == 1 && this.children.size() > 1;
	}

	public List<ParserResult> getChildren() {
		return this.children;
	}

	public int childrenSize() {
		if (this.children == null || this.children.isEmpty()) {
			return 0;
		}
		return this.children.size();
	}

	public int tagsSize() {
		if (this.childrenMap == null || this.childrenMap.isEmpty()) {
			return 0;
		}
		return this.childrenMap.size();
	}

	public boolean containsTag(String tagName) {
		if (this.childrenMap == null || this.childrenMap.isEmpty()) {
			return false;
		}
		return this.childrenMap.containsKey(tagName);
	}

	/**
	 * @return the {@link #text}
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the {@link #text} to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the {@link #tagName}
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @return the {@link #type}
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return the {@link #version}
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return the {@link #label}
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * @return the {@link #position}
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @return the {@link #isRequired}
	 */
	public String getIsRequired() {
		return isRequired;
	}

	/**
	 * @return the {@link #updateable}
	 */
	public String getUpdateable() {
		return updateable;
	}

	/**
	 * @return the {@link #isEncoded}
	 */
	public String getIsEncoded() {
		return isEncoded;
	}

	/**
	 * @return the {@link #attrWithRequiredPosition}
	 */
	public String getAttrWithRequiredPosition() {
		return attrWithRequiredPosition;
	}

	/**
	 * @param isEncoded
	 *            the {@link #isEncoded} to set
	 */
	public void setIsEncoded(String isEncoded) {
		this.isEncoded = isEncoded;
	}

	/**
	 * @param type
	 *            the {@link #type} to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the {@link #isLob}
	 */
	public boolean isLob() {
		return isLob;
	}

	/**
	 * @param isLob
	 *            the {@link #isLob} to set
	 */
	public void setLob(boolean isLob) {
		this.isLob = isLob;
	}

	/**
	 * @return the {@link #hasLobElements}
	 */
	public boolean hasLobElements() {
		return hasLobElements;
	}

	/**
	 * @param hasLobElements
	 *            the {@link #hasLobElements} to set
	 */
	public void setHasLobElements(boolean hasLobElements) {
		this.hasLobElements = hasLobElements;
	}

	/**
	 * @return the {@link #parent}
	 */
	public ParserResult getParent() {
		return parent;
	}

	/**
	 * @param parent
	 *            the {@link #parent} to set
	 */
	public void setParent(ParserResult parent) {
		this.parent = parent;
	}

	/**
	 * @return the {@link #attributes}
	 */
	public java.util.Iterator<Attribute> getAttributes() {
		return attributes;
	}

	/**
	 * @param attributes
	 *            the {@link #attributes} to set
	 */
	public void setAttributes(java.util.Iterator<Attribute> attributes) {
		this.attributes = attributes;
	}

	/**
	 * @return the {@link #status}
	 */
	public CcrtoPropertyStatus getStatus() {
		return status;
	}

	/**
	 * @return the {@link #ccrtoObject}
	 */
	public Object getCcrtoObject() {
		return ccrtoObject;
	}

	/**
	 * @param ccrtoProperty
	 *            the {@link #ccrtoObject} to set
	 */
	public void setCcrtoObject(Object ccrtoObject) {
		this.ccrtoObject = ccrtoObject;
	}

	/**
	 * @return the {@link #header}
	 */
	public ParserResult getHeader() {
		return header;
	}

	/**
	 * @param header
	 *            the {@link #header} to set
	 */
	public void setHeader(ParserResult header) {
		this.header = header;
	}

}
