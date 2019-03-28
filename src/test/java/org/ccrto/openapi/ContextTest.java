package org.ccrto.openapi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.ccrto.openapi.context.CacheUsage;
import org.ccrto.openapi.context.Context;
import org.ccrto.openapi.context.ContextFormats;
import org.ccrto.openapi.context.ContextHelper;
import org.ccrto.openapi.context.UserRoleContext;
import org.ccrto.openapi.system.SystemProperties;
import org.ccrto.openapi.test.utils.XMLUtils;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for RCTO.
 */
public class ContextTest extends TestCase {

	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 */
	public ContextTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(ContextTest.class);
	}

	public void testContext() throws FileNotFoundException, JAXBException, ParserConfigurationException, SAXException,
			IOException, TransformerException {

		ContextFormats formats = new ContextFormats();
		formats.setCurrencyCode("PLN");
		formats.setDateShortFormat("yyyy-MM-dd");

		UserRoleContext role;
		Set<UserRoleContext> userRoles;

		ObjectMapper objectMapper = new ObjectMapper();
		SystemProperties systemProperties = SystemProperties.getSystemProperties();
		Context context = systemProperties.createDefaultContext();
		context.setFormats(formats);
		/* Sample mercury DB Context */
		role = createUserRole(systemProperties, "SciAdmin");
		context.setCurrentRole(role);
		userRoles = new HashSet<>();
		role = createUserRole(systemProperties, "mrc-admin");
		userRoles.add(role);
		role = createUserRole(systemProperties, "mrc-user");
		userRoles.add(role);
		role = createUserRole(systemProperties, "mrc-groupadmin");
		userRoles.add(role);
		context.setUserRoles(userRoles);
		/**
		 * Informacja czy zwracane dane w warstwie biznesowej mają zawierać nagłówek
		 * sprawy - wykorzystywane np, podczas pobierania słowników składowanych jako
		 * sprawy. Wartość domyślna {@code false}
		 */
		context.addRequestPropertyValue("ignoreCaseHeaderInResponse", false);
		/**
		 * Informacja o wykorzystania pamięci podręcznej do przechowywania całego wyniku
		 * żądania HTTP/HTTPS. Można wykorzystać np. podczas częstych pobrań danych
		 * słownikowych. Możliwe wartości o nazwy elementów obiektu enum
		 * {@link CacheUsage}
		 */
		context.addRequestPropertyValue("httpResponseCacheUsage", CacheUsage.NONE.name());

		System.out.println(objectMapper.valueToTree(context));
		XMLUtils.transformation2XML(Context.class, context, "context_sample.v1.xml");
		assertTrue(true);
	}

	private UserRoleContext createUserRole(SystemProperties systemProperties, String roleId) {
		return ContextHelper.createRoleReference(roleId, systemProperties.getRoleHref(roleId), /* role */ null);
	}

	/**
	 * Pobranie strumienia pliku.
	 * 
	 * @param clazz
	 *            klas wywołująca
	 * @param fileName
	 *            nazwa pliku XML
	 * @return strumień (pamiętaj go później zamknąć!!!)
	 * @throws FileNotFoundException
	 */
	public static InputStream getInputStream(Class<?> clazz, String fileName) throws FileNotFoundException {
		URL resource = clazz.getResource(fileName);
		if (resource == null) {
			FileInputStream fis = new FileInputStream(fileName);
			return fis;
		} else {
			return clazz.getResourceAsStream(fileName);
		}
	}
}
