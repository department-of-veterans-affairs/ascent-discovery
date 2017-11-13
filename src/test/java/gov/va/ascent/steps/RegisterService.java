package gov.va.ascent.steps;

import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.va.ascent.util.BaseStepDef;
import gov.va.ascent.util.RESTConfig;

public class RegisterService extends BaseStepDef {

	final Logger log = LoggerFactory.getLogger(RESTConfig.class);

	@Before({ "@registertestservice" })
	public void setUpREST() {
		initREST();
	}

	@Given("^I pass the header information for discovery$")
	public void passTheHeaderInformationForDiscovery(Map<String, String> tblHeader) throws Throwable {
		passHeaderInformation(tblHeader);
	}

	@When("^user makes a request to apps register URL \"([^\"]*)\" and \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String strURL, String requestFile) throws Throwable {
		resUtil.setUpRequest(requestFile, headerMap);
		invokeAPIUsingPost(strURL, "discoveryURL");
		//strResponse = resUtil.POSTResponse(strURL);
	}

	@Then("^the response code must be for registered service (\\d+)$")
	public void serviceresposestatuscodemustbe(int intStatusCode) throws Throwable {
		ValidateStatusCode(intStatusCode);
	}


	@When("user makes a request to verify instance URL \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String strURL) throws Throwable {
		invokeAPIUsingGet(strURL, "discoveryURL");
	}

	@Then("^test service is registered in eureka after register \"([^\"]*)\"$")
	public void currentServicesInEureka(String appName) throws Throwable {
		String rootXml = resUtil.parseXML(strResponse, "applications");
		assertTrue(rootXml.contains(appName));
	}
	
	@And("test service \"([^\"]*)\" is registered in eureka \"([^\"]*)\" after register")
	public void currentServicesInEurekaAfter(String appName, String appUrl) throws Throwable {
		invokeAPIUsingGet(appUrl, "discoveryURL");
		String rootXml = resUtil.parseXML(strResponse, "applications");
		assertTrue(rootXml.contains(appName));
	}
	

	@After({ "@registertestservice" })
	public void cleanUp(Scenario scenario) {
		postProcess(scenario);
	}

}
