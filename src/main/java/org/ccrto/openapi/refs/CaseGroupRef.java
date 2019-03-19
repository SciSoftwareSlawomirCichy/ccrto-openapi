package org.ccrto.openapi.refs;

import java.io.Serializable;

/**
 * 
 * CaseGroupRef: link to the resource that holds information about another
 * entity (caseGroup)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface CaseGroupRef extends Serializable {

	/**
	 * Unique identifier for the case' group entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the case' group entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the case' group
	 * entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         case' group entity information
	 */
	String getHref();

	/**
	 * Set Unique identifier for the case' group entity
	 * 
	 * @param id
	 *            to set Unique identifier for the case' group entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the case'
	 * group entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the case' group entity information
	 */
	void setHref(String href);

}
