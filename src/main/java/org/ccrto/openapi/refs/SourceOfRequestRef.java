package org.ccrto.openapi.refs;

import java.io.Serializable;

/**
 * 
 * SourceOfRequestRef: link to the source of request that holds information
 * about another entity (source)
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface SourceOfRequestRef extends Serializable {

	/**
	 * Unique identifier for the source of request
	 * 
	 * Mandatory in API msg: Yes in request and response
	 * 
	 * @return Unique identifier for the source of request
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
	 * Involvement source used to define the source of request
	 * 
	 * Mandatory in API msg: No
	 * 
	 * @return Involvement source used to define the source of request
	 */
	String getSource();

	/**
	 * Set Unique identifier the source of request
	 * 
	 * @param id
	 *            to set Unique identifier the source of request
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
	 * Set Involvement source used to define the source of request
	 * 
	 * @param source
	 *            to set Involvement source used to define the source of request
	 */
	void setSource(String source);

}
