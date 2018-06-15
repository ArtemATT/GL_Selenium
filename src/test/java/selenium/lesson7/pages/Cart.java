package selenium.lesson7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Cart extends Item {
    Cart(WebDriver driver) {
        super(driver);
    }

    public void removeAllItemsFromCart() {
        int numberOfShortcuts = getNumberOfShortcuts();
        for (int i = 0; i < numberOfShortcuts; i++) {
            WebElement orderSummary = driver.findElement(By.xpath(".//table[@class='dataTable rounded-corners']"));
            if (isElementPresent(By.xpath(".//ul[@class='shortcuts']/li[1]"))) {
                driver.findElement(By.xpath(".//ul[@class='shortcuts']/li[1]")).click();
            }
            removeButtonClick();
            wait.until(ExpectedConditions.stalenessOf(orderSummary));
        }
    }

    Cart navigateToCart() {
        try {
            driver.findElement(By.xpath(".//a[@class='link'][contains(text(), 'Checkout')]")).click();
            return this;
        } catch (NoSuchElementException ex) {return null;}
    }

    private int getNumberOfShortcuts() {
        if (!isElementPresent(By.xpath(".//ul[@class='shortcuts']/li"))) {
            return  1;
        } else {
            return driver.findElements(By.xpath(".//ul[@class='shortcuts']/li")).size();
        }
    }

    private void removeButtonClick() {
        try {
            driver.findElement(By.xpath(".//button[@type='submit'][@name='remove_cart_item']")).click();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
    }
}