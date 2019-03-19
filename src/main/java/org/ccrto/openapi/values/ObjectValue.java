package org.ccrto.openapi.values;

import java.io.Serializable;

public interface ObjectValue extends Serializable {

	void setType(String type);

	String getType();

}
