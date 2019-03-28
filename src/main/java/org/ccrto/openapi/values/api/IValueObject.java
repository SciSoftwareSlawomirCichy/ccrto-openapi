package org.ccrto.openapi.values.api;

import java.io.Serializable;

public interface IValueObject extends Serializable {

	void setType(String type);

	String getType();

	boolean isNull();

}
