# README #

This document provides the details of **Ascent Discovery Acceptance test** .

## Acceptance test for ascent Discovery ##
Acceptance test are created to make sure the core services in ascent discovery are working as expected. If the developers make any changes to ascent discovery service this acceptance test will make sure any existing service Is not broken with new updated changes. It has a dependency to ascent test framework. 

This project uses Java - Maven platform, the REST-Assured jars for core API validations.

## Project Structure ##

Resources: This folder has JSON files for Request and Response for API calls.

Request – This folder contains request JSON files if the body of your API call is static and needs to be sent as a JSON file / XML file.
Response – This folder contains response files that you need to compare JSON to JSON, you can store the Response files in this folder.
gov/va/ascent/features - This is where you will create the cucumber feature files that contain the Feature and Scenarios for the discovery service you are testing.

log4j - File used to store all the log4j properties.

src/inttest/java/gov/va/ascent/discovery/steps- The implementation steps related to the feature and scenarios mentioned in the cucumber file for the API needs to be created in this location.

src/inttest/java/gov/va/ascent/discovery/runner - Cucumber runner class that contains all feature file entries that needs to be executed at runtime. The annotations provided in the cucumber runner class will assist in bridging the features to step definitions.
vetapi.properties – configuration properties such as URL are specified here.

## Execution ##
**Command Line:** Use this command to execute the discovery acceptance test. mvn verify –Pinttest 

Use this command to execute for different environment.

mvn verify -Pinttest -Dtest.env=env -DX-Vault-Token=token

Note: env is the environment 

The parameter X-Vault-Token is not valid for local environment. It is passed thru pipeline. 
