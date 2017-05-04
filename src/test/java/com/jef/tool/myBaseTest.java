package com.jef.tool;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by jmgivera on 5/5/2017.
 */
public class myBaseTest {
    WebDriver driver;
    Properties prop = new Properties();
    ObjectMap objectMap;

    //@BeforeMethod
    public void beforeMethod() throws Exception {
        System.setProperty("webdriver.chrome.driver", "browser/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("credentials_enable_service", false);
        prefs.put("password_manager_enabled", false);
        options.setExperimentalOption("prefs", prefs);

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        driver = new ChromeDriver(capabilities);
        driver.manage().timeouts().implicitlyWait(10, SECONDS);

        File file = new File("src/main/resources/automation.properties");
        FileInputStream fileInput = null;
        try {
            fileInput = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //load properties file
        try {
            prop.load(fileInput);
        } catch (IOException e) {
            e.printStackTrace();
        }

        objectMap = new ObjectMap ("src/main/resources/automation.properties");
        driver.navigate().to(prop.getProperty("test-environment"));
        //driver.navigate().to(String.valueOf(objectMap.getLocator("test-environment")));

    }

    @AfterTest
    public void stop() {
        driver.quit();
    }
}
