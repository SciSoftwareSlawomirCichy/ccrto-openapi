package org.ccrto.openapi.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.core.CaseHeader;
import org.ccrto.openapi.core.CaseType;
import org.ccrto.openapi.core.CcrtoProperty;
import org.ccrto.openapi.core.CcrtoPropertyCase;
import org.ccrto.openapi.core.CcrtoPropertyCurrency;
import org.ccrto.openapi.core.CcrtoPropertyDate;
import org.ccrto.openapi.core.CcrtoPropertyEntry;
import org.ccrto.openapi.core.CcrtoPropertyInteger;
import org.ccrto.openapi.core.CcrtoPropertyList;
import org.ccrto.openapi.core.CcrtoPropertyLob;
import org.ccrto.openapi.core.CcrtoPropertyMap;
import org.ccrto.openapi.core.CcrtoPropertyNameValuePair;
import org.ccrto.openapi.core.CcrtoPropertyNumber;
import org.ccrto.openapi.core.CcrtoPropertyString;
import org.ccrto.openapi.core.CcrtoPropertyType;
import org.ccrto.openapi.core.CcrtoPropertyURL;
import org.ccrto.openapi.core.CcrtoUrlClass;
import org.ccrto.openapi.core.Context;
import org.ccrto.openapi.core.ContextHelper;
import org.ccrto.openapi.core.DecodeMethod;
import org.ccrto.openapi.core.KnownLobMetadata;
import org.ccrto.openapi.core.ObjectFactory;
import org.ccrto.openapi.core.exceptions.CcrtoCreateSimplePropertyException;
import org.ccrto.openapi.core.exceptions.CcrtoException;
import org.ccrto.openapi.core.exceptions.CcrtoWrongDateFormatException;
import org.ccrto.openapi.core.internal.IValueWithEncodedFlag;
import org.ccrto.openapi.core.internal.IValueWithProperties;
import org.ccrto.openapi.core.system.SystemProperties;
import org.ccrto.openapi.core.utils.CcrtoJAXBContextChangeRequest.Change;
import org.ccrto.openapi.core.utils.NumberValueAnalyser.AnalysisResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * CcrtoJAXBUnmarshalHelper - klasa narzędziowa/pomocnicza wykorzystywana
 * podczas parsowania dokumentu XML.
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class CcrtoJAXBUnmarshalHelper {

	private static final Logger logger = LoggerFactory.getLogger(CcrtoJAXBUnmarshalHelper.class);

	/**
	 * <p>
	 * Feature for secure processing.
	 * </p>
	 * 
	 * <ul>
	 * <li><code>true</code> instructs the implementation to process XML securely.
	 * This may set limits on XML constructs to avoid conditions such as denial of
	 * service attacks.</li>
	 * <li><code>false</code> instructs the implementation to process XML according
	 * to the letter of the XML specifications ignoring security issues such as
	 * limits on XML constructs to avoid conditions such as denial of service
	 * attacks.</li>
	 * </ul>
	 */
	public static final String FEATURE_SECURE_PROCESSING = "http://javax.xml.XMLConstants/feature/secure-processing";

	/** Domyślna wartość atrybutu dekodowania dla pól nie będących datą i LOB */
	public static final String DEFAULT_IS_ENCODED = Boolean.toString(true);
	/** Domyślna wartość atrybutu dekodowania dla pól będących datą lub LOB */
	public static final String DEFAULT_DATE_IS_ENCODED = Boolean.toString(false);
	public static final String EMPTY_ID = "";

	public static final String MAP_CLASS_NAME = "Map";
	public static final String INDEXED_MAP_CLASS_NAME = "IndexedMap";
	public static final String ANY_TYPE_CLASS_NAME = "ANY";
	public static final String ANY_TYPE_ARRAY_CLASS_NAME = "ANY" + CcrtoPropertyTypeUtils.ARRAY_SUFFIX;

	private CcrtoJAXBUnmarshalHelper() {
	}

	/**
	 * Właściwa metoda do czytania/tworzenia obiektów TW z elementów XML (wywoływana
	 * w poszczególnych iteracjach)
	 * 
	 * @see #readObject(Node)
	 * 
	 * @param context
	 *            kontekst wykonania operacji ładowania danych zawierający nazwę i
	 *            komentarz użytkownika, oraz parametry języka i strefy czasowej
	 * @param node
	 *            element XML
	 * @param iteration
	 *            numer iteracji
	 * @return
	 * @throws CcrtoException
	 */
	public static Object readObject(Context context, Node node, int iteration) throws CcrtoException {

		Locale locale = ContextHelper.getUserLocale(context);

		DecodeMethod decodeRequestMethod = (context.getDecodeRequest() != null ? context.getDecodeRequest()
				: DecodeMethod.DATE_AND_LOB);

		switch (decodeRequestMethod) {
		case NOTHING:
			/* nie zidentyfikuję potencjalnego LOB'a */
		case DATE_ONLY:
			/* nie zidentyfikuję potencjalnego LOB'a */
		case ALL:
			/*
			 * nie odczytam prawidłowo wartości podstawowych związanych ze słownikami (para
			 * nazwa wartość)
			 */
		case ALL_WITHOUT_LOB:
			/*
			 * nie odczytam prawidłowo wartości podstawowych związanych ze słownikami (para
			 * nazwa wartość)
			 */
			throw new IllegalAccessError("Wrong decode method in context."
					+ "For unmarshal action should be used only values: 'LOB_ONLY' and 'DATE_AND_LOB' as decodeRequest parameter.");
		default:
		}
		ParserResult parserResult = parse(context, locale, (Element) node);
		return parserResult.getCcrtoObject();
	}

	/**
	 * Parsowanie elementu XML do postaci obiektu MRC
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param locale
	 *            locale użytkownika
	 * @param element
	 *            analizowany element XML
	 * @return tworzony obiekt rezultatu zawierający utworzony obiekt MRC
	 * @throws CcrtoException
	 * 
	 */
	private static ParserResult parse(Context context, Locale locale, Element element) throws CcrtoException {
		Long startDate = Calendar.getInstance().getTimeInMillis();
		ParserResult result = new ParserResult(element);
		NodeList nodeList = element.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				String tagName = result.getTagName();
				if (tagName.equals(ObjectFactory.TAG_HEADER)) {
					try {
						Unmarshaller jaxbMarshaller = CcrtoJAXBContextUtils.getJaxbContext().createUnmarshaller();
						JAXBElement<CaseHeader> c = jaxbMarshaller.unmarshal(element, CaseHeader.class);
						if (c.getValue() != null) {
							result.setCcrtoObject(c.getValue());
						} else {
							result.setCcrtoObject(new CaseHeader());
						}
					} catch (JAXBException e) {
						throw new CcrtoException(e);
					}
					return result;
				} else {
					ParserResult child = null;
					Element childElement = (Element) node;
					child = parse(context, locale, childElement);
					if (child.isLob()) {
						result.setHasLobElements(true);
					}
					result.addChild(child);
				}

			} else if (node.getNodeType() == Node.CDATA_SECTION_NODE || node.getNodeType() == Node.TEXT_NODE) {
				String textValue = node.getNodeValue().trim();
				result.setText(textValue);
			}
		}
		return postEndingTagAnalysis(context, locale, element, startDate, result);

	}

	/**
	 * Analiza zawartości tag'a XML. Przekształcenie treści XML do obiektu MRC.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param locale
	 *            locale użytkownika
	 * @param element
	 *            element XML
	 * @param startDate
	 *            czas rozpoczęcia akcji parsowania
	 * @param result
	 *            tworzony obiekt rezultatu (zostanie uzupełniony o obiekt MRC
	 * @return tworzony obiekt rezultatu
	 * @throws CcrtoException
	 */
	private static ParserResult postEndingTagAnalysis(Context context, Locale locale, Element element, Long startDate,
			ParserResult result) throws CcrtoException {
		boolean attributeWithTypeIsNotSet = false;

		/* Obowiązkowo ustawiam locale wartości - KONIEC */

		String typeName = result.getType();
		if (StringUtils.isBlank(typeName)) {
			typeName = CcrtoPropertyType.STRING.getName();
			attributeWithTypeIsNotSet = true;
		}
		/*
		 * na podstawie dostępnych danych sprawdzam czy jest to macierz - macierz na
		 * nazwę typu zakończoną sufiksem '[]'
		 */
		boolean isArray = typeName.endsWith(CcrtoPropertyTypeUtils.ARRAY_SUFFIX);
		if (isArray) {
			/*
			 * dobra, udało się zidentyfikować, że mamy do czynienia z macierzą, obcinam
			 * nazwę typu
			 */
			typeName = typeName.substring(0, typeName.length() - 2);
		}

		boolean isLob = result.isLob();
		if (!isLob && result.childrenSize() == 0) {
			/** mamy do czynienia z typem prostym prostym lub wartością pustą - START */
			CcrtoProperty property;
			if (isArray) {
				/* na podstawie przesłanych danych śmiało mogę powiedzieć, że mam MrcList */
				property = CcrtoJAXBUnmarshalHelper.createEmptyCcrtoPropertyList(result, typeName);
			} else if (result.getHeader() != null) {
				/* tag nie ma dzieci, ale ma ustawiony tag z metadanymi */
				property = CcrtoJAXBUnmarshalHelper.createEmptyCcrtoPropertyObject(result, typeName);
			} else {
				/* to nie jest zidentyfikowana macierz */
				/* ustawiam wartość dla parametru prostego */
				boolean checkDateFormat = true;
				if (attributeWithTypeIsNotSet) {
					/* próba identyfikacji typu na podstawie wartości */
					CcrtoJAXBUnmarshalHelper.setValueForUnknownType(context, locale, result);
					/* jak przeszedłem ten etap, to mam zidentyfikowany format daty */
					checkDateFormat = false;
				}
				property = CcrtoJAXBUnmarshalHelper.createSimpleProperty(context, result, checkDateFormat);
			}
			CcrtoJAXBUnmarshalHelper.setParamAttributes(property, result);
			result.setCcrtoObject(property);
			logTimeOfExecution(startDate, result);
			return result;
			/** mamy do czynienia z typem prostym prostym lub wartością pustą - KONIEC */
		}

		/* mamy jakieś tagi wewnętrzne, kontynuujemy analizę... */
		/* 1. Identyfikacja NameValuePair */
		if (!isArray && !isLob && !result.hasLobElements()) {
			CcrtoJAXBUnmarshalHelper.identifyCcrtoPropertyNameValuePair(context, result);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
			isLob = result.isLob();
		}
		/* 2. Identyfikacja Entry */
		if (!isArray && !isLob && !result.hasLobElements()) {
			CcrtoJAXBUnmarshalHelper.identifyCcrtoPropertyEntry(context, result);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
			isLob = result.isLob();
		}
		/* 3. Identyfikacja sprawy */
		if (!isArray && !isLob) {

			CcrtoJAXBUnmarshalHelper.identifyCcrtoPropertyCase(context, result);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
			isLob = result.isLob();
		}
		/* 4.1 Identyfikacja listy (ma elementy 'item') */
		if (!isLob) {
			CcrtoJAXBUnmarshalHelper.identifyCcrtoPropertyList(context, result, ObjectFactory.TAG_ARRAY_ITEM, typeName);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
			isLob = result.isLob();
		}
		/* 4.2 Identyfikacja mapy (ma elementy 'entry') */
		if (!isLob) {
			CcrtoJAXBUnmarshalHelper.identifyCcrtoPropertyList(context, result, ObjectFactory.TAG_MAP_ENTRY, typeName);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
			isLob = result.isLob();
		}
		/* 4.3 Identyfikacja URL'a */
		if (!isLob) {
			CcrtoJAXBUnmarshalHelper.identifyUrl(context, result);
			if (result.getCcrtoObject() != null) {
				logTimeOfExecution(startDate, result);
				return result;
			}
		}
		/*
		 * 5. końcowa definicja obiektu LOB
		 */
		result.setLob(true);
		CcrtoPropertyLob lobProperty = new CcrtoPropertyLob();
		NodeList lobNodes = element.getChildNodes();
		for (int n = 0; n < lobNodes.getLength(); n++) {
			Node lobNode = lobNodes.item(n);
			if (lobNode instanceof Element) {
				Element lobElement = (Element) lobNode;
				lobElement.setAttribute("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
				lobProperty.addValue(lobElement);
			}
		}
		setParamAttributes(lobProperty, result);
		lobProperty.setType(typeName + (isArray ? CcrtoPropertyTypeUtils.ARRAY_SUFFIX : ""));
		lobProperty.setMetadataInfo(KnownLobMetadata.XML);
		lobProperty.setMimeType(KnownLobMetadata.XML.getDefaultContentType());
		result.setCcrtoObject(lobProperty);
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("--->postEndingTagAnalysis: element '%s' is LOB!", result.getTagName()));
		}
		logTimeOfExecution(startDate, result);
		return result;
	}

	private static void logTimeOfExecution(Long startDate, ParserResult result) {
		if (logger.isTraceEnabled()) {
			Long endDate = Calendar.getInstance().getTimeInMillis();
			if (endDate - startDate != 0)
				logger.trace(String.format("--->parse: element:%s, timeOfExecution: %s[ms]", result.getTagName(),
						endDate - startDate));
		}
	}

	/**
	 * Gets the first child element of a node.
	 * 
	 * @param node
	 *            the node to get the child from
	 * @return the first element child of {@code node} or {@code null} if none
	 * @throws NullPointerException
	 *             if {@code node} is {@code null}
	 */
	public static Element getFirstChildElement(Node node) {
		node = node.getFirstChild();
		while (node != null && node.getNodeType() != Node.ELEMENT_NODE) {
			node = node.getNextSibling();
		}
		return (Element) node;
	}

	/**
	 * Czytanie wartości tekstowej elementu XML
	 * 
	 * @param node
	 *            element XML
	 * @return wartość zawarta w elemencie XML
	 */
	public static String readTextValue(Node node) {
		NodeList props = node.getChildNodes();
		String value = null;
		for (int i = 0; i < props.getLength(); i++) {
			Node prop = props.item(i);
			if (prop.getNodeType() == Node.CDATA_SECTION_NODE || prop.getNodeType() == Node.TEXT_NODE) {
				value = prop.getNodeValue();
				break;
			}
		}
		return value;
	}

	/**
	 * Czy element XML o podanej nazwie reprezentuje obiekt sprawy?
	 * 
	 * @param propertyName
	 *            nazwa parametru
	 * @param node
	 *            node
	 * @return {@code true} jeżeli zostanie znaleziony tag nagłówka sprawy.
	 */
	public static boolean isCcrtoPropertyCase(String propertyName, Node node) {
		if (propertyName.equals(ObjectFactory.TAG_HEADER)) {
			return true;
		}
		NodeList props = node.getChildNodes();
		for (int i = 0; i < props.getLength(); i++) {
			Node prop = props.item(i);
			if (prop.getNodeType() == Node.ELEMENT_NODE) {
				Element nElement = (Element) prop;
				String name = nElement.getNodeName();
				if (StringUtils.isNotBlank(name) && name.equals(ObjectFactory.TAG_HEADER)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Czy element XML reprezentuje obiekt sprawy?
	 * 
	 * @param node
	 *            node
	 * @return {@code true} jeżeli zostanie znaleziony tag nagłówka sprawy.
	 */
	public static boolean isCcrtoPropertyCase(Node node) {
		NodeList props = node.getChildNodes();
		for (int i = 0; i < props.getLength(); i++) {
			Node prop = props.item(i);
			if (prop.getNodeType() == Node.ELEMENT_NODE) {
				Element nElement = (Element) prop;
				String name = nElement.getNodeName();
				if (StringUtils.isNotBlank(name) && name.equals(ObjectFactory.TAG_HEADER)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Utworzenie instancji prostego parametru wraz z analizą treści. Uwaga! metoda
	 * ustawia tylko wartość parametru, pozostałe pola parametru są ustawiane w
	 * innej metodzie, która powinna być wywołana po ustawieniu wartości. Jeżeli
	 * chcemy przekazać jakieś informacje o kodowaniu wartości (pole isEncoding), to
	 * zmieniony zostanie parametr wejściowy z danymi parsowanego XML'a.
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param parserResult
	 *            poddawany analizie wynik parsowania
	 * @param checkDateFormat
	 *            flaga nakazująca sprawdzenie formatu daty dla zidentyfikowanej
	 *            wartości typu daty.
	 * @return instancja prostego parametru
	 * @throws CcrtoException
	 *             może się pojawić gdy pojawi się problem parsowania daty
	 */
	public static CcrtoProperty createSimpleProperty(final Context context, final ParserResult parserResult,
			boolean checkDateFormat) throws CcrtoException {

		CcrtoProperty property = null;

		DecodeMethod decodeRequestMethod = (context.getDecodeRequest() != null ? context.getDecodeRequest()
				: DecodeMethod.DATE_AND_LOB);

		String typeName = parserResult.getType();
		String value = parserResult.getText();

		/* ustawianie parametru dekodowania wartości - START */
		String isEncodedStr = parserResult.getIsEncoded();
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("-->createSimpleProperty[BEFORE]: typeName: %s, value: %s, isEncodedStr: %s",
					typeName, value, isEncodedStr));
		}
		if (StringUtils.isBlank(isEncodedStr)) {
			if (decodeRequestMethod.equals(DecodeMethod.NOTHING) || decodeRequestMethod.equals(DecodeMethod.LOB_ONLY)) {
				isEncodedStr = DEFAULT_IS_ENCODED;
			} else {
				isEncodedStr = DEFAULT_DATE_IS_ENCODED;
			}
		}
		boolean isEncoded = Boolean.parseBoolean(isEncodedStr);
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("-->createSimpleProperty[AFTER]: typeName: %s, value: %s, isEncodedStr: %s",
					typeName, value, isEncoded));
		}
		/* ustawianie parametru dekodowania wartości - KONIEC */

		try {
			CcrtoPropertyType mrcType = CcrtoPropertyType.getType(typeName);
			if (mrcType.isDate()) {
				CcrtoPropertyDate dVal = new CcrtoPropertyDate();
				if (StringUtils.isNotBlank(value)) {
					if (isEncoded) {
						Long millis = Long.parseLong(value);
						dVal.setPropertyValue(millis.toString());
						dVal.setIsEncoded(isEncoded);
					} else {
						dVal.setPropertyValue(value);
						if (checkDateFormat) {
							checkDateFormat(context, parserResult, value, dVal, mrcType);
						}
					}
				}
				property = dVal;
			} else if (mrcType.isNumber()) {
				if (CcrtoPropertyType.INTEGER.equals(mrcType) || CcrtoPropertyType.LONG.equals(mrcType)) {
					CcrtoPropertyInteger iVal = new CcrtoPropertyInteger();
					iVal.setPropertyValue(value);
					property = iVal;
				} else if (CcrtoPropertyType.CURRENCY.equals(mrcType)) {
					CcrtoPropertyCurrency cVal = new CcrtoPropertyCurrency();
					cVal.setPropertyValue(value);
					cVal.setIsEncoded(isEncoded);
					property = cVal;
				} else {
					CcrtoPropertyNumber nVal = new CcrtoPropertyNumber();
					nVal.setPropertyValue(value);
					property = nVal;
				}
			} else {
				CcrtoProperty pVal;
				DataUri dataUri = DataUri.checkDataUriValue(value);
				if (CcrtoPropertyTypeUtils.isValueWithLobId(value) || dataUri != null) {
					pVal = createCcrtoPropertyLob(parserResult, value, dataUri);
					/* ustawiam na potrzeby trace */
					Boolean lobIsEncoded = ((CcrtoPropertyLob) pVal).getIsEncoded();
					if (lobIsEncoded != null) {
						parserResult.setIsEncoded(lobIsEncoded.toString());
					}
				} else {
					pVal = new CcrtoPropertyString();
				}
				pVal.setPropertyValue(value);
				property = pVal;
			}
		} catch (CcrtoException e) {
			throw e;
		} catch (Exception e) {
			throw new CcrtoCreateSimplePropertyException(e);
		}
		property.setType(typeName);
		parserResult.setCcrtoObject(property);
		if (logger.isTraceEnabled()) {
			logger.trace(String.format("-->createSimpleProperty[%s] %s, %s, value='%s'",
					property.getClass().getSimpleName(), parserResult.getTagName(), typeName, value));
		}
		return property;
	}

	private static void checkDateFormat(final Context context, final ParserResult parserResult, final String value,
			final CcrtoPropertyDate dVal, final CcrtoPropertyType propertyType) throws CcrtoException {
		String customFormat;
		/* sprawdzam krótki format */
		Locale userLocale = ContextHelper.getUserLocale(context);
		String systemName = ContextHelper.getSystemNameInContext(context);
		if (CcrtoPropertyType.DATE_SHORT.equals(propertyType)) {
			customFormat = ContextHelper.getShortDateFormatPattern(context);
		} else {
			customFormat = ContextHelper.getLongDateFormatPattern(context);
		}
		boolean isDate = DateValueAnalyser.analyse(systemName, customFormat, userLocale, value);
		if (!isDate) {
			throw new CcrtoWrongDateFormatException(
					String.format("Wrong format for value %s. Required format is '%s'", value, customFormat));
		}
	}

	private static CcrtoPropertyLob createCcrtoPropertyLob(final ParserResult parserResult, String value,
			DataUri dataUri) {
		/* jeżeli tu jesteśmy to albo wartość była "${LOB:....}" lub to dane pliku */
		CcrtoPropertyLob lobVal = new CcrtoPropertyLob();
		if (dataUri != null) {
			/* dane pliku */
			lobVal.setMimeType(dataUri.getContentType());
			lobVal.setMetadataInfo(KnownLobMetadata.FILE);
			String data = dataUri.getData();
			lobVal.setIsEncoded(CcrtoPropertyTypeUtils.isValueWithLobId(data));
		} else {
			/* wartość "${LOB:....}" - oznaczam jako wartość zakodowana, gotowa do zapisu */
			lobVal.setIsEncoded(Boolean.TRUE);
		}
		return lobVal;
	}

	/**
	 * Próba identyfikacji typu na podstawie wartości. Aby próba była udana wartość
	 * musi być nie pusta. Jeżeli identyfikacja zakończy się sukcesem zostanie
	 * zmienione pole {@link ParserResult#type} oraz {@link ParserResult#isEncoded}
	 * (jeżeli będzie to wymagane).
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji.
	 * @param locale
	 *            locale użytkownika
	 * @param parserResult
	 *            poddawany analizie wynik parsowania
	 */
	public static void setValueForUnknownType(final Context context, Locale locale, final ParserResult parserResult) {

		String systemName = ContextHelper.getSystemNameInContext(context);
		SystemProperties systemProperties = SystemProperties.getSystemProperties(systemName);
		final int MAX_PARAM_STRING_VALUE_LENGTH = systemProperties.getTextMaxLength();
		String value = parserResult.getText();
		if (StringUtils.isBlank(value) || value.length() > MAX_PARAM_STRING_VALUE_LENGTH) {
			/*
			 * jeżeli wartość jest pusta lub jeżeli wartość jest dłuższa od maksymalnej
			 * długości dla String'a, to nie mam co dalej analizować. Jeżeli tu jestem to
			 * znaczy, że rezultat nie ma ustawionego typu, a zatem ustawię domyślnie na
			 * 'String'. Tak będzie najbezpieczniej. Nie będę ustawiać 'Text' bo z tekstem
			 * są związane różne ograniczenia i zamknę ewentualną późniejszą analizę
			 * ewentualnych JSON'ów.
			 */
			parserResult.setType(CcrtoPropertyType.STRING.getName());
			return;
		}

		String isEncodedStr = parserResult.getIsEncoded();
		/*
		 * Jeżeli tu jestem to znaczy, że rezultat nie ma ustawionego typu, a powinien
		 * jakiś mieć, a zatem ustawię domyślnie na 'String'.
		 */
		String identifiedType = CcrtoPropertyType.STRING.getName();

		/*
		 * sprawdzę czy jeżeli nie został ustawiony atrybut typu, to czy mogę zmienić
		 * domyślne ustawienie STRING na inne - START?...
		 */
		AnalysisResult result = NumberValueAnalyser.analyse(systemName, locale, value);
		if (result != null) {
			identifiedType = result.getType().getName();
			isEncodedStr = result.isEncoded() ? "true" : "false";
		} else {
			/* a może jest datą? */
			String customFormat = null;
			/* sprawdzam krótki format */
			customFormat = ContextHelper.getShortDateFormatPattern(context);
			boolean isDate = DateValueAnalyser.analyse(systemName, customFormat, locale, value);
			if (isDate) {
				/* mam datę w krótkim formacie */
				identifiedType = CcrtoPropertyType.DATE_SHORT.getName();
				isEncodedStr = "false";
			} else {
				/* sprawdzam długi format */
				customFormat = ContextHelper.getLongDateFormatPattern(context);
				isDate = DateValueAnalyser.analyse(systemName, customFormat, locale, value);
				if (isDate) {
					/* mam datę w długim formacie */
					identifiedType = CcrtoPropertyType.DATE_LONG.getName();
					isEncodedStr = "false";
				}
			}
		}
		/*
		 * sprawdzę czy jeżeli nie został ustawiony atrybut typu, to czy mogę zmienić
		 * domyślne ustawienie STRING na inne - KONIEC?...
		 */
		parserResult.setText(value);
		if (StringUtils.isNotBlank(identifiedType)) {
			parserResult.setType(identifiedType);
		}
		parserResult.setIsEncoded(isEncodedStr);
	}

	/**
	 * Ustawienie parametrów pola na podstawie wartości z atrybutów XML
	 * 
	 * @param property
	 *            uniwersalny parametr, któremu zostaną ustawione przeczytane
	 *            wartości atrybutów
	 * @param parserResult
	 *            dane z elementu XML
	 * @return zmieniony obiekt uniwersalnego parametru
	 */
	public static CcrtoProperty setParamAttributes(CcrtoProperty property, ParserResult parserResult) {
		/* pozostałe parametry - START */
		property.setPropertyName(parserResult.getTagName());
		if (parserResult.getPosition() != null) {
			property.setPosition(parserResult.getPosition());
		}
		if (parserResult.getIsEncoded() != null && property instanceof IValueWithEncodedFlag) {
			((IValueWithEncodedFlag) property).setIsEncoded(Boolean.parseBoolean(parserResult.getIsEncoded()));
		}
		if (StringUtils.isNotBlank(parserResult.getIsRequired())) {
			property.getOtherAttributes().put(QName.valueOf(ObjectFactory.ATTR_IS_REQUIRED),
					parserResult.getIsRequired());
		}
		if (StringUtils.isNotBlank(parserResult.getVersion())) {
			property.getOtherAttributes().put(QName.valueOf(ObjectFactory.ATTR_VERSION), parserResult.getVersion());
		}
		if (StringUtils.isNotBlank(parserResult.getLabel())) {
			property.getOtherAttributes().put(QName.valueOf(ObjectFactory.ATTR_LABEL), parserResult.getLabel());
		}
		if (StringUtils.isNotBlank(parserResult.getUpdateable())) {
			property.getOtherAttributes().put(QName.valueOf(ObjectFactory.ATTR_UPDATEABLE),
					parserResult.getUpdateable());
		}
		if (StringUtils.isNotBlank(parserResult.getId())) {
			property.getOtherAttributes().put(QName.valueOf(ObjectFactory.ATTR_ID), parserResult.getId());
		}
		/* pozostałe parametry - KONIEC */
		return property;
	}

	/**
	 * Identyfikacja elementu NameValuePair
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param result
	 *            wynik parsowania elementu XML
	 */
	public static void identifyCcrtoPropertyNameValuePair(final Context context, final ParserResult result) {
		boolean isLob = false;
		/* Identyfikacja NameValuePair - START */
		if (result.childrenSize() == 2 && result.tagsSize() == 2
				&& result.containsTag(CcrtoPropertyNameValuePair.PROPERTY_NAME)
				&& result.containsTag(CcrtoPropertyNameValuePair.PROPERTY_VALUE)) {
			String name = null;
			String value = null;
			IdentifiedValue val;
			ParserResult nameChild = result.getChildByTagName(CcrtoPropertyNameValuePair.PROPERTY_NAME);
			val = getValue(context, nameChild);
			isLob = val.isLob;
			if (!isLob) {
				name = val.value;
				ParserResult valueChild = result.getChildByTagName(CcrtoPropertyNameValuePair.PROPERTY_VALUE);
				val = getValue(context, valueChild);
				isLob = val.isLob;
				value = val.value;
			}
			if (!isLob) {
				String typeName = result.getType();
				if (StringUtils.isBlank(typeName) || typeName.equals(CcrtoPropertyType.STRING.getName())) {
					typeName = CcrtoPropertyType.NVP.getName();
					result.setType(typeName);
				} else if (StringUtils.isNotBlank(typeName) && !typeName.equals(CcrtoPropertyType.NVP.getName())) {
					result.setType(
							typeName + CcrtoPropertyTypeUtils.TYPE_NAME_SEPARATOR + CcrtoPropertyType.NVP.getName());
				}
				CcrtoPropertyNameValuePair property = new CcrtoPropertyNameValuePair();
				property.setType(typeName);
				property.setName(name);
				property.setValue(value);
				result.setIsEncoded("true");
				setParamAttributes(property, result);
				result.setCcrtoObject(property);
			}
		}
		/* Identyfikacja NameValuePair - KONIEC */
		result.setLob(isLob);
	}

	/**
	 * Identyfikacja elementu Entry
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param result
	 *            wynik parsowania elementu XML
	 */
	public static void identifyCcrtoPropertyEntry(final Context context, final ParserResult result) {
		boolean isLob = false;
		/* Identyfikacja Entry - START */
		if (result.childrenSize() == 2 && result.tagsSize() == 2 && result.containsTag(CcrtoPropertyEntry.PROPERTY_KEY)
				&& result.containsTag(CcrtoPropertyEntry.PROPERTY_VALUE)) {
			String key = null;
			String value = null;
			IdentifiedValue val;
			ParserResult nameChild = result.getChildByTagName(CcrtoPropertyEntry.PROPERTY_KEY);
			val = getValue(context, nameChild);
			isLob = val.isLob;
			if (!isLob) {
				key = val.value;
				ParserResult valueChild = result.getChildByTagName(CcrtoPropertyEntry.PROPERTY_VALUE);
				val = getValue(context, valueChild);
				isLob = val.isLob;
				value = val.value;
			}
			if (!isLob) {
				String typeName = result.getType();
				if (StringUtils.isBlank(typeName) || typeName.equals(CcrtoPropertyType.STRING.getName())) {
					typeName = CcrtoPropertyType.ENTRY.getName();
					result.setType(typeName);
				} else if (StringUtils.isNotBlank(typeName) && !typeName.equals(CcrtoPropertyType.ENTRY.getName())) {
					result.setType(
							typeName + CcrtoPropertyTypeUtils.TYPE_NAME_SEPARATOR + CcrtoPropertyType.ENTRY.getName());
				}
				CcrtoPropertyEntry property = new CcrtoPropertyEntry();
				property.setType(typeName);
				property.setKey(key);
				property.setValue(value);
				result.setIsEncoded("true");
				setParamAttributes(property, result);
				result.setCcrtoObject(property);
			}
		}
		/* Identyfikacja Entry - KONIEC */
		result.setLob(isLob);
	}

	/**
	 * Identyfikacja elementu Entry
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param result
	 *            wynik parsowania elementu XML
	 */
	public static void identifyUrl(final Context context, final ParserResult result) {
		boolean isLob = false;
		/* Identyfikacja URL'a - START */
		if (result.childrenSize() == result.tagsSize() && result.containsTag(CcrtoPropertyURL.PROPERTY_REFERENCE)) {
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("-->identifyUrl[BEFORE]: typeName: %s, isEncodedStr: %s", result.getType(),
						result.getIsEncoded()));
			}
			String isEncoded = "true";
			String urlReference = null;
			String mimeType = null;
			/* krok 1: pobranie pola 'referenceId' */
			ParserResult referenceChild = result.getChildByTagName(CcrtoPropertyURL.PROPERTY_REFERENCE);
			Object mrcValue = referenceChild.getCcrtoObject();
			urlReference = StringUtils.EMPTY;
			if (mrcValue instanceof CcrtoPropertyLob) {
				CcrtoPropertyLob mrcLob = (CcrtoPropertyLob) mrcValue;
				mimeType = mrcLob.getMimeType();
				urlReference = mrcLob.getPropertyValue();
				if (mrcLob.getIsEncoded() != null) {
					isEncoded = mrcLob.getIsEncoded().toString();
				}
			} else if (mrcValue instanceof CcrtoPropertyString) {
				urlReference = ((CcrtoPropertyString) mrcValue).getPropertyValue();
			}
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("-->identifyUrl[BEFORE]: referenceId: %s", urlReference));
			}
			/* krok 2: pobranie pola z tytułem 'title' */
			String title = StringUtils.EMPTY;
			ParserResult titleChild = result.getChildByTagName(CcrtoPropertyURL.PROPERTY_TITLE);
			if (titleChild != null) {
				IdentifiedValue val = getValue(context, titleChild);
				isLob = val.isLob;
				if (!isLob) {
					title = val.value;
				}
			}
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("-->identifyUrl[BEFORE]: title: %s, isLob: %s", title, isLob));
			}
			if (isLob) {
				result.setLob(isLob);
				return;
			}
			/* krok 3: pobranie pola z typem 'type' */
			ParserResult typeChild = result.getChildByTagName(CcrtoPropertyURL.PROPERTY_TYPE);
			String urlClazzStr = StringUtils.EMPTY;
			if (typeChild != null) {
				IdentifiedValue val = getValue(context, typeChild);
				isLob = val.isLob;
				if (!isLob) {
					urlClazzStr = val.value;
				}
			}
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("-->identifyUrl[BEFORE]: urlClazz: %s, isLob: %s", urlClazzStr, isLob));
			}
			if (isLob) {
				result.setLob(isLob);
				return;
			}
			/* Ustawiam nazwę typu URL'a - START */
			CcrtoUrlClass urlClazz = null;
			if (StringUtils.isNotBlank(mimeType) && StringUtils.isBlank(urlClazzStr)) {
				/* typ nie został podany ale został zidentyfikowany z referencji */
				urlClazz = CcrtoUrlClass.valueOfMimeType(mimeType);
			}
			if (urlClazz == null) {
				/* domyślnie to jest URL */
				urlClazz = CcrtoUrlClass.LINK;
			}
			/* krok 4. Ustawiam nazwę typu elementu */
			String typeName = result.getType();
			if (StringUtils.isBlank(typeName) || typeName.equals(CcrtoPropertyType.STRING.getName())) {
				typeName = CcrtoPropertyType.URL.getName();
				result.setType(typeName);
			} else if (StringUtils.isNotBlank(typeName) && !typeName.equals(CcrtoPropertyType.URL.getName())) {
				result.setType(typeName + CcrtoPropertyTypeUtils.TYPE_NAME_SEPARATOR + CcrtoPropertyType.URL.getName());
			}

			/* Ustawiam nazwę typu URL'a - KONIEC */
			CcrtoPropertyURL property = new CcrtoPropertyURL();
			property.setType(typeName);
			property.setTitle(title);
			property.setUrlReference(urlReference);
			property.setUrlClass(urlClazz);
			result.setIsEncoded(isEncoded);
			setParamAttributes(property, result);
			result.setCcrtoObject(property);
			if (logger.isTraceEnabled()) {
				logger.trace(String.format("-->identifyUrl[AFTER]: typeName: %s, value: %s, isEncodedStr: %s", typeName,
						property.toString(), isEncoded));
			}
		}
		/* Identyfikacja URL'a - KONIEC */
	}

	private static IdentifiedValue getValue(Context context, ParserResult valueResult) {
		Object mrcValue = valueResult.getCcrtoObject();
		String value = StringUtils.EMPTY;
		if (mrcValue instanceof CcrtoProperty) {
			value = ((CcrtoProperty) mrcValue).getPropertyValue();
			return new IdentifiedValue(/* isLob */ (mrcValue instanceof CcrtoPropertyLob), value);
		}
		return new IdentifiedValue(/* isLob */ false, value);
	}

	/**
	 * 
	 * IdentifiedValue - klasa pomocnicza do identyfikacji wartości elementu XML
	 *
	 * @author Sławomir Cichy &lt;slawomir.cichy@ibpm.pro&gt;
	 * @version $Revision: 1.1 $
	 *
	 */
	private static class IdentifiedValue {
		/** czy wartość reprezentuje LOB? */
		private final boolean isLob;
		/** odczytana wartość */
		private final String value;

		private IdentifiedValue(boolean isLob, String value) {
			super();
			this.isLob = isLob;
			this.value = value;
		}

	}

	/**
	 * Identyfikacja obiektu sprawy
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param result
	 *            wynik parsowania elementu XML
	 */
	public static void identifyCcrtoPropertyCase(final Context context, final ParserResult result) {
		if (result.getHeader() != null) {
			ParserResult headerResult = result.getHeader();
			String typeName = result.getType();
			CaseHeader header = (CaseHeader) headerResult.getCcrtoObject();
			if (StringUtils.isBlank(typeName)) {
				/* Typ nie został zdefiniowany w atrybucie - pobieram z nagłówka - START */
				CaseType caseType = header.getType();
				if (caseType != null) {
					typeName = caseType.getType();
					if (StringUtils.isBlank(typeName)) {
						typeName = caseType.getId();
					}
				}
				/* Typ nie został zdefiniowany w atrybucie - pobieram z nagłówka - KONIEC */
			}
			result.setType(typeName);
			CcrtoPropertyCase mrcObject = new CcrtoPropertyCase();
			/* Potrzebujemy status obiektu */
			mrcObject.setStatus(result.getStatus());
			mrcObject.setCaseHeader(header);
			/* Potrzebujemy informacji o wymaganiu atrybutu pozycji obiektu */
			boolean mrcObjectWithRequiredPosition = false;
			if (StringUtils.isNotBlank(result.getAttrWithRequiredPosition())) {
				mrcObjectWithRequiredPosition = Boolean.parseBoolean(result.getAttrWithRequiredPosition());
			}
			boolean requiredPositionFromContext = context
					.getRequestPropertyValue(ObjectFactory.CONTEXT_OBJECT_WITH_REQUIRED_POSITION) != null
					&& (Boolean) context.getRequestPropertyValue(ObjectFactory.CONTEXT_OBJECT_WITH_REQUIRED_POSITION);
			mrcObject.setWithRequiredPosition(mrcObjectWithRequiredPosition || requiredPositionFromContext);
			for (ParserResult propResult : result.getChildren()) {
				if (propResult.getCcrtoObject() instanceof CcrtoProperty) {
					CcrtoProperty property = (CcrtoProperty) propResult.getCcrtoObject();
					mrcObject.add(property);
				}
			}
			setParamAttributes(mrcObject, result);
			result.setCcrtoObject(mrcObject);
		}
	}

	/**
	 * Identyfikacja listy elementów CCRTO
	 * 
	 * @param context
	 *            kontekst wykonywanej operacji
	 * @param result
	 *            wynik parsowania elementu XML
	 * @param itemTagName
	 *            nazwa tag'a elementu listy XML
	 * @param typeName
	 *            przygotowana, oczyszczona z sufiksu '[]' nazwa typu
	 */
	public static void identifyCcrtoPropertyList(final Context context, final ParserResult result, String itemTagName,
			String typeName) {
		if (result.childrenSize() >= 1 && result.tagsSize() == 1 && result.containsTag(itemTagName)) {
			boolean isLob = false;
			ParserResult previousItem = result.getChildByTagName(itemTagName);
			CcrtoProperty previousProperty = (CcrtoProperty) previousItem.getCcrtoObject();
			String previousItemType = previousProperty.getType();
			if (logger.isTraceEnabled()) {
				logger.trace("-->identifyMrcList: '{}'; type = {}, previousItemType={} ",
						new Object[] { result.getTagName(), typeName, previousItemType });
			}
			IValueWithProperties ccrtoList;
			if (ObjectFactory.TAG_MAP_ENTRY.equals(itemTagName)) {
				ccrtoList = new CcrtoPropertyMap();
			} else {
				ccrtoList = new CcrtoPropertyList();
			}
			/* Potrzebujemy status obiektu */
			ccrtoList.setStatus(result.getStatus());
			for (ParserResult propResult : result.getChildren()) {
				/* dołączam elementy listy i analizuję ich typ - START */
				CcrtoProperty currentItem = (CcrtoProperty) propResult.getCcrtoObject();
				if (logger.isTraceEnabled()) {
					logger.trace("-->identifyMrcList: name = {}; type = {},  class = {} ", new Object[] {
							currentItem.getPropertyName(), currentItem.getType(), currentItem.getClass() });
				}
				if (previousItemType != null && !previousItemType.equals(currentItem.getType())) {
					/*
					 * nie można podchodzić bezkrytycznie do zmiany typów - nowy mechanizm
					 * identyfikacji typu na podstawie wartości odróżnia Integer od Decimal oraz
					 * DateLong od Date i to teraz sprawia kłopot. Trzeba sprawdzić czy różne typy
					 * to pochodne tego samego rodzaju i dopiero później podjąć decyzję o tym czy
					 * jest to LOB czy nie.
					 */
					/*
					 * typ elementu powinien być ustawiony, zobacz metodę setValueForUnknownType(..)
					 */
					CcrtoPropertyType currentPropertyType = CcrtoPropertyType.getType(currentItem.getType());
					if (currentPropertyType == null) {
						currentPropertyType = CcrtoPropertyType.ANY;
					}
					CcrtoPropertyType previousPropertyType = CcrtoPropertyType.getType(previousItemType);
					if (previousPropertyType == null) {
						previousPropertyType = CcrtoPropertyType.ANY;
					}

					/* Analiza zmiany - START */
					/* Mam nadzieję, że nie popełniłem w tym błędu logicznego... */
					if (CcrtoPropertyType.STRING.equals(previousPropertyType)
							|| CcrtoPropertyType.STRING.equals(currentPropertyType)) {
						/* pozostaje String */
						propResult.setType(CcrtoPropertyType.STRING.getName());
					} else if (CcrtoPropertyType.CURRENCY.equals(previousPropertyType)
							|| CcrtoPropertyType.CURRENCY.getName().equals(currentPropertyType)) {
						/*
						 * jeżeli coś było 'String' to się załapie na poprzedni warunek, teraz pozostaje
						 * CURRENCY
						 */
						propResult.setType(CcrtoPropertyType.CURRENCY.getName());
					} else if (previousPropertyType.isNumber() || currentPropertyType.isNumber()) {
						/*
						 * jeżeli coś było 'String' lub 'Currency' to się załapie na poprzednie warunki,
						 * teraz ustawiam NUMBER
						 */
						propResult.setType(CcrtoPropertyType.NUMBER.getName());
					} else if ((previousPropertyType.isDate() || currentPropertyType.isDate())) {
						/*
						 * powinno się później wywalić na formatowaniu daty? i tak znać developerowi, że
						 * coś źle przesyła...
						 */
						propResult.setType(CcrtoPropertyType.DATE_LONG.getName());
					} else {
						isLob = true;
					}
					/* Analiza zmiany - KONIEC */
				}
				if (isLob || currentItem instanceof CcrtoPropertyList) {
					isLob = true;
					if (logger.isTraceEnabled()) {
						logger.trace("-->identifyMrcList: type = {} is broken ", new Object[] { typeName });
					}
					break;
				}
				previousItemType = propResult.getType();
				ccrtoList.add(currentItem);
				/* dołączam elementy listy i analizuję ich typ - KONIEC */
			}
			if (!isLob) {
				/* Robię analizę czy typ macierzy różni się od typów elementów - START */
				/* na razie typeName to typ z elementu macierzy */
				if (StringUtils.isBlank(typeName) || typeName.equals(CcrtoPropertyType.STRING.getName())) {
					/* ustawiam macierzy typ, jaki wyszedł z analizy elementów */
					typeName = previousItemType;
				}
				if (typeName != null) {
					/*
					 * typ jest ustawiony - teoretycznie nie powinno być sytuacji, ze element ma
					 * ustawiony prefiks, a weszliśmy z czystą nazwą, ale kto wie...
					 */
					if (typeName.endsWith(CcrtoPropertyTypeUtils.ARRAY_SUFFIX)) {
						typeName = typeName.substring(0, typeName.length() - 2);
					}
					if (StringUtils.isNotBlank(previousItemType) && !previousItemType.equals(typeName)) {
						/* OK, jeżeli typ macierzy jest inny niż jej elementy, tworzę nazwę złożoną */
						typeName = typeName + CcrtoPropertyTypeUtils.TYPE_NAME_SEPARATOR + previousItemType;
					}
				}
				if (CcrtoPropertyType.ENTRY.getName().equals(typeName)) {
					/* jeżeli jest to lista entry, to znaczy, ze jest to mapa */
					/* Mapa nie posiada sufiksu w nazwie */
					typeName = CcrtoPropertyType.MAP.getName();
				} else {
					typeName = typeName + CcrtoPropertyTypeUtils.ARRAY_SUFFIX;
				}
				if (logger.isTraceEnabled()) {
					logger.trace("-->identifyMrcList: type = {}", new Object[] { typeName });
				}
				result.setType(typeName);
				ccrtoList.setType(typeName);
				setParamAttributes((CcrtoProperty) ccrtoList, result);
				result.setCcrtoObject(ccrtoList);
			}
			result.setLob(isLob);
		}
	}

	/**
	 * usunięcie niepotrzebnych znaków z treści/wartości elementu XML
	 * 
	 * @param input
	 *            wartość
	 * @return oczyszczona wartość
	 */
	public static String trim(String input) {
		BufferedReader reader = new BufferedReader(new StringReader(input));
		StringBuilder result = new StringBuilder();
		try {
			String line;
			while ((line = reader.readLine()) != null)
				result.append(line.trim());
			return result.toString();
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * Utworzenie obiektu pustej listy MrcList
	 * 
	 * @param result
	 *            obiekt sparsowanego elementu XML
	 * @param typeName
	 *            dotychczas wykryta nazwa typu
	 * @return instancja MrcList bez elementów
	 */
	public static CcrtoPropertyList createEmptyCcrtoPropertyList(ParserResult result, String typeName) {
		CcrtoPropertyList mrcList = new CcrtoPropertyList();
		boolean hasSuffix = typeName.endsWith(CcrtoPropertyTypeUtils.ARRAY_SUFFIX);
		String listTypeName = (hasSuffix ? typeName : typeName + CcrtoPropertyTypeUtils.ARRAY_SUFFIX);
		mrcList.setType(listTypeName);
		/* Potrzebujemy status obiektu */
		mrcList.setStatus(result.getStatus());
		return mrcList;
	}

	/**
	 * Utworzenie pustego obiektu sprawy
	 * 
	 * @param result
	 *            obiekt sparsowanego elementu XML
	 * @param typeName
	 *            dotychczas wykryta nazwa typu
	 * @return pusta instancja CcrtoPropertyCase lub instancja CcrtoPropertyList bez
	 *         elementów gdy się okaże, że nazwa klasy zawierała sufiks '[]'.
	 */
	public static CcrtoProperty createEmptyCcrtoPropertyObject(ParserResult result, String typeName) {
		CcrtoProperty property;
		boolean isArray = typeName.endsWith(CcrtoPropertyTypeUtils.ARRAY_SUFFIX);
		if (isArray) {
			property = new CcrtoPropertyList();
		} else {
			property = new CcrtoPropertyCase();
		}
		property.setType(typeName);
		return property;
	}

	public static List<CcrtoPropertyCase> unmarshalCcrtoPropertyCaseList(Context context, List<CcrtoPropertyCase> data,
			String propertyName) {
		String lPropertyName = propertyName;
		if (StringUtils.isBlank(lPropertyName)) {
			lPropertyName = ObjectFactory.TAG_MAIN_DOCUMENT;
		}
		if (context != null) {
			List<CcrtoPropertyCase> result = new ArrayList<>();
			CcrtoJAXBContextChangeRequest changeRequest = new CcrtoJAXBContextChangeRequest();
			for (CcrtoPropertyCase caseObject : data) {
				String copyPropertyName = caseObject.getPropertyName();
				if (StringUtils.isBlank(copyPropertyName)) {
					copyPropertyName = lPropertyName;
				}
				CcrtoPropertyCase copy = copyCcrtoPropertyCaseWithoutProperties(caseObject, copyPropertyName);
				Change change = copyCcrtoPropertyCaseProperties(context, copy, caseObject.getAnyProperties());
				changeRequest.addChange(change);
				result.add(copy);
			}
			changeRequest.commit();
			return result;
		}
		return data;
	}

	private static Change copyCcrtoPropertyCaseProperties(Context context, CcrtoPropertyCase copy,
			List<JAXBElement<CcrtoProperty>> anyProperties) {
		if (anyProperties == null || anyProperties.isEmpty()) {
			return null;
		}
		Change change = null;
		try {
			for (Object anyObject : anyProperties) {
				if (anyObject instanceof Node) {
					CcrtoProperty p = (CcrtoProperty) CcrtoJAXBUnmarshalHelper.readObject(context, (Node) anyObject,
							/* iteration */ 0);
					change = CcrtoJAXBContextChangeRequest.createChange(p.getPropertyName(), p.getClass());
					copy.add(p);
				} else {
					@SuppressWarnings("unchecked")
					JAXBElement<CcrtoProperty> e = (JAXBElement<CcrtoProperty>) anyObject;
					copy.getAnyProperties().add(e);
				}

			}
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
		return change;
	}

	private static CcrtoPropertyCase copyCcrtoPropertyCaseWithoutProperties(CcrtoPropertyCase caseObject,
			String copyPropertyName) {
		CcrtoPropertyCase copy = new CcrtoPropertyCase();
		copy.setPropertyName(copyPropertyName);
		copy.setType(caseObject.getType());
		copy.setStatus(caseObject.getStatus());
		copy.setCaseHeader(caseObject.getCaseHeader());
		copy.setWithRequiredPosition(caseObject.getWithRequiredPosition());
		Map<QName, String> otherProperties = caseObject.getOtherAttributes();
		if (!otherProperties.isEmpty()) {
			for (Entry<QName, String> attr : otherProperties.entrySet()) {
				copy.getOtherAttributes().put(attr.getKey(), attr.getValue());
			}
		}
		return copy;
	}

	public static String nodeToString(Node node) {
		StringWriter sw = new StringWriter();
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			factory.setFeature(FEATURE_SECURE_PROCESSING, true);
			Transformer t = factory.newTransformer();
			t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			t.setOutputProperty(OutputKeys.INDENT, "yes");
			t.transform(new DOMSource(node), new StreamResult(sw));
		} catch (TransformerException te) {
			logger.error("[TransformerException] --> nodeToString", te);
			throw new IllegalAccessError(te.getMessage());
		}
		return sw.toString();
	}

}
