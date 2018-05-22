package selenium.lesson3;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class PropertyVerification {
    private static final String siteUrl = "http://localhost/litecart/";
    private static final String elNameOnMainXpath = ".//div[@id='box-campaigns']//div[@class='name']";
    private static final String regPriceOnMainXpath = ".//div[@id='box-campaigns']//div[@class='price-wrapper']/s[@class='regular-price']";
    private static final String camPriceOnMainXpath = ".//div[@id='box-campaigns']//div[@class='price-wrapper']/strong[@class='campaign-price']";
    private static final String elNameOnItemXpath = ".//div[@id='box-product']//h1";
    private static final String regPriceOnItemXpath = ".//div[@id='box-product']//div[@class='price-wrapper']/s[@class='regular-price']";
    private static final String camPriceOnItemXpath = ".//div[@id='box-product']//div[@class='price-wrapper']/strong[@class='campaign-price']";

    WebDriver driver;

     @After
    public void stopDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void propertyVerificationChrome() {
        driver = new ChromeDriver();
        test();
    }

    @Test
    public void propertyVerificationFirefox() {
        driver = new FirefoxDriver();
        test();

    }

    @Test
    public void propertyVerificationInternetExplorer() {
        driver = new InternetExplorerDriver();
        test();
    }

    public void test() {
        driver.get(siteUrl);
        driver.manage().window().maximize();

        if (isElementPresent(By.xpath(elNameOnMainXpath))) {
            String productNameMain = driver.findElement(By.xpath(elNameOnMainXpath)).getAttribute("innerText");
            String regularPrice = driver.findElement(By.xpath(regPriceOnMainXpath)).getAttribute("innerText");
            String regularPriceDecor = driver.findElement(By.xpath(regPriceOnMainXpath)).getCssValue("text-decoration-line");
            String campaignPrice = driver.findElement(By.xpath(camPriceOnMainXpath)).getAttribute("innerText");
            String campaignPriceColor = driver.findElement(By.xpath(camPriceOnMainXpath)).getCssValue("color");
            String campaignPriceFontWeight = getFontWeight();
            driver.findElement(By.xpath(elNameOnMainXpath)).click();

            if (isElementPresent(By.xpath(elNameOnItemXpath))) {

                Assert.assertEquals(productNameMain,
                        driver.findElement(By.xpath(elNameOnItemXpath)).getAttribute("innerText"));
                Assert.assertEquals(regularPrice,
                        driver.findElement(By.xpath(regPriceOnItemXpath)).getAttribute("innerText"));
                Assert.assertEquals(getColor(),
                        driver.findElement(By.xpath(regPriceOnItemXpath)).getCssValue("color"));
                Assert.assertEquals(regularPriceDecor,
                        driver.findElement(By.xpath(regPriceOnItemXpath)).getCssValue("text-decoration-line"));
                Assert.assertEquals(campaignPrice,
                        driver.findElement(By.xpath(camPriceOnItemXpath)).getAttribute("innerText"));
                Assert.assertEquals(campaignPriceColor,
                        driver.findElement(By.xpath(camPriceOnItemXpath)).getCssValue("color"));
                Assert.assertEquals(campaignPriceFontWeight,
                        driver.findElement(By.xpath(camPriceOnItemXpath)).getCssValue("font-weight"));
            }

            else System.out.println("Item page is not open yet.");
        }
        else System.out.println("Item has not been detected on Main page.");
    }

    private boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    private String getColor(){
         if (driver instanceof FirefoxDriver) {
             return "rgb(102, 102, 102)";
         }
         return "rgba(102, 102, 102, 1)";
    }

    private String getFontWeight() {
         if (driver instanceof ChromeDriver) {
             return driver.findElement(By.xpath(camPriceOnMainXpath)).getCssValue("font-weight");
         }
         return "700";
    }
}