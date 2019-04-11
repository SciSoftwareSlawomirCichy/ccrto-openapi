package org.ccrto.openapi.core.utils;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;

import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.messaging.SaveRequest;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CcrtoJAXBContextUtils {

	private static final Logger logger = LoggerFactory.getLogger(CcrtoJAXBContextUtils.class);

	private CcrtoJAXBContextUtils() {
	}

	private static JAXBContext jaxbContext = null;
	private static final Object jaxbContextLock = new Object();

	/**
	 * @return the {@link #jaxbContext}
	 */
	public static JAXBContext getJaxbContext() {
		synchronized (jaxbContextLock) {
			if (jaxbContext == null) {
				jaxbContext = buildContext();
			}
			return jaxbContext;
		}
	}

	/**
	 * @param jaxbContext
	 *            the {@link #jaxbContext} to set
	 */
	static void setJaxbContext(JAXBContext jaxbContext) {
		synchronized (jaxbContextLock) {
			CcrtoJAXBContextUtils.jaxbContext = jaxbContext;
		}
	}

	static JAXBContext buildContext() {
		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, CcrtoPropertyMetadataSource.getInstance());
		try {
			// TODO dokończyć listę klas
			return JAXBContext.newInstance(
					new Class[] { org.ccrto.openapi.core.Context.class, CcrtoPropertyCase.class, SaveRequest.class },
					properties);
		} catch (Exception e) {
			logger.error("Build JAXBContext error!", e);
		}
		return null;

	}

}
