package org.ccrto.openapi.core.internal;

import java.math.BigDecimal;

import org.ccrto.openapi.core.Context;

public interface IValueNumber extends IValueObject {

	Double toDouble(String systemName);

	Double toDouble(Context context);

	BigDecimal toBigDecimal(String systemName);

	BigDecimal toBigDecimal(Context context);

}
