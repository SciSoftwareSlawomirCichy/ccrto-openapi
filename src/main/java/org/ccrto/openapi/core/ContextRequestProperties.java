package org.ccrto.openapi.core;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ContextRequestProperties extends HashMap<String, Object> {

	private static final long serialVersionUID = -4398310366203796960L;

	public ContextRequestProperties() {
		super();
	}

	public ContextRequestProperties(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public ContextRequestProperties(int initialCapacity) {
		super(initialCapacity);
	}

	public ContextRequestProperties(Map<? extends String, ? extends Object> m) {
		super(m);
	}

}
