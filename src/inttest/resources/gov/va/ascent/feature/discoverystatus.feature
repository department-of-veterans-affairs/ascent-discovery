Feature: Log in to Discovery service to check the service is up 

@discoverystatus
  Scenario: Log in to discovery service to check the status 
      Given I pass the header information for discovery service
      | Pragma       | no-cache        |
      When user makes a request to Discovery URL
      Then the response code must be for discovery service 200
    