package gov.va.ascent.discovery.runner;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@RunWith(Cucumber.class)
@CucumberOptions(strict = false, plugin = {"pretty",
		"html:target/site/cucumber-pretty", "json:target/cucumber.json"}, 
		features = {"src/inttest/resources/gov/va/ascent/discovery/feature"},
		glue = "gov.va.ascent.discovery.steps")
public class AscentRunner extends AbstractTestNGCucumberTests {

	final Logger LOGGER = LoggerFactory.getLogger(AscentRunner.class);
	
	@BeforeSuite(alwaysRun = true)
	public void setUp() throws Exception {
		LOGGER.debug("setUp method");
	}

}
