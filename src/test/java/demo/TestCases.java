package demo;

import java.time.Duration;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in
     * instructions
     */

    /*
     * Do not change the provided methods unless necessary, they will help in
     * automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
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
        wrapper = new Wrappers(driver);

        driver.manage().window().maximize();
    }

    @Test
    public void testCase01() throws InterruptedException {
        System.out.println("Start TestCase 01");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Navigating to the Google Form URL
        wrapper.navigateToUrl(
                "https://docs.google.com/forms/d/e/1FAIpQLSep9LTMntH5YqIXa5nkiPKSs283kdwitBBhXWyZdAS-e4CxBQ/viewform");

        wrapper.fillTextField(By.xpath("(//input[@type='text'])[1]"), "Crio Learner");

        // Generating unique text for the TextArea field
        long epochTime = System.currentTimeMillis();
        wrapper.fillTextField(By.xpath("//textarea[@class='KHxj8b tL9Q4c']"),
                "I want to be the best QA Engineer!" + epochTime);

        wrapper.clickElement(By.xpath("(//div[@class=\"Od2TWd hYsg7c\"])[3]"));

        // Using WebDriverWait to ensure elements are clickable before interacting
        System.out.println("Using WebDriverWait to ensure the elements are clickable before interacting with them");
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-answer-value=\"Java\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-answer-value=\"Selenium\"]"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@data-answer-value=\"TestNG\"]"))).click();

        wrapper.clickElement(By.xpath("//span[text()=\"Choose\"]"));
        Thread.sleep(5000);
        wrapper.clickElement(By.xpath("(//span[@class='vRMGwf oJeWuf'][text()='Mr'])[2]"));

        String dateMinus7Days = wrapper.getDateMinusDays(7);
        wrapper.fillTextField(By.xpath("//input[contains(@type,'date')]"), dateMinus7Days);

        wrapper.fillTextField(By.xpath("//input[@aria-label='Hour']"), "07");
        wrapper.fillTextField(By.xpath("//input[@aria-label='Minute']"), "30");

        wrapper.clickElement(By.xpath("//div[@aria-label='Submit']"));

        Thread.sleep(5000);

        // Using WebDriverWait to ensure elements are clickable before interacting
        String successMsg = wrapper.printText(By.xpath("//div[@class='vHW8K']"));
        System.out.println(successMsg);
        // Ending the test case
        System.out.println("End TestCase 01");
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}