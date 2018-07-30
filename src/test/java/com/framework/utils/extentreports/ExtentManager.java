package com.framework.utils.extentreports;

import org.openqa.selenium.Platform;

import com.relevantcodes.extentreports.ExtentReports;

//AG: extentreports extent instance created here. That instance can be reachable by getReporter() method.

public class ExtentManager {

    private static ExtentReports extent;

    public synchronized static ExtentReports getReporter(){
        if(extent == null){
            //Set HTML reporting file location
            String workingDir = System.getProperty("user.dir");
            String fullPath = "";
            if (Platform.getCurrent().toString().equalsIgnoreCase("MAC")) {
            		fullPath = workingDir + "/extentreports/ExtentReportResults.html";
            } else if (Platform.getCurrent().toString().contains("WIN")) {
            		fullPath = workingDir + "\\extentreports\\ExtentReportResults.html";
            }
            extent = new ExtentReports(fullPath, true);
        }
        return extent;
    }
}
