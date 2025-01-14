package demo.wrappers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    WebDriver driver;
    WebDriverWait wait;

    public Wrappers(WebDriver driver) {
        try {
            this.driver = driver;
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        } catch (Exception e) {
            System.err.println("Error initializing Wrappers class: " + e.getMessage());
        }
    }

    public void navigateToUrl(String url) {
        driver.get(url);
    }

    public void fillTextField(By locator, String text) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Failed to fill text field at locator: " + locator + " | Error: " + e.getMessage());
        }
    }

    public void clickElement(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
            element.click();
        } catch (Exception e) {
            System.err.println("Failed to click element at locator: " + locator + " | Error: " + e.getMessage());
        }
    }

    public String printText(By locator) {
        try {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return element.getText();
        } catch (Exception e) {
            System.err.println(
                    "Failed to retrieve text from element at locator: " + locator + " | Error: " + e.getMessage());
        }
        return null;
    }

    public String getDateMinusDays(int days) {
        LocalDate date = LocalDate.now().minusDays(days);
        return date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }

    public void selectRadioButtonByLabel(String desiredLabel) {
        try {
            List<WebElement> radioButtons = driver.findElements(By.xpath("//div[@role='radio']"));
            boolean found = false;
            for (WebElement radioButton : radioButtons) {
                String label = radioButton.getAttribute("aria-label");

                if (label.equalsIgnoreCase(desiredLabel)) {
                    if (!radioButton.getAttribute("aria-checked").equals("true")) {
                        radioButton.click();
                    } else {
                        System.out.println("Radio button with label '" + label + "' is already selected.");
                    }
                    found = true;
                    break;
                }
            }
            if (!found) {
                System.out.println("Radio button with label '" + desiredLabel + "' not found.");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while selecting the radio button: " + e.getMessage());
        }
    }

    public void selectCheckboxesByLabels(List<String> labels) {
        for (String label : labels) {
            try {
                By checkboxLocator = By.xpath(".//span[text()='" + label + "']");
                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
                WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(checkboxLocator));
                checkbox.click();
                System.out.println("Checkbox with label '" + label + "' selected successfully.");
            } catch (Exception e) {
                System.err.println("Error while selecting checkbox with label '" + label + "': " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void selectDropdownOptionByLabel(String desiredLabel) {
        try {
            By dropdownOptionLocator = By
                    .xpath("//div[@role='option']//span[@class='vRMGwf oJeWuf' and text()='" + desiredLabel + "']");
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement dropdownOption = wait.until(ExpectedConditions.elementToBeClickable(dropdownOptionLocator));
            dropdownOption.click();
            System.out.println("Dropdown option with label '" + desiredLabel + "' selected.");
        } catch (Exception e) {
            System.out.println(
                    "Error while selecting dropdown option with label '" + desiredLabel + "': " + e.getMessage());
            e.printStackTrace();
        }
    }

}
