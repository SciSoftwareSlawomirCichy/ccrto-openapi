package org.ccrto.openapi.context;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 * 
 * ContextFormats
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextFormats implements Serializable {

	private static final long serialVersionUID = 5670246589883177813L;

	/** format daty krótkiej np. 'yyyy-MM-dd'. */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String dateShortFormat;

	/** format daty długiej np. 'dd-MM-yyyy HH:mm:ss' */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String dateLongFormat;

	/** format liczby zmiennoprzecinkowej */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String numberFormat;

	/** format liczby zmiennoprzecinkowej reprezentującej wartość/cenę */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String currencyFormat;

	/**
	 * kod waluty (trzyliterowy kod zgodny z ISO_4217
	 * 
	 * https://en.wikipedia.org/wiki/ISO_4217#Active_codes
	 */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String currencyCode;

	/** format liczby całkowitej */
	@JsonInclude(Include.NON_NULL)
	@JsonProperty(required = false)
	@XmlElement(required = false)
	private String integerFormat;

	/**
	 * @return the {@link #dateShortFormat}
	 */
	public String getDateShortFormat() {
		return dateShortFormat;
	}

	/**
	 * @param dateShortFormat
	 *            the {@link #dateShortFormat} to set
	 */
	public void setDateShortFormat(String dateShortFormat) {
		this.dateShortFormat = dateShortFormat;
	}

	/**
	 * @return the {@link #dateLongFormat}
	 */
	public String getDateLongFormat() {
		return dateLongFormat;
	}

	/**
	 * @param dateLongFormat
	 *            the {@link #dateLongFormat} to set
	 */
	public void setDateLongFormat(String dateLongFormat) {
		this.dateLongFormat = dateLongFormat;
	}

	/**
	 * @return the {@link #numberFormat}
	 */
	public String getNumberFormat() {
		return numberFormat;
	}

	/**
	 * @param numberFormat
	 *            the {@link #numberFormat} to set
	 */
	public void setNumberFormat(String numberFormat) {
		this.numberFormat = numberFormat;
	}

	/**
	 * @return the {@link #currencyFormat}
	 */
	public String getCurrencyFormat() {
		return currencyFormat;
	}

	/**
	 * @param currencyFormat
	 *            the {@link #currencyFormat} to set
	 */
	public void setCurrencyFormat(String currencyFormat) {
		this.currencyFormat = currencyFormat;
	}

	/**
	 * @return the {@link #currencyCode}
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * @param currencyCode
	 *            the {@link #currencyCode} to set
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * @return the {@link #integerFormat}
	 */
	public String getIntegerFormat() {
		return integerFormat;
	}

	/**
	 * @param integerFormat
	 *            the {@link #integerFormat} to set
	 */
	public void setIntegerFormat(String integerFormat) {
		this.integerFormat = integerFormat;
	}

	/**
	 * Tworzenie kopii obecnej instancji obiektu
	 * 
	 * @return kopia instancji
	 */
	public ContextFormats copy() {
		ContextFormats copy = new ContextFormats();
		copy.dateShortFormat = this.dateShortFormat;
		copy.dateLongFormat = this.dateLongFormat;
		copy.numberFormat = this.numberFormat;
		copy.currencyFormat = this.currencyFormat;
		copy.currencyCode = this.currencyCode;
		copy.integerFormat = this.integerFormat;
		return copy;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
		result = PRIME * result + ((currencyFormat == null) ? 0 : currencyFormat.hashCode());
		result = PRIME * result + ((dateLongFormat == null) ? 0 : dateLongFormat.hashCode());
		result = PRIME * result + ((dateShortFormat == null) ? 0 : dateShortFormat.hashCode());
		result = PRIME * result + ((integerFormat == null) ? 0 : integerFormat.hashCode());
		result = PRIME * result + ((numberFormat == null) ? 0 : numberFormat.hashCode());
		return result;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ContextFormats other = (ContextFormats) obj;
		if (currencyCode == null) {
			if (other.currencyCode != null) {
				return false;
			}
		} else if (!currencyCode.equals(other.currencyCode)) {
			return false;
		}
		if (currencyFormat == null) {
			if (other.currencyFormat != null) {
				return false;
			}
		} else if (!currencyFormat.equals(other.currencyFormat)) {
			return false;
		}
		if (dateLongFormat == null) {
			if (other.dateLongFormat != null) {
				return false;
			}
		} else if (!dateLongFormat.equals(other.dateLongFormat)) {
			return false;
		}
		if (dateShortFormat == null) {
			if (other.dateShortFormat != null) {
				return false;
			}
		} else if (!dateShortFormat.equals(other.dateShortFormat)) {
			return false;
		}
		if (integerFormat == null) {
			if (other.integerFormat != null) {
				return false;
			}
		} else if (!integerFormat.equals(other.integerFormat)) {
			return false;
		}
		if (numberFormat == null) {
			if (other.numberFormat != null) {
				return false;
			}
		} else if (!numberFormat.equals(other.numberFormat)) {
			return false;
		}
		return true;
	}

}
