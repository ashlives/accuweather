package tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.ExtendedPage;
import pages.NavBar;


import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.io.File;
import java.io.IOException;

import static org.testng.Assert.assertTrue;

/*TestBase for all the testcases to be executed*/

public class TestBase {
    WebDriver driver;
    WebDriverWait wait;
    NavBar objNavBar;
    ExtendedPage objExtend;
    String cityName;
    private boolean acceptNextAlert = true;
    private StringBuffer verificationErrors = new StringBuffer();

    ExtentReports extent;
    ExtentTest logger;
    ExtentHtmlReporter htmlReporter;
    String htmlReportPath = "C:\\Screenshots/MyOwnReport.html"; //Path for the HTML report to be saved


    @BeforeClass
    @Parameters({"city"}) //Fetch city name from testng.xml
    public void initialize(String city) throws Exception {

        cityName = city;
        htmlReporter = new ExtentHtmlReporter(htmlReportPath);
        extent = new ExtentReports();
        htmlReporter.setAppendExisting(true);
        extent.attachReporter(htmlReporter);

        System.setProperty("webdriver.chrome.driver", "C:\\selenium-java-3.4.0/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://www.accuweather.com/");
        System.out.println("Accuweather site loaded.");
        objNavBar = new NavBar(driver);
        objExtend = new ExtendedPage(driver);
        objNavBar.search(city);
        String cityName = driver.findElement(By.id("current-city-tab")).getText();
        assertTrue(cityName.contains(city));
        objNavBar.selectExtended();
    }

    //Creating ExtentReport based on tests performed
    @AfterMethod
    public void getResult(ITestResult result) throws Exception {
        if (result.getStatus() == ITestResult.FAILURE) {
            String failureScreenshot = captureScreenshot();
            logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
            logger.fail(result.getThrowable());
            logger.addScreenCaptureFromPath(failureScreenshot);
        } else if (result.getStatus() == ITestResult.SUCCESS) {
            logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test Case PASSED", ExtentColor.GREEN));
        } else if (result.getStatus() == ITestResult.SKIP) {
            String skipScreenshot = captureScreenshot();
            logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test Case SKIPPED", ExtentColor.BLUE));
            logger.addScreenCaptureFromPath(skipScreenshot);
        }

    }

    //Flush the test results in html report
    @AfterTest
    public void testend() throws Exception {
        extent.flush();
    }


    @AfterClass
    public void tearDown() throws Exception {
        driver.close();
    }

    //Exception handling
    private boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    private boolean isAlertPresent() {
        try {
            driver.switchTo().alert();
            return true;
        } catch (NoAlertPresentException e) {
            return false;
        }
    }

    private String closeAlertAndGetItsText() {
        try {
            Alert alert = driver.switchTo().alert();
            String alertText = alert.getText();
            if (acceptNextAlert) {
                alert.accept();
            } else {
                alert.dismiss();
            }
            return alertText;
        } finally {
            acceptNextAlert = true;
        }
    }

    public String captureScreenshot(){
        // Take screenshot and store as a file format
        File src= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        String path = "C:/Screenshots/"+System.currentTimeMillis()+".png";
        try {
            // now copy the  screenshot to desired location using copyFile //method
            FileUtils.copyFile(src, new File(path));
        }

        catch (IOException e)
        {
            System.out.println(e.getMessage());

        }
        return path;
    }
}