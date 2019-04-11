package org.ccrto.openapi.core.internal;

import org.ccrto.openapi.core.CcrtoProperty;
import org.ccrto.openapi.core.CcrtoPropertyStatus;

public interface IValueWithProperties extends IValueObject {

	CcrtoPropertyStatus getStatus();

	void setStatus(CcrtoPropertyStatus status);

	boolean add(CcrtoProperty e);

}
