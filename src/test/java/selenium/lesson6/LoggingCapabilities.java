package selenium.lesson6;

import com.automation.remarks.video.annotations.Video;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoggingCapabilities {
    EventFiringWebDriver eventFiringWebDriver;
    WebDriverWait wait;
    private static final String mainMenuLocator = ".//ul[@id='box-apps-menu']/li[@id='app-']";
    private static final String mainSubMenuLocator = ".//ul[@id='box-apps-menu']/li[@id='app-']/ul/li";

    @Before
    public void startChromeDriver() {
        eventFiringWebDriver = new EventFiringWebDriver(new ChromeDriver());
        eventFiringWebDriver.register(new Listener());
        wait = new WebDriverWait(eventFiringWebDriver, 5);
        eventFiringWebDriver.manage().window().maximize();
    }
    @After
    public void stopChromeDriver(){
        if (eventFiringWebDriver != null) {
            eventFiringWebDriver.quit();
        }
    }

    @Test @Video
    public void test(){
        eventFiringWebDriver.navigate().to("http://localhost/litecart/admin");
        loginAsUser("admin", "admin");

        int numberOfMenuItems = eventFiringWebDriver.findElements(By.xpath(mainMenuLocator)).size();
        int countMenuItems = 0;
        while (countMenuItems < numberOfMenuItems) {
            eventFiringWebDriver.findElements(By.xpath(mainMenuLocator)).get(countMenuItems).click();

            int numberOfSubMenuItems = eventFiringWebDriver.findElements(By.xpath(mainSubMenuLocator)).size();
            if (numberOfSubMenuItems == 0) {
                Assert.assertTrue(isElementPresent(By.xpath(".//h1")));
            }
            else {
                int countSubMenuItems = 0;
                while (countSubMenuItems < numberOfSubMenuItems) {
                    eventFiringWebDriver.findElements(By.xpath(mainSubMenuLocator)).get(countSubMenuItems).click();
                    Assert.assertTrue(isElementPresent(By.xpath(".//h1")));
                    countSubMenuItems++;
                }
            }
            countMenuItems++;
        }
    }

    private boolean isElementPresent(By locator) {
        try {
            eventFiringWebDriver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
        }
    }

    public void loginAsUser(String name, String pass){
        eventFiringWebDriver.findElement(By.name("username")).sendKeys(name);
        eventFiringWebDriver.findElement(By.name("password")).sendKeys(pass);
        eventFiringWebDriver.findElement(By.name("login")).click();
    }
}