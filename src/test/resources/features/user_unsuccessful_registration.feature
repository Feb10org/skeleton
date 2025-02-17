Feature: User Registration

  Scenario: Registering the same user twice should return an error
    Given the following user details:
      | username  | password   |
      | test      | secret     |
    When I register the user with username "test" and password "secret" the first time
    And I register the user with username "test" and password "secret" again
    Then the registration should fail with message "User already exists"
