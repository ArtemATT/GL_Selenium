package selenium.lesson7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import selenium.lesson7.utils.ConfigReader;

public class Page {

    protected WebDriver driver;
    WebDriverWait wait;
    ConfigReader configReader;

    Page(WebDriver driver) {
        configReader = new ConfigReader();
        this.driver = driver;
        wait = new WebDriverWait(driver, configReader.getImplicitWait());
    }

    boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    void navigateToHome() throws NoSuchElementException {
        driver.findElement(By.xpath(".//i[@class='fa fa-home']")).click();
    }
}