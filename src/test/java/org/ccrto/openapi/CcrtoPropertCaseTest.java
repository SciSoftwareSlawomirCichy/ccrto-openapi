package org.ccrto.openapi;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.ccrto.openapi.caseheader.CaseHeader;
import org.ccrto.openapi.test.utils.XMLUtils;
import org.ccrto.openapi.values.CcrtoPropertyBoolean;
import org.ccrto.openapi.values.CcrtoPropertyCase;
import org.ccrto.openapi.values.CcrtoPropertyDate;
import org.ccrto.openapi.values.CcrtoPropertyList;
import org.ccrto.openapi.values.CcrtoPropertyMap;
import org.ccrto.openapi.values.CcrtoPropertyNameValuePair;
import org.ccrto.openapi.values.CcrtoPropertyNumber;
import org.ccrto.openapi.values.CcrtoPropertyString;
import org.xml.sax.SAXException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class CcrtoPropertCaseTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public CcrtoPropertCaseTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(CcrtoPropertCaseTest.class);
	}

	public void testCase() throws FileNotFoundException, JAXBException, ParserConfigurationException, SAXException,
			IOException, TransformerException {
		CcrtoPropertyCase propertyCase = new CcrtoPropertyCase();
		CaseHeader caseHeader = new CaseHeader();
		caseHeader.setCreateDate(CcrtoPropertyDate.getInstance("test"));
		propertyCase.setCaseHeader(caseHeader);

		CcrtoPropertyString param1 = CcrtoPropertyString.getInstance("test");
		param1.getOtherAttributes().put(QName.valueOf("position"), "1");
		param1.setPropertyName("param1");
		propertyCase.add(param1);

		CcrtoPropertyNameValuePair nvp = new CcrtoPropertyNameValuePair();
		nvp.getOtherAttributes().put(QName.valueOf("position"), "2");
		nvp.setPropertyName("param2");
		nvp.setName("name");
		nvp.setValue("value");
		propertyCase.add(nvp);

		CcrtoPropertyCase propertySubCase = new CcrtoPropertyCase();
		propertySubCase.setType("TestCcrtoPropertyCase");
		propertySubCase.setPropertyName("param3");
		propertySubCase.getOtherAttributes().put(QName.valueOf("position"), "3");
		propertySubCase.setCaseHeader(caseHeader);
		propertyCase.add(propertySubCase);

		propertySubCase = new CcrtoPropertyCase();
		propertySubCase.setType("TestCcrtoPropertyCase");
		propertySubCase.setCaseHeader(caseHeader);

		CcrtoPropertyList propertyList = new CcrtoPropertyList();
		propertyList.setType("TestCcrtoPropertyCase");
		propertyList.setPropertyName("param4");
		propertyList.getOtherAttributes().put(QName.valueOf("position"), "4");
		propertyList.add(propertySubCase);
		propertyCase.add(propertyList);

		propertyList = new CcrtoPropertyList();
		propertyList.setPropertyName("param5");
		propertyList.getOtherAttributes().put(QName.valueOf("position"), "5");
		propertyList.add(CcrtoPropertyString.getInstance("wartość 1"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 2"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 3"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 4"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 5"));
		propertyCase.add(propertyList);

		CcrtoPropertyMap propertyMap = new CcrtoPropertyMap();
		propertyMap.setPropertyName("param6");
		propertyMap.getOtherAttributes().put(QName.valueOf("position"), "6");
		propertyMap.put("key1", "value1");
		propertyMap.put("key2", "value2");
		propertyMap.put("key3", "value3");
		propertyMap.put("key4", "value4");
		propertyCase.add(propertyMap);

		propertyList = new CcrtoPropertyList();
		propertyList.setPropertyName("param7");
		propertyList.getOtherAttributes().put(QName.valueOf("position"), "7");
		propertyList.add(CcrtoPropertyString.getInstance("wartość 1"));
		propertyList.add(CcrtoPropertyBoolean.getInstance(true));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 3"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 4"));
		propertyList.add(CcrtoPropertyString.getInstance("wartość 5"));
		propertyCase.add(propertyList);

		propertyList = new CcrtoPropertyList();
		propertyList.setPropertyName("param8");
		propertyList.getOtherAttributes().put(QName.valueOf("position"), "8");
		propertyList.add(CcrtoPropertyNumber.getInstance(Integer.valueOf(1)));
		propertyList.add(CcrtoPropertyNumber.getInstance(Integer.valueOf(2)));
		propertyCase.add(propertyList);
		
		propertyList = new CcrtoPropertyList();
		propertyList.setPropertyName("param9");
		propertyList.getOtherAttributes().put(QName.valueOf("position"), "9");
		propertyList.add(CcrtoPropertyNumber.getInstance(Double.valueOf(1)));
		propertyList.add(CcrtoPropertyNumber.getInstance(Double.valueOf(2)));
		propertyCase.add(propertyList);
		
		ResponseSample s = new ResponseSample();
		s.data = propertyCase;

		String fileName = "case_sample.v1.xml";
		Class<?> clazz = CcrtoPropertyCase.class;
		XMLUtils.transformation2XML(clazz, propertyCase, fileName);
		propertyCase = (CcrtoPropertyCase) XMLUtils.transformation2Object(clazz, fileName);
		XMLUtils.transformation2XML(clazz, propertyCase, /* fileName */ null);
		assertTrue(true);

	}

}
