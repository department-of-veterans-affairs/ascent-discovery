package gov.va.ascent.steps;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.va.ascent.util.BaseStepDef;


public class DiscoveryStatus extends BaseStepDef {

	final Logger log = LoggerFactory.getLogger(DiscoveryStatus.class);
	private String discoveryURL;
	@Before({ "@discoverystatus" })
	public void setUpREST() {
		initREST();
		discoveryURL = restConfig.getPropertyName("discoveryURL");
	}

	@Given("^I pass the header information for discovery service$")
	public void passTheHeaderInformationForDiscovery(Map<String, String> tblHeader) throws Throwable {
		passHeaderInformation(tblHeader);
	}

	@When("^user makes a request to Discovery URL$")
	public void makerequesustoappsurlDelete() throws Throwable {
		invokeAPIUsingGet(discoveryURL, false);
	}
	@Then("^the response code must be for discovery service (\\d+)$")
	public void serviceresposestatuscodemustbe(int intStatusCode) throws Throwable {
		ValidateStatusCode(intStatusCode);
	}


	@After({ "@discoverystatus" })
	public void cleanUp(Scenario scenario) {
		postProcess(scenario);
	}

}
