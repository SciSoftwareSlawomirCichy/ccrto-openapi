package org.ccrto.openapi.core;

import java.io.Serializable;
import java.util.Set;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.ccrto.openapi.core.refs.InvolvementIdentificationRef;
import org.ccrto.openapi.core.refs.UserRoleRef;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * ContextSecurity
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ContextSecurity implements Serializable {

	private static final long serialVersionUID = -5210481603657157180L;

	/**
	 * Dane użytkownika realizującego żądanie. Format zgodny z otwartym standardem
	 * TMF672
	 * 
	 * @see InvolvementIdentificationRef
	 */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private ContextUser user;

	/**
	 * Lista ról użytkownika (unikalne nazwy/identyfikatory). Niezbędne do
	 * weryfikacji uprawnień/autoryzacji wykonywanych przez użytkownika operacji.
	 * Format zgodny z otwartym standardem TMF672
	 * 
	 * @see UserRoleRef
	 */
	@JsonProperty(required = true)
	@XmlElement(required = true)
	private Set<ContextUserRole> userRoles;

	public ContextSecurity() {
		super();
	}

	public ContextSecurity(Context context) {
		this(context.getUser(), context.getUserRoles());
	}

	public ContextSecurity(ContextUser user, Set<ContextUserRole> userRoles) {
		super();
		this.user = user;
		this.userRoles = userRoles;
	}

	/**
	 * @return the {@link #user}
	 */
	public ContextUser getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the {@link #user} to set
	 */
	public void setUser(ContextUser user) {
		this.user = user;
	}

	/**
	 * @return the {@link #userRoles}
	 */
	public Set<ContextUserRole> getUserRoles() {
		return userRoles;
	}

	/**
	 * @param userRoles
	 *            the {@link #userRoles} to set
	 */
	public void setUserRoles(Set<ContextUserRole> userRoles) {
		this.userRoles = userRoles;
	}

	/* Overridden (non-Javadoc) */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		if (!(obj instanceof ContextSecurity)) {
			return false;
		}
		ContextSecurity other = (ContextSecurity) obj;
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
