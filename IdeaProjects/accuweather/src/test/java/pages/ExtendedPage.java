package pages;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

//Extended page Page Object Model
public class ExtendedPage {
    WebDriver driver;
    WebDriverWait wait;

    //POM for static elements. Elements to be dynamically picked for navigation are added in class instead
    By detailsDayTemp = By.xpath(".//*[@id='detail-day-night']/div[1]/div/div[1]/div/div[1]/span[1]");
    By detailsNightTemp = By.xpath(".//*[@id='detail-day-night']/div[2]/div/div[1]/div[1]/span[1]");
    By detailsIconDay = By.xpath(".//*[@id='detail-day-night']/div[1]/div/div[1]/div/div[2]");

    //Constructor for the class
    public ExtendedPage(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver,50);
    }

    //Click on each day card based on the incoming day value
    public void clickDayFeed(int day){
        driver.findElement(By.xpath("(.//*[@id='feed-tabs']/ul/li["+day+"])[2]")).click();
    }

    //Fetch and send the class name of weather icon from feeds card
    public String getFeedIconClass(int day){
        String iconClass = driver.findElement(By.xpath("(.//*[@id='feed-tabs']/ul/li["+day+"]/div/div[1])[2]")).getAttribute("class");
        return(StringUtils.removeEnd(iconClass, " ")); //Removing whitespace at the end of the class name
    }

    //Fetch and send the class name of weather icon from details card
    public String getDetailsIconClass(){
        String iconClass = driver.findElement(detailsIconDay).getAttribute("class");
        return(StringUtils.removeEnd(iconClass, " "));//Removing whitespace at the end of the class name
    }

    //Fetch and send the night temperature from feeds card
    public String getDayFeedTemp(int day){
        String xpath = "(.//*[@id='feed-tabs']/ul/li["+day+"]/div/div[2]/div/span[1])[2]";
        String largeTemp = driver.findElement(By.xpath(xpath)).getAttribute("innerHTML");
        return(largeTemp);
    }

    //Fetch and send the day temperature from details card
    public String getDayDetailsTemp(){
        String temp = driver.findElement(detailsDayTemp).getAttribute("innerHTML");
        return(StringUtils.substring(temp, 0, 3));
    }

    //Fetch and send the night temperature from feeds card
    public String getNightFeedTemp(int day){
        String largeTemp = driver.findElement(By.xpath("(.//*[@id='feed-tabs']/ul/li["+day+"]/div/div[2]/div/span[2])[2]")).getAttribute("innerHTML");
        return(StringUtils.substring(largeTemp,1,4));
    }

    //Fetch and send the night temperature from details card
    public String getNightDetailsTemp(){
        String temp = driver.findElement(detailsNightTemp).getAttribute("innerHTML");
        return(StringUtils.substring(temp, 0, 3));
    }

    //Checking if current tab is showing day/night or just night
    public String checkDayNight(int day){
        String text = driver.findElement(By.xpath("(.//*[@id='feed-tabs']/ul/li["+day+"]/div/h3/a)[2]")).getText();
        System.out.println(text);
        if ( text.equals("TONIGHT")||text.equals("EARLY AM"))
        {
            System.out.println("Sending night");
            return ("night");
        }
        else {
            System.out.println("Sending day");
            return ("day");
        }
    }
}
