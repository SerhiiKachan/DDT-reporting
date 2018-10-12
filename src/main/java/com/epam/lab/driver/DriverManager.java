package com.epam.lab.driver;

import com.epam.lab.constants.Constants;
import com.epam.lab.utils.parser.MyParser;
import org.openqa.selenium.Alert;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DriverManager {

    private DriverManager() {
    }

    private static final ThreadLocal<WebDriver> drivers = ThreadLocal.withInitial(() -> {
        Properties driverProperties = MyParser.parsePropertiesFile(Constants.DRIVER_PROPERTIES_PATH);
        System.setProperty("webdriver.chrome.driver", driverProperties.getProperty("browser_driver"));
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
        WebDriver driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(Integer.parseInt(driverProperties.getProperty("implicit_wait")), TimeUnit.SECONDS);
        return driver;
    });

    public static WebDriver getDriver() {
        return drivers.get();
    }

    public static void exit() {
        drivers.get().quit();
        drivers.remove();
    }
}
