package selenium.lesson7.tests;

import org.junit.Assert;
import org.junit.Test;

public class SimpleTests extends TestBase{
    private final int addItemsNumber = 3;

    @Test
    public void loginAsAdminTest() {
        app.loginAsAdmin();
    }

    @Test
    public void addItemsTest(){
        app.addItemsToCart(addItemsNumber);

        Assert.assertEquals(addItemsNumber, app.getNumberOfItemsInCart());
    }

    @Test
    public void removeItemsTest(){
        int expectedNumberOfItems = app.getNumberOfItemsInCart();
        app.addItemsToCart(addItemsNumber);
        app.removeAllItemsFromCart();

        Assert.assertEquals(expectedNumberOfItems, app.getNumberOfItemsInCart());
    }
}