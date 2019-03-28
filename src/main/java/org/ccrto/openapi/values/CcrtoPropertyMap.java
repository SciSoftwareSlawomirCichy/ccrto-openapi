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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.values.api.IValueMap;
import org.ccrto.openapi.values.utils.CcrtoPropertyTypeUtils;

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
@XmlType(name = "ccrtoMap")
public class CcrtoPropertyMap extends CcrtoProperty implements IValueMap {

	private static final long serialVersionUID = 293486657109948155L;

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
	public CcrtoPropertyMap(int initialCapacity, float loadFactor) {
		map = new HashMap<>(initialCapacity, loadFactor);
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
	public CcrtoPropertyMap(int initialCapacity) {
		map = new HashMap<>(initialCapacity);
	}

	/**
	 * Constructs an empty <tt>HashMap</tt> with the default initial capacity (16)
	 * and the default load factor (0.75).
	 */
	public CcrtoPropertyMap() {
		map = new HashMap<>();
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
	public CcrtoPropertyMap(Map<String, String> m) {
		map = new HashMap<>(m);
	}

	/**
	 * @return the {@link #value}
	 */
	@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_MAP_ENTRY_NAME, type = CcrtoPropertyEntry.class)
	public List<CcrtoPropertyEntry> getValue() {
		List<CcrtoPropertyEntry> value = new ArrayList<>();
		for (Entry<String, String> entry : this.map.entrySet()) {
			value.add(new CcrtoPropertyEntry(entry.getKey(), entry.getValue()));
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
				this.map.put(entry.getKey(), entry.getValue());
			}
		}
	}

	public int size() {
		return this.map.size();
	}

	public boolean isEmpty() {
		return this.map.isEmpty();
	}

	public boolean containsKey(Object key) {
		return this.map.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return this.map.containsValue(value);
	}

	public String get(Object key) {
		return this.map.get(key);
	}

	public String put(String key, String value) {
		return this.map.put(key, value);
	}

	public String remove(Object key) {
		return this.map.remove(key);
	}

	public void putAll(Map<? extends String, ? extends String> m) {
		this.map.putAll(m);
	}

	public void clear() {
		this.map.clear();
	}

	public Set<String> keySet() {
		return this.map.keySet();
	}

	public Collection<String> values() {
		return this.map.values();
	}

	public Set<Entry<String, String>> entrySet() {
		Set<Entry<String, String>> value = new HashSet<>();
		for (Entry<String, String> entry : this.map.entrySet()) {
			value.add(new CcrtoPropertyEntry(entry.getKey(), entry.getValue()));
		}
		return value;
	}

	public void setType(String type) {
		CcrtoPropertyType fieldType = CcrtoPropertyType.getType(type);
		if (fieldType == null || !CcrtoPropertyType.MAP.equals(fieldType)) {
			throw new IllegalArgumentException(
					String.format("Type should be value: \"%s\"", CcrtoPropertyType.MAP.getName()));
		}
		this.type = type;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return false;
	}

}
