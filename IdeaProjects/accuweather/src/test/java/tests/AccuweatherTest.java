package tests;

import com.aventstack.extentreports.Status;
import org.testng.SkipException;
import org.testng.annotations.*;

import static org.testng.Assert.assertEquals;

public class AccuweatherTest extends TestBase{

    //Test for checking day temperature on feeds card vs day temperature on details card
    @Test(dataProvider="DayCountProvider")
    public void testDayTemp(int day) throws Exception {

        logger = extent.createTest(cityName+"-DayTemp-Day"+day,"Checking day temperature of "+cityName+" for Day"+day);
        objExtend.clickDayFeed(day);
        logger.log(Status.INFO,"Clicked on Day"+day+" feed.");

        String status = objExtend.checkDayNight(day);
        if (status == "day")
        {
            String dayTemp = objExtend.getDayFeedTemp(day);
            System.out.println("Day temp: " + dayTemp);
            logger.log(Status.INFO, "Day temp on the card: " + dayTemp);
            String detailsDayTemp = objExtend.getDayDetailsTemp();
            System.out.println("Day temp in details: " + detailsDayTemp);
            logger.log(Status.INFO, "Day temperature on the details card: " + detailsDayTemp);
            assertEquals(detailsDayTemp, dayTemp, "Day temperature missmatch.");
            logger.log(Status.PASS, "Day Temperature on feed card and details card is same.");
        } else {
            logger.log(Status.INFO,"Its night in "+cityName+". Skipping this test.");
            throw new SkipException("Its night in "+cityName);
        }
    }

    //Test for checking night temperature on feeds card vs night temperature on details card
    @Test(dataProvider="DayCountProvider")
    public void testNightTemp(int day) throws Exception
    {

        logger = extent.createTest(cityName+"-NightTemp-Day"+day,"Checking night temperature of "+cityName+" for Day"+day);
        objExtend.clickDayFeed(day);
        logger.log(Status.INFO,"Clicked on Day"+day+" feed.");
        String nightTemp;
        String status = objExtend.checkDayNight(day);
        if (status == "day") {
            nightTemp = objExtend.getNightFeedTemp(day);
        }else {
            nightTemp = objExtend.getDayFeedTemp(day);
        }
        System.out.println("Night temp: "+nightTemp);
        logger.log(Status.INFO,"Night temp on the card: "+nightTemp);
        String detailsNightTemp = objExtend.getNightDetailsTemp();
        System.out.println("Night temp in details: "+detailsNightTemp);
        logger.log(Status.INFO,"Night temperature on the details card: "+detailsNightTemp);
        assertEquals(detailsNightTemp,nightTemp,"Night temperature missmatch.");
        logger.log(Status.PASS,"Night Temperature on feed card and details card is same.");
    }

    //Test for checking weather icon on feeds card vs weather icon on details card (only for day's temperature)
    @Test(dataProvider = "DayCountProvider")
    public void testWeatherIcon(int day) throws Exception
    {

        logger = extent.createTest(cityName+"-WeatherIcon-Day"+day,"Checking weather icon of "+cityName+" for Day"+day);
        objExtend.clickDayFeed(day);
        logger.log(Status.INFO,"Clicked on Day"+day+" feed.");

        String status = objExtend.checkDayNight(day);
        if (status == "day")
        {
        String weatherIcon = objExtend.getFeedIconClass(day);
        System.out.println("Icon class: "+weatherIcon);
        logger.log(Status.INFO,"Weather icon class on the card: "+weatherIcon);
        String weatherDetailsIcon = objExtend.getDetailsIconClass();
        System.out.println("Details Icon class: "+weatherDetailsIcon);
        logger.log(Status.INFO,"Weather icon class on the details card: "+weatherDetailsIcon);
        assertEquals(weatherDetailsIcon,weatherIcon,"Icon missmatch.");
        logger.log(Status.PASS,"Weather icon on feed card and details card is same.");
         } else {
        logger.log(Status.INFO,"Its night in "+cityName+". Skipping this test.");
        throw new SkipException("Its night in "+cityName);
        }
    }

    //Providing day count i.e. upto 5 to the tests. Keeping it in tests class for easy manipulation
    @DataProvider(name="DayCountProvider")
    public Object[][] getDataFromDataprovider(){
        return new Object[][]
                {
                        {1 }, {2 }, { 3 }, { 4 }, { 5 }

                };

    }
}
