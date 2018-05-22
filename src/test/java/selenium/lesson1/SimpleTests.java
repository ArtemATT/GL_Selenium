package selenium.lesson1;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SimpleTests {
    public static final String siteUrl = "http://google.com";
    public WebDriver driver;

    @After
    public void stop() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void simpleTestChrome() {
        ChromeDriverManager.getInstance().setup();
        driver = new ChromeDriver();
        driver.get(siteUrl);
    }

    @Test
    public void simpleTestFirefox() {
        FirefoxDriverManager.getInstance().setup();
        driver = new FirefoxDriver();
        driver.get(siteUrl);
    }

    @Test
    public void simpleTestInternetExplorer() {
        InternetExplorerDriverManager.getInstance().setup();
        driver = new InternetExplorerDriver();
        driver.get(siteUrl);
    }

//    @Test
//    public void simpleTestEdge() {
//        EdgeDriverManager.getInstance().setup();
//        driver = new EdgeDriver();
//        driver.get(siteUrl);
//    }
}