package selenium.lesson1;

import io.github.bonigarcia.wdm.ChromeDriverManager;
import io.github.bonigarcia.wdm.EdgeDriverManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;
import io.github.bonigarcia.wdm.InternetExplorerDriverManager;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class SimpleTests {
    public static final String siteUrl = "http://google.com";

    @Test
    public void simpleTestChrome() {
        ChromeDriverManager.getInstance().setup();
        WebDriver driver = new ChromeDriver();
        driver.get(siteUrl);
        driver.quit();
    }

    @Test
    public void simpleTestFirefox() {
        FirefoxDriverManager.getInstance().setup();
        WebDriver driver = new FirefoxDriver();
        driver.get(siteUrl);
        driver.quit();
    }

    @Test
    public void simpleTestInternetExplorer() {
        InternetExplorerDriverManager.getInstance().setup();
        WebDriver driver = new InternetExplorerDriver();
        driver.get(siteUrl);
        driver.quit();
    }

    @Test
    public void simpleTestEdge() {
        EdgeDriverManager.getInstance().setup();
        WebDriver driver = new EdgeDriver();
        driver.get(siteUrl);
        driver.quit();
    }
}