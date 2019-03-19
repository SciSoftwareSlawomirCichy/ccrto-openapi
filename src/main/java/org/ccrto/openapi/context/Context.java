/**
 * 
 */
package org.ccrto.openapi.context;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.StringUtils;
import org.ccrto.openapi.refs.InvolvementIdentificationRef;
import org.ccrto.openapi.refs.SourceOfRequestRef;
import org.ccrto.openapi.refs.UserRoleRef;
import org.ccrto.openapi.system.SystemPropertiesDefaults;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * Context
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Context implements Serializable {

	private static final long serialVersionUID = -1195024965344450017L;

	/** Nazwa aplikacji */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private String appName;

	/** Wersja aplikacji */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private String appVersion;

	/**
	 * Dane użytkownika realizującego żądanie. Format zgodny z otwartym standardem
	 * TMF672
	 * 
	 * @see InvolvementIdentificationRef
	 */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private UserContext user;

	/**
	 * Identyfikator obecnej rola użytkownika (potrzebne do rejestracji zespołu w
	 * ramach którego użytkownik dokonuje zmian. Format zgodny z otwartym standardem
	 * TMF672.
	 * 
	 * @see UserRoleRef
	 */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private UserRoleContext currentRole;

	/**
	 * Lista ról użytkownika (unikalne nazwy/identyfikatory). Niezbędne do
	 * weryfikacji uprawnień/autoryzacji wykonywanych przez użytkownika operacji.
	 * Format zgodny z otwartym standardem TMF672
	 * 
	 * @see UserRoleRef
	 */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private Set<UserRoleContext> userRoles;

	/**
	 * Czy można ufać ustawionym danym? Wykorzystamy tę flagę do ewentualnej
	 * aktualizacji danych o użytkowniku
	 */
	@JsonProperty(required = false, defaultValue = "false")
	@XmlElement(required = false, defaultValue = "false")
	private boolean trustedData = false;

	/** Definicja wersji językowej użytkownika */
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String locale = SystemPropertiesDefaults.getSystemLocaleTag();

	/** Definicja strefy czasowej użytkownika */
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String timeZone = SystemPropertiesDefaults.TIMEZONE;

	/**
	 * Formaty prezentacji liczb oraz daty
	 */
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private ContextFormats formats;

	/**
	 * informacja, czy wartości parametrów pojawiających się w żądaniu to wartości
	 * prezentacyjne. Możliwe wartości to wartości odpowiadające w zależności od
	 * metod i usług przyjmują wartości odpowiadające pozycjom obiektu
	 * {@link DecodeMethod}.
	 * 
	 * @see DecodeMethod
	 */
	@JsonProperty(required = true, defaultValue = "DATE_AND_LOB")
	@XmlElement(required = true, defaultValue = "DATE_AND_LOB")
	private String decodeRequest = SystemPropertiesDefaults.DECODE_RESULT_AND_REQUEST.name();

	/**
	 * informacja, czy wynik, parametry sprawy mają zdekodowane wartości parametrów
	 * (wartości prezentacyjne). Możliwe wartości to wartości odpowiadające pozycjom
	 * obiektu {@link DecodeMethod}.
	 * 
	 * @see DecodeMethod
	 */
	@JsonProperty(required = false, defaultValue = "DATE_AND_LOB")
	@XmlElement(required = false, defaultValue = "DATE_AND_LOB")
	private String decodeResult = SystemPropertiesDefaults.DECODE_RESULT_AND_REQUEST.name();

	/**
	 * Akronim aplikacji/źródła pochodzenia żądania akcji
	 * 
	 * @see SourceOfRequestRef#getId()
	 */
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private SourceOfRequestInContext sourceOfRequest;

	/** identyfikator zewnętrznego kontekstu np. SESSION_ID */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private String rootVersionContextID;

	/**
	 * Parametr do obsługi komunikacji pomiędzy node'ami klastra. Oznacza on, że
	 * operacja musi być wykonana lokalnie, przez członka klastra, do którego
	 * trafiło żądanie wykonania operacji. Takiego żądania nie można przesłać do
	 * innego członka klastra.
	 */
	@JsonProperty(required = false, defaultValue = "false")
	@XmlElement(required = false, defaultValue = "false")
	private boolean directRequest = false;

	/**
	 * Maksymalny poziom zagnieżdżenia dla spraw zależnych zwracanych w wyniku.
	 */
	@JsonProperty(required = true, defaultValue = "3")
	@XmlElement(required = true, defaultValue = "3")
	private Integer maxDepthResult = SystemPropertiesDefaults.MAX_DEPTH;

	@JsonProperty(required = true)
	@XmlElement(required = true)
	private QueryRequestContext queryRequestContext;

	@JsonProperty(required = false)
	@XmlElement(required = false)
	private SaveRequestContext saveRequestContext;

	/**
	 * dodatkowe parametry wykorzystywane wewnętrznie przez system, do którego
	 * realizowane jest żądanie.
	 */
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private RequestProperties requestProperties;

	/**
	 * Domyślny konstruktor
	 */
	public Context() {
		super();
	}

	public Context copy() {
		Context copy = new Context();
		copy.appName = this.appName;
		copy.appVersion = this.appVersion;
		copy.user = this.user;
		copy.currentRole = this.currentRole;
		copy.userRoles = this.userRoles;
		copy.trustedData = this.trustedData;
		copy.locale = this.locale;
		copy.timeZone = this.timeZone;
		if (this.formats != null) {
			copy.formats = this.formats.copy();
		}
		copy.sourceOfRequest = this.sourceOfRequest;
		copy.rootVersionContextID = this.rootVersionContextID;
		copy.directRequest = this.directRequest;
		copy.maxDepthResult = this.maxDepthResult;
		if (this.requestProperties != null) {
			copy.requestProperties = new RequestProperties(this.requestProperties);
		}
		if (this.queryRequestContext != null) {
			copy.queryRequestContext = this.queryRequestContext.copy();
		}
		if (this.saveRequestContext != null) {
			copy.saveRequestContext = this.saveRequestContext.copy();
		}
		copy.decodeResult = this.decodeResult;
		copy.decodeRequest = this.decodeRequest;
		return copy;

	}

	/**
	 * @return lista ustawionych nazw parametrów dodatkowych
	 */
	public RequestProperties getRequestProperties() {
		if (this.requestProperties == null) {
			this.requestProperties = new RequestProperties();
		}
		return this.requestProperties;

	}

	public Context setRequestProperties(RequestProperties requestProperties) {
		synchronized (this) {
			this.requestProperties = requestProperties;
		}
		return this;
	}

	/**
	 * Pobranie listy nazw dodatkowych parametrów ustawionych w kontekście.
	 * 
	 * @return lista nazw dodatkowych parametrów
	 */
	@JsonIgnore
	public Set<String> getRequestPropertyNames() {
		synchronized (this) {
			return getRequestProperties().keySet();
		}
	}

	/**
	 * Pobranie wartości dodatkowego parametru ustawionego w kontekście
	 * 
	 * @param propertyName
	 *            nazwa parametru
	 * @return wartość parametru
	 */
	@JsonIgnore
	public Object getRequestPropertyValue(String propertyName) {
		synchronized (this) {
			return getRequestProperties().get(propertyName);
		}
	}

	/**
	 * Ustawianie wartości dodatkowego parametru w kontekście
	 * 
	 * @param propertyName
	 *            nazwa parametru
	 * @param paramValue
	 *            wartość parametru
	 * @return obecny obiekt kontekstu
	 */
	public Context addRequestPropertyValue(String propertyName, Serializable paramValue) {
		synchronized (this) {
			if (paramValue == null) {
				getRequestProperties().put(propertyName, StringUtils.EMPTY);
			} else {
				getRequestProperties().put(propertyName, paramValue);
			}
		}
		return this;
	}

	public void clearRequestProperties() {
		synchronized (this) {
			getRequestProperties().clear();
		}
	}

	public void removeRequestProperty(String propertyName) {
		synchronized (this) {
			getRequestProperties().remove(propertyName);
		}
	}

	/**
	 * @return the {@link #appName}
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the {@link #appName} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setAppName(String appName) {
		this.appName = appName;
		return this;
	}

	/**
	 * @return the {@link #appVersion}
	 */
	public String getAppVersion() {
		return appVersion;
	}

	/**
	 * @param appVersion
	 *            the {@link #appVersion} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setAppVersion(String appVersion) {
		this.appVersion = appVersion;
		return this;
	}

	/**
	 * @return the {@link #user}
	 */
	public UserContext getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the {@link #user} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setUser(UserContext user) {
		this.user = user;
		return this;
	}

	/**
	 * @return the {@link #currentRole}
	 */
	public UserRoleContext getCurrentRole() {
		return currentRole;
	}

	/**
	 * @param currentRole
	 *            the {@link #currentRole} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setCurrentRole(UserRoleContext currentRole) {
		this.currentRole = currentRole;
		return this;
	}

	/**
	 * @return the {@link #userRoles}
	 */
	public Set<UserRoleContext> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles
	 *            the {@link #userRoles} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setUserRoles(Set<UserRoleContext> userRoles) {
		this.userRoles = userRoles;
		return this;
	}

	/**
	 * @return the {@link #trustedData}
	 */
	public boolean isTrustedData() {
		return trustedData;
	}

	/**
	 * @param trustedData
	 *            the {@link #trustedData} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setTrustedData(boolean trustedData) {
		this.trustedData = trustedData;
		return this;
	}

	/**
	 * @return the {@link #locale}
	 */
	public String getLocale() {
		return locale;
	}

	/**
	 * @param locale
	 *            the {@link #locale} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setLocale(String locale) {
		this.locale = locale;
		return this;
	}

	/**
	 * @return the {@link #timeZone}
	 */
	public String getTimeZone() {
		return timeZone;
	}

	/**
	 * @param timeZone
	 *            the {@link #timeZone} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setTimeZone(String timeZone) {
		this.timeZone = timeZone;
		return this;
	}

	/**
	 * @return the {@link #formats}
	 */
	public ContextFormats getFormats() {
		return formats;
	}

	/**
	 * @param formats
	 *            the {@link #formats} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setFormats(ContextFormats formats) {
		this.formats = formats;
		return this;
	}

	/**
	 * @return the {@link #sourceOfRequest}
	 */
	public SourceOfRequestInContext getSourceOfRequest() {
		return sourceOfRequest;
	}

	/**
	 * @param sourceOfRequest
	 *            the {@link #sourceOfRequest} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setSourceOfRequest(SourceOfRequestInContext sourceOfRequest) {
		this.sourceOfRequest = sourceOfRequest;
		return this;
	}

	/**
	 * @return the {@link #rootVersionContextID}
	 */
	public String getRootVersionContextID() {
		return rootVersionContextID;
	}

	/**
	 * @param rootVersionContextID
	 *            the {@link #rootVersionContextID} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setRootVersionContextID(String rootVersionContextID) {
		this.rootVersionContextID = rootVersionContextID;
		return this;
	}

	/**
	 * @return the {@link #directRequest}
	 */
	public boolean isDirectRequest() {
		return directRequest;
	}

	/**
	 * @param directRequest
	 *            the {@link #directRequest} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setDirectRequest(boolean directRequest) {
		this.directRequest = directRequest;
		return this;
	}

	/**
	 * @return the {@link #maxDepthResult}
	 */
	public Integer getMaxDepthResult() {
		return maxDepthResult;
	}

	/**
	 * @param maxDepthResult
	 *            the {@link #maxDepthResult} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setMaxDepthResult(Integer maxDepthResult) {
		this.maxDepthResult = maxDepthResult;
		return this;
	}

	/**
	 * @return the {@link #queryRequestContext}
	 */
	public QueryRequestContext getQueryRequestContext() {
		if (queryRequestContext == null) {
			queryRequestContext = new QueryRequestContext();
		}
		return queryRequestContext;
	}

	/**
	 * @param queryRequestContext
	 *            the {@link #queryRequestContext} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setQueryRequestContext(QueryRequestContext queryRequestContext) {
		this.queryRequestContext = queryRequestContext;
		return this;
	}

	/**
	 * @return the {@link #saveRequestContext}
	 */
	public SaveRequestContext getSaveRequestContext() {
		if (saveRequestContext == null) {
			saveRequestContext = new SaveRequestContext();
		}
		return saveRequestContext;
	}

	/**
	 * @param saveRequestContext
	 *            the {@link #saveRequestContext} to set
	 * @return obecny obiekt kontekstu
	 */
	public Context setSaveRequestContext(SaveRequestContext saveRequestContext) {
		this.saveRequestContext = saveRequestContext;
		return this;
	}

	/**
	 * @return the {@link #decodeResult}
	 */
	public String getDecodeResult() {
		return decodeResult;
	}

	/**
	 * @param decodeResult
	 *            the {@link #decodeResult} to set
	 */
	public void setDecodeResult(String decodeResult) {
		this.decodeResult = decodeResult;
	}

	/**
	 * @return the {@link #decodeRequest}
	 */
	public String getDecodeRequest() {
		return decodeRequest;
	}

	/**
	 * @param decodeRequest
	 *            the {@link #decodeRequest} to set
	 */
	public void setDecodeRequest(String decodeRequest) {
		this.decodeRequest = decodeRequest;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((appName == null) ? 0 : appName.hashCode());
		result = prime * result + ((appVersion == null) ? 0 : appVersion.hashCode());
		result = prime * result + ((currentRole == null) ? 0 : currentRole.hashCode());
		result = prime * result + ((decodeRequest == null) ? 0 : decodeRequest.hashCode());
		result = prime * result + ((decodeResult == null) ? 0 : decodeResult.hashCode());
		result = prime * result + (directRequest ? 1231 : 1237);
		result = prime * result + ((formats == null) ? 0 : formats.hashCode());
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
		result = prime * result + ((maxDepthResult == null) ? 0 : maxDepthResult.hashCode());
		result = prime * result + ((queryRequestContext == null) ? 0 : queryRequestContext.hashCode());
		result = prime * result + ((requestProperties == null) ? 0 : requestProperties.hashCode());
		result = prime * result + ((rootVersionContextID == null) ? 0 : rootVersionContextID.hashCode());
		result = prime * result + ((saveRequestContext == null) ? 0 : saveRequestContext.hashCode());
		result = prime * result + ((sourceOfRequest == null) ? 0 : sourceOfRequest.hashCode());
		result = prime * result + ((timeZone == null) ? 0 : timeZone.hashCode());
		result = prime * result + (trustedData ? 1231 : 1237);
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userRoles == null) ? 0 : userRoles.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Context)) {
			return false;
		}
		Context other = (Context) obj;
		if (appName == null) {
			if (other.appName != null) {
				return false;
			}
		} else if (!appName.equals(other.appName)) {
			return false;
		}
		if (appVersion == null) {
			if (other.appVersion != null) {
				return false;
			}
		} else if (!appVersion.equals(other.appVersion)) {
			return false;
		}
		if (currentRole == null) {
			if (other.currentRole != null) {
				return false;
			}
		} else if (!currentRole.equals(other.currentRole)) {
			return false;
		}
		if (decodeRequest == null) {
			if (other.decodeRequest != null) {
				return false;
			}
		} else if (!decodeRequest.equals(other.decodeRequest)) {
			return false;
		}
		if (decodeResult == null) {
			if (other.decodeResult != null) {
				return false;
			}
		} else if (!decodeResult.equals(other.decodeResult)) {
			return false;
		}
		if (directRequest != other.directRequest) {
			return false;
		}
		if (formats == null) {
			if (other.formats != null) {
				return false;
			}
		} else if (!formats.equals(other.formats)) {
			return false;
		}
		if (locale == null) {
			if (other.locale != null) {
				return false;
			}
		} else if (!locale.equals(other.locale)) {
			return false;
		}
		if (maxDepthResult == null) {
			if (other.maxDepthResult != null) {
				return false;
			}
		} else if (!maxDepthResult.equals(other.maxDepthResult)) {
			return false;
		}
		if (queryRequestContext == null) {
			if (other.queryRequestContext != null) {
				return false;
			}
		} else if (!queryRequestContext.equals(other.queryRequestContext)) {
			return false;
		}
		if (requestProperties == null) {
			if (other.requestProperties != null) {
				return false;
			}
		} else if (!requestProperties.equals(other.requestProperties)) {
			return false;
		}
		if (rootVersionContextID == null) {
			if (other.rootVersionContextID != null) {
				return false;
			}
		} else if (!rootVersionContextID.equals(other.rootVersionContextID)) {
			return false;
		}
		if (saveRequestContext == null) {
			if (other.saveRequestContext != null) {
				return false;
			}
		} else if (!saveRequestContext.equals(other.saveRequestContext)) {
			return false;
		}
		if (sourceOfRequest == null) {
			if (other.sourceOfRequest != null) {
				return false;
			}
		} else if (!sourceOfRequest.equals(other.sourceOfRequest)) {
			return false;
		}
		if (timeZone == null) {
			if (other.timeZone != null) {
				return false;
			}
		} else if (!timeZone.equals(other.timeZone)) {
			return false;
		}
		if (trustedData != other.trustedData) {
			return false;
		}
		if (user == null) {
			if (other.user != null) {
				return false;
			}
		} else if (!user.equals(other.user)) {
			return false;
		}
		if (userRoles == null) {
			if (other.userRoles != null) {
				return false;
			}
		} else if (!userRoles.equals(other.userRoles)) {
			return false;
		}
		return true;
	}

}
