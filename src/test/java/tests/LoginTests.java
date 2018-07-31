package tests;

import java.util.HashMap;

import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.framework.pages.HomePage;
import com.framework.pages.LoginPage;
import com.framework.utils.excelutils.ExcelUtil;
import com.framework.utils.extentreports.ExtentTestManager;
import com.framework.utils.listeners.TestListener;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;



//In order to eliminate attachment problem for Allure, you should add @Listener line.
//link: https://github.com/allure-framework/allure1/issues/730
@Listeners({ TestListener.class })
@Epic("Regression Tests")
@Feature("Login Tests")
public class LoginTests extends BaseTest {

	HashMap<String, HashMap<String, String>> hashMapTestParam = new HashMap<String, HashMap<String, String>>();
	HashMap<String, String> hashMap = new HashMap<String, String>();
	
    @BeforeGroups("LoginData")
    public void setupTestCaseInfo() {
    		System.out.println("************Setup Test Level Data**********");
        ExcelUtil.setExcelFileSheet("LoginData");
        
        ExcelUtil.hashMapTestData = ExcelUtil.fetchTestCaseInformation();
    }
	
	@BeforeTest
    public void setupTestData () {
        //Set Test Data Excel and Sheet
        
    }

    @Test (priority = 0, description="Invalid Login Scenario with wrong username and password.", groups = "LoginData")
    @Severity(SeverityLevel.BLOCKER)
    @Description("Test Description: Login test with wrong username and wrong password.")
    @Story("Invalid username and password login test")
    public void invalidLoginTest_InvalidUserNameInvalidPassword () throws InterruptedException {
        //extentreports Description
    		String testCaseName = "Invalid Username and Invalid Password Login Test";
    		hashMap = ExcelUtil.hashMapTestData.get(testCaseName);
    		
        ExtentTestManager.getTest().setDescription("Invalid Login Scenario with wrong username and password.");

        //*************PAGE INSTANTIATIONS*************
        HomePage homePage = new HomePage(driver,wait);
        LoginPage loginPage = new LoginPage(driver,wait);

        //*************PAGE METHODS********************
        //Open N11 HomePage
        homePage.goToN11();

        //Go to LoginPage
        homePage.goToLoginPage();

        int rowNumber = Integer.parseInt(hashMap.get("RowNumber"));
        //Login to N11 with first row of the login data
        loginPage.loginToN11(hashMap.get("Username"), hashMap.get("Password"));

        //Set test row number to 1
        ExcelUtil.setRowNumber(rowNumber);

        //Set Test Status Column number to 5
        ExcelUtil.setColumnNumber(5);

        //*************ASSERTIONS***********************
        Thread.sleep(500);
        //Verify password message by using excel's 2st row, 5th column
        //Row and Column numbers starting from 0. Thus we need to write 1 and 4, instead of 2 and 5!
        loginPage.verifyLoginPassword(hashMap.get("Password Error"));
    }

    @Test (priority = 1, description="Invalid Login Scenario with empty username and password.", groups = "LoginData")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test Description: Login test with empty username and empty password.")
    @Story("Empty username and password login test")
    public void invalidLoginTest_EmptyUserEmptyPassword () throws InterruptedException {
        //extentreports Description
        ExtentTestManager.getTest().setDescription("Invalid Login Scenario with empty username and password.");
        
        String testCaseName = "Empty Username and Empty Password Login Test";
		hashMap = ExcelUtil.hashMapTestData.get(testCaseName);

        //*************PAGE INSTANTIATIONS*************
        HomePage homePage = new HomePage(driver,wait);
        LoginPage loginPage = new LoginPage(driver,wait);

        //*************PAGE METHODS********************
        homePage.goToN11();
        homePage.goToLoginPage();

        int rowNumber = Integer.parseInt(hashMap.get("RowNumber"));
        //Login to N11 with second row of the login data
        loginPage.loginToN11(hashMap.get("Username"), hashMap.get("Password"));

        //Set test row number to 2
        ExcelUtil.setRowNumber(rowNumber);

        //Set Test Status column number to 5
        ExcelUtil.setColumnNumber(5);

        //*************ASSERTIONS***********************
        Thread.sleep(500);
        //Verify by 3rd row and 4th column
        loginPage.verifyLoginUserName(hashMap.get("Username Error"));
        //Verify by 3rd row and 5th column
        loginPage.verifyLoginPassword(hashMap.get("Password Error"));
    }

}
