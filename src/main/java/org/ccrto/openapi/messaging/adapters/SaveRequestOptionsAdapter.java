package org.ccrto.openapi.messaging.adapters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlValue;
import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.ccrto.openapi.core.ContextRequestProperties;
import org.eclipse.persistence.oxm.annotations.XmlVariableNode;

public class SaveRequestOptionsAdapter extends XmlAdapter<SaveRequestOptionsAdapter.AdaptedMap, ContextRequestProperties> {

	public static class AdaptedMap {
		@XmlVariableNode("key")
		List<AdaptedEntry> entries = new ArrayList<>();
	}

	public static class AdaptedEntry {
		@XmlTransient
		public String key;

		@XmlValue
		public Object value;
	}

	@Override
	public AdaptedMap marshal(ContextRequestProperties map) throws Exception {
		AdaptedMap adaptedMap = new AdaptedMap();
		for (Entry<String, Object> entry : map.entrySet()) {
			AdaptedEntry adaptedEntry = new AdaptedEntry();
			adaptedEntry.key = entry.getKey();
			adaptedEntry.value = entry.getValue();
			adaptedMap.entries.add(adaptedEntry);
		}
		return adaptedMap;
	}

	@Override
	public ContextRequestProperties unmarshal(AdaptedMap adaptedMap) throws Exception {
		List<AdaptedEntry> adaptedEntries = adaptedMap.entries;
		ContextRequestProperties map = new ContextRequestProperties(adaptedEntries.size());
		for (AdaptedEntry adaptedEntry : adaptedEntries) {
			map.put(adaptedEntry.key, adaptedEntry.value);
		}
		return map;
	}

}
