package gov.va.ascent.discovery.util;

import gov.va.ascent.test.framework.service.RESTConfigService;

public class DiscoveryAppUtil {
	
	private DiscoveryAppUtil() {
		
	}
	
	public static String getBaseURL() {
		return RESTConfigService.getBaseURL("data.username", "data.password");
		
	}
	
}
