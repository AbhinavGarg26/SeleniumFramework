package tests;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseTest {
    public WebDriver driver;
    public WebDriverWait wait;
    
    // properties
    public static Properties CONFIG;
    //Global test data excel file
    public static final String testDataExcelFileName = "testdata.xlsx";


    public WebDriver getDriver() {
        return driver;
    }


    @BeforeClass (description = "Class Level Setup!")
    public void setup () throws IOException {
        //Create a Chrome driver. All test classes use this.
    		System.setProperty("webdriver.chrome.driver", "chromedriver");
        driver = new ChromeDriver();

        //Create a wait. All test classes use this.
        wait = new WebDriverWait(driver,15);

        //Maximize Window
        driver.manage().window().maximize();
        
        FileInputStream fs = new FileInputStream("config/config.properties");
        CONFIG= new Properties();
        CONFIG.load(fs);

        
    }

    @AfterClass(description = "Class Level Teardown!")
    public void teardown () {
        driver.quit();
    }

}
