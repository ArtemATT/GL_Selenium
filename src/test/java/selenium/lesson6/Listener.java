package selenium.lesson6;

import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Listener extends AbstractWebDriverEventListener {

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("Search of the element '" + by + "' has been started.");
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        System.out.println("Element '" + by + "' has been found.");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tempFile.toPath(), new File("Screenshot-" + System.currentTimeMillis() + ".jpg").toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(" Something went wrong: " + throwable.getMessage() + " Refer to " + System.getProperty("user.dir") + " for screenshot.");
    }
}