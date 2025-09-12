# Selenium Grid Demo with Axe Watcher

## Prerequisites

- Java 17+
- Maven
- Docker Desktop (with Docker Compose)

## Project Structure

- At least two test files: `Test1.java` and `Test2.java`
- A `Base` class with `@BeforeMethod` and `@AfterMethod` for WebDriver and Axe Watcher setup/teardown
- `docker-compose.yml` for Selenium Grid (hub + Chrome node)
- `testng.xml` for parallel test execution

## Setup Instructions

1. **Create a Maven Project**

    - Initialize a Maven project with your preferred IDE or via command line.
    - Add dependencies for Selenium, TestNG, WebDriverManager, and Axe Watcher in `pom.xml`.

2. **Base Class Setup**

    - Implement a `Base` class with `@BeforeMethod` and `@AfterMethod` to handle WebDriver and Axe Watcher initialization and cleanup.

3. **Install Docker Desktop and Docker Compose**

    - Download and install Docker Desktop from [https://www.docker.com/products/docker-desktop/](https://www.docker.com/products/docker-desktop/).

4. **Create `docker-compose.yml`**

    - Add a file named `docker-compose.yml` with Selenium Hub and Chrome node configuration.

5. **Start Selenium Grid**

    - Open a terminal in your project directory.
    - Run:
      ```
      docker compose up
      ```
    - You should see Selenium containers running in Docker Desktop.

6. **Access Selenium Grid UI**

    - Open your browser and go to [http://localhost:4444/ui](http://localhost:4444/ui) to view running sessions and node status.

7. **Configure TestNG for Parallel Execution**

    - Create a `testng.xml` file to define your test suite and enable parallel execution of tests.

8. **Run Your Tests**

    - Use Maven to execute your tests:
      ```
      mvn clean test
      ```

## Example Files

- `docker-compose.yml` (Selenium Hub + Chrome node)
- `testng.xml` (parallel test configuration)
- `Base.java` (WebDriver and Axe Watcher setup)
- `Test1.java`, `Test2.java` (sample tests)

---

Follow these steps to set up and run your Selenium Grid tests with Axe Watcher integration.# axeWatcher-seleniumGrid