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
    private WebDriverWait wait;

    @Before
    public void startChromeDriver() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
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
        addItemsToCart(3);
        removeAllItemsFromCart();

        Assert.assertEquals(0, Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText()));
    }

    private void addItemsToCart (int numberOfItems) {
        for (int i = 0; i < numberOfItems; i++) {
            int numberOfItemsInitial = Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText());
            driver.findElement(By.xpath(".//ul[@class='listing-wrapper products']/li[1]")).click();
            if (isElementPresent(By.xpath(".//td[@class='options'][contains(text(), Size)]"))) {
                Select size = new Select(driver.findElement(By.xpath(".//select[@name='options[Size]']")));
                size.selectByIndex(1);
            }
            driver.findElement(By.xpath(".//button[@type='submit'][@name='add_cart_product']")).click();
            wait.until(ExpectedConditions.attributeContains(By.xpath(".//a/*[@class='quantity']"), "innerText", String.valueOf(numberOfItemsInitial + 1)));
            driver.findElement(By.xpath(".//i[@class='fa fa-home']")).click();
        }
    }

    private void removeAllItemsFromCart() {
        int numberOfGoods = Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText());
        if (numberOfGoods > 0) {
            driver.findElement(By.xpath(".//a[@class='link'][contains(text(), 'Checkout')]")).click();
            int numberOfShortcuts;
            if (!isElementPresent(By.xpath(".//ul[@class='shortcuts']/li"))) {
                numberOfShortcuts = 1;
            }
            else {
                numberOfShortcuts = driver.findElements(By.xpath(".//ul[@class='shortcuts']/li")).size();
            }

            for (int i = 0; i < numberOfShortcuts; i++) {
                WebElement orderSummary = driver.findElement(By.xpath(".//table[@class='dataTable rounded-corners']"));
                if (isElementPresent(By.xpath(".//ul[@class='shortcuts']/li[1]"))) {
                    driver.findElement(By.xpath(".//ul[@class='shortcuts']/li[1]")).click();
                }
                driver.findElement(By.xpath(".//button[@type='submit'][@name='remove_cart_item']")).click();
                wait.until(ExpectedConditions.stalenessOf(orderSummary));
            }
        }
        driver.findElement(By.xpath(".//i[@class='fa fa-home']")).click();
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
