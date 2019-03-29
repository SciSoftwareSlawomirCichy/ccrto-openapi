package org.ccrto.openapi.core.refs;

import java.io.Serializable;

/**
 * 
 * CaseRef: link to the resource that holds information about another entity
 * case' type definition (caseType)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface CaseTypeRef extends Serializable {

	/**
	 * Unique identifier for the case' type definition entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the case' type definition entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the type
	 * definition entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         type definition entity information
	 */
	String getHref();

	/**
	 * Set Unique identifier for the type definition entity
	 * 
	 * @param id
	 *            to set Unique identifier for the type definition entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the type
	 * definition entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the type definition entity information
	 */
	void setHref(String href);

}
