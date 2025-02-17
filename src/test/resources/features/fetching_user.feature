Feature: Getting user from database

  Scenario: I fetch the registered user and receive correct details

    Given I register the user with username "new_user" and password "pass"
    Given the registration is successful
    When I fetch the user with username "new_user"
    Then the fetched user should have password "pass"

  Scenario: I attempt to fetch the user that has not been registered before
  and I received the error message
    When I fetch the user with username "not_existing_user" that has not been registered before
    Then I got the error response with message "No user with username: not_existing_user"



