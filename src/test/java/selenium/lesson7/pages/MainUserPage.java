package selenium.lesson7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainUserPage extends Page {

    public MainUserPage(WebDriver driver) {
        super(driver);
    }

    public void openMainUserPage() {
        driver.navigate().to(configReader.getMainPageURL());
    }

    public boolean isPageOpened() {
        return driver.findElements(By.id("box-most-popular")).size() > 0;
    }
}