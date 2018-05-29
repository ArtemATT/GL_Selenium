package selenium.lesson4;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateNewItem {
    private WebDriver driver;
    private SimpleDateFormat formatter = new SimpleDateFormat("mm-ss");
    private final String shortDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue.";
    private final String description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Suspendisse sollicitudin ante massa, eget ornare libero porta congue. Cras scelerisque dui non consequat sollicitudin. " +
            "Sed pretium tortor ac auctor molestie. Nulla facilisi. Maecenas pulvinar nibh vitae lectus vehicula semper. Donec et aliquet velit. Curabitur non ullamcorper mauris. In hac habitasse platea dictumst." +
            " Phasellus ut pretium justo, sit amet bibendum urna. Maecenas sit amet arcu pulvinar, facilisis quam at, viverra nisi. Morbi sit amet adipiscing ante. Integer imperdiet volutpat ante, sed venenatis urna volutpat a. " +
            "Proin justo massa, convallis vitae consectetur sit amet, facilisis id libero.";

    public Date date = new Date();

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
    public void createNewItem() {
        try {
            driver.navigate().to("http://localhost/litecart/admin");
            loginAsUser("admin", "admin");
            driver.findElement(By.xpath(".//div[@id='box-apps-menu-wrapper']//li//*[contains(text(), 'Catalog')]")).click();
            driver.findElement(By.xpath(".//*[@class='button'][contains(text(), 'Add New Product')]")).click();
            fillGeneralTab();
            fillInformationTab();
            fillPriceTab();
            driver.findElement(By.xpath(".//button[@type='submit'][@name='save']")).click();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertTrue(isElementPresent(By.xpath(".//table[@class='dataTable']//a[contains(text(),'"+"GL Duck " + formatter.format(date)+"')]")));
    }

    public void loginAsUser(String name, String pass){
        driver.findElement(By.name("username")).sendKeys(name);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.name("login")).click();
    }

    public void fillGeneralTab() {
        try {
            driver.findElement(By.xpath(".//a[contains(text(), 'General')]")).click();
            driver.findElement(By.xpath(".//*[@type='radio'][@name='status'][@value='1']")).click();
            driver.findElement(By.xpath(".//*[@type='text'][@name='name[en]']")).sendKeys("GL Duck " + formatter.format(date));
            driver.findElement(By.xpath(".//*[@type='text'][@name='code']")).sendKeys("gl-86");
            driver.findElement(By.xpath(".//*[@type='checkbox'][@data-name='Root']")).click();
            driver.findElement(By.xpath(".//*[@type='checkbox'][@data-name='Rubber Ducks']")).click();
            driver.findElement(By.xpath(".//*[@type='checkbox'][@value='1-3']")).click();
            driver.findElement(By.xpath(".//*[@type='number'][@name='quantity']")).clear();
            driver.findElement(By.xpath(".//*[@type='number'][@name='quantity']")).sendKeys("55");
            Select soldOutStatus = new Select(driver.findElement(By.xpath(".//*[@name='sold_out_status_id']")));
            soldOutStatus.selectByVisibleText("Temporary sold out");
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("image.jpg").getFile());
            driver.findElement(By.xpath(".//*[@type='file'][@name='new_images[]']")).sendKeys(file.getAbsolutePath());
            WebElement dateValidFrom =  driver.findElement(By.xpath(".//*[@name='date_valid_from']"));
            dateValidFrom.click();
            dateValidFrom.sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, "7", "17", "2017");
            WebElement dateValidTo =  driver.findElement(By.xpath(".//*[@name='date_valid_to']"));
            dateValidTo.click();
            dateValidTo.sendKeys(Keys.ARROW_LEFT, Keys.ARROW_LEFT, "7", "17", "2019");
        } catch (Exception e) {
            System.out.println("General tab can't be filled");
            e.printStackTrace();
        }
    }

    public void fillInformationTab() {
        try {
            driver.findElement(By.xpath(".//a[contains(text(), 'Information')]")).click();
            Select manufacturer = new Select(driver.findElement(By.xpath(".//*[@name='manufacturer_id']")));
            manufacturer.selectByVisibleText("ACME Corp.");
            driver.findElement(By.xpath(".//*[@type='text'][@name='short_description[en]']")).sendKeys(shortDescription);
            driver.findElement(By.xpath(".//div[@class='trumbowyg-editor']")).sendKeys(description);

        } catch (Exception e) {
            System.out.println("Information tab can't be filled");
            e.printStackTrace();
        }
    }

    public void fillPriceTab() {
        try {
            driver.findElement(By.xpath(".//a[contains(text(), 'Price')]")).click();
            WebElement priceWebEl = driver.findElement(By.xpath(".//*[@name='purchase_price']"));
            priceWebEl.click();
            priceWebEl.clear();
            priceWebEl.sendKeys("30");
            Select priceCurrency = new Select(driver.findElement(By.xpath(".//*[@name='purchase_price_currency_code']")));
            priceCurrency.selectByValue("USD");
            driver.findElement(By.xpath(".//*[@name='prices[USD]'][@data-type='currency']")).sendKeys("50");

        } catch (Exception e) {
            System.out.println("Price tab can't be filled");
            e.printStackTrace();
        }
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