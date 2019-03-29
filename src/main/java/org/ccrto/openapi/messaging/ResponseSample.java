package org.ccrto.openapi.messaging;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.core.adapters.CcrtoPropertyAdapter;

@XmlRootElement
public class ResponseSample {

	public CcrtoPropertyCase data;
}
