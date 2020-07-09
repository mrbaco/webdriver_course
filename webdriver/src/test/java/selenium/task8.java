package selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class task8 {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, 10);

        driver.manage().timeouts().implicitlyWait(7, TimeUnit.SECONDS);
    }

    @Test
    public void test() {
        driver.get("http://localhost/");

        List<WebElement> products = driver.findElements(By.cssSelector(".product"));
        products.forEach(product -> {
            int numberOfStickers = product.findElements(By.cssSelector(".sticker")).size();
            Assert.assertTrue(numberOfStickers == 0 || numberOfStickers == 1);
        });
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
