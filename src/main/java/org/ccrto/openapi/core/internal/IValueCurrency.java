package org.ccrto.openapi.core.internal;

import org.ccrto.openapi.core.Context;

public interface IValueCurrency extends IValueObject, IValueWithEncodedFlag {

	Double toDouble(String systemName);

	Double toDouble(Context context);

	String getCode(String systemName);

	String getCode(Context context);
}
