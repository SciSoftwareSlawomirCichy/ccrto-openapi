package org.ccrto.openapi.core.internal;

import java.math.BigInteger;

import org.ccrto.openapi.core.Context;

public interface IValueInteger extends IValueObject {

	Long toLong(String systemName);

	Long toLong(Context context);

	BigInteger toBigInteger(String systemName);

	BigInteger toBigInteger(Context context);

}
