package selenium.lesson4;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AddRemoveFromCart {
    private WebDriver driver;
    @Before
    public void startChromeDriver() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();

        driver.manage().window().maximize();
    }

    @After
    public void stopChromeDriver(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void addRemoveFromCart() {
        driver.navigate().to("http://localhost/litecart");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        for (int i = 0; i < 7; i++) {
            int numberOfItems = Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText());
            driver.findElement(By.xpath(".//ul[@class='listing-wrapper products']/li[1]")).click();
            if (isElementPresent(By.xpath(".//td[@class='options'][contains(text(), Size)]"))) {
                Select size = new Select(driver.findElement(By.xpath(".//select[@name='options[Size]']")));
                size.selectByIndex(1);
            }
            driver.findElement(By.xpath(".//button[@type='submit'][@name='add_cart_product']")).click();
            wait.until(ExpectedConditions.attributeContains(By.xpath(".//a/*[@class='quantity']"), "innerText", String.valueOf(numberOfItems + 1)));
            driver.findElement(By.xpath(".//i[@class='fa fa-home']")).click();
        }

        driver.findElement(By.xpath(".//a[@class='link'][contains(text(), 'Checkout')]")).click();
        int numberOfShortcuts = driver.findElements(By.xpath(".//ul[@class='shortcuts']/li")).size();
        int numberOfRows = driver.findElements(By.xpath(".//table[@class='dataTable rounded-corners']//tr")).size();

        for (int i = 0; i <= numberOfShortcuts; i++) {

            if (isElementPresent(By.xpath(".//ul[@class='shortcuts']/li[1]"))) {
                //wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//ul[@class='shortcuts']/li[1]")));
                driver.findElement(By.xpath(".//ul[@class='shortcuts']/li[1]")).click();
                driver.findElement(By.xpath(".//button[@type='submit'][@name='remove_cart_item']")).click();
                wait.until(ExpectedConditions.refreshed(ExpectedConditions.numberOfElementsToBe(By.xpath(".//table[@class='dataTable rounded-corners']//tr"), numberOfRows -1)));
            }
            else {
                wait.until(ExpectedConditions.elementToBeClickable(By.xpath(".//button[@type='submit'][@name='remove_cart_item']")));
                driver.findElement(By.xpath(".//button[@type='submit'][@name='remove_cart_item']")).click();
            }
        }
        driver.findElement(By.xpath(".//i[@class='fa fa-home']")).click();

        Assert.assertEquals(0, Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText()));
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

}
