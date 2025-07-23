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

        WebDriver baseDriver;
        try {
            baseDriver = new RemoteWebDriver(new URL("http://localhost:4444/"), chromeOptions);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        AxeWatcherOptions options = new AxeWatcherOptions()
                .setApiKey("e514e163-ee4d-4f82-8fa5-cb413683e574")
                .setServerUrl("https://axe-qa.dequelabs.com");

        AxeWatcher watcher = new AxeWatcher(options).enableDebugLogger();
        driver = watcher.wrapDriver((ChromeDriver) baseDriver);

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
        driver.manage().window().maximize();
        driver.get("https://www.jetbrains.com/");
    }

    @AfterMethod
    public void tearDown() {
        if (driver instanceof AxeWatcherDriver) {
            ((AxeWatcherDriver) driver).axeWatcher().flush();
        }
        driver.quit();
    }
}