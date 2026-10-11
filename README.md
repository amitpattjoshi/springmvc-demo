# Spring Boot Demo

This is a minimal Spring Boot project using the latest stable version (3.2.5) with Java 17.

## Features
- Basic REST controller at `/hello`
- Maven-based build (pom.xml)
- application.properties for configuration

## Getting Started

1. Build the project:
   ```sh
   mvn clean package
   ```
2. Run the application:
   ```sh
   mvn spring-boot:run
   ```
3. Access the endpoint:
   - [http://localhost:8080/hello](http://localhost:8080/hello)

## Build and Docker image

Build the jar locally:

```sh
mvn clean package
```

Build the Docker image locally:

```sh
docker build -t springmvc-demo:latest .
docker run -p 8080:8080 springmvc-demo:latest
```

## GitHub Actions CI/CD

This repository includes a GitHub Actions workflow at `.github/workflows/ci-docker.yml` that:

- runs `mvn test` on push and pull requests
- packages the application with Maven
- builds and pushes a Docker image to GitHub Container Registry on `main`/`master` pushes

To push to GitHub:

```sh
git add .
git commit -m "Add CI/CD pipeline and Docker build"
git push origin master
```

If your default branch is `main`, replace `master` with `main`.
