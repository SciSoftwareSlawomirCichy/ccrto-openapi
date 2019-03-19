package org.ccrto.openapi.entites;

import org.ccrto.openapi.refs.InvolvementIdentificationRef;

/**
 * 
 * UserData - dane użytkownika
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public interface UserData extends InvolvementIdentificationRef {

	String getLocale();

	String getTimeZone();

	void setLocale(String locale);

	String setTimeZone(String timeZone);

}
