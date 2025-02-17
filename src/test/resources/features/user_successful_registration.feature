Feature: User Registration

  Scenario Outline: Register a user with valid credentials
    Given the following user details:
      | username  | password   |
      | <username> | <password> |
    When I register the user with username "<username>" and password "<password>"
    Then the registration is successful

    Examples:
      | username  | password  |
      | testuser  | secret123 |
      | john      | pass456   |
