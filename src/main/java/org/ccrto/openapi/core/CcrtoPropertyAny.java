package org.ccrto.openapi.core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.core.internal.IValueObject;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "Any")
public class CcrtoPropertyAny extends CcrtoProperty implements IValueObject {

	private static final long serialVersionUID = -4159932601037809795L;

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return false;
	}

}
