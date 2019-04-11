package org.ccrto.openapi.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.CaseHeader;
import org.ccrto.openapi.core.CcrtoProperty;
import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.core.CcrtoPropertyType;
import org.ccrto.openapi.core.Context;
import org.ccrto.openapi.core.ContextHelper;
import org.ccrto.openapi.core.DecodeMethod;
import org.ccrto.openapi.core.ObjectFactory;
import org.ccrto.openapi.core.exceptions.CcrtoException;
import org.ccrto.openapi.core.utils.CcrtoJAXBContextUtils;
import org.ccrto.openapi.core.utils.CcrtoPropertyMetadataSource;
import org.ccrto.openapi.core.utils.CcrtoPropertyTypeUtils;
import org.ccrto.openapi.core.utils.ParserResult;
import org.ccrto.openapi.core.utils.CcrtoJAXBUnmarshalHelper;
import org.ccrto.openapi.messaging.SaveRequest;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLUtils {

	private static final Logger logger = LoggerFactory.getLogger(CcrtoJAXBUnmarshalHelper.class);

	private XMLUtils() {
	}

	public static void transformation2XML(Class<?> clazz, Object object, String fileName) throws FileNotFoundException,
			JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, CcrtoPropertyMetadataSource.getInstance());
		JAXBContext jaxbContext = JAXBContext.newInstance(
				new Class[] { org.ccrto.openapi.core.Context.class, CcrtoPropertyCase.class, SaveRequest.class },
				properties);

		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		if (fileName != null) {
			File file = new File(fileName);
			jaxbMarshaller.marshal(object, file);
		}
		Long startTime = Calendar.getInstance().getTimeInMillis();
		jaxbMarshaller.marshal(object, System.out);
		Long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("--->transformation2XML: " + (endTime - startTime));
	}

	public static Object transformation2Object(Context context, Class<?> clazz, String fileName)
			throws FileNotFoundException, JAXBException, ParserConfigurationException, SAXException, IOException,
			TransformerException {

		org.eclipse.persistence.jaxb.JAXBContextFactory x;

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put(JAXBContextProperties.OXM_METADATA_SOURCE, CcrtoPropertyMetadataSource.getInstance());
		JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { org.ccrto.openapi.core.Context.class,
				CcrtoPropertyCase.class, SaveRequest.class, CaseHeader.class }, properties);
		File file = new File(fileName);
		Object result = null;
		Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
		Long startTime = Calendar.getInstance().getTimeInMillis();
		try (FileInputStream in = new FileInputStream(file)) {
			result = jaxbMarshaller.unmarshal(in);
		}
		Long endTime = Calendar.getInstance().getTimeInMillis();
		System.out.println("--->transformation2Object: " + (endTime - startTime));
		return result;
	}


}
