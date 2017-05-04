package com.jef.tool;



import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import static org.testng.Assert.assertTrue;

/**
 * Created by jmgivera on 5/5/2017.
 */
public class SanityTest extends myBaseTest {
    private Properties prop = new Properties();
    //private WebDriver driver;

    @Test
    public void verifyLogin() throws Exception {

        super.beforeMethod();
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
        driver.findElement(objectMap.getLocator("btn_login_home")).click();
        driver.findElement(objectMap.getLocator("txt_username")).sendKeys(prop.getProperty("amaysim"));
        driver.findElement(objectMap.getLocator("txt_password")).sendKeys(prop.getProperty("password"));
        driver.findElement(objectMap.getLocator("btn_login")).click();



    }

    @Test
    public void verifyPremSMSLimit() throws Exception {
        //WebElement element = driver.findElement(By.cssSelector(prop.getProperty("modal_welcome_close")));
        WebElement element = driver.findElement(objectMap.getLocator("modal_welcome_close"));
        JavascriptExecutor executor = (JavascriptExecutor)driver;
        executor.executeScript("arguments[0].click()", element);

        WebDriverWait wait = new WebDriverWait(driver, 20);
        if (driver.findElement(objectMap.getLocator("modal_welcome")).getAttribute("style").contains("visibility")){
        /*try {
                wait(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
            System.out.println("Successfully closed the welcome Popup");
        }
        WebElement element_setting = driver.findElement(objectMap.getLocator("settings"));
        executor.executeScript("arguments[0].click()", element_setting);

        WebElement element_prem_sms_limit_edit = driver.findElement(objectMap.getLocator("edit_prem_sms_limit"));
        executor.executeScript("arguments[0].click()", element_prem_sms_limit_edit);

        Select prem_sms_limit_value_dropdown = new Select(driver.findElement(objectMap.getLocator("prem_sms_limit_dropdown")));
        prem_sms_limit_value_dropdown.selectByVisibleText("$30");

        WebElement element_prem_sms_limit_save = driver.findElement(objectMap.getLocator("pre_sms_limit_save"));
        executor.executeScript("arguments[0].click()", element_prem_sms_limit_save);

        wait.until(ExpectedConditions.visibilityOfElementLocated(objectMap.getLocator("loading_spinner"))).isDisplayed();
        WebElement element_prem_sms_limit_newvalue = driver.findElement(objectMap.getLocator("remium_sms_limit_value"));
        System.out.println(executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue));
        System.out.println(executor.executeScript("return arguments[0]", element_prem_sms_limit_newvalue));
        //assertEquals(executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue),"$30");
        String prem_sms_new = executor.executeScript("return arguments[0].innerHTML", element_prem_sms_limit_newvalue).toString();
        assertTrue(prem_sms_new.contains("$30"));
    }
}
