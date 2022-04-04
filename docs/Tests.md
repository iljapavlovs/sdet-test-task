# Tests

## Rationale Behind The Approach

#### On where test code should be located
Firstly, I am rooting against having tests outside of code (like any tms, jira), but should reside together with the codebase.
This applies for all levels of test type including contract, acceptance (e2e), security and performance tests.
Since codebase is versioned together with the tests and tests should run on every commit to trunk, then we can achieve releasing services independently which is one of the main problems in microservices realm where integration testing between services is key.

#### On using the same tech stack for tests as for codebase
In addition, preferably we need to use the same tech stack for tests as in which codebase is written. 
It makes much easier for QAs and devs work together and T-shape skill approach could be applied with less hassle.
> E.g. don't use RestAssured, use the same client which is used in codebase. 
It makes much easier for QAs and devs work together and T-shape skill approach could be applied with less hassle. 
> Also, instead of learning some high level DSL (like *RestAssured, CodeceptJs, Karate, etc*), you are learning a proper library which is intended for development purposes (*Retrofit, Feign, Axios, etc*).

Also, developers should have full accountability of their code starting with writing the code and tests to deployment and monitoring in PROD, 
so this makes up another motive for having tests written in the same tech stack as codebase.

#### On how to make tests stored in code more readable for not-tech people
In order to bridge the gap between developers and stakeholders for e2e we could use Cucumber and Gherkin for better readability for non-technical people, 
but then Gherkin steps should be designed with thought considering balance between being very low level and high level in its description of steps.

#### On test coverage or requirements traceability
Every test should also be annotated with corresponding requirement. This could be an Epic, Story.
By means of modern reporting solutions (e.g. Allure) we can even easily integrate reports with Jira or TMS if needed.

#### On Test Pyramid
Regarding test pyramid - not all below-mentioned tests should be part of e2e layer! 
Tests like verifying syntax of the requests and not business logic should be automated in unit/integration level in order to adhere to proper Test Pyramid approach.

## Test cases 

### Functional
1. Happy Path
    1. checking collateral is created
        1. check status code
        2. check conformity to REST conventions - response's status code 201, header has a Location to the created
           resource, other headers
    2. getting created collateral
        1. verifying ids are created in sequence and are unique
        2. check conformity to REST conventions - status code 200, headers
        3. verifying actual created collaterals
2. Negative Path in Business Logic (semantics) - usually server responds with 5xx error
   1. set `year` field earlier than `min-asset-year` setting
   2. set `value` field less than `min-asset-value` setting
   
3. Negative Path in syntax - usually server responds with 4xx error
   1. Payload with invalid model (violates schema)
      1. checking for nullability for nun-nullable fields
      2. testing with not allowed values, e.g. currency - non ISO 4217 standard values
      3. set `type` field with non `asset` value or null - **this was left out since I am working with data models in the framework, but could be added**
   2. with very big numbers or characters length
   3. not allowed characters
   4. getting non existent collateral
   5. Invalid values in HTTP headers
   6. Unsupported methods for endpoints
   7. etc
   
#### What should be fixed in the application
* currency must be non-nullable
* currency should confirm to ISO 4217 standard - **already fixed**
* name must be non-nullable
* assess date should be provided also from the client
* name should be unique
* add validation for future dates - for `year` field
* test data management issue - currently it is problematic to rerun the same test twice and verify `id`.
We are missing GET ALL endpoint. This way we could verify the ids from Gherkin as well.
* In order to support parallel execution, the best way is to add unique data (e.g. unique by name) and before the tests check if it exists,
if yes, then delete it. 
Of course, we could manage it via sql, but this proves to be harder to maintain and is more error-prone.

### Non-functional
* Performance
  * NFRs - throughput or active users
  * Types
    * Load
    * Stress
    * Endurance
    * Spike 
    * Volume
    * Scalability
* Security
  * OWASP ZAP - ZAP Api can be used for automation
