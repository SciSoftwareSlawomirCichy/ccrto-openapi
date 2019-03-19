package org.ccrto.openapi.values;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pro.ibpm.mercury.attrs.AttributeType;
import pro.ibpm.mercury.attrs.AttributeTypeUtils;
import pro.ibpm.mercury.attrs.javax.api.IObjectValue;
import pro.ibpm.mercury.attrs.sub.api.ISubType;

@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "List", namespace = "http://pro.ibpm.mercury.attrs")
public class ListValue<E> implements IObjectValue {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4736522027130030852L;

	@JsonIgnore
	@XmlAttribute(required = false)
	private String type;

	private final ArrayList<E> value;

	private boolean typeIsInit = false;

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public ListValue() {
		super();
		value = new ArrayList<E>();
	}

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity
	 *            the initial capacity of the list
	 * @exception IllegalArgumentException
	 *                if the specified initial capacity is negative
	 */
	public ListValue(int initialCapacity) {
		super();
		value = new ArrayList<E>(initialCapacity);
	}

	/**
	 * Constructs a list containing the elements of the specified collection, in the
	 * order they are returned by the collection's iterator.
	 *
	 * @param c
	 *            the collection whose elements are to be placed into this list
	 * @throws NullPointerException
	 *             if the specified collection is null
	 */
	public ListValue(Collection<? extends E> c) {
		super();
		value = new ArrayList<E>(c);
		if (!value.isEmpty()) {
			typeIsInit = true;
			setType(value.get(0).getClass());
		}
	}

	private void setType(Class<?> clazz) {
		String typeName = clazz.getSimpleName();
		ISubType subType = AttributeType.subTypeClassOf(clazz);
		if (AttributeType.ANY.getName().equals(subType.getName())) {
			this.type = typeName + AttributeTypeUtils.ARRAY_SUFFIX;
		} else {
			this.type = subType.getName() + AttributeTypeUtils.ARRAY_SUFFIX;
		}
	}

	public int size() {
		return this.value.size();
	}

	public boolean isEmpty() {
		return this.value.isEmpty();
	}

	public boolean contains(Object o) {
		return this.value.contains(o);
	}

	public Iterator<E> iterator() {
		return this.value.iterator();
	}

	public Object[] toArray() {
		return this.value.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.value.toArray(a);
	}

	public boolean add(E e) {
		if (!typeIsInit) {
			typeIsInit = true;
			setType(e.getClass());
		}
		return this.value.add(e);
	}

	public boolean remove(Object o) {
		return this.value.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.value.containsAll(c);
	}

	public boolean addAll(Collection<? extends E> c) {
		return this.value.addAll(c);
	}

	public boolean addAll(int index, Collection<? extends E> c) {
		return this.value.addAll(index, c);
	}

	public boolean removeAll(Collection<?> c) {
		return this.value.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return this.value.retainAll(c);
	}

	public void clear() {
		this.value.clear();
	}

	public E get(int index) {
		return this.value.get(index);
	}

	public E set(int index, E element) {
		return this.value.set(index, element);
	}

	public void add(int index, E element) {
		this.value.add(index, element);
	}

	public E remove(int index) {
		return this.value.remove(index);
	}

	public int indexOf(Object o) {
		return this.value.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.value.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return this.value.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return this.value.listIterator(index);
	}

	public ListValue<E> subList(int fromIndex, int toIndex) {
		return new ListValue<E>(this.value.subList(fromIndex, toIndex));
	}

	/**
	 * @return the {@link #value}
	 */
	@XmlElement(name = "item", namespace = "http://pro.ibpm.mercury.attrs")
	public List<E> getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the {@link #value} to set
	 */
	public void setValue(List<E> value) {
		/* value is final - ignore */
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
