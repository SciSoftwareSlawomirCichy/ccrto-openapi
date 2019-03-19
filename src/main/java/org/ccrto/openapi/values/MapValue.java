package org.ccrto.openapi.values;

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
import javax.xml.bind.annotation.XmlType;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.javax.api.IObjectValue;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Map", namespace = "http://pro.ibpm.mercury.attrs")
public class MapValue implements IObjectValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7862022424418866359L;

	@XmlAttribute(required = false)
	private String type;

	private final Map<String, String> map;

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
	public MapValue(int initialCapacity, float loadFactor) {
		this.type = AttributeType.MAP.getName();
		map = new HashMap<String, String>(initialCapacity, loadFactor);
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
	public MapValue(int initialCapacity) {
		this.type = AttributeType.MAP.getName();
		map = new HashMap<String, String>(initialCapacity);
	}

	/**
	 * Constructs an empty <tt>HashMap</tt> with the default initial capacity (16)
	 * and the default load factor (0.75).
	 */
	public MapValue() {
		this.type = AttributeType.MAP.getName();
		map = new HashMap<String, String>();
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
	public MapValue(Map<String, String> m) {
		this.type = AttributeType.MAP.getName();
		map = new HashMap<String, String>(m);
	}

	/**
	 * @return the {@link #value}
	 */
	@XmlElement(name = "entry", namespace = "http://pro.ibpm.mercury.attrs")
	public List<EntryValue> getValue() {
		List<EntryValue> value = new ArrayList<EntryValue>();
		for (Entry<String, String> entry : this.map.entrySet()) {
			value.add(new EntryValue(entry.getKey(), entry.getValue()));
		}
		return value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void setValue(List<EntryValue> value) {
		if (value != null && !value.isEmpty()) {
			for (EntryValue entry : value) {
				this.map.put(entry.getKey(), entry.getValue());
			}
		}
	}

	/* Overridden (non-Javadoc) */

	public int size() {
		return this.map.size();
	}

	/* Overridden (non-Javadoc) */

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	/* Overridden (non-Javadoc) */

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	/* Overridden (non-Javadoc) */

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	/* Overridden (non-Javadoc) */

	public String get(Object key) {
		return this.map.get(key);
	}

	/* Overridden (non-Javadoc) */

	public String put(String key, String value) {
		return this.map.put(key, value);
	}

	/* Overridden (non-Javadoc) */

	public String remove(Object key) {
		return this.map.remove(key);
	}

	/* Overridden (non-Javadoc) */

	public void putAll(Map<? extends String, ? extends String> m) {
		this.map.putAll(m);
	}

	/* Overridden (non-Javadoc) */

	public void clear() {
		this.map.clear();
	}

	/* Overridden (non-Javadoc) */

	public Set<String> keySet() {
		return this.map.keySet();
	}

	/* Overridden (non-Javadoc) */

	public Collection<String> values() {
		return this.map.values();
	}

	/* Overridden (non-Javadoc) */

	public Set<Entry<String, String>> entrySet() {
		Set<Entry<String, String>> value = new HashSet<Map.Entry<String, String>>();
		for (Entry<String, String> entry : this.map.entrySet()) {
			value.add(new EntryValue(entry.getKey(), entry.getValue()));
		}
		return value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
