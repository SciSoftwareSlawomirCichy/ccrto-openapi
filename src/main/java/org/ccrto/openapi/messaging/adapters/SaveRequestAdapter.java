package org.ccrto.openapi.messaging.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import org.ccrto.openapi.messaging.SaveRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveRequestAdapter extends XmlAdapter<SaveRequest, SaveRequest> {

	private static final Logger logger = LoggerFactory.getLogger(SaveRequestAdapter.class);

	@Override
	public SaveRequest marshal(SaveRequest v) throws Exception {
		logger.info("--->marshal");
		return v;
	}

	@Override
	public SaveRequest unmarshal(SaveRequest adapted) throws Exception {
		logger.info("--->unmarshal");
		return adapted;
	}

}
