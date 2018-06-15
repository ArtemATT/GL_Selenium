package selenium.lesson7.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminLoginPage extends Page {

    public AdminLoginPage(WebDriver driver) {
        super(driver);
    }

    public AdminLoginPage openAdminLoginPage() {
        driver.navigate().to(configReader.getAdminPageURL());
        return this;
    }

    public boolean isPageOpened() {
        return driver.findElements(By.id("box-login")).size() > 0;
    }

    public AdminLoginPage enterAdminName() {
        driver.findElement(By.name("username")).sendKeys(configReader.getAdminLogin());
        return this;
    }

    public AdminLoginPage enterAdminPassword() {
        driver.findElement(By.name("password")).sendKeys(configReader.getAdminPassword());
        return this;
    }

    public void clickLogin() {
        driver.findElement(By.name("login")).click();
        wait.until((WebDriver driver) -> driver.findElement(By.id("box-apps-menu")));
    }
}