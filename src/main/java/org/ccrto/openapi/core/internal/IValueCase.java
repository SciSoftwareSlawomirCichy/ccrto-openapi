package org.ccrto.openapi.core.internal;

import java.util.List;

import org.ccrto.openapi.core.CaseHeader;
import org.ccrto.openapi.core.CcrtoProperty;

public interface IValueCase extends IValueWithProperties {

	List<CcrtoProperty> getCaseProperties();

	CaseHeader getCaseHeader();

	void setCaseHeader(CaseHeader caseHeader);

	int size();

	boolean isEmpty();

	boolean contains(CcrtoProperty o);

}
