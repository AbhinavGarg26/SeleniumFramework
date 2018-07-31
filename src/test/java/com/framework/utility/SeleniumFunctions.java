package com.framework.utility;

import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import tests.BaseTest;

	//Switch Functions
	public class SeleniumFunctions {
	/**
	 * switch to window handle based on handle name
	 * 
	 * @param driver
	 * @param wndHandle
	 */
	public static void switchToWindowHandle(WebDriver driver, String wndHandle) {

		//logger.info("inside switchtowindowhandle Method");
		Set<String> handler = driver.getWindowHandles();
		for (String handlesname : handler) {
			driver.switchTo().window(handlesname);
			String var = driver.getTitle();
			//logger.info("window Handle --> " + var);
			if (var.equalsIgnoreCase(wndHandle)) {
				//logger.info("Title matched hence switching to handle " + wndHandle);
				driver.switchTo().window(handlesname);
			} else {
				driver.switchTo().defaultContent();
			}
		}

	}
	
	/**
	 * Switch to window handle
	 * 
	 * @param driver
	 * @param wndHandle
	 */
	public static void switchToWindowHandleDirect(WebDriver driver, String wndHandle) {
		driver.switchTo().window(wndHandle);
	}
	
	/**
	 * Switch back to the Main or Default Window
	 * 
	 * @param driver
	 */
	public static WebDriver switchToDefault(WebDriver driver) {

		try {
			//logger.info("Switch to Default Content");
			return driver.switchTo().defaultContent();
		} catch (Exception e) {
			//logger.error("Error Occured while switch to default windiw ");
		}
		return driver;
	}
	

	/**
	 * Waits and Switches to the Frame
	 * 
	 * @param driver
	 * @param timeOutInSeconds
	 * @param locator
	 * @throws InterruptedException
	 */
	public static void waitAndSwitchToFrame(WebDriver driver,
			int timeOutInSeconds, By locator) throws InterruptedException {
		waitForPageLoad(driver);
		//logger.info("Switching to Frame" + locator.toString());
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
	}

	/**
	 * Waits and Switched to the Frame found by its Id or Name
	 * 
	 * @param driver
	 * @param timeOutInSeconds
	 * @param sFrameName
	 * @throws InterruptedException
	 */
	public static void waitAndSwitchToFrame(WebDriver driver,
			int timeOutInSeconds, String sFrameName)
					throws InterruptedException {
		WebDriverWait wait = null;
		try {
			waitForPageLoad(driver);
			//logger.info("Waiting and Switching to Frame by its Name " + sFrameName);
			wait = new WebDriverWait(driver, timeOutInSeconds);
			wait.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(sFrameName));
			//logger.info("Switched to Frame : " + sFrameName);
		} catch (TimeoutException e) {
			driver.navigate().refresh();
			waitForPageLoad(driver);
			wait.until(ExpectedConditions
					.frameToBeAvailableAndSwitchToIt(sFrameName));
		} catch (Exception e) {
			//logger.error("Error Occured while switching to frame " + sFrameName + e.getMessage());
			driver.switchTo().frame(sFrameName);
		}

	}
	
	//Wait Functions
	/**
	 * An expectation for checking if the given text is present in the specified
	 * element
	 * 
	 * @param driver
	 * @param element
	 * @param sText
	 */
	public static void waitFortextToBePresentInElement(WebDriver driver,
			final WebElement element, final String sText) {
		WebDriverWait wait = new WebDriverWait(driver,
				Integer.parseInt(BaseTest.CONFIG.getProperty("timeOutInSeconds")));
		wait.until(ExpectedConditions.textToBePresentInElement(element, sText));
	}

	/**
	 * An expectation for checking if the given text is present in the element
	 * that matches the given locator.
	 * 
	 * @param driver
	 * @param locator
	 * @param sText
	 */
	public static void waitFortextToBePresentInElementLocated(WebDriver driver,
			By locator, final String sText) {
		WebDriverWait wait = new WebDriverWait(driver,
				Integer.parseInt(BaseTest.CONFIG.getProperty("timeOutInSeconds")));
		wait.until(ExpectedConditions.textToBePresentInElementLocated(locator,
				sText));
	}
	
	//Page Load
		/**
		 * Waits for Page Load via Java Script Ready State
		 * 
		 * @param driver
		 * @param iTimeOut
		 * @throws InterruptedException
		 */
		public static boolean waitForPageLoad(WebDriver driver)
				throws InterruptedException {
			boolean isLoaded = false;
			int iTimeOut = Integer.parseInt(BaseTest.CONFIG.getProperty("timeOutInSeconds"));
			Thread.sleep(2000);
			try {
				//logger.info("Waiting For Page load via JS");
				ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor) driver).executeScript(
								"return document.readyState").equals("complete");
					}
				};
				WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
				wait.until(pageLoadCondition);
				isLoaded = true;
			} catch (Exception e) {
				//logger.error("Error Occured waiting for Page Load " + driver.getCurrentUrl());
			}
			return isLoaded;
		}

		/**
		 * Waits for Page Load via Java Script Ready State
		 * 
		 * @param driver
		 * @param iTimeOut
		 * @throws InterruptedException
		 */
		public static boolean waitForPageLoad(WebDriver driver, int iTimeOut)
				throws InterruptedException {
			boolean isLoaded = false;


			try {
				//logger.info("Waiting For Page load via JS");
				ExpectedCondition<Boolean> pageLoadCondition = new ExpectedCondition<Boolean>() {
					public Boolean apply(WebDriver driver) {
						return ((JavascriptExecutor) driver).executeScript(
								"return document.readyState").equals("complete");
					}
				};
				WebDriverWait wait = new WebDriverWait(driver, iTimeOut);
				wait.until(pageLoadCondition);
				isLoaded = true;
			} catch (Exception e) {
				//logger.error("Error Occured waiting for Page Load " + driver.getCurrentUrl());
			}
			return isLoaded;
		}
	
	//Click Functions
	/**
	 * Performs click operation using JS
	 * 
	 * @param driver
	 * @param element
	 */
	public static void clickByJS(WebDriver driver, WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	
	/**
	 * Performs Double Click on the Element using Action Class
	 * 
	 * @param driver
	 * @param element
	 */
	public static void doubleClick(WebDriver driver, WebElement element) {
		try {
			//logger.info("Double Click element via Action");
			Actions action = new Actions(driver);
			action.doubleClick(element).build().perform();
		} catch (Exception e) {
			//logger.error("Error Occured while double clicking " + element);
		}
	}


	/**
	 * Performs Right Click on the Element using Action
	 * 
	 * @param driver
	 * @param element
	 */
	public static void rightClick(WebDriver driver, WebElement element) {
		try {
			//logger.info("Right Click on Element using Action Class");
			Actions action = new Actions(driver);
			//highlightElementBorder(driver, element);
			action.contextClick(element).build().perform();
		} catch (Exception e) {
			//logger.error("Error Occured while right clicking " + element);
		}
	}
	
	//Hover Functions
	/**
	 * Move to the Element using coordinates
	 * 
	 * @param driver
	 * @param element
	 */
	public static void moveToElement(WebDriver driver, WebElement element) {
		Actions action = new Actions(driver);
		action.moveToElement(element, element.getLocation().getX(), element
				.getLocation().getY());
		action.build().perform();
	}
	
	/**
	 * mouse overs to an element and click specified.
	 * 
	 * @param driver
	 * @param Element
	 */
	public static void moveToElementAndClick(WebDriver driver,
			WebElement element) {
		Actions builder = new Actions(driver);
		builder.moveToElement(element).click().build().perform();
	}

	//Send Keys
	/**
	 * Waits and then sendkeys to element
	 * 
	 * @param driver
	 * @param element
	 * @param sValue
	 */
	public static void sendKeys(WebDriver driver, WebElement element,
			String sValue) {
		WebElement ele;
		try {
			//logger.info("Waiting for an element to be clickable " + element);
			WebDriverWait wait = new WebDriverWait(driver,
					Integer.parseInt(BaseTest.CONFIG.getProperty("timeOutInSeconds")));
			ele = wait.until(ExpectedConditions.elementToBeClickable(element));
			ele.sendKeys(sValue);
			//logger.info("Waited and send value to on element " + element);
		} catch (Exception e) {
			//logger.info("Exception while supplying text to element" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	 * Input the text using JavaScript
	 * 
	 * @param driver
	 * @param element
	 * @param sData
	 * @throws InterruptedException 
	 */
	public static void sendKeysByJS(WebDriver driver, WebElement element,
			String sData) throws InterruptedException {
		Thread.sleep(750);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].value=arguments[1];", element,
				sData);
	}
	
	
}
