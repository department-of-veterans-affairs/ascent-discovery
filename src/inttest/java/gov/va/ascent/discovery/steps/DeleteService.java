package gov.va.ascent.discovery.steps;

import static org.junit.Assert.assertFalse;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.va.ascent.discovery.util.DiscoveryAppUtil;
import gov.va.ascent.test.framework.restassured.BaseStepDef;

public class DeleteService extends BaseStepDef {

	final Logger log = LoggerFactory.getLogger(DeleteService.class);
	private String discoveryURL;
	
	@Before({ "@deleteservice" })
	public void setUpREST() {
		initREST();
		discoveryURL = DiscoveryAppUtil.getBaseURL();
	}

	@Given("^I pass header information for discovery$")
	public void passTheHeaderInformationForDiscovery(Map<String, String> tblHeader) throws Throwable {
		passHeaderInformation(tblHeader);
	}

	@When("^user makes a request to apps delete URL \"([^\"]*)\"$")
	public void makerequesustoappsurlDelete(String serviceURL) throws Throwable {
		invokeAPIUsingDelete(discoveryURL+serviceURL, false);
	}

	@Then("^the response code must be for deleted service (\\d+)$")
	public void serviceresposestatuscodemustbe(int intStatusCode) throws Throwable {
		validateStatusCode(intStatusCode);
	}

	@When("user makes a request to verify instance URL after delete \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String serviceURL) throws Throwable {
		invokeAPIUsingGet(discoveryURL+serviceURL, false);
	}

	@Then("^test service is deleted in eureka \"([^\"]*)\"$")
	public void currentServicesInEureka(String appName) throws Throwable {
		String rootXml = resUtil.parseXML(strResponse, "applications");
		assertFalse(rootXml.contains(appName));
	}

	@After({ "@deleteservice" })
	public void cleanUp(Scenario scenario) {
		postProcess(scenario);
	}

}
