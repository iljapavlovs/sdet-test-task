# Collateral Service

### Running project
1. Build the project
```bash
./gradlew clean build -x test
```

2. Run project via docker
```bash
docker compose up --build
```


### Running self-contained tests in Docker containers
```bash
./gradlew clean bootJar e2e-test allureReport -Dapplication.env=local
```
**Check generated report at `build/reports/allure-report/allureReport/index.html`**






