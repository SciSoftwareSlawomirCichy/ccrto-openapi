package org.ccrto.openapi.context;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RequestProperties extends HashMap<String, Object> {

	private static final long serialVersionUID = -4398310366203796960L;

	public RequestProperties() {
		super();
	}

	public RequestProperties(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public RequestProperties(int initialCapacity) {
		super(initialCapacity);
	}

	public RequestProperties(Map<? extends String, ? extends Object> m) {
		super(m);
	}

}
