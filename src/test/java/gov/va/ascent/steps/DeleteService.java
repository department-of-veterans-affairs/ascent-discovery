package gov.va.ascent.steps;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.restassured.path.json.JsonPath;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import gov.va.ascent.util.BaseStepDef;
import gov.va.ascent.util.RESTConfig;

public class DeleteService extends BaseStepDef {

	final Logger log = LoggerFactory.getLogger(RESTConfig.class);

	@Before({ "@deleteservice" })
	public void setUpREST() {
		initREST();
	}

	@Given("^I pass header information for discovery$")
	public void passTheHeaderInformationForDiscovery(Map<String, String> tblHeader) throws Throwable {
		passHeaderInformation(tblHeader);
	}

	@When("^user makes a request to apps delete URL \"([^\"]*)\"$")
	public void makerequesustoappsurlDelete(String strURL) throws Throwable {
		invokeAPIUsingDelete(strURL, "discoveryURL");
	}

	@Then("^the response code must be for deleted service (\\d+)$")
	public void serviceresposestatuscodemustbe(int intStatusCode) throws Throwable {
		ValidateStatusCode(intStatusCode);
	}

	@When("user makes a request to verify instance URL after delete \"([^\"]*)\"$")
	public void clientrequestGETwithjsondata(String strURL) throws Throwable {
		invokeAPIUsingGet(strURL, "discoveryURL");
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
