package org.ccrto.openapi.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.core.internal.IValueMap;
import org.ccrto.openapi.core.utils.CcrtoPropertyTypeUtils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * CcrtoValueMap
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Map")
public class CcrtoPropertyMap extends CcrtoProperty implements IValueMap {

	private static final long serialVersionUID = 293486657109948155L;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlAttribute(name = "status", required = false)
	private CcrtoPropertyStatus status;

	private Map<String, String> internalMap;

	/**
	 * @return the {@link #value}
	 */
	@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_MAP_ENTRY_NAME, type = CcrtoPropertyEntry.class)
	public List<CcrtoPropertyEntry> getValue() {
		List<CcrtoPropertyEntry> value = new ArrayList<>();
		for (Entry<String, String> entry : this.getInternalMap().entrySet()) {
			CcrtoPropertyEntry ccrtoEntry = new CcrtoPropertyEntry();
			ccrtoEntry.setKey(entry.getKey());
			ccrtoEntry.setValue(entry.getValue());
			value.add(ccrtoEntry);
		}
		return value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void setValue(List<CcrtoPropertyEntry> value) {
		if (value != null && !value.isEmpty()) {
			for (CcrtoPropertyEntry entry : value) {
				this.getInternalMap().put(entry.getKey(), entry.getValue());
			}
		}
	}

	public int size() {
		return this.getInternalMap().size();
	}

	public boolean isEmpty() {
		return this.getInternalMap().isEmpty();
	}

	public boolean containsKey(Object key) {
		return this.getInternalMap().containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.getInternalMap().containsValue(value);
	}

	public String get(Object key) {
		return this.getInternalMap().get(key);
	}

	public String put(String key, String value) {
		return this.getInternalMap().put(key, value);
	}

	public String remove(Object key) {
		return this.getInternalMap().remove(key);
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		this.getInternalMap().putAll(m);
	}

	public void clear() {
		this.getInternalMap().clear();
	}

	public Set<String> keySet() {
		return this.getInternalMap().keySet();
	}

	public Collection<String> values() {
		return this.getInternalMap().values();
	}

	public Set<Entry<String, String>> entrySet() {
		Set<Entry<String, String>> value = new HashSet<>();
		for (Entry<String, String> entry : this.getInternalMap().entrySet()) {
			CcrtoPropertyEntry ccrtoEntry = new CcrtoPropertyEntry();
			ccrtoEntry.setKey(entry.getKey());
			ccrtoEntry.setValue(entry.getValue());
			value.add(ccrtoEntry);
		}
		return value;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return false;
	}

	/**
	 * @return the {@link #internalMap}
	 */
	private Map<String, String> getInternalMap() {
		if (this.internalMap == null) {
			this.internalMap = new HashMap<>();
		}
		return internalMap;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((internalMap == null) ? 0 : internalMap.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof CcrtoPropertyMap)) {
			return false;
		}
		CcrtoPropertyMap other = (CcrtoPropertyMap) obj;
		if (internalMap == null) {
			if (other.internalMap != null) {
				return false;
			}
		} else if (!internalMap.equals(other.internalMap)) {
			return false;
		}
		return true;
	}

	/**
	 * Constructs an empty <tt>HashMap</tt> with the specified initial capacity and
	 * load factor.
	 *
	 * @param initialCapacity
	 *            the initial capacity
	 * @param loadFactor
	 *            the load factor
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative or the load factor is
	 *             nonpositive
	 */
	public static CcrtoPropertyMap getInstance(int initialCapacity, float loadFactor) {
		CcrtoPropertyMap instance = new CcrtoPropertyMap();
		instance.internalMap = new HashMap<>(initialCapacity, loadFactor);
		return instance;
	}

	/**
	 * Constructs a new <tt>HashMap</tt> with the same mappings as the specified
	 * <tt>Map</tt>. The <tt>HashMap</tt> is created with default load factor (0.75)
	 * and an initial capacity sufficient to hold the mappings in the specified
	 * <tt>Map</tt>.
	 *
	 * @param m
	 *            the map whose mappings are to be placed in this map
	 * @throws NullPointerException
	 *             if the specified map is null
	 */
	public static CcrtoPropertyMap getInstance(Map<String, String> m) {
		CcrtoPropertyMap instance = new CcrtoPropertyMap();
		instance.internalMap = m;
		return instance;
	}

	/**
	 * Constructs an empty <tt>HashMap</tt> with the specified initial capacity and
	 * the default load factor (0.75).
	 *
	 * @param initialCapacity
	 *            the initial capacity.
	 * @throws IllegalArgumentException
	 *             if the initial capacity is negative.
	 */
	public static CcrtoPropertyMap getInstance(int initialCapacity) {
		CcrtoPropertyMap instance = new CcrtoPropertyMap();
		instance.internalMap = new HashMap<>(initialCapacity);
		return instance;
	}

	@Override
	public CcrtoPropertyStatus getStatus() {
		return status;
	}

	@Override
	public void setStatus(CcrtoPropertyStatus status) {
		this.status = status;
	}

	@Override
	public boolean add(CcrtoProperty e) {
		if (e instanceof CcrtoPropertyEntry) {
			CcrtoPropertyEntry entry = (CcrtoPropertyEntry) e;
			this.getInternalMap().put(entry.getKey(), entry.getValue());
			return true;
		}
		return false;
	}

}
