package com.jef.tool;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.asserts.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import org.openqa.selenium.support.ui.ExpectedCondition;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class AppTest
  {
      public static WebDriver driver;
      private WebDriverWait wait;
      public static Properties prop = new Properties();

    public void testApp()
    {


        //my_click();
        //driver.navigate().to(prop.getProperty("test-environment"));
        //driver.findElement(By.xpath(prop.getProperty("btn_login_home"))).click();
        driver.findElement(By.id(prop.getProperty("txt_username"))).sendKeys(prop.getProperty("amaysim"));
        driver.findElement(By.id(prop.getProperty("txt_password"))).sendKeys(prop.getProperty("password"));
        driver.findElement(By.className(prop.getProperty("btn_login"))).click();

        WebElement element = driver.findElement(By.cssSelector(prop.getProperty("modal_welcome_close")));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        if (driver.findElement(By.id(prop.getProperty("modal_welcome"))).getAttribute("style").contains("visibility")){
/*            try {
                wait(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("Successfully closed the welcome Popup");
        }
        WebElement element_setting = driver.findElement(By.xpath(prop.getProperty("settings")));
        executor.executeScript("arguments[0].click()", element_setting);

        WebElement element_prem_sms_limit_edit = driver.findElement(By.cssSelector(prop.getProperty("edit_prem_sms_limit")));
        executor.executeScript("arguments[0].click()", element_prem_sms_limit_edit);

        Select prem_sms_limit_value_dropdown = new Select(driver.findElement(By.cssSelector(prop.getProperty("prem_sms_limit_dropdown"))));
        prem_sms_limit_value_dropdown.selectByVisibleText("$30");

        WebElement element_prem_sms_limit_save = driver.findElement(By.cssSelector(prop.getProperty("pre_sms_limit_save")));
        executor.executeScript("arguments[0].click()", element_prem_sms_limit_save);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(prop.getProperty("loading_spinner")))).isDisplayed();
        WebElement element_prem_sms_limit_newvalue = driver.findElement(By.cssSelector(prop.getProperty("remium_sms_limit_value")));
        System.out.println(executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue));
        System.out.println(executor.executeScript("return arguments[0]", element_prem_sms_limit_newvalue));
        //assertEquals(executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue),"$30");
        String prem_sms_new = executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue).toString();
        assertTrue(prem_sms_new.contains("$30"));
    }



      public static void beforeMethod() {
          System.out.println("test1.......");
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

          driver.navigate().to(prop.getProperty("test-environment"));
          System.out.println("test.......");
      }

      public static void stop() {
          driver.quit();
      }

/*      public void waitForPageToStopLoading() {
          try {
              this.wait.until(new ExpectedCondition() {
                  public Boolean apply(WebDriver driver) {
                      return Boolean.valueOf(((String)WebDriverUtil.this.jsx.executeScript("return document.readyState", new Object[0])).equalsIgnoreCase("complete"));
                  }
              });
          } catch (TimeoutException var2) {
              logger.info("Page load has timed out. Having browser stop loading and allowing passthrough.");
              this.jsx.executeScript("try{ window.stop(); } catch(e) { document.execCommand(\'Stop\'); } document.readyState = \'complete\';", new Object[0]);
          }

      }*/
}
