package org.ccrto.openapi.core.exceptions;

public class CcrtoException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8770597238002503333L;

	public CcrtoException() {
		super();
	}

	public CcrtoException(String message, Throwable cause) {
		super(message, cause);
	}

	public CcrtoException(String message) {
		super(message);
	}

	public CcrtoException(Throwable cause) {
		super(cause);
	}

}
