package selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleContains;

public class theFirstTest {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void start() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
    }

    @Test
    public void test() {
        driver.get("https://market.severstal.com/ru/ru");

        driver.findElement(By.xpath("//*[contains(@class, 'SearchBox')]//input"))
                .sendKeys("арматура");
        driver.findElement(By.xpath("//*[contains(@class, 'SearchBox')]//input"))
                .submit();

        wait.until(titleContains("арматура"));
    }

    @After
    public void stop() {
        driver.quit();
        driver = null;
    }

}
