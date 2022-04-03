@docker
Feature: Collateral

  Scenario: Create and get Collateral

    When REST - create new Collateral - verify response = true
      | name  | year | currency | value     | type  |
      | house | 2022 | EUR      | 100000000 | asset |
      | yacht | 2019 | EUR      | 100000000 | asset |

    Then REST - get Collaterals
      | id | name  | year | currency | value        | type  |
      | 1  | house | 2022 | EUR      | 100000000.00 | asset |
      | 2  | yacht | 2019 | EUR      | 100000000.00 | asset |

