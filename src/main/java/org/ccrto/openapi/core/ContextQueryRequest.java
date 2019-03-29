package org.ccrto.openapi.core;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ccrto.openapi.core.system.SystemPropertiesDefaults;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * ContextQueryRequest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextQueryRequest implements Serializable {

	private static final long serialVersionUID = 8362866922223425984L;

	/** Maksymalna liczba wyników uzyskana w wyniku wyszukiwania danych */
	@JsonProperty(required = true, defaultValue = "1000")
	@XmlElement(required = true, defaultValue = "1000")
	private Integer maxResults = SystemPropertiesDefaults.MAX_QUERY_HITS;

	/** Definicja ewentualnego timeout'u operacji - liczba milisekund */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false, defaultValue = "10000000")
	@XmlElement(required = false, defaultValue = "10000000")
	private Integer queryTimeout = SystemPropertiesDefaults.RESPONSE_TIMEOUT;

	/**
	 * Defines strategies for fetching data from the database.
	 * <ul>
	 * <li>{@code true} - represents he <code>LAZY</code> strategy is a hint to the
	 * persistence provider runtime that data should be fetched lazily when it is
	 * first accessed. The implementation is permitted to eagerly fetch data for
	 * which the <code>LAZY</code> strategy hint has been specified.</li>
	 * <li>{@code false} - represents the <code>EAGER</code> strategy is a
	 * requirement on the persistence provider runtime that data must be eagerly
	 * fetched. Defines that data must be eagerly fetched.</li>
	 * </ul>
	 */
	@JsonProperty(required = true, defaultValue = "true")
	@XmlElement(required = true, defaultValue = "true")
	private Boolean lazilyFetchType = true;

	/**
	 * informacja, czy podczas budowania zapytania wyszukującego mają być ignorowane
	 * pola alternatywne - jeżeli {@code false} wyszukiwanie odbędzie się tylko po
	 * polach podstawowych. Wartość domyślna zdefiniowana w
	 * {@link MercuryConfigConst#DEFAULT_IGNORE_ALTERNATE_FIELDS}.
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false, defaultValue = "true")
	@XmlElement(required = false, defaultValue = "true")
	private Boolean ignoreAlternateNamesOfFields = true;

	/**
	 * Informacja o sposobie wykorzystania pamięci podręcznych podczas realizacji
	 * zapytań wyszukiwania/pobierania obiektów encji. Wartość domyślna zdefiniowana
	 * w {@link MercuryConfigConst#DEFAULT_CACHE_USAGE}. Możliwe wartości o nazwy
	 * elementów obiektu enum {@link org.ccrto.openapi.common.cache.CacheUsage}
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false, defaultValue = "TO_USE")
	@XmlElement(required = false, defaultValue = "TO_USE")
	private String cacheUsage = CacheUsage.TO_USE.name();

	/**
	 * Informacja o domyślnym sposobie sortowania wyników wyszukiwania. Pole
	 * przyjmuje wartość:
	 * <ul>
	 * <li>dla sortowania A-Z
	 * 
	 * <pre>
	 * &lt;nazwa_pola> [ASC]
	 * </pre>
	 * 
	 * </li>
	 * <li>dla sortowania Z-A
	 * 
	 * <pre>
	 * &lt;nazwa_pola> DESC
	 * </pre>
	 * 
	 * </li>
	 * </ul>
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String defaultSortClause;

	/**
	 * Nazwa typu/widoku w jakim ma zostać zwrócony wynik
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private CaseType returnType;

	/**
	 * @return the {@link #maxResults}
	 */
	public Integer getMaxResults() {
		return maxResults;
	}

	/**
	 * @param maxResults
	 *            the {@link #maxResults} to set
	 */
	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	/**
	 * @return the {@link #queryTimeout}
	 */
	public Integer getQueryTimeout() {
		return queryTimeout;
	}

	/**
	 * @param queryTimeout
	 *            the {@link #queryTimeout} to set
	 */
	public void setQueryTimeout(Integer queryTimeout) {
		this.queryTimeout = queryTimeout;
	}

	/**
	 * @return the {@link #lazilyFetchType}
	 */
	public Boolean getLazilyFetchType() {
		return lazilyFetchType;
	}

	/**
	 * @param lazilyFetchType
	 *            the {@link #lazilyFetchType} to set
	 */
	public void setLazilyFetchType(Boolean lazilyFetchType) {
		this.lazilyFetchType = lazilyFetchType;
	}

	/**
	 * @return the {@link #ignoreAlternateNamesOfFields}
	 */
	public Boolean getIgnoreAlternateNamesOfFields() {
		return ignoreAlternateNamesOfFields;
	}

	/**
	 * @param ignoreAlternateNamesOfFields
	 *            the {@link #ignoreAlternateNamesOfFields} to set
	 */
	public void setIgnoreAlternateNamesOfFields(Boolean ignoreAlternateNamesOfFields) {
		this.ignoreAlternateNamesOfFields = ignoreAlternateNamesOfFields;
	}

	/**
	 * @return the {@link #cacheUsage}
	 */
	public String getCacheUsage() {
		return cacheUsage;
	}

	/**
	 * @param cacheUsage
	 *            the {@link #cacheUsage} to set
	 */
	public void setCacheUsage(String cacheUsage) {
		this.cacheUsage = cacheUsage;
	}

	/**
	 * @return the {@link #defaultSortClause}
	 */
	public String getDefaultSortClause() {
		return defaultSortClause;
	}

	/**
	 * @param defaultSortClause
	 *            the {@link #defaultSortClause} to set
	 */
	public void setDefaultSortClause(String defaultSortClause) {
		this.defaultSortClause = defaultSortClause;
	}

	/**
	 * @return the {@link #returnType}
	 */
	public CaseType getReturnType() {
		return returnType;
	}

	/**
	 * @param returnType
	 *            the {@link #returnType} to set
	 */
	public void setReturnType(CaseType returnType) {
		this.returnType = returnType;
	}

	/**
	 * Tworzenie kopii obecnej instancji obiektu
	 * 
	 * @return kopia instancji
	 */
	public ContextQueryRequest copy() {
		ContextQueryRequest copy = new ContextQueryRequest();
		copy.maxResults = this.maxResults;
		copy.queryTimeout = this.queryTimeout;
		copy.lazilyFetchType = this.lazilyFetchType;
		copy.ignoreAlternateNamesOfFields = this.ignoreAlternateNamesOfFields;
		copy.cacheUsage = this.cacheUsage;
		copy.defaultSortClause = this.defaultSortClause;
		copy.returnType = this.returnType;
		return copy;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cacheUsage == null) ? 0 : cacheUsage.hashCode());
		result = prime * result + ((defaultSortClause == null) ? 0 : defaultSortClause.hashCode());
		result = prime * result
				+ ((ignoreAlternateNamesOfFields == null) ? 0 : ignoreAlternateNamesOfFields.hashCode());
		result = prime * result + ((lazilyFetchType == null) ? 0 : lazilyFetchType.hashCode());
		result = prime * result + ((maxResults == null) ? 0 : maxResults.hashCode());
		result = prime * result + ((queryTimeout == null) ? 0 : queryTimeout.hashCode());
		result = prime * result + ((returnType == null) ? 0 : returnType.hashCode());
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
		if (!(obj instanceof ContextQueryRequest)) {
			return false;
		}
		ContextQueryRequest other = (ContextQueryRequest) obj;
		if (cacheUsage == null) {
			if (other.cacheUsage != null) {
				return false;
			}
		} else if (!cacheUsage.equals(other.cacheUsage)) {
			return false;
		}
		if (defaultSortClause == null) {
			if (other.defaultSortClause != null) {
				return false;
			}
		} else if (!defaultSortClause.equals(other.defaultSortClause)) {
			return false;
		}
		if (ignoreAlternateNamesOfFields == null) {
			if (other.ignoreAlternateNamesOfFields != null) {
				return false;
			}
		} else if (!ignoreAlternateNamesOfFields.equals(other.ignoreAlternateNamesOfFields)) {
			return false;
		}
		if (lazilyFetchType == null) {
			if (other.lazilyFetchType != null) {
				return false;
			}
		} else if (!lazilyFetchType.equals(other.lazilyFetchType)) {
			return false;
		}
		if (maxResults == null) {
			if (other.maxResults != null) {
				return false;
			}
		} else if (!maxResults.equals(other.maxResults)) {
			return false;
		}
		if (queryTimeout == null) {
			if (other.queryTimeout != null) {
				return false;
			}
		} else if (!queryTimeout.equals(other.queryTimeout)) {
			return false;
		}
		if (returnType == null) {
			if (other.returnType != null) {
				return false;
			}
		} else if (!returnType.equals(other.returnType)) {
			return false;
		}
		return true;
	}

}
