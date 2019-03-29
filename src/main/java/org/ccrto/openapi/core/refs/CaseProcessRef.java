package org.ccrto.openapi.core.refs;

import java.io.Serializable;

/**
 * 
 * CaseProcessRef: link to the resource that holds information about another
 * entity instance of process (caseProcess)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface CaseProcessRef extends Serializable {

	/**
	 * Unique identifier for the case' instance of processinstance of process entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the case' process instance entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the case'
	 * instance of process entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         case' instance of process entity information
	 */
	String getHref();

	/**
	 * Set Unique identifier for the case' instance of process entity
	 * 
	 * @param id
	 *            to set Unique identifier for the case' instance of process entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the case'
	 * instance of process entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the case' instance of process entity information
	 */
	void setHref(String href);

}
