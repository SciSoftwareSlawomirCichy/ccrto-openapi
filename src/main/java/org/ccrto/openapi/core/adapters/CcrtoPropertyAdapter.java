package org.ccrto.openapi.core.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.ccrto.openapi.core.CcrtoProperty;
import org.w3c.dom.Element;

public class CcrtoPropertyAdapter extends XmlAdapter<CcrtoProperty, CcrtoProperty> {

	@Override
	public CcrtoProperty unmarshal(CcrtoProperty v) throws Exception {
		System.out.println(v.getType());
		return v;
	}

	@Override
	public CcrtoProperty marshal(CcrtoProperty v) throws Exception {
		// TODO Auto-generated method stub
		return v;
	}

}
