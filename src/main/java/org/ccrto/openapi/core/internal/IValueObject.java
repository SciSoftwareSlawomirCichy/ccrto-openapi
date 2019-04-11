package org.ccrto.openapi.core.internal;

import java.io.Serializable;

public interface IValueObject extends Serializable {

	void setType(String type);

	String getType();

	boolean isNull();

	Integer getPosition();

	void setPosition(Integer position);

}
