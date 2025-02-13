Feature: Basic Service Addition

  As a user of the basic service,
  I want to be able to add two numbers,
  So that I get the correct result.

  Scenario: Add two numbers
    Given a basic service is available
    When I add 3 and 4
    Then the result should be 7