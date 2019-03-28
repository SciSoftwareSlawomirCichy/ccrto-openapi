package org.ccrto.openapi.values;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.ccrto.openapi.values.api.IValueLob;

@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(name = "ccrtoLob")
public class CcrtoPropertyLob extends CcrtoProperty implements IValueLob {

	private static final long serialVersionUID = 6818579847092109946L;

	@Override
	public boolean isNull() {
		// TODO Auto-generated method stub
		return false;
	}

}
