# What is wrong and how can be improved:
## Quite Big Mistakes:
1. Field Injection is Evil
2. using @Data on JPA entities is not advised (https://thorben-janssen.com/lombok-hibernate-how-to-avoid-common-pitfalls/)
3. using better suited types - like `java.util.Currency` class
4. AssetAdapter class - 
   1. An Adapter pattern acts as a connector between two incompatible interfaces that otherwise cannot be connected directly. 
   So no reason o use it, as it does not provide any additional value (so it is an adapter to AssetDto, which is totally unnecessary)
5. Working with time - 
   1. should use Clock in order to test it more conveniently
   2. should set Clock as a global bean with UTC as a timezone
6. use @Builder for simpler object creation for DTOs (maybe for Entities as well)
7. AssetService interface
   1. Unjustified usage of an interface with single implementation is plain wrong since this violates YAGNI
   2. Entity/DTO mapping should not be part of Service - single responsibility principle
   3. `Long getId(Asset asset)` - no point having this method since just receives an object and returns it's field
8. Naming is off - CollateralObject, CollateralObjectController
9. Controller
   1. add /api/v1 to the path to common path
   2. should have common `/collateralDtos` path on top
   3. methods should not return `HttpEntity`, but more narrowed down class as `ResponseEntity`, or just a DTO 
   4. by REST convention, POST should return Location header for the created resource 
   5. Controller should not have any logic for response. This should be handled in service, throwing an exception. 
   Exceptions then should be handled by @ExceptionHandler providing desired status code and error message
10. ExternalApproveService
    1. Imho, class should be redesigned. Should be handled on Design Pattern level - like with Command pattern - 
       1. having a handler interface and multiple implementations, one of which should be Asset
    2. Constants should be parametrized in config file (properties)
    3. Validation should be handled by Bean Validation Api (@NotNull set in dto)
    4. no need in codes. should throw an exception and exception handled by @ExceptionHandler
11. Response should show descriptive error message
12. Using real to world database instead of in memory for prod and test 
13. adding `final` for immutability for variables
14. SQL script - `NOT NULL` could be added as additional safety gate
15. Add logging since now when negative path occurs due to business req, then it's not clear what is going on. 
Was there a request made or not, what was the issue


## Minor
1. Single class imports instead of importing everything
2. using `var` for types
4. using build.gradle.kts in Kotlin 
5. using yml for spring configuration 
6. add Swagger doc
7. organize code more DDD way 
8. Missing configuration for test
