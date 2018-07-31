package com.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.framework.utility.Utility;

import io.qameta.allure.Step;
import tests.BaseTest;

public class HomePage extends BasePage {

    //*********Constructor*********
    public HomePage (WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //*********Page Variables*********
    String baseURL = BaseTest.CONFIG.getProperty("testsiteURL");


    //*********Page Methods*********

    //Go to Homepage
    @Step("Open N11 Step...")
    public void goToN11 (){
        driver.get(baseURL);
        //driver.navigate().to(baseURL)
    }

    //Go to LoginPage
    @Step("Go to Login Page Step...")
    public void goToLoginPage (){
        click(Utility.getLocator("or", "signInButtonClass"));
    }

}
