package selenium.lesson7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class Item extends Page {

    public Item(WebDriver driver) {
        super(driver);
    }

    public void addItemsToCart(int numberOfItems) {
        for (int i = 0; i < numberOfItems; i++) {
            int numberOfItemsInitial = Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText());
            clickOnFirstMostPopularItem();
            selectSizeIfPresent();
            addToCartButtonClick();
            wait.until(ExpectedConditions.attributeContains(By.xpath(".//a/*[@class='quantity']"), "innerText", String.valueOf(numberOfItemsInitial + 1)));
            navigateToHome();
        }
    }

    public void removeAllItemsFromCart() {
        int numberOfGoods = getNumberOfItemsInCart();
        Cart cart = new Cart(driver);
        if (numberOfGoods > 0) {
            cart.navigateToCart().removeAllItemsFromCart();
        }
        navigateToHome();
    }

    public int getNumberOfItemsInCart() {
        try {
            return Integer.parseInt(driver.findElement(By.xpath(".//span[@class='quantity']")).getText());
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    private void addToCartButtonClick() {
        try {
            driver.findElement(By.xpath(".//button[@type='submit'][@name='add_cart_product']")).click();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void clickOnFirstMostPopularItem() {
        try {
            driver.findElement(By.xpath(".//ul[@class='listing-wrapper products']/li[1]")).click();
        } catch (NoSuchElementException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void selectSizeIfPresent() {
        if (isElementPresent(By.xpath(".//td[@class='options'][contains(text(), Size)]"))) {
            Select size = new Select(driver.findElement(By.xpath(".//select[@name='options[Size]']")));
            size.selectByIndex(1);
        }
    }
}