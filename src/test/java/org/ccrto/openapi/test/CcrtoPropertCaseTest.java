package org.ccrto.openapi.test;

import java.io.FileNotFoundException;
import java.io.IOException;

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
import org.ccrto.openapi.core.CcrtoPropertyStatus;
import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.core.Context;
import org.ccrto.openapi.core.exceptions.CcrtoException;
import org.ccrto.openapi.core.system.SystemProperties;
import org.ccrto.openapi.messaging.SaveRequest;
import org.ccrto.openapi.test.utils.SystemOutLogger;
import org.ccrto.openapi.test.utils.XMLUtils;
import org.xml.sax.SAXException;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * CcrtoPropertCaseTest
 * https://stackoverflow.com/questions/8980204/configuring-cxf-with-spring-to-use-moxy-for-xml-marshalling-unmarshalling
 * https://stackoverflow.com/questions/12684103/xmladapter-not-being-used-in-cxf
 * https://www.eclipse.org/eclipselink/documentation/2.4/solutions/jpatoxml006.htm
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
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
			IOException, TransformerException, CcrtoException {
		final String METHOD = "testCase";

		String fileName;
		SystemProperties systemProperties = SystemProperties.getSystemProperties();
		Context context = systemProperties.createDefaultContext();

		CcrtoPropertyCase propertyCase = new CcrtoPropertyCase();
		propertyCase.setStatus(CcrtoPropertyStatus.FRAGMENT);
		CaseHeader caseHeader = new CaseHeader();
		caseHeader.setCreateDate(CcrtoPropertyDate.getInstance("test"));
		propertyCase.setCaseHeader(caseHeader);

		CcrtoPropertyString param1 = CcrtoPropertyString.getInstance("test");
		param1.setPropertyName("param1");
		param1.getOtherAttributes().put(QName.valueOf("position"), "1.1");
		propertyCase.add(param1);

		param1 = CcrtoPropertyString.getInstance("test_2");
		param1.setPropertyName("param1");
		param1.getOtherAttributes().put(QName.valueOf("position"), "1.2");
		propertyCase.add(param1);

		CcrtoPropertyNameValuePair nvp = new CcrtoPropertyNameValuePair();
		nvp.setPropertyName("param2");
		nvp.getOtherAttributes().put(QName.valueOf("position"), "2");
		nvp.setName("name");
		nvp.setValue("value");
		propertyCase.add(nvp);

		CcrtoPropertyCase propertySubCase = new CcrtoPropertyCase();
		propertySubCase.setStatus(CcrtoPropertyStatus.FRAGMENT);
		propertySubCase.setType("TestCcrtoPropertyCase");
		propertySubCase.setPropertyName("param3");
		propertySubCase.getOtherAttributes().put(QName.valueOf("position"), "3");
		propertySubCase.setCaseHeader(caseHeader);
		propertyCase.add(propertySubCase);

		propertySubCase = new CcrtoPropertyCase();
		propertySubCase.setStatus(CcrtoPropertyStatus.ALL);
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
		CcrtoPropertyString newParam = CcrtoPropertyString.getInstance("newParam");
		newParam.setPropertyName("newParam");
		newParam.getOtherAttributes().put(QName.valueOf("position"), "11");
		propertyCase.add(newParam);
		SystemOutLogger.log(METHOD, "---->CaseProperties: %s", propertyCase.getCaseProperties().size());

		SaveRequest request = new SaveRequest();
		request.setContext(context);
		request.addData(propertyCase);
		fileName = "case_sample.v1.xml";
		Class<?> clazz = SaveRequest.class;
		XMLUtils.transformation2XML(clazz, request, fileName);

		fileName = "case_sample.v3.xml";
		request = (SaveRequest) XMLUtils.transformation2Object(context, clazz, fileName);
		propertyCase = request.getData().get(0);

		SystemOutLogger.log(METHOD, "---->AnyProperties: %s", propertyCase.getAnyProperties().size());
		SystemOutLogger.log(METHOD, "---->CaseProperties: %s", propertyCase.getCaseProperties().size());

		clazz = CcrtoPropertyCase.class;
		XMLUtils.transformation2XML(clazz, propertyCase, /* fileName */ null);
		assertTrue(true);

	}

}
