# Framework Improvements and TODOs
1. Report is not generated with full steps due to compatibility issues in Cucumber 7 and Allure, but Cucumber 6 with Allure can be checked at a specific commit
2. DI could be used for caring less about object lifecycle mgmt - Cucumber and Guice example can be checked at a specific commit
3. Add unit and integration tests, including integration tests by slices and full context
4. Contract tests could be added, but there is no much need as there is no consumer side. 
 However, lets assume that consumer has written the contract or provider decided on the contract on their own for future consumers then it makes sense. 
5. Parallel tests can be added
6. Docker image could be optimized by using Buildpack (avoiding fat jars)
7. Add new model layer for Cucumber/Gherkin and convert to dtos via mappers
8. dates could be provided like `[date, minusYears=10, format='yyyy']` from Gherkin. Add parser for such expressions
9. Add performance tests 
10. Add Security tests
11. Add CI/CD pipeline with Github Actions
12. Add K8s and Helm
13. Add static code analysis, formatting tools
14. Git commits could have been smaller and frequent
