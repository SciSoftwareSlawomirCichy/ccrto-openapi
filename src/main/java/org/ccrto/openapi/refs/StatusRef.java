package org.ccrto.openapi.refs;

import java.io.Serializable;

/**
 * 
 * StatusRef: link to the resource that holds information about another entity
 * (status)
 * 
 * TMF672 Release 17.0.1 November 2017
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface StatusRef extends Serializable {

	/**
	 * Unique identifier for the status entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the status entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the status
	 * entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         status entity information
	 */
	String getHref();

	/**
	 * Involvement role used to define the status
	 * 
	 * Mandatory in API msg: No
	 * 
	 * @return Name of the partyUser
	 */
	String getStatus();

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
	 * Set Involvement role used to define the status
	 * 
	 * @param role
	 *            to set Involvement role used to define the status
	 */
	void setStatus(String role);

}
