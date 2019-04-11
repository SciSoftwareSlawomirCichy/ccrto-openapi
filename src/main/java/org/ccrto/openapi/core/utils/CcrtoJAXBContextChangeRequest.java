package org.ccrto.openapi.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.ccrto.openapi.core.CcrtoProperty;

public class CcrtoJAXBContextChangeRequest {

	private final List<Change> changes = new ArrayList<>();

	public boolean addChange(String propertyName, Class<? extends CcrtoProperty> clazz) {
		Change change = new Change(propertyName, clazz);
		return changes.add(change);
	}

	public boolean addChange(Change change) {
		if (change != null) {
			return changes.add(change);
		}
		return false;
	}

	/**
	 * @return the {@link #changes}
	 */
	public List<Change> getChanges() {
		return changes;
	}

	public void commit() {
		CcrtoPropertyMetadataSource.addJavaAttribute(changes);
	}

	public static Change createChange(String propertyName, Class<? extends CcrtoProperty> clazz) {
		return new Change(propertyName, clazz);
	}

	public static class Change {
		private final String propertyName;
		private final Class<? extends CcrtoProperty> clazz;

		/**
		 * @return the {@link #propertyName}
		 */
		public String getPropertyName() {
			return propertyName;
		}

		public Change(String propertyName, Class<? extends CcrtoProperty> clazz) {
			super();
			this.propertyName = propertyName;
			this.clazz = clazz;
		}

		/**
		 * @return the {@link #clazz}
		 */
		public Class<? extends CcrtoProperty> getClazz() {
			return clazz;
		}
	}

}
