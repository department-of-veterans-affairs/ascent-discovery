package gov.va.ascent.discovery.runner;

import org.junit.runner.RunWith;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, format = { "pretty",

		"html:target/site/cucumber-pretty", "json:target/cucumber.json" }, features = {

				"src/inttest/resources/gov/va/ascent/feature/discoverystatus.feature",
				"src/inttest/resources/gov/va/ascent/feature/register-deregister-eurekaservice.feature",

				
				},

		glue = { "gov.va.ascent.steps" })
public class AscentRunner extends AbstractTestNGCucumberTests {

	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("Before run------------------------");
	}

}
