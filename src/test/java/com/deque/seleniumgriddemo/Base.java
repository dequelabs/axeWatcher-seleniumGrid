package com.deque.seleniumgriddemo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.slf4j.simple.*;
import org.slf4j.spi.*;
import com.deque.axe_core.commons.AxeWatcherOptions;
import com.deque.axe_core.selenium.AxeWatcher;
import com.deque.axe_core.selenium.AxeWatcherDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class Base {
    public WebDriver driver;
    public MainPage mainPage;

    @BeforeMethod
    public void setUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--disable-dev-shm-usage");
        // TODO: unless you are using branded Chrome < 139, use Chromium or Chrome for Testing
        chromeOptions.setBinary("/Applications/Google Chrome for Testing.app");

        try {
          driver = new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        AxeWatcherOptions options = new AxeWatcherOptions()
            // TODO: use real credentials
                .setApiKey("my-api-key")
                .setServerUrl("https://axe.deque.com");

        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
        WebDriverManager.chromedriver().setup();
        chromeOptions = watcher.configure(chromeOptions);

        // Wrap the WebDriver with Axe Watcher
        driver = watcher.wrapDriver(new ChromeDriver(chromeOptions));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://www.jetbrains.com/");
    }
    // Set up WebDriver using WebDriverManager


    @AfterMethod
    public void tearDown() {
        if (driver instanceof AxeWatcherDriver) {
            ((AxeWatcherDriver) driver).axeWatcher().flush();
        }
        driver.quit();
    }
}
