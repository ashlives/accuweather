package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


//Navigation bar Page Object Model
public class NavBar {
    WebDriver driver;
    WebDriverWait wait;

    //POM for static elements.
    By searchBox = By.id("s");
    By extendedTab = By.xpath(".//*[@id='wrap']/div[4]/div[5]/div/div/ul/li[3]/a/span");
    By autoCompleteMenu = By.id("menu-cities");

    //Constructor for the class
    public NavBar(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,50);
    }

    //Clicking on Extended tab
    public void selectExtended(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(extendedTab)).click();
        System.out.println("Clicked on Extended Tab.");
    }

    //Searching and navigating for provided value/city
    public void search(String searchText){
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).clear();
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchBox)).sendKeys(searchText);
        System.out.println("Entered "+searchText+" in Search Box.");
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS); //Implicit wait for autocomplete
        wait.until(ExpectedConditions.visibilityOfElementLocated(autoCompleteMenu));
        driver.switchTo().activeElement();
        System.out.println("Switched to Autocomplete.");
        wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(searchText))).click();
    }
}