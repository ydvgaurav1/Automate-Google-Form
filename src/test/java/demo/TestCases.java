package demo;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
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
        Thread.sleep(3000);

        wrapper.fillTextField(By.xpath(
                "//div[@class='rFrNMe k3kHxc RdH0ib yqQS1 zKHdkd']//input[@type='text' and @class='whsOnd zHQkBf']"),
                "Crio Learner");

        // Generating unique text for the TextArea field
        long epochTime = System.currentTimeMillis();
        wrapper.fillTextField(By.xpath("//textarea[@class='KHxj8b tL9Q4c']"),
                "I want to be the best QA Engineer!" + epochTime);

        wrapper.selectRadioButtonByLabel("6 - 10");

        // Selecting check boxes
        System.out.println("Selecting check boxes");
        wrapper.selectCheckboxesByLabels(List.of("Java", "Selenium", "TestNG"));

        WebElement dropdown = wait
                .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Choose']")));
        dropdown.click();
        Thread.sleep(5000);
        wrapper.selectDropdownOptionByLabel("Mr");

        String dateMinus7Days = wrapper.getDateMinusDays(7);
        wrapper.fillTextField(By.xpath("//input[contains(@type,'date')]"), dateMinus7Days);

        wrapper.fillTextField(By.xpath("//input[@aria-label='Hour']"), "07");
        wrapper.fillTextField(By.xpath("//input[@aria-label='Minute']"), "30");

        wrapper.clickElement(By.xpath("//div[@aria-label='Submit']"));

        Thread.sleep(5000);

        // Using WebDriverWait to ensure elements are clickable before interacting
        WebElement successMessageElement = wait
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='vHW8K']")));
        String successMsg = successMessageElement.getText();
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
