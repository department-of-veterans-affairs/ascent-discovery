package gov.va.ascent.discovery.steps;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.va.ascent.discovery.util.AppUtil;
import gov.va.ascent.test.framework.restassured.BaseStepDef;


public class RegisterService extends BaseStepDef {

	final Logger log = LoggerFactory.getLogger(RegisterService.class);
	private String discoveryURL;
	@Before({ "@registertestservice" })
	public void setUpREST() {
		initREST();
		discoveryURL = AppUtil.getBaseURL();
	}
	

	@Given("^I pass the header information for discovery$")
	public void passTheHeaderInformationForDiscovery(Map<String, String> tblHeader) throws Throwable {
		passHeaderInformation(tblHeader);
	}

	@When("^user makes a request to apps register URL \"([^\"]*)\" and \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String serviceURL, String requestFile) throws Throwable {
		resUtil.setUpRequest(requestFile, headerMap);
		
		invokeAPIUsingPost( discoveryURL+serviceURL,false);
		//strResponse = resUtil.POSTResponse(strURL);
	}

	@Then("^the response code must be for registered service (\\d+)$")
	public void serviceresposestatuscodemustbe(int intStatusCode) throws Throwable {
		validateStatusCode(intStatusCode);
	}


	@When("user makes a request to verify instance URL \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String serviceURL) throws Throwable {
		
		invokeAPIUsingGet( discoveryURL+serviceURL,false);
	}

	@Then("^test service is registered in eureka after register \"([^\"]*)\"$")
	public void currentServicesInEureka(String appName) throws Throwable {
		String rootXml = resUtil.parseXML(strResponse, "applications");
		assertTrue(rootXml.contains(appName));
	}
	

	@After({ "@registertestservice" })
	public void cleanUp(Scenario scenario) {
		postProcess(scenario);
	}
}
