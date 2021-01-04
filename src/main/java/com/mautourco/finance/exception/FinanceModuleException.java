package com.mautourco.finance.exception;

public class FinanceModuleException extends RuntimeException {

	public FinanceModuleException(String message)

	{
		super(message);
	}

	public FinanceModuleException(String message, Throwable cause) {
		super(message, cause);
	}

	public FinanceModuleException(Throwable cause) {
		super(cause);
	}

}
