package org.ccrto.openapi.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.ccrto.openapi.core.CaseHeader;
import org.ccrto.openapi.core.CcrtoPropertyBoolean;
import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.core.CcrtoPropertyDate;
import org.ccrto.openapi.core.CcrtoPropertyList;
import org.ccrto.openapi.core.CcrtoPropertyMap;
import org.ccrto.openapi.core.CcrtoPropertyNameValuePair;
import org.ccrto.openapi.core.CcrtoPropertyNumber;
import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.messaging.ResponseSample;
import org.ccrto.openapi.test.utils.XMLUtils;
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

		String fileName;

		CcrtoPropertyCase propertyCase = new CcrtoPropertyCase();
		CaseHeader caseHeader = new CaseHeader();
		caseHeader.setCreateDate(CcrtoPropertyDate.getInstance("test"));
		propertyCase.setCaseHeader(caseHeader);

		CcrtoPropertyString param1 = CcrtoPropertyString.getInstance("test");
		param1.setPropertyName("param1");
		param1.getOtherAttributes().put(QName.valueOf("position"), "1");
		propertyCase.add(param1);

		CcrtoPropertyNameValuePair nvp = new CcrtoPropertyNameValuePair();
		nvp.setPropertyName("param2");
		nvp.getOtherAttributes().put(QName.valueOf("position"), "2");
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

		CcrtoPropertyString text = CcrtoPropertyString.getInstance(
				"test long text, test long text, test long text, test long text, test long text, test long "
						+ "text, test long text, test long text, test long text, test long text, test long text, test long text, "
						+ "text, test long text, <a>text</a>, test long text, test long text, test long text, test long text, "
						+ "text, test long text, test&nbps;long text, test long text, test long text, test long text, test long text, "
						+ "text, test long text, test long text, test long text, test long text, test long text, test long text, ");
		text.setPropertyName("param10");
		text.getOtherAttributes().put(QName.valueOf("position"), "10");
		propertyCase.add(text);

		ResponseSample response = new ResponseSample();
		response.data = propertyCase;

		fileName = "case_sample.v1.xml";
		Class<?> clazz = ResponseSample.class;
		XMLUtils.transformation2XML(clazz, response, fileName);

		//fileName = "case_sample.v2.xml";
		response = (ResponseSample) XMLUtils.transformation2Object(clazz, fileName);
		clazz = CcrtoPropertyCase.class;
		propertyCase = response.data;
		XMLUtils.transformation2XML(clazz, propertyCase, /* fileName */ null);

		fileName = "case_sample.v3.xml";
		propertyCase = (CcrtoPropertyCase) XMLUtils.transformation2Object(clazz, fileName);

		
		System.out.println(String.format("---->properties: %s", propertyCase.getProperties().size()));
//		List<Object> any = ((CcrtoPropertyString) propertyCase.getCaseProperties().get(1)).getAny();
//		System.out.println(String.format("---->any: %s", any.size()));
//		System.out.println(
//				String.format("---->header: %s", propertyCase.getCaseHeader().getCreateDate().getPropertyValue()));
//		System.out.println(String.format("---->param[10]: %s", propertyCase.getCaseProperties().get(9)));

		XMLUtils.transformation2XML(clazz, propertyCase, /* fileName */ null);
		assertTrue(true);

	}

}
