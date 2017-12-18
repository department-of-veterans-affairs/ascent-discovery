# README #

This document provides the details of **Ascent Discovery Acceptance test** .

## Acceptance test for Ascent Discovery ##
Acceptance test are created to make sure the the ascent discovery service works when deployed. If the developers modify codebase in ascent discovery service, this acceptance test will ensure that the service isn't broken with new updated changes. The project has a dependency on ascent test framework. 

Project uses Java - Maven platform, the REST-Assured jars for Core API validations.

## Project Structure ##

src/inttest/resources - This folder has JSON files for Request and Response for API calls.

src/inttest/resources/Request – This folder contains request  files if the body of your API call is static and needs to be sent as a XML/JSON file.

src/inttest/resources/Response – This folder contains response files that you may need to compare output, you can store the Response files in this folder. EMPTY for this project

src/inttest/resources/logback-test.xml - Logback Console Appender pattern and loggers defined for this project

src/inttest/gov/va/ascent/features - This is where you will create the cucumber feature files that contain the Feature and Scenarios for the discovery service you are testing.

src/inttest/java/gov/va/ascent/discovery/steps- The implementation steps related to the feature and scenarios mentioned in the cucumber file for the API needs to be created in this location.

src/inttest/java/gov/va/ascent/discovery/runner - Cucumber runner class that contains all feature file entries that needs to be executed at runtime. The annotations provided in the cucumber runner class will assist in bridging the features to step definitions.

src/inttest/resources/config/vetsapi-ci.properties – CI configuration properties such as URL are specified here.

src/inttest/resources/config/vetsapi-stage.properties – STAGE configuration properties such as URL are specified here.

## Execution ##
**Command Line:** Use below command(s) to execute the discovery acceptance test. 

Default Local: mvn verify –Pinttest 

Use below sample commands to execute for different environment: 
  
CI : mvn -Ddockerfile.skip=true integration-test -Pinttest -Dtest.env=ci -DX-Vault-Token=<<token>> -DbaseURL=https://ci.internal.vets-api.gov:8761
  
CI: mvn -Ddockerfile.skip=true integration-test -Pinttest -DX-Vault-Token=<<token>> -DbaseURL=https://ci.internal.vets-api.gov:8761 -Dvault.url=https://vault.internal.vets-api.gov:8200/v1/secret/ascent-discovery
  
STAGE : mvn -Ddockerfile.skip=true integration-test -Pinttest -Dtest.env=stage -DX-Vault-Token=<<token>> -DbaseURL=https://ci.internal.vets-api.gov:8761
  
STAGE: mvn -Ddockerfile.skip=true integration-test -Pinttest -DX-Vault-Token=<<token>> -DbaseURL=https://stage.internal.vets-api.gov:8761 -Dvault.url=https://vault.internal.vets-api.gov:8200/v1/secret/ascent-discovery

The parameter X-Vault-Token is not valid for local environment. It is passed thru pipeline. 
