package org.ccrto.openapi.core.internal;

import java.util.Calendar;

import org.ccrto.openapi.core.Context;

public interface IValueDate extends IValueObject {

	Calendar toCalendar(String systemName);
	
	Calendar toCalendar(Context context);
	
}
