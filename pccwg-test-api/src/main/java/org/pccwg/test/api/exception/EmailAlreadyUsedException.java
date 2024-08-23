package org.pccwg.test.api.exception;

public class EmailAlreadyUsedException extends Exception {

	private static final long serialVersionUID = 1L;

	public EmailAlreadyUsedException(String err) {
		super(err);
	}
}
