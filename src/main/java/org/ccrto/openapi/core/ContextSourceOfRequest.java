package org.ccrto.openapi.core;

import org.ccrto.openapi.core.refs.SourceOfRequestRef;

/**
 * 
 * ContextSourceOfRequest
 *
 * @author Sławomir Cichy &lt;slawas@scisoftware.pl&gt;
 * @version $Revision: 1.1 $
 *
 */
public class ContextSourceOfRequest implements SourceOfRequestRef {

	private static final long serialVersionUID = 346790192048876875L;
	private String id;
	private String href;
	private String source;

	/**
	 * @return the {@link #id}
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the {@link #id} to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the {@link #href}
	 */
	public String getHref() {
		return href;
	}

	/**
	 * @param href
	 *            the {@link #href} to set
	 */
	public void setHref(String href) {
		this.href = href;
	}

	/**
	 * @return the {@link #source}
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the {@link #source} to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

}
