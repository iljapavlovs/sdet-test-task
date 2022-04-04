# Collateral Service

### Test Infrastructure
* Self-contained tests which spin up the application in Docker containers using [Testcontainers](https://www.testcontainers.org/)
* Framework uses Cucumber version 7 with Junit 5 support
* Tests can be found at `src/e2e-test/resources/co/copper/testtask/Collateral.feature`

### Running Tests
```bash
./gradlew clean bootJar e2e-test allureReport -Dapplication.env=local
```

### Reporting
[Allure](http://allure.qatools.ru/) report by default is generated at **`build/reports/allure-report/allureReport/index.html`**

#### Running application separately for local development
1. Build the project
```bash
./gradlew clean build -x test
```

2. Run project via docker
```bash
docker compose up --build
```


## For Tests, Improvements, etc go to ![docs] 



