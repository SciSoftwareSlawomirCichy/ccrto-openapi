package org.ccrto.openapi.core.internal;

import java.util.List;

import org.ccrto.openapi.core.CaseHeader;
import org.ccrto.openapi.core.CcrtoProperty;
import org.ccrto.openapi.core.CcrtoPropertyStatus;

public interface IValueCase extends IValueObject {

	CcrtoPropertyStatus getStatus();

	void setStatus(CcrtoPropertyStatus status);

	List<CcrtoProperty> getCaseProperties();

	CaseHeader getCaseHeader();

	void setCaseHeader(CaseHeader caseHeader);

	int size();

	boolean isEmpty();

	boolean contains(CcrtoProperty o);

	boolean add(CcrtoProperty e);

}
