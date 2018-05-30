package selenium.lesson5;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Set;

public class LinksInNewWindows {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void startChromeDriver() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 5);
        driver.manage().window().setSize(new Dimension( 1500, 800));
    }

    @After
    public void stopChromeDriver(){
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLinksInNewWindows(){
        driver.navigate().to("http://localhost/litecart/admin");
        loginAsUser("admin", "admin");

        String baseWindow = driver.getWindowHandle();
        driver.findElement(By.xpath(".//ul[@id='box-apps-menu']//*[contains(text(), 'Countries')]")).click();
        driver.findElement(By.xpath(".//tr[@class='row']//*[contains(text(), 'Ukraine')]")).click();
        int numberOfLinks = driver.findElements(By.xpath(".//i[@class='fa fa-external-link']")).size();
        for (int i = 0; i < numberOfLinks; i++) {
            int numberOfWindows = driver.getWindowHandles().size();
            driver.findElements(By.xpath(".//i[@class='fa fa-external-link']")).get(i).click();
            wait.until(ExpectedConditions.numberOfWindowsToBe(numberOfWindows + 1));
            closeAllWindowsButMain(baseWindow);
        }

        Assert.assertEquals(1, driver.getWindowHandles().size());
        Assert.assertEquals(baseWindow, driver.getWindowHandle());
    }

    public void closeAllWindowsButMain(String mainWindow) {
        Set<String> set = driver.getWindowHandles();
        set.remove(mainWindow);

        for (String window : set) {
            driver.switchTo().window(window);
            driver.close();
            driver.switchTo().window(mainWindow);
        }
    }

    public void loginAsUser(String name, String pass){
        driver.findElement(By.name("username")).sendKeys(name);
        driver.findElement(By.name("password")).sendKeys(pass);
        driver.findElement(By.name("login")).click();
    }
}