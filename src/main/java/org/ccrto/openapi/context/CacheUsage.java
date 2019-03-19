package org.ccrto.openapi.context;

/**
 * 
 * CacheUsage
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $ 
 *
 */
public enum CacheUsage {

	/**
	 * Nie używaj cache podczas pobierania elementu.
	 */
	NONE,
	/**
	 * Użyj cache podczas pobierania elementu.
	 */
	TO_USE,
	/**
	 * Użyj cache, ale odśwież element tam się znajdujący.
	 */
	REFRESH;
	
}
