Feature: Check if sign in works

  Scenario: Sign in attempt
    Given starting page
    When user signing in with credentials "admin1" and "[9k<k8^z!+$$GkuP"
    Then user successfully signed in