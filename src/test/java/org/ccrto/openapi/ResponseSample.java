package org.ccrto.openapi;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ccrto.openapi.values.CcrtoPropertyCase;

@XmlRootElement
public class ResponseSample {

	@XmlElement
	public CcrtoPropertyCase data;
}
