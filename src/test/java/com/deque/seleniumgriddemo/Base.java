package com.deque.seleniumgriddemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.UUID;

public class Base {
    public WebDriver driver;
    public MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage");
        chromeOptions.addArguments("--no-sandbox");

        try {
          driver = new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        AxeWatcherOptions options = new AxeWatcherOptions()
                .setApiKey("474c5470-aaf8-41b5-8524-49a4ff05c25d")
                .setServerUrl("https://axe-qa.dequelabs.com");

        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
        WebDriverManager.chromedriver().setup();
        chromeOptions = watcher.configure(chromeOptions);

        // Wrap the WebDriver with Axe Watcher
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(90));
        driver.manage().window().maximize();
        driver.get("https://www.jetbrains.com/");
        mainPage = new MainPage(driver);
    }
    // Set up WebDriver using WebDriverManager


//    @AfterMethod
//    public void tearDown() {
//        if (driver instanceof AxeWatcherDriver) {
//            ((AxeWatcherDriver) driver).axeWatcher().flush();
//        }
//        driver.quit();
//    }
@AfterMethod(alwaysRun = true)
public void tearDown() {
    if (driver != null) {
        // Run Axe Watcher flush asynchronously so tests don’t hang
        if (driver instanceof AxeWatcherDriver axeDriver) {
            new Thread(() -> {
                try {
                    axeDriver.axeWatcher().flush();
                } catch (Exception e) {
                    System.err.println("⚠️ Async Axe Watcher flush failed: " + e.getMessage());
                }
            }).start();
        }

        // Always quit the browser
        try {
            driver.quit();
        } catch (Exception e) {
            System.err.println("⚠️ Driver quit failed: " + e.getMessage());
        }
    }
}
}
