package selenium.lesson7.app;

import org.openqa.selenium.WebDriver;
import selenium.lesson7.pages.AdminLoginPage;
import selenium.lesson7.pages.Item;
import selenium.lesson7.pages.MainUserPage;
import selenium.lesson7.utils.ConfigReader;

public class Application {

    private WebDriver driver;
    private AdminLoginPage adminLoginPage;
    private MainUserPage mainUserPage;
    private Item item;

    public Application() {
        ConfigReader configReader = new ConfigReader();
        driver = configReader.getBrowserDriver();
        adminLoginPage = new AdminLoginPage(driver);
        mainUserPage = new MainUserPage(driver);
        item = new Item(driver);
    }

    public void loginAsAdmin() {
        if (adminLoginPage.openAdminLoginPage().isPageOpened()) {
            adminLoginPage.enterAdminName().enterAdminPassword().clickLogin();
        }
    }

    public void addItemsToCart(int numberOfItems) {
        if (!mainUserPage.isPageOpened()) {
            mainUserPage.openMainUserPage();
        }
        item.addItemsToCart(numberOfItems);
    }

    public void removeAllItemsFromCart() {
        item.removeAllItemsFromCart();
    }

    public int getNumberOfItemsInCart () {
        return item.getNumberOfItemsInCart();
    }

    public void quit() {
        driver.quit();
    }
}