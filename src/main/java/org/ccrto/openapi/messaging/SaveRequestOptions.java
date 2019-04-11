package org.ccrto.openapi.messaging;

import java.util.HashMap;
import java.util.Map;

public class SaveRequestOptions extends HashMap<String, Object> {

	private static final long serialVersionUID = -9190177301800183408L;

	public SaveRequestOptions() {
		super();
	}

	public SaveRequestOptions(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor);
	}

	public SaveRequestOptions(int initialCapacity) {
		super(initialCapacity);
	}

	public SaveRequestOptions(Map<? extends String, ? extends Object> m) {
		super(m);
	}

}
