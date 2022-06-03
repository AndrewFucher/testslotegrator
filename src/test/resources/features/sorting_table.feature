Feature: table with players correctly sorted

  Scenario Outline:
    Given on table page with players with creds "admin1" and "[9k<k8^z!+$$GkuP"
    When sorting table by column "<col>"
    Then table sorted correctly by column "<col>"

    Examples:
      | col      |
      | Username |
