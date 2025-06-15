package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01() throws InterruptedException{
        Wrappers.navigateToUrl(driver, "http://www.flipkart.com/");
        Wrappers.searchProduct(driver, "Washing Machine");
        
        //sort by popularity
        By popularityBy = By.xpath("//div[contains(@class,'zg') and text()='Popularity']");
        Wrappers.clickElement(driver, popularityBy);

        //get count products with rating <=4
        By ratingBy = By.xpath("//span[contains(@class,'HWO')]/div[@class='XQDdHH']");
        Wrappers.getCountOfProductsByRating(driver, ratingBy);
        Thread.sleep(3000);
    }

    @Test
    public void testCase02() throws InterruptedException{
        Wrappers.navigateToUrl(driver, "http://www.flipkart.com/");
        Wrappers.searchProduct(driver, "iPhone");
        
        //get count products with rating <=4
        By productListBy = By.className("yKfJKb");
        Wrappers.getListOfTitleAndDiscount(driver, productListBy);
        // Thread.sleep(3000);
    }

    @Test
    public void testCase03() throws InterruptedException{
        Wrappers.navigateToUrl(driver, "http://www.flipkart.com/");
        Wrappers.searchProduct(driver, "Coffee Mug");
        
        //filter with rating 4* and above
        By filterBy = By.xpath("//div[contains(@class,'qKy') and text()='4★ & above']");
        Wrappers.clickElement(driver, filterBy);
        Thread.sleep(2000);
        By productListBy = By.xpath("//div[contains(@class,'slAVV')]");
        //or //div[contains(@class,'afFzxY')]//parent::div[contains(@class,'slAVV')] --> to get list of products with reviews
        Wrappers.getListOfProductWithHighReviews(driver, productListBy);
        Thread.sleep(3000);
    }
     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}