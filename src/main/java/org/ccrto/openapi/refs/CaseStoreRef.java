package org.ccrto.openapi.refs;

import java.io.Serializable;

/**
 * 
 * CaseStoreRef: link to the resource that holds information about another
 * entity (caseStore)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface CaseStoreRef extends Serializable {

	/**
	 * Unique identifier for the case' store entity
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the case' store entity
	 */
	String getId();

	/**
	 * A resource URI pointing to the resource in the OB that stores the case' store
	 * entity information
	 * 
	 * Mandatory in API msg: Yes in response
	 * 
	 * @return A resource URI pointing to the resource in the OB that stores the
	 *         case' store entity information
	 */
	String getHref();

	/**
	 * Set Unique identifier for the case entity
	 * 
	 * @param id
	 *            to set Unique identifier for the case' store entity
	 */
	void setId(String id);

	/**
	 * Set a resource URI pointing to the resource in the OB that stores the case'
	 * store entity information
	 * 
	 * @param href
	 *            to set a resource URI pointing to the resource in the OB that
	 *            stores the case' store entity information
	 */
	void setHref(String href);

}
