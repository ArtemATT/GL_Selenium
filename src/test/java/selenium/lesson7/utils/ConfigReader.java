package selenium.lesson7.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private Properties properties;
    private final String propertyFilePath = "D:\\Trainings\\Selenium\\Codelines\\src\\test\\java\\selenium\\lesson7\\configs\\Configuration.properties";

    public ConfigReader(){
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(propertyFilePath));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Configuration.properties not found at " + propertyFilePath);
        }
    }

    public int getImplicitWait() {
        String implicitlyWait = properties.getProperty("implicitlyWait");
        if (implicitlyWait != null) {
            return Integer.parseInt(implicitlyWait);
        } else throw new RuntimeException("'implicitlyWait' is not specified in the Configuration.properties file: " + propertyFilePath);
    }

    public String getAdminPageURL() {
        String adminPageURL = properties.getProperty("adminPageURL");
        if (adminPageURL != null) {
            return adminPageURL;
        } else throw new RuntimeException("'adminPageURL' is not specified in the Configuration.properties file: " + propertyFilePath);
    }

    public String getMainPageURL() {
        String mainPageURL = properties.getProperty("mainPageURL");
        if (mainPageURL != null) {
            return mainPageURL;
        } else throw new RuntimeException("'mainPageURL' is not specified in the Configuration.properties file: " + propertyFilePath);
    }

    public String getAdminLogin() {
        String adminLogin = properties.getProperty("adminLogin");
        if (adminLogin != null) {
            return adminLogin;
        } else throw new RuntimeException("'adminLogin' is not specified in the Configuration.properties file: " + propertyFilePath);
    }

    public String getAdminPassword() {
        String adminPassword = properties.getProperty("adminPassword");
        if (adminPassword != null) {
            return adminPassword;
        } else throw new RuntimeException("'adminLogin' is not specified in the Configuration.properties file: " + propertyFilePath);
    }

    public WebDriver getBrowserDriver() {
        String browser = properties.getProperty("browser");
        if (browser.equals("chrome")) {
            return new ChromeDriver();
        }
        if (browser.equals("firefox")) {
            return new FirefoxDriver();
        }
        if (browser.equals("ie")) {
            return new InternetExplorerDriver();
        }
        else throw new RuntimeException("'browser' is not specified in the Configuration.properties file: " + propertyFilePath);
    }
}