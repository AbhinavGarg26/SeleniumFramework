package com.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.framework.utility.Utility;

import io.qameta.allure.Step;

public class LoginPage extends BasePage {

    //*********Constructor*********
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    //*********Page Methods*********
    @Step("Login Step with username: {0}, password: {1}, for method: {method} step...")
    public void loginToN11 (String userName, String password){
        //Enter Username(Email)
        writeText(Utility.getLocator("or", "usenameId"),userName);
        //Enter Password
        writeText(Utility.getLocator("or", "passwordId"), password);
        //Click Login Button
        click(Utility.getLocator("or", "loginButtonId"));
    }

    //Verify Username Condition
    @Step("Verify username: {0} step...")
    public void verifyLoginUserName (String expectedUserNameMessage) {
        Assert.assertEquals(readText(Utility.getLocator("or", "errorMessageUsernameXpath")), expectedUserNameMessage);
    }

    //Verify Password Condition
    @Step("Verify verifyLoginPassword: {0} step...")
    public void verifyLoginPassword (String expectedPasswordMessage) {
        Assert.assertEquals(readText(Utility.getLocator("or", "errorMessagePasswordXpath")), expectedPasswordMessage);
    }

}
