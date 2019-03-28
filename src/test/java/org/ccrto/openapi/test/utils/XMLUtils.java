package org.ccrto.openapi.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

public class XMLUtils {

	private XMLUtils() {
	}

	public static void transformation2XML(Class<?> clazz, Object object, String fileName) throws FileNotFoundException,
			JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {

		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		if (fileName != null) {
			File file = new File(fileName);
			jaxbMarshaller.marshal(object, file);
		}
		jaxbMarshaller.marshal(object, System.out);

	}

	public static Object transformation2Object(Class<?> clazz, String fileName) throws FileNotFoundException,
			JAXBException, ParserConfigurationException, SAXException, IOException, TransformerException {

		File file = new File(fileName);
		JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
		Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();
		try (FileInputStream in = new FileInputStream(file)) {
			return jaxbMarshaller.unmarshal(in);
		}
	}

}
