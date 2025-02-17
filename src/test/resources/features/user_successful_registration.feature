Feature: User Registration

  Scenario Outline: Register a user with valid credentials
    Given the following user details:
      | username  | password   |
      | <username> | <password> |
    When I register the user with username "<username>" and password "<password>"
    Then the registration should be successful
    And I fetch the user with username "<username>"
    Then the fetched user should have password "<password>"

    Examples:
      | username  | password  |
      | testuser  | secret123 |
      | john      | pass456   |
