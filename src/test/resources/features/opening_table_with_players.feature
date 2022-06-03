Feature: Table with players loaded successfully

  Scenario: Load table with players page successfully from sidebar
    Given successful authorization with creds "admin1" and "[9k<k8^z!+$$GkuP"
    When going to table page with sidebar
    Then table with players successfully loaded

  Scenario: Load table with players page successfully by going to page with main page link
    Given successful authorization with creds "admin1" and "[9k<k8^z!+$$GkuP"
    When going to table page with link on main page
    Then table with players successfully loaded
