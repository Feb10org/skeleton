Feature: User Registration fails

  Scenario: Registering user with the same username twice should return an error
    Given the following user details:
      | username  | password   |
      | test      | secret     |
    When I register the user with username "test" and password "secret"
    And I register the user with username "test" and password "hello" for the second time
    Then the registration should fail with message "User already exists"
