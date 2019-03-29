package org.ccrto.openapi.core.refs;

import java.io.Serializable;

/**
 * 
 * CaseRef: link to the resource that holds information about another entity
 * (case)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface CaseRef extends Serializable {

	/**
	 * Unique identifier for the case entity
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return Unique identifier for the case entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the case entity
	 * information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         case entity information
	 */
	String getHref();

	/**
	 * Set Unique identifier for the case entity
	 * 
	 * @param id
	 *            to set Unique identifier for the case entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the case
	 * entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the case entity information
	 */
	void setHref(String href);

}
