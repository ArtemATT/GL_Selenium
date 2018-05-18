package selenium.lesson2;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class NavigationTest {
    public WebDriver driver;
    public static final String siteUrl = "http://localhost/litecart/admin";
    public static final String mainMenuLocator = ".//ul[@id='box-apps-menu']/li[@id='app-']";
    public static final String mainSubMenuLocator = ".//ul[@id='box-apps-menu']/li[@id='app-']/ul/li";
    public static final String pageHeaderLocator = ".//h1";
    public static final String adminName = "admin";
    public static final String adminPass = "admin";

    @Before
    public void startChromeDriver() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }
    @After
    public void stopChromeDriver(){
        driver.quit();

    }

    @Test
    public void test(){
        WebElement pageHeaderWebElement;
        driver.get(siteUrl);
        loginAsUser(adminName, adminPass);
        int numberOfMenuItems = driver.findElements(By.xpath(mainMenuLocator)).size();

        int countMenuItems = 0;
        while (countMenuItems < numberOfMenuItems) {
            driver.findElements(By.xpath(mainMenuLocator)).get(countMenuItems).click();
            int numberOfSubMenuItems = driver.findElements(By.xpath(mainSubMenuLocator)).size();

            if (numberOfSubMenuItems == 0) {
                pageHeaderWebElement = driver.findElement(By.xpath(pageHeaderLocator));
                Assert.assertTrue(pageHeaderWebElement != null);
            }
            else {
                int countSubMenuItems = 0;
                while (countSubMenuItems < numberOfSubMenuItems) {
                    driver.findElements(By.xpath(mainSubMenuLocator)).get(countSubMenuItems).click();
                    pageHeaderWebElement = driver.findElement(By.xpath(pageHeaderLocator));
                    Assert.assertTrue(pageHeaderWebElement != null);
                    countSubMenuItems++;
                }
            }
            countMenuItems++;

        }
  }

    public void loginAsUser(String name, String pass){
        WebElement userName = driver.findElement(By.name("username"));
        userName.sendKeys(name);
        WebElement userPass = driver.findElement(By.name("password"));
        userPass.sendKeys(pass);
        WebElement loginButton = driver.findElement(By.name("login"));
        loginButton.click();
    }
}