Feature: Collaterals

  Scenario: Create and get Collateral - happy path

    When REST - create new Collateral - verify response = true
      | name   | year | currency | value                | type  |
      | house  | 2022 | EUR      | 1000000              | asset |
      | yacht  | 2010 | USD      | 500000000.99         | asset |
      | boat   | 2000 | WST      | 99999999999          | asset |
      | island | 2000 | MXN      | 99999999999999999.99 | asset |

    Then REST - get Collaterals
      | name   | year | currency | value                | type  |
      | house  | 2022 | EUR      | 1000000.00           | asset |
      | yacht  | 2010 | USD      | 500000000.99         | asset |
      | boat   | 2000 | WST      | 99999999999.00       | asset |
      | island | 2000 | MXN      | 99999999999999999.99 | asset |


  # example of DDT
  # usually should be part of e2e as this is part of business logic
  Scenario Template: Create Collateral with <predicate> earlier than "<settingType>" setting

    When REST - create new Collateral - verify response = false
      | name  | year             | currency | value        | type  |
      | house | <assetYearValue> | EUR      | <assetValue> | asset |

    Then Response - HTTP Status - BAD REQUEST

    Examples:
      | predicate | settingType     | assetYearValue | assetValue |
      | year date | min-asset-year  | 1999           | 100000000  |
      | value     | min-asset-value | 2022           | 999999.99  |

  # these kind of test SHOULD NOT BE PART OF E2E as there are low level!!!
  # and should be verified in int tests (by Spring boot slices or full context - @SpringBootTest)
  Scenario Template: Create Collateral with not providing <field> field

    When REST - create new Collateral - verify response = false
      | name        | year        | currency        | value        | type        |
      | <nameValue> | <yearValue> | <currencyValue> | <valueValue> | <typeValue> |
    Then Response - HTTP Status - BAD REQUEST

    Examples:
      | field | nameValue | yearValue | currencyValue | valueValue | typeValue |
      | year  | house     |           | EUR           | 100000000  | asset     |
      | value | house     | 2019      | EUR           |            | asset     |
#      | currency | house     | 2019      |               | 100000000  | asset     | TODO - add to the app - currency must be non nullable
#      | name     |           | 2019      | EUR           | 100000000  | asset     | TODO - add to the app - name must be non nullable

  # these kind of test SHOULD NOT BE PART OF E2E as there are low level!!!
  # and should be verified in int tests (by Spring boot slices or full context - @SpringBootTest)
  Scenario Template: Create Collateral - incorrect or corrupted data for <field>

    When REST - create new Collateral - verify response = false
      | name        | year        | currency        | value        | type        |
      | <nameValue> | <yearValue> | <currencyValue> | <valueValue> | <typeValue> |
    Then Response - HTTP Status - INTERNAL SERVER ERROR

    Examples:
      | field | nameValue | yearValue | currencyValue | valueValue | typeValue |
      | value    | house     | 2019      | EUR           | 100000000000000000.00 | asset     |
#      | value    | gold      | 2019      | EUR           | 100000.00             | commodity | TODO - need to send JSON without serializing
#      | value    | house     | 2019      | EUR           | 1000000,00            | asset     | TODO - need to send JSON without serializing
#      | currency | house     | 2019      | XXXY          | 1000000.00            | asset     | TODO - need to send JSON without serializing
#      | year     | house     | 2999      | EUR           | 100000000             | asset     | TODO - year of the asset should not be in the future

#  todo - also need to check for long string for 'name', prohibited chars
