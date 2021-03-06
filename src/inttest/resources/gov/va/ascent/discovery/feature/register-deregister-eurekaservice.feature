Feature: Register and delete a service with eureka and verify the status 

  @registertestservice
  Scenario Outline: Register service with eureka and verify the status
    Given I pass the header information for discovery
      | Content-Type | application/xml |
      | Pragma       | no-cache        |
    When user makes a request to apps register URL "<ServiceURL>" and "<RequestFile>"
    Then the response code must be for registered service 204

    Examples:
    | ServiceURL | RequestFile | 
    | /eureka/apps/TestApp | register-eureka-service.xml | 

    @deleteservice
  Scenario Outline: Delete the registered service with eureka and verify the ststus
    Given I pass header information for discovery
      | Content-Type | application/xml |
      | Pragma       | no-cache        |
    When user makes a request to apps delete URL "<ServiceURL>"
    Then the response code must be for deleted service 200

    Examples:
    | ServiceURL | 
    | /eureka/apps/TestApp/localhost:test-app:8760 | 
    