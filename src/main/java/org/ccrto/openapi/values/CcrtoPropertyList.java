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
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.values.api.IValueList;
import org.ccrto.openapi.values.utils.CcrtoPropertyTypeUtils;

/**
 * 
 * CcrtoPropertyList
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 * @param <E>
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoList")
public class CcrtoPropertyList extends CcrtoProperty implements IValueList {

	private static final long serialVersionUID = 4884910109099685130L;

	@XmlAttribute(name = "status", required = false)
	private CcrtoPropertyStatus status;

	@XmlElements({
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyAny.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyBoolean.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyCase.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyCurrency.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyDate.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_MAP_ENTRY_NAME, type = CcrtoPropertyEntry.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyInteger.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyLob.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyNameValuePair.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyNumber.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyURL.class),
			@XmlElement(name = CcrtoPropertyTypeUtils.DEFAULT_COLLECTION_ITEM_NAME, type = CcrtoPropertyString.class) })
	private final List<CcrtoProperty> value;

	private boolean typeIsInit = false;
	private boolean allElementsSameType = true;
	private final Object valueLock = new Object();

	/**
	 * Constructs an empty list with an initial capacity of ten.
	 */
	public CcrtoPropertyList() {
		super();
		value = new ArrayList<>();
	}

	/**
	 * Constructs an empty list with the specified initial capacity.
	 *
	 * @param initialCapacity
	 *            the initial capacity of the list
	 * @exception IllegalArgumentException
	 *                if the specified initial capacity is negative
	 */
	public CcrtoPropertyList(int initialCapacity) {
		super();
		value = new ArrayList<>(initialCapacity);
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
	public CcrtoPropertyList(Collection<? extends CcrtoProperty> c) {
		super();
		value = new ArrayList<>(c);
		if (!value.isEmpty()) {
			String typeName = value.get(0).getType();
			if (StringUtils.isNotBlank(typeName)) {
				setType(typeName);
			} else {
				setType(value.get(0).getClass());
			}
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

	public Iterator<CcrtoProperty> iterator() {
		return this.value.iterator();
	}

	public Object[] toArray() {
		return this.value.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return this.value.toArray(a);
	}

	public boolean remove(Object o) {
		return this.value.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return this.value.containsAll(c);
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

	public CcrtoProperty get(int index) {
		return this.value.get(index);
	}

	public CcrtoProperty remove(int index) {
		return this.value.remove(index);
	}

	public int indexOf(Object o) {
		return this.value.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return this.value.lastIndexOf(o);
	}

	public ListIterator<CcrtoProperty> listIterator() {
		return this.value.listIterator();
	}

	public ListIterator<CcrtoProperty> listIterator(int index) {
		return this.value.listIterator(index);
	}

	public CcrtoPropertyList subList(int fromIndex, int toIndex) {
		return new CcrtoPropertyList(this.value.subList(fromIndex, toIndex));
	}

	public boolean add(CcrtoProperty e) {
		checkListType(e);
		return this.value.add(e);
	}

	public CcrtoProperty set(int index, CcrtoProperty element) {
		checkListType(element);
		return this.value.set(index, element);
	}

	public void add(int index, CcrtoProperty element) {
		checkListType(element);
		this.value.add(index, element);
	}

	private void checkListType(CcrtoProperty e) {
		synchronized (valueLock) {
			String typeName = e.type;
			if (StringUtils.isBlank(typeName)) {
				typeName = getItemTypeName(e.getClass());
				e.setType(typeName);
			}
			if (!typeIsInit) {
				setType(typeName);
			} else if (allElementsSameType) {
				String listTypeName = getListTypeName(typeName);
				if (!listTypeName.equals(getType())) {
					allElementsSameType = false;
					setType(CcrtoPropertyType.ANY.getName());
					for (CcrtoProperty cp : this.value) {
						cp.resetType();
					}
				}
			}
			if (allElementsSameType) {
				/* resetujemy typ elementu, o typie elementu mówi nam typ listy */
				e.setTypeAsNull();
			}
		}
	}

	public boolean addAll(Collection<? extends CcrtoProperty> c) {
		boolean returnValue = true;
		if (c != null && !c.isEmpty()) {
			Iterator<? extends CcrtoProperty> iterator = c.iterator();
			while (iterator.hasNext()) {
				CcrtoProperty element = iterator.next();
				returnValue = returnValue && add(element);
			}
		} else {
			returnValue = false;
		}
		return returnValue;
	}

	public boolean addAll(int index, Collection<? extends CcrtoProperty> c) {
		boolean returnValue = true;
		int addIndex = index;
		if (c != null && !c.isEmpty()) {
			Iterator<? extends CcrtoProperty> iterator = c.iterator();
			while (iterator.hasNext()) {
				CcrtoProperty element = iterator.next();
				add(addIndex, element);
				addIndex++;
			}
		} else {
			returnValue = false;
		}
		return returnValue;
	}

	/**
	 * @return the {@link #value}
	 */
	public List<CcrtoProperty> getValue() {
		return value;
	}

	public void setType(String type) {
		this.type = getListTypeName(type);
		this.typeIsInit = true;
	}

	public void setType(Class<?> clazz) {
		String typeName = getItemTypeName(clazz);
		setType(typeName);
	}

	private static String getItemTypeName(Class<?> clazz) {
		String typeName = clazz.getSimpleName();
		CcrtoPropertyType typeOfItem = CcrtoPropertyType.classOf(clazz);
		if (CcrtoPropertyType.ANY.equals(typeOfItem)) {
			return typeName;
		} else {
			return typeOfItem.getName();
		}
	}

	private static String getListTypeName(String type) {
		String typeOfItem = type;
		if (StringUtils.isNotBlank(typeOfItem) && !typeOfItem.endsWith(CcrtoPropertyTypeUtils.ARRAY_SUFFIX)) {
			return typeOfItem + CcrtoPropertyTypeUtils.ARRAY_SUFFIX;
		} else {
			return type;
		}
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean isNull() {
		return CcrtoPropertyStatus.NULL.equals(status);
	}

	/**
	 * @return the {@link #status}
	 */
	public CcrtoPropertyStatus getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the {@link #status} to set
	 */
	public void setStatus(CcrtoPropertyStatus status) {
		this.status = status;
	}

}
