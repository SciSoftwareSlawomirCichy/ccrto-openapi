package org.ccrto.openapi.refs;

import java.io.Serializable;

/**
 * 
 * InvolvementIdentificationRef: link to the resource that holds information
 * about another entity (user) with relationship to the permission. An
 * individual (party) could have multiple users associated (as employee, as
 * customer, …)
 * 
 * TMF672 Release 17.0.1 November 201
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface InvolvementIdentificationRef extends Serializable {

	/**
	 * Unique identifier for the partyUser entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the partyUser entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the party
	 * entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         party entity information
	 */
	String getHref();

	/**
	 * Name of the partyUser
	 * 
	 * Mandatory in API msg: No
	 * 
	 * @return Name of the partyUser
	 */
	String getName();

	/**
	 * Set Unique identifier for the partyUser entity
	 * 
	 * @param id
	 *            to set Unique identifier for the partyUser entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the party
	 * entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the party entity information
	 */
	void setHref(String href);

	/**
	 * Set Name of the partyUser
	 * 
	 * @param name
	 *            to set Name of the partyUser
	 */
	void setName(String name);

}
